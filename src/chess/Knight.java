package chess;

import config.ChessManager;
import util.Coordinate;
import view.ChessPane;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Knight extends Chess{

    public static final Image whiteIcon = new Image("file:resources/whiteKnight.png", ChessPane.ICON_SIZE, ChessPane.ICON_SIZE, true, true);
    public static final Image blackIcon = new Image("file:resources/blackKnight.png", ChessPane.ICON_SIZE, ChessPane.ICON_SIZE, true, true);

    public Knight(int row, int col, boolean isBlack) {
        super(row, col, isBlack, blackIcon, whiteIcon);
    }

    @Override
    public ArrayList<Coordinate> getAvailableNextMovePosition(){
        ChessManager chessManager = ChessManager.getInstance();
        ArrayList<Coordinate> possibleMove = currentLocation.get_L_Shape_MovingPosition();
        for(int i=0; i<possibleMove.size(); i++){
            if(chessManager.haveChess(possibleMove.get(i), isBlack)){
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
