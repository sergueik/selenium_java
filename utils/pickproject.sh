#!/bin/bash
PROJECT=$1
if [ -z $PROJECT ] ; then
  PROJECT=$(pwd | sed 's|^.*/||') # current directory
else
  mkdir $PROJECT
  cd $PROJECT
fi
tar xzvf ~/$PROJECT.tar.gz
find . -type f -exec touch {} \;
git add --all .
git reset HEAD  */bin */obj
