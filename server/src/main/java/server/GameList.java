package server;

import chess.ChessGame;
import dataAccess.DataAccessException;
import server.Game;
import java.util.*;

public class GameList implements GameDAO
{
    HashMap<Integer, Game> allGames = new HashMap();

    public Game get(int ID) throws DataAccessException
    {
        if(allGames.containsKey(ID)){
            return allGames.get(ID);
        }
        else{
            throw new DataAccessException("Element not found");
        }

    }
    public void insert(Game newGame) throws DataAccessException
    {
        if(newGame.getID() == -1)
        {
            newGame.setID(allGames.size());

        }
        int id = newGame.getID();

        if(allGames.containsKey(id)){
            throw new DataAccessException("Element already exists");
        }else{
            allGames.put(id,newGame);
        }
    }

    public void delete(int ID) throws DataAccessException
    {
        if(allGames.containsKey(ID)){
            allGames.remove(ID);
        }
        else{
            throw new DataAccessException("Element not found");
        }



        allGames.remove(ID);

    }

    public void post()
    {

    }

    public ArrayList<Game> getAll(){
        ArrayList<Game> games = new ArrayList();
        allGames.forEach((key, value)->{
            games.add(value);
        });
        return games;
    }
}
