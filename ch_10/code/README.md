# The Final Project

哦，你发现了这个 README 文件。

这是由我自己编写的，基于我对第 10 章的要求的理解编写的插件。

这个插件提供如下命令:
* /setscore @某人 分数 - 修改某人的分数
* /rank - 查询自己的分数
* /ranklist - 获取服务器的积分榜。仅展示前 10 个。
* /active - 将服务器的活跃频道设为发出命令时的消息所在的频道。活跃频道将被用于发送随机算式。

这个示例我使用了 2 天编写。

因为仅仅是为了教学用途，所以未考虑向多个服务器服务时的性能，所以如果你想直接部署它然后就提供服务的话，那还是算了吧...

我一度在编写求解随机算式的实现时遇到了困难，反复重写了多个版本的算法，但都不好用。

所以最终我使用了 Nashorn JavaScript Engine 完成求解随机算式的过程。lol

你已经自己完成了示例？看看我的代码吧！也许你还可以从中学到什么。

就这样吧！

--- SNWCreations, 2023/1/22
