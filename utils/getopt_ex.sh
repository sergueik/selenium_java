#!/bin/bash
OPTS=`getopt -o dvs: --long debug,verbose,size -n 'parse-options' -- "$@"`
if [ $? != 0 ] ; then echo "Failed parsing options." >&2 ; exit 1 ; fi

while true; do
  case "$1" in
    -d | --debug ) DEBUG=true; shift ;;
    -v | --verbose ) VERBOSE=true; shift ;;
    -s | --size ) SIZE="$2"; shift; shift ;;
    -- ) shift; break ;;
    * ) break ;;
  esac
done

  echo VERBOSE=$VERBOSE
  echo DEBUG=$DEBUG
  echo SIZE=$SIZE

