package gameAuthoring.JSON;

import java.io.File;
import org.w3c.dom.Node;

public class SingletonJSONWriter {
    
    public static SingletonJSONWriter myInstance;
    
    //Prevent multiple instances.
    private SingletonJSONWriter() {}
    
    public static SingletonJSONWriter getInstance() {
        if(myInstance == null){
            myInstance = new SingletonJSONWriter();
        }
        return myInstance;
    }
    
    /**
     * Writes JSON to file. Using node right now bc i dont know how to import json.
     */
    public void writeJSON(Node n, File file){
        
    }
}
