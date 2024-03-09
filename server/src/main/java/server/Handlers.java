package server;


import com.google.gson.Gson;
import model.User;
import records.*;
import spark.*;

public class Handlers
{
Services services;

public Handlers(){
    services = new Services();
}

    public Object clearAll(Request req, Response res)
    {
        services.clearAll();
        res.status(200);
        return "{}";
    }

    public Object register(Request req, Response res)
    {
        User newUser = new Gson().fromJson(req.body(), User.class);
        try
        {
            LoginResponce auth = services.register(newUser);
            res.status(200);
            return new Gson().toJson(auth);
        }catch(Exception ex){
            res = setErrorResponce(res,ex.getMessage());
            return "{ \"message\": \""+ ex.getMessage() +"\" }";
        }
    }

    public Object login(Request req, Response res)
    {
        LoginRequest login = new Gson().fromJson(req.body(), LoginRequest.class);
        try{
            LoginResponce loginResponce = services.login(login);
            res.status(200);
            return new Gson().toJson(loginResponce);
        }catch(Exception ex){
            res = setErrorResponce(res,ex.getMessage());
            return "{ \"message\": \""+ ex.getMessage() +"\" }";
        }
    }

    public Object logout(Request req, Response res)
    {
        String token = req.headers("authorization");
        try{
            services.logout(token);
            res.status(200);
            return "";
        }catch(Exception ex){
            res = setErrorResponce(res,ex.getMessage());
            return "{ \"message\": \""+ ex.getMessage() +"\" }";
        }
    }
    public Object listGames(Request req, Response res)
    {
        String token = req.headers("authorization");
        try
        {
            return new Gson().toJson(services.listGames(token));
        }catch(Exception ex){
            res = setErrorResponce(res,ex.getMessage());
            return "{ \"message\": \""+ ex.getMessage() +"\" }";
        }
    }

    public Object createGame(Request req, Response res)
    {
        String token = req.headers("authorization");
        CreateGameRequest login = new Gson().fromJson(req.body(), CreateGameRequest.class);
        try
        {
            GameReturn game = services.createGame(login.gameName(),token);
            return "{ \"gameID\":" + game.gameID() + " }";
        }
        catch(Exception ex){
            res = setErrorResponce(res,ex.getMessage());
            return "{ \"message\": \""+ ex.getMessage() +"\" }";
        }
    }

    public Object joinGame(Request req, Response res)
    {
        String token = req.headers("authorization");
        JoinGameRequest join = new Gson().fromJson(req.body(), JoinGameRequest.class);
        try
        {
            services.joinGame(join, token);
            res.status(200);
            return "{}";
        }catch(Exception ex){
            res = setErrorResponce(res,ex.getMessage());
            return "{ \"message\": \""+ ex.getMessage() +"\" }";
        }
    }

    private Response setErrorResponce(Response res, String message){

    res.status();
    switch (message){
        case "Error: bad request":
            res.status(400);
            break;
        case "Error: unauthorized":
            res.status(401);
            break;
        case "Error: already taken":
            res.status(403);
            break;
        default:
            res.status(500);
            break;

    }


    return res;
    }


    }
