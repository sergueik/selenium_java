#!/bin/sh
OLD_VERSION=${1:-2.16.0}
NEW_VERSION=${2:-2.17.0}
# scan multple projects for CVE-2021-45105 vulnerable version and update

grep -ilr $OLD_VERSION * --include 'pom.xml' | xargs -IX echo X | while read F; do echo $F ; sed -i "s|$OLD_VERSION|$NEW_VERSION|g" $F; git add $F; done
git status .
exit
# alternative command for undo
# NOTE: quite a bit slower
find . -name pom.xml -exec grep -ilr "$NEW_VERSION" {} \; | xargs git reset HEADfind . -name pom.xml -exec grep -ilr "$NEW_VERSION" {} \; | xargs git checkout
