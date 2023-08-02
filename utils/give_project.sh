#!/bin/sh
pwd | sed 's|^/c/|c:\\|;s|/|\\|g;'
