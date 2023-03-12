package snw.jkook.example;

import snw.jkook.plugin.BasePlugin;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Main extends BasePlugin {

    @Override
    public void onLoad() {
        /* 1 */
        saveDefaultConfig(); // save default config file
                             // This is equals to saveResource("config.yml", false, false);
                             // But the IllegalArgumentException is ignored
    }

    @Override
    public void onEnable() {
        // The following statements will show you the usage about the methods in Plugin interface.
        getLogger().info("Hello world");

        getLogger().info("My name is {}, version {}",
                getDescription().getName(), /* 2 */
                getDescription().getVersion()
        );
        getLogger().info("I'm working on JKook API version {}",
                getDescription().getApiVersion()
        );

        getLogger().info("I'm at {}", getFile()); /* 3 */

        InputStream stream = getResource("hello.txt"); /* 4 */
        // You can use the following statements to read data from your plugin JAR:
        byte[] streamData;
        try {
            streamData = inputStreamToByteArray(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String data = new String(streamData, StandardCharsets.UTF_8);
        assert data.equals(
                "This is an example for telling you how to use Plugin#saveResource (or Plugin#getResource) method."
        );


        saveResource("hello.txt", false, false); /* 5 */

        getLogger().info("Go to {}, a surprise is appeared there!", getDataFolder()); /* 6 */
    }

    @Override
    public void onDisable() {
        getLogger().info("Goodbye world");
    }

    public static byte[] inputStreamToByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }
}
