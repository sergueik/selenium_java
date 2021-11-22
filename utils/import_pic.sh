#!/bin/bash
# NOTE: to set DEBUG need to export it from the calling shell
# import_pic.sh -p "$(pwd)"

if [[ -z "${DEBUG}" ]] ; then
  echo 'DEBUG was not set explicitly, default is false'
  DEBUG='false'
fi
echo "DEBUG=${DEBUG}"
OPTS=`getopt -o vnhp:i: --long verbose,dry-run,help,path:image: -n 'parse-options' -- "$@"`
if [ $? != 0 ] ; then echo 'Failed parsing options.' >&2 ; exit 1 ; fi
VERBOSE=false
HELP=false
DRY_RUN=false
WINDOWS_PATH='.'
IMAGE='folder.jpg'
EXTENSION='flac'

while true; do
  case "$1" in
    -v | --verbose ) VERBOSE=true; shift ;;
    -h | --help )    HELP=true; shift ;;
    -n | --dry-run ) DRY_RUN=true; shift ;;
    -p | --path ) WINDOWS_PATH="$2"; shift; shift ;;
    -i | --image ) IMAGE="$2"; shift; shift ;;
    -- ) shift; break ;;
    * ) break ;;
  esac
done

if [[ -z "${WINDOWS_PATH}" ]]; then
  WINDOWS_WINDOWS_PATH=${2:-C:\\Users\\Serguei\\Music\\Disk Name}
fi
if [[ "${VERBOSE}" = "true" ]]; then
  echo VERBOSE=$VERBOSE
  echo HELP=$HELP
  echo DRY_RUN=$DRY_RUN
  echo WINDOWS_PATH=$WINDOWS_PATH
  echo EXTENSION=$EXTENSION
else
  STAGING=$(echo $WINDOWS_PATH| sed 's|\\|/|g;s|^\([a-z]\):|/\1|i' )
  echo "STAGING=${STAGING}"
  pushd "$STAGING"
  pwd
  ls -1 *$EXTENSION| while read F
  do
    if [[ "${DEBUG}" = 'true' ]]
    then
      echo metaflac --import-picture-from="folder.jpg" "$F"
      echo "Generated ${SCRIPT}"
    else
       metaflac --import-picture-from="$IMAGE" "$F"
    fi
  done
  popd
fi
