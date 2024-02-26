package server;

import chess.ChessGame;
import server.Game;
import java.util.*;

public class GameList implements GameDAO
{
    HashMap<String, Game> allGames;

    public Game get(String ID){
        return allGames.get(ID);
    }
    public void insert(Game newGame)
    {

        String iD =  String.valueOf(allGames.size());
        newGame.setID(iD);



        allGames.put(iD,newGame);
    }

    public void delete(String ID)
    {
        allGames.remove(ID);

    }

    public void post()
    {

    }
}
