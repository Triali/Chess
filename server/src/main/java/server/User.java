package server;

import java.util.Objects;

public class User
{
    private String userName;
    private String passWord;
    private String email;

    public String getPassWord()
    {
        return passWord;
    }

    public String getEmail()
    {
        return email;
    }

    public User(String userName, String passWord, String email)
    {
        this.userName = userName;
        this.passWord = passWord;
        this.email = email;
    }

    @Override
    public String toString()
    {
        return "User{" +
                "userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getUserName()
    {
        return userName;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName) && Objects.equals(passWord, user.passWord) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(userName, passWord, email);
    }
}
