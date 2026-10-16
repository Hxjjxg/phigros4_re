# Java 层（classes.dex）

`classes.dex` 5.86 MB，jadx 反编译出 **3928 个 `.java`**，4 个反编译错误。

## 结论：全是第三方 SDK，没有游戏业务代码

`com/PigeonGames/Phigros/` 下**只有 `BuildConfig.java` 和 `R.java`**。游戏逻辑一行都不在 Java 层——全在 `libil2cpp.so` 里。

## 包结构

| 包 | 文件数 | 是什么 |
|---|---|---|
| `com` | 3192 | 见下 |
| `androidx` | 401 | AndroidX 支持库 |
| `tds` | 127 | TapTap Data Service（含 androidx 重打包副本） |
| `io` | 109 | `io/fabric`（Twitter Fabric） |
| `retrofit` | 65 | Retrofit 1.x |
| `android` | 30 | 支持库残留 |
| `bitter` | 2 | `bitter/jnibridge`（JNI 桥） |
| `org` | 2 | `org/fmod`（FMOD 音频） |

`com` 下的主要模块：

| 包 | 是什么 |
|---|---|
| `com.tds.common.*` | **TapTap TDS**：`websocket/`、`bridge/`、`protobuf/`、`lz4/`、`xxhash/`、`gson/` |
| `com.taptap.sdk.*` | TapTap SDK：登录、防沉迷 UI |
| `com.tapsdk.antiaddiction.*` | 防沉迷（内含 shaded OkHttp 3 + Retrofit 2） |
| `com.voxelbusters.*` | Native Plugins（Google Play Games） |
| `com.digits.sdk.*` / `com.twitter.sdk.*` | Twitter Digits 登录 |
| `com.squareup.picasso` | 图片加载 |
| `com.google.androidgamesdk` | Android Game SDK |
| `com.Exceed7.NativeAudio` | NativeAudio 插件 |
| `com.pingak9.nativepopup` | 原生弹窗插件 |
| `com.p000pigeongames.networksecuritylib` | 只有 `R.java`（空壳） |

## WebSocket

Java 层有**两套** WebSocket 实现，**游戏自身都不调用**：

| 实现 | 位置 | 状态 |
|---|---|---|
| Java-WebSocket（RFC6455 完整） | `com/tds/common/websocket/` | ✅ 完整可用，零调用点 |
| shaded OkHttp 3 | `com/tapsdk/antiaddiction/skynet/okhttp3/` | WS 部分是死代码，只跑 HTTP |

**被复用的就是第一套**——见 [`../network/ws/`](../network/ws/)。

### 验证「零调用点」的三条证据

1. `grep TDSWebSocketService` 在整个 dex 内除自身包外**零引用**（连反射所需的字符串都没有）
2. `setDestUrl` 只出现在定义处
3. C# 侧字符串常量里也没有这个类名

结论：它由 Unity 运行时通过 JNI 桥按需唤醒，静态拿不到 URL 和 token。

## 重新生成

在仓库根目录执行：

```powershell
.\build.ps1 -SkipAapt -SkipIl2cpp
```

`--no-res` 跳过资源解码（资源走 `aapt2`，见 `apk/`）。

`sources/` 已加入 `.gitignore`，仅供本地分析，不再跟踪其中的 3,928 个反编译文件。
现有本地文件无需删除；新克隆仓库需要先运行上述命令生成。历史提交仍保留原始收录内容。
手写 Java 和编译桩位于 `network/`、`packaging/`，不受此规则影响。
