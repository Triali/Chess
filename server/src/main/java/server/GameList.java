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
            newGame.setID(allGames.size()+1);

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
        if(ID <= 0)
        {
            throw new DataAccessException("bad request");

        }
        Game game = allGames.get(ID);
        if(color == null){
            //observer
            return;
        }
        if(color.equals("BLACK")){
            if(allGames.get(ID).getBlackUsername()==null){
                allGames.get(ID).setBlackUsername(username);
                return;
            }
        }else if (color.equals("WHITE")){
            if(allGames.get(ID).getWhiteUsername()==null){
                allGames.get(ID).setWhiteUsername(username);
                return;
            }
        }
        throw new DataAccessException("bad color");

    }

    public ArrayList<Game> getAll(){
        ArrayList<Game>  games = new ArrayList();
        for (int i = 1; i <= allGames.size(); i++)
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
