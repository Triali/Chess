package server;

public class GameReturn
{
    int gameID;
    String whiteUsername;
    String blackUsername;
    String gameName;

    public GameReturn(int gameID, String whiteUsername, String blackUsername, String gameName)
    {
        this.gameID = gameID;
        this.whiteUsername = whiteUsername;
        this.blackUsername = blackUsername;
        this.gameName = gameName;
    }
}
