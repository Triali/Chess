package server;

import java.util.ArrayList;

public interface UserDAO
{

    public User get(String userName);

    public ArrayList<User> getAll();

    public void insert(String userName, String password,String email);

    public void delete(String userName);

    public void post();



}
