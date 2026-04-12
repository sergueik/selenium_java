#Copyright (c) 2020,2021,2025 Serguei Kouzmine
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
param(
  [Parameter(Mandatory = $true)]
  [string]$owner,
  [Parameter(Mandatory = $true)]
  [string]$repo,
  [Parameter(Mandatory = $true)]
  [string]$project,
  [string]$branch = 'master',
  [string]$output_dir = '.'
)

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
