package snw.jkook.example;

import snw.jkook.plugin.BasePlugin;

public class Main extends BasePlugin {

    @Override
    public void onEnable() /* 2 */ {
        new JKookCommand("eg")
            // Old register method from API 0.37:
            // .setExecutor((sender, args, msg) -> {
            //     msg.reply("Hi!");
            // })
            .setUserExecutor((user, args, msg) -> {
                msg.reply("Hi!");
            })
            .register(this);
    }
}
