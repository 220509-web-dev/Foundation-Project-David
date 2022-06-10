package dev.david.daos;

import dev.david.entities.Users;

import java.util.List;

public interface AppDao {
    // Create user
    Users createUser(Users user);

    // Read user
    Users getUserById(int id);

    Users getUserByUsername(String username);

    List<Users> getAllUsers();

    // Update user
    Users updateUser(Users user);

    // Delete user
    void deleteUserById(int id);
}
