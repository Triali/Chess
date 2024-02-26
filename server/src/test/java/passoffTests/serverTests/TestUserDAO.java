package passoffTests.serverTests;

import dataAccess.DataAccessException;
import org.junit.jupiter.api.*;
import server.User;
import server.UserDAO;
import server.UserList;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestUserDAO
{
    UserDAO fillUsers(){
        UserDAO Users = new UserList();

        Users.insert("Spencer", "S_username","spencer@email.com");
        Users.insert("Jim", "J_username","jim@email.com");
        Users.insert("Harry", "H_username","harry@email.com");
        return Users;
    }
    ArrayList<User> fillTestUsers(){
        ArrayList TestUsers = new ArrayList();
        TestUsers.add(new User("Harry", "H_username","harry@email.com"));
        TestUsers.add(new User("Spencer", "S_username","spencer@email.com"));
        TestUsers.add(new User("Jim", "J_username","jim@email.com"));

        return TestUsers;
    }



@Test
@DisplayName("Add Users")
public void InsertUsers() {
UserDAO Users = fillUsers();
ArrayList allUsers = Users.getAll();
    ArrayList TestUsers = fillTestUsers();

//    System.out.println(allUsers);
//    System.out.println("----");
//    System.out.println(TestUsers);
    Assertions.assertEquals(allUsers,TestUsers);
}

    @Test
    @DisplayName("Add Duplicate User")
    public void InsertDuplicateUser(){
        UserDAO Users = fillUsers();
        Exception ex = assertThrows(DataAccessException.class,()->{
            Users.insert("Jim", "J_username","jim@email.com");
        });
        Assertions.assertEquals("Already Present",ex.getMessage());
        }


    @Test
    @DisplayName("Get User")
    public void GetUser(){
        UserDAO Users = fillUsers();
    }


    @Test
    @DisplayName("Get Nonexsitant User")
    public void GetNonUser(){
        UserDAO Users = fillUsers();
    }

    @Test
    @DisplayName("Delete User")
    public void DeleteUsers(){
        UserDAO Users = fillUsers();
    }

    @Test
    @DisplayName("Delete Non User")
    public void DeleteNonUsers(){
        UserDAO Users = fillUsers();
    }




}
