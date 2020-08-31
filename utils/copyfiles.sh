#!/bin/sh

TARGETHOST='192.168.0.64'
USER='sergueik'
DESTINATION_FOLDER='Downloads'
CNT1=100
CNT2=25
ls -1Sr *| head -$CNT1| tail -$CNT2| while read B ; do scp "${B}" ${USER}@${TARGETHOST}:${DESTINATION_FOLDER} ; done

