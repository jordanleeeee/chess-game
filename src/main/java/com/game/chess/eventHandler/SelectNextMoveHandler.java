package com.game.chess.eventHandler;

import com.game.chess.chess.Chess;
import com.game.chess.chess.King;
import com.game.chess.chess.Pawn;
import com.game.chess.config.ChessManager;
import com.game.chess.util.Coordinate;
import com.game.chess.view.ChessPane;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.util.List;


public class SelectNextMoveHandler implements EventHandler<MouseEvent> {
    private final Chess chess;
    private final ChessPane chessPane;
    private final ChessManager chessManager;

    public SelectNextMoveHandler(Chess chess) {
        this.chess = chess;
        this.chessPane = ChessPane.getInstance();
        this.chessManager = ChessManager.getInstance();
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        changeBackToDefaultChessBoard();
        System.out.println("chess clicked " + chess.toString());
        List<Coordinate> availableNextMove = chess.getAvailableNextMovePosition();
        for (Coordinate destination : availableNextMove) {
            Label target = chessPane.getOneCell(destination);
            target.setStyle(ChessPane.defaultAvailableMoveGuideStyle);
            if (chess instanceof Pawn) {
                if (((Pawn) chess).willReachedBoundary(destination)) {
                    target.setOnMouseClicked(new ConfirmMoveEventHandler(chess, destination, SpecialEvent.promotion));
                    continue;
                }
            }
            target.setOnMouseClicked(new ConfirmMoveEventHandler(chess, destination, SpecialEvent.NA));
        }

        if (chess instanceof King) {
            Coordinate castingTarget = ((King) chess).dealWithCastingCase();
            if (castingTarget != null) {
                Label target = chessPane.getOneCell(castingTarget);
                target.setStyle(ChessPane.defaultAvailableMoveGuideStyle);
                target.setOnMouseClicked(new ConfirmMoveEventHandler(chess, castingTarget, SpecialEvent.casting));
            }
        }

        if (chess instanceof Pawn) {
            Coordinate passantTarget = ((Pawn) chess).dealWithEnPassant();
            if (passantTarget != null) {
                Label target = chessPane.getOneCell(passantTarget);
                target.setStyle(ChessPane.defaultAvailableMoveGuideStyle);
                target.setOnMouseClicked(new ConfirmMoveEventHandler(chess, passantTarget, SpecialEvent.enPassant));
            }
        }
    }

    private void changeBackToDefaultChessBoard() {
        for (int i = 0; i < ChessPane.height; i++) {
            for (int j = 0; j < ChessPane.width; j++) {
                Label target = chessPane.getOneCell(i, j);
                target.setStyle(ChessPane.defaultGridStyle);
                //clear eventHandler of all grid with no same color chess
                if (!chessManager.haveChess(new Coordinate(i, j), chess.isBlack())) {
                    target.setOnMouseClicked(null);
                }
            }
        }
    }

}
