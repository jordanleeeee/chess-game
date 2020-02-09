package chess;

import config.ChessManager;
import util.Coordinate;
import util.Movement;
import config.StepRecorder;
import view.ChessPane;
import javafx.scene.image.Image;

import java.util.ArrayList;
//兵
public class Pawn extends Chess{

    private static final Image whiteIcon = new Image("file:resources/image/whitePawn.png", ChessPane.ICON_SIZE, ChessPane.ICON_SIZE, true, true);
    private static final Image blackIcon = new Image("file:resources/image/blackPawn.png", ChessPane.ICON_SIZE, ChessPane.ICON_SIZE, true, true);

    public Pawn(int row, int col, boolean isBlack) {
        super(row, col, isBlack, blackIcon, whiteIcon);
    }

    @Override
    public ArrayList<Coordinate> getAvailableNextMovePosition() {
        ChessManager chessManager = ChessManager.getInstance();
        ArrayList<Coordinate> availableMove = new ArrayList<>();
        Coordinate tempCoord;
        if(isBlack) {
            tempCoord = currentLocation.getDownward();
            if (isValidMove(tempCoord)) {
                availableMove.add(tempCoord);
            }
            if(movingTimes == 0 ) {
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
            if(movingTimes==0) {
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

    private boolean isValidKill(Coordinate coord, boolean isBlack){
        ChessManager chessManager = ChessManager.getInstance();
        if(coord.isValidCoordinate()){
            return chessManager.haveChess(coord, !isBlack);
        }
        return false;
    }

    private boolean isValidMove(Coordinate coord){
        ChessManager chessManager = ChessManager.getInstance();
        if(coord.isValidCoordinate()){
            return !chessManager.haveChess(coord);
        }
        return false;
    }

    private boolean isReachedBoundary(){
        if(isBlack){
            return currentLocation.getRow() == ChessPane.height - 1;
        }
        else{
            return currentLocation.getRow() == 0;
        }
    }

    public boolean willReachedBoundary(Coordinate destination){
        Coordinate temp = currentLocation;
        setCurrentLocation(destination);
        boolean isReachedBoundary = isReachedBoundary();
        setCurrentLocation(temp);
        return isReachedBoundary;
    }
    /**
     * @return destination of the chess if en passant is possible, null if otherwise
     */
    public Coordinate dealWithEnPassant(){
        Movement previousMovement = StepRecorder.getInstance().getPreviousMovement();

        if(previousMovement != null) {
            if (previousMovement.isMovingPawnTwoStepVertically()) {
                if (previousMovement.getChess().currentLocation.getLHS().equals(currentLocation)) {
                    return (isBlack)? currentLocation.getBottomRight(): currentLocation.getTopRight();
                }
                if (previousMovement.getChess().currentLocation.getRHS().equals(currentLocation)) {
                    return (isBlack)? currentLocation.getBottomLeft(): currentLocation.getTopLeft();
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Pawn";
    }
}
