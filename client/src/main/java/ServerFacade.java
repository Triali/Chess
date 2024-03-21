import com.google.gson.Gson;
import model.User;
import records.*;


import java.io.IOException;

public class ServerFacade
{
    private String urlString = "https://localhost:8080";
    private Communicator coms = new Communicator();
    public LoginResponce register(User newUser)throws IOException
    {
        String token = null;
        String user = new Gson().toJson(newUser);
        String json = coms.doPost(urlString + "/user",user,token);
        return new Gson().fromJson(json,LoginResponce.class);

    }
    public LoginResponce login(LoginRequest newLogin) throws IOException
    {
        String token = null;
        String user = new Gson().toJson(newLogin);
        String json = coms.doPost(urlString + "/session",user,token);
        return new Gson().fromJson(json,LoginResponce.class);
    }
    public void logout(String token)throws IOException{
        String json = coms.doDelete(urlString + "/session",token);
    }
    public AllGamesReturn listGames(String token) throws IOException
    {
        String json = coms.doGet(urlString + "/game",token);
        return new Gson().fromJson(json,AllGamesReturn.class);
    }
    public void joinGame(String joinReq, String token) throws IOException
    {
        String json = coms.doPut(urlString + "/game",joinReq,token);
    }
    public GameReturn createGame(String newGame, String token)throws IOException
    {
        String json = coms.doPost(urlString + "/game",newGame,token);
        return new Gson().fromJson(json,GameReturn.class);
    }
}
