#!/bin/bash
# canonical example
# NOTE: no long options in this snippet
while getopts 'hdve:s:' OPT
do
  case $OPT in
    d) echo 'debug switch detected'
      DEBUG=true
      ;;
    v) echo 'verbose switch detected'
      VERBOSE=true
      ;;
    s) echo 'size option detected'
       SIZE=$OPTARG
       ;;
    e) echo 'reading extension'
       EXT=$OPTARG
       ;;
    *) echo 'unrecognized option' 
       ;;
  esac
done
echo VERBOSE=$VERBOSE
echo DEBUG=$DEBUG
echo SIZE=$SIZE
echo EXT=$EXT

