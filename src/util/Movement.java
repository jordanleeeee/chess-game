package util;

import chess.Chess;
import chess.Pawn;
import chess.Rook;
import config.ChessManager;
import config.Player;
import eventHandler.SpecialEvent;

import java.util.ArrayList;

public class Movement {

    private Player player;
    private Chess chess;
    private Coordinate from;
    private Coordinate to;
    private boolean isKilling;
    private SpecialEvent specialEvent;

    private ChessManager chessManager = ChessManager.getInstance();

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

        if(specialEvent == SpecialEvent.promotion){
            chessManager.generatePromotionSelectionPane(chess);
        }
    }

    public void reverseMovement(){
        chess.experienceReverseMovement();
        chess.clearChessIcon();
        chess.setCurrentLocation(from);
        chess.visualizeChess();

        if(isKilling){
            chessManager.revivalOneChess();
        }

        if(specialEvent == SpecialEvent.casting) {
            Coordinate rookLocation = (chess.isBlack()) ? new Coordinate(0, 2) : new Coordinate(7, 5);
            Chess rook = chessManager.getOneChess(rookLocation);
            ((Rook) rook).reverseCasting();
        }

        if(specialEvent == SpecialEvent.promotion){
            ArrayList<Chess> groupOfChess = chessManager.getChess(chess.isBlack());
            groupOfChess.add(chess);
            for(int i=0; i<groupOfChess.size(); i++){
                if(groupOfChess.get(i).getCoordinate().equals(to)){
                    groupOfChess.remove(i);
                    break;
                }
            }
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

    public Chess getChess() { return chess; }

    @Override
    public String toString(){
        return player.getName() + " move " + chess.toString() + " form " + from.toString()+
                " to " + to.toString();
    }
}
