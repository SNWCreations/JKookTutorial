package snw.jkook.example;

import snw.jkook.command.JKookCommand;
import snw.jkook.message.component.MarkdownComponent;
import snw.jkook.plugin.BasePlugin;
import snw.jkook.scheduler.JKookRunnable;

import java.util.concurrent.TimeUnit;

public class Main extends BasePlugin {

    @Override
    public void onEnable() {
        new JKookCommand("notice")
                .executesUser((sender, arguments, message) -> {
                    new JKookRunnable() {
                        @Override
                        public void run() {
                            sender.sendPrivateMessage(new MarkdownComponent("I'm the task from the scheduler!"));
                        }
                    }.runTaskTimer(this, TimeUnit.MINUTES.toMillis(1), TimeUnit.MINUTES.toMillis(1));
                })
                .register(this);
    }
}
