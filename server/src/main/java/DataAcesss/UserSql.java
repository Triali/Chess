package DataAcesss;

import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import model.AuthToken;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSql implements UserDAO
{
    public UserSql() throws DataAccessException
    {
        configureDatabase();
    }


    public User get(String userName) throws DataAccessException
    {
        try (var conn = DatabaseManager.getConnection())
        {
            var statement = "SELECT username, password, email FROM users WHERE username=?";
            try (var ps = conn.prepareStatement(statement))
            {
                ps.setString(1, userName);
                try (var rs = ps.executeQuery())
                {
                    if (rs.next())
                    {
                        return readUser(rs);
                    }
                }
            }
        } catch (Exception e)
        {
            throw new DataAccessException(e.getMessage());
        }
        throw new DataAccessException("Error: bad request2");
    }
    private User readUser(ResultSet rs) throws SQLException
    {
        String username = rs.getString("username");
        String password = rs.getString("password");
        String email = rs.getString("email");
        return new User(username, password,email);
    }


    public void insert(User user) throws DataAccessException
    {
        try (var conn = DatabaseManager.getConnection())
        {
            try (var preparedStatement = conn.prepareStatement("INSERT INTO users (username,password,email) VALUES(?,?,?)"))
            {
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex)
        {
            throw new DataAccessException(ex.getMessage());
        }

    }
    private boolean isInDataBase(String user)
    {
        try
        {
            if (get(user) == null)
            {
                return false;
            }
            return true;
        } catch (Exception ex)
        {
            return false;
        }
    }

    public void delete(String userName) throws DataAccessException
    {
        if (!isInDataBase(userName))
        {
            throw new DataAccessException("Error: bad request");
        }
        try (var conn = DatabaseManager.getConnection())
        {
            try (var ps = conn.prepareStatement("DELETE FROM users WHERE username=?"))
            {
                ps.setString(1, userName);
                ps.executeUpdate();
            }
        } catch (SQLException ex)
        {
            throw new DataAccessException("Error: bad request");
        }
    }


    public void clear()throws DataAccessException
    {
        try (var conn = DatabaseManager.getConnection())
        {
            try (var ps = conn.prepareStatement("TRUNCATE users"))
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
            CREATE TABLE IF NOT EXISTS users (
              `username` varchar(256) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(256) NOT NULL,
              PRIMARY KEY (`username`)
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

