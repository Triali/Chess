package server;

import DataAcesss.*;
import dataAccess.DataAccessException;
import model.AuthToken;
import model.Game;
import model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import records.*;

import java.util.ArrayList;

public class Services
{
    UserDAO users;
    GameDAO games;
    AuthTokenDAO tokens;
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

public Services() throws DataAccessException{
    users = new UserSql();
    games = new GameSql();
    tokens = new AuthTokenSql();
}
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
        if (!encoder.matches(login.password(),password))
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
                String HashedPasword = encoder.encode(login.getPassword());
                login.setPassword(HashedPasword);
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
        try{
        users.clear();
        tokens.clear();
        games.clear();}
        catch (DataAccessException ex){
            System.out.println("failed to clear");
        }
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
        throw new DataAccessException("Error: already taken");
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
        int id = games.insert(game);
        return new GameReturn(id, game.getWhiteUsername(), game.getBlackUsername(), game.getGameName());
    }
}
