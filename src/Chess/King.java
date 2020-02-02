package Chess;

import config.ChessManager;
import util.Coordinate;
import config.ChessPane;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class King extends Chess{

    private ChessManager chessManager = null;
    private final Image whiteIcon = new Image("file:resources/whiteKing.png", ChessPane.ICON_SIZE, ChessPane.ICON_SIZE, true, true);
    private final Image blackIcon = new Image("file:resources/blackKing.png", ChessPane.ICON_SIZE, ChessPane.ICON_SIZE, true, true);

    public King(int row, int col, boolean isBlack) {
        super(row, col, isBlack);
    }

    @Override
    public ArrayList<Coordinate> getAvailableNextMovePosition() {
        if(chessManager == null){
            chessManager = ChessManager.getInstance();
        }
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

    @Override
    public Image getIcon() {
        return (isBlack)? blackIcon: whiteIcon;
    }
}
