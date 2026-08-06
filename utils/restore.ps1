#Copyright (c) 2020,2021,2025,2026 Serguei Kouzmine
#
#Permission is hereby granted, free of charge, to any person obtaining a copy
#of this software and associated documentation files (the "Software"), to deal
#in the Software without restriction, including without limitation the rights
#to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
#copies of the Software, and to permit persons to whom the Software is
#furnished to do so, subject to the following conditions:
#
#The above copyright notice and this permission notice shall be included in
#all copies or substantial portions of the Software.
#
#THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
#IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
#FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
#AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
#LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
#OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
#THE SOFTWARE.

# surprisingly, the PowerShell version becomes lighter, not heavier
# usage $0 sergueik springboot_study basic-static
[CmdletBinding(DefaultParameterSetName = 'url')]

param(
  [Parameter(Mandatory = $true, Position=0, ParameterSetName = 'url')]
  [string]$url,
  [Parameter(Mandatory = $true, Position=0, ParameterSetName = 'detailed')]
  [string]$owner,
  [Parameter(Mandatory = $true, Position=1, ParameterSetName = 'detailed')]
  [string]$repo,
  [Parameter(Mandatory = $true, Position=2, ParameterSetName = 'detailed')]
  [string]$project,
  [Parameter(Mandatory = $false, Position=3,ParameterSetName = 'detailed')]
  [string]$branch = 'master',
  [string]$output_dir = '.'
)

# NOTE:
# Parameter binding happens before any script code executes.
# If PowerShell selects the wrong parameter set, execution never reaches
# this point. The typical symptom is:
#
#   A positional parameter cannot be found that accepts argument '<argument 2>'.
try {
  # idiomatic Powershell discourages strict type declaration and in certain scenarios it is impossible tecnically:
  # Microsoft.PowerShell.Commands.MatchInfo is the runtime type returned by Select-String, but it cannot
  # be referenced as a type literal:
  #
  #   [Microsoft.PowerShell.Commands.MatchInfo]$m = $null
  #
  # results in:
  #
  #   Unable to find type
  #
  # Therefore PowerShell code typically leaves this variable untyped (or
  # uses [object]), relying on type inference.  [Microsoft.PowerShell.Commands.MatchInfo]$m = $null
} catch [System.Management.Automation.RuntimeException] {
  # Unable to find type [Microsoft.PowerShell.Commands.MatchInfo].
  write-host ('Exception (ignored): {0} {1}' -f $_.Exception.GetType().FullName, $_.Exception.Message)
}
switch ($PSCmdlet.ParameterSetName) {
    'Url' {
	    if ($url -match '^https://github\.com/' ){
              [String]$p = 'github\.com/([^/]+)/([^/]+)/tree/([^/]+)/(.*)$'
              $m = select-string -pattern $p -InputObject $url
              if (($m -ne $null ) -and ($m.matches -ne $null)) {
                try {
                  $g = $m.Matches.Groups
                  $owner = $g.Item(1).Value
                  $repo = $g.Item(2).Value
                  $branch = $g.Item(3).Value
                  $project = $g.Item(4).Value
                } catch [System.Management.Automation.RuntimeException] {
  	          write-host ('Exception (ignored): {0} {1}' -f $_.Exception.GetType().FullName, $_.Exception.Message)
               }
             }
           } else {
             write-error ('invalid argument: {0}' -f $url ) 
	     exit 1
           }
         }
    'detailed' { }
}

$tree_url = "https://api.github.com/repos/$owner/$repo/git/trees/${branch}?recursive=1"
<#
PowerShell token boundary ambiguity in interpolated strings

Always use ${var} when immediately followed by ?, /, :, -, or path separators

#>

$github_base = "https://raw.githubusercontent.com/$owner/$repo/$branch"
$listFile = Join-Path $env:TEMP "github_restore_$PID.txt"
write-host $tree_url
if ($output_dir -ne ".") {
  new-item -ItemType Directory -Force -Path $output_dir | Out-Null
}

$response = Invoke-RestMethod -Uri $tree_url

$files = $response.tree |
  where-object {
    $_.type -eq 'blob' -and $_.path.StartsWith("${project}/")
  } |
  select-object -ExpandProperty path
# Powershell does not have a built-in 'tee to stderr', but is straightforward to implement one
$files | ForEach-Object {
  $_ | Out-File -FilePath $listFile
  [Console]::Error.WriteLine($_)
}
<#
function Tee-ToError {
  param([string]$Path)

  process {
    $_ | Out-File $Path -Append
    [Console]::Error.WriteLine($_)
    $_
  }
}
#>
$ProgressPreference = 'SilentlyContinue'
# suppresss download progress UI (novelty CONSOLE progress bar / noise)
$files | foreach-object {
  $source = $_
  $target = join-path $output_dir $source
  $parent = split-path $target -Parent

  new-item -ItemType Directory -Force -Path $parent | Out-Null

  $url = "$github_base/$source"

  write-host ('Restoring: {0}' -f $source )
  write-host ('From: {0}' -f $url )

  invoke-webrequest -Uri $url -OutFile $target
}
# don't worry if one finds oneself
# continuously discovering boundary conditions between incompatible execution models

<#
#  early version of the tool has interface that models the *implementation*:
```
tool owner repo project [branch] [output_dir]
```
# The new interface should model the *user intent*:
```
tool <github-tree-url> [output_dir]
```
#>
