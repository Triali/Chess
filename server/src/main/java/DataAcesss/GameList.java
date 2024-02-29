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
        if(allGames.containsKey(id)){
            return allGames.get(id);
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

    public void delete(int id) throws DataAccessException
    {
        if(allGames.containsKey(id)){
            allGames.remove(id);
        }
        else{
            throw new DataAccessException("Element not found");
        }



        allGames.remove(id);

    }

    public void post(int id, String color, String username)throws DataAccessException
    {
        if(id <= 0)
        {
            throw new DataAccessException("bad request");

        }
        Game game = allGames.get(id);
        if(color == null){
            //observer
            return;
        }
        if(color.equals("BLACK")){
            if(allGames.get(id).getBlackUsername()==null){
                allGames.get(id).setBlackUsername(username);
                return;
            }
        }else if (color.equals("WHITE")){
            if(allGames.get(id).getWhiteUsername()==null){
                allGames.get(id).setWhiteUsername(username);
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
