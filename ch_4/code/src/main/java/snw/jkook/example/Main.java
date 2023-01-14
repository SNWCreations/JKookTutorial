package snw.jkook.example;

import snw.jkook.plugin.BasePlugin;

public class Main extends BasePlugin /* 1 */ {

    @Override
    public void onLoad() /* 2 */ {
        getLogger().info("Hello world plugin loaded!");
    }

    @Override
    public void onEnable() /* 3 */ {
        getLogger().info("Hello world plugin enabled!");
    }

    @Override
    public void onDisable() /* 4 */ {
        getLogger().info("Hello world plugin disabled!");
    }

}
