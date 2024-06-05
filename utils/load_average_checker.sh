#!/bin/sh
if [ -z "${DEBUG}" ]; then
  DEBUG=false
else
  DEBUG=true
  1>&2 echo 'Setting DEBUG to ' $DEBUG
fi
ACTION=$2
FACTOR=${1:-1}
# default factor is '1', which means max two sleeping proceses per CPU
# NOTE: browsers tend to create a high load average 
# without really heavy cPU loads due to JS single threaded
# example of CPU hungry proces: media conversion
# https://stackoverflow.com/questions/20231362/error-message-during-the-expr-command-execution-expr-non-integer-argument
LOAD_AVERAGE=$(cat /proc/loadavg | cut -f 1 -d ' ')
CPU_COUNT=$(cat /proc/cpuinfo |  grep 'cpu cores' | head -1 | awk '{print $NF}')
if $DEBUG ; then
  1>&2 echo "LOAD_AVERAGE=${LOAD_AVERAGE}"
  1>&2 echo "CPU_COUNT=${CPU_COUNT}"
fi
RESULT=$(echo $LOAD_AVERAGE \- $CPU_COUNT \* $FACTOR | bc -l )
if $DEBUG ; then
  1>&2 echo "RESULT=${RESULT}"
fi
RESULT=$(echo $RESULT \> 0 | bc -l )
if $DEBUG ; then
  1>&2 echo "RESULT=${RESULT}"
fi
DATABASE='load_average.db'
TABLE='load_average'
if [ "$ACTION" = 'init' ] ; then
  SQL="DROP TABLE IF EXISTS ${TABLE}; CREATE TABLE IF NOT EXISTS ${TABLE} (id INTEGER PRIMARY KEY AUTOINCREMENT, ins_date DATETIME, status TINYINT NOT NULL, load_average REAL comment TEXT NULL);"
  if $DEBUG ; then
    echo "sqlite3 $DATABASE \"$SQL\""
  fi
  sqlite3 $DATABASE "$SQL"
fi
if [ "$RESULT" = 1 ] ; then
  echo 'load is high'
else
  echo 'normal'
fi
SQL="INSERT INTO ${TABLE} (ins_date,status,load_average) VALUES (CURRENT_TIMESTAMP, $RESULT, $LOAD_AVERAGE); "
if $DEBUG ; then
  echo "sqlite3 $DATABASE \"$SQL\""
fi
sqlite3 $DATABASE "$SQL"

# Median calculation
# https://stackoverflow.com/questions/15763965/how-can-i-calculate-the-median-of-values-in-sqlite
# illustrate with subquery
# https://www.sqlitetutorial.net/sqlite-subquery/

# SELECT x,y FROM ((SELECT COUNT(1) x FROM  load_average), (SELECT COUNT(1) y FROM load_average WHERE load_average > (SELECT load_average FROM load_average ORDER BY load_average LIMIT 1 OFFSET (SELECT COUNT(1) FROM load_average) / 2) ));

# select median, better take

# SELECT x,y FROM ((SELECT COUNT(1) x FROM  load_average), (SELECT COUNT(1) y FROM load_average WHERE load_average > (SELECT load_average FROM load_average ORDER BY load_average LIMIT 1 OFFSET CAST((SELECT COUNT(1) FROM load_average) * .5 AS INTEGER)) ));

# select .75 median, and the threshold value

# SELECT x,y,z FROM ((SELECT COUNT(1) x FROM  load_average), (SELECT COUNT(1) y FROM load_average WHERE load_average > (SELECT load_average FROM load_average ORDER BY load_average LIMIT 1 OFFSET CAST((SELECT COUNT(1) FROM load_average) * .75 AS INTEGER)) ),  (SELECT load_average z FROM load_average ORDER BY load_average LIMIT 1 OFFSET CAST((SELECT COUNT(1) FROM load_average) * .75 AS INTEGER) ) );

# with headers:

# .headers on
# SELECT total,stopped,threshold FROM ((SELECT COUNT(1) total FROM  load_average), (SELECT COUNT(1) stopped FROM load_average WHERE load_average > (SELECT load_average FROM load_average ORDER BY load_average LIMIT 1 OFFSET CAST((SELECT COUNT(1) FROM load_average) * .75 AS INTEGER)) ),  (SELECT load_average threshold FROM load_average ORDER BY load_average LIMIT 1 OFFSET CAST((SELECT COUNT(1) FROM load_average) * .75 AS INTEGER) ) );

