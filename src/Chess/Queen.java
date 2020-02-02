package Chess;

import util.Coordinate;
import config.ChessPane;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Queen extends Chess{

    private final Image whiteIcon = new Image("file:resources/whiteQueen.png", ChessPane.ICON_SIZE, ChessPane.ICON_SIZE, true, true);
    private final Image blackIcon = new Image("file:resources/blackQueen.png", ChessPane.ICON_SIZE, ChessPane.ICON_SIZE, true, true);

    public Queen(int row, int col, boolean isBlack) {
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
