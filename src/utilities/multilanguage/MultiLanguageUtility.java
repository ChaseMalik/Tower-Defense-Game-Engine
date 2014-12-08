package utilities.multilanguage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.errorPopup.ErrorPopup;
import utilities.multilanguage.languageexceptions.LanguageNotFoundException;
import utilities.multilanguage.languageexceptions.LanguagePropertyNotFoundException;


/**
 * Utility for supporting multiple languages in the view.
 * The utility works by reading a list of resource files for different languages and
 * uses JavaFX ObjectProperties (which use native JavaFX Observer/Observable pattern)
 * to bind JavaFX nodes to certain texts. Furthermore, switching between languages is
 * supported by a call to the setLanguage(String languageName) method. For any JavaFX
 * node to update it's text it requires a 1-time setup to bind the property
 * e.g.,
 * myButton.getTextProperty().bind(MultiLanguageUtility.newInstance().getString("BUTTON_LABEL"));
 * 
 * public API:
 * getInstance() --> instance of the utility
 * initLanguages(String ... resourceFiles) --> initializes the languages in the utility
 * setLanguage(String language) --> sets the language of the utility
 * getCurrentLanguage() --> returns the current language
 * getSupportedLanguages() --> ObjectProperty holding ObservableList for supported languages
 * getStringProperty(String propertyName) --> gets the string property (ObjectProperty holding
 * String)
 * getStringListProperty(String propertyName) --> gets the string list property (ObjectProperty
 * holding ObservableList)
 * Requires a resource (.properties) file for each language.
 * The name of the language must be specified in the resource file as a tag "language=..."
 * If it is not, then the name of the language is assumed to be the name of the file.
 * 
 * Singleton pattern: 1 MultiLanguageUtility instance per program to handle language
 * (assumes that a program can only have 1 language at any given time)
 * 
 * @author Jonathan Tseng
 *
 */
public class MultiLanguageUtility {

    private static final String LANGUAGE_KEY = "Language";
    private static final String LIST_SPLITTER = "\\|";
    private static final String DIVIDER = "\\";
    private static final String PROPERTIES_FILE_EXTENSION = ".properties";
    private static final String DOT = ".";

    private static MultiLanguageUtility myOnlyInstance;

    private Map<String, Map<String, String>> myLanguagesToStringsMap;
    private Map<String, Map<String, List<String>>> myLanguagesToStringListsMap;
    private Map<String, ObjectProperty<String>> myStringProperties;
    private Map<String, ObjectProperty<ObservableList<String>>> myStringListProperties;
    private ObjectProperty<ObservableList<String>> myLanguages;
    private String myCurrentLanguage;

    /**
     * private constructor for the utility
     */
    private MultiLanguageUtility () {
        myLanguages = new SimpleObjectProperty<ObservableList<String>>();
        myLanguages.setValue(FXCollections.observableArrayList());
        myLanguagesToStringsMap = new HashMap<>();
        myLanguagesToStringListsMap = new HashMap<>();
    }

    /**
     * returns the single instance of the utility in the program
     * 
     * @return
     */
    public static synchronized MultiLanguageUtility getInstance () {
        if (myOnlyInstance == null) {
            myOnlyInstance = new MultiLanguageUtility();
        }
        return myOnlyInstance;
    }

    /**
     * initializes the languages available in the utility with the
     * given array of language resource files
     * 
     * @param languageResourceFiles
     */
    public void initLanguages (String ... languageResourceFiles) {
        List<String> paths = new ArrayList<>(Arrays.asList(languageResourceFiles));
        myLanguagesToStringsMap = new HashMap<>();
        myLanguagesToStringListsMap = new HashMap<>();
        myLanguages = new SimpleObjectProperty<ObservableList<String>>();
        myLanguages.setValue(FXCollections.observableArrayList());
        initLanguages(paths);
        updateProperties();
    }

    /**
     * sets the current language of the program if the language property file exists
     * 
     * @param language
     * @throws LanguageNotFoundException
     */
    public void setLanguage (String language) throws LanguageNotFoundException {
        if (myLanguagesToStringsMap.containsKey(language)) {
            myCurrentLanguage = language;
            updateProperties();
        }
        else {
            throw new LanguageNotFoundException(language);
        }
    }

    /**
     * returns the current language
     */
    public String getCurrentLanguage () {
        return myCurrentLanguage;
    }

    /**
     * returns an object property containing an observable list of the languages currently parsed
     * and supported by this utility
     * 
     * @return
     */
    public ObjectProperty<ObservableList<String>> getSupportedLanguages () {
        return myLanguages;
    }

    /**
     * returns the string property for a given key
     * 
     * @param propertyKey
     * @return
     * @throws LanguagePropertyNotFoundException
     */
    public ObjectProperty<String> getStringProperty (String propertyKey){
        ObjectProperty<String> property = null;
        try {
            property = getProperty(myStringProperties, propertyKey);
        }
        catch (LanguagePropertyNotFoundException e) {
            new ErrorPopup("Property " + propertyKey + " not found");
        }
        return property;
    }

    /**
     * returns the object property with an observablelist for a given key
     * the observablelist is a list of strings
     * in the resource file, a string list property must be "|" separated
     * e.g., animals=dog|cat|fish
     * 
     * @param propertyKey
     * @return
     * @throws LanguagePropertyNotFoundException
     */
    public ObjectProperty<ObservableList<String>> getStringListProperty (String propertyKey)
                                                                                            throws LanguagePropertyNotFoundException {
        return getProperty(myStringListProperties, propertyKey);
    }

    /**
     * helper method to get a generic objectproperty by key from a map
     * 
     * @param map
     * @param propertyKey
     * @return
     * @throws LanguagePropertyNotFoundException
     */
    private <T> ObjectProperty<T> getProperty (Map<String, ObjectProperty<T>> map,
                                               String propertyKey)
                                                                  throws LanguagePropertyNotFoundException {
        if (map.containsKey(propertyKey)) {
            return map.get(propertyKey);
        }
        else {
            throw new LanguagePropertyNotFoundException(propertyKey, myCurrentLanguage);
        }
    }

    /**
     * private helper method that takes list of files and reads them
     * also sets a random default language
     */
    private void initLanguages (List<String> fileNames) {
        List<String> languageFiles = fileNames.stream().filter( (path) -> {
            return PROPERTIES_FILE_EXTENSION.equals(fileExtension(path));
        }).collect(Collectors.toList());
        languageFiles.forEach( (file) -> {
            String resourcePath = file.substring(file.lastIndexOf(DIVIDER) + 1)
                    .replace(PROPERTIES_FILE_EXTENSION, "");
            ResourceBundle bundle = ResourceBundle.getBundle(resourcePath);
            String languageName =
                    (bundle.keySet().contains(LANGUAGE_KEY)) ? bundle.getString(LANGUAGE_KEY)
                                                            : fileName(file);
            addLanguage(bundle, languageName);
        });
        myCurrentLanguage = new ArrayList<>(myLanguagesToStringsMap.keySet()).get(0);
    }

    /**
     * private helper method to add a single language with resourcebundle to the utility
     * 
     * @param bundle
     * @param languageName
     */
    private void addLanguage (ResourceBundle bundle, String languageName) {
        Map<String, String> stringsMap = new HashMap<>();
        Map<String, List<String>> listsMap = new HashMap<>();
        bundle.keySet().forEach( (key) -> {
            List<String> values =
                    new ArrayList<>(Arrays.asList(bundle.getString(key)
                            .split(LIST_SPLITTER)));
            if (values.size() == 1) {
                stringsMap.put(key, bundle.getString(key));
            }
                                else {
                                    listsMap.put(key, values);
                                }
                            });
        myLanguagesToStringsMap.put(languageName, stringsMap);
        myLanguagesToStringListsMap.put(languageName, listsMap);
        myLanguages.getValue().add(languageName);
    }

    /**
     * creates the string properties that are sent to GUI components to bind to
     */
    private void createStringProperties () {
        myStringProperties = new HashMap<>();
        myLanguagesToStringsMap.get(myCurrentLanguage).keySet().forEach( (key) -> {
            myStringProperties.put(key, new SimpleObjectProperty<String>());
        });
    }

    /**
     * creates the string list properties that are sent to GUI components to bind to
     */
    private void createStringListProperties () {
        myStringListProperties = new HashMap<>();
        myLanguagesToStringListsMap.get(myCurrentLanguage).keySet().forEach( (key) -> {
            myStringListProperties.put(key, new SimpleObjectProperty<>());
            myStringListProperties.get(key).setValue(FXCollections.observableArrayList());
        });
    }

    /**
     * updates the properties that GUI components are bound to
     */
    private void updateProperties () {
        if (myStringProperties == null) {
            createStringProperties();
        }
        updateStringProperties();
        if (myStringListProperties == null) {
            createStringListProperties();
        }
        updateStringListProperties();
    }

    /**
     * updates the string properties that GUI components are bound to
     */
    private void updateStringProperties () {
        myLanguagesToStringsMap.get(myCurrentLanguage).forEach( (key, value) -> {
            if (myStringProperties.containsKey(key)) {
                myStringProperties.get(key).setValue(value);
            }
        });
    }

    /**
     * updates the string list properties that GUI components are bound to
     */
    private void updateStringListProperties () {
        myLanguagesToStringListsMap.get(myCurrentLanguage)
                .forEach( (key, value) -> {
                    if (myStringListProperties.containsKey(key)) {
                        ObservableList<String> strings =
                                myStringListProperties.get(key).getValue();
                        strings.clear();
                        strings.addAll(value);
                    }
                });
    }

    /**
     * gets the extension of a filePath
     * if no "." found, then returns the given filePath
     * 
     * @param filePath
     */
    private String fileExtension (String filePath) {
        return (!filePath.contains(DOT)) ? filePath : filePath.substring(filePath.lastIndexOf(DOT));
    }

    /**
     * gets the language name from the file path by removing the extension
     * 
     * @param filePath
     * @return
     */
    private String fileName (String filePath) {
        String fileName = filePath.replace(fileExtension(filePath), "");
        return (fileName.contains(DOT)) ? fileName.substring(fileName.lastIndexOf(DOT) + 1)
                                       : fileName;
    }

}
