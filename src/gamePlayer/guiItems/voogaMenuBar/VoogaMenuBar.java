package gamePlayer.guiItems.voogaMenuBar;

import gamePlayer.guiItems.GuiItem;
import gamePlayer.guiItems.voogaMenuBar.menuItems.VoogaMenuItem;
import gamePlayer.mainClasses.guiBuilder.GuiBuilderConstants;
import gamePlayer.mainClasses.guiBuilder.GuiText;
import java.io.File;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import Utilities.XMLParsing.XMLParser;
import Utilities.XMLParsing.XMLParserInstantiator;
import Utilities.reflection.Reflection;

public class VoogaMenuBar implements GuiItem {

    private XMLParser myParser;
    private MenuBar myMenuBar;

    @Override
    public void initialize (List<Double> containerSize) {
        myParser = XMLParserInstantiator.getInstance(new File(myPropertiesPath+this.getClass().getSimpleName()+".XML"));

        myMenuBar = new MenuBar();
        List<Double> sizeRatio = myParser.getDoubleValuesFromTag("SizeRatio");
        myMenuBar.setPrefSize(sizeRatio.get(0)*containerSize.get(0), sizeRatio.get(1)*containerSize.get(1));
        initializeMenus();
    }

    private void initializeMenus() {
        Menu fileMenu = createMenu("FileMenu");
        fileMenu.setText(GuiBuilderConstants.TEXT_GEN.get(GuiText.FILE));
        myMenuBar.getMenus().add(fileMenu);
       
    }

    private Menu createMenu(String menuName) {
        Menu menu = new Menu();
        
        List<String> myItems = myParser.getValuesFromTag(menuName);
        for (String item:myItems) {
            VoogaMenuItem menuItem = (VoogaMenuItem) Reflection.createInstance(item);
            menuItem.initialize();
            menu.getItems().add(menuItem);
        }
        return menu;
    }

    @Override
    public Node getNode () {
        return myMenuBar;
    }
}
