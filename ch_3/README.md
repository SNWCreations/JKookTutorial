# Chapter 3

命令是 Bot 和用户的重要交流渠道。

其实所谓命令，只是一种规范化的消息。

本章，我们将介绍 JKook API 的命令系统用法。

本章基于两个 API 大版本编写: 0.37 LTS, 0.42 LTS 。

两个版本之间不同的部分将在下文提及。

本章的代码示例基于 API 0.42 编写。下文中的代码亦是如此。

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

## JKookCommand

这是 JKook API 命令系统的核心类。

表示一个命令。

是建造者模式。

在 JKook API 命令系统中，一个命令可以拥有如下属性：
* 名称
* 前缀
* 别称
* 简介
* 帮助信息
* 执行器
* 参数类型 (自 API 0.38 开始)

下文将详细讲解上文所列出的属性。

### 命令名称

命令名称通过 JKookCommand 的构造方法指定，唯一且不可更改。

**命令名称、别称、前缀不可以有空格。前缀可以是空字符串，其他两个不能。**

JKookCommand 有如下的多种构造方法:
```java
public final class JKookCommand {
    
    // 最简单的构造器，只需要一个名称。
    // 前缀此时使用默认值 "/"
    public JKookCommand(String rootName) {
    }

    // 在提供命令名称的同时提供一个 char 作为此命令的前缀。
    // 此时命令的前缀只有给定的 char
    public JKookCommand(String rootName, char prefix) {
    }

    // 与 JKookCommand(String, char) 类似，但是因为第二个参数是 String
    // 所以你可以指定一个无空格的字符串作为命令前缀
    public JKookCommand(String rootName, String prefix) {
    }
    
    // 主构造方法
    // 因为第二个参数是 Collection<String> ，所以你可以提供多个字符串作为命令前缀
    public JKookCommand(String rootName, Collection<String> prefixes) {
    }
    
    // ...
    // 更多方法已忽略
}
```

### 前缀

前缀不仅可以通过构造方法指定，也可以通过 `JKookCommand#addPrefix` 方法添加。

对于以下两段代码:

```java
new JKookCommand("eg", ".")
        // more command code
```

```java
new JKookCommand("eg")
        .addPrefix(".")
        // more building code
```

两者的区别是，前者的前缀只有 "." ，而后者同时有 "/" 和 "."。

所以若不希望使用 "/" 作为命令前缀，则需要 `new` 命令对象时就在构造方法指定所有的命令前缀。

### 别称

命令别称的作用正如其名，对命令的另一种称呼，比如:

```java
new JKookCommand("eg")
        .addAlias("ab")
        // more building code
```

以上代码表示的命令既可以通过发送 `/eg` 执行，也可以通过发送 `/ab` 执行。

命令别称可以通过 `JKookCommand#addAlias` 方法添加。

### 简介 & 帮助信息

简介和帮助信息不同的是，简介应该在 /help 命令 (由 JKook API 的实现提供) 的结果中展示，并且应该尽可能的简短。

帮助信息应该在用户使用 /help 命令的同时指定了此命令的名称 (如 /help eg 即 eg 命令被指定) 时展示。

### 注册

**在你对命令对象完成各项设置后，请务必调用 `JKookCommand#register` 方法！**

注意:
* 在 API 0.37 中，`register` 方法不需要参数。
* **在 API 0.42 中，`register` 方法需要一个 `Plugin` 作为命令的所有者。**
* 命令的属性在注册后不能更改。

---

执行器将在下文详细讲解，详见 [执行器](#执行器) 一节。

一个完整的 JKookCommand 的使用实例可以在本章的示例代码中找到。

## 执行器

JKook API 中有 3 种命令执行器。

我们按时间顺序来讲。

**建议先读完下文的 `CommandExecutor` 一节再读另外两节。**

### CommandExecutor

最早的 JKook API 命令系统使用 `CommandExecutor` 类作为命令执行器。

它的声明如下:

```java
public interface CommandExecutor {

    void onCommand(CommandSender sender, Object[] arguments, @Nullable Message message);

}
```

提供了命令执行者，参数，以及消息对象。

`CommandSender` 的直接子类有 `User` 和 `ConsoleCommandSender` ，前者表示用户，后者表示控制台。

**注意，`arguments` 在 API 0.37 和 API 0.42 中类型不一样。**

* 在 API 0.37 中，它是 `String[]` 类型。
* 在 API 0.42 中，它是 `Object[]` 类型。因为引入了参数解析系统。见下文 [参数解析系统](#参数解析系统) 一节。 _其实自 API 0.38 开始就已经是 `Object[]` 类型了，但是本教程不讲解非 LTS 版本的 API 。_

消息对象在命令执行者是 `User` 时一般不会是 `null` 。

**但是有个例外，命令在通过将 `User` 传给了 `CommandManager#executeCommand` 方法执行时，得到的 `message` 参数的值为 `null` 。**

为一个命令对象设置 `CommandExecutor` 可以调用 `JKookCommand#setExecutor` 方法。

---

然而，这个执行器设计有一个问题，若一个命令执行器对 `User` 和 `ConsoleCommandSender` 有不同的处理代码，则需要先做 `instanceof` 检查，这样的设计不利于维护。

最重要的是，很多时候，命令都是直接由 KOOK 用户执行的，仅控制台可用的命令较少。

所以，为了解决这个问题，我们引入了 `UserCommandExecutor` 和 `ConsoleCommandExecutor` ，下文将详细讲解。

### UserCommandExecutor

`UserCommandExecutor` 接口与上文的 `CommandExecutor` 接口的区别主要就是 `sender` 的类型是 `User` 。`arguments`参数的差异见上文。

为一个命令对象设置 `UserCommandExecutor` 可以调用 `JKookCommand#executesUser` 方法。

### ConsoleCommandExecutor

`ConsoleCommandExecutor` 接口与上文的 `CommandExecutor` 接口的区别主要就是 `sender` 的类型是 `ConsoleCommandSender` 。`arguments`参数的差异见上文。

为一个命令对象设置 `ConsoleCommandExecutor` 可以调用 `JKookCommand#executesConsole` 方法。

## 参数解析系统

**本节的内容需要 JKook API 版本为 0.38+ 。**

TODO