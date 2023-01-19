# Chapter 5

在你阅读上一章的时候，你应该注意到了我们提到了几次 `Message` ，那就是我们对 KOOK 消息的抽象接口。

本章，我们来讲解 JKook API 的消息体系。

## 运行一次示例

本章的示例代码基于 API 0.37 。

先运行本章的示例代码。

本章示例代码提供了两个命令，分别是 `/msginfo` 和 `/deleteme` 。

前者的效果是: 如果命令在文字频道中执行，会顺带提供频道 ID ，频道名称，频道所在服务器 ID ，频道所在服务器的名称。

后者的效果是: 如果命令在文字频道中执行，它会删除你发送的 `/deleteme` 消息。仅此而已。

在阅读下文的过程中阅读示例代码，你将渐渐理解它。
* _~~这句话好像在哪听过一次？~~_
* _~~是的，如果你读过上一章，你会发现本节与上一章关于示例代码的介绍出奇地像，因为我是照抄的。~~_

## 消息概述

消息是由 KOOK 用户发送的，包含了一些内容的实体。

**消息是一种实体。**
没有放在 `snw.jkook.entity` 包的原因在上一章已经讲过了。

JKook API 消息体系中的各种类基本统一放在 `snw.jkook.message` 包下。

## 总览包结构

先总览包结构。

```text
snw.jkook.message
|   Message
|   PrivateMessage
|   TextChannelMessage
|   
\---component
    |   BaseComponent
    |   FileComponent
    |   MarkdownComponent
    |   TextComponent
    |   
    \---card
        |   CardBuilder
        |   CardComponent
        |   CardScopeElement
        |   MultipleCardComponent
        |   Size
        |   Theme
        |   
        +---element
        |       BaseElement
        |       ButtonElement
        |       ImageElement
        |       InteractElement
        |       MarkdownElement
        |       PlainTextElement
        |       
        +---module
        |       ActionGroupModule
        |       BaseModule
        |       ContainerModule
        |       ContextModule
        |       CountdownModule
        |       DividerModule
        |       FileModule
        |       HeaderModule
        |       ImageGroupModule
        |       InviteModule
        |       package-info
        |       SectionModule
        |       
        \---structure
                BaseStructure
                Paragraph
```

根据以上结构，本章我们分为三大部分：

消息对象，消息组件，CardMessage（卡片消息）。

## 获取消息对象

最常见的方法是通过 `ChannelMessageEvent`（用户在文字频道发送消息的事件）或 `PrivateMessageReceivedEvent`（用户给自己发送私信的事件）的 `getMessage` 方法获得消息对象。
* 事件系统将在第 7 章讲解。

或者通过命令系统，`CommandExecutor` 和 `UserCommandExecutor` 接口的 `onCommand` 方法均提供了 `Message` 对象，表示导致命令被执行的消息对象。

## 消息对象

消息对象目前有两种：`TextChannelMessage` 和 `PrivateMessage` 。它们有一个共同的父类是 `Message` 接口。

### Message

完整限定名为 `snw.jkook.message.Message` 。

作为对一个消息的基本抽象，此接口可以获取的内容比较多。其子类主要是提供一些功能扩展。

通过 `Message#getId` 方法可以获得此消息的 ID 。

消息 ID 是一个 UUID 。

通过 `Message#getSender` 方法可以获得此消息的发送者。

通过 `Message#getTimeStamp` 方法可以获得此消息被发送时的时间戳。

通过 `Message#getComponent` 方法可以获得此消息包含的消息组件。通过消息组件对象，你可以得知用户发送的内容。消息组件相关内容将在下文讲解。

更新一条消息的内容可以使用 `Message#setComponent` 方法，但只支持更新 KMarkdown 消息以及卡片消息。
**只能更新机器人自己发出的消息。**

删除一条消息可以使用 `Message#delete` 方法。在私信中只能删除自己的消息，在文字频道中删除其他人的消息需要自己有消息管理权限。

在一条消息被删除后，对其对应的消息对象进行更新等操作将失败，因此，已被删除的消息的消息对象不再具有可用性。

## 消息组件

TODO

## CardMessage

TODO
