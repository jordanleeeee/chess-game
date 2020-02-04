package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import view.GamePlatformPane;

import java.util.EmptyStackException;
import java.util.Stack;

public class StepRecorder {

    private static StepRecorder stepRecorder = new StepRecorder();

    private Stack<Movement> movements = new Stack<>();
    private ObservableList<String> moves = FXCollections.observableArrayList();


    public void addStep(Movement movement){
        movements.add(movement);
    }

    public void generatePreviousMoveDetails(){
        try {
            String info = movements.peek().toString();
            moves.add(info);
        }
        catch (EmptyStackException ignored){}
    }

    public Movement processUndo(){
        if(moves.size() == 0){
            GamePlatformPane.getInstance().setSpecialNotice("No step to undo any more");
            return null;
        }
        moves.remove(moves.size() - 1);
        return movements.pop();

    }

    public ObservableList<String> getMoves(){
        return moves;
    }

    public static StepRecorder getInstance(){
        return stepRecorder;
    }
}
