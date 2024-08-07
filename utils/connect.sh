#!/bin/bash
# origin: https://serverfault.com/questions/167416/how-can-i-automatically-change-directory-on-ssh-login 
# see also https://serverfault.com/questions/499565/change-default-directory-when-i-ssh-to-server
# NOTE: `whoami` in Windows in git bash can be different from the user name on Linux box
DEFAULT_HOST='192.168.0.64'
DEFAULT_USER='sergueik'
# about customizing ~/.ssh/authorized_keys and ~/.ssh/config
env | grep -q 'OS='
if [ $? = 0 ] ; then
  PROJECTS_DIR="/c/developer/${DEFAULT_USER}"
else
  PROJECTS_DIR="${HOME}/src"
fi
TARGET_PROJECTS_DIR='src'

PROJECT_DIR=$(pwd | sed "s|^${PROJECTS_DIR}/||") # current directory with swapped base dir
REMOTE_HOST=${1:-$DEFAULT_HOST}
REMOTE_USER=${2:-$DEFAULT_USER}
REMOTE_DIR="${TARGET_PROJECTS_DIR}/${PROJECT_DIR}"
ssh -t $REMOTE_USER@${REMOTE_HOST} "test -d ~/$REMOTE_DIR || mkdir ~/$REMOTE_DIR; cd ~/$REMOTE_DIR; exec \$SHELL --login"
