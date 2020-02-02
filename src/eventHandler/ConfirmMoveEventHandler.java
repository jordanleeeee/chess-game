package eventHandler;

import Chess.Chess;
import Chess.Pawn;
import config.ChessManager;
import config.ChessPane;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import util.Coordinate;


public class ConfirmMoveEventHandler implements EventHandler<MouseEvent> {

    private Chess chess;
    private ChessPane chessPane;
    private Coordinate destination;
    private ChessManager chessManager;

    ConfirmMoveEventHandler(Chess chess, Coordinate destination){
        this.chess = chess;
        this.chessPane = ChessPane.getInstance();
        this.destination = destination;
        this.chessManager = ChessManager.getInstance();
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        Coordinate currentLocation = chess.getCoordinate();
        chessPane.getOneCell(currentLocation).setGraphic(null);
        chess.setCurrentLocation(destination);
        chessPane.getOneCell(destination).setGraphic(new ImageView(chess.getIcon()));
        if(chess instanceof Pawn){
            ((Pawn) chess).moved();
        }
        chessManager.goNextIteration();
    }
}
