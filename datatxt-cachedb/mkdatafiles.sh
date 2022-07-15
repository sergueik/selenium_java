#!/usr/bin/env bash
# #!/usr/bin/bash
# NOTE: github bash is different location (/usr/bin/bash) than vanilla ubuntu (/bin/bash)

YEAR=${1:-2022}
MONTH=${2:-06}
DAY=${3:-30}


date="$YEAR$MONTH$DAY" # no separators
datadir=$date
mkdir $datadir
pushd $datadir
for H in $(seq 0 1 23) ; do 
  for M in $(seq 0 1 59) ; do
    # filename="data.txt.${date}${H}${M}"
    # https://linuxize.com/post/bash-printf-command/
    filename=$(printf "data.txt.${date}%02d%02d" $H $M)
    # echo $filename
    # touch $filename
    # TODO: date format to %s
    # TODO; randomize sec
    TIMESTAMP=$(date --date="${YEAR}/${MONTH}/${DAY} ${H}:${M}:00" +%s)
    # pad
    TIMESTAMP="${TIMESTAMP}"
    cat <<EOF>$filename
cpu: 12
memory: 22
disk: 42.5
load_average: 1 2 3 4 6
rpm: 102
uptime: 0
timestamp: ${TIMESTAMP}

EOF
  done
done
popd
