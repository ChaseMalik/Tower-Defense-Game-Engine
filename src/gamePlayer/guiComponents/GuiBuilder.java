package gamePlayer.guiComponents;

public class GuiBuilder {
    static GuiBuilder myReference = null;
    
    private GuiBuilder() {
        
    }
    
    public static GuiBuilder getInstance() {
        if (myReference == null) {
            myReference = new GuiBuilder();
        }
        return myReference;
    }
}
