package dataAccessTests;

import DataAcesss.AuthTokenDAO;
import DataAcesss.AuthTokenSql;
import dataAccess.DataAccessException;
import model.AuthToken;
import org.junit.jupiter.api.*;

public class TestAuthTokenDAO
{
    private AuthTokenDAO tokens;
    String auth1;
    String auth2;
    String auth3;

    public void setup()
    {
        try
        {
            tokens = new AuthTokenSql();
            tokens.clear();
        } catch (DataAccessException ex)
        {
            System.out.println("failed to setup");
            System.out.println(ex.getMessage());
            return;
        }
        AuthToken token1 = new AuthToken("user1");
        auth1 = token1.getAuthToken();
        AuthToken token2 = new AuthToken("user2");
        auth2 = token2.getAuthToken();
        AuthToken token3 = new AuthToken("user3");
        auth3 = token3.getAuthToken();
        try
        {
            tokens.insert(token1);
            tokens.insert(token2);
            tokens.insert(token3);
        } catch (DataAccessException ex)
        {
            System.out.println("failed to execute insert");
            System.out.println(ex.getMessage());
        }
    }

    @Test
    @DisplayName("Good Get")
    public void goodGet()
    {
        setup();
        AuthToken tokenA;
        AuthToken tokenB;
        AuthToken tokenC;
        try
        {
            tokenA = tokens.get(auth1);
            tokenB = tokens.get(auth2);
            tokenC = tokens.get(auth3);
        } catch (Exception ex)
        {
            System.out.println("failed to get token");
            System.out.println(ex.getMessage());
            return;
        }
        AuthToken token1 = new AuthToken("user1",auth1);
        AuthToken token2 = new AuthToken("user2",auth2);
        AuthToken token3 = new AuthToken("user3",auth3);
        Assertions.assertEquals(token1, tokenA);
        Assertions.assertEquals(token2, tokenB);
        Assertions.assertEquals(token3, tokenC);
    }

    @Test
    @DisplayName("Bad Get")
    public void badGet()
    {
        setup();
        String auth4 = "dfghjkliujnhjyughvgftr";
        Assertions.assertThrows(DataAccessException.class, () ->
        {
            tokens.get(auth4);
        });
    }


    @Test
    @DisplayName("Good Insert")
    public void insert()
    {
        setup();
        AuthToken token4 = new AuthToken("token4");
        String auth4 = token4.getAuthToken();
        try
        {
            tokens.insert(token4);
        } catch (DataAccessException ex)
        {
            System.out.println("failed to execute insert");
            System.out.println(ex.getMessage());
        }
        AuthToken testToken = null;
        try
        {
          testToken = tokens.get(auth4);

        } catch (DataAccessException ex)
        {
            System.out.println("failed to execute getAll");
            System.out.println(ex.getMessage());
        }
        Assertions.assertEquals(token4, testToken);
    }

    @Test
    @DisplayName("Bad Insert")
    public void badInsert()
    {
        setup();
        AuthToken token4 = new AuthToken("token4",auth1);
        Assertions.assertThrows(DataAccessException.class, () -> tokens.insert(token4));
    }

    @Test
    @DisplayName("Good Delete")
    public void goodDelete()
    {
        setup();
        try{
            tokens.delete(auth1);
        }catch(DataAccessException ex){
            System.out.println("failed to delete token");
            System.out.println(ex.getMessage());
            return;
        }
        try
        {
            tokens.get(auth2);
            tokens.get(auth3);
        }catch (DataAccessException ex){
            System.out.println("failed to delete correct token");
            System.out.println(ex.getMessage());
            return;
        }
        Assertions.assertThrows(DataAccessException.class, () ->
                tokens.get(auth1));
    }

    @Test
    @DisplayName("Bad Delete")
    public void BadDelete()
    {
        setup();

        Assertions.assertThrows(DataAccessException.class, () ->
                tokens.delete("rtyukmnbhgjkm"));
    }

    @Test
    @DisplayName("Good Clear")
    public void goodClear()
    {
        setup();
        try
        {
            tokens.clear();
        } catch (Exception ex)
        {
            System.out.println("failed to Clear");
            System.out.println(ex.getMessage());
            return;
        }
        Assertions.assertThrows(DataAccessException.class, () ->
                tokens.get(auth1));
        Assertions.assertThrows(DataAccessException.class, () ->
                tokens.get(auth2));
        Assertions.assertThrows(DataAccessException.class, () ->
                tokens.get(auth3));
    }







}
