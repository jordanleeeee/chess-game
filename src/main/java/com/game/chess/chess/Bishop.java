package com.game.chess.chess;


import com.game.chess.config.ChessManager;
import com.game.chess.util.Coordinate;
import com.game.chess.util.Resources;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Chess {
    public static final Image whiteIcon = Resources.imageFromPath("image/whiteBishop.png");
    public static final Image blackIcon = Resources.imageFromPath("image/blackBishop.png");

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
