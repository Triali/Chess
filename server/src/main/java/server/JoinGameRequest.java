package server;

import java.util.Objects;

public class JoinGameRequest
{
    private String color;
    private int gameID;

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JoinGameRequest joinGame = (JoinGameRequest) o;
        return gameID == joinGame.gameID && Objects.equals(color, joinGame.color);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(color, gameID);
    }

    public int getGameID()
    {
        return gameID;
    }

    public void setGameID(int gameID)
    {
        this.gameID = gameID;
    }
}
