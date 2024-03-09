package DataAcesss;

import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import model.AuthToken;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthTokenSql implements AuthTokenDAO
{
    public AuthTokenSql() throws DataAccessException
    {
        configureDatabase();
    }

    public AuthToken get(String authToken) throws DataAccessException
    {
        try (var conn = DatabaseManager.getConnection())
        {
            var statement = "SELECT username, authToken FROM tokens WHERE authToken=?";
            try (var ps = conn.prepareStatement(statement))
            {
                ps.setString(1, authToken);
                try (var rs = ps.executeQuery())
                {
                    if (rs.next())
                    {
                        return readToken(rs);
                    }
                }
            }
        } catch (Exception e)
        {
            throw new DataAccessException("Error: bad request");
        }
        throw new DataAccessException("Error: bad request");
    }

    private AuthToken readToken(ResultSet rs) throws SQLException
    {
        String token = rs.getString("authToken");
        String username = rs.getString("username");
        return new AuthToken(username, token);
    }

    public void insert(AuthToken token) throws DataAccessException
    {
        try (var conn = DatabaseManager.getConnection())
        {
            try (var preparedStatement = conn.prepareStatement("INSERT INTO tokens (username,authToken) VALUES(?,?)"))
            {
                preparedStatement.setString(1, token.getUsername());
                preparedStatement.setString(2, token.getAuthToken());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex)
        {
            throw new DataAccessException(ex.getMessage());
        }
    }

    private boolean isInDataBase(String token)
    {
        try
        {
            if (get(token) == null)
            {
                return false;
            }
            return true;
        } catch (Exception ex)
        {
            return false;
        }
    }

    public void delete(String authToken) throws DataAccessException
    {
        if (!isInDataBase(authToken))
        {
            throw new DataAccessException("Error: bad request");
        }
        try (var conn = DatabaseManager.getConnection())
        {
            try (var ps = conn.prepareStatement("DELETE FROM tokens WHERE authToken=?"))
            {
                ps.setString(1, authToken);
                ps.executeUpdate();
            }
        } catch (SQLException ex)
        {
            throw new DataAccessException("Error: bad request");
        }
    }


    public void clear() throws DataAccessException
    {
        try (var conn = DatabaseManager.getConnection())
        {
            try (var ps = conn.prepareStatement("TRUNCATE tokens"))
            {
                ps.execute();
            }
        } catch (SQLException | DataAccessException ex)
        {
            throw new DataAccessException("Error: bad request");
        }
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  tokens (
              `username` varchar(256) NOT NULL,
              `authToken` varchar(256) NOT NULL,
              PRIMARY KEY (`authToken`),
              INDEX(username)
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
