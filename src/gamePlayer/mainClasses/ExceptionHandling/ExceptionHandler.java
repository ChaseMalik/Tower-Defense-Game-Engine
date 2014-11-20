package gamePlayer.mainClasses.ExceptionHandling;

/**
 * Class handles exceptions thrown in the GUI
 * @author allankiplagat
 *
 */
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
    
    //TODO: Add actual exception-handling code
    public void handle(NullPointerException e) {
        System.out.println("Null pointer exception\n");
        e.printStackTrace();
    }
}
