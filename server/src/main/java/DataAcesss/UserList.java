package DataAcesss;

import DataAcesss.UserDAO;
import dataAccess.DataAccessException;
import model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class UserList implements UserDAO
{
//    private
    HashMap<String, User> allUsers = new HashMap();
    public User get(String userName) throws DataAccessException
    {
        User user =  allUsers.get(userName);
        if(user != null){
            return user;
        }
        throw new DataAccessException("Error: unauthorized");
    }


    public void insert(User user) throws DataAccessException
    {
        allUsers.put(user.getUsername(), user);
    }

    public void delete(String userName) throws DataAccessException
    {
        allUsers.remove(userName);
    }


    public void clear()
    {
        allUsers.clear();
    }


}
