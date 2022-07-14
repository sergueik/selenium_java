#!/bin/sh
# based on:
# https://stackoverflow.com/questions/17648033/counting-number-of-directories-in-a-specific-directory
# NOTE: performance wise find based approach is better

BASE=${1:-/tmp}
DEBUG=$2
LOG=${3:-/tmp/result.txt}
cat /dev/null > $LOG
for DIR in $(ls -1 $BASE); do

find $BASE/$DIR -maxdepth 1 -type d | wc -l | grep -qv '^1$'
  if [ $? -eq 0 ] ; then
    if [ ! -z $DEBUG ] ; then
      1>&2 echo "collect $DIR"
    fi
    echo $DIR| tee -a $LOG
  else
    if [ ! -z $DEBUG ] ; then
      1>&2 echo "reject $DIR"
    fi
  fi
done
echo 'Results:'
cat $LOG

