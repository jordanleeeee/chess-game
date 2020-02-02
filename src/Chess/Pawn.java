package Chess;

import config.ChessManager;
import util.Coordinate;
import config.ChessPane;
import javafx.scene.image.Image;

import java.util.ArrayList;
//兵
public class Pawn extends Chess{

    private ChessManager chessManager = null;
    private boolean isFirstMove = true;

    private final Image whiteIcon = new Image("file:resources/whitePawn.png", ChessPane.ICON_SIZE, ChessPane.ICON_SIZE, true, true);
    private final Image blackIcon = new Image("file:resources/blackPawn.png", ChessPane.ICON_SIZE, ChessPane.ICON_SIZE, true, true);

    public Pawn(int row, int col, boolean isBlack) {
        super(row, col, isBlack);
    }

    @Override
    public ArrayList<Coordinate> getAvailableNextMovePosition() {
        if(chessManager == null){
            chessManager = ChessManager.getInstance();
        }
        ArrayList<Coordinate> availableMove = new ArrayList<>();
        Coordinate tempCoord;
        if(isBlack) {
            tempCoord = currentLocation.getDownward();
            if (isValidMove(tempCoord)) {
                availableMove.add(tempCoord);
            }
            if(isFirstMove) {
                if(!chessManager.haveChess(currentLocation.getDownward())) {
                    tempCoord = currentLocation.getDownward().getDownward();
                    if (isValidMove(tempCoord)) {
                        availableMove.add(tempCoord);
                    }
                }
            }

            tempCoord = currentLocation.getBottomLeft();
            if(isValidKill(tempCoord, true)){
                availableMove.add(tempCoord);
            }
            tempCoord = currentLocation.getBottomRight();
            if(isValidKill(tempCoord, true)){
                availableMove.add(tempCoord);
            }
        }
        else{
            tempCoord = currentLocation.getUpward();
            if (isValidMove(tempCoord)) {
                availableMove.add(tempCoord);
            }
            if(isFirstMove) {
                if(!chessManager.haveChess(currentLocation.getUpward())) {
                    tempCoord = currentLocation.getUpward().getUpward();
                    if (isValidMove(tempCoord)) {
                        availableMove.add(tempCoord);
                    }
                }
            }
            tempCoord = currentLocation.getTopLeft();
            if(isValidKill(tempCoord, false)){
                availableMove.add(tempCoord);
            }
            tempCoord = currentLocation.getTopRight();
            if(isValidKill(tempCoord, false)){
                availableMove.add(tempCoord);
            }
        }

        return availableMove;
    }

    @Override
    public Image getIcon() {
        return (isBlack)? blackIcon: whiteIcon;
    }

    private boolean isValidKill(Coordinate coord, boolean isBlack){
        if(coord.isValidCoordinate()){
            return chessManager.haveChess(coord, !isBlack);
        }
        return false;
    }

    private boolean isValidMove(Coordinate coord){
        if(coord.isValidCoordinate()){
            return !chessManager.haveChess(coord);
        }
        return false;
    }

    public void moved(){
        isFirstMove = false;
    }

}
