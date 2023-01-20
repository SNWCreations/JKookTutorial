# Chapter 7

在这个世界上，每一分，每一秒，都在发生一些事情，传递这些事情"发生了"这个信息的东西我们称之为事件。

本章我们讲解 JKook API 的事件体系。以及如何监听它们。

**本章的代码基于 API 0.46 ，以下内容也基于 API 0.46。**

## 运行一次示例

如果你读过前两章，我不多说了，自己编译一下示例代码，跑一下。

这个示例插件的效果是: 当你在机器人能访问的频道中发送了消息后，机器人会回复你 `Hello world!` 。

## 总览包结构

所有由 JKook API 提供的事件均放在了 `snw.jkook.event` 包下。

先总览包结构。

```text
snw.jkook.event
|   Event
|   EventHandler
|   EventManager
|   Listener
|   TimedEvent (不存在于 API 0.37)
|   
+---channel
|       ChannelCreateEvent
|       ChannelDeleteEvent
|       ChannelEvent
|       ChannelInfoUpdateEvent
|       ChannelMessageDeleteEvent
|       ChannelMessageEvent
|       ChannelMessagePinEvent
|       ChannelMessageUnpinEvent
|       ChannelMessageUpdateEvent
|       
+---guild
|       GuildAddEmojiEvent
|       GuildBanUserEvent
|       GuildDeleteEvent
|       GuildEvent
|       GuildInfoUpdateEvent
|       GuildRemoveEmojiEvent
|       GuildUnbanUserEvent
|       GuildUpdateEmojiEvent
|       GuildUserNickNameUpdateEvent
|       
+---item
|       ItemConsumedEvent
|       ItemEvent
|       
+---pm
|       PrivateMessageDeleteEvent
|       PrivateMessageEvent
|       PrivateMessageReceivedEvent
|       PrivateMessageUpdateEvent
|       
+---role
|       RoleCreateEvent
|       RoleDeleteEvent
|       RoleEvent
|       RoleInfoUpdateEvent
|       
\---user
        UserAddReactionEvent
        UserClickButtonEvent
        UserEvent
        UserInfoUpdateEvent
        UserJoinGuildEvent
        UserJoinVoiceChannelEvent
        UserLeaveGuildEvent
        UserLeaveVoiceChannelEvent
        UserOfflineEvent
        UserOnlineEvent
        UserRemoveReactionEvent
```

## 重点类详解

### Event

其完整限定名为 `snw.jkook.event.Event` 。

所有的 JKook 事件均继承此类。

#### Event 与 TimedEvent

`TimedEvent` 是在更新的 JKook API 中加入的，是 `Event` 的子类。

我们将原本存放在 `Event` 对象中的时间戳转移到了 `TimedEvent` ，这么做可以方便开发者自定义事件。
* 自定义事件将在下文讲解。

_然而，在 API 0.37 没有 `TimedEvent` 。_

### EventManager

其完整限定名为 `snw.jkook.event.EventManager` 。

你可以通过此接口的实现注册事件监听器，以及发布一个事件。

### Listener

其完整限定名为 `snw.jkook.event.Listener` 。

它是一个接口，你需要在写了事件监听器的类中 `implements` 此接口。

此接口没有任何需要你实现的方法，它自己本就没有定义任何方法。

它的作用仅仅是标记，以及将你的监听器类与 `Object` 区分。

因此，像这样的空类是可以写的:
```java
public class MyListener implements Listener {
}
```

但它无意义。

### EventHandler

其完整限定名为 `snw.jkook.event.EventHandler` 。

它是一个注解，用于标记你的方法是一个用于处理事件的方法。

声明一个事件监听器可以这么写:
```java
public class MyListener implements Listener {
    @EventHandler
    public void onEvent(XXXEvent event) { // 将 XXXEvent 替换为一种具体的事件
        // code here
    }
}
```

写给从 Bukkit 迁移而来的开发者: JKook API 提供的所有事件均无法取消。不同于 Bukkit 优先于 Minecraft Server 所以可以修改事件，KOOK 的事件是在发生后才推送给机器人。也正因为这个，我们认为没有必要编写监听器优先级系统，故 `EventHandler` 没有 `priority` 属性。

## 监听事件

这才是重头戏。

有这么多的事件，那我们怎么知道它们发生了呢？

上文其实已经提了:
```java
public class MyListener implements Listener {
    @EventHandler
    public void onEvent(XXXEvent event) { // 将 XXXEvent 替换为一种具体的事件
        // code here
    }
}
```

但是只有这个还不够，我们还需要用 `EventManager#registerHandlers` 方法注册监听器。
* **写了监听器类一定要注册！**

最常见的注册方法莫过于:

```java
public class MyPlugin extends BasePlugin {

    @Override
    public void onEnable() {
        getCore().getEventManager().registerHandlers(this, new MyListener());
    }
}
```

**注意，监听的事件类型不能是抽象的（即 `abstract` 的）。**

因为篇幅原因，具体的事件意义请自行查阅 JKook API 文档或 [KOOK 开发者文档](https://developer.kookapp.cn/doc/event/event-introduction)。

## 自定义事件

我们支持自定义事件！

若你的插件中有一些有价值的事件，不妨通过我们的事件系统分享出来！

写一个直接继承 `Event` 的类即可。

然后在你的事件发生时，`new` 一个事件对象，然后通过 `EventManager#callEvent` 方法发布出去！

**注意：`callEvent` 方法是同步的。**
这意味着这个方法会在所有监听了你提交的事件的代码返回之后才返回，如果你只需要发布一个事件，而不需要关心后续，可以考虑用任务调度器发布事件，以提升代码性能。任务调度器的使用将在下章讲解。

---

本章我们讲解了 JKook API 事件系统的结构。

其实并没有什么深奥的，只是具体的事件有何内容需要你自行探索。
