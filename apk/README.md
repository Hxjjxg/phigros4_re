# APK 解剖

## 基本身份

| | |
|---|---|
| 包名 | `com.PigeonGames.Phigros` |
| versionCode / versionName | `157` / `4.0.1` |
| min / target / compile SDK | 26 / 30 / 30 |
| ABI | `arm64-v8a`, `armeabi-v7a` |
| 入口 Activity | `com.unity3d.player.UnityPlayerActivity`（exported=true, launchMode=singleTask） |
| 权限 | 仅 `INTERNET` + `ACCESS_NETWORK_STATE` |

**权限只有网络两项**——没有存储、没有定位。网络是它唯一的对外能力。

## zip 结构

2928 个条目，其中 **2694 个是 `assets/aa/`**（Unity Addressables 资源包），占了几乎全部体积。

| 文件 | 解压后 | 角色 |
|---|---|---|
| `classes.dex` | 5.86 MB | Java 层（纯 SDK 壳） |
| `lib/arm64-v8a/libil2cpp.so` | 71.7 MB | 游戏逻辑 + 网络栈 |
| `lib/arm64-v8a/libunity.so` | 20.3 MB | Unity 运行时 |
| `lib/arm64-v8a/libmain.so` | 6.7 KB | JNI 入口 |
| `lib/arm64-v8a/libtapsdkcore.so` | 4.2 MB | TapTap SDK core |
| `lib/arm64-v8a/libnativeaudioe7.so` | 106 KB | Exceed7 NativeAudio |
| `assets/bin/Data/Managed/Metadata/global-metadata.dat` | 11.3 MB | IL2CPP 元数据 |
| `assets/bin/Data/data.unity3d` | 176 MB | Unity 序列化资源 |

## Manifest 要点

- `android:extractNativeLibs="true"` → `.so` 解到 `/data/app/…/lib/arm64/`，Frida 可直接 `Module.findBaseAddress`
- `android:networkSecurityConfig=@0x7f0d0000` → `res/8G.xml`，**放行明文 + 信任用户 CA**，见根 README
- 其他 Activity：`com.taptap.sdk.TapTapActivity`、`com.taptap.sdk.ui.TapTapActivity`、`com.taptap.sdk.TapLoginHelperActivity`、`GoogleApiActivity`、`GooglePlayGameUIActivity`
- Provider：
  - `com.tds.common.provider.CurrentConfigProvider`（authority `…tap_config.provider`）
  - `com.taptap.sdk.internal.user.CurrentUserProvider`（authority `…tap_user.provider`）
- meta-data：`com.google.android.gms.games.APP_ID = 358918849784`、`com.tapsdk.antiaddiction.loader = true`

## 本目录文件

| 文件 | 来源 |
|---|---|
| `AndroidManifest_tree.txt` | `aapt2 dump xmltree --file AndroidManifest.xml` |
| `resources.txt` | `aapt2 dump resources`（完整 ARSC 资源表，2375 行） |

## 重新生成

```powershell
$AAPT2 = "$env:LOCALAPPDATA\Android\Sdk\build-tools\36.1.0\aapt2.exe"
& $AAPT2 dump xmltree --file AndroidManifest.xml ..\Phigros_4.0.1.apk > AndroidManifest_tree.txt
& $AAPT2 dump resources ..\Phigros_4.0.1.apk > resources.txt
& $AAPT2 dump badging ..\Phigros_4.0.1.apk      # 包名/权限/ABI 摘要
```

`resources.txt` 里查资源 id 的用法：manifest 里的 `@0x7f0d0000` → 在表里搜到 `xml/network_security_config` → 实际文件是 `res/8G.xml`。
