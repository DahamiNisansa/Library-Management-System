package controller.user;

import db.DBConnection;
import module.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserController implements UserService{
    @Override
    public boolean addUser(Users newUser) {
        String sql = "INSERT INTO Users (uid, uName, contactNo, address, membershipDate) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);

            // Setting parameter values from Users object
            pstmt.setInt(1, newUser.getUid());
            pstmt.setString(2, newUser.getUName());
            pstmt.setString(3, newUser.getContactNo());
            pstmt.setString(4, newUser.getAddress());
            pstmt.setString(5, newUser.getMembershipDate());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

            // Execute update query
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;
    }


    @Override
    public boolean updateUser(Users users) {
        return false;
    }

    @Override
    public boolean deleteUser(int uid) {
        return false;
    }

    @Override
    public Users searchUser(String uName) {
        return null;
    }


    @Override
    public List<Users> getAll() {
        ArrayList<Users> userArrayList = new ArrayList<>();

        try {
            //create Db connection and load data from DB
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Users");

            //set loaded data to user object
            while (resultSet.next()){
                Users users = new Users(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                );
                //add user object into arraylist
                userArrayList.add(users);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userArrayList;
    }
}
