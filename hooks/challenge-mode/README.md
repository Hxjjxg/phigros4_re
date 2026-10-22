# Challenge Mode 入口补丁

适用原始 **Phigros 4.0.1 (157)、Android arm64、Frida 16**。脚本独立使用，无需 npm 或 IL2CPP bridge。

## 写入存档版

使用 `unlock_challenge_mode_save.js`。先退出旧 Frida 会话，或卸载旧的入口 Hook，再加载这个脚本。
沿用原来的连接参数，把 `-l` 后面的文件换成：

```powershell
& 'F:\frida16\Scripts\frida.exe' -H 127.0.0.1:27042 -n Gadget -l hooks\challenge-mode\unlock_challenge_mode_save.js
```

USB Frida server 可把 `-H 127.0.0.1:27042` 换成 `-U`。

1. 看到 `[phi-challenge-save] Ready` 后，离开章节选择页再进入。
2. 等到 `"action":"unlocked"` 和 `Saved` 日志，表示已写入并读回核对。**Ready 只表示安装成功，还没有写存档。**
3. 再离开章节选择页并重新进入，从“全部歌曲”打开挑战模式。当前一次 UI 读取保留原返回值，需要重建页面。
4. 退出脚本并重启游戏，检查入口是否仍存在。

脚本通过游戏的 `SaveManagement.SaveString` 加密保存 `GameCompleted = "4.0"`，然后调用
`PlayerPrefs.Save()`。原值先备份至同一存档的 `PhiChallengeMode.Backup.v1`，备份保存并读回成功后才改通关标志。
已有非空 `GameCompleted` 时保留原值，输出 `"action":"unchanged"`。失败时查看：

```javascript
PhiChallengeSave.status()
PhiChallengeSave.unlock()   // 排队重试；随后重新进入章节选择页
```

这是全局通关标志，会同时影响全部歌曲卡片、旧谱可见性及设置中的片尾入口；
不写 `chapter8Passed`、`C8DistortedFateUnlocked` 或第九章完成标志，也不触发片尾播放。

如需恢复原值，在自然通关之前、同一存档中执行：

```javascript
PhiChallengeSave.restore()  // 可重新加载本脚本后立即调用，然后再进入章节选择页
```

等到 `"action":"restored"` 和 `Saved`，再次进入章节选择页或重启游戏。恢复成功后清空备份内容。
恢复原值会重新应用原入口门槛；游戏自然通关也会使用 `"4.0"`，脚本无法分辨它和补丁写入的值。
若当前值已变为其他值，恢复操作会拒绝覆盖。`PhiChallengeSave.dispose()` 只卸载 Hook，不撤销存档修改。

保存函数 RVA 为 `0x1CC33DC`，落盘函数 RVA 为 `0x3C53F20`。与两个 UI 调用点、读取函数一起核对机器码后才安装。
修改在游戏自己的 UI 读取线程执行，不在 Frida 注入线程直接调用 Unity 保存 API。

## 仅运行期间生效的入口版

使用 `unlock_challenge_mode.js`。已修复 `expected an integer`：Frida 原生 `bool` 参数使用数字 `1`，而非 JS `true`。

在章节选择页保留包含挑战模式按钮的 **All Songs / 全部歌曲** 卡片，并同步修正卡片数量计算。
只在两个已核实的 UI 调用点，把空的 `GameCompleted` 读取结果替换为临时非空字符串。
不写入存档、不标记剧情通关，也不补 DF 的永久解锁或 AT 条件。效果持续到禁用或卸载脚本；
下次启动需要重新加载，或将脚本作为现有 Gadget 自动加载脚本的一部分。

### 使用

在提供 Frida server 或 Gadget listener 的开发环境中运行：

```powershell
& 'F:\frida16\Scripts\frida.exe' -U -n Gadget -l hooks\challenge-mode\unlock_challenge_mode.js
```

原版进程也可将 `-n Gadget` 换成 `-N com.PigeonGames.Phigros`。连接远程 listener 时，将 `-U` 换成
`-H 127.0.0.1:27042`。已配置自动执行脚本、未开放 listener 的独立 APK 不能直接 attach。

看到 `[phi-challenge] Ready` 后，**离开章节选择页再重新进入**，然后从全部歌曲卡片打开挑战模式。
当前场景中已经销毁的卡片需要重建，加载脚本不会立即恢复它。

Frida REPL：

```javascript
PhiChallengeMode.status()       // installed / enabled / replacements / error
PhiChallengeMode.enable(false)  // 禁用；重新进入章节选择后恢复原门槛
PhiChallengeMode.enable(true)
PhiChallengeMode.dispose()      // 卸载 hook 并释放临时字符串，再退出 Frida
```

脚本不改变第九章挑战曲的触发条件。正常触发 Exoplanetary Mirage 仍需进入第九章、阅读
`poyidomejiu1`，再在挑战模式选择同难度的 **Evanescent → About The Universe**，由游戏加入隐藏第三曲。

## 验证

- Hook：`SaveManagement.LoadString`，RVA `0x1CC42C8`。
- 仅处理 key 为 `GameCompleted` 且调用返回地址为 `0x1D5390C` / `0x1D576F4` 的读取：
  分别是 `ChapterSelector.Start` 的卡片计数和 `RemoveAllSongControl.Awake` 的卡片销毁条件。
- 其他调用点、其他 key、已有非空值均返回原结果。All Songs 与 Challenge Mode 共用父卡片，因此一起显示。
- 安装前核对函数入口和两个调用点的机器码；不匹配时拒绝安装。偏移仅适用于已分析的版本。
- 临时字符串在游戏线程创建并以 GC handle 保留，`dispose()` 释放；没有调用 SaveString、SaveBool 或 PlayerPrefs.Save。

```powershell
node --check hooks/challenge-mode/unlock_challenge_mode.js
node --check hooks/challenge-mode/unlock_challenge_mode_save.js
node --test hooks/challenge-mode/tests/unlock.test.js
```

已通过语法检查与 10 项模拟 Frida/IL2CPP 测试，覆盖两个门槛、作用域、已有存档、版本检查、卸载、
备份、写入顺序、重载后恢复以及失败保护。另以真实 Frida 16 的原生回调复现了布尔参数转换报错，数字 `1` 调用成功。
尚未注入设备验证实际入口和第九章挑战流程；测试通过不代表实机验证完成。
