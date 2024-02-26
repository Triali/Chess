package server;

import dataAccess.DataAccessException;

import java.util.ArrayList;
import java.util.HashMap;

public class UserList implements UserDAO
{
//    private
    HashMap<String, User> allUsers = new HashMap();
    public User get(String userName) throws DataAccessException
    {
        if(allUsers.containsKey(userName)){
            return allUsers.get(userName);
        }
        else{
            throw new DataAccessException("Element not found");
        }
    }

    public ArrayList<User> getAll(){
        ArrayList<User> users = new ArrayList();
        allUsers.forEach((key, value)->{
            users.add(value);
        });
        return users;
    }

    public void insert(String userName,String password, String email) throws DataAccessException
    {
        if(allUsers.containsKey(userName)){
            throw new DataAccessException("Element already exists");
        }else{
            allUsers.put(userName,new User(userName,password,email));
        }


    }

    public void delete(String userName) throws DataAccessException
    {
        if(allUsers.containsKey(userName)){
            allUsers.remove("Username");
        }
        else{
            throw new DataAccessException("Element not found");
        }

    }
    public void post()
    {

    }
    public String printAll(){
        String alltoString = new String();
        allUsers.forEach((key, value)->{
            alltoString.concat(key + " : " + value+"\n");
        });
        return alltoString;
    }

    public void deleteAll()
    {
        allUsers.clear();
    }


}
