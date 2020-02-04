package view;

import chess.*;
import config.ChessManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ButtonSelectionStage extends Stage {

    private final String[] chessNameList = {"Queen", "Knight", "Bishop", "Rook"};

    public ButtonSelectionStage(Chess chess) {
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

        VBox selectionPage = new VBox();
        Label msg = new Label("Please select a piece to promote to:");
        selectionPage.getChildren().add(msg);
        selectionPage.getChildren().add(buttons);

        Scene scene = new Scene(selectionPage);
        this.setResizable(false);
        this.initStyle(StageStyle.UTILITY);
        this.setScene(scene);
        this.setTitle("Chess Replacement Options");
    }

}