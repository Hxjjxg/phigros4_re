# 独立 APK 验收记录

日期：2026-10-17。

## 产物身份

| 项目 | 值 |
| --- | --- |
| 文件 | `out/Phigros_4.0.1-DGLab-arm64.apk` |
| 大小 | 3,335,076,183 bytes |
| APK SHA-256 | `452aac1d644ddf98af873ba1e17a12777360574094cdff69e2f1492f5991dcfa` |
| 原始 APK SHA-256 | `f59a041f5bc02d0f3eb51a67739ec0271590ac412e68cf6cc6b43621cdf08985` |
| Gadget | Frida 16.7.19，arm64，script 模式 |
| Gadget SHA-256 | `61be4bbeae285bb19164dc9eebee58669f6ad2ab4b4d64422ea0a779731b0898` |
| apktool | 3.0.3 |
| apktool SHA-256 | `dbf930b076c6b9be08d57c449cacefc3bdd6b71ebd59b3066fc0e1f5b14f9423` |
| 签名证书 SHA-256 | `ecfbe0ef2da8a29da809bc1a4212ccd9e9f1d6ff85ba987591e483abdae3c624` |

## 构建时自动检查

- Java 启动器和 transport 编译、附加 `classes2.dex` 生成成功。
- 生成的独立 JS 通过 Node 语法检查。
- 5 项 DG-LAB 协议测试和 4 项冷启动测试通过，共 9 项。
  冷启动测试覆盖库/domain 延迟就绪、超时报告、部分 hook 安装失败后的回滚及 Application 等待。
- `apksigner verify` 成功，报告 v2、v3 签名验证通过。
- `zipalign -c -P 16 4` 成功。这是 ZIP 对齐检查，不代表所有原始 ELF 已验证支持 16 KB 系统页。
- 2,922 个保留条目的 CRC/大小与原 APK 一致，注入内容逐项比对成功。
- APK 完整 ZIP CRC 检查通过，无重复或意外条目。

检查命令：

```powershell
node --test network/dglab/tests/core.test.js hooks/judgment/tests/startup.test.js
```

完整机器构建报告位于产物旁的 `.build.json`。其中 `deviceTest: not performed` 记录的是
构建完成时自动构建流程没有操作设备，不代表后续用户未验证；用户反馈记录如下。

## 用户实机反馈

产物交付后，用户反馈：**“可以可以可以 正常工作”**，并要求记录和提交。
据此将该独立包标记为“用户确认正常工作”。构建时 ADB 没有连接设备，代理没有独立采集
此包的安装日志、截图或逐项运行计数，不能将反馈扩展为所有场景已逐项自动验收。

此前 Frida 外部注入版本已完成官方 DG-LAB 4.2.6 同机配对、真实判定任务响应，
用户明确确认实体设备成功输出。这些证据与独立 APK 的本次反馈分别记录，不能混用。

## 后续回归范围

- 强制停止游戏、手机重启后的自动加载和配对 ID 保留。
- 设置保存后重新打开、STOP 后恢复。
- DG-LAB 断线重连、长时间前后台切换、系统回收后的恢复。
- 重新签名对登录及云存档的影响。
- 物理输出起始延迟需独立测量；任务完成响应时间不是该指标。
