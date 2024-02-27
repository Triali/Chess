package passoffTests.serverTests;

import dataAccess.DataAccessException;
import org.junit.jupiter.api.*;

import server.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestServices
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
            services.CreateGame("game1");
            services.CreateGame("game2");
            services.CreateGame("game3");

            services.CreateUser("Harry", "H_username", "harry@email.com");
            services.CreateUser("Spencer", "S_username", "spencer@email.com");
            services.CreateUser("Jim", "J_username", "jim@email.com");

            String hToken = services.CreateAuthToken("Harry");
            String sToken = services.CreateAuthToken("Spencer");
            String jToken = services.CreateAuthToken("Jim");

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
                services.CreateUser("Jim", "J_username", "jim@email.com"));
        Assertions.assertEquals("Element already exists", ex.getMessage());
    }

    //CreateAuthToken
    @Test
    @DisplayName("Create Duplicate Authtoken")
    void CreateDuplicateAuthToken()
    {
        Exception ex = assertThrows(DataAccessException.class, () ->
                services.CreateAuthToken("Jim"));
        Assertions.assertEquals("Element already exists", ex.getMessage());

    }

    //ClearAll
    @Test
    @DisplayName("Clear All")
    void ClearAll()
    {
        Services test = new Services();
        services.ClearAll();
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
                services.RemoveAuthToken("bfdjlk"));
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
    @Test
    @DisplayName("Get All Games")
    void GetAllGames()
    {
        ArrayList<Game> games = fillGames();
        Assertions.assertEquals(games, services.getAllGames());

    }
    //CreateGame

    //UpdateUsername
    @Test
    @DisplayName("Update Black Username, Empty Game")
    void UpdateUsernameBlackOnEmpty()
    {
        ArrayList<Game> games = fillGames();
        games.get(0).setBlackUsername("Bob");

        try
        {
            services.UpdateUsername(0, "BLACK", "Bob");
        } catch (Exception ex)
        {
        }
        Assertions.assertEquals(games.toString(), services.getAllGames().toString());
    }

    @Test
    @DisplayName("Update Black Username, White in Game")
    void UpdateUsernameBlackOnWhite()
    {
        ArrayList<Game> games = fillGames();
        games.get(0).setBlackUsername("Sue");
        games.get(0).setWhiteUsername("Bob");

        try
        {
            services.UpdateUsername(0, "BLACK", "Sue");
            services.UpdateUsername(0, "WHITE", "Bob");
        } catch (Exception ex)
        {
        }
        Assertions.assertEquals(games.toString(), services.getAllGames().toString());

    }

    @Test
    @DisplayName("Update Black Username, Black in Game")
    void UpdateUsernameBlackOnBlack()
    {
        ArrayList<Game> games = fillGames();
        games.get(0).setBlackUsername("Sue");
        Exception ex = assertThrows(DataAccessException.class, () ->
        {
            services.UpdateUsername(0, "BLACK", "Sue");
            services.UpdateUsername(0, "BLACK", "Sue");
        });
        Assertions.assertEquals("Can not add username to existing game", ex.getMessage());

    }

    @Test
    @DisplayName("Update Black Username, Empty Game")
    void UpdateUsernameWhiteOnEmpty()
    {
        ArrayList<Game> games = fillGames();
        games.get(0).setWhiteUsername("Sue");
        try
        {
            services.UpdateUsername(0, "WHITE", "Sue");
        } catch (Exception ex)
        {
        }
        Assertions.assertEquals(games.toString(), services.getAllGames().toString());
    }

    @Test
    @DisplayName("Update Black Username, White in Game")
    void UpdateUsernameWhiteOnWhite()
    {
        ArrayList<Game> games = fillGames();
        games.get(0).setWhiteUsername("Sue");
        Exception ex = assertThrows(DataAccessException.class, () ->
        {
            services.UpdateUsername(0, "WHITE", "Sue");
            services.UpdateUsername(0, "WHITE", "Sue");
        });
        Assertions.assertEquals("Can not add username to existing game", ex.getMessage());

    }

    @Test
    @DisplayName("Update Black Username, Black in Game")
    void UpdateUsernameWhiteOnBlack()
    {
        ArrayList<Game> games = fillGames();
        games.get(0).setBlackUsername("Bob");
        try
        {
            services.UpdateUsername(0, "WHITE", "Sue");
        } catch (Exception ex)
        {
        }
        Assertions.assertEquals(games.toString(), services.getAllGames().toString());
    }

    @Test
    @DisplayName("Update Username, Bad Color")
    void UpdateUsernameBlue()
    {
        Exception ex = assertThrows(DataAccessException.class, () ->
        {
            services.UpdateUsername(0, "BLUE", "Sue");

        });
        Assertions.assertEquals("Can not add username to existing game", ex.getMessage());

    }

    @Test
    @DisplayName("Update Username, Bad Game ID")
    void UpdateUsernameBadID()
    {
        Exception ex = assertThrows(DataAccessException.class, () ->
        {
            services.UpdateUsername(100, "BLACK", "Sue");

        });
        Assertions.assertEquals("Can not add username to existing game", ex.getMessage());
    }

    //Register
    @Test
    @DisplayName("Register")
    void Register()
    {
        User request = new User("Phil", "P_username", "phil@gmail.com");
        try
        {
            services.Register(request);
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
            services.Register(request);
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
            services.Logout("iubdss");
        });
        Assertions.assertEquals("Element not found", ex.getMessage());
    }

    @Test
    @DisplayName("Logout")
    void Logout()
    {
        try
        {
            services.CreateUser("Phil", "P_username", "phil@gmail.com");
            String pToken = services.CreateAuthToken("Phil");
            services.Logout(pToken);
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
            services.CreateUser("Phil", "P_username", "phil@gmail.com");
            services.Login(request);
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
            services.CreateUser("Phil", "P_username", "phil@gmail.com");
            services.Login(request);
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
            services.CreateUser("Phil", "P_username", "phil@gmail.com");
            services.Login(request);
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
            services.JoinGame(request,"iubdss");
        });
        Assertions.assertEquals("Element not found", ex.getMessage());
    }

    @DisplayName("Join Game")
    void joinGame(){
        JoinGameRequest request = new JoinGameRequest("BLACK",0);
        try{
            services.JoinGame(request,"hToken");
        }catch (Exception ex){}
        ArrayList<Game> games = fillGames();
        games.get(0).setBlackUsername("Harry");
        Assertions.assertEquals(games.toString(), services.getAllGames().toString());
    }
}
