package DAO;

import models.User;

public interface UserDAO {
    User getUserByUsername(String username);

    boolean addUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(int userId);

    User getUserById(int userId);

}