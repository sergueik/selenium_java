#!/bin/bash
# https://serverok.in/install-xfce-vnc-remote-desktop-on-ubuntu
# TODO: check the DIPLAY_PORT part
# NOTE: unable to connecting to DISPLAY :0
# NOTE: the original article suggests having in ~/.vnc/xstartup
# /usr/bin/vncconfig -nowin -display :1 &
#
# startxfce4 &
# this is not stable: no window manager
# https://www.ubuntu18.com/install-vnc-server-ubuntu-18
# see also: https://a-katyrev.blogspot.com/2019/11/vnc-lubuntu-1804.html
COMMAND=${1:-stop}
if [ "$COMMAND" = 'stop' ]; then
  # NOTE: not double [
  DISPLAY_PORT=${2:-1}

  tightvncserver -kill :$DISPLAY_PORT
  echo "Detect already running tigntvnc ${DISPLAY_PORT}"
  RUNNING_PID2=$(netstat -npl 2>/dev/null| grep STREAM |grep $DISPLAY_PORT| awk '{print $9}'|head -1 | sed 's/\/.*$//')

  RUNNING_PID=$(netstat -npl 2>/dev/null| grep STREAM |grep 'vnc'| awk '{print $9}'|head -1 | sed 's/\/.*$//')
  if [ ! -z "${RUNNING_PID}" ] ; then
    echo "Stop running tigntvnc ${RUNNING_PID}"
    kill -HUP $RUNNING_PID
    sleep 3
    kill -KILL $RUNNING_PID
    rm "/tmp/.X11-unix/X${DISPLAY_PORT}"
    rm "/tmp/.X${DISPLAY_PORT}-lock"
  fi
else
  GEOMETRY=${2:-1366x768}
  DISPLAY_PORT=${3:-1}
  echo "Launching tigntvnc on $DISPLAY_PORT"
  tightvncserver -geometry $GEOMETRY ":${DISPLAY_PORT}"
  # NOTE: after the launch of the script observed two displays being created:
  # netstat -npl 2>/dev/null| grep STREAM | grep X11
  # unix  2      [ ACC ]     STREAM     LISTENING     27294    -                    /tmp/.X11-unix/X0
  # unix  2      [ ACC ]     STREAM     LISTENING     27293    -                    @/tmp/.X11-unix/X0
  # unix  2      [ ACC ]     STREAM     LISTENING     34367    2353/Xtightvnc       /tmp/.X11-unix/X1
  # and vnc client can connect to either $HOST:0 or $HOST:1

fi

