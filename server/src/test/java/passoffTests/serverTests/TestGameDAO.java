package passoffTests.serverTests;
import chess.ChessGame;
import dataAccess.DataAccessException;
import org.junit.jupiter.api.*;
import server.Game;
import server.GameDAO;
import server.GameList;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
public class TestGameDAO
{
    GameDAO fillGames()
    {
        GameDAO Games = new GameList();
        try
        {
            Games.insert(new Game("game1","Tod","Bob") );
            Games.insert(new Game("game2","Phil","Sue") );
            Games.insert(new Game("game3","Jan","Ted") );
        } catch (DataAccessException ex)
        {

        }
        return Games;
    }

    ArrayList<Game> fillTestGames()
    {
        ArrayList<Game> TestGames = new ArrayList();
        TestGames.add(new Game("game1","Tod","Bob") );
        TestGames.add(new Game("game2","Phil","Sue") );
        TestGames.add(new Game("game3","Jan","Ted") );
        for (int i = 0; i < TestGames.size(); i++)
        {
            TestGames.get(i).setID(i);
        }

        return TestGames;
    }


    @Test
    @DisplayName("Add Games")
    public void InsertGames()
    {
        GameDAO Games = fillGames();
        ArrayList allGames = Games.getAll();
        ArrayList TestGames = fillTestGames();

//    System.out.println(allGames);
//    System.out.println("----");
//    System.out.println(TestGames);
        Assertions.assertEquals(TestGames,allGames);
    }

//    @Test
//    @DisplayName("Add Duplicate Game")
//    public void InsertDuplicateGame()
//    {
//        GameDAO Games = fillGames();
//        Exception ex = assertThrows(DataAccessException.class, () ->
//        {
//            Games.insert("Jim", "J_username", "jim@email.com");
//        });
//        Assertions.assertEquals("Element already exists", ex.getMessage());
//    }


    @Test
    @DisplayName("Get Game")
    public void GetGame()
    {
        GameDAO Games = fillGames();
        Game testGet;
        Game test = new Game("game2","Phil","Sue");
        test.setID(3);
        try
        {
            testGet = Games.get(3);
            Assertions.assertEquals(test,testGet);
        } catch (DataAccessException ex)
        {
        }

    }


    @Test
    @DisplayName("Get Nonexsitant Game")
    public void GetNonGame()
    {
        GameDAO Games = fillGames();
        Exception ex = assertThrows(DataAccessException.class, () ->
        {
            Games.get(10);
        });
        Assertions.assertEquals("Element not found", ex.getMessage());
    }

    @Test
    @DisplayName("Delete Game")
    public void DeleteGames()
    {
        GameDAO Games = fillGames();
        ArrayList<Game> testGames = fillTestGames();
        try
        {
            testGames.remove(2);
            Games.delete(3);
            Assertions.assertEquals(Games.getAll(), testGames);
        } catch (DataAccessException ex)
        {
        }

    }

    @Test
    @DisplayName("Delete Non Game")
    public void DeleteNonGames()
    {
        GameDAO Games = fillGames();
        Exception ex = assertThrows(DataAccessException.class, () ->
        {
            Games.delete(100);
        });
        Assertions.assertEquals("Element not found", ex.getMessage());

    }


    @Test
    @DisplayName("Delete All Games")
    public void DeleteAllGames()
    {
        GameDAO Games = fillGames();
        Games.deleteAll();
        ArrayList<Game> TestGames = new ArrayList();
        Assertions.assertEquals(TestGames,Games.getAll());
    }

//    @Test
//    @DisplayName("print All")
//    public void PrintAll()
//    {
//        GameDAO Games = fillGames();
//        ArrayList<Game> TestGames = fillTestGames();
//        String testString = new String();
//        for (Game user : TestGames)
//        {
//            testString.concat(user.getGameName() + " : " + user);
//
//        }
//        Assertions.assertEquals(testString, Games.printAll());
//
//    }


}
