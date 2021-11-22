#!/bin/bash
IMAGE=${1:-lenovo120s}
DISK=${2:-/dev/sdc}
# When copying larger ssd to a later useful is to provide count e.g.
# count=64 bs=500M
STAGING='/media/sergueik/Windows8_OS/Users/Serguei/Desktop/Private/z/'
if [ ! -d $STAGING ] ;then
  exit 0
fi
pushd $STAGING
dd if=$DISK of=$IMAGE.img bs=500M conv=noerror status=progress
popd
