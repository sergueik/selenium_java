#!/bin/sh

javafxpackager -deploy -native \
  -outdir build \
  -outfile SQLibri-0.4.3 \
  -srcdir build/libs/ \
  -srcfiles sqlibri-0.4.3.jar \
  -appclass com.sqlibri.App \
  -name "SQLibri" \
  -title "SQLibri"
