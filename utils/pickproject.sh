#!/bin/bash

PROJECT=$(pwd | sed 's|^.*/||') # current directory
tar xzvf ~/$PROJECT.tar.gz
find . -type f -exec touch {} \;
git add --all .
git reset HEAD  */bin */obj
