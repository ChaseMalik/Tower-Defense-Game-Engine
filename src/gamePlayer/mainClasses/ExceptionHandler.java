package gamePlayer.mainClasses;

//TODO Deprecated class for now 
public class ExceptionHandler {
    private static ExceptionHandler myReference = null;
    private ExceptionHandler() {
    }
    
    public static ExceptionHandler getInstance() {
        if (myReference==null) {
            myReference = new ExceptionHandler();
        }
        return myReference;
    }   
    
    public void handle(ReflectiveOperationException e) {
        System.out.println("ReflectiveOperationException exception raised\n");
        e.printStackTrace();
    }
    
}
