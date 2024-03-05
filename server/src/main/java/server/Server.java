package Server;


import dataAccess.DataAccessException;
import model.User;
import spark.*;
import com.google.gson.Gson;
import Server.Handlers;

public class Server {

    private Handlers handler;


    public Server()
    {

        handler = new Handlers();
    }
    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        Spark.delete("/db", (req, res) ->handler.clearAll(req,res));//clear all
        Spark.post("/user", (req, res) ->handler.register(req,res));//register
        Spark.post("/session",(req, res) ->handler.login(req,res));//login
        Spark.delete("/session",(req, res) ->handler.logout(req,res));//logout
        Spark.get("/game", (req, res) ->handler.listGames(req,res));//list games
        Spark.post("/game", (req, res) ->handler.createGame(req,res));//create game
        Spark.put("/game", (req, res) ->handler.joinGame(req,res));//join game


        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
    }
}