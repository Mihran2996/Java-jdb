package jdb.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBCConnection {
    private final String DB_URL = "jdbc:mysql://localhost:3306/users";
    private final String USERNAME="root";
    private final String PASSWORD="root";
    private final String DRIVER_NAME = "com.mysql.jdbc.Driver";
    private static DBCConnection instance=new DBCConnection();
    Connection connection;
    private DBCConnection(){
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public  Connection getConnection() throws SQLException {
        if (connection==null || connection.isClosed()){
            try {
                connection= DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
    public static DBCConnection getInstance(){
        return instance;
    }
    

}
