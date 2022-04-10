#!/bin/bash
# origin: https://serverfault.com/questions/167416/how-can-i-automatically-change-directory-on-ssh-login 
# see also https://serverfault.com/questions/499565/change-default-directory-when-i-ssh-to-server
# about customizing ~/.ssh/authorized_keys and ~/.ssh/config
PROJECT_IDIR=$(pwd | sed 's|^/c/developer/sergueik|src|') # current directory with swapped base dir
# NOTE: `whoami` in Windows in git bash can be different from
REMOTE_HOST=${1:-192.168.0.64}
REMOTE_USER=${2:-sergueik}

ssh -t $REMOTE_USER@${REMOTE_HOST} "cd ~/$PROJECT_IDIR; exec \$SHELL --login"
