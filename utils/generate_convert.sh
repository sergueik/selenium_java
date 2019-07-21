#!/bin/bash

# Convert high-res mp4 to low res mkv
# ffmpeg options tken from:
# https://opensource.com/article/17/6/ffmpeg-convert-media-file-formats

SCRIPT='/tmp/convert.sh'
# SCRIPT='/tmp/convert1.sh'
find . -iname '*mp4' |sort | while read filename ; do D=$(dirname "$filename"); S=$(basename "$filename"); T="${S/mp4/mkv}" ;  echo "pushd '${D}'"; echo "echo \"Converting \\"${S}\\"\""; echo "if [[ ! -f \"$T\" ]] ; then ffmpeg -i \"${S}\" -c:v vp9 -s hd480 -v 0 \"${T}\"; fi" ; echo popd ;  done | tee $SCRIPT
chmod +x $SCRIPT
$SCRIPT
