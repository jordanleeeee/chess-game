package config;

import chess.Chess;
import chess.King;
import eventHandler.SelectNextMoveHandler;
import javafx.scene.control.Label;
import view.ChessPane;
import view.GamePlatformPane;

import java.util.ArrayList;

public class Player {

    private String name;
    private ArrayList<Chess> ownedChess;
    private King king;

    Player(String name){
        this.name = name;
    }

    void addChess(ArrayList<Chess> chess){
        ownedChess = chess;
        identifyKing();
    }

    void identifyKing(){
        for(Chess oneChess: ownedChess){
            if(oneChess instanceof King){
                king = (King)oneChess;
                break;
            }
        }
    }

    public String getName(){ return name; }

    void processEachRound(){
        if (isInDanger()) {
            System.out.println("Check...........");
            GamePlatformPane.getInstance().setSpecialNotice("Check...........");
        }
        addEventHandler();
    }

    private void addEventHandler(){
        for(Chess oneChess: ownedChess){
            Label target = ChessPane.getInstance().getOneCell(oneChess.getCoordinate());
            target.setOnMouseClicked(new SelectNextMoveHandler(oneChess));
        }
    }

    public boolean isInDanger(){
        return king.isCheck();
    }

    public boolean isLoss() {
        return king.isCheckmate();
    }
}
