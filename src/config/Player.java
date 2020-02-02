package config;

import Chess.Chess;
import eventHandler.SelectNextMoveHandler;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class Player {

    private String name;
    private ArrayList<Chess> ownedChess;

    Player(String name){
        this.name = name;
    }

    public void addChess(ArrayList<Chess> chess){
        ownedChess = chess;
    }
    String getName(){ return name; }

    public void processEachRound(){
        addEventHandler();
    }

    private void addEventHandler(){
        System.out.println("hey"+ name+"'s turn");
        for(Chess oneChess: ownedChess){
            Label target = ChessPane.getInstance().getOneCell(oneChess.getCoordinate());
            target.setOnMouseClicked(new SelectNextMoveHandler(oneChess));
        }
    }
}
