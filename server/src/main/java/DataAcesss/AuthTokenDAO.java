package DataAcesss;

import dataAccess.DataAccessException;
import model.AuthToken;

import java.util.ArrayList;

public interface AuthTokenDAO
{
    public AuthToken get(String authToken) throws DataAccessException;

    public String insert(String userName) throws DataAccessException;

    public void insert(AuthToken token) throws DataAccessException;

    public void delete(String authToken) throws DataAccessException;

    public ArrayList<AuthToken> getAll();

    public String allToString();

    public void deleteAll();

}
