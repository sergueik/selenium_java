# origin: https://stackoverflow.com/questions/42592518/encode-decode-exe-into-base64
param (
  [string] $inputfile,
  [string] $outputfile,
  [int]$length = 1000,
  [switch] $decode,
  [switch] $split,
  [switch] $useutil,
  [switch] $join,
  [switch] $help
)
if ([bool]$psboundparameters['help'].ispresent) {
  write-host @'
  Usage: uuencode.ps1 [-decode] -inputfile [INPUTFILE] -outputfile [OUTPUTFILE] [-split] [-join] [-length LENGTH] [-useutil]
  The [INPUTFILE] and [OUTPUTFILE] are read and created in the current directory
'@  
  exit 0
}
# Powershell found prone to create files under $env:USERPROFILE when specified path is relative
$outputfilepath = (( resolve-path -path '.').path + '\' + $outputfile )
[bool]$split_flag = [bool]$psboundparameters['split'].ispresent 
[bool]$join_flag = [bool]$psboundparameters['join'].ispresent 
[bool]$decode_flag = [bool]$psboundparameters['decode'].ispresent 
[bool]$useutil_flag = [bool]$psboundparameters['useutil'].ispresent 
if ($decode_flag) {
  if ($useutil_flag) {
    if (test-path -path  $outputfilepath ) {
       remove-item -path  $outputfilepath -erroraction silentlycontinue -force
       # prevent
       # EncodeToFile returned The file exists. 0x80070050 (WIN32: 80 ERROR_FILE_EXISTS)
    }
     # cmd %% - /c "echo C:\Windows\System32\certutil.exe -decode ""${inputfile}"" ""${outputfile}"" >nul 2>&1"
     cmd %% - /c "C:\Windows\System32\certutil.exe -decode ""${inputfile}"" ""${outputfile}"" >nul 2>nul"
     # NOTE: without the quotes, will get an error
     # Redirection to 'nul' failed: FileStream will not open Win32 devices such as disk partitions and tape drives. Avoid use of "\\.\" in the path. 
  } else {
    $o = get-content -encoding ascii -raw -path $inputfile 
    if ($join_flag) {
      $o = $o -replace '\r?\n', ''
    }
    [IO.File]::WriteAllBytes($outputfilepath, [Convert]::FromBase64String($o))
  }
} else  {
  # see also: https://raw.githubusercontent.com/Empyreal96/win-10-services-toolbox/master/Windows-Toolkit.cmd
  if ($useutil_flag) {
    if (test-path -path  $outputfilepath ) {
       remove-item -path  $outputfilepath -erroraction silentlycontinue -force
    }
     write-host ("C:\Windows\System32\certutil.exe -encode ""${inputfile}"" ""${outputfile}"" >nul 2>nul")
     cmd %% - /c "C:\Windows\System32\certutil.exe -encode ""${inputfile}"" ""${outputfile}"" >nul 2>nul"
     # NOTE: certutil.exe will add
     # -----BEGIN CERTIFICATE-----
     # -----END CERTIFICATE-----
     # around base64 data
     # Redirection to 'nul' failed: FileStream will not open Win32 devices such as disk partitions and tape drives. Avoid use of "\\.\" in the path. 
  } else {
    $o = [Convert]::ToBase64String([IO.File]::ReadAllBytes((( resolve-path -path '.' ).path + '\' + $inputfile)))
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
}
