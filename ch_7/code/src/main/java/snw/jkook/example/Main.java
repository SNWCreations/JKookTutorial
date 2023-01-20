package snw.jkook.example;

import snw.jkook.plugin.BasePlugin;

public class Main extends BasePlugin {

    @Override
    public void onEnable() {
        this.getCore().getEventManager().registerHandlers(this, new EventListener());
    }
}
