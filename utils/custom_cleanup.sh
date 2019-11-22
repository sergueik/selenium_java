#!/bin/sh

# origin: http://www.cyberforum.ru/shell/thread2526703.html
# somewhat long and interesting discusion started with 
# a simple "coding interview"-grade question
export DEBUG=true
BASENAME='test'
if [ "${DEBUG}" = 'true' ]
then
  echo 'Mocking up the files'
  touch $BASENAME.tgz
  touch "${BASENAME}2.tgz"
  touch "${BASENAME}3.tgz"
fi

for MASK in "${BASENAME}*"{'.tgz','.sht','_prev.lnk'}; do echo 1>&2 "Probing File mask: ${MASK}";  for FILE in $(ls -1 $MASK 2>/dev/null); do if [ -f $FILE ]; then echo rm -i "${FILE}" ; fi ;  done ;  done

for MASK in "${BASENAME}*"{'.tgz','.sht','_prev.lnk'}; do
  echo 1>&2 "Probing File mask: ${MASK}"
  for FILENAME in $(ls -1 $MASK 2>/dev/null); do
    if [ -f "$FILENAME" ]; then
      echo rm -i "${FILENAME}"
    fi
  done
done
# will show
# rm -i test2.tgz
# rm -i test3.tgz
# rm -i test.tgz
# NOTE:  with space in file name, a different snippet:
BASENAME='test file'
echo "BASENAME=\"${BASENAME}\""

if [ "${DEBUG}" = 'true' ]
then
  echo 'Mocking up the files'
  touch $BASENAME.tgz
  touch "${BASENAME}2.tgz"
  touch "${BASENAME}3.tgz"
fi

for MASK in "${BASENAME}.*"{'.tgz','.sht','_prev.lnk'}; do
  # NOTE use 2>/dev/null to hide the spam logging
  echo 1>&2 "Probing File mask: \"${MASK}\""
  ls -1 | grep "${MASK}" 2>/dev/null | while read FILENAME; do
    if [ -f "${FILENAME}" ]; then
      echo rm -i "${FILENAME}"
    fi
  done
done
# will show
# rm -i "test file 2.tgz"
# rm -i "test file 3.tgz"
# rm -i "test file.tgz"
exit 0

BASENAME='test'
if [ "${DEBUG}" = 'true' ]
then
  echo 'Mocking up the files'
  touch $BASENAME.tgz
  touch "${BASENAME}2.tgz"
  touch "${BASENAME}3.tgz"
fi
  ls "$BASENAME"*{.tgz,.sht,_prev.lnk} 2>/dev/null | xargs -pd'\n' -IX rm X
# will show
# rm -i test2.tgz
# rm -i test3.tgz
# rm -i test.tgz
# rm -i "test file 2.tgz"
# rm -i "test file 3.tgz"
# rm -i "test file.tgz"


