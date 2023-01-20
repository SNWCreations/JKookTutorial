package snw.jkook.example;

import snw.jkook.config.serialization.ConfigurationSerialization;
import snw.jkook.plugin.BasePlugin;

public class Main extends BasePlugin {

    @Override
    public void onLoad() {
        ConfigurationSerialization.registerClass(DataObj.class);
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        Object data = getConfig().get("data");
        Object data2 = getConfig().get("data2");
        getLogger().info("The deserialized data: {}", data);
        getLogger().info("The deserialized data2: {}", data2);
        if (data == null || data2 == null){
            getLogger().info("No data? Creating new objects, it will be saved on disable");
            getConfig().set("data", new DataObj("theName"));
            getConfig().set("data2", new AnotherDataObj("anotherObj"));
        }
    }

    @Override
    public void onDisable() {
        saveConfig();
    }
}
