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

    private static ChessManager INSTANCE = null;

    private ChessPane chessPane = ChessPane.getInstance();
    private GamePlatformPane gamePlatformPane = GamePlatformPane.getInstance();
    private StepRecorder stepRecorder = StepRecorder.getInstance();

    private ArrayList<Chess> blackChess = new ArrayList<>();
    private ArrayList<Chess> whiteChess = new ArrayList<>();

    private Stack<Chess> killedChess = new Stack<>();
    private Stack<Chess> killedBlackChess = new Stack<>();
    private Stack<Chess> killedWhiteChess = new Stack<>();

    private int round = 1;
    private Player black;
    private Player white;
    private boolean isGameOver = false;


    private ChessManager(){
        addChess();
        visualizeChess(blackChess);
        visualizeChess(whiteChess);
    }

    private void addChess(){
        blackChess.add(new Rook(0,0, true));
        blackChess.add(new Knight(0,1, true));
        blackChess.add(new Bishop(0,2, true));
        blackChess.add(new Queen(0,4, true));
        blackChess.add(new King(0,3, true));
        blackChess.add(new Bishop(0,5, true));
        blackChess.add(new Knight(0,6, true));
        blackChess.add(new Rook(0,7, true));
        for (int i=0; i<ChessPane.width; i++){
            blackChess.add(new Pawn(1,i, true));
        }

        for (int i = 0; i < ChessPane.width; i++) {
            whiteChess.add(new Pawn(6, i, false));
        }
        whiteChess.add(new Rook(7,7, false));
        whiteChess.add(new Knight(7,6, false));
        whiteChess.add(new Bishop(7,5, false));
        whiteChess.add(new Queen(7,3, false));
        whiteChess.add(new King(7,4, false));
        whiteChess.add(new Bishop(7,2, false));
        whiteChess.add(new Knight(7,1, false));
        whiteChess.add(new Rook(7,0, false));
    }

    private void visualizeChess(ArrayList<Chess> chess){
        for (Chess oneChess : chess) {
            oneChess.visualizeChess();
        }
    }

    private void clearAllChessIcon(ArrayList<Chess> chess){
        for (Chess oneChess : chess) {
            oneChess.clearChessIcon();
        }
    }

    public void startGame(@NotNull Player black, @NotNull Player white){
        gamePlatformPane.setSpecialNotice("Game start");
        AudioManager.getInstance().playSound(AudioManager.SoundRes.WIN);
        black.addChess(blackChess);
        white.addChess(whiteChess);
        this.black = black;
        this.white = white;
        goNextIteration();
    }

    private boolean isBlackTurn(){
        return (round%2 == 0);
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
        ArrayList<Chess> targetedChess = (isBlack)? blackChess: whiteChess;
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
            ArrayList<Chess> target = (isBlack)? blackChess: whiteChess;
            Stack<Chess> killedTarget = (isBlack)? killedBlackChess: killedWhiteChess;
            for(int i=0; i<target.size(); i++){
                Chess targetingChess = target.get(i);
                if(targetingChess.getCoordinate().equals(coord)){
                    killedChess.push(targetingChess);
                    killedTarget.push(targetingChess);
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
        round = 1;
        killedChess.clear();
        killedBlackChess.clear();
        killedWhiteChess.clear();
        clearAllChessIcon(blackChess);
        clearAllChessIcon(whiteChess);
        blackChess.clear();
        whiteChess.clear();
        addChess();
        visualizeChess(blackChess);
        visualizeChess(whiteChess);
        startGame(black, white);
        stepRecorder.clearMemory();
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
            killedBlackChess.pop();
            blackChess.add(target);
        }
        else {
            killedWhiteChess.pop();
            whiteChess.add(target);
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

    private Player getPlayer(boolean isBlack) {
        return (isBlack)? black: white;
    }

    public ArrayList<Chess> getChess(boolean isBlack){
        return (isBlack)? blackChess: whiteChess;
    }

    public Chess getOneChess(Coordinate coord){
        for(Chess oneChess: blackChess){
            if(oneChess.getCoordinate().equals(coord)){
                return oneChess;
            }
        }
        for(Chess oneChess: whiteChess){
            if(oneChess.getCoordinate().equals(coord)){
                return oneChess;
            }
        }
        return null;
    }

    public static ChessManager getInstance(){
        if(INSTANCE == null){
            INSTANCE = new ChessManager();
        }
        return INSTANCE;
    }

    public void chessClicked(@NotNull Chess chess, @NotNull String newType){
        ArrayList<Chess> target = (chess.isBlack())? blackChess: whiteChess;
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

    public void generatePromotionSelectionPane(@NotNull Chess chess){
        clearAllEventHandler();
        promotionSelectionStage stage = new promotionSelectionStage(chess);
        chessPane.blur();
        stage.showAndWait();
        chessPane.unblur();
    }
}