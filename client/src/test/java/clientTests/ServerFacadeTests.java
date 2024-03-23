package clientTests;

import DataAcesss.*;
import dataAccess.DataAccessException;
import model.AuthToken;
import model.Game;
import model.User;
import org.junit.jupiter.api.*;
import org.springframework.security.crypto.bcrypt.*;
import records.*;
import server.Server;
import ClientSupport.*;


import java.util.ArrayList;
import java.util.Collection;


public class ServerFacadeTests {


    private static Server server;

    String auth1;
    String auth2;
    String auth3;

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

    @BeforeEach
    public void setup(){
        try{
            serverFacade.clear();
        }catch(Exception ex){
            System.out.println("failed to clear");
        }
    }



    @AfterAll
     public static void  stopServer() {
        server.stop();
    }

    @Test
    @DisplayName("Good Register")
    public void registerGood() {
        User user1 = new User("user1", "pass1","1@email.com");
        User user2 = new User("user2","pass2","2@email.com");
        User user3 = new User("user3","pass3","3@email.com");
        LoginResponce res1;
        LoginResponce res2;
        LoginResponce res3;

        try
        {
            res1 =serverFacade.register(user1);


        } catch (Exception ex)
        {
            System.out.println("failed to execute Register");
            System.out.println(ex.getMessage());
            Assertions.fail();
            return;
        }
        Assertions.assertEquals("user1", res1.username());

    }

    @Test
    @DisplayName("Bad Register")
    public void registerBad() {
        User user1 = new User("user1", "pass1","1@email.com");
        LoginResponce res1;
        try
        {
          res1=  serverFacade.register(user1);

        } catch (Exception ex)
        {
            System.out.println("failed to execute Register");
            System.out.println(ex.getMessage());
            Assertions.fail();
            return;
        }
        User user4 = new User("user1", "pass4","4@email.com");
        Assertions.assertThrows(Exception.class, () ->serverFacade.register(user4));
    }

    @Test
    @DisplayName("Good Login")
    public void loginGood() {

        User user1 = new User("user1", "pass1","1@email.com");
        LoginResponce res1;
        LoginResponce testAuth;
        try
        {
            res1 =serverFacade.register(user1);

        } catch (Exception ex)
        {
            System.out.println("failed to execute Register");
            System.out.println(ex.getMessage());
            Assertions.fail();
            return;
        }
        try{

            LoginRequest logReq = new LoginRequest("user1","pass1");
            testAuth = serverFacade.login(logReq);
        }catch (Exception ex){
            System.out.println("failed to Login");
            Assertions.fail();
            return;
        }
        Assertions.assertEquals(res1.username(),testAuth.username());

    }

    @Test
    public void loginBad()
    {
        Assertions.assertThrows(Exception.class, () -> serverFacade.login(new LoginRequest("user1","pass2")));
    }

    @Test
    public void logoutGood() {
//
//        try{
//            serverFacade.logout(auth1);
//        }catch (Exception ex){
//            System.out.println("failed to Login");
//        }
//        Assertions.assertThrows(Exception.class, () -> tokens.get(auth1));
    }

    @Test
    public void logoutBad() {

        Assertions.assertThrows(Exception.class, () -> serverFacade.logout("qwertyuiop"));
    }

    @Test
    public void createGameGood() {

        CreateGameRequest testGame = new CreateGameRequest("TestGame");
        try{
            serverFacade.createGame(testGame, auth1);
        }catch(Exception ex){
            System.out.println("failed to create game");
        }
    }

    @Test
    public void createGameBad() {

        CreateGameRequest testGame = new CreateGameRequest("TestGame");
        Assertions.assertThrows(Exception.class, () -> serverFacade.createGame(testGame,  "qwertyuiop"));

    }

    @Test
    public void joinGameGood() {
//
//        Game gameA = new Game("testGame");
//        gameA.setID(1);
//        gameA.setWhiteUsername("user1");
//        JoinGameRequest joinReq = new JoinGameRequest("WHITE",1);
//        Game testGame = null;
//        try{
//            serverFacade.joinGame(joinReq,auth1);
//        }catch(Exception ex){
//            System.out.println("failed to join game");
//        }
//        try{
//            testGame = games.get(1);
//        }catch(Exception ex){
//            System.out.println("failed to get game");
//        }
//        Assertions.assertEquals(gameA,testGame);
    }

    @Test
    public void joinGameBad() {


        Game gameA = new Game("testGame");
        gameA.setID(1);
        gameA.setWhiteUsername("user1");
        JoinGameRequest joinReq = new JoinGameRequest("WHITE",6);
        Assertions.assertThrows(Exception.class, () -> serverFacade.joinGame(joinReq,  auth1));
    }

    @Test
    public void listGamesGood() {
        User user1 = new User("user1", "pass1","1@email.com");

        try
        {
            auth1 = serverFacade.register(user1).authToken();


        } catch (Exception ex)
        {
            System.out.println("failed to execute Register");
            System.out.println(ex.getMessage());
            Assertions.fail();
            return;
        }
        Collection<GameReturn> gameList = new ArrayList<>();
        AllGamesReturn allGames = null;

        CreateGameRequest game1 = new CreateGameRequest("TestGame");
        CreateGameRequest game2 = new CreateGameRequest("TestGame");
        CreateGameRequest game3 = new CreateGameRequest("TestGame");
        try{
            serverFacade.createGame(game1, auth1);
            serverFacade.createGame(game1, auth1);
            serverFacade.createGame(game1, auth1);
        }catch(Exception ex){
            System.out.println("failed to create game");
        }
        try
        {
            allGames = serverFacade.listGames(auth1);
            gameList = allGames.games();
        } catch (Exception ex)
        {
            System.out.println("failed to execute listall");
            System.out.println(ex.getMessage());
        }
        Assertions.assertEquals(3, gameList.size());
    }

    @Test
    public void listGamesBad() {

        Collection<GameReturn> gameList = new ArrayList<>();
        AllGamesReturn allGames = null;
        Assertions.assertThrows(Exception.class, () -> serverFacade.listGames("ydtcuvio"));
    }

}

