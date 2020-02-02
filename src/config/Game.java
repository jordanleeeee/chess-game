package config;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Scanner;

public class Game extends Application {

    static private Player white;
    static private Player black;
    static private ChessManager chessManager;

    @Override
    public void start(Stage stage) {
        ChessPane gamePane = ChessPane.getInstance();
        Label nameDetails = new Label("Black: "+black.getName()+" vs White: "+ white.getName());
        nameDetails.setAlignment(Pos.CENTER);

        VBox vBox = new VBox();
        vBox.getChildren().add(nameDetails);
        vBox.getChildren().add(gamePane);

        Scene scene = new Scene(vBox);
        stage.setTitle("Chess Game");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        chessManager = ChessManager.getInstance(gamePane);
        chessManager.startGame(black, white);
    }
    public static void main(String[] args){
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("enter name of player 1");
//        String name;
//        name = scanner.nextLine();
//        black = new Player(name);
//        System.out.println("enter name of player 2");
//        name = scanner.nextLine();
//        white = new Player(name);

        black = new Player("Jordan");
        white = new Player("Carolyn");
        launch(args);
    }
}
