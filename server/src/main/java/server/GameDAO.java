package server;

import chess.ChessGame;

interface GameDAO
{

    public Game get(String ID);

    public void insert(Game newGame);

    public void delete(String ID);

    public void post();
}
