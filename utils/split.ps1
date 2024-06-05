# based on: https://stackoverflow.com/questions/1001776/how-can-i-split-a-text-file-using-powershell
param(
  [string]$filename = "test.txt",
  [string]$rootName = "result",
  [string]$ext = ".txt",
  [int]$maxlines = 10000, #10k
  [switch]$debug
)
function spliti_file {
  param(
    [string]$filename = "test.txt",
    [string]$rootName = "result",
    [string]$ext = ".txt",
    [int]$maxlines = 10000, #10k
    [boolean]$debug
  )
  $filecount = 1
  $reader = $null
  try{
    $reader = [io.file]::OpenText($filename)
    try{
      write-host ('Creating file number {0}' -f $filecount)
      $writer = [io.file]::CreateText(('{0}{1}.{2}' -f $rootName,$filecount.ToString("000"),$ext))
      $filecount++
      $linecount = 0
  
      while($reader.EndOfStream -ne $true) {
        write-host ('Reading {0}' -f $maxlines )
        while( ($linecount -lt $maxlines) -and ($reader.EndOfStream -ne $true)){
          $writer.WriteLine($reader.ReadLine());
          $linecount++
        }
  
        if($reader.EndOfStream -ne $true) {
          write-hosr 'Closing file'
          $writer.Dispose();
  
          write-host ('Creating file number {0}' -f $filecount)
          $writer = [io.file]::CreateText(('{0}{1}.{2}' -f $rootName,$filecount.ToString("000"),$ext))
          $filecount++
          $linecount = 0
        }
      }
    } finally {
      $writer.Dispose();
    }
  } finally {
    $reader.Dispose();
  }
  
}
#split test
# $sw = new-object System.Diagnostics.Stopwatch
# $sw.Start()
split_file -rootname $rootname -filename $filename -maxlines $maxlines
# $sw.Stop()
# Write-Host "Split complete in " $sw.Elapsed.TotalSeconds "seconds"
