package server;

import dataAccess.DataAccessException;

import java.util.ArrayList;

public class Requests
{

    UserDAO users = new UserList();
    GameDAO games = new GameList();
    AuthTokenDAO tokens = new AuthTokenList();


public User getUser(String username) throws DataAccessException{
        return users.get(username);
}

public void CreateUser(String username, String password, String email) throws DataAccessException{
    users.insert(username,password,email);
}
public String CreateAuthToken(String username) throws DataAccessException
{
    AuthToken token = new AuthToken(username);
    tokens.insert(token);
    return token.getAuthToken();

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
public void CreateGame(String gameName, String color, String username)throws DataAccessException{
if(color == "BLACK"){
    games.insert(new Game(gameName,username,""));
}else if (color == "WHITE"){
    games.insert(new Game(gameName,"","username"));
}else{
    new DataAccessException("Bad Color");
}
}

public void UpdateUsername(int gameID, String color, String username)throws DataAccessException{
    games.post(gameID,color,username);
}




}
