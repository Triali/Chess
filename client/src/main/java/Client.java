import ClientSupport.ServerFacade;
import chess.*;
import model.User;
import records.CreateGameRequest;
import records.GameReturn;
import records.JoinGameRequest;
import records.LoginRequest;
import ui.PrintBoard;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

import static ui.EscapeSequences.SET_TEXT_COLOR_BLACK;
import static ui.EscapeSequences.SET_TEXT_COLOR_WHITE;

public class Client
{
    private String token = null;
    private ServerFacade server;
    PrintStream out;

    ArrayList<GameReturn> games;

    public void start()
    {
        server = new ServerFacade();
        this.out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        int option = 0;
        while (token != null || option != 3)
        {
            if (token == null)
            {
                listLoginOption();
                option = getLoginOption(server);
            } else
            {
                listUserOption();
                option = getUserOption(server);
            }
        }

    }

    private void listLoginOption()
    {
        out.print(SET_TEXT_COLOR_WHITE);
        System.out.println("Please select an option by entering the number");
        System.out.println("1.Register");
        System.out.println("2.Login");
        System.out.println("3.Quit");
        System.out.println("4.Help");
        System.out.print(">>");
    }

    public int getLoginOption(ServerFacade sf)
    {
        Scanner in = new Scanner(System.in);
        String username;
        String password;
        int a;

        try
        {
            a = in.nextInt();
        } catch (Exception ex)
        {
            a = 4;
        }
        String restOfLine = in.nextLine();

        switch (a)
        {
            case 1: //register
                System.out.print("Username: ");
                username = in.nextLine();
                System.out.print("Password: ");
                password = in.nextLine();
                System.out.print("Email: ");
                String email = in.nextLine();
                try
                {
                    token = sf.register(new User(username, password, email)).authToken();
                    System.out.println("You successfully Registered");
                } catch (IOException ex)
                {
                    System.out.println("You failed to Register");
                }
                break;
            case 2:// Login
                System.out.print("Username: ");
                username = in.nextLine();
                System.out.print("Password: ");
                password = in.nextLine();
                try
                {
                    token = sf.login(new LoginRequest(username, password)).authToken();
                    System.out.println("You successfully Logged in");
                } catch (IOException ex)
                {
                    System.out.println("You failed to Login");
                }
                break;
            case 3: // quit
                System.out.println("You are closing the program");
                break;
            case 4: // Help
                System.out.println("Press 1 to register as a new player \n" +
                        "Press 2 to login\n" +
                        "Press 3 to Quit the program");
                break;

            default:
                break;
        }
        return a;
    }

    private void listUserOption()
    {
        out.print(SET_TEXT_COLOR_WHITE);
        System.out.println("Please select an option by entering the number");
        System.out.println("1.Logout");
        System.out.println("2.List Games");
        System.out.println("3.Create Game");
        System.out.println("4.Join Game");
        System.out.println("5.Join Observer");
        System.out.println("6.Help");
        System.out.print(">>");
    }

    public int getUserOption(ServerFacade sf)
    {
        Scanner in = new Scanner(System.in);
        int a;
        try
        {
            a = in.nextInt();
        } catch (Exception ex)
        {
            a = 6;
        }
        switch (a)
        {
            case 1://Logout
                logout(sf);
                break;
            case 2:// List Games
                listGames(sf);
                break;
            case 3://create game
                createGame(sf);
                break;
            case 4://join game
                joinGame(sf);
                break;
            case 5://join as observer
                joinGameObserver(sf);
                break;
            case 6:// help
                System.out.println("1. Logout: end your gaming session" +
                        "2. List Games: see all available games" +
                        "3. Create Game: create a new game" +
                        "4. Join Game: join game as a player" +
                        "5. Join Observer: watch a game be played");
                break;
            default:
                break;

        }
        return a;

    }

    private void logout(ServerFacade sf)
    {
        try
        {
            sf.logout(token);
            System.out.println("You have logged out");
            token = null;
        } catch (IOException ex)
        {
            System.out.println("Logout failed");
        }
    }

    private void listGames(ServerFacade sf)
    {
        try
        {
            games = sf.listGames(token).games();
            for (int i = 1; i <= games.size(); i++)
            {

                System.out.println(i + ". " + games.get(i - 1).gameName());
                System.out.println("    White: " + games.get(i - 1).whiteUsername());
                System.out.println("    Black: " + games.get(i - 1).blackUsername());
            }
        } catch (IOException ex)
        {
            System.out.println("Failed to print games");
        }
    }

    private void joinGame(ServerFacade sf)
    {
        Scanner in = new Scanner(System.in);
        try
        {
            games = sf.listGames(token).games();
        } catch (IOException ex)
        {
            System.out.println("Failed to get games");
        }
        System.out.println("Enter Game Number:");
        int gameNum;
        try
        {
            gameNum = in.nextInt();
        }catch(Exception ex){
            System.out.println("Failed to join game");
            return;
        }
        int gameID;
        try
        {
            gameID = games.get(gameNum - 1).gameID();
        } catch (Exception ex)
        {
            System.out.println("failed to join game");
            return;
        }
        System.out.println(games.get(gameNum - 1).gameName());
        String white = games.get(gameNum - 1).whiteUsername();
                System.out.println("    White: " + white);
                String black = games.get(gameNum - 1).blackUsername();
        System.out.println("    Black: " + black);

        String playerColor = null;
        while (playerColor == null)
        {
            System.out.println("\nChoose your Player: \n 1. White\n2. Black\n3.Observer");
            int color = in.nextInt();
            switch (color)
            {
                case 1:
                    if(white!=null){
                        System.out.println("failed to join game");
                        return;
                    }
                    playerColor = "WHITE";

                    break;
                case 2:
                    if(black!=null){
                        System.out.println("failed to join game");
                        return;
                    }
                    playerColor = "BLACK";
                    break;
                case 3:
                    try
                    {
                        sf.joinGame(new JoinGameRequest(null, gameID), token);
                        System.out.println("Joined Game as Observer");
                    } catch (IOException ex)
                    {
                        System.out.println("Failed to Join Game");
                    }
                    return;
                default:
                    System.out.println("invalid choice");
                    playerColor = null;
                    break;
            }
        }
        try
        {
            sf.joinGame(new JoinGameRequest(playerColor, gameID), token);
            System.out.println("Joined Game as " + playerColor);
            printBoard(playerColor);
        } catch (IOException ex)
        {
            System.out.println("Failed to Join Game");
        }
    }


    private void joinGameObserver(ServerFacade sf)
    {
        Scanner in = new Scanner(System.in);
        try
        {
            games = sf.listGames(token).games();
        } catch (IOException ex)
        {
            System.out.println("Failed to get games");
        }
        System.out.println("Enter Game Number:");
        int gameNum = in.nextInt();
        int gameID;
        try
        {
            gameID = games.get(gameNum - 1).gameID();
        } catch (Exception ex)
        {
            System.out.println("failed to join game");
            return;
        }
        try
        {
            sf.joinGame(new JoinGameRequest(null, gameID), token);
            System.out.println("Joined Game as Observer");
        } catch (IOException ex)
        {
            System.out.println("Failed to Join Game");
        }
    }


    private void printBlack(ChessGame game)
    {
        PrintBoard printing = new PrintBoard(game);
        printing.printBoardBlack();
    }

    private void printWhite(ChessGame game)
    {
        PrintBoard printing = new PrintBoard(game);
        printing.printBoardWhite();
    }

    private void printBoard(String playerColor)
    {
        if (playerColor == "WHITE")
        {
            printWhite(new ChessGame());
        } else
        {
            printBlack(new ChessGame());
        }
    }

    private void createGame(ServerFacade sf)
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter Game Name:");
        String gameName = in.nextLine();
        CreateGameRequest game = new CreateGameRequest(gameName);
        try
        {
            sf.createGame(game, token);
            System.out.println("created Game");
        } catch (IOException ex)
        {
            System.out.println("Failed to create Game");
        }
    }
}