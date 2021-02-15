#!/bin/sh

LIST=${1:-list.txt}
REMOVE_TITLE_FRAGMENT=${2:--git.ir}
# list.txt has list of urls
# incorrect curl arguments
cat $LIST | while read F
do
  echo "$F"

  S=$(echo $F| sed 's| |0x20|g')
  T=$(echo $S| sed 's|^.*/||g')
  T2=$(echo $F| sed 's|^.*/||g')
  echo wget -q --no-check-certificate -O "$T" "$F"
  wget -q --no-check-certificate -O "$T" "$F"
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

# Remove some portion of the name
if false ; then
  if [[ '' != "${REMOVE_TITLE_FRAGMENT}"  ]]; then
    ls -1 *mp4| while read F ; do G=$(echo $F|sed "s|$REMOVE_TITLE_FRAGMENT||") ; mv "$F" "$G" ; done
  fi
fi
# Better way is to use /usr/bin/prename
TITLE_FRAGMENT_ARGUMENT=$(echo $REMOVE_TITLE_FRAGMENT| sed 's|-|\\-|g')
prename -v "s|$TITLE_FRAGMENT_ARGUMENT||" *mp4
