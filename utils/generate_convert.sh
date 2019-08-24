#!/bin/bash

# Convert high-res mp4 to low res mkv
# ffmpeg options taken from:
# https://opensource.com/article/17/6/ffmpeg-convert-media-file-formats
# https://ffmpeg.org/ffmpeg-utils.html#Video-size
# e.g.  svga, hd480, hd720
SCRIPT="/tmp/convert.$$.sh"
EXTENSION='mp4'
SIZE=${1:-svga}
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
  echo "Running ${SCRIPT}"
  $SCRIPT
  rm -f $SCRIPT
fi


