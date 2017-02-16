[CmdletBinding()]
Param (
  [Parameter(Mandatory=$false,Position=0)]
  [string]$MAIN_APP_CLASS = 'SimpleToolBarEx'
)

$MAVEN_VERSION = '3.3.9'
$JAVA_VERSION = '1.8.0_101'
$env:JAVA_HOME = "c:\java\jdk${JAVA_VERSION}"
$env:M2_HOME = "c:\java\apache-maven-${MAVEN_VERSION}"
$env:M2 = "${env:M2_HOME}\bin"

$env:PATH = "${env:JAVA_HOME}\bin;${env:M2};${env:PATH}"
$env:JAVA_OPTS = $env:MAVEN_OPTS = @('-Xms256m','-Xmx512m')

$PROJECT_VERSION = '0.0.3-SNAPSHOT'
$MAIN_APP_PACKAGE = 'com.mycompany.app'


# external dependencies
$DEPENDENCIES = @{ 'opal' = '1.0.4'; }

$DEPENDENCIES.Keys | ForEach-Object {
  $ALIAS = $_;
  $JARFILE_VERSION = $DEPENDENCIES[$_];
  $JARFILE = "${ALIAS}-${JARFILE_VERSION}.jar"

  $JARFILE_LOCALPATH = (Resolve-Path '.\src\main\resources').path + '\' + $JARFILE
  if (-not (Test-Path -Path $JARFILE_LOCALPATH)) {

    $URI = "https://github.com/lcaron/opal/blob/releases/V${JARFILE_VERSION}/opal-${JARFILE_VERSION}.jar?raw=true"
    $request = Invoke-WebRequest -uri $URI -MaximumRedirection 0 -ErrorAction ignore
    if ($request.StatusDescription -eq 'found') {
      $uri = $request.Headers.Location
      Write-Output ('downloading from {0}' -f $uri)
    }
    write-output "Downloading ${JARFILE_LOCALPATH}"
    Invoke-WebRequest -uri $URI -OutFile $JARFILE_LOCALPATH
  }
}

# Compile
& 'mvn.cmd' 'package' 'install'

# Run
& 'java.exe' `
   '-cp' "target\myswt-${PROJECT_VERSION}.jar;target\lib\*" `
   "${MAIN_APP_PACKAGE}.${MAIN_APP_CLASS}"
