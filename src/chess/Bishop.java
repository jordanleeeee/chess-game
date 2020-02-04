package chess;

import config.ChessManager;
import util.Coordinate;
import view.ChessPane;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Bishop extends Chess{

    public static final Image whiteIcon = new Image("file:resources/whiteBishop.png", ChessPane.ICON_SIZE, ChessPane.ICON_SIZE, true, true);
    public static final Image blackIcon = new Image("file:resources/blackBishop.png", ChessPane.ICON_SIZE, ChessPane.ICON_SIZE, true, true);

    public Bishop(int row, int col, boolean isBlack) {
        super(row, col, isBlack, blackIcon, whiteIcon);
    }

    @Override
    public ArrayList<Coordinate> getAvailableNextMovePosition() {
        ChessManager chessManager = ChessManager.getInstance();

        ArrayList<Coordinate> possibleMove = new ArrayList<>();
        Coordinate tempCoord;
        tempCoord = currentLocation.getTopRight();
        while (tempCoord.isValidCoordinate()) {
            if(chessManager.haveChess(tempCoord, isBlack)){
                break;
            }
            if(chessManager.haveChess(tempCoord, !isBlack)){
                possibleMove.add(tempCoord);
                break;
            }
            possibleMove.add(tempCoord);
            tempCoord = tempCoord.getTopRight();
        }

        tempCoord = currentLocation.getTopLeft();
        while (tempCoord.isValidCoordinate()) {
            if(chessManager.haveChess(tempCoord, isBlack)){
                break;
            }
            if(chessManager.haveChess(tempCoord, !isBlack)){
                possibleMove.add(tempCoord);
                break;
            }
            possibleMove.add(tempCoord);
            tempCoord = tempCoord.getTopLeft();
        }
        tempCoord = currentLocation.getBottomRight();
        while (tempCoord.isValidCoordinate()) {
            if(chessManager.haveChess(tempCoord, isBlack)){
                break;
            }
            if(chessManager.haveChess(tempCoord, !isBlack)){
                possibleMove.add(tempCoord);
                break;
            }
            possibleMove.add(tempCoord);
            tempCoord = tempCoord.getBottomRight();
        }
        tempCoord = currentLocation.getBottomLeft();
        while (tempCoord.isValidCoordinate()) {
            if(chessManager.haveChess(tempCoord, isBlack)){
                break;
            }
            if(chessManager.haveChess(tempCoord, !isBlack)){
                possibleMove.add(tempCoord);
                break;
            }
            possibleMove.add(tempCoord);
            tempCoord = tempCoord.getBottomLeft();
        }
        return possibleMove;
    }

    @Override
    public String toString() {
        return "Bishop";
    }
}
