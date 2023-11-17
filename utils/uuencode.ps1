# origin: https://stackoverflow.com/questions/42592518/encode-decode-exe-into-base64
param (
  [string] $inputfile,
  [string] $outputfile,
  [int]$length = 1000,
  [switch] $decode,
  [switch] $split,
  [switch] $join,
  [switch] $help
)
if ([bool]$psboundparameters['help'].ispresent) {
  write-host @'
  Usage: uuencode.ps1 [-decode] -inputfile [INPUTFILE] -outputfile [OUTPUTFILE] [-split] [-join] [-length LENGTH]
  The [INPUTFILE] and [OUTPUTFILE] are read and created in the current directory
'@  
  exit 0
}
# Powershell found to create files under $env:USERPROFILE unless full path is specified
$outputfilepath = (( resolve-path -path '.').path + '\' + $outputfile )
[bool]$split_flag = [bool]$psboundparameters['split'].ispresent 
[bool]$join_flag = [bool]$psboundparameters['join'].ispresent 
[bool]$decode_flag = [bool]$psboundparameters['decode'].ispresent 
if ($decode_flag) {
  $o = get-content -encoding ascii -raw -path $inputfile 
  if ($join_flag) {
    $o = $o -replace '\r?\n', ''
  }
  [IO.File]::WriteAllBytes($outputfilepath, [Convert]::FromBase64String($o))
} else  {
  $o = [Convert]::ToBase64String([IO.File]::ReadAllBytes($inputfile))
  if ($split_flag) {
    # https://stackoverflow.com/questions/1450774/splitting-a-string-into-chunks-of-a-certain-size
    add-type -typeDefinition @'
    using System;
    using System.Collections;
    using System.Collections.Generic;
public static class EnumerableEx {
    public static IEnumerable<String> SplitBy(this string str, int chunkLength) {
        if (String.IsNullOrEmpty(str)) throw new ArgumentException();
        if (chunkLength < 1) throw new ArgumentException();

        for (int i = 0; i < str.Length; i += chunkLength)
        {
            if (chunkLength + i > str.Length)
                chunkLength = str.Length - i;

            yield return str.Substring(i, chunkLength);
        }
    }
}
'@
    # NOTE: cannot use the extension methods defined in inline Classes in Powershell:
    # Method invocation failed because [System.String] does not contain a method named 'SplitBy'.
    # $o.SplitBy($length) | foreach-object {  write-output $_ }

    out-file -encoding ascii -filepath $outputfilepath -inputobject ''
    [EnumerableEx]::SplitBy($o, $length) | foreach-object {
      $line = $_
      out-file -encoding ascii -filepath $outputfilepath -inputobject $line -append }
  } else {
    out-file -encoding ascii -filepath $outputfilepath -inputobject $o
  }
}
