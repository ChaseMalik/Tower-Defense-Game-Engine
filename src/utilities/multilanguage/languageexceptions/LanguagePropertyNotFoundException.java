package utilities.multilanguage.languageexceptions;

/**
 * Exception to throw when a language property cannot be found
 * 
 * @author Jonathan Tseng
 *
 */
public class LanguagePropertyNotFoundException extends LanguageException {

    /**
     * Auto-generated
     */
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE_FORMAT =
            "Failed to find language property: %s for current language: %s";

    public LanguagePropertyNotFoundException (String property, String language) {
        super(String.format(MESSAGE_FORMAT, property, language));
    }

}
