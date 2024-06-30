#!/bin/bash

OPTS=`getopt -o dhb: --long debug,help,battery: -h 'parse-options' -- "$@"`
if [ $? != 0 ] ; then echo "Failed parsing options." >&2 ; exit 1 ; fi
HELP=false
BATTERY_CHECK=false
SIZE=5

while true; do
  case "$1" in
    -d | --debug )     DEBUG=true; shift ;;
    -h | --help )      HELP=true; shift ;;
    -b | --battery )   BATTERY_CHECK=true; SIZE="$2"; shift; shift ;;
    -- ) shift; break ;;
    * ) break ;;
  esac
done
if [[ "${HELP}" = "true" ]]; then
cat <<EOF
Usage:
  $0 [-d|--debug] [-h|--help] [-b|--battery] THRESHOLD
EOF
exit 0
fi
if [[ "${DEBUG}" = "true" ]]; then
  echo HELP=$HELP
  echo SIZE=$SIZE
  echo BATTERY_CHECK=$BATTERY_CHECK
fi


# based on: https://www.cyberciti.biz/faq/linux-laptop-battery-status-temperature/ 

check_remaining_battery () {
STATE=$(upower -i $(upower -e | grep -i battery | head -1)| grep -E 'state: *' | awk '{print $2}')
HOURS=$(upower -i $(upower -e | grep -i battery | head -1)| grep 'time to empty' | grep hours | awk '{print $4}')
MINUTES=$(upower -i $(upower -e | grep -i battery | head -1)| grep 'time to empty' | grep minutes | awk '{print $4}' | sed 's|\.[0-9][0-9]*||')
if [[ "${DEBUG}" = 'true' ]]; then
  echo "Battery state: \"${STATE}\""
  if [ ! -z $MINUTES ] ;  then
    echo "Battery empty time: \"${MINUTES}\" minutes"
  fi
  if [ ! -z $HOURS ] ;  then
    echo "Battery empty time: \"${HOURS}\" hours"
  fi
fi
if [[ "${STATE}" -eq 'discharging' ]]
# when the battery is charging the time to empty is meaningless
# NOTE: == is better than -eq , since -ne does not work
then
  if [ ! -z $MINUTES ] ;  then	  
    if [[ "${DEBUG}" = 'true' ]]; then
      echo "Comparing Battery empty time: ${MINUTES} to ${SIZE}"
    fi
    if [ $MINUTES -lt $SIZE ]
    then
      echo 'Too little battery left - will abort'
      # exit 0
    fi
  fi
fi
if [[ "${STATE}" != 'charging' ]]
# when the battery is charging the time to empty is meaningless
then
  if [ ! -z $MINUTES ] ;  then
    if [[ "${DEBUG}" = 'true' ]]; then
      echo "Comparing Battery empty time: ${MINUTES} to ${SIZE}"
    fi
    if [ $MINUTES -lt $SIZE ]
    then
      echo 'Too little battery left - will abort'
     #  exit 0
    fi
  fi
fi
# TODO: -ne does not work
if [[ "${STATE}" -ne 'charging' ]]
# when the battery is charging the time to empty is meaningless
then
  if [ ! -z $MINUTES ] ;  then
    if [[ "${DEBUG}" = 'true' ]]; then
      echo "Comparing Battery empty time: ${MINUTES} to ${SIZE}"
    fi
    if [ $MINUTES -lt $SIZE ]
    then
      echo 'Too little battery left - will abort'
     #  exit 0
    fi
  fi
fi
}

# NOTE: to set DEBUG need to export it from the calling shell
if [[ -z "${DEBUG}" ]] ; then
  echo 'DEBUG was not set explicitly, default is false'
  DEBUG='false'
fi
echo "DEBUG=${DEBUG}"
check_remaining_battery

