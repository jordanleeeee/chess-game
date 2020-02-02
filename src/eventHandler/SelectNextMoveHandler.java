package eventHandler;

import Chess.Chess;
import config.ChessPane;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import util.Coordinate;

import java.util.ArrayList;

public class SelectNextMoveHandler implements EventHandler<MouseEvent> {

    private Chess chess;
    private ChessPane chessPane;

    public SelectNextMoveHandler(Chess chess){
        this.chess = chess;
        this.chessPane = ChessPane.getInstance();
    }
    @Override
    public void handle(MouseEvent mouseEvent) {
        for(int i = 0; i< ChessPane.height; i++){
            for(int j=0; j<ChessPane.width; j++){
                Label target = chessPane.getOneCell(i, j);
                target.setStyle(ChessPane.defaultGridStyle);
            }
        }
        System.out.println("chess clicked "+ chess.getClass().getName());
        ArrayList<Coordinate> availableNextMove = chess.getAvailableNextMovePosition();
        for(Coordinate destination: availableNextMove){
            Label target = ChessPane.getInstance().getOneCell(destination);
            target.setStyle(ChessPane.defaultAvailableMoveGuideStyle);
        }

    }
}
