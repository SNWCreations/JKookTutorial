package snw.jkook.example;

import snw.jkook.config.serialization.ConfigurationSerializable;
import snw.jkook.entity.Guild;
import snw.jkook.entity.User;
import snw.jkook.example.events.LevelUpEvent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.DoubleFunction;
import java.util.function.IntFunction;

// Represents a scoreboard.
// Map keys are all user ID.
public class Scoreboard implements ConfigurationSerializable {
    private static final int FIRST_LEVEL = 100;
    private static final DoubleFunction<Double> TO_NEXT_LEVEL = (i) -> (i * 1.5);

    private final Guild guild;
    private final Map<String, Integer> map;
    private final Map<String, Integer> levelMap;

    public Scoreboard(Guild guild) {
        this(guild, new ConcurrentHashMap<>());
    }

    public Scoreboard(String guildId, Map<String, Integer> map) {
        this(Main.getInstance().getCore().getHttpAPI().getGuild(guildId), map);
    }

    public Scoreboard(Guild guild, Map<String, Integer> map) {
        this.guild = guild;
        this.map = map;
        this.levelMap = calcLevelMap(map); // restore level data
    }

    public void calc(User user, IntFunction<Integer> calcLogic) {
        int previous = map.computeIfAbsent(user.getId(), i -> 0); // NotNull, so int
        Integer result = calcLogic.apply(previous);
        if (result == null) {
            throw new NullPointerException("result cannot be null");
        }
        setScore(user, result);
    }

    public int getScore(User user) {
        return map.computeIfAbsent(user.getId(), i -> 0);
    }

    public int getLevel(User user) {
        return levelMap.computeIfAbsent(user.getId(), i -> (Integer) scoreToLevel(getScore(user))[0]);
    }

    public List<Map.Entry<String, Integer>> getSortedLevelEntries() {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(levelMap.entrySet());
        entries.sort((o1, o2) -> {
            int a = o1.getValue();
            int b = o2.getValue();
            return Integer.compare(b, a);
        });
        return entries;
    }

    public void setScore(User target, int newScore) {
        updateLevel(target, newScore);
        map.put(target.getId(), newScore);
    }

    public void reset(User user) {
        map.remove(user.getId());
    }

    private void updateLevel(User user, int score) {
        int prevLevel = getLevel(user);
        int newLevel = (int) scoreToLevel(score)[0];
        if (newLevel > prevLevel) {
            Main.getInstance().getCore().getEventManager().callEvent(new LevelUpEvent(guild, user, prevLevel, newLevel));
            levelMap.put(user.getId(), newLevel);
        }
    }

    // The methods that related to serialization:

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();
        result.put("guild_id", guild.getId());
        result.put("data", map);
        return result;
    }

    @SuppressWarnings("unused") // used by configuration serialization system
    public static ConfigurationSerializable deserialize(Map<String, Object> data) {
        //noinspection unchecked
        return new Scoreboard((String) data.get("guild_id"), new ConcurrentHashMap<>((Map<String, Integer>) data.get("data")));
    }

    // Utility methods:

    // format:
    // [0] -> current level (int)
    // [1] -> the score needed to next level (double)
    // [2] -> the point of the next level (double)
    public static Number[] scoreToLevel(int score) {
        if (score < FIRST_LEVEL) {
            return new Number[]{0, FIRST_LEVEL - score, FIRST_LEVEL};
        }
        int level = 1;
        double score1 = score - FIRST_LEVEL;
        double n = FIRST_LEVEL;
        double t; // temp
        while ((t = (score1 - (n = TO_NEXT_LEVEL.apply(n)))) >= 0) {
            score1 = t;
            level++;
        }
        return new Number[]{level, score1, n};
    }

    private static Map<String, Integer> calcLevelMap(Map<String, Integer> data) {
        Map<String, Integer> result = new HashMap<>();
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            int i = (int) scoreToLevel(entry.getValue())[0];
            result.put(entry.getKey(), i);
        }
        return result;
    }

}
