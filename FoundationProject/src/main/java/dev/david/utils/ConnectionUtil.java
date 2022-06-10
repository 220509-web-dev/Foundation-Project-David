package dev.david.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

    private static ConnectionUtil connection;

    public static ConnectionUtil getInstance(){
        if(connection == null){
            connection = new ConnectionUtil();
        }
        return connection;
    }

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            System.err.println("Failed to load PostgreSQL Driver");
            throw new RuntimeException(e);
        }
    }


    private ConnectionUtil() {
/*        try{

        } catch (Exception e){
            System.err.println("Failed to load database!");
            throw e;
        }*/
    }

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
