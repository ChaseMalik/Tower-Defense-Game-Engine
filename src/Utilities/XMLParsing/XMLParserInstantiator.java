package Utilities.XMLParsing;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 * Utility class to allow cleaner code elsewhere in the project by having all of the try/catch blocks here
 * @author allankiplagat
 *
 */
public class XMLParserInstantiator {
    public static XMLParser getInstance(File file) {
        XMLParser parser = null;
        try {
            parser = new XMLParser(file);
        }
        catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("Error creating XML Parser\n");
            e.printStackTrace();
        }
        return parser;
    }
}
