package config;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.Movement;
import view.GamePlatformPane;

import java.util.EmptyStackException;
import java.util.Stack;

public class StepRecorder {

    private static StepRecorder stepRecorder = null;

    private Stack<Movement> movements = new Stack<>();
    private ObservableList<String> moves = FXCollections.observableArrayList();

    void addStep(Movement movement){
        movements.add(movement);

        String numOfStep = String.valueOf(movements.size());
        String info = movements.peek().toString();
        moves.add(numOfStep + ". " + info);
    }

    /**
     * get previous movement and remove it
     * @return previous movement if any. Null if otherwise
     */
    Movement processUndo(){
        if(moves.size() == 0){
            GamePlatformPane.getInstance().setSpecialNotice("No step to undo any more");
            return null;
        }
        moves.remove(moves.size() - 1);
        return movements.pop();
    }

    /**
     * @return previous movement if any. Null if otherwise
     */
    public Movement getPreviousMovement(){
        try {
            return movements.peek();
        }
        catch (EmptyStackException e){
            return null;
        }
    }

    public void clearMemory(){
        movements.clear();
        moves.clear();
    }

    public ObservableList<String> getMoves(){
        return moves;
    }

    public static StepRecorder getInstance(){
        if(stepRecorder == null){
            stepRecorder = new StepRecorder();
        }
        return stepRecorder;
    }
}
