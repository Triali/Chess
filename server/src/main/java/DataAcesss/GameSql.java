package DataAcesss;

import chess.ChessGame;
import chess.ChessPosition;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataAccess.*;
import model.Game;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class GameSql implements GameDAO
{

    public GameSql() throws DataAccessException
    {
        configureDatabase();

    }

    public Game get(int id) throws DataAccessException
    {
        try (var conn = DatabaseManager.getConnection())
        {
            var statement = "SELECT id,name,blackUsername,whiteUsername, game FROM games WHERE id=?";
            try (var ps = conn.prepareStatement(statement))
            {
                ps.setInt(1, id);
                try (var rs = ps.executeQuery())
                {
                    if (rs.next())
                    {
                        return readGame(rs);
                    }
                }
            }
        } catch (Exception e)
        {
            throw new DataAccessException("Error: bad request");
        }

        throw new DataAccessException("Error: bad request");

    }

    private Game readGame(ResultSet rs) throws SQLException
    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ChessPosition.class, new JsonTypeAdapterSerilazer());
        gsonBuilder.registerTypeAdapter(ChessPosition.class, new JsonTypeAdapterDeserializer());

        Gson gson = gsonBuilder.create();

        var id = rs.getInt("id");
        Game game = new Game(rs.getString("name"));
        game.setBlackUsername(rs.getString("blackUsername"));
        game.setWhiteUsername(rs.getString("whiteUsername"));
        game.setID(id);
        String json = rs.getString("game");
//        System.out.println("From Json");
//        System.out.println(json);
        ChessGame chessGame = gson.fromJson(json, ChessGame.class);
//        System.out.println("HERE!");
        game.setGame(chessGame);
        return game;
    }

    public int insert(Game game) throws DataAccessException
    {
        int id = 0;
        if (isInDataBase(game.getID()))
        {
            throw new DataAccessException("Error: bad request(is in game)");
        }
        try (var conn = DatabaseManager.getConnection())
        {
            try (var preparedStatement = conn.prepareStatement("INSERT INTO games (name, blackUsername,whiteUsername,game) VALUES(?, ?,?,?)", RETURN_GENERATED_KEYS))
            {
                preparedStatement.setString(1, game.getGameName());
                preparedStatement.setString(2, game.getBlackUsername());
                preparedStatement.setString(3, game.getWhiteUsername());
                ChessGame chessGame = game.getGame();
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(ChessPosition.class, new JsonTypeAdapterSerilazer());
                gsonBuilder.registerTypeAdapter(ChessPosition.class, new JsonTypeAdapterDeserializer());

                Gson gson = gsonBuilder.create();

                String json = gson.toJson(chessGame);
//                System.out.println("To Json");
//                System.out.println(json);

                preparedStatement.setString(4, json);


                preparedStatement.executeUpdate();

                var resultSet = preparedStatement.getGeneratedKeys();

                if (resultSet.next())
                {
                    id = resultSet.getInt(1);
                }
            }
        } catch (SQLException ex)
        {
            throw new DataAccessException(ex.getMessage());
        }
        return id;
    }

    private boolean isInDataBase(int id)
    {
        try
        {
            if (get(id) == null)
            {
                return false;
            }
            return true;
        } catch (Exception ex)
        {
            return false;
        }
    }


    public void delete(int id) throws DataAccessException
    {
        if (!isInDataBase(id))
        {
            throw new DataAccessException("Error: unauthorized");
        }
        int size;
        try
        {
            size = getAllGames().size();
        } catch (DataAccessException ex)
        {
            return;
        }
        try (var conn = DatabaseManager.getConnection())
        {
            try (var ps = conn.prepareStatement("DELETE FROM games WHERE id=?"))
            {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        } catch (SQLException ex)
        {
            throw new DataAccessException("Error: unauthorized");
        }
        int sizeEnd;
        try
        {
            sizeEnd = getAllGames().size();
        } catch (DataAccessException ex)
        {
            return;
        }
        if (sizeEnd != size - 1)
        {
            throw new DataAccessException("Error: bad request");
        }

    }

    public void postBlack(int id, String username) throws DataAccessException
    {
        if (!isInDataBase(id))
        {
            throw new DataAccessException("Error: unauthorized");
        }
        try (var conn = DatabaseManager.getConnection())
        {
            try (var ps = conn.prepareStatement("UPDATE games SET blackUsername=? where id=?"))
            {
                ps.setString(1, username);
                ps.setInt(2, id);
                ps.executeUpdate();
            }
        } catch (SQLException ex)
        {
            throw new DataAccessException("Error: unauthorized");
        }
    }

    public void postWhite(int id, String username) throws DataAccessException
    {
        if (!isInDataBase(id))
        {
            throw new DataAccessException("Error: unauthorized");
        }
        try (var conn = DatabaseManager.getConnection())
        {
            try (var ps = conn.prepareStatement("UPDATE games SET whiteUsername=? where id=?"))
            {
                ps.setString(1, username);
                ps.setInt(2, id);
                ps.executeUpdate();
            }
        } catch (SQLException ex)
        {
            throw new DataAccessException("Error: unauthorized");
        }
    }

    public void postObserver(int id, String username) throws DataAccessException
    {
        if (!isInDataBase(id))
        {
            throw new DataAccessException("Error: unauthorized");
        }
        try (var conn = DatabaseManager.getConnection())
        {
            try (var ps = conn.prepareStatement("SELECT name FROM games where id=?"))
            {
                ps.setInt(1, id);
            }
        } catch (SQLException ex)
        {
            throw new DataAccessException("Error: unauthorized");
        }
    }

    public void clear() throws DataAccessException
    {
        try (var conn = DatabaseManager.getConnection())
        {
            try (var ps = conn.prepareStatement("TRUNCATE games"))
            {
                ps.execute();
            }
        } catch (SQLException ex)
        {
            throw new DataAccessException(ex.getMessage());
        }
    }

    public ArrayList<Game> getAllGames() throws DataAccessException
    {
        ArrayList games = new ArrayList<>();
        try (var conn = DatabaseManager.getConnection())
        {
            try (var preparedStatement = conn.prepareStatement("SELECT id, name, blackUsername,whiteUsername,game FROM games"))
            {
                try (var rs = preparedStatement.executeQuery())
                {
                    while (rs.next())
                    {
                        Game game = readGame(rs);
                        games.add(game);
                    }
                }
            }
        } catch (Exception ex)
        {

            throw new DataAccessException(ex.getMessage());
        }
        return games;
    }

    // commands to det up table
    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  games (
              `id` int NOT NULL AUTO_INCREMENT,
              `name` varchar(256) NOT NULL,
              `blackUsername` varchar(256) DEFAULT NULL,
              `whiteUsername` varchar(256) DEFAULT NULL,
              `game` text  NOT NULL,
              PRIMARY KEY (`id`),
              INDEX(name)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    private void configureDatabase() throws DataAccessException
    {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection())
        {
            for (var statement : createStatements)
            {
                try (var preparedStatement = conn.prepareStatement(statement))
                {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex)
        {
            throw new DataAccessException(ex.getMessage());
        }
    }
}
