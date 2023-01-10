# Chapter 3

命令是 Bot 和用户的重要交流渠道。

其实所谓命令，只是一种规范化的消息。

本章，我们将介绍 JKook API 的命令系统用法。

本章基于两个 API 大版本编写: 0.37 LTS, 0.42 LTS 。

两个版本之间不同的部分将在下文提及。

本章的代码示例基于 API 0.42 编写。

请在读完本文后再阅读示例代码。

## 总纲

JKook API 命令系统相关的类均可以在 `snw.jkook.command` 包中找到。

此包的结构如下:

```text
snw.jkook
|
+---command
       JKookCommand
       CommandException
       CommandExecutor
       CommandManager
       CommandSender
       ConsoleCommandSender
       ConsoleCommandExecutor (自 API 0.38 开始)
       OptionalArgumentContainer (自 API 0.38 开始)
       UserCommandExecutor (自 API 0.38 开始)
```

下文将一一解释这些类。

## JKookCommand

这是 JKook API 命令系统的核心类。

表示一个命令。

在 JKook API 命令系统中，一个命令可以拥有如下属性：
* 名称
* 别称
* 前缀
* 简介
* 帮助信息
* 执行器
* 参数类型 (自 API 0.38 开始)
