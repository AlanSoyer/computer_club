package dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConnectionProperty {
    private static final String CONFIG_NAME = "config/config.properties";
    private static Properties prop = new Properties();

    public ConnectionProperty() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream is = classLoader.getResourceAsStream(CONFIG_NAME);
        prop.load(is);
    }

    public static String getProperty(String key) {
        return prop.getProperty(key);
    }
}