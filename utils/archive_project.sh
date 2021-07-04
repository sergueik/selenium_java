#!/bin/bash

# inspired by https://www.cyberforum.ru/shell/thread2855488.html

REMOVE=$1
if [ -z "$REMOVE" ]; then
  REMOVE_FILES=''
else
  REMOVE_FILES='--remove-files'
fi
# default name archive by current directory name
# NOTE: -C "path" doe not appear to work if -P flag was set when archiving
# find $DIR -type f  -exec tar -P -uf $ARCHIVE --remove-files {} \;
# tar -C /tmp -xvf $ARVHIVE
# will end up creating /tmp/home/$USER/...

CWD=$(pwd)
PROJECT=${CWD##*/}
PROJECT=$(echo $CWD | sed 's|^.*/||')
ARCHIVE=${2:-${PROJECT}.tar}
DIR=${3:-$CWD}
# see also https://gist.github.com/caruccio/4340471
echo "archive $DIR into $ARCHIVE"
find $DIR -type f | xargs -IX sh -c 'F=$0; echo $F | sed "s|$1/||"' X $DIR \;  | xargs -IX tar -P -uf $ARCHIVE $REMOVE_FILES X

exit 0
