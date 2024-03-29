package model;

import chess.ChessBoard;
import chess.ChessGame;

import java.util.Objects;

public class Game
{
    private int id = -1;
    private String blackUsername = null;
    private String whiteUsername =null;
    private String gameName = null;
    private ChessGame game = null;




    public String getBlackUsername()
    {
        return blackUsername;
    }

    public void setBlackUsername(String blackUsername)
    {
        this.blackUsername = blackUsername;
    }

    public String getWhiteUsername()
    {
        return whiteUsername;
    }

    public void setWhiteUsername(String whiteUsername)
    {
        this.whiteUsername = whiteUsername;
    }

    public Game(String name)
    {
         ChessBoard board = new ChessBoard();
         board.initalSetup();
        this.gameName = name;
        game = new ChessGame(board, ChessGame.TeamColor.WHITE);

    }

    public String getGameName()
    {
        return gameName;
    }

    public int getID()
    {
        return id;
    }

    public void setID(int id)
    {
        this.id = id;
    }

    @Override
    public boolean equals(Object o)
    {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game1 = (Game) o;
        return Objects.equals(id, game1.id);
    }

    @Override
    public String toString()
    {
        return "Game{"+ gameName +", "+ id+'}';
    }

    public void setGameName(String gameName)
    {
        this.gameName = gameName;
    }

    public ChessGame getGame()
    {
        return game;
    }

    public void setGame(ChessGame game)
    {
        this.game = game;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(game);
    }
}
