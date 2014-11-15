package utilities.XMLParsing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javafx.geometry.Dimension2D;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Test;
import org.xml.sax.SAXException;

public class XMLParserTest {
    
    @Test
    public void XMLParsingTest() throws FileNotFoundException, ParserConfigurationException, 
    SAXException, IOException {
        File testFile = new File("./src/Utilities/XMLParsing/test.xml");
        XMLParser parser = new XMLParser(testFile);
        
        List<String> movementsList = parser.getValuesFromTag("movement");
        assertEquals(3, movementsList.size());
        String[] movementVals = new String[]{"FastMovement", "SlowMovement", "NoMovement"};
        for(int i = 0; i < movementsList.size(); i++){
            assertEquals(movementVals[i], movementsList.get(i));
        }        
        
        List<String> attackList = parser.getValuesFromTag("attack");
        assertEquals(2, attackList.size());
        String[] attackVals = new String[]{"BigAttack", "SmallAttack"};
        for(int i = 0; i < attackList.size(); i++){
            assertEquals(attackVals[i], attackList.get(i));
        } 
        
        List<Integer> integerValuesList = parser.getIntegerValuesFromTag("integerValues");
        assertEquals(2, integerValuesList.size());
        int[] integers = new int[]{600, 400};
        for(int i = 0; i < integerValuesList.size(); i++){
            assertTrue(integers[i]==integerValuesList.get(i));
        } 
        
        
        List<Double> doubleValuesList = parser.getDoubleValuesFromTag("doubleValues");
        assertEquals(2, doubleValuesList.size());
        double[] doubles = new double[]{0.6, 0.4};
        for(int i = 0; i < doubleValuesList.size(); i++){
            assertTrue(doubles[i]==doubleValuesList.get(i));
        } 
        
        Dimension2D dimension = parser.getDimension("dimensions");
        assertTrue(Math.abs(dimension.getWidth()-600)<0.0001);
        assertTrue(Math.abs(dimension.getHeight()-400)<0.0001);
    }
}
