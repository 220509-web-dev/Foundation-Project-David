package dev.david.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    public static Connection getConnection(){
        try {
            String dbInfo = System.getenv("DB_CONNECT");
            Connection connection = DriverManager.getConnection(dbInfo);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
