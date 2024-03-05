package DataAcesss;

import DataAcesss.AuthTokenDAO;
import dataAccess.DataAccessException;
import model.AuthToken;
import model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class AuthTokenList implements AuthTokenDAO
{
    HashMap<String, AuthToken> allTokens = new HashMap();

    public AuthToken get(String authToken) throws DataAccessException
    {
        AuthToken token =  allTokens.get(authToken);
        if(token != null){
            return token;
        }
        throw new DataAccessException("Error: unauthorized");
    }

    public void delete(String authToken) throws DataAccessException
    {
        if (allTokens.containsKey(authToken))
        {
            allTokens.remove(authToken);
        } else
        {
            throw new DataAccessException("Error: unauthorized");
        }
    }

    public void insert(AuthToken token) throws DataAccessException
    {
        allTokens.put(token.getAuthToken(),token);
    }
    public void clear()
    {
        allTokens.clear();
    }


}
