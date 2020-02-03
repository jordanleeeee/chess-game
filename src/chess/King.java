package chess;

import config.ChessManager;
import util.Coordinate;
import view.ChessPane;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class King extends Chess{

    private static final Image whiteIcon = new Image("file:resources/whiteKing.png", ChessPane.ICON_SIZE, ChessPane.ICON_SIZE, true, true);
    private static final Image blackIcon = new Image("file:resources/blackKing.png", ChessPane.ICON_SIZE, ChessPane.ICON_SIZE, true, true);

    public King(int row, int col, boolean isBlack) {
        super(row, col, isBlack, blackIcon, whiteIcon);
    }

    @Override
    public ArrayList<Coordinate> getAvailableNextMovePosition() {
        ChessManager chessManager = ChessManager.getInstance();

        ArrayList<Coordinate> possibleMove = new ArrayList<>();
        Coordinate tempCoord;

        tempCoord = currentLocation.getUpward();
        if(isValidMove(tempCoord, isBlack)){
            possibleMove.add(tempCoord);
        }
        tempCoord = currentLocation.getDownward();
        if(isValidMove(tempCoord, isBlack)){
            possibleMove.add(tempCoord);
        }
        tempCoord = currentLocation.getLHS();
        if(isValidMove(tempCoord, isBlack)){
            possibleMove.add(tempCoord);
        }
        tempCoord = currentLocation.getRHS();
        if(isValidMove(tempCoord, isBlack)){
            possibleMove.add(tempCoord);
        }
        tempCoord = currentLocation.getBottomRight();
        if(isValidMove(tempCoord, isBlack)){
            possibleMove.add(tempCoord);
        }
        tempCoord = currentLocation.getBottomLeft();
        if(isValidMove(tempCoord, isBlack)){
            possibleMove.add(tempCoord);
        }
        tempCoord = currentLocation.getTopRight();
        if(isValidMove(tempCoord, isBlack)){
            possibleMove.add(tempCoord);
        }
        tempCoord = currentLocation.getTopLeft();
        if(isValidMove(tempCoord, isBlack)){
            possibleMove.add(tempCoord);
        }

        return possibleMove;
    }

    private boolean isValidMove(Coordinate coord, boolean isBlack){
        ChessManager chessManager = ChessManager.getInstance();
        if(coord.isValidCoordinate()){
            if(!chessManager.haveChess(coord)) {
                return true;
            }
            else {
                return !chessManager.haveChess(coord, isBlack);
            }
        }
        return false;
    }

    public boolean isCheck(){
        return isInDanger(currentLocation);
    }

    public boolean isCheckmate(){
        ChessManager chessManager = ChessManager.getInstance();
        ArrayList<Chess> sameColorChess = chessManager.getChess(isBlack);
        for(Chess oneChess: sameColorChess){
            ArrayList<Coordinate> allDestination = oneChess.getAvailableNextMovePosition();
            for(Coordinate oneDestination: allDestination){
                if(chessManager.moveAllowed(oneChess, oneDestination)){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isInDanger(Coordinate coord){
        ChessManager chessManager = ChessManager.getInstance();
        ArrayList<Chess> opponentChess = chessManager.getChess(!isBlack);
        for(Chess oneChess: opponentChess){
            ArrayList<Coordinate> opponentPossibleMove = oneChess.getAvailableNextMovePosition();
            for(Coordinate onePossibleDestination: opponentPossibleMove){
                if(onePossibleDestination.equals(coord)){
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
