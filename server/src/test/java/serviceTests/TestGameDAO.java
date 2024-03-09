package serviceTests;

import DataAcesss.GameDAO;
import DataAcesss.GameSql;
import dataAccess.DataAccessException;
import model.Game;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Collection;


public class TestGameDAO
{
    private static GameDAO games;


    public static void setup()
    {
        try
        {
            games = new GameSql();
            games.clear();
        } catch (DataAccessException ex)
        {
            System.out.println("failed to setup");
            System.out.println(ex.getMessage());
            return;
        }
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
    }

    @Test
    @DisplayName("Get All Games")
    public void getAll()
    {
        setup();
        Collection<Game> gameList = new ArrayList<>();
        try
        {
            gameList = games.getAllGames();

        } catch (DataAccessException ex)
        {
            System.out.println("failed to execute getAll");
            System.out.println(ex.getMessage());
        }
        Assertions.assertEquals(3, gameList.size());
    }

    @Test
    @DisplayName("Good Insert")
    public void insert()
    {setup();
        Game game4 = new Game("game4");
        try
        {
            games.insert(game4);
        } catch (DataAccessException ex)
        {
            System.out.println("failed to execute insert");
            System.out.println(ex.getMessage());
        }
        Collection<Game> gameList = new ArrayList<>();
        try
        {
            gameList = games.getAllGames();

        } catch (DataAccessException ex)
        {
            System.out.println("failed to execute getAll");
            System.out.println(ex.getMessage());
        }
        Assertions.assertEquals(4, gameList.size());
    }

    @Test
    @DisplayName("Bad Insert")
    public void badInsert()
    {
        setup();
        Game game4 = new Game("game4");
        game4.setID(1);
        Assertions.assertThrows(DataAccessException.class, () -> games.insert(game4));}

    @Test
    @DisplayName("Good Get")
    public void goodGet()
    {
        setup();
        Game gameA;
        Game gameB;
        Game gameC;
        try
        {
            gameA = games.get(1);
            gameB = games.get(2);
            gameC = games.get(3);
        } catch (Exception ex)
        {
            System.out.println("failed to get game");
            System.out.println(ex.getMessage());
            return;
        }
        Game game1 = new Game("game1");
        game1.setID(1);
        Game game2 = new Game("game2");
        game2.setID(2);
        Game game3 = new Game("game3");
        game3.setID(3);
        game2.setBlackUsername("phil");
        game3.setBlackUsername("Jim");
        game3.setWhiteUsername("Sue");
        Assertions.assertEquals(game1, gameA);
        Assertions.assertEquals(game2, gameB);
        Assertions.assertEquals(game3, gameC);
    }

    @Test
    @DisplayName("Bad Get")
    public void badGet()
    {
setup();
        Assertions.assertThrows(DataAccessException.class, () ->
        {
            games.get(4);
        });
    }

    @Test
    @DisplayName("Good Delete")
    public void goodDelete()
    {
        setup();
        Collection<Game> gameList = new ArrayList<>();
        try
        {
            games.delete(1);
            gameList = games.getAllGames();
        } catch (Exception ex)
        {
            System.out.println("failed to get game");
            System.out.println(ex.getMessage());
            return;
        }
        Assertions.assertEquals(2, gameList.size());
        try
        {
            games.delete(2);
            gameList = games.getAllGames();
        } catch (Exception ex)
        {
            System.out.println("failed to get game");
            System.out.println(ex.getMessage());
            return;
        }
        Assertions.assertEquals(1, gameList.size());
        try
        {
            games.delete(3);
            gameList = games.getAllGames();
        } catch (Exception ex)
        {
            System.out.println("failed to get game");
            System.out.println(ex.getMessage());
            return;
        }
        Assertions.assertEquals(0, gameList.size());
    }

    @Test
    @DisplayName("Bad Delete")
    public void BadDelete()
    {
        setup();
        Collection<Game> gameList = new ArrayList<>();
        try
        {
            games.delete(1);
            gameList = games.getAllGames();
        } catch (Exception ex)
        {
            System.out.println("failed to get game");
            System.out.println(ex.getMessage());
            return;
        }
        Assertions.assertEquals(2, gameList.size());
        Assertions.assertThrows(DataAccessException.class, () ->
                games.delete(1));
    }

    @Test
    @DisplayName("Good PostBlack")
    public void goodPostBlack()
    {
        setup();
        try{
            games.postBlack(1,"nick");
            Assertions.assertEquals("nick", games.get(1).getBlackUsername());
        }catch (Exception ex)
        {
            System.out.println("failed to post Black");
            System.out.println(ex.getMessage());
            return;
        }

    }

    @Test
    @DisplayName("Bad PostBlack")
    public void BadPostBlack()
    {
        setup();
        Assertions.assertThrows(DataAccessException.class, () ->
                games.postBlack(4,"nick"));
    }

    @Test
    @DisplayName("Good PostWhite")
    public void goodPostWhite()
    {
        setup();
        try{
            games.postBlack(1,"nick");
            games.postWhite(1,"Sally");
            Assertions.assertEquals("nick", games.get(1).getBlackUsername());
            Assertions.assertEquals("Sally", games.get(1).getWhiteUsername());
        }catch (Exception ex)
        {
            System.out.println("failed to post White");
            System.out.println(ex.getMessage());
            return;
        }
    }

    @Test
    @DisplayName("Bad PostWhite")
    public void BadPostWhite()
    {
        setup();
        Assertions.assertThrows(DataAccessException.class, () ->
                games.postWhite(4,"nick"));
    }

    @Test
    @DisplayName("Good PostObserver")
    public void goodPostObserver()
    {
        setup();
        try{
            games.postObserver(1,"nick");
            Assertions.assertNotEquals("nick", games.get(1).getBlackUsername());
            Assertions.assertNotEquals("nick", games.get(1).getWhiteUsername());
        }catch (Exception ex)
        {
            System.out.println("failed to post Observer");
            System.out.println(ex.getMessage());
            return;
        }
    }

    @Test
    @DisplayName("Bad PostObserver")
    public void BadPostObserver()
    {
        setup();
        Assertions.assertThrows(DataAccessException.class, () ->
                games.postObserver(4,"nick"));
    }

    @Test
    @DisplayName("Good Clear")
    public void goodClear()
    {setup();
        Collection<Game> gameList = new ArrayList<>();
        try{
            games.clear();
            gameList = games.getAllGames();
        }catch (Exception ex)
        {
            System.out.println("failed to Clear");
            System.out.println(ex.getMessage());
            return;
        }
        Assertions.assertEquals(0,gameList.size());
    }


}
