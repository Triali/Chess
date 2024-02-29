package DataAcesss;

import dataAccess.DataAccessException;
import model.User;

import java.util.ArrayList;

public interface UserDAO
{

    public User get(String userName) throws DataAccessException;

    public ArrayList<User> getAll();

    public void insert(String userName, String password,String email) throws DataAccessException;

    public void delete(String userName) throws DataAccessException;


    public String printAll();

    public void deleteAll();



}