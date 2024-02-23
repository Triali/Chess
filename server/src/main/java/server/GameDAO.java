package server;

import chess.ChessGame;

interface GameDAO
{

    public ChessGame get();

    public void insert();

    public void delete();

    public void post();
}
