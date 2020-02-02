package Chess;

import config.ChessManager;
import util.Coordinate;
import config.ChessPane;
import javafx.scene.image.Image;

import java.util.ArrayList;

//城堡
public class Rook extends Chess{

    private final Image whiteIcon = new Image("file:resources/whiteRook.png", ChessPane.ICON_SIZE, ChessPane.ICON_SIZE, true, true);
    private final Image blackIcon = new Image("file:resources/blackRook.png", ChessPane.ICON_SIZE, ChessPane.ICON_SIZE, true, true);

    public Rook(int row, int col, boolean isBlack) {
        super(row, col, isBlack);
    }

    @Override
    public ArrayList<Coordinate> getAvailableNextMovePosition() {
        ArrayList<Coordinate> possibleMove = currentLocation.getCrossMovingPosition();
        for(int i=0; i<possibleMove.size(); i++){
            if(isBlack){
                if(ChessManager.getInstance().haveChess(possibleMove.get(i), true)){
                    possibleMove.remove(i--);
                }
            }
            else{
                if(ChessManager.getInstance().haveChess(possibleMove.get(i), false)){
                    possibleMove.remove(i--);
                }
            }
        }
        return possibleMove;
    }

    @Override
    public Image getIcon() {
        return (isBlack)? blackIcon: whiteIcon;
    }

}
