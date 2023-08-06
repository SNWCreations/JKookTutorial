# Chapter 8

本章我们讲解 JKook API 中的任务调度系统。

## 运行一次示例

是的，本章有示例代码。

编译，运行，看看效果？

效果是: 在你发送 `/notice` 命令后，每经过 1 分钟，机器人会向你发送一条私信，内容是 `I'm the task in the scheduler!` 。

## 内容详情

JKook API 的任务调度系统位于 `snw.jkook.scheduler` 包下。

只有三个类，所以就不写包结构了。

它们分别是 `Scheduler`，`Task` 和 `JKookRunnable` 。

## Scheduler

其完整限定名为 `snw.jkook.scheduler.Scheduler` 。

这是整个 JKook 任务调度系统的核心。表示一个任务调度器。

所有提交给调度器的任务会统一在一个线程池中运行。

任务调度器提供如下方法:
* `runTask` - 让任务在线程池中立刻运行
* `runTaskLater` - 让任务在一定时间后才运行
* `runTaskTimer` - 让任务反复以一段时间为间隔运行，直到其被取消
* `isScheduled` - 接受一个任务 ID（类型为 `int`），然后检查是否有一个任务与之对应，有则返回 `true` ，无则返回 `false`
* `cancelTask` - 接受一个任务 ID（类型为 `int`），然后取消与其对应的任务
* `cancelTasks` - 取消由一个插件预定的所有任务

所有发布任务的方法都需要 `Plugin` 实例。
* 在 API 0.37 中，`runTask` 方法不需要 `Plugin` 实例，但是在高版本中需要，还请注意。

**这里提供的所有 `runTaskXXX` 方法的时间单位为毫秒。下文将要提及的 `JKookRunnable` 同理。**

## Task

其完整限定名为 `snw.jkook.scheduler.Task` 。

表示一个任务。

它提供如下方法:
* `getPlugin` - 获取预定了它的插件
* `cancel` - 取消它，但是当其 `isCancelled` 方法返回 `true` 时，抛出 `IllegalStateException` 异常。
* `isCancelled` - 当此任务已经被取消时返回 `true`
* `isExecuted` - 当此任务已被执行过一次时返回 `true`
* `getTaskId` - 获取此任务的 ID

## JKookRunnable

写给 Bukkit 开发者: 这个名字很眼熟？是的，它复刻了 `BukkitRunnable` ！

其完整限定名为 `snw.jkook.scheduler.JKookRunnable` 。

它提供了一个更便于向任务调度器发布任务的方法。

它继承自 `java.lang.Runnable` ，但是其 `run` 方法需要你自行实现————这也本就是你使用它时应该做的。

它一比一复刻了 `Scheduler` 接口中的部分方法。

它提供如下方法:
* `runTask`
* `runTaskLater`
* `runTaskTimer`

和任务调度器一样，每一个方法都需要 `Plugin` 实例。

它可以取消自己。使用 `cancel` 方法即可。因此，即使这个任务使用 `runTaskTimer` 方法发布，在满足某种条件时你可以手动调用 `cancel` 方法。

