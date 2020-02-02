package config;

import Chess.*;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import util.Coordinate;

import java.util.ArrayList;

public class ChessManager {

    public static ChessManager INSTANCE = null;
    private ChessPane gamePane;
    private ArrayList<Chess> allChess = new ArrayList<>();
    private ArrayList<Chess> blackChess = new ArrayList<>();
    private ArrayList<Chess> whiteChess = new ArrayList<>();
    private int round = 1;


    private ChessManager(ChessPane gamePane){
        this.gamePane = gamePane;
        addChess();
        separateChess();
        visualizeChess();
    }

    private void addChess(){
        allChess.add(new Rook(0,0, true));
        allChess.add(new Knight(0,1, true));
        allChess.add(new Bishop(0,2, true));
        allChess.add(new Queen(0,3, true));
        allChess.add(new King(0,4, true));
        allChess.add(new Bishop(0,5, true));
        allChess.add(new Knight(0,6, true));
        allChess.add(new Rook(0,7, true));
        for (int i=0; i<ChessPane.width; i++){
            allChess.add(new Pawn(1,i, true));
        }

        for (int i=0; i<ChessPane.width; i++){
            allChess.add(new Pawn(6,i, false));
        }
        allChess.add(new Rook(7,7, false));
        allChess.add(new Knight(7,6, false));
        allChess.add(new Bishop(7,5, false));
        allChess.add(new Queen(7,4, false));
        allChess.add(new King(7,3, false));
        allChess.add(new Bishop(7,2, false));
        allChess.add(new Knight(7,1, false));
        allChess.add(new Rook(7,0, false));
    }

    private void separateChess(){
        for(Chess oneChess: allChess){
            if(oneChess.isBlack()){
                blackChess.add(oneChess);
            }
            else{
                whiteChess.add(oneChess);
            }
        }
    }

    private void visualizeChess(){
        for(Chess oneChess: allChess){
            int width = oneChess.getCoordinate().getCol();
            int height = oneChess.getCoordinate().getRow();
            Label target = gamePane.getOneCell(height, width);
            target.setGraphic(new ImageView(oneChess.getIcon()));
            target.setAlignment(Pos.CENTER);
        }
    }

    public void startGame(Player black, Player white){
        black.addChess(blackChess);
        white.addChess(whiteChess);
        startIteration(black, white);
    }

    private void startIteration(Player black, Player white){
        System.out.println("hey game start, black go first");
        black.processEachRound();
//        while(true){
//            if(round++%2==0){
//                black.processEachRound();
//            }
//            else {
//                white.processEachRound();
//            }
//        }
    }

    public static ChessManager getInstance(ChessPane gamePane){
        if(INSTANCE == null){
            INSTANCE = new ChessManager(gamePane);
        }
        return INSTANCE;
    }

    /**
     * check if a cell has any chess
     * @param coord coordinate
     * @return true if have
     */
    public boolean haveChess(Coordinate coord){
        return (haveChess(coord, true) || haveChess(coord, true));
    }

    /**
     * check if a cell has a white or black chess
     * @param coord coordinate
     * @param isBlack check black cell
     * @return true if have
     */
    public boolean haveChess(Coordinate coord, boolean isBlack){
        if(isBlack) {
            for (Chess oneChess : blackChess) {
                if (oneChess.getCoordinate().equals(coord)) {
                    return true;
                }
            }
        }
        else{
            for (Chess oneChess : whiteChess) {
                if (oneChess.getCoordinate().equals(coord)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static ChessManager getInstance(){
        if(INSTANCE == null){
            throw new IllegalCallerException("please initialize chessManager first");
        }
        return INSTANCE;
    }


}
