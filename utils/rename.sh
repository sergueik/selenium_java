#!/bin/sh

REMOVE_TITLE_FRAGMENT=${1:- Artist Name}
WINDOWS_PATH=${2:-C:\\Users\\Serguei\\Music\\Disk Name}
STAGING=$(echo $WINDOWS_PATH| sed 's|\\|/|g;s|^\([a-z]\):|/\1|i' ) ; pushd "$STAGING"; ls -1 *flac| while read F ; do G=$(echo $F|sed "s|$REMOVE_TITLE_FRAGMENT||") ; mv "$F" "$G" ; done
