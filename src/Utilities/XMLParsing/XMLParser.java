package Utilities.XMLParsing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * An XML Parser utility that will allow different parts of the application
 * to parse data files for specific information.
 * @author Austin Kyker
 *
 */
public class XMLParser {

    private Document myDocument;

    public XMLParser(File file) throws ParserConfigurationException, FileNotFoundException, 
    SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        myDocument = builder.parse(new FileInputStream(file));
    }

    /**
     * This will be used in game authoring environment to get the different behavior classes.
     * For instance tagName might be "Movement" and the returning list will have "FastMovement",
     * "SlowMovement", "NoMovement", etc.
     * @param tagName
     */
    public List<String> getValuesFromTag(String tagName) {
        List<String> valuesFromTag = new ArrayList<String>();
        Element tag =  (Element) myDocument.getElementsByTagName(tagName).item(0);
        NodeList tagChildren = tag.getChildNodes();
        for(int i = 0; i < tagChildren.getLength(); i++){
            Node n = tagChildren.item(i);
            if(n instanceof Element){
                valuesFromTag.add(tagChildren.item(i).getTextContent());
            }
        }
        return valuesFromTag;
    }

    public List<Integer> getIntegerValuesFromTag(String tagName) {
        return getValuesFromTag(tagName)
                .stream().map(s->Integer.parseInt(s)).collect(Collectors.toList());
    }
}
