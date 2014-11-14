package Utilities.XMLParsing;

import java.io.File;

/**
 * Utility class to allow cleaner code elsewhere in the project by having all of the try/catch blocks here
 * @author allankiplagat
 *
 */
public class XMLParserInstantiator {
    public static XMLParser getInstance(File file) {
        return new XMLParser(file);
    }
}
