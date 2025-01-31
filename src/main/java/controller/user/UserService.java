package controller.user;

import module.Users;

import java.util.List;

public interface UserService {
    boolean addUser (Users users);
    boolean updateUser (Users users);
    boolean deleteUser (int uid);
    Users searchUser (String uName);
    List<Users> getAll();
}
