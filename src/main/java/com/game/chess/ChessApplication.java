package com.game.chess;

import com.game.chess.config.ChessManager;
import com.game.chess.config.Player;
import com.game.chess.view.GamePlatformPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Scanner;

public class ChessApplication extends Application {
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
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter name of player 1");
        String name;
        name = scanner.nextLine();
        black = new Player(name, true);
        System.out.println("enter name of player 2");
        name = scanner.nextLine();
        white = new Player(name, false);

//        black = new Player("Jordan", true);
//        white = new Player("Peter", false);
    }

    public static void main(String[] args){
        assignPlayer();
        launch(args);
    }
}
