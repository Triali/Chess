package server;

import java.util.Objects;
import java.util.UUID;


import static java.util.UUID.randomUUID;

public class AuthToken
{
    private String usermane;
    private String authToken;

    public String getAuthToken()
    {
        return authToken;
    }

    public AuthToken(String usermane)
    {
        this.usermane = usermane;
        authToken = UUID.randomUUID().toString();
    }

    @Override
    public String toString()
    {
        return "AuthToken{" +
                "usermane='" + usermane + '\'' +
                ", authToken='" + authToken + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken1 = (AuthToken) o;
        return Objects.equals(usermane, authToken1.usermane) && Objects.equals(authToken, authToken1.authToken);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(usermane, authToken);
    }
}
