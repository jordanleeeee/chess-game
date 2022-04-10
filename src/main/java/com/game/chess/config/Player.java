package com.game.chess.config;

import com.game.chess.chess.Bishop;
import com.game.chess.chess.Chess;
import com.game.chess.chess.King;
import com.game.chess.chess.Knight;
import com.game.chess.chess.Pawn;
import com.game.chess.chess.Queen;
import com.game.chess.chess.Rook;
import com.game.chess.eventHandler.SelectNextMoveHandler;
import com.game.chess.view.ChessPane;
import com.game.chess.view.GamePlatformPane;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private final List<Chess> ownedChess = new ArrayList<>();
    private King king;
    private final boolean isBlack;

    public Player(String name, boolean isBlack) {
        this.name = name;
        this.isBlack = isBlack;
        addChess();
        visualizeChess();
    }

    private void addChess() {
        if (isBlack) {
            ownedChess.add(new Rook(0, 0, true));
            ownedChess.add(new Knight(0, 1, true));
            ownedChess.add(new Bishop(0, 2, true));
            ownedChess.add(new Queen(0, 4, true));
            king = new King(0, 3, true);
            ownedChess.add(king);
            ownedChess.add(new Bishop(0, 5, true));
            ownedChess.add(new Knight(0, 6, true));
            ownedChess.add(new Rook(0, 7, true));
            for (int i = 0; i < ChessPane.width; i++) {
                ownedChess.add(new Pawn(1, i, true));
            }
        } else {
            for (int i = 0; i < ChessPane.width; i++) {
                ownedChess.add(new Pawn(6, i, false));
            }
            ownedChess.add(new Rook(7, 7, false));
            ownedChess.add(new Knight(7, 6, false));
            ownedChess.add(new Bishop(7, 5, false));
            ownedChess.add(new Queen(7, 3, false));
            king = new King(7, 4, false);
            ownedChess.add(king);
            ownedChess.add(new Bishop(7, 2, false));
            ownedChess.add(new Knight(7, 1, false));
            ownedChess.add(new Rook(7, 0, false));
        }
    }

    private void visualizeChess() {
        for (Chess oneChess : ownedChess) {
            oneChess.visualizeChess();
        }
    }

    private void clearAllChessIcon() {
        for (Chess oneChess : ownedChess) {
            oneChess.clearChessIcon();
        }
    }

    void processEachRound() {
        if (isLoss()) {
            ChessManager.getInstance().generateLoseNotification(true);
            return;
        }

        if (isInDanger()) {
            GamePlatformPane.getInstance().setSpecialNotice("Check...........");
        }
        addEventHandler();
    }

    private void addEventHandler() {
        for (Chess oneChess : ownedChess) {
            Label target = ChessPane.getInstance().getOneCell(oneChess.getCoordinate());
            target.setOnMouseClicked(new SelectNextMoveHandler(oneChess));
        }
    }

    void wantResign() {
        Alert optForResign = new Alert(Alert.AlertType.WARNING, "Are you sure you want to resign?", ButtonType.OK, ButtonType.CANCEL);
        optForResign.showAndWait().ifPresent(type -> {
            if (type == ButtonType.OK) {
                ChessManager.getInstance().generateLoseNotification(false);
            } else {
                ChessPane.getInstance().unblur();
            }
        });
    }

    void processNewGame() {
        clearAllChessIcon();
        ownedChess.clear();
        addChess();
        visualizeChess();
    }

    boolean isInDanger() {
        return king.isCheck();
    }

    private boolean isLoss() {
        return king.isCheckmate();
    }

    public String getName() {
        return name;
    }

    List<Chess> getOwnedChess() {
        return ownedChess;
    }
}
