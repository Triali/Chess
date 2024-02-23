package server;

import chess.ChessGame;

import java.util.Objects;

public class Game
{
    ChessGame game;

    public Game(ChessGame game)
    {
        this.game = game;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game1 = (Game) o;
        return Objects.equals(game, game1.game);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(game);
    }
}
