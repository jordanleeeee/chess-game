package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.EmptyStackException;
import java.util.Stack;

public class StepRecorder {

    private static StepRecorder stepRecorder = new StepRecorder();

    private Stack<Movement> movements = new Stack<>();
    private ObservableList<String> moves = FXCollections.observableArrayList();


    public void addStep(Movement movement){
        movements.add(movement);
    }

    public String getPeriousMoveDetails(){
        try {
            String info = movements.peek().toString();
            moves.add(info);
            return info;
        }
        catch (EmptyStackException e) {
            return "Ready? Go!!!";
        }
    }

    public ObservableList<String> getMoves(){
        return moves;
    }

    public static StepRecorder getInstance(){
        return stepRecorder;
    }
}
