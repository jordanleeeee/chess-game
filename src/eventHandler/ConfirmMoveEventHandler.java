package eventHandler;

import chess.Chess;
import config.AudioManager;
import config.ChessManager;
import view.GamePlatformPane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import util.Coordinate;

public class ConfirmMoveEventHandler implements EventHandler<MouseEvent> {

    private Chess chess;
    private Coordinate from;
    private Coordinate destination;
    private ChessManager chessManager;
    private SpecialEvent specialEvent;

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
            String msg = "moving this chess will kill your king... please choose a better move";
            GamePlatformPane.getInstance().setSpecialNotice(msg);
        }
    }

    private void movingChess(){
        boolean isKilling = chessManager.seeIfChessIsKillAfterMove(chess, destination, specialEvent);
        chessManager.havingChessMovement(chess, from, destination, isKilling, specialEvent);
    }

}
