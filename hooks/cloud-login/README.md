# 云存档 WebView 登录

适用于 Phigros 4.0.1、TapTap Android SDK 3.30.3、Frida 16.x。2026-10-19 用户确认 v2 实机恢复正常；提供的日志显示连续四次进入强制 WebView 选路。

| 文件 | 用途 |
|---|---|
| `force_webview.js` | 强制使用 SDK 自带的 WebView 授权，默认启用 |
| `signature_probe.js` | 观察签名访问与登录错误来源，保留原返回值 |
| `tests/force_webview.test.js` | 服务解绑崩溃及原选路的回归测试 |

定位过程、原生函数 RVA 与崩溃依据见 [DEBUG.md](../../docs/cloud-login/DEBUG.md)。

## 强制 WebView 授权

用户实测：卸载 TapTap 客户端后，SDK 自动使用 WebView，云存档登录可正常进行。`force_webview.js` 用于在保留 TapTap 客户端的情况下测试同一网页授权路径。

对于已验证的监听型 Gadget：先打开游戏，再在 PowerShell 中运行：

```powershell
Set-Location 'E:\phigros4_re'
adb forward tcp:27042 tcp:27042
& 'F:\frida16\Scripts\frida.exe' -H 127.0.0.1:27042 -n Gadget -l hooks/cloud-login/force_webview.js
```

看到以下日志后再点击云存档登录：

```text
[phi-web-login] Ready v2: LoginClient.sendLoginRequest -> WebViewHandler.authorize (cloud-service cleanup fixed)
```

先退出旧 Frida 控制台，再连接新脚本。旧脚本闪退后应重新打开游戏。

使用 Frida server 时，也可从项目目录启动并注入：

```powershell
& 'F:\frida16\Scripts\frida.exe' -U -f com.PigeonGames.Phigros -l hooks/cloud-login/force_webview.js
```

已运行的游戏可用 `-p <游戏PID>` 替代 `-f com.PigeonGames.Phigros`。需要已有 Frida server 或可 attach 的 Gadget 环境；当前独立 APK 的自动脚本型 Gadget 不提供 attach 端口。本次未将实验脚本打入 APK。

脚本替换 `LoginClient.sendLoginRequest(LoginRequest)`：保留原方法的 verifier 生成、`initLoginRequest()` 和 request 保存，然后调用该实例已有的 `WebViewHandler.authorize()`。返回 `false` 表示 `LoginBlock.isAuthorizeByClient=false`，不是登录失败；最终结果仍由 SDK 的网页授权、token 交换和回调处理。调用保持在原来的登录线程上。

v2 在打开 WebView 前将此 LoginClient 尚未绑定服务的 `cloudGameHandler` 置空。WebView 会同步销毁 LoginBlock；其 onDestroy 根据该字段决定是否调用 unbindService。v1 跳过了绑定却留下非空连接，导致 `Service not registered` 闪退。v2 只修改强制网页选路所用的实例；关闭开关后新登录实例仍调用原方法。

Frida REPL 控制：

```javascript
phiWebLogin()       // 查询开关
phiWebLogin(false)  // 下一次登录恢复 SDK 原有选路
phiWebLogin(true)   // 下一次登录强制 WebView
```

开关不改变已经打开的授权页面。退出当前登录页面后再开始下一次测试。脚本重载后默认启用；日志只记录选路和次数，不打印登录凭据。

验证记录（2026-10-19）：用户在 PGJM10 / Android 13、Frida 16.7.19 的 Gadget 环境中反馈“现在好了”，日志包含 `Ready v2` 和 `Opening SDK WebView, attempt=1` 至 `attempt=4`。此反馈确认 v2 在该环境中恢复正常；日志本身只记录选路，不能据此确认云存档读写。取消登录及 `phiWebLogin(false)` 恢复原选路的实机行为未单独记录。

从项目根目录运行本地检查：

```powershell
node --check hooks/cloud-login/force_webview.js
node --check hooks/cloud-login/signature_probe.js
node --test hooks/cloud-login/tests/force_webview.test.js
```

回归测试模拟 SDK 在替换 LoginBlock 时的同步销毁与解绑行为：v1 复现 `Service not registered`，v2 成功创建 WebBlock，禁用 hook 时调用原方法并保留服务处理器。

## 登录签名定位探针

`signature_probe.js` 用于定位签名读取、校验结果和 TapTap 登录错误来源。所有 hook 都调用原方法一次并保留返回值与异常；它不是绕过补丁。兼容目标为项目现用的 Frida 16.x Java bridge。

## 使用

在已有 Frida server 或可 attach 的 Gadget 环境中，对游戏进程加载：

```powershell
& 'F:\frida16\Scripts\frida.exe' -U -f com.PigeonGames.Phigros -l hooks/cloud-login/signature_probe.js
```

进入游戏后触发一次云存档登录，查看 `[phi-sign]` 日志。也可用 `-p <PID>` 附加到已有进程。当前独立 APK 的 Gadget 是自动执行脚本模式，不开放 attach 端口，不能直接使用以上命令连接它。此探针未加入独立 APK 构建。

若日志指向外部 TapTap 客户端，在 Frida 可访问该客户端进程的环境中，用它的实际 PID 加载同一脚本，并重新触发登录。脚本会输出进程 PID；TapTap 进程可能没有游戏所用 SDK 类，相应 `hook-unavailable` 属于预期结果。

## 如何判断

| 日志 | 含义 |
|---|---|
| `authorization-start`，route=`external-client` | SDK 正在拉起外部 TapTap 授权 |
| `login-intent-result`，signatureMismatch=true | 错误已在返回的 Intent 中；结合 route 判断来自哪条授权路径 |
| `token-exchange-response` / `token-exchange-error` | 授权码换取 token 阶段的结果 |
| `profile-fetch-error` | 已进入 token 后的用户资料请求，失败发生于此阶段 |
| `game-login-error` | 游戏 Java SDK 最终收到的错误与调用栈 |
| `package-signatures-requested` / `signers-*` / `certificate-*` | 本进程读取或比较证书的调用栈，可用于找到业务校验函数 |
| `package-checkSignatures` | Android 包签名比较结果；0 表示匹配，负数表示失败原因 |
| `package-hasSigningCertificate` | Android 证书查询的布尔结果 |
| `crypto-signature-verify` | TapTap/Phigros 相关调用栈中的密码学验签结果；它也可能属于网络协议，需结合调用栈判断 |

`-1` 来自 AccountGlobalError 的通用错误包装，不能与 Android `checkSignatures()` 的返回码混为一谈。

探针仅观察其所在进程。Java hook 不覆盖纯 native/JNI 校验、服务端校验、其他进程或已缓存的结果；没有日志不能证明不存在校验。对 TapTap 的证书读取也可能是在校验别的包，必须核对调用栈及目标。脚本不输出证书内容、token、授权码或完整响应，只记录相关字段是否存在、是否匹配已知错误文本和调用栈。同一事件/字段/栈组合最多输出三次。

## 后续修改依据

只有找到实际的本地比较或验证函数后，才能确定合适的 hook。只改 `TapSDKWrapper.Login()` 的成功标记不会创建有效 TDSUser/sessionToken。

若游戏从外部 TapTap 客户端直接收到签名错误，游戏进程里的 PackageManager hook 无法改变另一个进程看到的签名。可以继续定位 TapTap 进程中的校验，或测试 SDK 自带的 WebView 授权分支；网页分支仍须正常完成用户授权及服务端认证。

## 验证状态

- 已按仓库 Java 源码和原有分析核对 SDK 方法名及回调阶段。
- 签名定位探针尚未在设备上验证；上面的实机反馈针对 `force_webview.js` v2。

## Bundle 字符串扫描

`docs/cloud-login/scan_bundles.py` 使用 UnityPy 解压 APK 中的 `.bundle`，搜索错误文本（UTF-8 / UTF-16LE）。安装 UnityPy 后从项目根目录运行：

```powershell
python docs/cloud-login/scan_bundles.py Phigros_4.0.1.apk work/cloud-login/bundle-scan.json
```

输出目录需已存在。已扫描原 APK 的 2,691 个 bundle，零错误、零匹配；完整结果属于本地分析产物，不入库。
