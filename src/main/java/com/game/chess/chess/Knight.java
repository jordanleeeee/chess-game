package com.game.chess.chess;

import com.game.chess.ChessApplication;
import com.game.chess.config.ChessManager;
import com.game.chess.util.Coordinate;
import com.game.chess.view.ChessPane;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Knight extends Chess {

    public static final Image whiteIcon = new Image(Objects.requireNonNull(ChessApplication.class.getResource("image/whiteKnight.png")).toString(), ChessPane.ICON_SIZE, ChessPane.ICON_SIZE, true, true);
    public static final Image blackIcon = new Image(Objects.requireNonNull(ChessApplication.class.getResource("image/blackKnight.png")).toString(), ChessPane.ICON_SIZE, ChessPane.ICON_SIZE, true, true);

    public Knight(int row, int col, boolean isBlack) {
        super(row, col, isBlack, blackIcon, whiteIcon);
    }

    @Override
    public List<Coordinate> getAvailableNextMovePosition() {
        ChessManager chessManager = ChessManager.getInstance();
        List<Coordinate> possibleMove = currentLocation.get_L_Shape_MovingPosition();
        for (int i = 0; i < possibleMove.size(); i++) {
            if (chessManager.haveChess(possibleMove.get(i), isBlack)) {
                possibleMove.remove(i--);
            }
        }
        return possibleMove;
    }

    @Override
    public String toString() {
        return "Knight";
    }
}
