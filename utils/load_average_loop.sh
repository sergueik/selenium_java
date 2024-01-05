#!/bin/bash
$(pwd)/load_average_checker.sh  1 init
INTERVAL=${1:-30}
while true; do
  $(pwd)/load_average_checker.sh
  echo "sleeping $INTERVAL second"
  sleep $INTERVAL
done

