#!/bin/sh
# based on:
# https://stackoverflow.com/questions/17648033/counting-number-of-directories-in-a-specific-directory
# NOTE: performance wise find based approach is better

BASE=${1:-/tmp}
DEBUG=$2
LOG=${3:-/tmp/result.txt}
cat /dev/null > $LOG
for DIR in $(ls -1 $BASE); do

  find $BASE/$DIR/* -maxdepth 0 -type d 2>/dev/null | wc -l | grep -q '[1-9]'
  # alternarively,
  # ls -ld $BASE/$DIR/* 2>/dev/null | wc -l | grep -q '[1-9]'
  # NOTE: both will carp about empty dirs by printing to STDERR
  # '/tmp/basedir/xxx/*': No such file or directory
  # hence the redirection
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

