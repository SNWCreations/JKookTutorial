package snw.jkook.example;

import snw.jkook.plugin.BasePlugin;

public class Main extends BasePlugin {

    @Override
    public void onLoad() /* 2 */ {
        new JKookCommand("eg")
            .setExecutor((sender, args, msg) -> {
                msg.reply("Hi!");
            })
            .register();
    }

    @Override
    public void onEnable() /* 3 */ {
        getLogger().info("Hello world plugin enabled!");
    }

    @Override
    public void onDisable() /* 4 */ {
        getLogger().info("Hello world plugin disabled!");
    }

}
