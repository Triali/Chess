package dataAccessTests;

import DataAcesss.UserDAO;
import DataAcesss.UserSql;
import dataAccess.DataAccessException;
import model.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestUserDAO
{
    private UserDAO users;
    String password1;
    String password2;
    String password3;
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    public void setup()
    {
        try
        {
            users = new UserSql();
            users.clear();
        } catch (DataAccessException ex)
        {
            System.out.println("failed to setup");
            System.out.println(ex.getMessage());
            Assertions.fail();
            return;
        }
        User user1 = new User("user1",encoder.encode( "pass1"),"1@email.com");
        password1 = user1.getPassword();
        User user2 = new User("user2",encoder.encode("pass2"),"2@email.com");
        password2 = user2.getPassword();
        User user3 = new User("user3",encoder.encode("pass3"),"3@email.com");
        password3 = user3.getPassword();
        try
        {
            users.insert(user1);
            users.insert(user2);
            users.insert(user3);
        } catch (DataAccessException ex)
        {
            System.out.println("failed to execute insert");
            System.out.println(ex.getMessage());
            Assertions.fail();
        }
    }

    @Test
    @DisplayName("Good Get")
    public void goodGet()
    {
        setup();
        User userA;
        User userB;
        User userC;
        try
        {
            userA = users.get("user1");
            userB = users.get("user2");
            userC = users.get("user3");
        } catch (Exception ex)
        {
            System.out.println("failed to get user");
            System.out.println(ex.getMessage());
            Assertions.fail();
            return;
        }
        User user1 = new User("user1","pass1","1@email.com");
        User user2 = new User("user2","pass2","2@email.com");
        User user3 = new User("user3","pass3","3@email.com");

        Assertions.assertEquals(user1.getUsername(), userA.getUsername());
        Assertions.assertTrue(encoder.matches(user1.getPassword(), userA.getPassword()));
        Assertions.assertEquals(user1.getEmail(), userA.getEmail());

        Assertions.assertEquals(user2.getUsername(), userB.getUsername());
        Assertions.assertTrue(encoder.matches(user2.getPassword(), userB.getPassword()));
        Assertions.assertEquals(user2.getEmail(), userB.getEmail());

        Assertions.assertEquals(user3.getUsername(), userC.getUsername());
        Assertions.assertTrue(encoder.matches(user3.getPassword(), userC.getPassword()));
        Assertions.assertEquals(user3.getEmail(), userC.getEmail());
    }

    @Test
    @DisplayName("Bad Get")
    public void badGet()
    {
        setup();
        Assertions.assertThrows(DataAccessException.class, () ->
        {
            users.get("user4");
        });
    }


    @Test
    @DisplayName("Good Insert")
    public void insert()
    {
        setup();
        User user4 = new User("user4", encoder.encode( "pass4"),"4@email.com");
        try
        {
            users.insert(user4);
        } catch (DataAccessException ex)
        {
            System.out.println("failed to execute insert");
            System.out.println(ex.getMessage());
            Assertions.fail();
        }
        User testUser = null;
        try
        {
            testUser = users.get("user4");

        } catch (DataAccessException ex)
        {
            System.out.println("failed to execute get");
            System.out.println(ex.getMessage());
            Assertions.fail();
        }
        Assertions.assertEquals(user4.getUsername(), testUser.getUsername());
        Assertions.assertTrue(encoder.matches("pass4", testUser.getPassword()));
        Assertions.assertEquals(user4.getEmail(), testUser.getEmail());
    }

    @Test
    @DisplayName("Bad Insert")
    public void badInsert()
    {
        setup();

        User user3 = new User("user3",encoder.encode("pass3"),"3@email.com");
        Assertions.assertThrows(DataAccessException.class, () -> users.insert(user3));
    }

    @Test
    @DisplayName("Good Delete")
    public void goodDelete()
    {
        setup();
        try{
            users.delete("user1");
        }catch(DataAccessException ex){
            System.out.println("failed to delete token");
            System.out.println(ex.getMessage());
            Assertions.fail();
            return;
        }
        try
        {
            users.get("user2");
            users.get("user3");
        }catch (DataAccessException ex){
            System.out.println("failed to delete correct user");
            System.out.println(ex.getMessage());
            Assertions.fail();
            return;
        }
        Assertions.assertThrows(DataAccessException.class, () ->
                users.get("user1"));
    }

    @Test
    @DisplayName("Bad Delete")
    public void BadDelete()
    {
        setup();
        Assertions.assertThrows(DataAccessException.class, () ->
                users.delete("rtyukmnbhgjkm"));
    }

    @Test
    @DisplayName("Good Clear")
    public void goodClear()
    {
        setup();
        try
        {
            users.clear();
        } catch (Exception ex)
        {
            System.out.println("failed to Clear");
            System.out.println(ex.getMessage());
            Assertions.fail();
            return;
        }
        Assertions.assertThrows(DataAccessException.class, () ->
                users.get("user1"));
        Assertions.assertThrows(DataAccessException.class, () ->
                users.get("user2"));
        Assertions.assertThrows(DataAccessException.class, () ->
                users.get("user3"));
    }
}
