#!/bin/sh
# cygpath -w $(pwd) | clip
pwd | sed 's|^/c/|c:\\|;s|/|\\|g;'
