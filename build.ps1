<#
.SYNOPSIS
    从 APK 重新生成全部分析产物。

.DESCRIPTION
    本仓库把 APK、工具二进制、IL2CPP 大中间产物都排除在版本控制外。
    这个脚本负责把它们重建出来。

    产物落点：
      java\sources\              jadx 反编译的 Java 源码
      apk\*.txt                  aapt2 导出的 Manifest 树与资源表
      work\il2cpp\*.so / .dat    从 APK 提取的原始件
      work\il2cpp\dump\          Il2CppDumper 完整输出（il2cpp.h / script.json / DummyDll）
      il2cpp\dump.cs             从 dump 复制出来的核心产物
      il2cpp\stringliteral.json

.EXAMPLE
    .\build.ps1
    .\build.ps1 -ApkPath D:\dl\Phigros.apk
    .\build.ps1 -SkipJadx -SkipAapt     # 只重建 IL2CPP 部分
#>

[CmdletBinding()]
param(
    [string] $ApkPath = (Join-Path $PSScriptRoot 'Phigros_4.0.1.apk'),

    [string] $Jadx = (Join-Path $PSScriptRoot 'tools\jadx-1.5.6\bin\jadx.bat'),
    [string] $Il2CppDumper = 'F:\tool\dumper\Il2CppDumper.exe',
    [string] $Aapt2 = "$env:LOCALAPPDATA\Android\Sdk\build-tools\36.1.0\aapt2.exe",

    [string] $Abi = 'arm64-v8a',
    [switch] $SkipJadx,
    [switch] $SkipAapt,
    [switch] $SkipIl2cpp
)

$ErrorActionPreference = 'Stop'
Set-Location $PSScriptRoot

function Say  ($m) { Write-Host "  $m" }
function Head ($m) { Write-Host "`n=== $m ===" -ForegroundColor Cyan }

function Need ($path, $what) {
    if (-not (Test-Path $path)) {
        throw "缺 $what : $path`n    见 tools\README.md 了解从哪获取。"
    }
}

function Elapsed ($sw) { '{0:n1}s' -f $sw.Elapsed.TotalSeconds }

if (-not (Test-Path $ApkPath)) { throw "找不到 APK: $ApkPath`n    用 -ApkPath 指定。" }
Say "APK : $ApkPath"
Say ("大小: {0:n2} GB" -f ((Get-Item $ApkPath).Length / 1GB))

foreach ($d in 'java', 'apk', 'il2cpp', 'work\il2cpp', 'work\il2cpp\dump') {
    New-Item -ItemType Directory -Force $d | Out-Null
}

# ─────────────────────────────────────────────────────────────
# 1. Java 层 —— jadx
# ─────────────────────────────────────────────────────────────
if (-not $SkipJadx) {
    Head '1/4  Java 层 (jadx)'
    Need $Jadx 'jadx'
    $sw = [Diagnostics.Stopwatch]::StartNew()

    # --no-res 跳过资源解码：这个 APK 有 3.3 GB 资源，资源交给 aapt2
    & $Jadx --no-res -d 'java\sources' $ApkPath
    if ($LASTEXITCODE -ne 0) {
        # jadx 有反编译错误时也会返回非 0，但有产物就算成功
        Say "jadx 退出码 $LASTEXITCODE（有部分方法反编译失败是正常的）"
    }

    $n = (Get-ChildItem 'java\sources' -Recurse -Filter *.java -ErrorAction SilentlyContinue).Count
    Say "产出 $n 个 .java  ($(Elapsed $sw))"
} else { Head '1/4  Java 层 —— 已跳过' }

# ─────────────────────────────────────────────────────────────
# 2. APK 元数据 —— aapt2
# ─────────────────────────────────────────────────────────────
if (-not $SkipAapt) {
    Head '2/4  APK 元数据 (aapt2)'
    Need $Aapt2 'aapt2'
    $sw = [Diagnostics.Stopwatch]::StartNew()

    & $Aapt2 dump xmltree --file AndroidManifest.xml $ApkPath > 'apk\AndroidManifest_tree.txt'
    Say 'apk\AndroidManifest_tree.txt'

    & $Aapt2 dump resources $ApkPath > 'apk\resources.txt'
    Say 'apk\resources.txt'

    Say "完成 ($(Elapsed $sw))"
} else { Head '2/4  APK 元数据 —— 已跳过' }

# ─────────────────────────────────────────────────────────────
# 3. 从 APK 提取 IL2CPP 输入件
# ─────────────────────────────────────────────────────────────
if (-not $SkipIl2cpp) {
    Head '3/4  提取 libil2cpp.so + global-metadata.dat'
    $sw = [Diagnostics.Stopwatch]::StartNew()

    # 用 Python 解 zip —— 处理 Zip64 比 .NET Framework 的 ZipFile 稳
    $py = @'
import sys, zipfile, os
apk, abi, outdir = sys.argv[1], sys.argv[2], sys.argv[3]
items = [
    ('lib/%s/libil2cpp.so' % abi,              'libil2cpp_%s.so' % abi.replace('-', '')),
    ('assets/bin/Data/Managed/Metadata/global-metadata.dat', 'global-metadata.dat'),
]
z = zipfile.ZipFile(apk)
for src, dst in items:
    try:
        info = z.getinfo(src)
    except KeyError:
        sys.exit('APK 里没有: ' + src)
    dstpath = os.path.join(outdir, dst)
    with z.open(info) as f, open(dstpath, 'wb') as o:
        while True:
            b = f.read(1 << 20)
            if not b:
                break
            o.write(b)
    print('  %-42s %8.1f MB' % (dst, os.path.getsize(dstpath) / 1048576))
'@
    $py | Out-File -Encoding utf8 'work\_extract.py'
    python 'work\_extract.py' $ApkPath $Abi 'work\il2cpp'
    if ($LASTEXITCODE -ne 0) { throw 'APK 提取失败' }
    Remove-Item 'work\_extract.py' -ErrorAction SilentlyContinue

    Say "完成 ($(Elapsed $sw))"

    # ─────────────────────────────────────────────────────────
    # 4. Il2CppDumper
    # ─────────────────────────────────────────────────────────
    Head '4/4  IL2CPP dump'
    Need $Il2CppDumper 'Il2CppDumper'
    $sw = [Diagnostics.Stopwatch]::StartNew()

    $so  = "work\il2cpp\libil2cpp_$($Abi -replace '-','').so"
    $dat = 'work\il2cpp\global-metadata.dat'

    & $Il2CppDumper $so $dat 'work\il2cpp\dump'
    if ($LASTEXITCODE -ne 0) { throw 'Il2CppDumper 失败' }

    # 只把核心文本产物收进版本控制；il2cpp.h / script.json / DummyDll 留在 work\
    Copy-Item 'work\il2cpp\dump\dump.cs'            'il2cpp\dump.cs'            -Force
    Copy-Item 'work\il2cpp\dump\stringliteral.json' 'il2cpp\stringliteral.json' -Force

    Say ('dump.cs            {0,8:n1} MB' -f ((Get-Item 'il2cpp\dump.cs').Length / 1MB))
    Say ('stringliteral.json {0,8:n1} MB' -f ((Get-Item 'il2cpp\stringliteral.json').Length / 1MB))
    Say "完成 ($(Elapsed $sw))"
} else { Head '3-4  IL2CPP —— 已跳过' }

Head '全部完成'
Say '入库: java\sources\, il2cpp\dump.cs, il2cpp\stringliteral.json, apk\*.txt'
Say '未入库（可重生成）: work\ 下的 .so / .dat / il2cpp.h / script.json / DummyDll'
