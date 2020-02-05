package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import org.jetbrains.annotations.NotNull;
import util.Coordinate;

public class ChessPane extends Pane {

    private static final ChessPane INSTANCE = new ChessPane();

    public static final int height = 8;
    public static final int width = 8;
    public static final int GRID_SIZE = 60;
    public static final double BORDER_SIZE = GRID_SIZE*0.3;
    public static final int ICON_SIZE = GRID_SIZE-10;
    public static final String defaultGridStyle = "-fx-border-color: black;";
    public static final String defaultAvailableMoveGuideStyle = "-fx-border-color: green; -fx-border-width: 4px";

    private Label[][] grids;

    private ChessPane(){
        grids = new Label[height][width];
        for (int i=0; i<height+2; i++){
            for(int j=0; j<width+2; j++){
                addGridToThePane(i, j);
            }
        }
    }

    private void addGridToThePane(int height, int width){
        Label newLabel = new Label();
        newLabel.setAlignment(Pos.CENTER);
        if((height%2==0 && width%2==0) || (height%2==1 && width%2==1)){
            newLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        }
        else {
            newLabel.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        }

        if(height==0 && width==0){
            newLabel.setLayoutX(0);
            newLabel.setLayoutY(0);
            newLabel.setPrefHeight(BORDER_SIZE);
            newLabel.setPrefWidth(BORDER_SIZE);
        }
        else if(height==0 && width==9){
            newLabel.setLayoutX(BORDER_SIZE+GRID_SIZE*8);
            newLabel.setLayoutY(0);
            newLabel.setPrefHeight(BORDER_SIZE);
            newLabel.setPrefWidth(BORDER_SIZE);
        }
        else if(height==0){
            newLabel.setLayoutX(BORDER_SIZE+(width-1)*GRID_SIZE);
            newLabel.setLayoutY(0);
            newLabel.setPrefHeight(BORDER_SIZE);
            newLabel.setPrefWidth(GRID_SIZE);
            newLabel.setText(String.valueOf((char)(width + 96)));
        }
        else if(width == 0 && height == 9){
            newLabel.setLayoutX(0);
            newLabel.setLayoutY(BORDER_SIZE+GRID_SIZE*8);
            newLabel.setPrefHeight(BORDER_SIZE);
            newLabel.setPrefWidth(BORDER_SIZE);
        }
        else if(width == 0){
            newLabel.setLayoutX(0);
            newLabel.setLayoutY(BORDER_SIZE+(height-1)*GRID_SIZE);
            newLabel.setPrefHeight(GRID_SIZE);
            newLabel.setPrefWidth(BORDER_SIZE);
            newLabel.setText(String.valueOf(9-height));
        }
        else if(width == 9 && height== 9){
            newLabel.setLayoutX(BORDER_SIZE+GRID_SIZE*8);
            newLabel.setLayoutY(BORDER_SIZE+GRID_SIZE*8);
            newLabel.setPrefHeight(BORDER_SIZE);
            newLabel.setPrefWidth(BORDER_SIZE);
        }
        else if(width == 9){
            newLabel.setLayoutX(BORDER_SIZE+GRID_SIZE*8);
            newLabel.setLayoutY(BORDER_SIZE+GRID_SIZE*(height-1));
            newLabel.setPrefHeight(GRID_SIZE);
            newLabel.setPrefWidth(BORDER_SIZE);
            newLabel.setText(String.valueOf(9-height));
        }
        else if(height == 9){
            newLabel.setLayoutX(BORDER_SIZE+GRID_SIZE*(width-1));
            newLabel.setLayoutY(BORDER_SIZE+GRID_SIZE*8);
            newLabel.setPrefHeight(BORDER_SIZE);
            newLabel.setPrefWidth(GRID_SIZE);
            newLabel.setText(String.valueOf((char)(width + 96)));
        }
        else {
            newLabel.setLayoutX(((width-1) * GRID_SIZE) + BORDER_SIZE);
            newLabel.setLayoutY(((height-1) * GRID_SIZE) + BORDER_SIZE);
            newLabel.setPrefHeight(GRID_SIZE);
            newLabel.setPrefWidth(GRID_SIZE);
            grids[height-1][width-1] = newLabel;
        }
        newLabel.setStyle(defaultGridStyle);
        this.getChildren().add(newLabel);
    }

    public Label getOneCell(int row, int col){
        return grids[row][col];
    }

    public Label getOneCell(@NotNull Coordinate coord){
        return grids[coord.getRow()][coord.getCol()];
    }

    public void blur(){
        this.setOpacity(0.4);
    }

    public void unblur(){
        this.setOpacity(1);
    }

    public static ChessPane getInstance(){
        return INSTANCE;
    }
}
