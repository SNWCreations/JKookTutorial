package snw.jkook.example;

import snw.jkook.plugin.BasePlugin;

public class Main extends BasePlugin /* 1 */ {
    private static Main instance;

    @Override
    public void onLoad() /* 2 */ {
        instance = this; /* 5 */
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

    /* 6 */
    public static Main getInstance() {
        return instance;
    }

}
