package Chess;

import config.ChessManager;
import util.Coordinate;
import config.ChessPane;
import javafx.scene.image.Image;

import java.util.ArrayList;

//城堡
public class Rook extends Chess{

    private ChessManager chessManager = null;

    private final Image whiteIcon = new Image("file:resources/whiteRook.png", ChessPane.ICON_SIZE, ChessPane.ICON_SIZE, true, true);
    private final Image blackIcon = new Image("file:resources/blackRook.png", ChessPane.ICON_SIZE, ChessPane.ICON_SIZE, true, true);

    public Rook(int row, int col, boolean isBlack) {
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
    public Image getIcon() {
        return (isBlack)? blackIcon: whiteIcon;
    }

}
