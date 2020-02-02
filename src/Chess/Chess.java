package Chess;
import javafx.scene.image.Image;
import util.Coordinate;
import java.util.ArrayList;

public abstract class Chess {

    protected boolean isBlack;
    protected Coordinate currentLocation;

    public Chess(int row, int col, boolean isBlack){
        currentLocation = new Coordinate(row, col);
        this.isBlack = isBlack;
    }

    public abstract ArrayList<Coordinate> getAvailableNextMovePosition();
    public Coordinate getCoordinate(){
        return currentLocation;
    }

    public boolean isBlack() {
        return isBlack;
    }

    public abstract Image getIcon();

}
