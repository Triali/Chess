package ClientSupport;

import ClientSupport.Communicator;
import com.google.gson.Gson;
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
    }

    public AllGamesReturn listGames(String token) throws IOException
    {
        String json = coms.doGet(urlString + "/game", token);
        return new Gson().fromJson(json, AllGamesReturn.class);
    }

    public void joinGame(JoinGameRequest joinReq, String token) throws IOException
    {

        String json = new Gson().toJson(joinReq);
        coms.doPut(urlString + "/game", json, token);
    }

    public GameReturn createGame(CreateGameRequest newGame, String token) throws IOException
    {
        String json = new Gson().toJson(newGame);
        coms.doPost(urlString + "/game", json, token);
        return new Gson().fromJson(json, GameReturn.class);
    }
}
