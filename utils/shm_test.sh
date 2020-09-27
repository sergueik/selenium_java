#!/bin/bash

OPTS=`getopt -o vrhnwei:d: --long verbose,read,help,dry-run,write,erase,id:,data: -n 'parse-options' -- "$@"`
if [ $? != 0 ] ; then echo "Failed parsing options." >&2 ; exit 1 ; fi

VERBOSE=false
HELP=false
READ=false
DRY_RUN=false
WRITE=false
ERASE=false
DATA_ID='data_id'
DATA_VALUE='data value'

while true; do
  case "$1" in
    -v | --verbose ) VERBOSE=true; shift ;;
    -r | --read ) READ=true; shift ;;
    -h | --help ) HELP=true; shift ;;
    -e | --erase ) ERASE=true; shift ;;
    -w | --write ) WRITE=true; shift ;;
    -n | --dry-run ) DRY_RUN=true; shift ;;
    -i | --id ) DATA_ID="$2"; shift; shift ;;
    -d | --data ) DATA_VALUE="$2"; shift; shift ;;
    -- ) shift; break ;;
    * ) break ;;
  esac
done
if [[ -z "${DEBUG}" ]] ; then
  echo 'DEBUG was not set explicitly, default is false'
  DEBUG='false'
fi
if [[ "${VERBOSE}" = "true" ]]; then
  echo VERBOSE=$VERBOSE
  echo READ=$READ
  echo WRITE=$WRITE
  echo ERASE=$ERASE
  echo HELP=$HELP
  echo DRY_RUN=$DRY_RUN
  echo DATA_ID=$DATA_ID
  echo DATA_VALUE=$DATA_VALUE
  echo "DEBUG=${DEBUG}"
fi

if [[ "${WRITE}" = 'true' ]]; then
  if [[ "${VERBOSE}" = 'true' ]]; then
    echo "Write ${DATA_VALUE} into ${DATA_ID}"
  fi	
  $(echo $DATA_VALUE >/dev/shm/$DATA_ID);
fi
if [[ "${READ}" = 'true' ]]; then
  if [[ "${VERBOSE}" = 'true' ]]; then
    echo "Read ${DATA_ID}"
  fi	
  cat /dev/shm/$DATA_ID
fi
if [[ "${ERASE}" = 'true' ]]; then
  if [[ "${VERBOSE}" = 'true' ]]; then
    echo "Erase ${DATA_ID}"
  fi	
  rm -f /dev/shm/$DATA_ID
fi
