package com.game.chess.chess;


import com.game.chess.ChessApplication;
import com.game.chess.config.ChessManager;
import com.game.chess.util.Coordinate;
import com.game.chess.view.ChessPane;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Bishop extends Chess {
    public static final Image whiteIcon = new Image(Objects.requireNonNull(ChessApplication.class.getResource("image/whiteBishop.png")).toString(), ChessPane.ICON_SIZE, ChessPane.ICON_SIZE, true, true);
    public static final Image blackIcon = new Image(Objects.requireNonNull(ChessApplication.class.getResource("image/blackBishop.png")).toString(), ChessPane.ICON_SIZE, ChessPane.ICON_SIZE, true, true);

    public Bishop(int row, int col, boolean isBlack) {
        super(row, col, isBlack, blackIcon, whiteIcon);
    }

    @Override
    public List<Coordinate> getAvailableNextMovePosition() {
        ChessManager chessManager = ChessManager.getInstance();

        List<Coordinate> possibleMove = new ArrayList<>();
        Coordinate tempCoord;
        tempCoord = currentLocation.getTopRight();
        while (tempCoord.isValidCoordinate()) {
            if (chessManager.haveChess(tempCoord, isBlack)) {
                break;
            }
            if (chessManager.haveChess(tempCoord, !isBlack)) {
                possibleMove.add(tempCoord);
                break;
            }
            possibleMove.add(tempCoord);
            tempCoord = tempCoord.getTopRight();
        }

        tempCoord = currentLocation.getTopLeft();
        while (tempCoord.isValidCoordinate()) {
            if (chessManager.haveChess(tempCoord, isBlack)) {
                break;
            }
            if (chessManager.haveChess(tempCoord, !isBlack)) {
                possibleMove.add(tempCoord);
                break;
            }
            possibleMove.add(tempCoord);
            tempCoord = tempCoord.getTopLeft();
        }
        tempCoord = currentLocation.getBottomRight();
        while (tempCoord.isValidCoordinate()) {
            if (chessManager.haveChess(tempCoord, isBlack)) {
                break;
            }
            if (chessManager.haveChess(tempCoord, !isBlack)) {
                possibleMove.add(tempCoord);
                break;
            }
            possibleMove.add(tempCoord);
            tempCoord = tempCoord.getBottomRight();
        }
        tempCoord = currentLocation.getBottomLeft();
        while (tempCoord.isValidCoordinate()) {
            if (chessManager.haveChess(tempCoord, isBlack)) {
                break;
            }
            if (chessManager.haveChess(tempCoord, !isBlack)) {
                possibleMove.add(tempCoord);
                break;
            }
            possibleMove.add(tempCoord);
            tempCoord = tempCoord.getBottomLeft();
        }
        return possibleMove;
    }

    @Override
    public String toString() {
        return "Bishop";
    }
}
