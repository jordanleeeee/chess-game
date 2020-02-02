package util;

import config.ChessPane;
import java.util.ArrayList;

public class Coordinate {
    private int row;
    private int col;

    public Coordinate(int row, int col){
        this.row = row;
        this.col = col;
    }

    public ArrayList<Coordinate> getCrossMovingPosition(){
        ArrayList<Coordinate> possibleMove = new ArrayList<>();
        int r = row;
        while(r<ChessPane.height-1){
            possibleMove.add(new Coordinate(++r, col));
        }
        int c = col;
        while(c<ChessPane.width-1){
            possibleMove.add(new Coordinate(row, ++c));
        }
        r = row;
        while(r>0){
            possibleMove.add(new Coordinate(--r, col));
        }
        c = col;
        while(c>0){
            possibleMove.add(new Coordinate(row, --c));
        }
        return possibleMove;
    }

    public ArrayList<Coordinate> getDiagonalMovingPosition(){
        ArrayList<Coordinate> possibleMove = new ArrayList<>();
        int r,c;
        r=row; c=col;
        //top right hand side
        while(r<0 && c<ChessPane.width-1){
            possibleMove.add(new Coordinate(--r, ++c));
        }
        r=row; c=col;
        //bottom right hand side
        while(r<ChessPane.height-1 && c<ChessPane.width-1){
            possibleMove.add(new Coordinate(++r, ++c));
        }
        r=row; c=col;
        //top left hand side
        while(r<0 && c<0){
            possibleMove.add(new Coordinate(--r, --c));
        }
        r=row; c=col;
        //bottom left hand side
        while(r<ChessPane.height-1 && c<0){
            possibleMove.add(new Coordinate(++r, --c));
        }
        return possibleMove;
    }

    public ArrayList<Coordinate> get_L_Shape_MovingPosition(){
        ArrayList<Coordinate> possibleMove = new ArrayList<>();
        possibleMove.add(new Coordinate(row+2, col+1));
        possibleMove.add(new Coordinate(row+2, col-1));
        possibleMove.add(new Coordinate(row-2, col-1));
        possibleMove.add(new Coordinate(row-2, col+1));
        possibleMove.add(new Coordinate(row-1, col-2));
        possibleMove.add(new Coordinate(row+1, col-2));
        possibleMove.add(new Coordinate(row-1, col+2));
        possibleMove.add(new Coordinate(row+1, col+2));
        for(int i=0; i<possibleMove.size(); i++){
            if(!possibleMove.get(i).isValidCoordinate()){
                possibleMove.remove(i--);
            }
        }
        return possibleMove;
    }

    public Coordinate getUpward(){
        return new Coordinate(row-1, col);
    }

    public Coordinate getDownward(){
        return new Coordinate(row+1, col);
    }

    public Coordinate getLHS(){
        return new Coordinate(row, col-1);
    }

    public Coordinate getRHS(){
        return new Coordinate(row, col+1);
    }

    public Coordinate getTopLeft(){
        return this.getUpward().getLHS();
    }

    public Coordinate getTopRight(){
        return this.getUpward().getRHS();
    }

    public Coordinate getBottomLeft(){
        return this.getDownward().getLHS();
    }

    public Coordinate getBottomRight(){
        return this.getDownward().getRHS();
    }

    @Override
    public boolean equals(Object coord){
        if(coord instanceof Coordinate){
            Coordinate c = (Coordinate)coord;
            return (c.getCol()==col && c.getRow()==row);
        }
        throw new IllegalArgumentException();
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public boolean isValidCoordinate(){
        return (row>=0 && col>=0 && row<ChessPane.height && col<ChessPane.width);
    }

    @Override
    public String toString(){
        return "< "+row+", "+col+" >";
    }

}
