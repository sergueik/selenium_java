#!/bin/bash

PROJECT=$(pwd | sed 's|^.*/||') # current directory
REMOTE_HOST=${1:-192.168.0.64}
REMOTE_USER=${2:-sergueik}
# unfortunately `whoami` in Windows in git bash can be different from
# target unix machine user

# using gnu tar exclude option: --exclude='packages'
# TODO: switch to compact --exclude=PATTERN exclude files, given as a PATTERN
if [[ $DEBUG == 'true' ]] ; then
  echo tar czvf ../$PROJECT.tar.gz --exclude='.git*' --exclude='target' --exclude='packages' --exclude='obj' --exclude='bin' --exclude='.terraform' .
fi
tar czvf ../$PROJECT.tar.gz --exclude='.git*' --exclude='target' --exclude='packages' --exclude='obj' --exclude='bin' --exclude='.terraform' .
if [[ $DEBUG == 'true' ]] ; then
  echo scp ../$PROJECT.tar.gz $REMOTE_USER@${REMOTE_HOST}:
fi
echo "sending $( stat -c '%s'  ../$PROJECT.tar.gz ) bytes"
scp ../$PROJECT.tar.gz $REMOTE_USER@${REMOTE_HOST}:


