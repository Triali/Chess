package model;

import java.util.Objects;
import org.springframework.security.crypto.bcrypt.*;

public class User
{
    private String username;
    private String password;
    private String email;
//    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String getPassword()
    {
        return password;
    }

    public String getEmail()
    {
        return email;
    }

    public User(String username, String password, String email)
    {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @Override
    public String toString()
    {
        return "User{" +
                "userName='" + username + '\'' +
                ", passWord='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getUsername()
    {
        return username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(username, password, email);
    }
}
