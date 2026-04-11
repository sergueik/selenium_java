#!/usr/bin/env bash
set -euo pipefail

OWNER=${1:?owner required}
REPO=${2:?repo required}
PROJECT=${3:?project folder required}
BRANCH=${4:-master}
OUTPUT_DIR=${5:-.}

TREE_URL="https://api.github.com/repos/${OWNER}/${REPO}/git/trees/${BRANCH}?recursive=1"
GITHUB_BASE="https://raw.githubusercontent.com/${OWNER}/${REPO}/${BRANCH}"

if [ "$OUTPUT_DIR" != '.' ] ; then mkdir -p "$OUTPUT_DIR" ;fi

curl -fsSL "$TREE_URL" |
jq -r --arg prefix "${PROJECT}/" '
    .tree[]
    | select(.type == "blob")
    | select(.path | startswith($prefix))
    | .path
' |tr -d '\r' | tee /tmp/a.$$

# DO not modify IFS	
cat /tmp/a.$$ | while read -r SOURCE; do
    TARGET="${OUTPUT_DIR}/${SOURCE}"

    mkdir -p "$(dirname "$TARGET")"
    URL="$GITHUB_BASE/$SOURCE"

    echo "Restoring: \"$SOURCE\""
    echo "From: \"$URL\""
    echo 1>&2  curl -fsSL "$URL" -o "$TARGET"
    curl -fsSL "$URL" -o "$TARGET"
done
exit 0
# NOTE
# while read -r SOURCE; do
#   ...
# done < /tmp/a.$$

# avoids creation of pipeline subshell and is slightly faster
