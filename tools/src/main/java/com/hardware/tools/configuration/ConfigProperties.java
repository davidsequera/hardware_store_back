package com.hardware.tools.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigProperties {

    private static ConfigProperties instance;
    private static final Properties properties = new Properties();

//    private static final String properties_path = "config.properties";
    private static final String properties_path = "D:\\Projects\\Mini\\WEB\\hardware_store_back\\config.properties";

    private ConfigProperties() throws IOException {
        FileInputStream inputStream = new FileInputStream(ConfigProperties.properties_path);
        properties.load(inputStream);
    }

    public static ConfigProperties getInstance() throws IOException {
        if(instance == null) {
            instance = new ConfigProperties();
        }
        return instance;
    }

    public String getProperty(String key)  {
        return properties.getProperty(key);
    }
}
