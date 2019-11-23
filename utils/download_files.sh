#!/bin/bash


# origin: https://gist.github.com/cosimo/3760587
OPTS=`getopt -o nhsbl: --long dry-run,help,batterycheck,listfile: -n 'parse-options' -- "$@"`
if [ $? != 0 ] ; then echo "Failed parsing options." >&2 ; exit 1 ; fi

VERBOSE=false
DRY_RUN=false
BATTERY_CHECK=false
LISTFILE=

while true; do
  case "$1" in
    -v | --verbose ) VERBOSE=true; shift ;;
    -n | --dry-run ) DRY_RUN=true; shift ;;
    -b | --battery-check ) BATTERY_CHECK=true; shift ;;
    -l | --listfile ) LISTFILE="$2"; shift; shift ;;
    -- ) shift; break ;;
    * ) break ;;
  esac
done

if [[ "${VERBOSE}" = "true" ]]; then
  echo VERBOSE=$VERBOSE
  echo HELP=$HELP
  echo DRY_RUN=$DRY_RUN
  echo LISTFILE=$LISTFILE
  echo BATTERY_CHECK=$BATTERY_CHECK
fi

TITLE='Course Title'
FILELIST="files.$$.txt"
1>/dev/null 2>/dev/null pushd '/tmp'
mkdir "${TITLE}"
cd "${TITLE}"
pwd
if [[ -z $LISTFILE ]] ; then
cat <<EOF| grep '[a-z]'| tee $FILELIST

EOF
else
  grep '[a-z]' $LISTFILE | tee $FILELIST
fi
# incorrect: quotes too early
# awk '{print "\""$0"\""}' $FILELIST | while read FILENAME; do wget $FILENAME ; done
# correct:
awk '{print $0}' $FILELIST | while read FILENAME; do wget --no-check-certificate "${FILENAME}" ; done
rm -f $FILELIST

popd 1>/dev/null 2>/dev/null
exit 0
