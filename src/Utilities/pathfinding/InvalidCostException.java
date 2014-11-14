package Utilities.pathfinding;

public class InvalidCostException extends IllegalArgumentException{

    private final static String myMessage = "Invalid path or heuristic cost";
    
    public InvalidCostException(){
        super(myMessage);
    }
}
