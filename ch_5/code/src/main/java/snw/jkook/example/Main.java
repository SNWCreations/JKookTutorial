package snw.jkook.example;

import snw.jkook.command.JKookCommand;
import snw.jkook.entity.Guild;
import snw.jkook.entity.channel.TextChannel;
import snw.jkook.message.PrivateMessage;
import snw.jkook.message.TextChannelMessage;
import snw.jkook.message.component.MarkdownComponent;
import snw.jkook.plugin.BasePlugin;

public class Main extends BasePlugin {

    @Override
    public void onEnable() {
        new JKookCommand("msginfo")
                .executesUser((sender, arguments, message) -> {
                    if (message != null) {
                        StringBuilder contentBuilder = new StringBuilder();
                        String messageId = message.getId();
                        long timestamp = message.getTimeStamp();
                        contentBuilder.append("消息 ID: ").append(messageId).append("\n");
                        contentBuilder.append("消息发送时的时间戳: ").append(timestamp);

                        if (message instanceof TextChannelMessage) { // if this command was executed in a channel
                            contentBuilder.append("\n"); // ignore this please, just for better look

                            TextChannel channel = ((TextChannelMessage) message).getChannel();
                            Guild guild = channel.getGuild();
                            String guildName = guild.getName();
                            String guildId = guild.getId();
                            String channelName = channel.getName();
                            String channelId = channel.getId();
                            contentBuilder.append("此消息所在的服务器的名称: ").append(guildName).append("\n");
                            contentBuilder.append("此消息所在的服务器的 ID: ").append(guildId).append("\n");
                            contentBuilder.append("此消息所在的频道的名称: ").append(channelName).append("\n");
                            contentBuilder.append("此消息所在的频道的 ID: ").append(channelId);
                        }

                        message.reply(new MarkdownComponent(contentBuilder.toString()));
                    }
                })
                .register(this);

        // This command can just delete your message.
        new JKookCommand("deleteme")
                .executesUser((sender, arguments, message) -> {
                    if (message instanceof TextChannelMessage) {
                        message.delete();
                    } else if (message instanceof PrivateMessage) { // You can't delete messages in private chat
                        message.reply(new MarkdownComponent("我无法删除你的消息。lol"));
                    }
                })
                .register(this);
    }

}
