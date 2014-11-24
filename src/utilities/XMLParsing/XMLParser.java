package utilities.XMLParsing;

import gameAuthoring.scenes.actorBuildingScenes.behaviorBuilders.SliderInfo;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.geometry.Dimension2D;
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

    private static final String SLIDER_NAME = "sliderName";
    private static final String SLIDER_MIN = "sliderMin";
    private static final String SLIDER_MAX = "sliderMax";
    private Document myDocument;

    public XMLParser(File file)  {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            myDocument = builder.parse(new FileInputStream(file));
        }
        catch (ParserConfigurationException |SAXException | IOException e) {
            System.out.println("Error creating XML parser\n");
            e.printStackTrace();
        }
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
    
    public List<String> getAllBehaviorTypes() {
        List<String> allBehaviorTypes = new ArrayList<String>();
        NodeList behaviorTypesNodes = myDocument.getDocumentElement().getChildNodes();
        for(int i = 0; i < behaviorTypesNodes.getLength(); i++){
            Node n = behaviorTypesNodes.item(i);
            if(n instanceof Element) {
                allBehaviorTypes.add(n.getNodeName());
            }
        }
        return allBehaviorTypes;
    }
    
    public SliderInfo getSliderInfo (String behaviorType) {
        Element tag =  (Element) myDocument.getElementsByTagName(behaviorType).item(0);
        String sliderName = tag.getAttribute(SLIDER_NAME);
        double sliderMin = Double.parseDouble(tag.getAttribute(SLIDER_MIN));
        double sliderMax = Double.parseDouble(tag.getAttribute(SLIDER_MAX));
        return new SliderInfo(sliderName, sliderMin, sliderMax);
    }
    

    public List<Integer> getIntegerValuesFromTag(String tagName) {
        return getValuesFromTag(tagName)
                .stream().map(s->Integer.parseInt(s)).collect(Collectors.toList());
    }
    
    public List<Double> getDoubleValuesFromTag(String tagName) {
        return getValuesFromTag(tagName)
                .stream().map(s->Double.parseDouble(s)).collect(Collectors.toList());
    }
    
    public Dimension2D getDimension(String tagName) {
        List<Double> dimensionList = getDoubleValuesFromTag(tagName);
        return new Dimension2D(dimensionList.get(0),dimensionList.get(1));
    }    
}