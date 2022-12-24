package snw.jkook.example;

import snw.jkook.plugin.BasePlugin;

public class Main extends BasePlugin {

    @Override
    public void onLoad() {
        saveDefaultConfig(); /* 1 */
    }

    @Override
    public void onEnable() {
        getLogger().info(getConfig().getString("msg")); /* 2 */
        getConfig().set("msg", "fine"); /* 3 */
    }

    @Override
    public void onDisable() {
        saveConfig(); /* 4 */
    }

}
