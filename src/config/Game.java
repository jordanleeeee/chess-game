package config;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.GamePlatformPane;

import java.util.Scanner;

public class Game extends Application {

    static private Player white;
    static private Player black;

    @Override
    public void start(Stage stage) {
        GamePlatformPane gamePlatformPane = GamePlatformPane.getInstance(black, white);

        Scene scene = new Scene(gamePlatformPane);
        stage.setTitle("Chess Game");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        ChessManager chessManager = ChessManager.getInstance();
        chessManager.startGame(black, white);
    }

    private static void assignPlayer(){
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("enter name of player 1");
//        String name;
//        name = scanner.nextLine();
//        black = new Player(name);
//        System.out.println("enter name of player 2");
//        name = scanner.nextLine();
//        white = new Player(name);

        black = new Player("Jordan");
        //white = new Player("Carolyn");

        //white = new Player("Sharon");
        white = new Player("Rainbow");
    }

    public static void main(String[] args){
        assignPlayer();
        launch(args);
    }
}
