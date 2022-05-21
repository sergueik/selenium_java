#!/bin/bash
# origin: https://serverfault.com/questions/167416/how-can-i-automatically-change-directory-on-ssh-login 
# see also https://serverfault.com/questions/499565/change-default-directory-when-i-ssh-to-server
# about customizing ~/.ssh/authorized_keys and ~/.ssh/config
env | grep -q 'OS='
if [ $? = 0 ] ; then
  PROJECTS_DIR='/c/developer/sergueik'
else
  PROJECTS_DIR="${HOME}/src"
fi
TARGET_PROJECTS_DIR='src'

PROJECT_DIR=$(pwd | sed "s|^${PROJECTS_DIR}/||") # current directory with swapped base dir
# NOTE: `whoami` in Windows in git bash can be different from
REMOTE_HOST=${1:-192.168.0.64}
REMOTE_USER=${2:-sergueik}

ssh -t $REMOTE_USER@${REMOTE_HOST} "cd ~/$TARGET_PROJECTS_DIR/$PROJECT_DIR; exec \$SHELL --login"
