param(
    # [Parameter(Mandatory=$false)]
    [string]$Repo,

    # [Parameter(Mandatory=$false)]
    [string]$Skill,

    [switch]$Help,

    [switch]$debug
)

function Show-Help {
    @"
Usage:
  ./find-skill.ps1 -Repo owner/repository -Skill skill-name

Options:
  -Repo        GitHub repository (owner/name)
  -Skill       Skill directory name to find
  -Help        Show this help
  -Debug       Show diagnostic information

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

if ([string]::IsNullOrEmpty($Repo) -or
    [string]::IsNullOrEmpty($Skill)) {
    Show-Help
    exit 1
}

if ($PSBoundParameters['Debug'].IsPresent) {
    Write-Host "Repository: $Repo"
    Write-Host "Skill:      $Skill"
    Write-Host "API URL:    https://api.github.com/repos/$Repo/contents/skills"
}

$url = "https://api.github.com/repos/$Repo/contents/skills"

try {
    if ($PSBoundParameters['Debug'].IsPresent) {
        Write-Host "Calling GitHub API..."
    }

    $skills = Invoke-RestMethod -Uri $url

    if ($PSBoundParameters['Debug'].IsPresent) {
        Write-Host "Received $($skills.Count) entries"
    }

    $found = $skills |
        Where-Object { $_.name -eq $Skill }

    if ($null -eq $found) {
        if ($PSBoundParameters['Debug'].IsPresent) {
            Write-Host "Skill not found"
        }
        exit 2
    }

    $found |
        Select-Object -ExpandProperty html_url

}
catch {
    Write-Error $_.Exception.Message
    exit 3
}
