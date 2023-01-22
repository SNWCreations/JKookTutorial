package snw.jkook.example;

import snw.jkook.config.ConfigurationSection;
import snw.jkook.entity.Guild;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ScoreboardStorage {
    private static final Map<String, Scoreboard> storage;

    static {
        storage = new ConcurrentHashMap<>();
    }

    private ScoreboardStorage() {} // just a util class

    public static Scoreboard getScoreboard(Guild guild) {
        return storage.computeIfAbsent(guild.getId(), i -> new Scoreboard(guild));
    }

    public static void loadAll(ConfigurationSection section) {
        Map<String, Object> values = section.getValues(false);
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            try {
                storage.put(entry.getKey(), (Scoreboard) entry.getValue());
            } catch (Exception ignored) {}
        }
    }

    public static void saveAll(ConfigurationSection section) {
        for (Map.Entry<String, Scoreboard> entry : storage.entrySet()) {
            section.set(entry.getKey(), entry.getValue());
        }
    }
}
