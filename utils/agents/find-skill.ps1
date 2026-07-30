param(
  [string]$repo,
  [string]$skill,
  [string]$skill_path = 'skills',
  [switch]$Help,
  [switch]$exact,
  # NOTE: case-sensitive -  overlaps with prdefined vars
  [switch]$Debug
)

function Show-Help {
@"
Usage:
  ./find-skill.ps1 -Repo owner/repository -Skill skill-name [-Skill_Path path]

Options:
  -Repo        GitHub repository (owner/name)
  -Skill       Skill directory name/pattern to find
  -Skill_Path  Path containing skills (default: skills)
  -Help        Show this help
  -Exact       Require exact skill name match

Examples:

  ./find-skill.ps1 `
    -Repo sickn33/agentic-awesome-skills `
    -Skill java-pro

  ./find-skill.ps1 `
    -Repo JetBrains/skills `
    -Skill_Path . `
    -Skill spring


. .\find-skill.ps1 -skill_path '.' -repo JetBrains/skills -skill spring -debug
Repository: JetBrains/skills
Skill:      spring
Skill Path: .
API URL:    https://api.github.com/repos/JetBrains/skills/contents/.
Calling GitHub API...
Received 133 entries
Filtering to contain spring
https://github.com/JetBrains/skills/tree/main/jpa-spring-data-kotlin-mapper
https://github.com/JetBrains/skills/tree/main/kotlin-idiomatic-refactorer-spring-aware
https://github.com/JetBrains/skills/tree/main/kotlin-spring-proxy-compatibility
https://github.com/JetBrains/skills/tree/main/spring-context-di-reasoning
https://github.com/JetBrains/skills/tree/main/spring-kotlin-code-review
https://github.com/JetBrains/skills/tree/main/spring-mvc-webflux-api-builder
https://github.com/JetBrains/skills/tree/main/spring-security-configurator-auditor
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

$debug_flag = [bool]$PSBoundParameters['debug'].IsPresent

$url = (
  'https://api.github.com/repos/{0}/contents/{1}' -f
  $repo,
  $skill_path
)

if ($debug_flag) {
  write-host "Repository: $repo"
  write-host "Skill:      $skill"
  write-host "Skill Path: $skill_path"
  write-host "API URL:    $url"
}

try {

  if ($debug_flag) {
    write-host 'Calling GitHub API...'
  }

  $dirs = Invoke-RestMethod -Uri $url

  if ($debug_flag) {
    write-host ('Received {0} entries' -f $dirs.Count)
  }

  if ($exact.IsPresent) {
    if ($debug_flag) {
	  write-host ('Filtering to exact "{0}"' -f $skill )
    }

    $found = $dirs | where-object { $_.name -eq $skill }
  }
  else {
    if ($debug_flag) {
	  write-host ('Filtering to contain {0}' -f $skill )
    }
    $found = $dirs | where-object { $_.name -like "*$skill*" }
  }

  if ($null -eq $found) {
    if ($debug_flag) {
      write-host ('Skill "{0}" not found' -f $skill)
    }
    exit 2
  }

  $found |
    select-object -expandproperty html_url

}
catch {
  write-error $_.Exception.Message
  exit 3
}