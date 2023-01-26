# Chapter 9

_本章可以选择性阅读，你可以直接跳到下一章。_

本章基于 API 0.46 编写。

---

首先，感谢你读到了这里。

在前几章中，我们就算是已经讲解了 95% 的 JKook API 内容了。

而本章，我们会讲解一些进阶内容。

## Unsafe

其完全限定名为 `snw.jkook.Unsafe` 。

这个接口的实现提供了一些不安全的操作，虽然不安全，但是用好它们可以帮助你编码。

其实 `Unsafe` 我们早在 [CustomEmoji 简述 - 第 4 章](../ch_4/README.md#CustomEmoji) 中就有提到了。

那时，我们用了 `Unsafe#getCustomEmoji` 方法，所以在这里就不再讲解它了，需要的可以去上面的链接处看看。

而现在，让我们再看这个接口。

其实这个接口的内容不多，也就是提供了几个构造实体类的方法。

它们分别是:
* `getTextChannelMessage` - 通过提供的 ID 构造一个 `TextChannelMessage` 并返回
* `getPrivateMessage` - 通过提供的 ID 构造一个 `PrivateMessage` 并返回
* `getEmoji` - 通过提供的 ID 构造一个 `CustomEmoji` 并返回
* `getGame` - 通过提供的 ID 构造一个 `Game` 并返回

**注意：**
* 以上列出的方法的返回结果中，除了 `getId` 方法，所有以 `get` 开头的方法均是不可用的，它们不能给出正确的数据。
* 以上列出的方法均不会检查它们得到的参数。

一些修改其属性的方法也许可用。

我们推荐你在使用此接口时参照 JKook API 实现的相应源代码看。

## 配置系统中的序列化

这也许本应该在第 2 章就写了。

但是因为这是进阶内容，所以放在了这章。

本章的示例代码就是为此准备的。你可以先运行一次，看看它生成的 `config.yml` ，然后在阅读下文的过程中阅读它的源代码。

这里讲解 `snw.jkook.config.file.serialization` 包。

这个包提供了一个将你的数据结构更方便地序列化的途径。

我们将依次讲解以下类:
* ConfigurationSerializable
* ConfigurationSerialization
* SerializableAs
* DelegateDeserialization

### ConfigurationSerializable

其完整限定名为 `snw.jkook.config.file.serialization.ConfigurationSerializable` 。

实现它的类将被 JKook 序列化系统支持。从而使其可以在保存配置数据时按序列化系统的逻辑序列化，也可以正确地反序列化。

实现它分三步:
1. 创建一个类(`class`)
2. 使其实现 `ConfigurationSerializable` 接口及其声明的 `serialize` 方法。
3. 使这个类满足以下三个条件中的一个:
* 有一个接受 `Map<String, Object>` 的构造方法
* 有一个静态的（`static`的），接受 `Map<String, Object>` 的，名字为 `deserialize` 的方法。
* 有一个静态的（`static`的），接受 `Map<String, Object>` 的，名字为 `valueOf` 的方法。

### ConfigurationSerialization

其完整限定名为 `snw.jkook.config.file.serialization.ConfigurationSerialization` 。

这个类用于注册/注销 `ConfigurationSerializable` 接口的实现。

在按照上文列举的三步创建了一个 `ConfigurationSerializable` 的实现后，你还需要让 JKook API 知道。

直接通过向 `ConfigurationSerialization#registerClass` 方法传递你的 `ConfigurationSerializable` 实现类的 `Class` 对象即可。

就像这样:
```java
ConfigurationSerialization.registerClass(DataObj.class);
```

在这个示例中，`DataObj` 类是一个 `ConfigurationSerializable` 的实现。

### DelegateDeserialization

其完整限定名为 `snw.jkook.config.file.serialization.DelegateDeserialization` 。

这是一个注解，用于在一个 `ConfigurationSerializable` 接口的实现类上指定另一个 `ConfigurationSerializable` 接口的实现类，让被指定的类完成自己的反序列化。

比如在示例代码中，`AnotherDataObj` 类就指定了 `DataObj` 类完成自己的反序列化。

而 `DataObj` 类的反序列化方法是这么写的:
```java
public static ConfigurationSerializable deserialize(Map<String, Object> data) {
    String name = data.get("name").toString();
    if (name.startsWith("another")) { // 专门用于匹配 AnotherDataObj 的条件
        return new AnotherDataObj(name); // 如果成立，构造 AnotherDataObj 的实例，而不是 DataObj
    }
    return new DataObj(name);
}
```

这个注解的好处是可以避免写大量的 `deserialize` 方法，只需要按照特定条件去检查即可。

但过量使用容易导致被指定的类的反序列化方法太过复杂。

而且，严格意义上，这个注解使代码违背了 "单一职责" 原则。
* "单一职责" 原则即一段代码只做一种事情。

按需使用。

### SerializableAs

其完整限定名为 `snw.jkook.config.file.serialization.SerializableAs` 。

这也是一个注解。

它接受一个 `String` 作为它的 `value` ，表示在序列化时使用给定的名称作为它的标识。

举个例子:
```yml
data: # 数据的名称
  ==: ClassName # SerializableAs 决定这里的值
  foo: bar # 具体的数据
```

如果要使用这个注解，提供的名称必须独一无二，比如你可以在具体的名称前加上插件的名称（形如 `MyPluginThing`的形式）。

## 一次性 Listener

在前面的 [第 7 章](../ch_7/README.md) 中，关于 `EventManager` 其实有一个方法没讲。

`EventManager#unregisterHandlers` 。

这个方法会使传入的 `Listener` 实例不再收到事件。

看个例子:
```java
public class ExecuteOnceListener implements Listener {
    @EventHandler
    public void onMessage(ChannelMessageEvent event) {
        Plugin plugin; // 你的插件的实例
        event.getMessage().reply("Execute once!");
        plugin.getCore().getEventManager().unregisterHandlers(this);
    }
}
```

这就是一个最简单的一次性 `Listener` 的实现。

当然，你也可以让 `Listener` 在一个特定条件时失效，发挥你的想象力！
