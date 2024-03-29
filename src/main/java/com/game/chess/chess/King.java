package com.game.chess.chess;

import com.game.chess.config.ChessManager;
import com.game.chess.eventHandler.SpecialEvent;
import com.game.chess.util.Coordinate;
import com.game.chess.util.Resources;
import javafx.scene.image.Image;

import java.util.List;

public class King extends Chess {
    public static final Image whiteIcon = Resources.imageFromPath("image/whiteKing.png");
    public static final Image blackIcon = Resources.imageFromPath("image/blackKing.png");

    public King(int row, int col, boolean isBlack) {
        super(row, col, isBlack, blackIcon, whiteIcon);
    }

    @Override
    public List<Coordinate> getAvailableNextMovePosition() {
        ChessManager chessManager = ChessManager.getInstance();
        List<Coordinate> possibleMove = currentLocation.getSoundingPosition();

        for (int i = 0; i < possibleMove.size(); i++) {
            if (chessManager.haveChess(possibleMove.get(i), isBlack)) {
                possibleMove.remove(i--);
            }
        }
        return possibleMove;
    }

    /**
     * @return destination of the chess if casting is possible, null if otherwise
     */
    public Coordinate dealWithCastingCase() {
        if (canCasting()) {
            return (isBlack) ? currentLocation.getLHS().getLHS() : currentLocation.getRHS().getRHS();
        }
        return null;
    }

    private boolean canCasting() {
        ChessManager chessManager = ChessManager.getInstance();

        if (!isCheck()) {
            if (isBlack) {
                if (!chessManager.haveChess(currentLocation.getLHS())) {
                    if (!chessManager.haveChess(currentLocation.getLHS().getLHS())) {
                        if (this.movingTimes == 0) {
                            Chess targetRook = chessManager.getOneChess(currentLocation.getLHS().getLHS().getLHS());
                            if (targetRook != null) {
                                if (targetRook instanceof Rook) {
                                    if (targetRook.isBlack) {
                                        return targetRook.movingTimes == 0;
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                if (!chessManager.haveChess(currentLocation.getRHS())) {
                    if (!chessManager.haveChess(currentLocation.getRHS().getRHS())) {
                        if (this.movingTimes == 0) {
                            Chess targetRook = chessManager.getOneChess(currentLocation.getRHS().getRHS().getRHS());
                            if (targetRook != null) {
                                if (targetRook instanceof Rook) {
                                    if (!targetRook.isBlack) {
                                        return targetRook.movingTimes == 0;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * check if is in Checkmate condition i.e. no possible move to protect the king
     *
     * @return true if yes, false if otherwise
     */
    public boolean isCheckmate() {
        ChessManager chessManager = ChessManager.getInstance();
        List<Chess> sameColorChess = chessManager.getChess(isBlack);
        for (Chess oneChess : sameColorChess) {
            List<Coordinate> allDestination = oneChess.getAvailableNextMovePosition();
            for (Coordinate oneDestination : allDestination) {
                if (chessManager.isMoveAllowed(oneChess, oneDestination, SpecialEvent.NA)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * check if the chess(King) is threatened by opponent's chess
     *
     * @return true if yes
     */
    public boolean isCheck() {
        List<Chess> opponentChess = ChessManager.getInstance().getChess(!isBlack);
        for (Chess oneChess : opponentChess) {
            List<Coordinate> opponentPossibleMove = oneChess.getAvailableNextMovePosition();
            for (Coordinate onePossibleDestination : opponentPossibleMove) {
                if (onePossibleDestination.equals(currentLocation)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "King";
    }
}
