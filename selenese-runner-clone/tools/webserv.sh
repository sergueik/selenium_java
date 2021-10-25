#!/bin/bash

set -eu

cd $(dirname "$0")/..

# set port number
port="${1:-80}"
if [ "$OSTYPE" != "cygwin" -a "$port" -lt 1024 ]; then
  sudo=sudo
else
  sudo=""
fi

$sudo java -cp "$(./tools/classpath.sh)" \
      -Dlogback.configurationFile=src/shade/resources/logback.xml \
      -Dsrj.log.level=DEBUG \
      jp.vmi.selenium.testutils.WebServer $port src/test/resources/htdocs
