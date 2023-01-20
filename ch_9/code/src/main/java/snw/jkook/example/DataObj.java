package snw.jkook.example;

import snw.jkook.config.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class DataObj implements ConfigurationSerializable {
    private final String name;

    public DataObj(String name) {
        this.name = name;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();
        result.put("name", name);
        return result;
    }

    @Override
    public String toString() {
        return "DataObj{" +
                "name='" + name + '\'' +
                '}';
    }

    // will be called by the JKook's serialization system
    @SuppressWarnings("unused") // actually it will be used
    public static ConfigurationSerializable deserialize(Map<String, Object> data) {
        String name = data.get("name").toString();
        if (name.startsWith("another")) {
            return new AnotherDataObj(name);
        }
        return new DataObj(name);
    }
}
