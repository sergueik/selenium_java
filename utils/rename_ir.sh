#!/bin/sh
REMOVE_TITLE_FRAGMENT=${2:--git.ir}
EXTENSION=${1:-mp4}
# after copying rename the files - undo URL-encoded names
for F in $(ls -1 *.$EXTENSION)
do
  T=$(echo $F| sed 's|0x20| |g'| sed 's|%20| |g')
  if [ "$F" != "$T" ]; then
    mv "$F" "$T"
  fi
done


  ls -1 *.$EXTENSION| while read F ; do 
G=$(echo $F|sed "s|$REMOVE_TITLE_FRAGMENT||") ; 
  if [ "$F" != "$G" ]; then
mv "$F" "$G" ; 
  fi
done
