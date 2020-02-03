package eventHandler;

import chess.Chess;
import config.ChessManager;
import view.ChessPane;
import view.GamePlatformPane;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import util.Coordinate;

public class ConfirmMoveEventHandler implements EventHandler<MouseEvent> {

    private Chess chess;
    private ChessPane chessPane;
    private Coordinate from;
    private Coordinate destination;
    private ChessManager chessManager;

    ConfirmMoveEventHandler(Chess chess, Coordinate destination){
        this.chess = chess;
        this.chessPane = ChessPane.getInstance();
        this.from = chess.getCoordinate();
        this.destination = destination;
        this.chessManager = ChessManager.getInstance();
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if(chessManager.moveAllowed(chess, destination)) {
            movingChessAndItsIcon();
            chessManager.goNextIteration();
        }
        else{
            String msg = "moving this chess will kill your king... please choose a better move";
            GamePlatformPane.getInstance().setSpecialNotice(msg);
        }
    }

    private void movingChessAndItsIcon(){
        chessPane.getOneCell(from).setGraphic(null);
        boolean isKilling = chessManager.processKillingChess(chess, destination);
        chess.setCurrentLocation(destination);
        chessPane.getOneCell(destination).setGraphic(new ImageView(chess.getIcon()));
        chessManager.updateRecord(chess.isBlack(), chess, from, destination, isKilling);
        chess.setIsMoved();
    }
}
