#!/bin/sh

LIST=${0:-list.txt}

# list.txt has list of urls
# incorrect curl arguments
cat $LIST | while read F ; do S=$(echo $F| sed 's| |0x20|g'); T=$(echo $F| sed 's|^.*/||g'); curl -O "$F" ;done

# NOTE: commented
# after copying rename the files - may need to undo URL-encoded names 
# awaiting the check that the filename does contain a 0x20
#for F in $(ls -1 *.mp4) ; do T=$(echo $F| sed 's|0x20| |g') ; mv $F "$T";  done
