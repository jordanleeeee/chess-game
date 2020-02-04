package util;

import chess.Chess;
import chess.Rook;
import config.ChessManager;
import config.Player;
import javafx.scene.image.ImageView;
import view.ChessPane;

public class Movement {

    private Player player;
    private Chess chess;
    private Coordinate from;
    private Coordinate to;
    private boolean isKilling;
    private boolean hasCasting;

    public Movement(Player player, Chess chess, Coordinate from, Coordinate to, boolean isKilling, boolean hasCasting){
        this.player = player;
        this.chess = chess;
        this.from = from;
        this.to = to;
        this.isKilling = isKilling;
        this.hasCasting = hasCasting;
    }

    public void reverseMovement(){
        ChessPane chessPane = ChessPane.getInstance();
        ChessManager chessManager = ChessManager.getInstance();
        chess.experienceReverseMovement();
        chess.setCurrentLocation(from);
        chessPane.getOneCell(to).setGraphic(null);
        chessPane.getOneCell(from).setGraphic(new ImageView(chess.getIcon()));
        if(isKilling){
            ChessManager.getInstance().revivalOneChess();
        }
        if(hasCasting){
            if(chess.isBlack()){
                chessPane.getOneCell(0,0).setGraphic(new ImageView(Rook.blackIcon));
                chessPane.getOneCell(0,2).setGraphic(null);
                chessManager.getOneChess(new Coordinate(0,2)).setCurrentLocation(new Coordinate(0, 0));
            }
            else{
                chessPane.getOneCell(7,7).setGraphic(new ImageView(Rook.whiteIcon));
                chessPane.getOneCell(7,5).setGraphic(null);
                chessManager.getOneChess(new Coordinate(7,5)).setCurrentLocation(new Coordinate(7, 7));
            }
        }
    }

    @Override
    public String toString(){
        return player.getName() + " move " + chess.toString() + " form " + from.toString()+
                " to " + to.toString();
    }
}
