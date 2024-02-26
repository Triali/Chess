package server;

import chess.ChessGame;

import java.util.Objects;

public class Game
{
    private ChessGame game;
    private String name;
    private int ID = -1;
    private String blackUsername = "";
    private String whiteUsername ="";

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
        this.name = name;
        game = new ChessGame();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getID()
    {
        return ID;
    }

    public void setID(int ID)
    {
        this.ID = ID;
    }

    @Override
    public boolean equals(Object o)
    {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game1 = (Game) o;
        return Objects.equals(ID, game1.ID);
    }

    @Override
    public String toString()
    {
        return "Game{"+name +", "+ ID+'}';
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(game);
    }
}
