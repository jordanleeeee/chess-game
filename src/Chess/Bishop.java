package Chess;

import util.Coordinate;
import config.ChessPane;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Bishop extends Chess{

    private final Image whiteIcon = new Image("file:resources/whiteBishop.png", ChessPane.ICON_SIZE, ChessPane.ICON_SIZE, true, true);
    private final Image blackIcon = new Image("file:resources/blackBishop.png", ChessPane.ICON_SIZE, ChessPane.ICON_SIZE, true, true);

    public Bishop(int row, int col, boolean isBlack) {
        super(row, col, isBlack);
    }

    @Override
    public ArrayList<Coordinate> getAvailableNextMovePosition() {
        ArrayList<Coordinate> possibleMove = new ArrayList<>();
        return possibleMove;
    }

    @Override
    public  Image getIcon() {
        return (isBlack)? blackIcon: whiteIcon;
    }

}
