package Chess;

import util.Coordinate;
import config.ChessPane;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class King extends Chess{

    private final Image whiteIcon = new Image("file:resources/whiteKing.png", ChessPane.ICON_SIZE, ChessPane.ICON_SIZE, true, true);
    private final Image blackIcon = new Image("file:resources/blackKing.png", ChessPane.ICON_SIZE, ChessPane.ICON_SIZE, true, true);

    public King(int row, int col, boolean isBlack) {
        super(row, col, isBlack);
    }

    @Override
    public ArrayList<Coordinate> getAvailableNextMovePosition() {
        ArrayList<Coordinate> possibleMove = new ArrayList<>();
        return possibleMove;
    }

    @Override
    public Image getIcon() {
        return (isBlack)? blackIcon: whiteIcon;
    }

}
