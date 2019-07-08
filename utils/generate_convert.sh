#!/bin/sh

# ffmpeg options from: https://opensource.com/article/17/6/ffmpeg-convert-media-file-formats

find . -iname '*mp4' | while read filename ; do D=$(dirname "$filename"); S=$(basename "$filename"); T="${S/mp4/mkv}" ;  echo "pushd '${D}'"; echo "echo \"Converting '${S}'\"";echo "ffmpeg -i '${S}' -c:v vp9 -s hd480 -v 0 '${T}'" ; echo popd ;  done | 
tee /tmp/convert.sh
chmod +x /tmp/convert.sh
/tmp/convert.sh
