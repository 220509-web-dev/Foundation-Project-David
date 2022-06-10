package dev.david.services;

import dev.david.daos.UserDaoPostgres;
import dev.david.entities.Users;
import dev.david.exceptions.EmailNotAvailableException;
import dev.david.exceptions.InvalidRequestException;
import dev.david.exceptions.UserNotFoundException;
import dev.david.exceptions.UsernameNotAvailableException;

import java.util.List;

public class UserService {

    private final UserDaoPostgres userDAO;

    public UserService(UserDaoPostgres userDAO){
        this.userDAO = userDAO;
    }

    public Users createNewUser(Users newUser) {

        if(newUser == null || newUser.getEmail().equals("") || newUser.getUsername().equals("") || newUser.getPassword().equals("")){
            throw new InvalidRequestException("User data was invalid!");
        }

        for(Users u : userDAO.getAllUsers()){
            if(u.getUsername().equals(newUser.getUsername())){
                throw new UsernameNotAvailableException("This username is already taken!");
            }
        }

        for(Users u : userDAO.getAllUsers()){
            if(u.getEmail().equals(newUser.getEmail())){
                throw new EmailNotAvailableException("This email is already taken!");
            }
        }

        return userDAO.createUser(newUser);

    }

    public Users getUserById(int id){
        Users foundUser = userDAO.getUserById(id);
        if(foundUser == null){
            throw new UserNotFoundException("There is no user with this id!");
        }
        return foundUser;
    }

    public Users getUserByUsername(String username){
        Users foundUser = userDAO.getUserByUsername(username);
        if(foundUser == null){
            throw new UserNotFoundException("There is no user with this username!");
        }
        return foundUser;
    }

    public List<Users> getAllUsers(){
        return userDAO.getAllUsers();
    }

}
