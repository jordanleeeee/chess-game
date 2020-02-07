package config;

import chess.Chess;
import chess.King;
import eventHandler.SelectNextMoveHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import view.ChessPane;
import view.GamePlatformPane;

import java.util.ArrayList;

public class Player {

    private String name;
    private ArrayList<Chess> ownedChess;
    private King king;

    public Player(String name){
        this.name = name;
    }

    void addChess(ArrayList<Chess> chess){
        ownedChess = chess;
        identifyKing();
    }

    private void identifyKing(){
        for(Chess oneChess: ownedChess){
            if(oneChess instanceof King){
                king = (King)oneChess;
                break;
            }
        }
    }

    void processEachRound(){
        if(isLoss()){
            ChessManager.getInstance().generateLoseNotification(true);
            return;
        }

        if (isInDanger()) {
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

    void wantResign(){
        Alert optForResign = new Alert(Alert.AlertType.WARNING, "Are you sure you want to resign?", ButtonType.OK, ButtonType.CANCEL);
        optForResign.showAndWait().ifPresent(type -> {
            if (type == ButtonType.OK) {
                ChessManager.getInstance().generateLoseNotification(false);
            }
            else {
                ChessPane.getInstance().unblur();
            }
        });
    }

    boolean isInDanger(){
        return king.isCheck();
    }

    private boolean isLoss() {
        return king.isCheckmate();
    }

    public String getName(){ return name; }
}
