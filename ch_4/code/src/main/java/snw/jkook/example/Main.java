package snw.jkook.example;

import snw.jkook.JKook;
import snw.jkook.command.JKookCommand;
import snw.jkook.entity.CustomEmoji;
import snw.jkook.message.component.MarkdownComponent;
import snw.jkook.plugin.BasePlugin;

public class Main extends BasePlugin {

    @Override
    public void onEnable() {
        new JKookCommand("info")
                .executesUser((sender, arguments, message) -> { // sender is a User in this case
                    if (message == null) return; // ignore CommandManager#executeCommand calls

                    CustomEmoji smile = JKook.getCore().getUnsafe().getEmoji("\ud83d\ude04");
                    message.sendReaction(smile);

                    StringBuilder replyContent = new StringBuilder();

                    String name = sender.getName(); // sender's name
                    String userId = sender.getId(); // sender's ID
                    String fullName = name + "#" + sender.getIdentifyNumber(); // sender's full name

                    replyContent.append("你的名字: ").append(name).append("\n");
                    replyContent.append("你的用户 ID: ").append(userId).append("\n");
                    replyContent.append("你的完整用户名称: ").append(fullName);

                    message.reply(new MarkdownComponent(replyContent.toString()));
                })
                .register();
    }

}
