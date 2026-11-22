package com.graduation.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** Reads settings from config.properties. A -Dkey=value system property overrides the file. */
public final class ConfigReader {

    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in == null) {
                throw new IllegalStateException("config.properties not found in src/test/resources");
            }
            PROPS.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load config.properties", e);
        }
    }

    private ConfigReader() { }

    public static String get(String key) {
        return System.getProperty(key, PROPS.getProperty(key, "")).trim();
    }

    public static String getBaseUrl() {
        String url = get("base.url");
        return url.endsWith("/") ? url : url + "/";
    }

    public static String getBrowser() {
        String browser = get("browser");
        return browser.isEmpty() ? "chrome" : browser;
    }

    public static int getExplicitWait() {
        String value = get("explicit.wait");
        return value.isEmpty() ? 10 : Integer.parseInt(value);
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(get("headless"));
    }
}