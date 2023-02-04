# Chapter 0

很高兴你选择了 JKook 框架作为你的开发框架！我相信 JKook 框架不会让你失望！

那么，本章没有示例代码，只有对 JKook API 的介绍，以及一些基础知识。

## 总论

JKook 是一个面向 Java 平台的，对 KOOK API 进行了封装的 API 项目。

它以插件为程序的基本单位，多个插件在同一个机器人上同时运行，从而提供不同的功能。

它是以 Bukkit API 为原型设计的。因此，若你曾开发过 Bukkit 插件，JKook 对你会很友好。

没学过 Bukkit ？没关系，本教程也不需要你刻意学过 Bukkit 。放轻松。

## 有用的信息

[KOOK 官方开发者服务器](https://kook.top/cwznfo)

[JKook 开发者服务器](https://kook.top/aecCr6)

有问题就来这问问吧！

[JKook API Javadoc (最新 API)](https://jitpack.io/com/github/SNWCreations/JKook/latest/javadoc)

~~[JKook API Javadoc (0.37)](https://jitpack.io/com/github/SNWCreations/JKook/0.37.6/javadoc/)~~
* API 0.37 已经过时。

善用 Javadoc ，在遇到不知道用法的方法时去看看！

## JKook 插件开发中常用的类、方法

本节不讲解以下提及的类与方法，只希望你能在示例代码中看见它们时知道它们是什么，本教程会有专门的章节详细讲解它们。

你会在后面的教程的示例代码中经常看见本小节提到的类和方法的。

`snw.jkook.Core` - 作为所有 JKook API 服务组件的提供者

`snw.jkook.JKook` - 存放一个 `Core` 的实例，相当于 `Core` 的单例模式封装
* 在 API 0.38+ 下更推荐通过 `snw.jkook.plugin.Plugin#getCore` 方法获取 `Core` 的实例进而调用

`snw.jkook.plugin.Plugin` - 表示一个插件

`snw.jkook.plugin.BasePlugin` - `snw.jkook.plugin.Plugin` 接口的一个可用实现，遵循 [JKook 插件格式规范](https://github.com/SNWCreations/JKook/wiki/Plugin-Format) 。推荐插件开发者直接继承此类作为插件的主类。

`snw.jkook.command.JKookCommand` - 表示一个命令，对此类的详细讲解详见第 6 章

`snw.jkook.scheduler.Scheduler` - 任务调度器，对此类的详细讲解详见第 8 章

`snw.jkook.plugin.Plugin#getLogger` - 获取插件的日志记录器

`snw.jkook.entity.User` - 用户对象，对此类的详细讲解详见第 4 章

`snw.jkook.entity.Guild` - 服务器对象，对此类的详细讲解详见第 4 章
