#!/bin/bash
# Standalone Selenium hub launcher script The bub is listening to 4444

HUB_IP_ADDRESS=${1:-127.0.0.1}
HUB_PORT=${2:-4444}
if [ -z "${SELENIUM_VERSION}" ] ; then
  SELENIUM_VERSION=2.53.0
fi
if [ -z "${LOG4J_VERSION}" ] ; then
  LOG4J_VERSION=1.2.17
fi
# Verify that `selenium-sever-standalone.jar` points to the correct version of `selenium jar`.
# to install the selenium-server-standalone.jar easiest is through a dummy pom.xml dependency
#+ <dependency>
#+    <groupId>org.seleniumhq.selenium</groupId>
#+    <artifactId>selenium-server-standalone</artifactId>
#+    <version>2.53.0</version>
#+</dependency>
# find ~/.m2/repository/ -iname 'selenium-server-standalone*jar' -a -name "*${SELENIUM_VERSION}*" | xargs -IX  ln -s X selenium-server-standalone.jar
/bin/readlink selenium-server-standalone.jar | grep -in $SELENIUM_VERSION
if [ $? != 0 ]
then
  echo "The Selenium version is incorrect: need version $SELENIUM_VERSION"
  ls -l selenium*jar
  exit 0
fi

# Only one selenium hub can run at a time
# This code detects the already running instances.
HUB_PID=$(sudo netstat -npl | grep $HUB_PORT | awk '{print $7}'| grep '/java'|head -1 | sed 's/\/.*$//')
if [[ $HUB_PID ]]
then
  echo killing java process $HUB_PID
  ps -ocomm -oargs -p $HUB_PID
  # alternarive
  # sudo ps ax -opid,comm,args | grep java | grep $HUB_PORT 2>/dev/null| tail -1 | awk '{print $1}' | xargs kill -HUP
  # sleep 1
  # sending HUP
  kill -HUP $HUB_PID
  # echo
fi

# options for java runtime.
LAUNCHER_OPTS='-XX:MaxPermSize=256M -Xmn128M -Xms256M -Xmx256M'

SELENIUM_HUB_OPTS=$(
cat <<EOF
-Dlog4j.configuration=/root/hub.log4j.properties \
-cp log4j-${LOG4J_VERSION}.jar:selenium-server-standalone.jar:log4j.xml:log4j.properties org.openqa.grid.selenium.GridLauncher \
-role hub \
-host ${HUB_IP_ADDRESS} \
-port ${HUB_PORT}
EOF
)

java $LAUNCHER_OPTS $SELENIUM_HUB_OPTS &
