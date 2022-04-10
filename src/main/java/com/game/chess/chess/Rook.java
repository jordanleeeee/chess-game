package com.game.chess.chess;

import com.game.chess.ChessApplication;
import com.game.chess.config.ChessManager;
import com.game.chess.util.Coordinate;
import com.game.chess.view.ChessPane;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//城堡
public class Rook extends Chess {
    public static final Image whiteIcon = new Image(Objects.requireNonNull(ChessApplication.class.getResource("image/whiteRook.png")).toString(), ChessPane.ICON_SIZE, ChessPane.ICON_SIZE, true, true);
    public static final Image blackIcon = new Image(Objects.requireNonNull(ChessApplication.class.getResource("image/blackRook.png")).toString(), ChessPane.ICON_SIZE, ChessPane.ICON_SIZE, true, true);

    public Rook(int row, int col, boolean isBlack) {
        super(row, col, isBlack, blackIcon, whiteIcon);
    }

    @Override
    public List<Coordinate> getAvailableNextMovePosition() {
        ChessManager chessManager = ChessManager.getInstance();
        List<Coordinate> possibleMove = new ArrayList<>();
        Coordinate tempCoord;
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

    public void dealWithCasting() {
        clearChessIcon();
        if (isBlack) {
            setCurrentLocation(new Coordinate(0, 2));
        } else {
            setCurrentLocation(new Coordinate(7, 5));
        }
        visualizeChess();
    }

    public void reverseCasting() {
        clearChessIcon();
        if (isBlack) {
            setCurrentLocation(new Coordinate(0, 0));
        } else {
            setCurrentLocation(new Coordinate(7, 7));
        }
        visualizeChess();
    }

    @Override
    public String toString() {
        return "Rook";
    }

}
