# 工具链

**工具二进制不入库**（`jadx-*/`、`*.exe`、`*.jar` 都在 `.gitignore` 里）。这份文档说明每个工具从哪来、装到哪、怎么调。

## 本机现状

| 工具 | 路径 | 版本 | 来源 |
|---|---|---|---|
| jadx | `tools\jadx-1.5.6\bin\jadx.bat` | 1.5.6 | [GitHub Releases](https://github.com/skylot/jadx/releases) |
| Il2CppDumper | `F:\tool\dumper\Il2CppDumper.exe` | — | [GitHub Releases](https://github.com/Perfare/Il2CppDumper/releases) |
| aapt2 | `%LOCALAPPDATA%\Android\Sdk\build-tools\36.1.0\aapt2.exe` | 36.1.0 | Android SDK Build-Tools |
| apktool | `C:\Windows\apktool.bat` | 3.0.2 | [GitHub Releases](https://github.com/iBotPeaches/Apktool/releases) |
| frida | `%APPDATA%\Python\Python313\Scripts\frida.exe` | 17.9.1 | `pip install frida-tools` |
| adb | PATH | 1.0.41 (36.0.0) | Android Platform-Tools |
| java | PATH | 17.0.10 | Oracle JDK |
| python | `C:\Python313\python.exe` | 3.13.1 | python.org |
| dotnet | PATH | 6 / 8 / 9 / 10 | 部分工具依赖 |

## 各自负责什么

### jadx —— Java 层

```powershell
tools\jadx-1.5.6\bin\jadx.bat --no-res -d java\sources Phigros_4.0.1.apk
```

`--no-res` 只反编译 dex，跳过资源——资源交给 aapt2（快得多，且这个 APK 有 3.3 GB 资源）。

其他常用：

```powershell
jadx.bat --version
jadx.bat -d out --single-class com.example.Foo Phigros_4.0.1.apk
```

### Il2CppDumper —— C# 层

```powershell
Il2CppDumper.exe libil2cpp_arm64.so global-metadata.dat <输出目录>
```

输入的两个文件要先从 APK 里抠出来（`build.ps1` 会做）。产出四样：

| 产出 | 用途 |
|---|---|
| `dump.cs` | C# 类型结构 + RVA/offset/VA，**最常读的就是这个** |
| `script.json` | IDA / Ghidra 的符号脚本 |
| `il2cpp.h` | 结构体定义，喂 IDA |
| `DummyDll/` | 桩程序集，丢进 dnSpy 能像看 .NET 一样浏览 |

自带的 IDA 脚本：`ida_with_struct_py3.py`（IDAPython 3.x + 结构体）。

### aapt2 —— APK 元数据

```powershell
aapt2 dump badging Phigros_4.0.1.apk                    # 包名/权限/ABI 摘要
aapt2 dump xmltree --file AndroidManifest.xml <apk>     # 完整 Manifest 树
aapt2 dump resources <apk>                              # 完整 ARSC 资源表
aapt2 dump xmltree --file res/8G.xml <apk>              # 解某个二进制 XML
```

### frida —— 动态

```powershell
frida --version
frida-ps -U                       # 列进程
frida -U -n Gadget -l script.js   # 注入（gadget 重打包路线）
frida -U -f com.pkg -l script.js  # 注入（frida-server 路线）
```

> **frida-server 版本必须与 PC 端 `frida --version` 一致**，否则握手失败。

### apktool —— 重打包（改 smali / 塞 gadget）

```powershell
apktool d Phigros_4.0.1.apk -o unpacked
apktool b unpacked -o rebuilt.apk
```

这条链路还要 `zipalign` + `apksigner` 补签名，参考 `apk-reverse` skill 的 `rebuild-sign-install.ps1`。

## Python 依赖

| 包 | 版本 | 谁需要 |
|---|---|---|
| `websockets` | 12.0 | `network\ws\ws_server.py` |

> ⚠️ 本机这套 `websockets` 的 `connect`/`serve` 都解析到 **legacy** 实现：handler 签名是 `(ws, path)`，header 参数是 `extra_headers` 而非 `additional_headers`。`ws_server.py` 已写成版本无关。
