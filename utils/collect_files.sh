#!/bin/sh

LIST=${1:-list.txt}

# list.txt has list of urls
# incorrect curl arguments
cat $LIST | while read F
do
  echo "$F"

  S=$(echo $F| sed 's| |0x20|g')
  T=$(echo $S| sed 's|^.*/||g')
  T2=$(echo $F| sed 's|^.*/||g')
  echo wget -q -O "$T" "$F"
  wget -q -O "$T" "$F"
 #  mv $T "$T2"
done

# NOTE: commented
# after copying rename the files - undo URL-encoded names
for F in $(ls -1 *.mp4)
do
  T=$(echo $F| sed 's|0x20| |g')
  if [ "$F" != "$T" ]; then
    mv "$F" "$T"
  fi
done

