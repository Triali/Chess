package model;

import java.util.Objects;
import java.util.UUID;

public class AuthToken
{
    private String username;
    private String authToken;

    public String getAuthToken()
    {
        return authToken;
    }

    public AuthToken(String username)
    {
        this.username = username;
        authToken = UUID.randomUUID().toString();
    }

    public AuthToken(String username,String token)
    {
        this.username = username;
        authToken = token;
    }
    @Override
    public String toString()
    {
        return "AuthToken{" +
                "usermane='" + username + '\'' +
                ", authToken='" + authToken + '\'' +
                '}';
    }

    public String getUsername()
    {
        return username;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken1 = (AuthToken) o;
        return Objects.equals(username, authToken1.username);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(username, authToken);
    }
}
