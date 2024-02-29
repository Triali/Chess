package server;

import DataAcesss.*;
import dataAccess.DataAccessException;
import model.Game;
import model.User;

import java.util.ArrayList;

public class Services
{

    UserDAO users = new UserList();
    GameDAO games = new GameList();
    AuthTokenDAO tokens = new AuthTokenList();


public User getUser(String username) throws DataAccessException{
        return users.get(username);
}

    public UserDAO getUsers()
    {
        return users;
    }

    public void createUser(String username, String password, String email) throws DataAccessException{
    users.insert(username,password,email);
}
public String createAuthToken(String username) throws DataAccessException
{

    return tokens.insert(username);


}

public void clearAll(){
    users.deleteAll();
    tokens.deleteAll();
    games.deleteAll();
}

public String getUserName(String authToken) throws DataAccessException
{
    return tokens.get(authToken).getUsername();
}

public void removeAuthToken(String authToken) throws DataAccessException
{
    tokens.delete(authToken);
}

public String getPassword(String username) throws DataAccessException{
    return users.get(username).getPassword();

}

public ArrayList<Game> getAllGames(){
return games.getAll();
}

public AllGamesReturn listGames(String token)throws DataAccessException{
    getUserName(token);
    ArrayList<Game> games = getAllGames();

    ArrayList<GameReturn> gamesReturns = new ArrayList();
    if(games.isEmpty())
    {
        return new AllGamesReturn(gamesReturns);
    }
    for(int i=0; i < games.size();i++)
    {
        gamesReturns.add(new GameReturn(games.get(i).getID(), games.get(i).getWhiteUsername(), games.get(i).getBlackUsername(), games.get(i).getGameName()));

    }
    return new AllGamesReturn(gamesReturns);
}
public int createGame(String gameName, String token)throws DataAccessException{
    getUserName(token);
    return games.insert(gameName);

}

public void updateUsername(int gameID, String color, String username)throws DataAccessException{
    games.post(gameID,color,username);
}

    public AuthTokenDAO getTokens()
    {
        return tokens;
    }

    public String register(User loginRequest) throws Exception
    {
        try{

            getUser(loginRequest.getUsername());
            throw new IllegalArgumentException("Username already exists");
        }catch( DataAccessException ex){
            if(loginRequest.getUsername() == null|| loginRequest.getPassword()==null|| loginRequest.getEmail()==null){
                throw new DataAccessException("bad request");
            }
            createUser(loginRequest.getUsername(), loginRequest.getPassword(), loginRequest.getEmail());
            return createAuthToken(loginRequest.getUsername());
        }
    }

    public void logout(String authToken)throws DataAccessException{
        removeAuthToken(authToken);
    }

    public GameDAO getGames()
    {
        return games;
    }

    public String login(LoginRequest login)throws Exception{
        String password  = getPassword(login.getUsername());
        if(!password.equals(login.getPassword())){
            throw new IllegalArgumentException("Incorrect Password");
        }
        return createAuthToken(login.getUsername());
    }

    public void joinGame(JoinGameRequest joinGame, String authToken)throws DataAccessException{

    String name = tokens.get(authToken).getUsername();
        updateUsername(joinGame.getGameID(), joinGame.getPlayerColor(), name);
    }



}
