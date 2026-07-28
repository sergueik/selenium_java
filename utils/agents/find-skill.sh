#!/usr/bin/env bash

set -euo pipefail

usage() {
    cat <<EOF
Usage:
  $0 -r owner/repository -s skill-name

Options:
  -r, --repo      GitHub repository (owner/name)
  -s, --skill     Skill directory name to find
  -h, --help      Show this help

Example:
  $0 -r sickn33/agentic-awesome-skills -s java-pro
This outputs:
https://github.com/sickn33/agentic-awesome-skills/tree/main/skills/java-pro
EOF
}

repo=""
skill=""

while [[ $# -gt 0 ]]; do
    case "$1" in
        -r|--repo)
            repo="$2"
            shift 2
            ;;
        -s|--skill)
            skill="$2"
            shift 2
            ;;
        -h|--help)
            usage
            exit 0
            ;;
        *)
            echo "Unknown option: $1" >&2
            usage >&2
            exit 1
            ;;
    esac
done

if [[ -z "$repo" || -z "$skill" ]]; then
    usage >&2
    exit 1
fi

curl -sf \
  "https://api.github.com/repos/$repo/contents/skills" \
  | jq -r --arg skill "$skill" '
    .[]?
    | select(.name == $skill)
    | .html_url
'
