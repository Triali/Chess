package DataAcesss;

import dataAccess.DataAccessException;
import model.Game;

import java.util.ArrayList;

public interface GameDAO
{

    public Game get(int id) throws DataAccessException;

    public void insert(Game game) throws DataAccessException;

    public void delete(int id) throws DataAccessException;

    public void postBlack(int id,String username)throws DataAccessException;
    public void postWhite(int id,String username)throws DataAccessException;
    public void postObserver(int id, String username)throws DataAccessException;
    public void clear();
    public ArrayList<Game> getAllGames();
}
