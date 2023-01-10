package snw.jkook.example;

import snw.jkook.command.JKookCommand;
import snw.jkook.message.component.MarkdownComponent;
import snw.jkook.plugin.BasePlugin;

public class Main extends BasePlugin {

    @Override
    public void onLoad() {
        new JKookCommand("eg")
            .setExecutor((sender, args, msg) -> {
                msg.reply(new MarkdownComponent("Hi!"));
            })
            .register();
    }

}
