[CmdletBinding()]
param(
    [string] $AndroidJar = "$env:LOCALAPPDATA\Android\Sdk\platforms\android-36\android.jar",
    [string] $BuildTools = "$env:LOCALAPPDATA\Android\Sdk\build-tools\36.1.0"
)
$ErrorActionPreference = 'Stop'
$buildDir = Join-Path $PSScriptRoot ('build\' + [Guid]::NewGuid().ToString('N'))
$classesDir = Join-Path $buildDir 'classes'
$dexDir = Join-Path $buildDir 'dex'
New-Item -ItemType Directory -Force $classesDir, $dexDir | Out-Null
$sources = @(Get-ChildItem (Join-Path $PSScriptRoot 'android') -Recurse -Filter *.java | ForEach-Object FullName)
$sources += @(Get-ChildItem (Join-Path $PSScriptRoot '..\ws\server\stubs\com') -Recurse -Filter *.java | ForEach-Object FullName)
& javac -encoding UTF-8 -classpath $AndroidJar -d $classesDir @sources
if ($LASTEXITCODE -ne 0) { throw 'javac failed' }
$realClasses = @(Get-ChildItem (Join-Path $classesDir 're') -Recurse -Filter *.class | ForEach-Object FullName)
& (Join-Path $BuildTools 'd8.bat') --lib $AndroidJar --min-api 26 --output $dexDir @realClasses
if ($LASTEXITCODE -ne 0) { throw 'd8 failed' }
Copy-Item -LiteralPath (Join-Path $dexDir 'classes.dex') -Destination (Join-Path $PSScriptRoot 'EmbeddedTransport.dex') -Force
Write-Host 'Built EmbeddedTransport.dex (compile-time stubs excluded).'
