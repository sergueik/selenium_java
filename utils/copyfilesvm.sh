#!/bin/sh
PROJECT=$(pwd | sed 's|^.*/||') # current directory
cp -R . ~/Downloads/$PROJECT
