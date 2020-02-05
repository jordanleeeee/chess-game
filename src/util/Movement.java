package util;

import chess.Chess;
import chess.Pawn;
import chess.Rook;
import config.ChessManager;
import config.Player;
import eventHandler.SpecialEvent;
import javafx.scene.image.ImageView;
import view.ChessPane;

public class Movement {

    private Player player;
    private Chess chess;
    private Coordinate from;
    private Coordinate to;
    private boolean isKilling;
    private SpecialEvent specialEvent;

    private ChessManager chessManager = ChessManager.getInstance();
    private ChessPane chessPane = ChessPane.getInstance();

    public Movement(Player player, Chess chess, Coordinate from, Coordinate to, boolean isKilling, SpecialEvent specialEvent){
        this.player = player;
        this.chess = chess;
        this.from = from;
        this.to = to;
        this.isKilling = isKilling;
        this.specialEvent = specialEvent;

        processMovement();
    }

    private void processMovement(){
        chess.clearChessIcon();
        chess.setCurrentLocation(to);
        chess.visualizeChess();
        chess.setIsMoved();

        if(specialEvent == SpecialEvent.casting){
            Coordinate rookLocation = (chess.isBlack())? new Coordinate(0, 0): new Coordinate(7, 7);
            Chess rook = chessManager.getOneChess(rookLocation);
            ((Rook)rook).dealWithCasting();
        }
    }

    public void reverseMovement(){
        chess.experienceReverseMovement();
        chess.setCurrentLocation(from);
        chessPane.getOneCell(to).setGraphic(null);
        chessPane.getOneCell(from).setGraphic(new ImageView(chess.getIcon()));
        if(isKilling){
            ChessManager.getInstance().revivalOneChess();
        }

        if(specialEvent == SpecialEvent.casting) {
            Coordinate rookLocation = (chess.isBlack()) ? new Coordinate(0, 2) : new Coordinate(7, 5);
            Chess rook = chessManager.getOneChess(rookLocation);
            ((Rook) rook).reverseCasting();
        }
    }

    public boolean isMovingPawnTwoStepVertically(){
        if(chess instanceof Pawn){
            if(from.getCol() == to.getCol()){
                return (Math.abs(from.getRow() - to.getRow()) == 2);
            }
        }
        return false;
    }

    public Chess getChess() {
        return chess;
    }

    @Override
    public String toString(){
        return player.getName() + " move " + chess.toString() + " form " + from.toString()+
                " to " + to.toString();
    }
}
