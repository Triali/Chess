package serviceTests;

import DataAcesss.AuthTokenDAO;
import DataAcesss.AuthTokenList;
import dataAccess.DataAccessException;
import model.AuthToken;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestAuthTokenDAO
{
    AuthTokenDAO fillTokens(){
        AuthTokenDAO Tokens = new AuthTokenList();
        try
        {
            Tokens.insert("Spencer");
            Tokens.insert("Jim");
            Tokens.insert("Harry");
        }catch(DataAccessException ex){

        }
        return Tokens;
    }
    ArrayList<AuthToken> fillTestTokens(){
        ArrayList TestTokens = new ArrayList();

        TestTokens.add(new AuthToken("Spencer"));
        TestTokens.add(new AuthToken("Jim"));
        TestTokens.add(new AuthToken("Harry"));


        return TestTokens;
    }

    String toString(ArrayList<AuthToken> tokens){
        String alltokens = new String();
        tokens.forEach((value)->{
            alltokens.concat(value.toString()+"\n");
        });
        return alltokens;
    }



    @Test
    @DisplayName("Add Tokens")
    public void InsertTokens() {
        AuthTokenDAO Tokens = fillTokens();
        ArrayList<AuthToken> allTokens = Tokens.getAll();


        Assertions.assertEquals(toString(allTokens),Tokens.allToString());
    }

    @Test
    @DisplayName("Add Duplicate AuthToken")
    public void InsertDuplicateAuthToken(){
        AuthTokenDAO Tokens = fillTokens();
        Exception ex = assertThrows(DataAccessException.class,()->{
            Tokens.insert("Jim");
        });
        Assertions.assertEquals("Element already exists",ex.getMessage());
    }


    @Test
    @DisplayName("Get AuthToken")
    public void GetAuthToken(){
        AuthTokenDAO Tokens = new AuthTokenList();

        AuthToken test = new AuthToken("Spencer");
        String testauthToken = test.getAuthToken();
        try
        {
            Tokens.insert(test);
            Tokens.insert("Jim");
            Tokens.insert("Harry");
        }catch(DataAccessException ex){

        }

        try
        {
            AuthToken testGet = Tokens.get(testauthToken);
            Assertions.assertEquals(test,testGet);
        }catch(DataAccessException ex){}

    }


    @Test
    @DisplayName("Get Nonexsitant AuthToken")
    public void GetNonAuthToken(){
        AuthTokenDAO Tokens = fillTokens();
        Exception ex = assertThrows(DataAccessException.class,()->{
            Tokens.get("xx9xx9xx");
        });
        Assertions.assertEquals("Element not found",ex.getMessage());
    }

    @Test
    @DisplayName("Delete AuthToken")
    public void DeleteTokens(){
        AuthTokenDAO Tokens = new AuthTokenList();
        ArrayList<AuthToken> TestAuthTokens= fillTestTokens();

        AuthToken test = new AuthToken("Spencer");
        String testauthToken = test.getAuthToken();
        try
        {
            Tokens.insert(test);
            Tokens.insert("Jim");
            Tokens.insert("Harry");
        }catch(DataAccessException ex){

        }

        try
        {
            TestAuthTokens.remove(0);
            Tokens.delete(testauthToken);
            Assertions.assertEquals(2,Tokens.getAll().size());
        }catch(DataAccessException ex){}

    }

    @Test
    @DisplayName("Delete Non AuthToken")
    public void DeleteNonTokens(){
        AuthTokenDAO Tokens = fillTokens();
        Exception ex = assertThrows(DataAccessException.class,()->{
            Tokens.delete("xx9xx9xx");
        });
        Assertions.assertEquals("Element not found",ex.getMessage());

    }


    @Test
    @DisplayName("Delete All AuthTokens")
    public void DeleteAllAuthTokens()
    {
        AuthTokenDAO Tokens = fillTokens();
        Tokens.deleteAll();
        ArrayList<AuthToken> testTokens = new ArrayList();
        Assertions.assertEquals(testTokens,Tokens.getAll());
    }

//    @Test
//    @DisplayName("print All")
//    public void PrintAll(){
//        AuthTokenDAO Tokens = fillTokens();
//        ArrayList<AuthToken> TestTokens = fillTestTokens();
//        String testString = new String();
//        for (AuthToken user:TestTokens)
//        {
//            testString.concat(user.getAuthToken()+" : "+user);
//
//        }
//        Assertions.assertEquals(testString,Tokens.printAll());
//
//    }





}

