package gamePlayer.mainClasses;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import Utilities.XMLParsing.XMLParser;

/**
 * Singleton class generates the text to be displayed on the GUI using resource bundles
 * @author allankiplagat
 *
 */
public class TextGenerator  {

    public static final String VOOGASALAD = "VOOGASALAD";

    public static final String ENGLISH = "English";

    private static TextGenerator myself;
    private Map<String, Locale> supportedLocales;
    private ResourceBundle myCurrentResourceBundle;
    
    private static final String myPropertiesPath =  "./src/gamePlayer/properties/TextGeneratorProperties.XML";
    private XMLParser myParser;

    private TextGenerator() {
        supportedLocales = new HashMap<String, Locale>();
        addSupportedLanguages();
        myCurrentResourceBundle = getResourceBundle(ENGLISH);
        
        try {
            myParser = new XMLParser(new File(myPropertiesPath));
        }
        catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("Error creating XML parser\n");
            e.printStackTrace();
        }
    }

    public static TextGenerator getInstance() {
        if (myself==null) {
            myself = new TextGenerator();
        } 
        return myself;
    }

    private void addLocale(String language,Locale locale) {
        supportedLocales.put(language, locale);
    }

    //in the future have this read from properties file too
    private void addSupportedLanguages() {
        addLocale(ENGLISH, Locale.ENGLISH);
    }

    private ResourceBundle getResourceBundle (String language) {
        return ResourceBundle.getBundle("resources.guiResources/LabelsBundle",
                                        supportedLocales.get(language));
    }

    /**
     * Method gets text in the current language
     * @param text
     * @return translated text
     */
    public String get(String text) {
        return myCurrentResourceBundle.getString(text);
    }

    /**
     * Method sets the current TextGenerator language
     * @param language
     * @return true if language switch successful, false otherwise
     */
    public boolean setLanguage (String language) {
        if (supportedLocales.containsKey(language)) {
            myCurrentResourceBundle = getResourceBundle(language); 
            return true;
        }
        return false;
    }

    public boolean languageSupported(String language) {
        return supportedLocales.containsKey(language);
    }
}
