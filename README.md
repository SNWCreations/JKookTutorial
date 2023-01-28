# JKook Tutorial

欢迎！

此仓库是 [JKook API](https://github.com/SNWCreations/JKook) 的系列教程存放地。

通过阅读此仓库的文档，你将了解 JKook API 中的各种类，从而帮助你开发 JKook 插件。

若需要从 JKook API 0.37 迁移到 API 0.48 ，见[这篇教程](Migrate_To_0.48.md)。

## 观前提示

每一章节的文件夹以 `"ch_<章节号>"` 格式命名。

具体的示例代码在章节文件夹下的 `code` 文件夹，以 Maven 项目形式呈现。

示例代码中会有形如 `/* 1 */` ， `/* 2 */` 之类的纯数字 Java 注释，这些数字注释将在章节中的README中以 "第 X 处" 的形式提及。

示例代码可以不加修改地编译，并在 JKook API 实现上运行。

章节文档中会大量出现诸如 `ClassName#methodName` 的字样，其中 `ClassName` 为一个类的名称，`methodName` 为类中一个方法的名称。

章节文档中提及的路径均为以章节目录为根目录的相对路径。

若在教程文档中发现你不认识的方法或类，可以查阅 JKook API 的 Javadoc (链接在第 0 章)，或者带着疑问继续阅读后续教程(可能有些类在后文会讲)。

**请确保你已经有 Java 基础，这是编写 JKook 插件的前提。**
* Java 开发教程我们推荐 [廖雪峰](https://www.liaoxuefeng.com/wiki/1252599548343744) 编写的。但其教程始终基于最新版 Java 编写，而我们在 Java 8 ，故可能有些新版本的语法在 Java 8 中不可用。具体的区别请自行辨析。若需要使用更高版本的 Java ，则运行 KookBC 时将需要使用对应版本的 JRE 。更多内容详见 [第 1 章](ch_1/README.md) 的 "配置 KookBC 以用于调试" 部分。
* 你需要掌握：Java 基础语法，Java 基础库（如 `java.util` 包及其中包含的 Java Collection 框架），泛型等
* 学习本教程的内容不需要你刻意学习 Java ME 或 Java EE ，Java SE 就够了。额外的，像 JDBC 数据库操作一类的请自行学习，那也不是本教程的范围。

看每一个 Chapter 时，请同时打开其目录下的 README.md 及具体代码，善用你的 IDE 的分栏功能 (如果有) 。

推荐使用 IntelliJ IDEA 打开此仓库，可以边读教程边运行示例。

也可以在本页面单击空白处，按下键盘上的 `.` 键，即可在 Github.dev 在线编辑器中打开本仓库。**但是仅能读，不能编译运行示例。** 点击 [本链接](https://github.dev/SNWCreations/JKookTutorial) 也可以直接跳转。

运行示例请自行准备一个 Bot Token ，如果你不知道怎么获取，[看看这篇教程](Get_Bot_Token.md)。

## 环境要求

本教程的所有示例在无特殊说明的情况下使用 JKook API 0.37 。

无特殊说明时，所用工具链如下:

* OpenJDK 8
* Apache Maven 3.8

是的，我们使用 Maven 。Gradle 也十分优秀，但是...

_作者不会用。_

不过你可以看看 [JKook 插件 Gradle 模板](https://github.com/RealSeek/JKookExample) ，我想只需要把示例的具体代码移动一下...应该就行了吧。

JDK 与 Maven 的安装这里不作详细讲解。

KookBC 的使用方法可以在[其仓库](https://github.com/SNWCreations/KookBC)的 README 找到。

## 目录

[Chapter 0 - JKook API 介绍](ch_0/README.md)

[Chapter 1 - Hello World!](ch_1/README.md)

[Chapter 2 - 配置文件](ch_2/README.md)

[Chapter 3 - 再看 Plugin](ch_3/README.md)

[Chapter 4 - 实体体系概述](ch_4/README.md)

[Chapter 5 - 消息体系概述](ch_5/README.md)

[Chapter 6 - 命令系统概述](ch_6/README.md)

[Chapter 7 - 事件体系概述](ch_7/README.md)

[Chapter 8 - 任务调度系统](ch_8/README.md)

[Chapter 9 - 进阶内容](ch_9/README.md)

[Chapter 10 - 终章！动手实践！](ch_10/README.md)

## 贡献

啊！我很高兴你愿意为此教程做出贡献！

对于教程内容的错误，你可以在 Issue 中提出，或者直接 PR 提交你的修改！

## 版权

JKook Tutorial（即本教程，不包含引用自他人的内容）使用 CC BY-SA 4.0 许可协议。

访问 http://creativecommons.org/licenses/by-sa/4.0/ 以查看具体条款。

为了使本教程行文方便，文章中引用了一些来自他人的内容，已尽量在文章中列出出处，在此表示一并感激。

未列出出处的，找到正确的出处后，也可以向我们提交 Pull Request 以补充。

若您发现本教程引用的内容来自您，并且不希望本教程使用，请通过诸如电子邮件等方式联系我们，我们在核实后将予以移除。
