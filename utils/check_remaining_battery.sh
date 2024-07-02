#!/bin/bash

# based on: https://www.cyberciti.biz/faq/linux-laptop-battery-status-temperature/ 

OPTS=`getopt -o hb: --long help,battery: -h 'parse-options' -- "$@"`
if [ $? != 0 ] ; then echo "Failed parsing options." >&2 ; exit 1 ; fi
HELP=false
BATTERY_SIZE=5

while true; do
  case "$1" in
    -h | --help )      HELP=true; shift ;;
    -b | --battery )   BATTERY_SIZE="$2"; shift; shift ;;
    -- ) shift; break ;;
    * ) break ;;
  esac
done
if [[ "${HELP}" = "true" ]]; then
cat <<EOF
Usage:
  $0 [-h|--help] [-b|--battery] THRESHOLD
EOF
exit 0
fi
echo BATTERY_SIZE=$BATTERY_SIZE


check_remaining_battery () {
STATE=$(upower -i $(upower -e | grep -i battery | head -1)| grep -E 'state: *' | awk '{print $2}')
HOURS=$(upower -i $(upower -e | grep -i battery | head -1)| grep 'time to empty' | grep hours | awk '{print $4}')
MINUTES=$(upower -i $(upower -e | grep -i battery | head -1)| grep 'time to empty' | grep minutes | awk '{print $4}' | sed 's|\.[0-9][0-9]*||')
echo "Battery state: \"${STATE}\""
if [ ! -z $MINUTES ] ;  then
  echo "Battery empty time: \"${MINUTES}\" minutes"
fi
if [ ! -z $HOURS ] ;  then
  echo "Battery empty time: \"${HOURS}\" hours"
fi
if [[ "${STATE}" -eq 'discharging' ]]
# when the battery is charging the time to empty is meaningless
# NOTE: == is better than -eq , since -ne does not work
then
  if [ ! -z $MINUTES ] ;  then	  
    echo "Comparing Battery empty time: ${MINUTES} to ${BATTERY_SIZE}"
    if [ $MINUTES -lt $BATTERY_SIZE ]
    then
      echo 'Too little battery left - will abort'
      # exit 0
    fi
  fi
else 
  echo "Battery state is: \"${STATE}\""
fi
if [[ "${STATE}" != 'charging' ]]
# when the battery is charging the time to empty is meaningless
then
  if [ ! -z $MINUTES ] ;  then
    echo "Comparing Battery empty time: ${MINUTES} to ${BATTERY_SIZE}"
    if [ $MINUTES -lt $BATTERY_SIZE ] ; then
      echo 'Too little battery left - will abort'
     #  exit 0
    fi
  fi
else 
  echo "Battery state is: \"${STATE}\""
fi
# TODO: -ne does not work
if [[ "${STATE}" -ne 'charging' ]]
# when the battery is charging the time to empty is meaningless
then
  if [ ! -z $MINUTES ] ;  then
    echo "Comparing Battery empty time: ${MINUTES} to ${BATTERY_SIZE}"
    if [ $MINUTES -lt $BATTERY_SIZE ] ; then
      echo 'Too little battery left - will abort'
     #  exit 0
    fi
  fi
else 
  echo "Battery state is: \"${STATE}\""
fi
}

# NOTE: to set DEBUG need to export it from the calling shell
if [[ -z "${DEBUG}" ]] ; then
  echo 'DEBUG was not set explicitly, default is false'
  DEBUG='false'
fi
echo "DEBUG=${DEBUG}"
check_remaining_battery

