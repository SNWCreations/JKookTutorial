# Chapter 2

本章介绍 JKook API 的配置系统。

希望上一章的代码在你的设备上已经正常运行了。

如果说，有人对你的 Hello World 插件产生了兴趣，但是TA想让你的插件输出点不一样的东西，如果要修改代码，岂不是每需要另一种话时就需要修改一次代码？太麻烦了！

而通过本章的学习，你将知道如何用配置文件使你的插件变得可配置。

首先，我们需要明确一些概念。

JKook 使用 YAML (1.1 版) 作为 `plugin.yml` 以及配置文件的格式。

YAML 是以键值对的形式存储数据的。这点和 JSON 很像，也很像 `java.util.Map` 。

如 `foo: bar` 中，foo 是键，bar是值。

以下为一个实例 (注释会解释这些值的类型):
```yml
a: 1 # int
b: 1.1 # double, 也可以是 float
c: # ConfigurationSection
  d: 1.1.1 # String (因为不是一个有效数字), 此配置项的路径是 c.d
e: true # boolean
f: ["a", "b", "c"] # List<String>
```
请注意: YAML 对缩进要求很严格。

YAML 的完整语法这里不再讲述。

当你能理解这些之后，就开始学习下面的内容吧。

请打开 `code/src/main/java/snw/jkook/example/Main.java` 文件，并找到第 1 处。

在这里，我们调用了 `saveDefaultConfig` 方法，这个方法将从插件的 JAR 包中释放名为 `config.yml` 的文件到插件的数据文件夹。数据文件夹的 `java.io.File` 实例可以通过插件的 `getDataFolder` 方法获得。

但是，若插件的数据文件夹中已经有了 `config.yml` ，则 `saveDefaultConfig` 将不会释放配置文件。

请注意: 若你的插件有配置文件，请一定只在 `onLoad` 的时候调用 `saveDefaultConfig` 方法，因为一个 JKook API 的实现会在 `onLoad` 之后才加载配置文件到内存，然后调用 `onEnable` 方法。

接着，转到第 2 处。

我们的重点是其中的参数。

`getConfig().getString("msg")` 意为从当前插件的配置中获取 "msg" 键的值并返回。

**注意，当 msg 键在配置文件中未被定义时，`getString` 方法将返回 `null` ，而不是 `null` 的字符串形式，也不是空字符串。**

当然，你可以用 `getString("msg", "")` ，这样，当 msg 键在配置文件中未被定义时，将返回 "" (即空字符串) 。其中，第二个参数称之为默认值。

纵观 `snw.jkook.config.ConfigurationSection` 接口，你会发现很多的形如 getXXX 的方法，如 `getInt`，`getDouble` 等。

它们都有两种重载。

第一种是单参数的，当获取不到，或者无法将存在的值解析为你想要的类型时返回一个默认值。(如 `getInt` 的一般默认值为 0，`getDouble` 的一般默认值为 0.0)

第二种即为需要自行提供默认值的。

另外，此接口也提供了一些方法，它们能返回 `Collection` 的子类的实例。如 `getXXXList` (具体的有 `getStringList`, `getIntegerList` 等) 。

既然 get 方法都有了，有没有 setXXX 的方法？

如果你看了第 3 处，你应该知道答案了。没有。

直接 `set(path, value)` 即可。其中，path 是配置项的名称，value 是将为此配置项赋予的新值，为 `Object` 类型。

如果想要删掉某个配置项，value 传入 `null` 即可。

最后，请看第 4 处。

这里我们调用了 `saveConfig` 方法，作用是保存内存中的配置数据到插件数据文件夹中的 `config.yml` 文件。

如果在插件运行期间，内存中的配置被修改，请务必在 `onDisable` 的时候调用 `saveConfig` 。否则被修改过的配置将丢失。当然，若你的配置不需要保存，可以不写。

---

现在聊聊运行这个示例的结果吧。

假设你是第一次运行这个示例。

首先，默认配置文件在 `onLoad` 时被释放，然后在 `onEnable` 前被加载，这时，配置项 `msg` 的值为 "Hello world!" 。

但在 `onEnable` 时，配置项 `msg` 的值被设为 `fine` 。

又因为 `onDisable` 时，调用了 `saveConfig` 以保存配置，故关闭 API 实现后，在本地的 `config.yml` 中，`msg` 配置项的值为 `fine` 。

---

本章我们讲解了:

* YAML 的基本语法
* ConfigurationSection 中的各种方法
* 加载与保存配置数据