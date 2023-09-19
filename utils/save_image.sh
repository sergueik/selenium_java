#!/bin/bash
IMAGE=${1:-lenovo40withminikube}
DISK=${2:-/dev/sdc}
# When copying larger ssd to a later useful is to provide count e.g.
# count=64 bs=500M
# for reducing the volumne of copy to the Space In-Use onl
# see https://www.baeldung.com/linux/clone-space-in-use-from-disk
# see also https://askubuntu.com/questions/260620/resize-dev-loop0-and-increase-space
# for partclone see https://ru.linux-console.net/?p=11393#gsc.tab=0 (in Russian)
# original forum: https://qna.habr.com/q/1306968 (in Russian) -  recommends preparing source partition with zero fills on sparse via dd if=/dev/zero of=$DISK/zero; rm $DISK/zero
STAGING='/media/sergueik/Windows8_OS/Users/Serguei/Desktop/Private/z/'
if [ ! -d $STAGING ] ;then
  exit 0
fi
pushd $STAGING
dd if=$DISK of=$IMAGE.img bs=500M conv=noerror status=progress
popd
