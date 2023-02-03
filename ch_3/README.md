# Chapter 3

_注意: 为了更好的展示 `Plugin` 接口的内容，本章示例代码使用 **JKook API 0.48** 。_

相信经过前两章的学习，你已经对 JKook 框架有了初步的认识。

现在，让我们回过头来，再看看 `Plugin` 接口。

当然，也会顺带讲一点与其相关的类。

但是，在此之前，先编译一下示例代码，然后拿着工件去一个 JKook API 的实现跑一下！

注意观察控制台。

跑完之后请打开 `code/src/main/java/snw/jkook/example/Main.java` 文件，然后我们再详解。

---

开篇而来的是... `getLogger()` 方法，老朋友了，[第 1 章](../ch_1/README.md) 就讲过了。不多讲了。

第 1 处。老朋友了，`saveDefaultConfig` 方法我们已经在 [第 2 章](../ch_2/README.md) 里讲过。也不多讲了。

第 2 处。

`Plugin#getDescription` 方法返回的是此插件的描述信息对象(即 `snw.jkook.plugin.PluginDescription` 类的实例)。

其本质上是对 `plugin.yml` 的封装。

---

接下来讲点与 IO 有点关系的方法。

第 3 处。

`Plugin#getFile` 方法返回插件所在的文件对象。

比如你可以借此方法实现一次性插件？？？

_~~这个想法很奇怪。我也不知道为什么我会想到这个。~~_

```java
public class Main extends BasePlugin {
    @Override
    public void onEnable() {
        getFile().deleteOnExit();
    }
}
```


第 4 处。

`Plugin#getResource` 方法尝试从插件所在的文件对象中获取一个指定的资源的输入流。

其本质是对 `ClassLoader#getResource` 方法的二次封装。

你可以通过此方法配合 IO 流实现从插件文件中读取数据，从而让一些数据不需要在代码中硬编码。

第 5 处。

`Plugin#saveResource` 方法是对 `Plugin#getResource` 方法的又一层包装。

此方法可以让你将插件文件中的数据文件"解压"到插件的数据文件夹。

第 6 处。

`Plugin#getDataFolder` 方法可以让你获得插件数据文件夹的文件对象。

在 KookBC 中，插件数据文件夹一般在 KookBC 所在的目录下的 `plugins` 目录中，以插件 `plugin.yml` 的 `name` 项的值命名。

若你的插件数据需要保存，则请保存在这个文件夹下。

---

其实还有少数几个方法未详解，但因为不常用，故不作详细讲解了，可以自行翻阅 Javadoc 。
* 我也不能什么都讲，有一些东西需要自己探索。
