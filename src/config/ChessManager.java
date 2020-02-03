package config;

import chess.*;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import util.Coordinate;
import util.Movement;
import util.StepRecorder;
import view.ChessPane;
import view.GamePlatformPane;

import java.util.ArrayList;
import java.util.Stack;

public class ChessManager {

    private static ChessManager INSTANCE = null;

    private ChessPane chessPane = ChessPane.getInstance();
    private GamePlatformPane gamePlatformPane = GamePlatformPane.getInstance();

    private ArrayList<Chess> allChess = new ArrayList<>();
    private ArrayList<Chess> blackChess = new ArrayList<>();
    private ArrayList<Chess> whiteChess = new ArrayList<>();

    private Stack<Chess> killedChess = new Stack<>();
    private Stack<Chess> killedBlackChess = new Stack<>();
    private Stack<Chess> killedWhiteChess = new Stack<>();

    private StepRecorder stepRecorder = StepRecorder.getInstance();

    private int round = 1;
    private Player black;
    private Player white;


    private ChessManager(){
        addChess();
        separateChess();
        visualizeChess();
    }

    private void addChess(){
        allChess.add(new Rook(0,0, true));
        allChess.add(new Knight(0,1, true));
        allChess.add(new Bishop(0,2, true));
        allChess.add(new Queen(0,3, true));
        allChess.add(new King(0,4, true));
        allChess.add(new Bishop(0,5, true));
        allChess.add(new Knight(0,6, true));
        allChess.add(new Rook(0,7, true));
        for (int i=0; i<ChessPane.width; i++){
            allChess.add(new Pawn(1,i, true));
        }

        for (int i=0; i<ChessPane.width; i++){
            allChess.add(new Pawn(6,i, false));
        }
        allChess.add(new Rook(7,7, false));
        allChess.add(new Knight(7,6, false));
        allChess.add(new Bishop(7,5, false));
        allChess.add(new Queen(7,4, false));
        allChess.add(new King(7,3, false));
        allChess.add(new Bishop(7,2, false));
        allChess.add(new Knight(7,1, false));
        allChess.add(new Rook(7,0, false));
    }

    private void separateChess(){
        for(Chess oneChess: allChess){
            if(oneChess.isBlack()){
                blackChess.add(oneChess);
            }
            else{
                whiteChess.add(oneChess);
            }
        }
    }

    private void visualizeChess(){
        for(Chess oneChess: allChess){
            Label target = chessPane.getOneCell(oneChess.getCoordinate());
            target.setGraphic(new ImageView(oneChess.getIcon()));
            target.setAlignment(Pos.CENTER);
        }
    }

    void startGame(Player black, Player white){
        black.addChess(blackChess);
        white.addChess(whiteChess);
        this.black = black;
        this.white = white;
        goNextIteration();
    }

    public void goNextIteration(){
        gamePlatformPane.setSpecialNotice("");
        System.out.println(stepRecorder.getPeriousMoveDetails());
        clearAllEventHandler();

        boolean isBlackTurn = (++round%2 == 0);
        if(isGameOver(isBlackTurn)){
            String msg = "Checkmate...";
            msg+= (isBlackTurn)?"Black": "White"+" loss... Good Job ";
            msg+= (!isBlackTurn)? black.getName(): white.getName();
            gamePlatformPane.setSpecialNotice(msg);
            return;
        }
        if(isBlackTurn){
            gamePlatformPane.setGameInfo("Black turn");
            black.processEachRound();
        }
        else{
            gamePlatformPane.setGameInfo("White turn");
            white.processEachRound();
        }
    }

    public void updateRecord(boolean isBlack, Chess chess, Coordinate from, Coordinate to, boolean isKilling){
        Player player = (isBlack)? black: white;
        stepRecorder.addStep(new Movement(player, chess, from, to, isKilling));
    }

    /**
     * check if a cell has any chess
     * @param coord coordinate
     * @return true if have
     */
    public boolean haveChess(Coordinate coord){
        return (haveChess(coord, true) || haveChess(coord, false));
    }

    /**
     * check if a cell has a white or black chess
     * @param coord coordinate
     * @param isBlack check black cell
     * @return true if have
     */
    public boolean haveChess(Coordinate coord, boolean isBlack){
        ArrayList<Chess> targetedChess = (isBlack)? blackChess: whiteChess;
        for (Chess oneChess : targetedChess) {
            if (oneChess.getCoordinate().equals(coord)) {
                return true;
            }
        }
        return false;
    }

    private void removeOneChess(Coordinate coord, boolean isBlack){
        if(haveChess(coord)){
            for(int i=0; i<allChess.size(); i++){
                if(allChess.get(i).getCoordinate().equals(coord)){
                    killedChess.push(allChess.get(i));
                    allChess.remove(i);
                    break;
                }
            }
            ArrayList<Chess> target = (isBlack)? blackChess: whiteChess;
            Stack<Chess> killedTarget = (isBlack)? killedBlackChess: killedWhiteChess;
            for(int i=0; i<target.size(); i++){
                if(target.get(i).getCoordinate().equals(coord)){
                    killedTarget.push(target.get(i));
                    target.remove(i);
                    break;
                }
            }
        }
        else {
            throw new IllegalStateException("no chess in such coordinate");
        }
    }

    private void revivalOneChess(){
        Chess target = killedChess.pop();
        if(target == null){
            throw new IllegalStateException();
        }

        if ((target.isBlack())) {
            killedBlackChess.pop();
            blackChess.add(target);
        }
        else {
            killedWhiteChess.pop();
            whiteChess.add(target);
        }
        allChess.add(target);
    }

    private void clearAllEventHandler(){
        for(int i = 0; i< ChessPane.height; i++){
            for(int j=0; j<ChessPane.width; j++){
                Label target = chessPane.getOneCell(i, j);
                target.setStyle(ChessPane.defaultGridStyle);
                target.setOnMouseClicked(null);
            }
        }
    }

    private boolean isGameOver(boolean isBlackTurn){
        return getPlayer(isBlackTurn).isLoss();
    }

    public boolean moveAllowed(Chess chess, Coordinate destination){
        Coordinate currentLocation = chess.getCoordinate();
        boolean haveChessGotKilled = processKillingChess(chess, destination);
        chess.setCurrentLocation(destination);
        if (getPlayer(chess.isBlack()).isInDanger()) {
            chess.setCurrentLocation(currentLocation);
            if(haveChessGotKilled){
                revivalOneChess();
            }
            return false;
        }
        chess.setCurrentLocation(currentLocation);
        if(haveChessGotKilled){
            revivalOneChess();
        }
        return true;
    }

    /**
     * check if some chess being killed, if yes, remove that chess and return true
     * @return true of some chess being killed
     */
    public boolean processKillingChess(Chess chess, Coordinate destination){
        if(haveChess(destination)){
            removeOneChess(destination, !chess.isBlack());
            return true;
        }
        return false;
    }

    public static ChessManager getInstance(){
        if(INSTANCE == null){
            INSTANCE = new ChessManager();
        }
        return INSTANCE;
    }

    public ArrayList<Chess> getChess(boolean isBlack){
        return (isBlack)? blackChess: whiteChess;
    }

    private Player getPlayer(boolean isBlack) {
        return (isBlack)? black: white;
    }

}