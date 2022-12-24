package com.game.chess.chess;

import com.game.chess.config.ChessManager;
import com.game.chess.util.Coordinate;
import com.game.chess.util.Resources;
import javafx.scene.image.Image;

import java.util.List;

public class Knight extends Chess {
    public static final Image whiteIcon = Resources.imageFromPath("image/whiteKnight.png");
    public static final Image blackIcon = Resources.imageFromPath("image/blackKnight.png");

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
