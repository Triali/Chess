package clientTests;

import DataAcesss.*;
import dataAccess.DataAccessException;
import model.AuthToken;
import model.Game;
import model.User;
import org.junit.jupiter.api.*;
import org.springframework.security.crypto.bcrypt.*;
import records.CreateGameRequest;
import records.GameReturn;
import records.JoinGameRequest;
import records.LoginRequest;
import server.Server;
import ClientSupport.*;


import java.io.IOException;


public class ServerFacadeTests {


    private static Server server;
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private AuthTokenDAO tokens;
    private GameDAO games;
    String auth1;
    String auth2;
    String auth3;
    private UserDAO users;
    String password1;
    String password2;
    String password3;
    static ServerFacade serverFacade;
    int id1;



    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(8080);
        System.out.println("Started test HTTP server on " + port);
        serverFacade =  new ServerFacade();
    }

    public void setup()
    {
        try
        {
            tokens = new AuthTokenSql();
            tokens.clear();
            games = new GameSql();
            games.clear();
            users = new UserSql();
            users.clear();
        } catch (DataAccessException ex)
        {
            System.out.println("failed to setup");
            System.out.println(ex.getMessage());
            return;
        }
        ////
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
        ////
        Game game1 = new Game("game1");

        Game game2 = new Game("game2");
        Game game3 = new Game("game3");
        game2.setBlackUsername("phil");
        game3.setBlackUsername("Jim");
        game3.setWhiteUsername("Sue");
        try
        {
            games.insert(game1);
            games.insert(game2);
            games.insert(game3);


        } catch (DataAccessException ex)
        {
            System.out.println("failed to execute insert");
            System.out.println(ex.getMessage());
        }
        ////
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


    @AfterAll
     public static void  stopServer() {
        server.stop();
    }

    @Test
    @DisplayName("Good Register")
    public void registerGood() {
        setup();
        User user4 = new User("user4", "pass4","4@email.com");
        try
        {
            serverFacade.register(user4);
        }catch(Exception ex){
            System.out.println("Failed to Register");
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
        Assertions.assertTrue(encoder.matches(user4.getPassword(), testUser.getPassword()));
        Assertions.assertEquals(user4.getEmail(), testUser.getEmail());

    }

    @Test
    @DisplayName("Bad Register")
    public void registerBad() {
        setup();
        User user4 = new User("user1", "pass4","4@email.com");
        Assertions.assertThrows(Exception.class, () ->serverFacade.register(user4));
    }

    @Test
    @DisplayName("Good Login")
    public void loginGood() {
        setup();

        String testAuth = null;
        String testName = null;
        try{
            tokens.clear();
            LoginRequest logReq = new LoginRequest("user1","pass1");
            testAuth = serverFacade.login(logReq).authToken();
        }catch (Exception ex){
            System.out.println("failed to Login");
        }
        try{
            testName = tokens.get(testAuth).getUsername();
        } catch (Exception ex){
        System.out.println("failed to get Auth");
    }
        Assertions.assertEquals("user1",testName);

    }

    @Test
    public void loginBad()
    {
        setup();
        Assertions.assertThrows(Exception.class, () -> serverFacade.login(new LoginRequest("user1","pass2")));
    }

    @Test
    public void logoutGood() {
        setup();
        try{
            serverFacade.logout(auth1);
        }catch (Exception ex){
            System.out.println("failed to Login");
        }
        Assertions.assertThrows(Exception.class, () -> tokens.get(auth1));
    }

    @Test
    public void logoutBad() {
        setup();
        Assertions.assertThrows(Exception.class, () -> serverFacade.logout("qwertyuiop"));
    }

    @Test
    public void createGameGood() {
        setup();
        CreateGameRequest testGame = new CreateGameRequest("TestGame");
        try{
            serverFacade.createGame(testGame, auth1);
        }catch(Exception ex){
            System.out.println("failed to create game");
        }
    }

    @Test
    public void createGameBad() {
        setup();
        CreateGameRequest testGame = new CreateGameRequest("TestGame");
        Assertions.assertThrows(Exception.class, () -> serverFacade.createGame(testGame,  "qwertyuiop"));

    }

    @Test
    public void joinGameGood() {
        setup();
        Game gameA = new Game("testGame");
        gameA.setID(1);
        gameA.setWhiteUsername("user1");
        JoinGameRequest joinReq = new JoinGameRequest("WHITE",1);
        Game testGame = null;
        try{
            serverFacade.joinGame(joinReq,auth1);
        }catch(Exception ex){
            System.out.println("failed to join game");
        }
        try{
            testGame = games.get(1);
        }catch(Exception ex){
            System.out.println("failed to get game");
        }
        Assertions.assertEquals(gameA,testGame);
    }

    @Test
    public void joinGameBad() {
        setup();
        setup();
        Game gameA = new Game("testGame");
        gameA.setID(1);
        gameA.setWhiteUsername("user1");
        JoinGameRequest joinReq = new JoinGameRequest("WHITE",6);
        Assertions.assertThrows(Exception.class, () -> serverFacade.joinGame(joinReq,  auth1));
    }
//
//    @Test
//    public void listGamesGood() {
//        setup();
//        Assertions.assertTrue(true);
//    }
//
//    @Test
//    public void listGamesBad() {
//        setup();
//        Assertions.assertTrue(true);
//    }

}

