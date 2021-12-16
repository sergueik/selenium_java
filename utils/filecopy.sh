#!/bin/bash
env | grep -q 'OS='
if [ $? = 0 ] ; then
  PROJECTS_DIR='/c/developer/sergueik'
else
  PROJECTS_DIR="${HOME}/src"
fi
TARGET_PROJECTS_DIR='src'

PROJECT=$(pwd | sed "s|^${PROJECTS_DIR}/||") # directory in the project
FILEMASK=${1}
if [ "$FILEMASK" = '' ] ; then
  echo "Usage: $0 <FILE MASK> <HOST> <USER>"
fi
REMOTE_HOST=${2:-192.168.0.64}
REMOTE_USER=${3:-sergueik}
if [[ $DEBUG == 'true' ]] ; then
  echo "scp $FILEMASK  $REMOTE_USER@${REMOTE_HOST}:$TARGET_PROJECTS_DIR/$PROJECT"
fi
scp $FILEMASK  $REMOTE_USER@${REMOTE_HOST}:$TARGET_PROJECTS_DIR/$PROJECT

