package snw.jkook.example;

import snw.jkook.JKook;
import snw.jkook.command.JKookCommand;
import snw.jkook.entity.CustomEmoji;
import snw.jkook.entity.Guild;
import snw.jkook.entity.channel.TextChannel;
import snw.jkook.message.TextChannelMessage;
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

                    if (message instanceof TextChannelMessage) { // if this command was executed in a channel
                        replyContent.append("\n"); // ignore this please, just for better look

                        TextChannel channel = ((TextChannelMessage) message).getChannel();
                        Guild guild = channel.getGuild();
                        String guildName = guild.getName();
                        String guildId = guild.getId();
                        String channelName = channel.getName();
                        String channelId = channel.getId();
                        replyContent.append("此消息所在的服务器的名称: ").append(guildName).append("\n");
                        replyContent.append("此消息所在的服务器的 ID: ").append(guildId).append("\n");
                        replyContent.append("此消息所在的频道的名称: ").append(channelName).append("\n");
                        replyContent.append("此消息所在的频道的 ID: ").append(channelId).append("\n");
                    }

                    message.reply(new MarkdownComponent(replyContent.toString()));
                })
                .register();
    }

}
