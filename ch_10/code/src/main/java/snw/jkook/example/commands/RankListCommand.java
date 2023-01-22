package snw.jkook.example.commands;

import snw.jkook.command.UserCommandExecutor;
import snw.jkook.entity.Guild;
import snw.jkook.entity.User;
import snw.jkook.example.Main;
import snw.jkook.example.Scoreboard;
import snw.jkook.example.ScoreboardStorage;
import snw.jkook.message.Message;
import snw.jkook.message.TextChannelMessage;
import snw.jkook.message.component.MarkdownComponent;

import java.util.List;
import java.util.Map;

public class RankListCommand implements UserCommandExecutor {
    @Override
    public void onCommand(User sender, Object[] arguments, Message message) {
        if (message instanceof TextChannelMessage) {
            TextChannelMessage txtmsg = (TextChannelMessage) message;
            Guild guild = txtmsg.getChannel().getGuild();
            Scoreboard scoreboard = ScoreboardStorage.getScoreboard(guild);
            List<Map.Entry<String, Integer>> entries = scoreboard.getSortedLevelEntries();
            if (entries.isEmpty()) {
                message.reply(new MarkdownComponent("积分榜为空。"));
                return;
            }

            StringBuilder contentBuilder = new StringBuilder();
            contentBuilder.append("========== 积分榜 ==========").append("\n");
            for (int i = 0; i < 10; i++) {
                if (entries.size() < (i + 1)) {
                    break; // not enough data!
                }
                Map.Entry<String, Integer> entry = entries.get(i);
                User user = Main.getInstance().getCore().getHttpAPI().getUser(entry.getKey());
                Number[] levelData = Scoreboard.scoreToLevel(scoreboard.getScore(user));
                contentBuilder.append("#").append(i).append(" - ").append(user.getNickName(guild)).append("#").append(user.getIdentifyNumber()).append("\n");
                contentBuilder.append("等级: ").append(levelData[0]).append("\n");
                contentBuilder.append("积分: ")
                        .append(levelData[2].intValue() - levelData[1].intValue())
                        .append("/")
                        .append(levelData[2]).append("\n");
                if (entries.size() > (i + 1)) { // if not the last round, and there will have next round
                    contentBuilder.append("---\n"); // append new line separator
                }
            }
            message.reply(contentBuilder.toString());
        }
    }
}
