package snw.jkook.example;

import snw.jkook.config.ConfigurationSection;
import snw.jkook.entity.Guild;
import snw.jkook.entity.channel.TextChannel;
import snw.jkook.example.math.MathEventPublisher;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// We should save this
public class GuildChannelStorage {
    private static final Map<String, String> map = new ConcurrentHashMap<>(); // key: guild id, value: channel id

    public static TextChannel get(Guild guild) {
        String id = map.get(guild.getId());
        if (id == null) {
            return null;
        }
        try {
            return (TextChannel) Main.getInstance().getCore().getHttpAPI().getChannel(id);
        } catch (ClassCastException e) {
            map.remove(guild.getId()); // invalid channel type!
            return null;
        }
    }

    public static void set(Guild guild, TextChannel channel) {
        map.put(guild.getId(), channel.getId());
        new MathEventPublisher(guild).start();
    }

    public static void loadAll(ConfigurationSection section) {
        Map<String, Object> values = section.getValues(false);
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            try {
                map.put(entry.getKey(), (String) entry.getValue());
                new MathEventPublisher(Main.getInstance().getCore().getHttpAPI().getGuild(entry.getKey())).start();
            } catch (Exception ignored) {}
        }
    }

    public static void saveAll(ConfigurationSection section) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            section.set(entry.getKey(), entry.getValue());
        }
    }
}
