package server;

import java.util.ArrayList;
import java.util.HashMap;

public class UserList implements UserDAO
{
//    private
    HashMap<String, User> allUsers = new HashMap();
    public User get(String userName)
    {
        return allUsers.get(userName);
    }

    public ArrayList<User> getAll(){
        ArrayList<User> users = new ArrayList();
        allUsers.forEach((key, value)->{
            users.add(value);
        });
        return users;
    }

    public void insert(String userName,String password, String email)
    {
allUsers.put(userName,new User(userName,password,email));
    }

    public void delete(String userName)
    {
        allUsers.remove(userName);
    }

    public void post()
    {

    }
}
