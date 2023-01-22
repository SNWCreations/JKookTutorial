package snw.jkook.example.events;

import snw.jkook.entity.Guild;
import snw.jkook.entity.User;
import snw.jkook.event.Event;

public class LevelUpEvent extends Event {
    private final Guild guild;
    private final User user;
    private final int prev;
    private final int now;

    public LevelUpEvent(Guild guild, User user, int prev, int now) {
        this.guild = guild;
        this.user = user;
        this.prev = prev;
        this.now = now;
    }

    public Guild getGuild() {
        return guild;
    }

    public User getUser() {
        return user;
    }

    public int getPrev() {
        return prev;
    }

    public int getNow() {
        return now;
    }
}
