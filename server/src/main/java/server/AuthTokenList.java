package server;

import dataAccess.DataAccessException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class AuthTokenList implements AuthTokenDAO
{
    HashMap<String, AuthToken> allTokens = new HashMap();
    public AuthToken get(String authToken) throws DataAccessException
    {
        if(allTokens.containsKey(authToken)){
            return allTokens.get(authToken);
        }
        else{
            throw new DataAccessException("Element not found");
        }

    }

    public String insert(String userName) throws DataAccessException
    {


AuthToken temptoken = new AuthToken(userName);
        if(allTokens.containsValue(temptoken)){
            throw new DataAccessException("Element already exists");
        }else{
            AuthToken newToken = new AuthToken(userName);
            allTokens.put(newToken.getAuthToken(),newToken);
            return newToken.getAuthToken();
        }


    }

    public void delete(String authToken) throws DataAccessException
    {

        if(allTokens.containsKey(authToken)){
            allTokens.remove(authToken);
        }
        else{
            throw new DataAccessException("Element not found");
        }

    }

    public void insert(AuthToken token) throws DataAccessException
    {
        if(allTokens.containsKey(token.getAuthToken())){
            throw new DataAccessException("Element already exists");
        }else{
//            AuthToken newToken = new AuthToken(userName);
            allTokens.put(token.getAuthToken(),token);
        }

    }
    public String allToString(){
        String alltokens = new String();
        allTokens.forEach((key,value)-> {
            alltokens.concat(value.toString()+"\n");
        });
        return alltokens;
    }

    public void deleteAll()
    {
        allTokens.clear();
    }

    public void post()
    {

    }

    public ArrayList<AuthToken> getAll(){
        ArrayList<AuthToken> tokens = new ArrayList();
        allTokens.forEach((key, value)->{
            tokens.add(value);
        });
        return tokens;
    }
}
