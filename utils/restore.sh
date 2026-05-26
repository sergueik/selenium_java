#!/usr/bin/env bash
set -euo pipefail

usage() {
cat <<EOF
Usage:
  $0 <owner> <repo> <project> [branch] [output_dir]

Restore a subtree from a public GitHub repository without cloning.

Arguments:
  owner       GitHub owner or organization
  repo        Repository name
  project     Subdirectory to restore
  branch      Branch or tag (default: master)
  output_dir  Local target root (default: current directory)

Example:
  $0 sergueik springboot_study basic-static master basic-static
EOF
}

case "${1:-}" in
    -h|--help)
        usage
        exit 0
        ;;
esac

OWNER=${1:?owner required}
REPO=${2:?repo required}
PROJECT=${3:?project folder required}
BRANCH=${4:-master}
OUTPUT_DIR=${5:-.}

TREE_URL="https://api.github.com/repos/$OWNER/$REPO/git/trees/${BRANCH}?recursive=1"
GITHUB_BASE="https://raw.githubusercontent.com/$OWNER/$REPO/$BRANCH"
LIST_FILE=/tmp/a.$$

if [ "$OUTPUT_DIR" != "." ] ; then mkdir -p "$OUTPUT_DIR" ; fi

curl -fsSL "$TREE_URL" |
jq -r --arg prefix "$PROJECT/" '
    .tree[]
    | select(.type == "blob")
    | select(.path | startswith($prefix))
    | .path
    ' | tr -d '\r' | tee $LIST_FILE /dev/stderr >/dev/null
while IFS= read -r SOURCE; do
    SOURCE=${SOURCE%$'\r'}

    TARGET="$OUTPUT_DIR/$SOURCE"
    URL="$GITHUB_BASE/$SOURCE"

    mkdir -p "$(dirname "$TARGET")"

    echo >&2 Restoring: $SOURCE
    echo >&2 From: $URL
    echo >&2 curl -fsSL "$URL" -o "$TARGET"

    curl -fsSL "$URL" -o "$TARGET"
done < $LIST_FILE
rm -f $LIST_FILE
exit 0
