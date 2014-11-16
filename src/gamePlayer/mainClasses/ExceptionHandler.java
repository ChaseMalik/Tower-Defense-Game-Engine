package gamePlayer.mainClasses;

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
    
}
