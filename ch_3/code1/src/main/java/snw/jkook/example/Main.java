package snw.jkook.example;

import snw.jkook.plugin.BasePlugin;

public class Main extends BasePlugin {

    @Override
    public void onLoad() {
        new JKookCommand("eg")
            .setExecutor((sender, args, msg) -> {
                msg.reply("Hi!");
            })
            .register();
    }

}
