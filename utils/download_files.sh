#!/bin/bash


TITLE='Course Title'
FILELIST="files.$$.txt"
1>/dev/null 2>/dev/null pushd '/tmp'
mkdir "${TITLE}"
cd "${TITLE}"
pwd
cat <<EOF| grep '[a-z]'| tee $FILELIST
list of urls with possible whitespace in the path 

EOF
# incorrect: quotes too early
# awk '{print "\""$0"\""}' $FILELIST | while read FILENAME; do wget $FILENAME ; done
# correct:
awk '{print $0}' $FILELIST | while read FILENAME; do wget "${FILENAME}" ; done
rm -f $FILELIST

popd 1>/dev/null 2>/dev/null
exit 0
