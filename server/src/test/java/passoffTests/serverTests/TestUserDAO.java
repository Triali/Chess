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
try
{
    Users.insert("Spencer", "S_username", "spencer@email.com");
    Users.insert("Jim", "J_username", "jim@email.com");
    Users.insert("Harry", "H_username", "harry@email.com");
}catch(DataAccessException ex){

}
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
        Assertions.assertEquals("Element already exists",ex.getMessage());
        }


    @Test
    @DisplayName("Get User")
    public void GetUser(){
        UserDAO Users = fillUsers();
        User test;
        try
        {
            test = Users.get("Jim");
            Assertions.assertEquals(test,new User("Jim", "J_username", "jim@email.com"));
        }catch(DataAccessException ex){}

    }


    @Test
    @DisplayName("Get Nonexsitant User")
    public void GetNonUser(){
        UserDAO Users = fillUsers();
        Exception ex = assertThrows(DataAccessException.class,()->{
            Users.get("Phil");
        });
        Assertions.assertEquals("Element not found",ex.getMessage());
    }

    @Test
    @DisplayName("Delete User")
    public void DeleteUsers(){
        UserDAO Users = fillUsers();
        ArrayList<User> testUsers = fillTestUsers();
        try
        {
         testUsers.remove(0);
         Users.delete("Harry");
         Assertions.assertEquals(Users.getAll(),testUsers);
        }catch(DataAccessException ex){}

    }

    @Test
    @DisplayName("Delete Non User")
    public void DeleteNonUsers(){
        UserDAO Users = fillUsers();
        Exception ex = assertThrows(DataAccessException.class,()->{
            Users.delete("Phil");
        });
        Assertions.assertEquals("Element not found",ex.getMessage());

    }

    @Test
    @DisplayName("print All")
    public void PrintAll(){
        UserDAO Users = fillUsers();
        ArrayList<User> TestUsers = fillTestUsers();
        String testString = new String();
        for (User user:TestUsers)
        {
            testString.concat(user.getUserName()+" : "+user);

        }
        Assertions.assertEquals(testString,Users.printAll());

    }





}
