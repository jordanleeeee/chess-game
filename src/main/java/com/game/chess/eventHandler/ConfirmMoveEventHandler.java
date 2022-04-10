package com.game.chess.eventHandler;


import com.game.chess.chess.Chess;
import com.game.chess.config.AudioManager;
import com.game.chess.config.ChessManager;
import com.game.chess.util.Coordinate;
import com.game.chess.view.GamePlatformPane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;


public class ConfirmMoveEventHandler implements EventHandler<MouseEvent> {

    private final Chess chess;
    private final Coordinate from;
    private final Coordinate destination;
    private final ChessManager chessManager;
    private final SpecialEvent specialEvent;

    ConfirmMoveEventHandler(Chess chess, Coordinate destination, SpecialEvent specialEvent){
        this.chess = chess;
        this.from = chess.getCoordinate();
        this.destination = destination;
        this.chessManager = ChessManager.getInstance();
        this.specialEvent = specialEvent;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if(chessManager.isMoveAllowed(chess, destination, specialEvent)) {
            AudioManager.getInstance().playSound(AudioManager.SoundRes.MOVE);
            movingChess();
            chessManager.goNextIteration();
        }
        else{
            String msg = "moving this chess will kill your king... \nPlease choose a better move";
            GamePlatformPane.getInstance().setSpecialNotice(msg);
        }
    }

    private void movingChess(){
        boolean isKilling = chessManager.seeIfChessIsKillAfterMove(chess, destination, specialEvent);
        chessManager.havingChessMovement(chess, from, destination, isKilling, specialEvent);
    }

}
