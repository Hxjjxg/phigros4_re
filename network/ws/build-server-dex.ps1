<#
.SYNOPSIS
    Compile PhigrosWSServer into a dex for Frida to inject.

.DESCRIPTION
    Output PhigrosWSServer.dex is loaded by phigros_ws_server.js via
    Java.openClassFile().

    Compilation does NOT link against the real classes from the APK.
    server\stubs\ holds a set of compile-time stubs whose signatures were
    each verified against the classes.dex bytecode. The stubs exist only to
    satisfy javac and are NOT packed into the dex -- the real base classes
    come from the APK at runtime.

    NOTE: this file is deliberately ASCII-only. Windows PowerShell 5.1 reads
    BOM-less .ps1 as ANSI and mangles non-ASCII, which breaks parsing.

.EXAMPLE
    .\build-server-dex.ps1
    .\build-server-dex.ps1 -AndroidJar "C:\...\android-32\android.jar"
#>

[CmdletBinding()]
param(
    [string] $AndroidJar = "$env:LOCALAPPDATA\Android\Sdk\platforms\android-36\android.jar",
    [string] $BuildTools = "$env:LOCALAPPDATA\Android\Sdk\build-tools\36.1.0",
    [int]    $MinApi     = 26
)

$ErrorActionPreference = 'Stop'
$here  = $PSScriptRoot
$build = Join-Path $here 'build'
$src   = Join-Path $here 'server'

function Say  ($m) { Write-Host "  $m" }
function Head ($m) { Write-Host "`n=== $m ===" -ForegroundColor Cyan }

$javacCmd = Get-Command javac -ErrorAction SilentlyContinue
if (-not $javacCmd) { throw 'javac not found in PATH. Need a JDK (not a JRE).' }
$javac = $javacCmd.Source

$d8 = Join-Path $BuildTools 'd8.bat'
if (-not (Test-Path $d8)) { throw "d8 not found: $d8`n    Point -BuildTools at your Android SDK build-tools dir." }

if (-not (Test-Path $AndroidJar)) { throw "android.jar not found: $AndroidJar`n    Pass -AndroidJar." }

Say "javac      : $javac"
Say "d8         : $d8"
Say "android.jar: $AndroidJar"

# ---- clean --------------------------------------------------------
if (Test-Path $build) { Remove-Item $build -Recurse -Force }
$classesDir = Join-Path $build 'classes'
$dexDir     = Join-Path $build 'dex'
New-Item -ItemType Directory -Force $classesDir | Out-Null
New-Item -ItemType Directory -Force $dexDir     | Out-Null

# ---- 1. javac: real subclass + compile-time stubs ------------------
Head '1/2  javac'

$sources = @(Get-ChildItem $src -Recurse -Filter *.java | ForEach-Object { $_.FullName })
Say "source files: $($sources.Count)"

& javac -nowarn -encoding UTF-8 -d $classesDir @sources 2>&1 | ForEach-Object { Say $_ }
if ($LASTEXITCODE -ne 0) { throw 'javac failed' }

$sub = Join-Path $classesDir 're\phigros'
if (-not (Test-Path $sub)) { throw "no output at $sub" }
Say "produced $((Get-ChildItem $sub -Filter *.class).Count) class files under re/phigros"

# ---- 2. d8: pack ONLY the real subclass, never the stubs -----------
Head '2/2  d8'

$subClasses = @(Get-ChildItem $sub -Recurse -Filter *.class | ForEach-Object { $_.FullName })
Say "packing re/phigros/*.class only; stubs excluded"

& $d8 --lib $AndroidJar --min-api $MinApi --output $dexDir @subClasses 2>&1 | ForEach-Object { Say $_ }
if ($LASTEXITCODE -ne 0) { throw 'd8 failed' }

$out = Join-Path $here 'PhigrosWSServer.dex'
Copy-Item (Join-Path $dexDir 'classes.dex') $out -Force

# ---- done ----------------------------------------------------------
Head 'done'
Say ("{0}   {1:n1} KB" -f $out, ((Get-Item $out).Length / 1KB))
Say ''
Say 'Usage:'
Say '  adb push PhigrosWSServer.dex /data/local/tmp/'
Say '  adb shell chmod 644 /data/local/tmp/PhigrosWSServer.dex'
Say '  adb forward tcp:8767 tcp:8767'
Say '  F:\frida16\Scripts\frida.exe -H 127.0.0.1:27042 -n Gadget -l phigros_ws_server.js'
Say '  then connect from this machine to ws://127.0.0.1:8767'
