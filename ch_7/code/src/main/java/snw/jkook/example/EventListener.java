package snw.jkook.example;

import snw.jkook.event.EventHandler;
import snw.jkook.event.Listener;
import snw.jkook.event.channel.ChannelMessageEvent;

public class EventListener implements Listener {
    @EventHandler
    public void onMessage(ChannelMessageEvent event) {
        event.getMessage().reply("Hello world!");
    }
}
