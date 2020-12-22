#!/bin/bash
# NOTE: to set DEBUG need to export it from the calling shell
if [[ -z "${DEBUG}" ]] ; then
  echo 'DEBUG was not set explicitly, default is false'
  DEBUG='false'
fi
echo "DEBUG=${DEBUG}"
# origin: https://gist.github.com/cosimo/3760587
OPTS=`getopt -o vnhp:r: --long verbose,dry-run,help,path,replace: -n 'parse-options' -- "$@"`
if [ $? != 0 ] ; then echo "Failed parsing options." >&2 ; exit 1 ; fi
VERBOSE=false
HELP=false
DRY_RUN=false
REPLACE='' # NOTE:  blank value will lead it to skip
WINDOWS_PATH='.'
EXTENSION='flac'

while true; do
  case "$1" in
    -v | --verbose ) VERBOSE=true; shift ;;
    -h | --help )    HELP=true; shift ;;
    -n | --dry-run ) DRY_RUN=true; shift ;;
    -p | --path ) WINDOWS_PATH="$2"; shift; shift ;;
    -r | --replace ) REPLACE="$2"; shift ;;
    -- ) shift; break ;;
    * ) break ;;
  esac
done

if [[ ! -z "${REMOVE}" ]]; then
  REMOVE_TITLE_FRAGMENT="${REMOVE}" 
else
  REMOVE_TITLE_FRAGMENT=${1:- Artist Name}
fi
if [[ -z "${WINDOWS_PATH}" ]]; then
  WINDOWS_WINDOWS_PATH=${2:-C:\\Users\\Serguei\\Music\\Disk Name}
fi
if [[ "${VERBOSE}" = "true" ]]; then
  echo VERBOSE=$VERBOSE
  echo HELP=$HELP
  echo DRY_RUN=$DRY_RUN
  echo WINDOWS_PATH=$WINDOWS_PATH
  echo REPLACE=$REPLACE
else
  STAGING=$(echo $WINDOWS_PATH| sed 's|\\|/|g;s|^\([a-z]\):|/\1|i' ) 
  echo "STAGING=${STAGING}"
  pushd "$STAGING"
  pwd
  ls -1 *flac| while read F  
  do 
    G=$(echo $F|sed "s|$REMOVE_TITLE_FRAGMENT||")
    mv "$F" "$G" 
  done
fi
exit 0
