# JKook Tutorial

欢迎！

此仓库是 [JKook API](https://github.com/SNWCreations/JKook) 的系列教程存放地。

通过阅读此仓库的文档，你将了解 JKook API 中的各种类，从而帮助你开发 JKook 插件。

## 观前提示

每一章节的文件夹以 `"ch_<章节号>"` 格式命名。

具体的示例代码在章节文件夹下的 `code` 文件夹，以 Maven 项目形式呈现。

示例代码中会有形如 `/* 1 */` ， `/* 2 */` 之类的纯数字 Java 注释，这些数字注释将在章节中的README中以 "第 X 处" 的形式提及。

示例代码可以不加修改地编译，并在 JKook API 实现上运行。

章节文档中提及的路径均为以章节目录为根目录的相对路径。

**请确保你已经有 Java 基础 (Java 语法至少要会) ，这是编写 JKook 插件的前提。**

看每一个 Chapter 时，请同时打开其目录下的 README.md 及具体代码，善用你的 IDE 的分栏功能 (如果有) 。

推荐使用 IntelliJ IDEA 打开此仓库，观看体验最佳。

运行示例请自行准备一个 Bot Token ，如果你不知道怎么获取，去 [KOOK 开发者中心服务器](https://kook.top/cwznfo) 问问吧，大家会为你解答的。

## 环境要求

本教程的所有示例使用 JKook API 0.37 。

所用工具链如下:

* OpenJDK 8
* Apache Maven 3.8
* KookBC 0.17

是的，我们使用 Maven 。Gradle 也十分优秀，但是...

_作者不会用。_

不过你可以看看 [JKook 插件 Gradle 模板](https://github.com/RealSeek/JKookExample) ，我想只需要把示例的具体代码移动一下...应该就行了吧。

JDK 与 Maven 的安装这里不作详细讲解。

KookBC 的使用方法可以在[其仓库](https://github.com/SNWCreations/KookBC)的 README 找到。

## 目录

* 无链接的为目前尚未编写的章节。**您可以参与编写！**

[Chapter 0 - JKook API 介绍](ch_0/README.md)

[Chapter 1 - Hello World!](ch_1/README.md)

Chapter 2 - 命令系统初步

Chapter 3 - 事件系统概述

Chapter 4 - HTTP API

Chapter 5 - 任务调度系统

## 贡献

啊！我很高兴你愿意为此教程做出贡献！

目前，文档还未完成，我也只是写了大纲，如果你知道应该写什么，Fork 此仓库，笔就在你手上了，开始写吧！

对于教程内容的错误，你可以在 Issue 中提出，或者直接 PR 提交你的修改！
