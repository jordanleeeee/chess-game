package chess;

import config.ChessManager;
import util.Coordinate;
import view.ChessPane;
import javafx.scene.image.Image;

import java.util.ArrayList;

//城堡
public class Rook extends Chess{

    private static final Image whiteIcon = new Image("file:resources/whiteRook.png", ChessPane.ICON_SIZE, ChessPane.ICON_SIZE, true, true);
    private static final Image blackIcon = new Image("file:resources/blackRook.png", ChessPane.ICON_SIZE, ChessPane.ICON_SIZE, true, true);

    public Rook(int row, int col, boolean isBlack) {
        super(row, col, isBlack, blackIcon, whiteIcon);
    }

    @Override
    public ArrayList<Coordinate> getAvailableNextMovePosition() {
        ChessManager chessManager = ChessManager.getInstance();
        ArrayList<Coordinate> possibleMove = new ArrayList<>();
        Coordinate tempCoord;
        tempCoord = currentLocation.getUpward();
        while (tempCoord.isValidCoordinate()) {
            if(chessManager.haveChess(tempCoord, isBlack)){
                break;
            }
            if(chessManager.haveChess(tempCoord, !isBlack)){
                possibleMove.add(tempCoord);
                break;
            }
            possibleMove.add(tempCoord);
            tempCoord = tempCoord.getUpward();
        }

        tempCoord = currentLocation.getDownward();
        while (tempCoord.isValidCoordinate()) {
            if(chessManager.haveChess(tempCoord, isBlack)){
                break;
            }
            if(chessManager.haveChess(tempCoord, !isBlack)){
                possibleMove.add(tempCoord);
                break;
            }
            possibleMove.add(tempCoord);
            tempCoord = tempCoord.getDownward();
        }
        tempCoord = currentLocation.getLHS();
        while (tempCoord.isValidCoordinate()) {
            if(chessManager.haveChess(tempCoord, isBlack)){
                break;
            }
            if(chessManager.haveChess(tempCoord, !isBlack)){
                possibleMove.add(tempCoord);
                break;
            }
            possibleMove.add(tempCoord);
            tempCoord = tempCoord.getLHS();
        }
        tempCoord = currentLocation.getRHS();
        while (tempCoord.isValidCoordinate()) {
            if(chessManager.haveChess(tempCoord, isBlack)){
                break;
            }
            if(chessManager.haveChess(tempCoord, !isBlack)){
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
        return "Rook";
    }

}
