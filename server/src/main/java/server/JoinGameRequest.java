package server;

import java.util.Objects;

public class JoinGameRequest
{
    private String playerColor;
    private int gameID;

    public JoinGameRequest(String color, int gameID)
    {
        this.playerColor = color;
        this.gameID = gameID;
    }

    public String getPlayerColor()
    {
        return playerColor;
    }

    public void setPlayerColor(String playerColor)
    {
        this.playerColor = playerColor;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JoinGameRequest joinGame = (JoinGameRequest) o;
        return gameID == joinGame.gameID && Objects.equals(playerColor, joinGame.playerColor);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(playerColor, gameID);
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
