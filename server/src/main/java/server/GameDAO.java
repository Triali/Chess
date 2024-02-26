package server;

import chess.ChessGame;
import dataAccess.DataAccessException;

import java.util.ArrayList;

public interface GameDAO
{

    public Game get(int ID) throws DataAccessException;

    public void insert(String name) throws DataAccessException;

    public void delete(int ID) throws DataAccessException;

    public void post(int ID, String color,String username) throws DataAccessException;

    public ArrayList<Game> getAll();

    public void deleteAll();
}
