package Chess;

import config.ChessManager;
import util.Coordinate;
import config.ChessPane;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Knight extends Chess{

    private final Image whiteIcon = new Image("file:resources/whiteKnight.png", ChessPane.ICON_SIZE, ChessPane.ICON_SIZE, true, true);
    private final Image blackIcon = new Image("file:resources/blackKnight.png", ChessPane.ICON_SIZE, ChessPane.ICON_SIZE, true, true);

    public Knight(int row, int col, boolean isBlack) {
        super(row, col, isBlack);
    }

    @Override
    public ArrayList<Coordinate> getAvailableNextMovePosition(){
        ArrayList<Coordinate> possibleMove = currentLocation.get_L_Shape_MovingPosition();
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
