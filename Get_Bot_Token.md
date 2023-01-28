# 附录: 获取一个 KOOK 的机器人 Token

开发 KOOK 的机器人，当然要先有一个机器人账号啦，获取机器人账号也很简单，按以下方法操作即可。

首先，你要有一个 KOOK 账号。

来到 [KOOK 开发者中心 - 应用后台](https://developer.kookapp.cn/app/index) ，登录你的 KOOK 账号。

之后在跳转到的新页面中间，偏右下角的地方找到一个 "授权" 按钮，点击。

然后浏览器会跳转到 KOOK 的应用后台，点击页面右上角的 "新建应用" 按钮，在弹出的对话框中输入新机器人的名字，按确定。

这样，Bot 就创建好了。

然后你将会回到应用后台，点击创建好的机器人应用头像。

现在，你能在左侧的「设置」选项卡列表中看到一项「机器人」，点开它。

点开「机器人」选项卡后，就能看到机器人的信息以及「机器人连接模式」、「Token」。

「机器人连接模式」请保持为「websocket」。这个连接模式便于你在你的设备上调试你的机器人，在你的机器人用户量不大时很推荐。

本教程不会讲 Webhook 模式的使用。如果你是 KookBC (一个 JKook API 的标准实现) 的用户，并且需要此模式，可以看 [KookBC 与 Webhook](https://github.com/SNWCreations/KookBC/blob/main/docs/KookBC_with_Webhook.md) 配置文档。

「Token」是机器人的身份凭证，请不要:

* 上传到任何地方
* 告诉其他人
* 写进将被公开的代码

若有不慎泄露(如在开发时遇到问题需要求助却不小心在截图中包含了没有打码的 Token)，在后台的 Token 框右侧有 "重置" 按钮，请毫不犹豫的按下去，然后用新 Token 替换你环境中的旧 Token。

---

本文编写时参考了 [khl.py Example - 引言](https://github.com/TWT233/khl.py/blob/main/example/README.md) ，在此表示感谢。
