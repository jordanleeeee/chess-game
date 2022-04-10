package com.game.chess.view;


import com.game.chess.chess.Bishop;
import com.game.chess.chess.Chess;
import com.game.chess.chess.Knight;
import com.game.chess.chess.Queen;
import com.game.chess.chess.Rook;
import com.game.chess.config.ChessManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PromotionSelectionStage extends Stage {
    private final String[] chessNameList = {"Queen", "Knight", "Bishop", "Rook"};

    public PromotionSelectionStage(Chess chess) {

        HBox buttons = getButtonPane(chess);

        VBox selectionPage = new VBox();
        Label msg = new Label("Please select a piece to promote to:");
        msg.setStyle("-fx-padding: 10; -fx-font-size: 20pt;");
        selectionPage.getChildren().add(msg);
        selectionPage.getChildren().add(buttons);
        selectionPage.setStyle("-fx-padding: 10;");
        selectionPage.setBackground(new Background(new BackgroundFill(Color.rgb(186, 70, 24), CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(selectionPage);
        setStyle(scene);

    }

    private HBox getButtonPane(Chess chess) {
        Image[] whiteChessImgList = {Queen.whiteIcon, Knight.whiteIcon, Bishop.whiteIcon, Rook.whiteIcon};
        Image[] blackChessImgList = {Queen.blackIcon, Knight.blackIcon, Bishop.blackIcon, Rook.blackIcon};
        final Image[] chessImgList = (chess.isBlack()) ? blackChessImgList : whiteChessImgList;

        HBox buttons = new HBox();
        buttons.setStyle("-fx-spacing: 10;");
        buttons.setAlignment(Pos.CENTER);

        for (int i = 0; i < chessImgList.length; i++) {
            Button chessIcon = new Button();
            final int finalI = i;
            chessIcon.setOnAction(e -> {
                ChessManager.getInstance().chessClicked(chess, chessNameList[finalI]);
                this.close();
            });
            chessIcon.setGraphic(new ImageView(chessImgList[i]));
            buttons.getChildren().add(chessIcon);
        }
        return buttons;
    }

    private void setStyle(Scene scene) {
        this.setResizable(false);
        this.initStyle(StageStyle.UNDECORATED);
        this.setScene(scene);
        this.setAlwaysOnTop(true);
        this.setTitle("Chess Replacement Options");
    }
}
