package com.game.chess.view;

import com.game.chess.config.ChessManager;
import com.game.chess.config.Player;
import com.game.chess.config.StepRecorder;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

public class GamePlatformPane extends Pane {

    private static GamePlatformPane INSTANCE = null;

    private final Label gameInfo;
    private final Label specialNotice;

    private GamePlatformPane(Player black, Player white) {
        Label playerDetails = new Label();
        playerDetails.setText("Black: " + black.getName() + " vs White: " + white.getName());
        playerDetails.setPrefHeight(30);

        gameInfo = new Label();
        specialNotice = new Label();

        HBox middleBox = getMiddlePane();
        HBox bottomBox = getBottomPane();

        VBox vBox = new VBox();
        vBox.getChildren().add(playerDetails);
        vBox.getChildren().add(gameInfo);
        vBox.getChildren().add(middleBox);
        vBox.getChildren().add(bottomBox);
        vBox.setAlignment(Pos.CENTER);

        this.getChildren().add(vBox);
    }

    private HBox getMiddlePane() {
        HBox middleBox = new HBox();

        ListView<String> stepsView = new ListView<>(StepRecorder.getInstance().getMoves());
        stepsView.setPrefWidth(250);

        StackPane stackPane = new StackPane();
        specialNotice.setStyle("-fx-text-fill: red; -fx-font-size: 20pt;");
        ChessPane chessPane = ChessPane.getInstance();
        stackPane.getChildren().add(chessPane);
        stackPane.getChildren().add(specialNotice);

        middleBox.getChildren().add(stackPane);
        middleBox.getChildren().add(stepsView);
        return middleBox;
    }

    private HBox getBottomPane() {
        HBox bottomBox = new HBox();
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setStyle("-fx-padding: 10;");

        Button undoButton = new BigButton("Undo");
        undoButton.setOnAction(e -> ChessManager.getInstance().undoStep());

        Button resignButton = new BigButton("Resign");
        resignButton.setOnAction(e -> ChessManager.getInstance().resign());

        Button newGameButton = new BigButton("New Game");
        newGameButton.setOnAction(e -> ChessManager.getInstance().wantNewGame());

        bottomBox.getChildren().add(undoButton);
        bottomBox.getChildren().add(resignButton);
        bottomBox.getChildren().add(newGameButton);
        return bottomBox;
    }

    public void setGameInfo(String info) {
        gameInfo.setText(info);
    }

    public void setSpecialNotice(String info) {
        specialNotice.setText(info);
    }

    public static GamePlatformPane getInstance(@NotNull Player black, Player white) {
        if (INSTANCE == null) {
            INSTANCE = new GamePlatformPane(black, white);
            return INSTANCE;
        }
        throw new IllegalStateException();
    }

    public static GamePlatformPane getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        throw new IllegalStateException();
    }
}
