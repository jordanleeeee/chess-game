package eventHandler;

import chess.Chess;
import config.ChessManager;
import view.ChessPane;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import util.Coordinate;

import java.util.ArrayList;

public class SelectNextMoveHandler implements EventHandler<MouseEvent> {

    private Chess chess;
    private ChessPane chessPane;
    private ChessManager chessManager;

    public SelectNextMoveHandler(Chess chess){
        this.chess = chess;
        this.chessPane = ChessPane.getInstance();
        this.chessManager = ChessManager.getInstance();
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        changeBackToDefaultChessBoard();
        //System.out.println("chess clicked "+ chess.getClass().getName());
        ArrayList<Coordinate> availableNextMove = chess.getAvailableNextMovePosition();
        for(Coordinate destination: availableNextMove){
            Label target = ChessPane.getInstance().getOneCell(destination);
            target.setStyle(ChessPane.defaultAvailableMoveGuideStyle);
            target.setOnMouseClicked(new ConfirmMoveEventHandler(chess, destination));
        }
    }

    private void changeBackToDefaultChessBoard(){
        for(int i = 0; i< ChessPane.height; i++){
            for(int j=0; j<ChessPane.width; j++){
                Label target = chessPane.getOneCell(i, j);
                target.setStyle(ChessPane.defaultGridStyle);
                //clear eventHandler of all grid with no same color chess
                if(!chessManager.haveChess(new Coordinate(i, j), chess.isBlack())){
                    target.setOnMouseClicked(null);
                }
            }
        }
    }

}
