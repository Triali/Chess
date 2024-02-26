package server;

import java.util.HashMap;
import java.util.HashSet;

public class AuthTokenList implements AuthTokenDAO
{
    HashMap<String, AuthToken> allTokens;
    public AuthToken get(String authToken)
    {
        return allTokens.get(authToken);
    }

    public void insert(String userName)
    {
        AuthToken newToken = new AuthToken(userName);
        allTokens.put(newToken.getAuthToken(),newToken);
    }

    public void delete(String authToken)
    {
        allTokens.remove(authToken);
    }

    public void post()
    {

    }
}
