#!/usr/bin/env bash

set -euo pipefail

usage() {
    cat <<EOF
Usage:
  $0 -r owner/repository -s skill-name [-p skill-path]

Options:
  -r, --repo      GitHub repository (owner/name)
  -s, --skill     Skill directory name to find
  -p, --skill-path  Path containing skills (default: skills)
  -h, --help      Show this help

Example:
  $0 -r sickn33/agentic-awesome-skills -s java-pro
This outputs:
https://github.com/sickn33/agentic-awesome-skills/tree/main/skills/java-pro
EOF
}

REPO=''
SKILL=''
SKILL_PATH='skills'

while getopts "r:s:p:h" opt; do
    case "$opt" in
        r) REPO="$OPTARG" ;;
        s) SKILL="$OPTARG" ;;
        p) SKILL_PATH="$OPTARG" ;;
        h) usage; exit 0 ;;
        *) echo "unrecognized option: \"$opt\""; usage >&2; exit 1 ;;
    esac
done


if [[ -z "$REPO" || -z "$SKILL" ]]; then
    usage >&2
    exit 1
fi
curl -sf \
  "https://api.github.com/repos/$REPO/contents/$SKILL_PATH" \
  | jq -r --arg skill "$SKILL" '
    .[]?
    | select(.name | contains($skill))
    | .html_url
'
