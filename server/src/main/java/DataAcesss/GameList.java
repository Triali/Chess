package DataAcesss;

import DataAcesss.GameDAO;
import dataAccess.DataAccessException;
import model.Game;

import java.util.*;

public class GameList implements GameDAO
{
    HashMap<Integer, Game> allGames = new HashMap();

    public Game get(int id) throws DataAccessException
    {
        Game game = allGames.get(id);
        if (game != null)
        {
            return game;
        }
        throw new DataAccessException("Error: unauthorized");
    }

    public void insert(Game game) throws DataAccessException
    {
        if (game.getID() == -1)
        {
            game.setID(allGames.size() + 1);
        }
        int id = game.getID();

        if (allGames.containsKey(id))
        {
            throw new DataAccessException("Element already exists");
        } else
        {
            allGames.put(id, game);
        }
    }

    public void delete(int id) throws DataAccessException
    {
        allGames.remove(id);
    }

    public void postWhite(int id, String username) throws DataAccessException
    {
        allGames.get(id).setWhiteUsername(username);
    }

    public void postBlack(int id, String username) throws DataAccessException
    {
        allGames.get(id).setBlackUsername(username);
    }

    public void postObserver(int id, String username) throws DataAccessException
    {
        allGames.get(id);
    }

    public void clear()throws DataAccessException
    {
        allGames.clear();
    }

    public ArrayList<Game> getAllGames() throws DataAccessException
    {
        ArrayList<Game> games = new ArrayList();
        for (int i = 1; i <= allGames.size(); i++)
        {
            games.add(allGames.get(i));
        }
        return games;
    }

}
