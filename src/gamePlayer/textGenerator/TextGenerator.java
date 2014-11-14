package gamePlayer.textGenerator;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
    private static TextGenerator myself;
    private Map<String, Locale> supportedLocales;
    private ResourceBundle myCurrentResourceBundle;
    
    public static final String ENGLISH = "English";
    private static final String myPropertiesPath =  "./src/gamePlayer/properties/TextGeneratorProperties.XML";
    private XMLParser myParser;

    private static final String myBundlesPath = "gamePlayer.properties.languages/LanguagesBundle";
    
    private TextGenerator() {
        try {
            myParser = new XMLParser(new File(myPropertiesPath));
        }
        catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("Error creating XML parser\n");
            e.printStackTrace();
        }
        
        supportedLocales = new HashMap<String, Locale>();
        addSupportedLanguages();
        myCurrentResourceBundle = getResourceBundle(ENGLISH);
        
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

    private void addSupportedLanguages() {
        myParser.getValuesFromTag("LanguagesSupported");
        List<String> languagesSupported = myParser.getValuesFromTag("LanguagesSupported");
        for (String language:languagesSupported) {
            addLocale(language,new Locale(language));
        }
    }

    private ResourceBundle getResourceBundle (String language) {
        return ResourceBundle.getBundle(myBundlesPath,supportedLocales.get(language));
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
