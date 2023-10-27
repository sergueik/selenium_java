# origin: https://stackoverflow.com/questions/42592518/encode-decode-exe-into-base64
#
param (
  [string] $inputfile,
  [string] $outputfile,
  [switch] $decode,
  [switch] $help
)
if ([bool]$psboundparameters['help'].ispresent) {
  write-host @'
  Usage: uuencode.ps1 [-decode] -inputfile [INPUTFILE] -outputfile [OUTPUTFILE]
'@  
  exit 0
}
if ([bool]$psboundparameters['decode'].ispresent) {
  $o = get-content -encoding ascii -raw -path $inputfile
  [IO.File]::WriteAllBytes($outputfile, [Convert]::FromBase64String($o))
} else  {
  $o = [Convert]::ToBase64String([IO.File]::ReadAllBytes($inputfile))
  out-file -encoding ascii -filepath $outputfile -inputobject $o
}
