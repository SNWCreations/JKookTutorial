package snw.jkook.example;

import snw.jkook.command.JKookCommand;
import snw.jkook.message.component.MarkdownComponent;
import snw.jkook.plugin.BasePlugin;

public class Main extends BasePlugin {

    @Override
    public void onEnable() {
        new JKookCommand("eg")
            // Old register method from API 0.37:
            // .setExecutor((sender, args, msg) -> {
            //     msg.reply("Hi!");
            // })
            .executesUser((user, args, msg) -> {
                msg.reply(new MarkdownComponent("Hi!"));
            })
            .register(this);
    }

}
