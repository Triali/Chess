package ClientSupport;

import ClientSupport.Communicator;
import com.google.gson.Gson;
import model.Game;
import model.User;
import records.*;


import java.io.IOException;

public class ServerFacade
{
    private String urlString = "http://localhost:";
    private Communicator coms = new Communicator();

    public ServerFacade(int port){
        urlString += port;
    }
    public ServerFacade(){
        urlString += "8080";
    }

    public LoginResponce register(User newUser) throws IOException
    {
        String token = null;
        String user = new Gson().toJson(newUser);
        String json = coms.doPost(urlString + "/user", user, token);
        LoginResponce login = new Gson().fromJson(json, LoginResponce.class);
        if(login.username()==null && login.authToken()== null){
            throw new IOException(json);
        }
        return login;
    }

    public LoginResponce login(LoginRequest newLogin) throws IOException
    {
        String token = null;
        String user = new Gson().toJson(newLogin);
        String json = coms.doPost(urlString + "/session", user, token);
        LoginResponce login = new Gson().fromJson(json, LoginResponce.class);
        if(login.username()==null && login.authToken()== null){
            throw new IOException(json);
        }
        return login;
    }

    public void logout(String token) throws IOException
    {
        String json = coms.doDelete(urlString + "/session", token);
        if(!json.equals(""))
        {
            throw new IOException(json);
        }
    }

    public AllGamesReturn listGames(String token) throws IOException
    {
        String json = coms.doGet(urlString + "/game", token);
        AllGamesReturn allGames =  new Gson().fromJson(json, AllGamesReturn.class);
        if(allGames.games() == null){
            throw new IOException(json);
        }
        return allGames;
    }

    public void joinGame(JoinGameRequest joinReq, String token) throws IOException
    {

        String join = new Gson().toJson(joinReq);
        String json = coms.doPut(urlString + "/game", join, token);
        if(!json.equals(""))
        {
            throw new IOException(json);
        }
    }

    public GameReturn createGame(CreateGameRequest newGame, String token) throws IOException
    {
        String gameRer = new Gson().toJson(newGame);
        String json =coms.doPost(urlString + "/game", gameRer, token);
        GameReturn game = new Gson().fromJson(json, GameReturn.class);
        if(game.gameName()==null){
            throw new IOException(json);
        }
        return new Gson().fromJson(json, GameReturn.class);
    }
}
