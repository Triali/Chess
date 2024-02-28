package server;

import dataAccess.DataAccessException;
import model.Game;
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
    public int insert(String name) throws DataAccessException
    {
        Game newGame = new Game(name);
        if(newGame.getID() == -1)
        {
            newGame.setID(allGames.size());

        }
        int id = newGame.getID();

        if(allGames.containsKey(id)){
            throw new DataAccessException("Element already exists");
        }else{
            allGames.put(id,newGame);
            return id;
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

    public void post(int ID, String color,String username)throws DataAccessException
    {
        if(ID < 0 || ID >= allGames.size())
        {
            throw new DataAccessException("bad request");
        }
        Game game = allGames.get(ID);
        if(color == "BLACK"){
            if(allGames.get(ID).getBlackUsername() == ""){
                allGames.get(ID).setBlackUsername(username);
                return;
            }
        }else if (color == "WHITE"){
            if(allGames.get(ID).getWhiteUsername() == ""){
                allGames.get(ID).setWhiteUsername(username);
                return;
            }
        }
        throw new DataAccessException("Can not add username to existing game");

    }

    public ArrayList<Game> getAll(){
        ArrayList<Game> games = new ArrayList();
        for (int i = 0; i < allGames.size(); i++)
        {
            games.add(allGames.get(i));
        }


//        allGames.forEach((key, value)->{
//            games.add(value);
//        });
        return games;
    }
    public void deleteAll(){
        allGames.clear();
    }
}
