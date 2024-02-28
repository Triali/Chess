package server;

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

    public void CreateUser(String username, String password, String email) throws DataAccessException{
    users.insert(username,password,email);
}
public String CreateAuthToken(String username) throws DataAccessException
{

    return tokens.insert(username);


}

public void ClearAll(){
    users.deleteAll();
    tokens.deleteAll();
    games.deleteAll();
}

public String getUserName(String authToken) throws DataAccessException
{
    return tokens.get(authToken).getUsername();
}

public void RemoveAuthToken(String authToken) throws DataAccessException
{
    tokens.delete(authToken);
}

public String getPassword(String username) throws DataAccessException{
    return users.get(username).getPassWord();

}

public ArrayList<Game> getAllGames(){
return games.getAll();
}

public ArrayList<Game> ListGames(String token)throws DataAccessException{
    getUserName(token);
    return getAllGames();
}
public int CreateGame(String gameName)throws DataAccessException{
    return games.insert(gameName);

}

public void UpdateUsername(int gameID, String color, String username)throws DataAccessException{
    games.post(gameID,color,username);
}

    public AuthTokenDAO getTokens()
    {
        return tokens;
    }

    public String Register(User loginRequest) throws Exception
    {
        try{
            getUser(loginRequest.getUserName());
            throw new IllegalArgumentException("Username already exists");
        }catch( DataAccessException ex){
            CreateUser(loginRequest.getUserName(), loginRequest.getPassWord(), loginRequest.getEmail());
            return CreateAuthToken(loginRequest.getUserName());
        }
    }

    public void Logout(String authToken)throws DataAccessException{
        RemoveAuthToken(authToken);
    }

    public GameDAO getGames()
    {
        return games;
    }

    public String Login(LoginRequest login)throws Exception{
        String password  = getPassword(login.getUsername());
        if(password != login.getPassword()){
            throw new IllegalArgumentException("Incorrect Password");
        }
        return CreateAuthToken(login.getUsername());
    }

    public void JoinGame(JoinGameRequest joinGame, String authToken)throws DataAccessException{
        String name = getUser(authToken).getUserName();
        UpdateUsername(joinGame.getGameID(), joinGame.getPlayerColor(), name);
    }



}
