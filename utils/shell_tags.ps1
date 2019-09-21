# based on: https://www.reddit.com/r/PowerShell/comments/4bybjd/renaming_mp3_files_based_on_customextended/
# http://wiki.hydrogenaud.io/index.php?title=PowerShell_Audio
# https://devblogs.microsoft.com/scripting/list-music-file-metadata-in-a-csv-and-open-in-excel-with-powershell/
# dump everything shell knows
# https://blogs.msdn.microsoft.com/johan/2008/09/23/powershell-automatically-organizing-your-mp3-collection/
# optimized to only show track info:
# http://www.edugeek.net/forums/scripts/144131-read-artist-name-album-create-folder-move.html


$objShell = new-object -ComObject Shell.Application

function getFullMetaData {
  param (
    $filePath
  )
	$filename = split-PATH $filePath -leaf
	$dirname = split-PATH $filePath
	$objFolder = $objShell.namespace($dirname)
	$objFile = $objFolder.parsename($filename)
	$result = @{}
        # the original code does not appear to be optimized with respect to the shell calls
	0..266 | foreach-object {
    $index = $_
		if ($objFolder.getDetailsOf($objFile, $index)) {
			$result[$($objFolder.getDetailsOf($objFolder.items, $index))] = ('{0} {1}' -f $index, $objFolder.getDetailsOf($objFile, $index))
		}
	}
	return $result
}
getFullMetaData -filePath "C:\Users\Serguei\Music\!Deladap\01 - Zsa Manca - Deladap.mp3"

function getMP3MetaData {
  param (
    $filePath
  )
  $hash = @{
    'Artists' = 13;
    'Title' = 21;
    '#'     = 26;
    #    'Bitrate' = 28;
    #    'Album' = 14;
    #    'Year' = 15;
    #    'Genre' = 16;
    #    'Length' = 27;
  }
	$filename = split-PATH $filePath -leaf
	$dirname = split-PATH $filePath
	$objFolder = $objShell.namespace($dirname)
	$objFile = $objFolder.parsename($filename)
	$result = @{}

  $hash.Keys |
   ForEach-Object {
    $property = $_
    $value = $objFolder.GetDetailsOf($objFile, $hash.$property)
		if ($value) {
			$result[$property] = $value
		}
	}
	return $result
}

$filePath = "C:\Users\Serguei\Music\!Deladap\01 - Zsa Manca - Deladap.mp3"
$filedata = getMP3MetaData -filePath $filePath
$newName =  ('{0:00} - {1} - {2}.mp3' -f (0 + $filedata['#']), $filedata['Title'], $filedata['Artists'])
write-output $newName
# rename-item -Path $filePath -NewName $newName
<#
# alternatively, use mpv
# still need to suppress the silent playback and configure to display more tags
PATH=%PATH%;"c:\Program Files\SMPlayer\mpv"
mpv.exe --display-tags --no-audio --no-video "C:\Users\Serguei\Music\!Deladap\01 - Zsa Manca - Deladap.mp3"
Playing: C:\Users\Serguei\Music\!Deladap\01 - Zsa Manca - Deladap.mp3
[ffmpeg/demuxer] mp3: Estimating duration from bitrate, this may be inaccurate
     Video --vid=1 [P] (png 900x806)
 (+) Audio --aid=1 (mp3 2ch 44100Hz)
AO: [wasapi] 48000Hz stereo 2ch float
^C 00:00:17 / 00:04:56 (5%)
# NOTE: 
# C:\Users\Serguei>"path=%path%;c:\Program Files\SMPlayer\mpv"
# The input line is too long.

#>
