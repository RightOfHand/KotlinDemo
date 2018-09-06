package app.songy.com.global_base.common.helper;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;


public class BuildProperties {

    private Properties properties = new Properties();

    private BuildProperties() throws IOException {
        File file = new File(Environment.getRootDirectory(), "build.prop");
        FileInputStream inputStream = new FileInputStream(file);
        try {
            properties.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            inputStream.close();
        }

        inputStream.close();
    }

    public boolean containsKey(final Object key) {
        return properties.containsKey(key);
    }

    public boolean containsValue(final Object value) {
        return properties.containsValue(value);
    }

    public Set<Map.Entry<Object, Object>> entrySet() {
        return properties.entrySet();
    }

    public String getProperty(final String name) {
        return properties.getProperty(name);
    }

    public String getProperty(final String name, final String defaultValue) {
        return properties.getProperty(name, defaultValue);
    }

    public boolean isEmpty() {
        return properties.isEmpty();
    }

    public Enumeration<Object> keys() {
        return properties.keys();
    }

    public Set<Object> keySet() {
        return properties.keySet();
    }

    public int size() {
        return properties.size();
    }

    public Collection<Object> values() {
        return properties.values();
    }

    private static BuildProperties buildProperties = null;

    public static BuildProperties newInstance() throws IOException {
        if (buildProperties == null) {
            buildProperties = new BuildProperties();
        }
        return buildProperties;
    }
}
