package snw.jkook.example;

import snw.jkook.plugin.BasePlugin;

public class Main extends BasePlugin {

    @Override
    public void onEnable() {
        getLogger().info(getConfig().getString("msg")); /* 1 */
    }

}
