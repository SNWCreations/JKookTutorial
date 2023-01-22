package snw.jkook.example.commands;

import snw.jkook.command.UserCommandExecutor;
import snw.jkook.entity.Guild;
import snw.jkook.entity.User;
import snw.jkook.example.Scoreboard;
import snw.jkook.example.ScoreboardStorage;
import snw.jkook.message.Message;
import snw.jkook.message.TextChannelMessage;

public class RankCommand implements UserCommandExecutor {
    @Override
    public void onCommand(User sender, Object[] arguments, Message message) {
        if (message instanceof TextChannelMessage) {
            TextChannelMessage txtmsg = (TextChannelMessage) message;
            Guild guild = txtmsg.getChannel().getGuild();
            Scoreboard scoreboard = ScoreboardStorage.getScoreboard(guild);
            StringBuilder contentBuilder = new StringBuilder();
            Number[] levelData = Scoreboard.scoreToLevel(scoreboard.getScore(sender));
            contentBuilder.append(sender.getNickName(guild)).append("#").append(sender.getIdentifyNumber()).append("\n");
            contentBuilder.append("等级: ").append(levelData[0]).append("\n");
            contentBuilder.append("积分: ").append(levelData[1]).append("/").append(levelData[2]);
            message.reply(contentBuilder.toString());
        }
    }
}
