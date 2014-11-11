package Utilities.XMLParsing;

import static org.junit.Assert.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
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
    }
}
