package view;

import config.ChessManager;
import config.Player;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;
import config.StepRecorder;

public class GamePlatformPane extends Pane {

    private static GamePlatformPane INSTANCE = null;

    private Label gameInfo;
    private Label specialNotice;

    private GamePlatformPane(Player black, Player white){
        Label playerDetails = new Label();
        playerDetails.setText("Black: "+ black.getName() +" vs White: "+ white.getName());
        playerDetails.setPrefHeight(30);

        gameInfo = new Label();
        specialNotice = new Label();
        setSpecialNotice("Game start");

        HBox middleBox = getMiddlePane();
        HBox bottomBox = getBottomPane();

        VBox vBox = new VBox();
        vBox.getChildren().add(playerDetails);
        vBox.getChildren().add(gameInfo);
        vBox.getChildren().add(specialNotice);
        vBox.getChildren().add(middleBox);
        vBox.getChildren().add(bottomBox);
        vBox.setAlignment(Pos.CENTER);

        this.getChildren().add(vBox);
    }

    private HBox getMiddlePane(){
        HBox middleBox = new HBox();

        ListView<String> stepsView = new ListView<>(StepRecorder.getInstance().getMoves());
        stepsView.setPrefWidth(250);
        ChessPane chessPane = ChessPane.getInstance();

        middleBox.getChildren().add(chessPane);
        middleBox.getChildren().add(stepsView);
        return middleBox;
    }

    private HBox getBottomPane(){
        HBox bottomBox = new HBox();
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setStyle("-fx-padding: 10;");

        Button undoButton = new BigButton("Undo");
        undoButton.setOnAction(e -> ChessManager.getInstance().undoStep());

        Button resignButton = new BigButton("Resign");
        resignButton.setOnAction( e-> ChessManager.getInstance().resign() );

        bottomBox.getChildren().add(undoButton);
        bottomBox.getChildren().add(resignButton);
        return bottomBox;
    }

    public void setGameInfo(String info){
        gameInfo.setText(info);
    }

    public void setSpecialNotice(String info){
       specialNotice.setText(info);
    }

    public static GamePlatformPane getInstance(@NotNull Player black, Player white){
        if(INSTANCE == null){
            INSTANCE = new GamePlatformPane(black, white);
            return INSTANCE;
        }
        throw new IllegalStateException();
    }

    public static GamePlatformPane getInstance(){
        if(INSTANCE != null){
            return INSTANCE;
        }
        throw new IllegalStateException();
    }
}
