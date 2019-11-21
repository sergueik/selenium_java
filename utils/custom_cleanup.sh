#!/bin/sh
# origin: http://www.cyberforum.ru/shell/thread2526703.html

BASENAME='test'
# touch $BASENAME.tgz
# touch "${BASENAME}2.tgz"
# touch "${BASENAME}3.tgz"

for MASK in "${BASENAME}*"{'.tgz','.sht','_prev.lnk'}; do echo 1>&2 "Probing File mask: ${MASK}";  for FILE in $(ls -1 $MASK 2>/dev/null); do if [ -f $FILE ]; then echo rm -i "${FILE}" ; fi ;  done ;  done

for MASK in "${BASENAME}*"{'.tgz','.sht','_prev.lnk'}; do
  echo 1>&2 "Probing File mask: ${MASK}"
  for FILENAME in $(ls -1 $MASK 2>/dev/null); do
    if [ -f "$FILENAME" ]; then
      echo rm -i "${FILENAME}"
    fi
  done
done
# rm -i test2.tgz
# rm -i test3.tgz
# rm -i test.tgz
# NOTE:  with space in file name, a different snippet:
BASENAME='test file'
echo "BASENAME=\"${BASENAME}\""
# touch "${BASENAME}.tgz"
# touch "${BASENAME} 2.tgz"
# touch "${BASENAME} 3.tgz"

for MASK in "${BASENAME}.*"{'.tgz','.sht','_prev.lnk'}; do
  echo 1>&2 "Probing File mask: \"${MASK}\""
  ls -1 | grep "${MASK}" 2>/dev/null | while read FILENAME; do
    if [ -f "${FILENAME}" ]; then
      echo rm -i "${FILENAME}"
    fi
  done
done
# rm -i "test file 2.tgz"
# rm -i "test file 3.tgz"
# rm -i "test file.tgz"
exit 0

# NOTE 2>/dev/null to remove the spam logging



