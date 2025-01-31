package db;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public class DBConnection {

    private static DBConnection instance;

    private Connection connection;

    private DBConnection(){
        String URL="jdbc:mysql://localhost:3306/Library_Management_System";
        String userName="root";
        String password="";
        try {
            this.connection = DriverManager.getConnection(URL, userName, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DBConnection getInstance(){
        return instance==null?instance = new DBConnection():instance;
    }

}
