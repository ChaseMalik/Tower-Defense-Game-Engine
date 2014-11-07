package gameAuthoring.data;

import org.w3c.dom.Node;

public abstract class Data {
    
    //We will likely use JSON so Node will change to JSONObject
    public abstract Node getJsonRepresentation();
}
