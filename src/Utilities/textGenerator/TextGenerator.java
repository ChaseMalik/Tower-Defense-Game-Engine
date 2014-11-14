package Utilities.textGenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Locale.LanguageRange;
import java.util.Map;
import java.util.ResourceBundle;
import Utilities.XMLParsing.XMLParser;
import Utilities.XMLParsing.XMLParserInstantiator;

/**
 * Singleton class generates the text to be displayed on the GUI using resource bundles
 * @author allankiplagat
 *
 */
public class TextGenerator  {
    private XMLParser myParser;
    private String myBundlesPath;
    private Map<String, Locale> supportedLocales;
    private ResourceBundle myCurrentResourceBundle;

    public TextGenerator (String propertiesPath) {
        myParser = XMLParserInstantiator.getInstance(new File(propertiesPath));
        myBundlesPath = myParser.getValuesFromTag("BundlesPath").get(0);
        supportedLocales = new HashMap<String,Locale>();
        addSupportedLocales();
        setDefaultLanguage();
    }

    private void addLocale(String language,Locale locale) {
        supportedLocales.put(language, locale);
    }

    private void addSupportedLocales() {
        List<String> languagesSupported = myParser.getValuesFromTag("LanguagesSupported");
        
        for (String language:languagesSupported) {
            List<Locale.LanguageRange> range = new ArrayList<Locale.LanguageRange>();    
            range.add(new LanguageRange(language));
            Locale newLocale = Locale.lookup(range,Arrays.asList(Locale.getAvailableLocales()) );
            addLocale(language,newLocale);
        }
    }

    private void setDefaultLanguage() {
        String defaultLanguage = myParser.getValuesFromTag("DefaultLanguage").get(0);
        setLanguage(defaultLanguage);
    };

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

    /**
     * Checks whether the current language is supported
     * @param language
     * @return true if supported, false otherwise
     */
    public boolean languageSupported(String language) {
        return supportedLocales.containsKey(language);
    }
}
