package server;


import dataAccess.DataAccessException;
import model.User;
import spark.*;
import com.google.gson.Gson;

public class Server
{

    private Services services;


    public Server()
    {

        services = new Services();
    }


    public Server run(int port)
    {
        Spark.port(port);

        Spark.staticFiles.location("web");

        Spark.delete("/db", this::ClearAll);//clear all
        Spark.post("/user", this::Register);//register
        Spark.post("/session", this::Login);//login
        Spark.delete("/session", this::Logout);//logout
        Spark.get("/game", this::ListGames);//list games
        Spark.post("/game", this::CreateGame);//create game
        Spark.put("/game", this::JoinGame);//join game


        Spark.awaitInitialization();
        return this;
    }

    public int port()
    {
        return Spark.port();
    }

    public void stop()
    {
        Spark.stop();
        Spark.awaitStop();
    }

    public Object ClearAll(Request req, Response res)
    {
        services.clearAll();
        res.status(200);
        return "{}";
    }

    public Object Register(Request req, Response res)
    {
        User newUser = new Gson().fromJson(req.body(), User.class);
        try
        {
            String auth = services.register(newUser);
            res.status(200);
            String gson = new Gson().toJson(new LoginResponce(newUser.getUsername(), auth));
            return gson;
        } catch (IllegalArgumentException iae)
        {
            res.status(403);
            return "{ \"message\": \"Error: already taken\" }";
        } catch (DataAccessException ex){
            res.status(400);
            return "{ \"message\": \"Error: bad request\" }";
        }
        catch (Exception e)
        {
            res.status(400);
            return "{ \"message\": \"\" }";
        }
    }

    public Object Login(Request req, Response res)
    {
        LoginRequest login = new Gson().fromJson(req.body(), LoginRequest.class);

        try
        {
            String auth = services.login(login);
            res.status(200);
            return new Gson().toJson(new LoginResponce(login.getUsername(), auth));
        } catch (IllegalArgumentException | DataAccessException ex)
        {
            res.status(401);
            return "{ \"message\": \"Error: unauthorized\" }";
        } catch (Exception iae)
        {
            res.status(500);
            return "{ \"message\": \"Error: description\" }";
        }

    }

    public Object Logout(Request req, Response res)
    {
        String token = req.headers("authorization");
        try
        {
            services.logout(token);
            res.status(200);
            return "";
        } catch (DataAccessException ex)
        {
            res.status(401);
            return "{ \"message\": \"Error: unauthorized\" }";
        } catch (Exception iae)
        {
            res.status(500);
            return "{ \"message\": \"Error: description\" }";
        }

    }

    public Object ListGames(Request req, Response res)
    {
        String token = req.headers("authorization");
        try
        {
            return new Gson().toJson(services.listGames(token));
        } catch (DataAccessException ex)
        {
            res.status(401);
            return "{ \"message\": \"Error: unauthorized\" }";
        } catch (Exception iae)
        {
            res.status(500);
            return "{ \"message\": \"Error: description\" }";
        }
    }

    public Object CreateGame(Request req, Response res)
    {
        String token = req.headers("authorization");
        CreateGameRequest login = new Gson().fromJson(req.body(), CreateGameRequest.class);
        try
        {
            int ID = services.createGame(login.gameName(), token);
            return "{ \"gameID\":" + ID + " }";
        } catch (DataAccessException ex)
        {
            res.status(401);
            return "{ \"message\": \"Error: unauthorized\" }";
        } catch (Exception iae)
        {
            res.status(500);
            return "{ \"message\": \"Error: description\" }";
        }
    }

    public Object JoinGame(Request req, Response res)
    {
        String token = req.headers("authorization");
        JoinGameRequest join = new Gson().fromJson(req.body(), JoinGameRequest.class);
        try
        {
            services.joinGame(join, token);
            res.status(200);
            return "{}";
        } catch (DataAccessException ex)
        {
            if (ex.getMessage() == "bad color")
            {
                res.status(403);
                return "{ \"message\": \"Error: already taken\" }";
            }
            else if (ex.getMessage() == "bad request" || ex.getMessage() == "Can not add username to existing game")
            {
                res.status(400);
                return "{ \"message\": \"Error: bad request\" }";
            } else if (ex.getMessage() == "Element not found")
            {
                res.status(401);
                return "{ \"message\": \"Error: unauthorized\" }";
            } else
            {
                res.status(500);
                return "{ \"message\": \"Error: description\" }";
            }
        } catch (Exception iae)
        {
            res.status(500);
            return "{ \"message\": \"Error: description\" }";
        }
    }

}
