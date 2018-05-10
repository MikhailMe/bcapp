package com.bbs.handlersystem.Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class Config {

    private static final String URL_KEY = "URL";
    private static final String HOST_KEY = "HOST";
    private static final String PORT_KEY = "PORT";
    private static final String BACKLOG_KEY = "BACKLOG";
    private static final String CONFIG_NAME = "config.ini";
    private static final String DB_USER_NAME_KEY = "DB_USER_NAME";
    private static final String DB_USER_PASSWORD_KEY = "DB_USER_PASSWORD";

    public static int PORT;
    public static String URL;
    public static String HOST;
    public static int BACKLOG;
    public static String DB_USER_NAME;
    public static String DB_USER_PASSWORD;

    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(new File(CONFIG_NAME)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        URL = properties.getProperty(URL_KEY);
        HOST = properties.getProperty(HOST_KEY);
        DB_USER_NAME = properties.getProperty(DB_USER_NAME_KEY);
        PORT = Integer.parseInt(properties.getProperty(PORT_KEY));
        BACKLOG = Integer.parseInt(properties.getProperty(BACKLOG_KEY));
        DB_USER_PASSWORD = properties.getProperty(DB_USER_PASSWORD_KEY);
    }
}
