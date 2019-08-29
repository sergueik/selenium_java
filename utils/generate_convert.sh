#!/bin/bash

# Convert high-res mp4 to low res mkv
# ffmpeg options taken from:
# https://opensource.com/article/17/6/ffmpeg-convert-media-file-formats
# https://ffmpeg.org/ffmpeg-utils.html#Video-size
# e.g.  svga, hd480, hd720

# origin: https://gist.github.com/cosimo/3760587
OPTS=`getopt -o vhnse: --long verbose,dry-run,help,size,extension: -n 'parse-options' -- "$@"`
if [ $? != 0 ] ; then echo "Failed parsing options." >&2 ; exit 1 ; fi

VERBOSE=false
HELP=false
DRY_RUN=false
SIZE='svga'
EXTENSION='mp4'

while true; do
  case "$1" in
    -v | --verbose ) VERBOSE=true; shift ;;
    -h | --help )    HELP=true; shift ;;
    -n | --dry-run ) DRY_RUN=true; shift ;;
    -s | --size ) SIZE="$2"; shift; shift ;;
    -e | --extension ) EXTeNSION="$2"; shift; shift ;;
    -- ) shift; break ;;
    * ) break ;;
  esac
done

if [[ "${VERBOSE}" = "true" ]]; then
  echo VERBOSE=$VERBOSE
  echo HELP=$HELP
  echo DRY_RUN=$DRY_RUN
  echo SIZE=$SIZE
  echo EXTENSION=$EXTENSION
fi

SCRIPT="/tmp/convert.$$.sh"

# NOTE: to set DEBUG need to export it from the calling shell
if [[ -z "${DEBUG}" ]]
then
  echo 'DEBUG was not set explicitly, default is false'
  DEBUG='false'
fi
echo "DEBUG=${DEBUG}"
if [[ "${DEBUG}" = 'true' ]]
then
  echo "Finding files \"*${EXTENSION}\""
fi
# technically able to descend, seldom used
find . -iname "*${EXTENSION}" | sort | while read filename ; do
  D=$(dirname "$filename")
  S=$(basename "$filename")
  T="${S/${EXTENSION}/mkv}"
  if [[ "$D" != '.' ]]
  then
    echo ">/dev/null pushd '${D}'"
  fi
  echo "if [[ ! -f \"$T\" ]];"
  echo '  then';
  echo "echo \"Converting \\\"${S}\\\"\""
  echo " ffmpeg -i \"${S}\" -c:v vp9 -s ${SIZE} -v 0 \"${T}\""
  echo 'fi'
  if [[ "$D" != '.' ]]
  then
    echo '>/dev/null popd'
  fi
done | tee $SCRIPT
chmod +x $SCRIPT
if [[ "${DEBUG}" = 'true' ]]
then
  echo "Generated ${SCRIPT}"
else
  if [[ "${DRY_RUN}" = "true" ]]; then
    echo "Generated ${SCRIPT}"
  else
    echo "Running ${SCRIPT}"
    $SCRIPT
    rm -f $SCRIPT
  fi
fi

# TODO:  need to do this check in every single generate 
MINUTES=$(upower -i $(upower -e | grep -i battery | head -1)| grep 'time to empty' | grep minutes | awk '{print $4}' | sed 's|\.[0-9][0-9]*||')
if [[ ! -z $MINUTES ]] 
then
  # when the battery is charging  there is no time to empty 
  if [ $MINUTES -lt 20 ] ; 
  then 
    echo ' Too little battery left - aborting' ; 
    exit 0
  fi
fi
