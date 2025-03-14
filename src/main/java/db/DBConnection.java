package db;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public class DBConnection {

    private static DBConnection instance;

    private final Connection connection;

    private DBConnection(){
        String url="jdbc:mysql://localhost:3306/library_management_system";
        String userName="root";
        String password="";
        try {
            this.connection = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DBConnection getInstance(){
        return instance==null?instance = new DBConnection():instance;
    }

}
