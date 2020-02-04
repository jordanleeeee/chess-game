package eventHandler;

import chess.*;
import config.ChessManager;
import view.ChessPane;
import view.GamePlatformPane;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import util.Coordinate;

import java.util.Scanner;

public class ConfirmMoveEventHandler implements EventHandler<MouseEvent> {

    private Chess chess;
    private ChessPane chessPane;
    private Coordinate from;
    private Coordinate destination;
    private ChessManager chessManager;
    private boolean castingHappened;

    ConfirmMoveEventHandler(Chess chess, Coordinate destination, boolean isCastingHappened){
        this.chess = chess;
        this.chessPane = ChessPane.getInstance();
        this.from = chess.getCoordinate();
        this.destination = destination;
        this.chessManager = ChessManager.getInstance();
        castingHappened = isCastingHappened;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if(chessManager.moveAllowed(chess, destination)) {
            movingChessAndItsIcon();
            handleSpecialEvent();
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
        chess.setIsMoved();

        if(castingHappened){
            if(chess.isBlack()){
                Chess rook = chessManager.getOneChess(new Coordinate(0,0));
                chessPane.getOneCell(0,0).setGraphic(null);
                chessPane.getOneCell(0,2).setGraphic(new ImageView((Rook.blackIcon)));
                rook.setCurrentLocation(new Coordinate(0,2));
            }
            else{
                Chess rook = chessManager.getOneChess(new Coordinate(7,7));
                chessPane.getOneCell(7,7).setGraphic(null);
                chessPane.getOneCell(7,5).setGraphic(new ImageView((Rook.whiteIcon)));
                rook.setCurrentLocation(new Coordinate(7,5));
            }
        }

        chessManager.updateRecord(chess, from, destination, isKilling, castingHappened);

    }

    private void handleSpecialEvent(){
        if(chess instanceof Pawn){
            if(((Pawn) chess).isReachedBoundary()){
                chessManager.promotion(chess);
            }
        }
    }
}
