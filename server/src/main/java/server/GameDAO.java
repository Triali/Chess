package server;

import chess.ChessGame;
import dataAccess.DataAccessException;

import java.util.ArrayList;

public interface GameDAO
{

    public Game get(int ID) throws DataAccessException;

    public void insert(Game newGame) throws DataAccessException;

    public void delete(int ID) throws DataAccessException;

    public void post();

    public ArrayList<Game> getAll();
}
