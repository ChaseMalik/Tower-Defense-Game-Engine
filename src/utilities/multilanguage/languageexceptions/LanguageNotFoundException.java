package utilities.multilanguage.languageexceptions;



/**
 * Exception thrown when a language to be set to cannot be found
 * 
 * @author Jonathan Tseng
 *
 */
public class LanguageNotFoundException extends LanguageException {

    /**
     * Auto-generated
     */
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE_FORMAT = "Set language not found (%s)";

    public LanguageNotFoundException (String language) {
        super(String.format(MESSAGE_FORMAT, language));
    }

}
