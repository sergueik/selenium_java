#!/bin/bash
IMAGE=${1:-lenovo40withminikube}
DISK=${2:-/dev/sdc}
STAGING='/media/sergueik/Windows8_OS/Users/Serguei/Desktop/Private/z/'
if [ ! -d $STAGING ] ;then
  exit 0
fi
pushd $STAGING
dd if=$IMAGE.img of=$DISK bs=500M conv=noerror status=progress
popd
