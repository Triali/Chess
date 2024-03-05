package DataAcesss;

import dataAccess.DataAccessException;
import model.User;

import java.util.ArrayList;

public interface UserDAO
{

    public User get(String userName) throws DataAccessException;

    public void insert(User user) throws DataAccessException;

    public void delete(String userName) throws DataAccessException;

    public void clear();



}
