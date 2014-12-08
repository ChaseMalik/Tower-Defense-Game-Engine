package utilities.XMLParsing;

import gameAuthoring.mainclasses.Constants;
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
import utilities.errorPopup.ErrorPopup;

/**
 * An XML Parser utility that will allow different parts of the application
 * to parse data files for specific information.
 * @author Austin Kyker
 *
 */
public class XMLParser {

    private static final String SLIDER_NAME = "name";
    private static final String SLIDER_MIN = "min";
    private static final String SLIDER_MAX = "max";
    private static final String ELEMENTS = "elements";
    private static final String SLIDERS = "sliders";
    private Document myDocument;

    public XMLParser(File file)  {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            myDocument = builder.parse(new FileInputStream(file));
        }
        catch (ParserConfigurationException |SAXException | IOException e) {
            new ErrorPopup(Constants.XML_LOADING_ERROR);
        }
    }

    /**
     * This will be used in game authoring environment to get the different behavior classes.
     * For instance tagName might be "Movement" and the returning list will have "FastMovement",
     * "SlowMovement", "NoMovement", etc.
     * @param tagName
     */    
    public List<String> getBehaviorElementsFromTag(String tagName) {
        Element tag =  (Element) myDocument.getElementsByTagName(tagName).item(0);
        Element elementsTag = (Element) tag.getElementsByTagName(ELEMENTS).item(0);
        return getValues(elementsTag);
    }


    public List<String> getValuesFromTag(String tagName) {
        Element tag =  (Element) myDocument.getElementsByTagName(tagName).item(0);
        return getValues(tag);
    }



    private List<String> getValues (Element tag) {
        List<String> valuesFromTag = new ArrayList<String>();
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

    public List<SliderInfo> getSliderInfo (String behaviorType) {
        List<SliderInfo> sliderInfoList = new ArrayList<SliderInfo>();
        Element tag =  (Element) myDocument.getElementsByTagName(behaviorType).item(0);
        NodeList sliders = tag.getElementsByTagName(SLIDERS).item(0).getChildNodes();
        for(int i = 0; i < sliders.getLength(); i++) {
            if(sliders.item(i) instanceof Element) {
                Element slider = (Element) sliders.item(i);
                String sliderName = slider.getAttribute(SLIDER_NAME);
                double sliderMin = Double.parseDouble(slider.getAttribute(SLIDER_MIN));
                double sliderMax = Double.parseDouble(slider.getAttribute(SLIDER_MAX));
                sliderInfoList.add(new SliderInfo(sliderName, sliderMin, sliderMax));
            }
        }
        return sliderInfoList;
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