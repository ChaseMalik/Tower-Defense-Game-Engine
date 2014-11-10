package gameEngine.backendExceptions;

/**
 * @author $cotty $haw
 *
 */
@SuppressWarnings("serial")
public class BackendException extends Exception {

    public BackendException (Exception ex, String exceptionNotification) {
        super(exceptionNotification, ex);
    }
}