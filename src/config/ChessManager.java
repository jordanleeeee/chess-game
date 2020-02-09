package config;

import chess.*;
import eventHandler.SpecialEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import org.jetbrains.annotations.NotNull;
import util.*;
import view.promotionSelectionStage;
import view.ChessPane;
import view.GamePlatformPane;

import java.util.ArrayList;
import java.util.Stack;

public class ChessManager {

    private static ChessManager INSTANCE = new ChessManager();

    private ChessPane chessPane = ChessPane.getInstance();
    private GamePlatformPane gamePlatformPane = GamePlatformPane.getInstance();
    private StepRecorder stepRecorder = StepRecorder.getInstance();

    private Stack<Chess> killedChess = new Stack<>();

    private int round = 0;
    private Player black;
    private Player white;
    private boolean isGameOver = false;

    private ChessManager(){}

    public void startGame(@NotNull Player black, @NotNull Player white){
        gamePlatformPane.setSpecialNotice("Game start");
        AudioManager.getInstance().playSound(AudioManager.SoundRes.WIN);
        this.black = black;
        this.white = white;
        goNextIteration();
    }

    /**
     * check which player takes turn, according to rules: black go first in a new game
     * @return true if black takes turn, false if otherwise
     */
    private boolean isBlackTurn(){
        return (round%2 == 1);
    }

    private void clearAllEventHandler(){
        for (int i = 0; i < ChessPane.height; i++) {
            for (int j = 0; j < ChessPane.width; j++) {
                Label target = chessPane.getOneCell(i, j);
                target.setStyle(ChessPane.defaultGridStyle);
                target.setOnMouseClicked(null);
            }
        }
    }

    public void goNextIteration(){
        round++;
        processPlayer();
    }

    private void processPlayer(){
        boolean isBlackTurn = isBlackTurn();
        clearAllEventHandler();
        if(isBlackTurn){
            gamePlatformPane.setGameInfo("Black turn");
            black.processEachRound();
        }
        else{
            gamePlatformPane.setGameInfo("White turn");
            white.processEachRound();
        }
    }

    void generateLoseNotification(boolean isCheckmate){
        AudioManager.getInstance().playSound(AudioManager.SoundRes.LOSE);
        clearAllEventHandler();
        isGameOver = true;
        chessPane.blur();

        String msg = "";
        if(isCheckmate) { msg += "Checkmate!!! "; }
        msg+= (isBlackTurn())?"Black": "White";
        msg+= (isCheckmate)? " loss...": " resign...";
        msg+="\nGood Job ";
        msg+= (!isBlackTurn())? black.getName(): white.getName();
        msg+=" you win";
        gamePlatformPane.setSpecialNotice(msg);
    }

    /**
     * check if a cell has any chess
     * @param coord coordinate
     * @return true if have
     */
    public boolean haveChess(@NotNull Coordinate coord){
        return (haveChess(coord, true) || haveChess(coord, false));
    }

    /**
     * check if a cell has a white or black chess
     * @param coord coordinate
     * @param isBlack check black cell
     * @return true if have
     */
    public boolean haveChess(@NotNull Coordinate coord, boolean isBlack){
        ArrayList<Chess> targetedChess = (isBlack)? black.getOwnedChess(): white.getOwnedChess();
        for (Chess oneChess : targetedChess) {
            if (oneChess.getCoordinate().equals(coord)) {
                return true;
            }
        }
        return false;
    }

    /**
     * check if any chess being killed after the move. If yes, remove it from the list of chess
     * @param chess the chess that being move
     * @param destination the destination of the move
     * @return true of some chess got killed
     */
    public boolean seeIfChessIsKillAfterMove(@NotNull Chess chess, @NotNull Coordinate destination, SpecialEvent specialEvent){
        if(haveChess(destination)){
            removeOneChess(destination, !chess.isBlack());
            return true;
        }
        if(specialEvent == SpecialEvent.enPassant){
            if(chess.isBlack()){
                removeOneChess(destination.getUpward(), !chess.isBlack());
            }
            else {
                removeOneChess(destination.getDownward(), !chess.isBlack());
            }
            return true;
        }
        return false;
    }

    private void removeOneChess(@NotNull Coordinate coord, boolean isBlack){
        if(haveChess(coord)){
            ArrayList<Chess> target = (isBlack)? black.getOwnedChess(): white.getOwnedChess();
            for(int i=0; i<target.size(); i++){
                Chess targetingChess = target.get(i);
                if(targetingChess.getCoordinate().equals(coord)){
                    killedChess.push(targetingChess);
                    targetingChess.clearChessIcon();
                    target.remove(i);
                    break;
                }
            }
        }
        else {
            throw new IllegalStateException("no chess in such coordinate");
        }
    }

    public void havingChessMovement(@NotNull Chess chess, @NotNull Coordinate from, @NotNull Coordinate to, boolean isKilling, SpecialEvent specialEvent) {
        Player player = (chess.isBlack())? black: white;
        Movement movement = new Movement(player, chess, from, to, isKilling, specialEvent);
        stepRecorder.addStep(movement);
        gamePlatformPane.setSpecialNotice("");
    }

    public void revivalOneChess(){
        Chess target = killedChess.pop();

        if ((target.isBlack())) {
            black.getOwnedChess().add(target);
        }
        else {
            white.getOwnedChess().add(target);
        }

        target.visualizeChess();
    }

    public boolean isMoveAllowed(@NotNull Chess chess, @NotNull Coordinate destination, SpecialEvent specialEvent){
        boolean isAllowed = true;
        Coordinate currentLocation = chess.getCoordinate();
        boolean haveChessGotKilled = seeIfChessIsKillAfterMove(chess, destination, specialEvent);
        chess.setCurrentLocation(destination);
        if (getPlayer(chess.isBlack()).isInDanger()) {
            isAllowed = false;
        }
        chess.setCurrentLocation(currentLocation);
        if(haveChessGotKilled){
            revivalOneChess();
        }
        return isAllowed;
    }

    public void chessClicked(@NotNull Chess chess, @NotNull String newType){
        ArrayList<Chess> target = (chess.isBlack())? black.getOwnedChess(): white.getOwnedChess();
        Chess newChess = null;
        int i;

        switch (newType) {
            case "Queen":
                for(i=0; i<target.size(); i++){
                    if(target.get(i) == chess){
                        newChess = new Queen(chess.getCoordinate().getRow(), chess.getCoordinate().getCol(), chess.isBlack());
                        break;
                    }
                }
                break;
            case "Knight":
                for(i=0; i<target.size(); i++){
                    if(target.get(i) == chess){
                        newChess = new Knight(chess.getCoordinate().getRow(), chess.getCoordinate().getCol(), chess.isBlack());
                        break;
                    }
                }
                break;
            case "Bishop":
                for(i=0; i<target.size(); i++){
                    if(target.get(i) == chess){
                        newChess = new Bishop(chess.getCoordinate().getRow(), chess.getCoordinate().getCol(), chess.isBlack());
                        break;
                    }
                }
                break;
            case "Rook":
                for(i=0; i<target.size(); i++){
                    if(target.get(i) == chess){
                        newChess = new Rook(chess.getCoordinate().getRow(), chess.getCoordinate().getCol(), chess.isBlack());
                        break;
                    }
                }
                break;
            default: throw new IllegalStateException();
        }
        assert newChess != null;

        target.set(i, newChess);
        newChess.visualizeChess();


    }

    public void resign(){
        chessPane.blur();
        getPlayer(isBlackTurn()).wantResign();
    }

    public void undoStep(){
        gamePlatformPane.setSpecialNotice("");
        Movement previousMovement = stepRecorder.processUndo();
        if(previousMovement != null) {
            previousMovement.reverseMovement();
            round--;
            processPlayer();
        }
    }

    public void wantNewGame(){
        if(isGameOver){
            chessPane.unblur();
            newGame();
        }
        else {
            Alert optForResign = new Alert(Alert.AlertType.WARNING, "Are you sure you want to start a new game? Current game process will not be save.", ButtonType.OK, ButtonType.CANCEL);
            optForResign.showAndWait().ifPresent(type -> {
                if (type == ButtonType.OK) {
                    ChessManager.getInstance().newGame();
                }
            });
        }
    }

    private void newGame(){
        round = 0;
        killedChess.clear();
        black.processNewGame();
        white.processNewGame();
        stepRecorder.clearMemory();
        isGameOver = false;
        startGame(black, white);
    }

    public void generatePromotionSelectionPane(@NotNull Chess chess){
        clearAllEventHandler();
        promotionSelectionStage stage = new promotionSelectionStage(chess);
        chessPane.blur();
        stage.showAndWait();
        chessPane.unblur();
    }

    private Player getPlayer(boolean isBlack) {
        return (isBlack)? black: white;
    }

    public ArrayList<Chess> getChess(boolean isBlack){
        return (isBlack)? black.getOwnedChess(): white.getOwnedChess();
    }

    public Chess getOneChess(Coordinate coord){
        for(Chess oneChess: black.getOwnedChess()){
            if(oneChess.getCoordinate().equals(coord)){
                return oneChess;
            }
        }
        for(Chess oneChess: white.getOwnedChess()){
            if(oneChess.getCoordinate().equals(coord)){
                return oneChess;
            }
        }
        return null;
    }

    public static ChessManager getInstance(){
        return INSTANCE;
    }
}