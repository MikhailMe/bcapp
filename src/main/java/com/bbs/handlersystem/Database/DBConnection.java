package com.bbs.handlersystem.Database;

import com.bbs.handlersystem.Config.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            connect();
        }
        return connection;
    }

    public static void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("Store: disconnection successful");
        }
    }

    private static void connect() {
        try {
            Config config = new Config();
            String url = config.getURL();
            String name = config.getDB_USER_NAME();
            String pass = config.getDB_USER_PASSWORD();
            Class.forName(config.getDRIVER_CLASS_NAME());
            System.out.println("JDBC driver connected");
            connection = DriverManager.getConnection(url, name, pass);
            System.out.println("Store: connection successful");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
