package Server;

import DataAcesss.*;
import dataAccess.DataAccessException;
import model.AuthToken;
import model.Game;
import model.User;
import records.*;

import java.util.ArrayList;

public class Services
{
    UserDAO users = new UserList();
    GameDAO games = new GameList();
    AuthTokenDAO tokens = new AuthTokenList();

    //helpers
    public AuthToken createAuthToken(String username) throws DataAccessException
    {
        AuthToken token = new AuthToken(username);
        tokens.insert(token);
        return token;
    }
    public boolean doesUserExists(String username)
    {
        try
        {
            users.get(username);
            return true;
        } catch (Exception ex)
        {
            return false;
        }
    }

    //Services
    public LoginResponce login(LoginRequest login) throws Exception
    {
        User user= users.get(login.username());
        String password = user.getPassword();
        if (!password.equals(login.password()))
        {
            throw new IllegalArgumentException("Error: unauthorized");// TODO fix Error message
        }
        AuthToken token = createAuthToken(login.username());
        return new LoginResponce(token.getUsername(), token.getAuthToken());
    }
    public void logout(String authToken) throws DataAccessException
    {
        tokens.delete(authToken);
    }
    public LoginResponce register(User login) throws Exception
    {
        if (!doesUserExists(login.getUsername()))
        {
            if (login.getUsername() == null || login.getPassword() == null || login.getEmail() == null)
            {
                throw new DataAccessException("Error: bad request");//TODO fix error message
            } else
            {
                users.insert(login);
                AuthToken token = createAuthToken(login.getUsername());
                return new LoginResponce(token.getUsername(), token.getAuthToken());
            }
        } else
        {
            throw new DataAccessException("Error: already taken");
        }
    }
    public void clearAll()
    {
        users.clear();
        tokens.clear();
        games.clear();
    }
    public void joinGame(JoinGameRequest joinGame, String authToken) throws DataAccessException
    {

        String name = tokens.get(authToken).getUsername();
        int id = joinGame.gameID();
        String color = joinGame.playerColor();
        if (id <= 0)
        {
            throw new DataAccessException("Error: bad request");//TODO fix error message
        }
        games.get(id);
        if (color == null)
        {
            games.postObserver(id, name);
            return;
        }
        if (color.equals("BLACK"))
        {
            if (games.get(id).getBlackUsername() == null)
            {
                games.postBlack(id, name);
                return;
            }
        } else if (color.equals("WHITE"))
        {
            if (games.get(id).getWhiteUsername() == null)
            {
                games.postWhite(id, name);
                return;
            }
        }
        throw new DataAccessException("Error: already taken");// TODO fix error message
    }
    public AllGamesReturn listGames(String authToken) throws DataAccessException
    {
        tokens.get(authToken);
        ArrayList<GameReturn> gameReturns = new ArrayList<>();
        games.getAllGames().forEach((game)->{
            gameReturns.add(new GameReturn(game.getID(), game.getWhiteUsername(), game.getBlackUsername(), game.getGameName()));
        });
        return new AllGamesReturn(gameReturns);

    }
    public GameReturn createGame(String name, String authToken) throws DataAccessException
    {
        tokens.get(authToken);
        Game game = new Game(name);
        games.insert(game);
        return new GameReturn(game.getID(), game.getWhiteUsername(), game.getBlackUsername(), game.getGameName());
    }
}
