#!/bin/bash

NODE_PORT=5555
NODE_HOST=$(ip addr | sed -En '/scope global/s/inet (([0-9]*\.){3}[0-9]*).*/\1/p')
# http://stackoverflow.com/questions/13322485/how-to-i-get-the-primary-ip-address-of-the-local-machine-on-linux-and-os-x
# export NODE_HOST=$(ifconfig | sed -En 's/127.0.0.1//;s/.*inet (addr:)?(([0-9]*\.){3}[0-9]*).*/\2/p')
# export NODE_HOST=$(hostname -I)
HUB_IP_ADDRESS=127.0.0.1
HUB_PORT=4444
SELENIUM_VERSION=2.47.1
LOG4J_VERSION=1.2.17
ROLE=node
SELENIUM_HOME=`pwd`
# configuration
DEFAULT_CONFIG_FILE='node.json'

usage() {
  cat<<EOF

This is bare bones standalone script to launch node on a specific "selenium port"
One is able to run two or more nodes on different displays on the same instance

Usage: $0 [-s <NODE_PORT>] [-d [vnc|xvfb]] [-l <LOGFILE>]
Options specify

-s NODE_PORT=5555 Selenium-specific TCP port node will be using uniquely identifying the node
   A separate instance of Xvfb / VncServer will need to run on DISPLAY_PORT=NODE_PORT-4555
-d DRIVER=[xvfb | vnv]  display driver (experimental)
EOF
  echo $USAGE
  exit 1
}

# TODO
terminate(){
  echo "Terminating the running instance(s):"
  ps ax | grep [j]ava | grep '\-role' | grep $ROLE
  echo "This is currently a no-op"
  exit 0
}

NODE_PORT_ARG=$NODE_PORT
DISPLAY_PORT_ARG=$DISPLAY_PORT
# use getopt 'short' here to get the input arguments
while getopts "hts:l:d:" arg; do
  case $arg in
    h)
      usage
      ;;
    t)
      terminate
      ;;
    s)
      NODE_PORT_ARG=$OPTARG
      echo NODE_PORT_ARG=$NODE_PORT_ARG
      ;;
    d)
      DRIVER_ARG=$OPTARG
      echo $DRIVER_ARG | egrep -in '^(vnc|xvfb)$' > /dev/null
      if [ $? != 0 ]
      then
        echo "Unknown driver: ${DRIVER_ARG}"
        exit 1
      fi
      echo DRIVER_ARG=$DRIVER_ARG
      ;;
  esac
done
if [ "$DRIVER_ARG" == 'xvfb' ]
then
   DISPLAY_PORT_ARG=$(expr $NODE_PORT_ARG - 4555)
   echo "No visible browser. Display will be set to: ${DISPLAY_PORT_ARG}"
else
   echo 'Visible browser window.'
fi
DISPLAY_PORT=$DISPLAY_PORT_ARG

# This allows setting alternative port on the commandline
if [[ $NODE_PORT_ARG ]]
then
  echo "Starting node on port $NODE_PORT_ARG"
  NODE_PORT=$NODE_PORT_ARG

  # use unique name for CONFIG_FILE
  CONFIG_FILE="/tmp/node.$NODE_PORT.json"
  echo "Copying $DEFAULT_CONFIG_FILE to $CONFIG_FILE"
  cp --force $DEFAULT_CONFIG_FILE $CONFIG_FILE

  # default node.json file has port set to 5555
  # the next command replaces the port with specified through NODE_PORT_ARG
  sed -i 's/"port":5555/"port":$NODE_PORT/' $CONFIG_FILE
  LOG_FILE="/var/log/node-${NODE_PORT}.log"
else
  CONFIG_FILE=$DEFAULT_CONFIG_FILE
  LOG_FILE='/var/log/node.log'
fi

# Verify that `selenium-sever-standalone.jar` points to the correct version of `selenium jar`.
/bin/readlink selenium-server-standalone.jar | grep -in $SELENIUM_VERSION >/dev/null
if [ $? != 0 ]
then
  echo "The Selenium version is incorrect: need version '$SELENIUM_VERSION'"
  ls -l selenium*jar
  exit 0
fi

# Confirm if Selenum hub is already running. This is required, otherwise
# the port listener based instance cleanup logic will not work.
netstat -npl | grep $HUB_PORT | awk '{print $7}'| grep -in '/java' >/dev/null
if [ $? == 0 ]
then
  echo 'Confirmed hub is running. Continue with laucnhing the node'
else
  echo "Please launch the Selenium hub on ${HUB_PORT} first."
  exit 1
fi

if false
then
  SERVICE_INFO=$(/sbin/service --status-all | egrep -i 'vncserver|xvnc')
  echo $SERVICE_INFO
  STATUS=$(expr "$SERVICE_INFO" : '.* is \(.*\)')
  echo $STATUS | grep -ivn 'runnng'  > /dev/null
  if [ $? != 0 ]
  then
    echo 'Starting service'
    /sbin/service vncserver start
  fi
fi

# Detect running instances.
# Only one selenium node can run listening to a specific "selenium port"

echo "Checking and terminating Selenium process if already listening to ${NODE_PORT}"
NODE_PID=$(netstat -npl | grep $NODE_PORT | awk '{print $7}'| grep '/java'|head -1 | sed 's/\/.*$//')
if [[ $NODE_PID ]]
then
  echo killing java $NODE_PID
  ps -ocomm -oargs -opid -p $NODE_PID
  # sending HUP
  kill -HUP $NODE_PID
  sleep 10
  kill $NODE_PID
  echo 'Done.'
fi

if [[ $TERMINATE_FIREFOX_INSTANCES ]]
then
  echo Killing firefoxes
  # This code terminates Firefox instances
  # (unfinished)
  # WARNING - all running will be stopped
  ps ax -opid,comm | grep [f]irefox | tail -1 | awk '{print $1}' | xargs kill -HUP
  sleep 10

  PROFILE=$(grep -Eio 'Path=(.*)' ~/.mozilla/firefox/profiles.ini)
  echo "Clearing firefox history in default profile $PROFILE"
  {
  rm ~/.mozilla/firefox/$PROFILE/cookies.txt
  rm ~/.mozilla/firefox/$PROFILE/Cache/*
  rm ~/.mozilla/firefox/$PROFILE/downloads.rdf
  rm ~/.mozilla/firefox/$PROFILE/history.dat
  }  > /dev/null 2>&1
  sleep 3
fi

if $(ps ax | grep -qE '\bX\b')
then
  echo 'X Windows is running'
  DISPLAY_PORT=0
  export DISPLAY=:$DISPLAY_PORT
else

  # Detect already running Xvfb.
  echo  "Detect already running Xvfb ${DISPLAY_PORT}"
  XVFB_PID=$(netstat -npl | grep STREAM  |grep $DISPLAY_PORT| awk '{print $9}' |head -1 | sed 's/\/.*$//')
  if [[ $XVFB_PID ]]
  then
    ps -ocomm -oargs -opid -p $XVFB_PID
    echo killing Xvfb $XVFB_PID
    # NOTE change of signal sent to Xvfb
    kill $XVFB_PID
  fi

  export DISPLAY=:$DISPLAY_PORT
  # TODO : specify geometry of the display 1280x1024x24
  Xvfb $DISPLAY -ac >/dev/null 2>&1 &
fi
SELENIUM_NODE_PID=$!

# options for java runtime.
LAUNCHER_OPTS='-XX:MaxPermSize=256M -Xmn128M -Xms256M -Xmx256M'

echo 'Starting Selenium in the background'

SELENIUM_NODE_OPTS=$(
cat <<EOF
-classpath \
log4j-${LOG4J_VERSION}.jar:selenium-server-standalone.jar: \
-Dlog4j.configuration=node.log4j.properties \
org.openqa.grid.selenium.GridLauncher \
-role node \
-host ${NODE_HOST} \
-port ${NODE_PORT} \
-hub http://${HUB_IP_ADDRESS}:${HUB_PORT}/hub/register \
-nodeConfig ${CONFIG_FILE} \
-browserTimeout 12000 -timeout 12000 \
-ensureCleanSession true \
-trustAllSSLCertificates
EOF
)

java $LAUNCHER_OPTS $SELENIUM_NODE_OPTS &
# echo $NODE_PID
# ps ax | grep [j]ava