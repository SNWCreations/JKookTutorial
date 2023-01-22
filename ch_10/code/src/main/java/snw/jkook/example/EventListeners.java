package snw.jkook.example;

import snw.jkook.entity.channel.TextChannel;
import snw.jkook.event.EventHandler;
import snw.jkook.event.Listener;
import snw.jkook.event.channel.ChannelMessageEvent;
import snw.jkook.event.role.RoleInfoUpdateEvent;
import snw.jkook.event.user.UserJoinGuildEvent;
import snw.jkook.event.user.UserLeaveGuildEvent;
import snw.jkook.example.events.LevelUpEvent;
import snw.jkook.message.component.MarkdownComponent;

public class EventListeners implements Listener {

    @EventHandler
    public void onJoin(UserJoinGuildEvent event) {
        event.getUser().sendPrivateMessage(new MarkdownComponent("欢迎！"));
    }

    @EventHandler
    public void onLeave(UserLeaveGuildEvent event) {
        ScoreboardStorage.getScoreboard(event.getGuild()).reset(event.getUser());
    }

    @EventHandler
    public void onMessage(ChannelMessageEvent event) {
        if (event.getMessage().getSender() == Main.getInstance().getCore().getUser()) {
            return; // do not record self
        }
        ScoreboardStorage.getScoreboard(event.getChannel().getGuild())
                .calc(event.getMessage().getSender(), i -> i + 1);
    }

    @EventHandler
    public void onRoleUpdate(RoleInfoUpdateEvent event) {
        PermissionStorage.update(event.getRole());
    }

    // The custom event!
    @EventHandler
    public void onLevelUp(LevelUpEvent event) {
        TextChannel textChannel = GuildChannelStorage.get(event.getGuild());
        if (textChannel == null) {
            return;
        }
        textChannel.sendComponent(
                new MarkdownComponent(
                        "恭喜 " + "(met)" + event.getUser().getId() + "(met)" + " 升至 " + event.getNow() + " 级！"
                )
        );
    }
}
