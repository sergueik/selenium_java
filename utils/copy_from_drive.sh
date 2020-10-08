#!/bin/sh
USER=$(whoami)
TOPIC=${1:-apigee}
DRIVE=${2:-Elements 2}
if [ -z "${DEBUG}" ] ; then
  echo 'DEBUG was not set explicitly, default is false'
  DEBUG='false'
fi
# echo "DEBUG=${DEBUG}"
if [ "${DEBUG}" = 'true' ] ; then
  NOOP='echo '
else
  NOOP=''
fi
# echo NOOP=${NOOP}
if $DEBUG ; then
  NOOP='echo '
else
  NOOP=''
fi
# echo NOOP=${NOOP}
ls "/media/$USER/${DRIVE}/Data/Downloads/" | grep -i "${TOPIC}" |xargs -IX $NOOP cp "/media/$USER/${DRIVE}/Data/Downloads/X" ~/Downloads/
