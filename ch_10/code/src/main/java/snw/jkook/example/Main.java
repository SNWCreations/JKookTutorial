package snw.jkook.example;

import snw.jkook.command.JKookCommand;
import snw.jkook.config.file.YamlConfiguration;
import snw.jkook.config.serialization.ConfigurationSerialization;
import snw.jkook.entity.User;
import snw.jkook.example.commands.ActiveCommand;
import snw.jkook.example.commands.RankCommand;
import snw.jkook.example.commands.RankListCommand;
import snw.jkook.example.commands.SetScoreCommand;
import snw.jkook.plugin.BasePlugin;

import java.io.File;
import java.io.IOException;

public class Main extends BasePlugin {
    private static Main instance;

    @Override
    public void onLoad() {
        instance = this;
        ConfigurationSerialization.registerClass(Scoreboard.class);
    }

    @Override
    public void onEnable() {
        loadConfigs();
        registerCommands();
        getCore().getEventManager().registerHandlers(this, new EventListeners());
    }

    @Override
    public void onDisable() {
        saveConfigs();
    }

    private void loadConfigs() {
        try {
            YamlConfiguration data = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "data.yml"));
            ScoreboardStorage.loadAll(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            YamlConfiguration gcc = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "guildchannelmapping.yml"));
            GuildChannelStorage.loadAll(gcc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveConfigs() {
        YamlConfiguration score = new YamlConfiguration();
        ScoreboardStorage.saveAll(score);
        try {
            score.save(new File(getDataFolder(), "data.yml"));
        } catch (IOException e) {
            // you should record the data in other way if the data is important. e.g. print it to logger?
            getLogger().error("Unable to save data, we will print it here");
            getLogger().error(score.saveToString());
        }

        YamlConfiguration gcc = new YamlConfiguration();
        GuildChannelStorage.saveAll(gcc);
        try {
            gcc.save(new File(getDataFolder(), "guildchannelmapping.yml"));
        } catch (IOException e) {
            // you should record the data in other way if the data is important. e.g. print it to logger?
            getLogger().error("Unable to save guild->channel mapping, we will print it here");
            getLogger().error(gcc.saveToString());
        }
    }

    private void registerCommands() {
        new JKookCommand("rank")
                .setDescription("使用此命令获取你在此服务器中的积分数据！")
                .executesUser(new RankCommand())
                .register(this);
        new JKookCommand("ranklist")
                .setDescription("使用此命令获取此服务器的积分榜。仅展示前 10 个。")
                .executesUser(new RankListCommand())
                .register(this);
        new JKookCommand("setscore")
                .setDescription("使用此命令设置指定用户的分数。需要你有消息管理权限。使用 /help setscore 获取详细帮助。")
                .setHelpContent("/setscore @某人 分数")
                .addArgument(User.class)
                .addArgument(int.class)
                .executesUser(new SetScoreCommand())
                .register(this);
        new JKookCommand("active")
                .setDescription("将当前服务器的活跃频道设为发出命令时的消息所在的频道。活跃频道将被用于发送随机算式。需要你有消息管理权限。")
                .executesUser(new ActiveCommand())
                .register(this);
    }

    public static Main getInstance() {
        return instance;
    }
}
