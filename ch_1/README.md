# Chapter 1

本章要讲的内容很简单，旨在告诉你一个简单的 JKook 插件如何声明。

本章是对 [JKook Wiki - 插件格式规范](https://github.com/SNWCreations/JKook/wiki/Plugin-Format) 的更深入讲解，**示例代码有所不同**。

`code/src/main/resources/plugin.yml` 文件是插件元数据文件，用于描述插件详情。其结构详解可以在 JKook 插件格式规范 Wiki 页面 (链接见上文) 中找到，此处不再赘述。

---

请打开 `code/src/main/java/snw/jkook/example/Main.java` 文件。

请看第 1 处。

`extends BasePlugin` 表明 Main 类继承 `BasePlugin` 类，而 `BasePlugin` 是 `Plugin` 接口类的一个可用实现。

**注意，plugin.yml 中指定的主类必须是 `Plugin` 类 (不绝对是 `BasePlugin` 类，因为其只是 `Plugin` 类的一种实现) 的子类，否则插件无效。**

再看第 2 处。

onLoad 方法在插件被加载后，启用前调用。

通过覆盖此方法即可在启用插件进行一些初始化操作，如释放默认配置。

**注意，默认配置若在 onEnable 阶段释放将无法被加载！**

接着，转到第 3 处。

若 onEnable 方法被调用，则意味着你的插件被启用了，在这个阶段，可以进行诸如加载配置，启动你的服务等操作。

**注意，若你的代码在以上两个阶段抛出异常，则插件将被禁用。**

但是，当你的插件真的出现了无法处理的异常，让你的插件被禁用无疑是最安全的。

最后，看第 4 处。

当 onDisable 方法被调用时，意味着你该关闭你的服务了。

在这个阶段，请关闭你的所有服务，清理不再使用的数据。

**注意: onDisable 被调用并不意味着你的插件不会再被启用。有可能在一个 JVM 中，你的插件会被启用/禁用多次。_对插件的管理是由 `PluginManager` 接口实现的。_**

---

至此，本章内容结束了。

本章我们了解了 JKook 插件的声明。

如果你准备好了，进入下一章吧！该聊聊命令系统了。
