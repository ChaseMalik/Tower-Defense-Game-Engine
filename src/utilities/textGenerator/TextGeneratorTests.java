package utilities.textGenerator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class TextGeneratorTests {
    //Set the path for the text generator properties file to be used. 
    //Write your own properties file since it is implementation-specific
    private static final String propertiesPath =  "./src/utilities/textGenerator/SampleGeneratorProperties.XML";
    
    private static TextGenerator generator;
    private void resetGenerator() {
        generator = new TextGenerator(propertiesPath);
    }
    
    @Test
    public void testLanguageSupport() {
        resetGenerator();
        assertTrue(generator.languageSupported("en"));
        assertTrue(generator.languageSupported("fr"));
        assertFalse(generator.setLanguage("NotALanguage"));
    }
    
    @Test
    public void testEnglish() {
        resetGenerator();
        generator.setLanguage("en");
        assertTrue("Load Game".equals(generator.get(Text.LOAD_GAME)));
        assertTrue("VOOGASALAD".equals(generator.get(Text.VOOGASALAD)));
    }
    
    @Test
    public void testFrench() {
        resetGenerator();
        generator.setLanguage("fr");
        assertTrue("Jeu de charge".equals(generator.get(Text.LOAD_GAME)));
        assertTrue("FRENCH VOOGASALAD".equals(generator.get(Text.VOOGASALAD)));   
    }
}
