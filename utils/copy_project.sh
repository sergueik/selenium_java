#!/bin/bash

PROJECT=$(pwd | sed 's|^.*/||') # current directory
REMOTE_HOST=${1:-192.168.0.64}
REMOTE_USER=${2:-sergueik}
# unfortunately `whoami` in Windows in git bash can be different from
# target unix machine user

tar czvf ../$PROJECT.tar.gz .
if [[ $DEBUG == 'true' ]] ; then
  echo scp ../$PROJECT.tar.gz $REMOTE_USER@${REMOTE_HOST}:
fi
scp ../$PROJECT.tar.gz $REMOTE_USER@${REMOTE_HOST}:

