# 从 API 0.37 迁移到 API 0.43

最新的版本有最新的修补与功能。

这句话对 JKook API 也适用。

虽然 API 0.37 是 LTS 的，但是我们不会永远专注于 API 0.37 ，我们把开发资源优先提供给更新的版本。

而且，迁移能让你得到更多的功能。

## 为什么会这样？

JKook API 有一条设计原则: 尽量向下兼容。

但是，在 API 0.38 中发生了与这条原则不符的事情: 我们重构了命令系统，加入了一些新功能，因此推翻了旧代码。

所以你在 API 0.37 上编写的命令直接拿到 API 0.43 的实现上是无法运行的。

## 两个 API 版本之间不兼容的内容

1. `UserAddReactionEvent#getEmoji` 方法在新版本中被移除了，转而增加了 `getReaction` 方法。

2. `Guild#getUsers` 的方法签名被修改了，除了 `keyword` 参数，所有其他的是基础数据类型的参数类型均被改为其包装器。(如 `int` -> `Integer`)

3. `JKookCommand#register` 方法在新版本中需要 `Plugin` 作为参数，但是旧版本不需要。

4. `CommandExecutor`，`UserCommandExecutor`，`ConsoleCommandExecutor` 中 `onCommand` 方法的 `arguments` 参数类型从 `String[]` 变成了 `Object[]` ，关于此变动的详细讲解，见本教程[第 6 章 - 参数解析系统](ch_6/README.md#参数解析系统)。

5. 移除了糟糕的 `snw.jkook.event.HandlerList` ，自定义事件不再需要写 `private static final HandlerList handlers = new HandlerList();` 以及 `public static HandlerList getHandlers()` 了。


## 如何迁移？

对于第 1 条，检查你的事件监听代码中有关原 `getEmoji()` 方法的调用，用 `getReaction().getEmoji()` 替代，即可。

因为 Java 有自动装/拆箱的特性，第 2 条在一般情况下可以安全忽略。
* 反射调用的话需要修改传递给 `Class#getMethod` 方法的参数类型的 `Class` 。~~(但为什么要这么做呢？API 0.37 又不是没有这个方法)~~

第 5 条，清理掉所有有关 `HandlerList` 的引用即可。

### 命令部分

先检查你的代码，若命令在插件主类中注册，则将所有的 `register()` 替换为 `register(this)` 。

反之，将插件实例暴露，然后向 `register` 方法传插件实例的引用即可。

然后，有两个方案。

#### 只做兼容

在你完成上文的修改后，若你的 `JKookCommand` 不打算使用新 API 中的参数解析系统，你可以使用以下方案。

你可以按如下方法修改你的命令执行器:

假设你的原有代码如下:

```java
public class Command implements UserCommandExecutor {
    
    @Override
    public void onCommand(CommandSender sender, String[] arguments, @Nullable Message message) {
        // command code
    }

}
```

可以改成:

```java
public class Command implements UserCommandExecutor {

    @Override
    public void onCommand(CommandSender sender, Object[] arguments, @Nullable Message message) {
        onCommand0(sender, toStringArray(arguments), message);
    }
    
    private void onCommand0(CommandSender sender, String[] arguments, @Nullable Message message) {
        // command code
    }

    // 此方法可以提取到一个单独的类，它只是实用方法
    public static String[] toStringArray(Object[] array) {
        String[] result = new String[array.length];
        int i = 0;
        for (Object obj : array) {
            result[i++] = obj.toString();
        }
        return result;
    }
}
```

为什么可以这么改？

在我们设计新的命令系统的时候，我们考虑到了你可能只想简单的兼容高版本。

特此，我们在开发新的命令系统的时候，加入了一个特性: 若你从未使用新版本的参数系统，则直接将 `String[]` 作为 `Object[]` 传递给你的命令。

#### 升级命令

既然迁移到了新版本，为什么不用新的命令系统？

关于新的参数系统，请见本教程[第 4 章 - 参数解析系统](ch_6/README.md#参数解析系统)。

举个例子。

```java
new JKookCommand("cmd")
        .executesUser(new Command())
        .register();
```

```java
public class Command implements UserCommandExecutor {

    @Override
    public void onCommand(CommandSender sender, String[] arguments, @Nullable Message message) {
        if (arguments.length == 1) {
            int a = Integer.parseInt(arguments[0]);
        }
    }

}
```

可以改为:

```java
new JKookCommand("cmd")
        .addArgument(int.class)
        .executeUser(new Command())
        .register();
```

```java
public class Command implements UserCommandExecutor {

    @Override
    public void onCommand(CommandSender sender, Object[] arguments, @Nullable Message message) {
        int a = (int) arguments[0];
    }

}
```
