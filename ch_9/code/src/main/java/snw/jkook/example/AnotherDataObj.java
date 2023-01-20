package snw.jkook.example;

import snw.jkook.config.serialization.ConfigurationSerializable;
import snw.jkook.config.serialization.DelegateDeserialization;

import java.util.HashMap;
import java.util.Map;

@DelegateDeserialization(value = DataObj.class)
public class AnotherDataObj implements ConfigurationSerializable {
    private final String name;

    public AnotherDataObj(String name) {
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
        return "AnotherDataObj{" +
                "name='" + name + '\'' +
                '}';
    }

    // will be called by the JKook's serialization system
    @SuppressWarnings("unused")
    public static AnotherDataObj deserialize(Map<String, Object> data) {
        return new AnotherDataObj(data.get("name").toString());
    }
}
