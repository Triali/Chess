package DataAcesss;

import dataAccess.DataAccessException;
import model.AuthToken;

import java.util.ArrayList;

public interface AuthTokenDAO
{
    public AuthToken get(String authToken) throws DataAccessException;

    public void insert(AuthToken token) throws DataAccessException;

    public void delete(String authToken) throws DataAccessException;

    public void clear() throws DataAccessException;

}
