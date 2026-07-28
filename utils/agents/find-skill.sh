#!/bin/bash

repo="$1"
skill="$2"

if [ -z "$repo" ] || [ -z "$skill" ]; then
    echo "Usage: $0 owner/repo skill-name"
    exit 1
fi

curl -sf \
  "https://api.github.com/repos/$repo/contents/skills" \
| jq -r --arg skill "$skill" \
  '.[]?.name | select(. == $skill)'
