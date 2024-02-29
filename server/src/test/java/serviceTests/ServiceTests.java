package serviceTests;

import dataAccess.DataAccessException;
import model.Game;
import model.User;
import org.junit.jupiter.api.*;

import server.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServiceTests
{
    Services services;
    String hToken;

    ArrayList<Game> fillGames()
    {
        ArrayList<Game> games = new ArrayList();
        Game game1 = new Game("game1");
        game1.setID(0);
        games.add(game1);
        Game game2 = new Game("game2");
        game2.setID(1);
        games.add(game2);
        Game game3 = new Game("game3");
        game3.setID(2);
        games.add(game3);
        return games;
    }

    @BeforeEach
    public void setup()
    {
        services = new Services();
        try
        {
            String hToken = services.createAuthToken("Harry");
            String sToken = services.createAuthToken("Spencer");
            String jToken = services.createAuthToken("Jim");
            services.createGame("game1",hToken);
            services.createGame("game2",sToken);
            services.createGame("game3",jToken);

            services.createUser("Harry", "H_username", "harry@email.com");
            services.createUser("Spencer", "S_username", "spencer@email.com");
            services.createUser("Jim", "J_username", "jim@email.com");



        } catch (Exception ex)
        {
        }

    }

    //getUser
    @Test
    @DisplayName("GetUser")
    void getUser()
    {
        User test = new User("Harry", "H_username", "harry@email.com");

        try
        {
            Assertions.assertEquals(test, services.getUser("Harry"));
        } catch (Exception ex)
        {
        }
    }

    @Test
    @DisplayName("Get Nonexsistant User")
    void getNonUser()
    {
        Exception ex = assertThrows(DataAccessException.class, () ->
        {
            services.getUser("Phil");
        });
        Assertions.assertEquals("Element not found", ex.getMessage());
    }

    //CreateUser
    @Test
    @DisplayName("Create Duplicate User")
    void CreateDuplicateUser()
    {
        Exception ex = assertThrows(DataAccessException.class, () ->
                services.createUser("Jim", "J_username", "jim@email.com"));
        Assertions.assertEquals("Element already exists", ex.getMessage());
    }

    //CreateAuthToken
//    @Test
//    @DisplayName("Create Duplicate Authtoken")
//    void CreateDuplicateAuthToken()
//    {
//        Exception ex = assertThrows(DataAccessException.class, () ->
//                services.createAuthToken("Jim"));
//        Assertions.assertEquals("Element already exists", ex.getMessage());
//
//    }

    //ClearAll
    @Test
    @DisplayName("Clear All")
    void ClearAll()
    {
        Services test = new Services();
        services.clearAll();
        Assertions.assertTrue(services.getTokens().getAll().isEmpty()&&
                services.getUsers().getAll().isEmpty()&&
                services.getGames().getAll().isEmpty());
    }

    //getUserName
    @Test
    @DisplayName("Get Username from AuthToken")
    void GetUsername()
    {
        try
        {
            Assertions.assertEquals("Harry", services.getUserName(hToken));
        } catch (Exception ex)
        {
        }
    }

    @Test
    @DisplayName("Get Nonexistant Username from AuthToken")
    void GetNonUsername()
    {
        Exception ex = assertThrows(DataAccessException.class, () ->
                services.getUserName("bfdjlk"));
        Assertions.assertEquals("Element not found", ex.getMessage());
    }

    //RemoveAuthToken
    @Test
    @DisplayName("Remove Nonexistant AuthToken")
    void RemoveNonAuthToken()
    {
        Exception ex = assertThrows(DataAccessException.class, () ->
                services.removeAuthToken("bfdjlk"));
        Assertions.assertEquals("Element not found", ex.getMessage());
    }

    //GetPassword
    @Test
    @DisplayName("Get Password from Username")
    void GetPassword()
    {
        try
        {
            Assertions.assertEquals("H_username", services.getPassword("Harry"));
        } catch (Exception ex)
        {
        }
    }

    @Test
    @DisplayName("Get Password From Nonexistant Username")
    void GetNonPassword()
    {
        Exception ex = assertThrows(DataAccessException.class, () ->
                services.getPassword("Phil"));
        Assertions.assertEquals("Element not found", ex.getMessage());

    }

    //getAllGames
//    @Test
//    @DisplayName("Get All Games")
//    void GetAllGames()
//    {
//        ArrayList<Game> games = fillGames();
//        Assertions.assertEquals(games, services.getAllGames());
//
//    }
    //CreateGame

    //UpdateUsername
//    @Test
//    @DisplayName("Update Black Username, Empty Game")
//    void UpdateUsernameBlackOnEmpty()
//    {
//        ArrayList<Game> games = fillGames();
//        games.get(0).setBlackUsername("Bob");
//
//        try
//        {
//            services.updateUsername(0, "BLACK", "Bob");
//        } catch (Exception ex)
//        {
//        }
//        Assertions.assertEquals(games.toString(), services.getAllGames().toString());
//    }

//    @Test
//    @DisplayName("Update Black Username, White in Game")
//    void UpdateUsernameBlackOnWhite()
//    {
//        ArrayList<Game> games = fillGames();
//        games.get(0).setBlackUsername("Sue");
//        games.get(0).setWhiteUsername("Bob");
//
//        try
//        {
//            services.updateUsername(0, "BLACK", "Sue");
//            services.updateUsername(0, "WHITE", "Bob");
//        } catch (Exception ex)
//        {
//        }
//        Assertions.assertEquals(games.toString(), services.getAllGames().toString());
//
//    }
//
//    @Test
//    @DisplayName("Update Black Username, Black in Game")
//    void UpdateUsernameBlackOnBlack()
//    {
//        ArrayList<Game> games = fillGames();
//        games.get(0).setBlackUsername("Sue");
//        Exception ex = assertThrows(DataAccessException.class, () ->
//        {
//            services.updateUsername(0, "BLACK", "Sue");
//            services.updateUsername(0, "BLACK", "Sue");
//        });
//        Assertions.assertEquals("Can not add username to existing game", ex.getMessage());
//
//    }
//
//    @Test
//    @DisplayName("Update Black Username, Empty Game")
//    void UpdateUsernameWhiteOnEmpty()
//    {
//        ArrayList<Game> games = fillGames();
//        games.get(0).setWhiteUsername("Sue");
//        try
//        {
//            services.updateUsername(0, "WHITE", "Sue");
//        } catch (Exception ex)
//        {
//        }
//        Assertions.assertEquals(games.toString(), services.getAllGames().toString());
//    }
//
//    @Test
//    @DisplayName("Update Black Username, White in Game")
//    void UpdateUsernameWhiteOnWhite()
//    {
//        ArrayList<Game> games = fillGames();
//        games.get(0).setWhiteUsername("Sue");
//        Exception ex = assertThrows(DataAccessException.class, () ->
//        {
//            services.updateUsername(0, "WHITE", "Sue");
//            services.updateUsername(0, "WHITE", "Sue");
//        });
//        Assertions.assertEquals("Can not add username to existing game", ex.getMessage());
//
//    }

//    @Test
//    @DisplayName("Update Black Username, Black in Game")
//    void UpdateUsernameWhiteOnBlack()
//    {
//        ArrayList<Game> games = fillGames();
//        games.get(0).setBlackUsername("Bob");
//        try
//        {
//            services.updateUsername(0, "WHITE", "Sue");
//        } catch (Exception ex)
//        {
//        }
//        Assertions.assertEquals(games.toString(), services.getAllGames().toString());
//    }

//    @Test
//    @DisplayName("Update Username, Bad Color")
//    void UpdateUsernameBlue()
//    {
//        Exception ex = assertThrows(DataAccessException.class, () ->
//        {
//            services.updateUsername(0, "BLUE", "Sue");
//
//        });
//        Assertions.assertEquals("Can not add username to existing game", ex.getMessage());
//
//    }

//    @Test
//    @DisplayName("Update Username, Bad Game ID")
//    void UpdateUsernameBadID()
//    {
//        Exception ex = assertThrows(DataAccessException.class, () ->
//        {
//            services.updateUsername(100, "BLACK", "Sue");
//
//        });
//        Assertions.assertEquals("Can not add username to existing game", ex.getMessage());
//    }

    //Register
    @Test
    @DisplayName("Register")
    void Register()
    {
        User request = new User("Phil", "P_username", "phil@gmail.com");
        try
        {
            services.register(request);
        } catch (Exception ex)
        {
        }
        Assertions.assertEquals(4, services.getUsers().getAll().size());
    }

    @Test
    @DisplayName("Register, Username DNE")
    void RegisterBadUsername()
    {
        User request = new User("Harry", "H_username", "harry@email.com");
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
        {
            services.register(request);
        });
        Assertions.assertEquals("Username already exists", ex.getMessage());
    }

    //Logout
    @Test
    @DisplayName("Logout, AuthToken DNE")
    void LogoutBADAuthToken()
    {
        Exception ex = assertThrows(DataAccessException.class, () ->
        {
            services.logout("iubdss");
        });
        Assertions.assertEquals("Element not found", ex.getMessage());
    }

    @Test
    @DisplayName("Logout")
    void Logout()
    {
        try
        {
            services.createUser("Phil", "P_username", "phil@gmail.com");
            String pToken = services.createAuthToken("Phil");
            services.logout(pToken);
        } catch (Exception ex)
        {
        }
        Assertions.assertEquals(3, services.getTokens().getAll().size());
    }

    //Login
    @Test
    @DisplayName("Login, Username DNE")
    void LoginBadUsername()
    {
        LoginRequest request = new LoginRequest("Phill", "P_username");
        Exception ex = assertThrows(DataAccessException.class, () ->
        {
            services.createUser("Phil", "P_username", "phil@gmail.com");
            services.login(request);
        });
        Assertions.assertEquals("Element not found", ex.getMessage());

    }

    @Test
    @DisplayName("Login, Password Incorrect")
    void LoginBadPassword()
    {
        LoginRequest request = new LoginRequest("Phil", "P_username!");
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
        {
            services.createUser("Phil", "P_username", "phil@gmail.com");
            services.login(request);
        });
        Assertions.assertEquals("Incorrect Password", ex.getMessage());

    }

    @Test
    @DisplayName("Login")
    void Login()
    {
        LoginRequest request = new LoginRequest("Phil", "P_username");
        try
        {
            services.createUser("Phil", "P_username", "phil@gmail.com");
            services.login(request);
        } catch (Exception ex)
        {
        }
        Assertions.assertEquals(4,services.getTokens().getAll().size());
    }

    //JoinGame
    @Test
    @DisplayName("Join Game, AuthToken DNE")
    void JoinGameBadAuthToken()
    {
        JoinGameRequest request = new JoinGameRequest("BLACK",0);
        Exception ex = assertThrows(DataAccessException.class, () ->
        {
            services.joinGame(request,"iubdss");
        });
        Assertions.assertEquals("Element not found", ex.getMessage());
    }

    @DisplayName("Join Game")
    void joinGame(){
        JoinGameRequest request = new JoinGameRequest("BLACK",0);
        try{
            services.joinGame(request,"hToken");
        }catch (Exception ex){}
        ArrayList<Game> games = fillGames();
        games.get(0).setBlackUsername("Harry");
        Assertions.assertEquals(games.toString(), services.getAllGames().toString());
    }
}
