param(
  # [Parameter(Mandatory=$false)]
  [string]$repo,

  # [Parameter(Mandatory=$false)]
  [string]$skill,

  [switch]$Help,

  [switch]$exact,
  [switch]$debug
)

function Show-Help {
  @"
Usage:
  ./find-skill.ps1 -Repo owner/repository -Skill skill-name

Options:
  -Repo    GitHub repository (owner/name)
  -Skill     Skill directory name to find
  -Help    Show this help
  -Debug     Show diagnostic information

Example:
  ./find-skill.ps1 `
    -Repo sickn33/agentic-awesome-skills `
    -Skill java-pro

"@
}

if ($Help.IsPresent) {
  Show-Help
  exit 0
}

if ([string]::IsNullOrEmpty($repo) -or
  [string]::IsNullOrEmpty($skill)) {
  Show-Help
  exit 1
}

[bool]$debug_flag = [bool]$psboundparameters['debug'].ispresent
if ($debug_flag) {
  write-host "Repository: $repo"
  write-host "Skill:    $skill"
  write-host "API URL:  https://api.github.com/repos/$repo/contents/skills"
}

$url = ( 'https://api.github.com/repos/{0}/contents/skills' -f $repo)

try {
  if ($debug_flag) {
    write-host 'Calling GitHub API...'
  }

  $dirs = Invoke-RestMethod -Uri $url

  if ($debug_flag) {
    write-host ('Received {0} entries' -f $dirs.Count )
  }

  # $found = $dirs |
  # where-object { $_.name -match "^${Skill}" }
  if ($exact) {
    $found = $dirs | where-object { $_.name -eq $skill }
  }
  else {
    $found = $dirs | where-object { $_.name -like "*${skill}*" }
  }
  if ($null -eq $found) {
    if ($debug_flag) {
      write-host ('Skill "{0}" not found' -f $skill )
    }
    exit 2
  }

  $found |
    select-object -ExpandProperty html_url

}
catch {
  write-error $_.Exception.Message
  exit 3
}
