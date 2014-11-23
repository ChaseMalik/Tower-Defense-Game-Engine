package utilities.XMLParsing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.util.List;
import javafx.geometry.Dimension2D;
import org.junit.Before;
import org.junit.Test;

public class XMLParserTest {
    
    private static final String TEST_FILE_1 = "./src/utilities/XMLParsing/testXMLFiles/test1.xml";
    private static final String TEST_FILE_2 = "./src/utilities/XMLParsing/testXMLFiles/test2.xml";
    private XMLParser myParameterExtractionXMLParser;
    private XMLParser myBehaviorExtractionXMLParser;
    
    @Before
    public void setup() {
        File parameterExtractionTestFile = new File(TEST_FILE_1);
        myParameterExtractionXMLParser = 
                new XMLParser(parameterExtractionTestFile);
        
        File actorBehaviorExtractionTestFile = new File(TEST_FILE_2);
        myBehaviorExtractionXMLParser = 
                new XMLParser(actorBehaviorExtractionTestFile);
    }
    
    @Test
    public void XMLParsingBehaviorOptionsTest() {
        List<String> movementsList = myBehaviorExtractionXMLParser.getValuesFromTag("movement");
        assertEquals(3, movementsList.size());
        String[] movementVals = new String[]{"FastMovement", "SlowMovement", "NoMovement"};
        for(int i = 0; i < movementsList.size(); i++){
            assertEquals(movementVals[i], movementsList.get(i));
        }        
        
        List<String> attackList = myBehaviorExtractionXMLParser.getValuesFromTag("attack");
        assertEquals(2, attackList.size());
        String[] attackVals = new String[]{"BigAttack", "SmallAttack"};
        for(int i = 0; i < attackList.size(); i++){
            assertEquals(attackVals[i], attackList.get(i));
        } 
    }
    
    @Test
    public void XMLParsingAllBehaviorTypesTest() {
        List<String> allBehaviorTypes = myBehaviorExtractionXMLParser.getAllBehaviorTypes();
        assertEquals(2, allBehaviorTypes.size());
        String[] expectedBehaviorTypes = new String[]{"movement", "attack"};
        for(int i = 0; i < allBehaviorTypes.size(); i++){
            assertEquals(expectedBehaviorTypes[i], allBehaviorTypes.get(i));
        } 
    }
    
    @Test
    public void XMLParsingIntegerExtractionTest() {       
        List<Integer> integerValuesList = myParameterExtractionXMLParser.getIntegerValuesFromTag("integerValues");
        assertEquals(2, integerValuesList.size());
        int[] integers = new int[]{600, 400};
        for(int i = 0; i < integerValuesList.size(); i++){
            assertTrue(integers[i]==integerValuesList.get(i));
        }         
    }
        
    @Test
    public void XMLParsingDoubleExtractionTest() {       
        List<Double> doubleValuesList = myParameterExtractionXMLParser.getDoubleValuesFromTag("doubleValues");
        assertEquals(2, doubleValuesList.size());
        double[] doubles = new double[]{0.6, 0.4};
        for(int i = 0; i < doubleValuesList.size(); i++){
            assertTrue(doubles[i]==doubleValuesList.get(i));
        } 
    }
    
    @Test
    public void XMLParsingDimensionExtractionTest() {       
        Dimension2D dimension = myParameterExtractionXMLParser.getDimension("dimensions");
        assertTrue(Math.abs(dimension.getWidth()-600)<0.0001);
        assertTrue(Math.abs(dimension.getHeight()-400)<0.0001);
    }
}