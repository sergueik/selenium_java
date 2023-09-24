param(
    [string] $collection = '.',
    [string] $image = 'folder.png',
    [switch]$debug
  )
# based on: https://stuart-moore.com/using-powershell-add-images-mp3-files/
# see also: https://github.com/yevster/AlbumArtist/tree/master/src/AlbumArtist
# NOTE: inherited from powershell_selenium	
[string]$shared_assemblies_path = 'c:\java\selenium\csharp\sharedassemblies'
# Flat directory seems a better choice for Selenium dlls
# than a subfolder-heavy C:\Windows\System32\WindowsPowerShell\v1.0\Modules
# or ${env:LOCALAPPDATA}\Microsoft\Windows\PowerShell
# the ${env:USERPROFILE}\Downloads is popular alternative
[string[]]$shared_assemblies = @(
      'taglibsharp.dll',
      'WebDriver.Support.dll',
      'nunit.core.dll',
      'Newtonsoft.Json.dll',
      'nunit.framework.dll'
    )

# SHARED_ASSEMBLIES_PATH environment overrides parameter, for Team City/Jenkinks
if (($env:SHARED_ASSEMBLIES_PATH -ne $null) -and ($env:SHARED_ASSEMBLIES_PATH -ne '')) {
  $shared_assemblies_path = $env:SHARED_ASSEMBLIES_PATH
}

# write-debug "load_shared_assemblies -shared_assemblies_path ${shared_assemblies_path} -shared_assemblies ${shared_assemblies}"
  # start-sleep -milliseconds 1000
pushd $shared_assemblies_path

  $shared_assemblies | ForEach-Object {
    $filepath = $_
    if ($host.Version.Major -gt 2) {
      Unblock-File -Path $filepath
    }
    write-debug $_
    [Reflection.Assembly]::LoadFrom( (Resolve-Path $filepath) )
    Add-Type -Path $filepath
  }
  popd
Add-Type -TypeDefinition @"

using System;
using TagLib;
            
             public class ExceptionTest
             {
             	public static void Test (String filepath)
             	{
             		try {
             			File file = File.Create (filepath);
             		} catch (CorruptFileException e) {
             			Console.WriteLine ("That file is corrupt: {0}", e.ToString ());
             		} catch (Exception e) {
             			Console.WriteLine ("Exception: {0}", e.ToString ());
             		}
            	}
             }

"@ -ReferencedAssemblies 'System.dll', ($shared_assemblies_path +'\'+ 'taglibsharp.dll')
foreach ($file in get-childitem -path $collection -filter *.mp3 -recurse){
    # [ExceptionTest]::Test($file.FullName)
    $mp = [TagLib.File]::Create($file.FullName.ToString())
    # With TagLibSharp 2.3.0 observing
    # Exception calling "Create" with "1" argument(s): "Method not found: 'System.String System.String.Format(System.IFormatProvider, System.String, System.Object, System.Object)'."
    if (test-path ($file.Directoryname + '\' + $image)){
      $pic = [taglib.picture]::createfrompath($file.Directoryname + '\' + $image )
    }

    $mp.tag.Pictures = $pic
    $mp.save()
    write-output ($file.BaseName + '.' + $file.Extension + ' updated' )
}

