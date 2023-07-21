#!/bin/bash
# https://www.ubuntu18.com/install-vnc-server-ubuntu-18/
# TODO: check the DIPLAY_PORT part
COMMAND=${1:-stop}
if [ "$COMMAND" = "stop" ]; then
  # NOTE: not double [
  DISPLAY_PORT=${2:-1}

  tightvncserver -kill :$DISPLAY_PORT
  echo  "Detect already running tigntvnc ${DISPLAY_PORT}"
  RUNNING_PID2=$(netstat -npl 2>/dev/null| grep STREAM |grep $DISPLAY_PORT| awk '{print $9}'|head -1 | sed 's/\/.*$//')

  RUNNING_PID=$(netstat -npl 2>/dev/null| grep STREAM |grep 'vnc'| awk '{print $9}'|head -1 | sed 's/\/.*$//')
  if [ ! -z "${RUNNING_PID}" ] ; then
    echo  "Stop running tigntvnc ${RUNNING_PID}"
    kill -HUP $RUNNING_PID
    sleep 3
    kill -KILL $RUNNING_PID
    rm "/tmp/.X11-unix/X${DISPLAY_PORT}"
    rm "/tmp/.X${DISPLAY_PORT}-lock"
  fi
else
  GEOMETRY=${2-1300x975}
  DISPLAY=${3-1}
  echo  "Launch tigntvnc"
  tightvncserver -geometry $GEOMETRY ":${DISPLAY}"

fi

