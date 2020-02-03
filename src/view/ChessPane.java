package view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import util.Coordinate;

public class ChessPane extends Pane {

    private static final ChessPane INSTANCE = new ChessPane();

    public static final int height = 8;
    public static final int width = 8;
    public static final int GRID_SIZE = 60;
    public static final int ICON_SIZE = GRID_SIZE-10;
    public static final String defaultGridStyle = "-fx-border-color: black;";
    public static final String defaultAvailableMoveGuideStyle = "-fx-border-color: green; -fx-border-width: 3px";

    private Label[][] grids;

    private ChessPane(){
        grids = new Label[height][width];
        for (int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                addGridToThePane(i, j);
            }
        }
    }

    private void addGridToThePane(int height, int width){
        Label newLabel = new Label();
        if((height%2==0 && width%2==0) || (height%2==1 && width%2==1)){
            newLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        }
        else {
            newLabel.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        }
        newLabel.setLayoutX(width * GRID_SIZE);
        newLabel.setLayoutY(height * GRID_SIZE);
        newLabel.setPrefHeight(GRID_SIZE);
        newLabel.setPrefWidth(GRID_SIZE);
        newLabel.setStyle(defaultGridStyle);
        grids[height][width] = newLabel;
        this.getChildren().add(newLabel);
    }

    public Label getOneCell(int row, int col){
        return grids[row][col];
    }

    public Label getOneCell(Coordinate coord){
        return grids[coord.getRow()][coord.getCol()];
    }

    public static ChessPane getInstance(){
        return INSTANCE;
    }
}
