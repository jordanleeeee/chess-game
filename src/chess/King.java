package chess;

import config.ChessManager;
import eventHandler.SpecialEvent;
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
        ArrayList<Coordinate> possibleMove = currentLocation.getSoundingPosition();

        for (int i = 0; i < possibleMove.size(); i++) {
            if(chessManager.haveChess(possibleMove.get(i), isBlack)){
                possibleMove.remove(i--);
            }
        }
        return possibleMove;
    }

    /**
     * @return destination of the chess if casting is possible, null if otherwise
     */
    public Coordinate dealWithCastingCase(){
        if(canCasting()){
            return (isBlack)? currentLocation.getLHS().getLHS(): currentLocation.getRHS().getRHS();
        }
        return null;
    }

    private boolean canCasting(){
        ChessManager chessManager = ChessManager.getInstance();

        if(!isCheck()) {
            if (isBlack) {
                if (!chessManager.haveChess(currentLocation.getLHS())) {
                    if (!chessManager.haveChess(currentLocation.getLHS().getLHS())) {
                        if (this.movingTimes == 0) {
                            Chess targetRook = chessManager.getOneChess(currentLocation.getLHS().getLHS().getLHS());
                            if (targetRook != null) {
                                if (targetRook instanceof Rook) {
                                    if (targetRook.isBlack) {
                                        return targetRook.movingTimes == 0;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else{
                if(!chessManager.haveChess(currentLocation.getRHS())){
                    if(!chessManager.haveChess(currentLocation.getRHS().getRHS())){
                        if(this.movingTimes==0){
                            Chess targetRook = chessManager.getOneChess(currentLocation.getRHS().getRHS().getRHS());
                            if(targetRook != null){
                                if(targetRook instanceof  Rook){
                                    if(!targetRook.isBlack){
                                        return targetRook.movingTimes == 0;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * check if is in Checkmate condition i.e. no possible move to protect the king
     * @return true if yes, false if otherwise
     */
    public boolean isCheckmate(){
        ChessManager chessManager = ChessManager.getInstance();
        ArrayList<Chess> sameColorChess = chessManager.getChess(isBlack);
        for(Chess oneChess: sameColorChess){
            ArrayList<Coordinate> allDestination = oneChess.getAvailableNextMovePosition();
            for(Coordinate oneDestination: allDestination){
                if(chessManager.isMoveAllowed(oneChess, oneDestination, SpecialEvent.NA)){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * check if the chess(King) is threatened by opponent's chess
     * @return true if yes
     */
    public boolean isCheck(){
        ArrayList<Chess> opponentChess = ChessManager.getInstance().getChess(!isBlack);
        for(Chess oneChess: opponentChess){
            ArrayList<Coordinate> opponentPossibleMove = oneChess.getAvailableNextMovePosition();
            for(Coordinate onePossibleDestination: opponentPossibleMove){
                if(onePossibleDestination.equals(currentLocation)){
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
