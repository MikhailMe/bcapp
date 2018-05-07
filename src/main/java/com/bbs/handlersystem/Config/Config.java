package com.bbs.handlersystem.Config;

import lombok.Getter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class Config {

    private static final String URL_KEY = "URL";
    private static final String CONFIG_NAME = "config.ini";
    private static final String DB_USER_NAME_KEY = "DB_USER_NAME";
    private static final String DB_USER_PASSWORD_KEY = "DB_USER_PASSWORD";
    private static final String DRIVER_CLASS_NAME_KEY = "DRIVER_CLASS_NAME";

    @Getter
    private String URL = null;

    @Getter
    private String DB_USER_NAME = null;

    @Getter
    private String DB_USER_PASSWORD = null;

    @Getter
    private String DRIVER_CLASS_NAME = null;

    public Config() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(new File(CONFIG_NAME)));
        URL = properties.getProperty(URL_KEY);
        DB_USER_NAME = properties.getProperty(DB_USER_NAME_KEY);
        DB_USER_PASSWORD = properties.getProperty(DB_USER_PASSWORD_KEY);
        DRIVER_CLASS_NAME = properties.getProperty(DRIVER_CLASS_NAME_KEY);
    }
}
