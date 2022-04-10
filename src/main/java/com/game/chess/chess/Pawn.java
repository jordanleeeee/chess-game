package com.game.chess.chess;


import com.game.chess.ChessApplication;
import com.game.chess.config.ChessManager;
import com.game.chess.config.StepRecorder;
import com.game.chess.util.Coordinate;
import com.game.chess.util.Movement;
import com.game.chess.view.ChessPane;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//兵
public class Pawn extends Chess {
    private static final Image whiteIcon = new Image(Objects.requireNonNull(ChessApplication.class.getResource("image/whitePawn.png")).toString(), ChessPane.ICON_SIZE, ChessPane.ICON_SIZE, true, true);
    private static final Image blackIcon = new Image(Objects.requireNonNull(ChessApplication.class.getResource("image/blackPawn.png")).toString(), ChessPane.ICON_SIZE, ChessPane.ICON_SIZE, true, true);

    public Pawn(int row, int col, boolean isBlack) {
        super(row, col, isBlack, blackIcon, whiteIcon);
    }

    @Override
    public List<Coordinate> getAvailableNextMovePosition() {
        ChessManager chessManager = ChessManager.getInstance();
        List<Coordinate> availableMove = new ArrayList<>();
        Coordinate tempCoord;
        if (isBlack) {
            tempCoord = currentLocation.getDownward();
            if (isValidMove(tempCoord)) {
                availableMove.add(tempCoord);
            }
            if (movingTimes == 0) {
                if (!chessManager.haveChess(currentLocation.getDownward())) {
                    tempCoord = currentLocation.getDownward().getDownward();
                    if (isValidMove(tempCoord)) {
                        availableMove.add(tempCoord);
                    }
                }
            }

            tempCoord = currentLocation.getBottomLeft();
            if (isValidKill(tempCoord, true)) {
                availableMove.add(tempCoord);
            }
            tempCoord = currentLocation.getBottomRight();
            if (isValidKill(tempCoord, true)) {
                availableMove.add(tempCoord);
            }
        } else {
            tempCoord = currentLocation.getUpward();
            if (isValidMove(tempCoord)) {
                availableMove.add(tempCoord);
            }
            if (movingTimes == 0) {
                if (!chessManager.haveChess(currentLocation.getUpward())) {
                    tempCoord = currentLocation.getUpward().getUpward();
                    if (isValidMove(tempCoord)) {
                        availableMove.add(tempCoord);
                    }
                }
            }
            tempCoord = currentLocation.getTopLeft();
            if (isValidKill(tempCoord, false)) {
                availableMove.add(tempCoord);
            }
            tempCoord = currentLocation.getTopRight();
            if (isValidKill(tempCoord, false)) {
                availableMove.add(tempCoord);
            }
        }

        return availableMove;
    }

    private boolean isValidKill(Coordinate coord, boolean isBlack) {
        ChessManager chessManager = ChessManager.getInstance();
        if (coord.isValidCoordinate()) {
            return chessManager.haveChess(coord, !isBlack);
        }
        return false;
    }

    private boolean isValidMove(Coordinate coord) {
        ChessManager chessManager = ChessManager.getInstance();
        if (coord.isValidCoordinate()) {
            return !chessManager.haveChess(coord);
        }
        return false;
    }

    private boolean isReachedBoundary() {
        if (isBlack) {
            return currentLocation.getRow() == ChessPane.height - 1;
        } else {
            return currentLocation.getRow() == 0;
        }
    }

    public boolean willReachedBoundary(Coordinate destination) {
        Coordinate temp = currentLocation;
        setCurrentLocation(destination);
        boolean isReachedBoundary = isReachedBoundary();
        setCurrentLocation(temp);
        return isReachedBoundary;
    }

    /**
     * @return destination of the chess if en passant is possible, null if otherwise
     */
    public Coordinate dealWithEnPassant() {
        Movement previousMovement = StepRecorder.getInstance().getPreviousMovement();

        if (previousMovement != null) {
            if (previousMovement.isMovingPawnTwoStepVertically()) {
                if (previousMovement.getChess().currentLocation.getLHS().equals(currentLocation)) {
                    return (isBlack) ? currentLocation.getBottomRight() : currentLocation.getTopRight();
                }
                if (previousMovement.getChess().currentLocation.getRHS().equals(currentLocation)) {
                    return (isBlack) ? currentLocation.getBottomLeft() : currentLocation.getTopLeft();
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Pawn";
    }
}
