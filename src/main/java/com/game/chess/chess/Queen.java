package com.game.chess.chess;

import com.game.chess.config.ChessManager;
import com.game.chess.util.Coordinate;
import com.game.chess.util.Resources;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Chess {
    public static final Image whiteIcon = Resources.imageFromPath("image/whiteQueen.png");
    public static final Image blackIcon = Resources.imageFromPath("image/blackQueen.png");

    public Queen(int row, int col, boolean isBlack) {
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
        tempCoord = currentLocation.getUpward();
        while (tempCoord.isValidCoordinate()) {
            if (chessManager.haveChess(tempCoord, isBlack)) {
                break;
            }
            if (chessManager.haveChess(tempCoord, !isBlack)) {
                possibleMove.add(tempCoord);
                break;
            }
            possibleMove.add(tempCoord);
            tempCoord = tempCoord.getUpward();
        }
        tempCoord = currentLocation.getDownward();
        while (tempCoord.isValidCoordinate()) {
            if (chessManager.haveChess(tempCoord, isBlack)) {
                break;
            }
            if (chessManager.haveChess(tempCoord, !isBlack)) {
                possibleMove.add(tempCoord);
                break;
            }
            possibleMove.add(tempCoord);
            tempCoord = tempCoord.getDownward();
        }
        tempCoord = currentLocation.getLHS();
        while (tempCoord.isValidCoordinate()) {
            if (chessManager.haveChess(tempCoord, isBlack)) {
                break;
            }
            if (chessManager.haveChess(tempCoord, !isBlack)) {
                possibleMove.add(tempCoord);
                break;
            }
            possibleMove.add(tempCoord);
            tempCoord = tempCoord.getLHS();
        }
        tempCoord = currentLocation.getRHS();
        while (tempCoord.isValidCoordinate()) {
            if (chessManager.haveChess(tempCoord, isBlack)) {
                break;
            }
            if (chessManager.haveChess(tempCoord, !isBlack)) {
                possibleMove.add(tempCoord);
                break;
            }
            possibleMove.add(tempCoord);
            tempCoord = tempCoord.getRHS();
        }
        return possibleMove;
    }

    @Override
    public String toString() {
        return "Queen";
    }

}
