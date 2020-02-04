package chess;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import util.Coordinate;
import view.ChessPane;

import java.util.ArrayList;

public abstract class Chess {

    boolean isBlack;
    int movingTimes = 0;
    Coordinate currentLocation;
    private final Image blackIcon;
    private final Image whiteIcon;

    public Chess(int row, int col, boolean isBlack, Image blackIcon, Image whiteIcon){
        currentLocation = new Coordinate(row, col);
        this.isBlack = isBlack;
        this.blackIcon = blackIcon;
        this.whiteIcon = whiteIcon;
    }

    public Coordinate getCoordinate(){
        return currentLocation;
    }
    public void setCurrentLocation(Coordinate currentLocation) {
        this.currentLocation = currentLocation;
    }
    public boolean isBlack() {
        return isBlack;
    }
    public Image getIcon() {
        return (isBlack)? blackIcon: whiteIcon;
    }
    public void setIsMoved(){
        movingTimes++;
        visualizeChess();
    }
    public void experienceReverseMovement(){
        movingTimes--;
    }
    public void visualizeChess(){
        Label target = ChessPane.getInstance().getOneCell(currentLocation);
        target.setGraphic(new ImageView((isBlack)? blackIcon: whiteIcon));
        target.setAlignment(Pos.CENTER);
    }
    public void clearChessIcon(){
        ChessPane.getInstance().getOneCell(currentLocation).setGraphic(null);
    }

    /**
     * get list of possible move of the chess (according to moving rules)
     * @return list of coordinate
     */
    public abstract ArrayList<Coordinate> getAvailableNextMovePosition();

    @Override
    public abstract String toString();

}
