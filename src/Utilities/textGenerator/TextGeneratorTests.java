package Utilities.textGenerator;

import static org.junit.Assert.*;
import org.junit.Test;

public class TextGeneratorTests {
    //Set the path for the text generator properties file to be used. 
    //Write your own properties file since it is implementation-specific
    private static final String propertiesPath =  "./src/Utilities/textGenerator/SampleGeneratorProperties.XML";
    
    private TextGenerator generator;
    private void resetGenerator() {
        generator = new TextGenerator(propertiesPath);
    }
    
    @Test
    public void testLanguageSupport() {
        resetGenerator();
        assertTrue(generator.languageSupported("English"));
        assertTrue(generator.languageSupported("French"));
    }
    
    @Test
    public void testEnglish() {
        resetGenerator();
        generator.setLanguage("English");
        assertTrue("Load Game".equals(generator.get(Text.LOAD_GAME)));
        assertTrue("VOOGASALAD".equals(generator.get(Text.VOOGASALAD)));
    }
    
    @Test
    public void testFrench() {
        resetGenerator();
        assertFalse(generator.setLanguage("Math"));
        assertTrue(generator.setLanguage("French"));
        generator.setLanguage("French");
        //assertTrue("Jeu De Charge".equals(generator.get(Text.LOAD_GAME)));
        System.out.println(generator.get(Text.VOOGASALAD));
        //assertTrue("VOOGASALAD FRANCAIS".equals(generator.get(Text.VOOGASALAD)));
        
    }
}
