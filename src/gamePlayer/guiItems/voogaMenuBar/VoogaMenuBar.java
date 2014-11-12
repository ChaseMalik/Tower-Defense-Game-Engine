package gamePlayer.guiItems.voogaMenuBar;

import gamePlayer.guiFeatures.FileLoader;
import gamePlayer.guiItems.GuiItem;
import gamePlayer.guiItems.voogaMenuBar.menus.LoadMenu;
import gamePlayer.guiItemsListeners.VoogaMenuBarListener;
import gamePlayer.mainClasses.guiBuilder.GuiBuilderConstants;
import gamePlayer.textGenerator.Text;
import gamePlayer.textGenerator.TextGenerator;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import Utilities.XMLParsing.XMLParser;
import Utilities.XMLParsing.XMLParserInstantiator;

public class VoogaMenuBar extends MenuBar implements GuiItem {
    private VoogaMenuBarListener myListener;
    private XMLParser myParser;    

    @Override
    public void initialize (List<Double> containerSize) {
        myParser = XMLParserInstantiator.
                getInstance(new File(myPropertiesPath+this.getClass().getSimpleName()+".XML"));

        //set item size
        List<Double> sizeRatio = myParser.getDoubleValuesFromTag("SizeRatio");
        List<Double> mySize = Arrays.asList(containerSize.get(0)*sizeRatio.get(0),containerSize.get(1)*sizeRatio.get(1));
        this.setPrefSize(mySize.get(0),mySize.get(1));

        initializeMenus();
    }

    private void initializeMenus() {
        LoadMenu loadMenu = new LoadMenu();
        loadMenu.setText(TextGenerator.getInstance().get(Text.LOAD_GAME));
        loadMenu.setOnAction(event->load());
        this.getMenus().add(loadMenu);
    }

    private void load() {
        GuiBuilderConstants.CURRENT_GUI_CONTROLLER.readLoadedFile(FileLoader.getInstance()
                                                 .load(GuiBuilderConstants.CURRENT_GUI_CONTROLLER.getStage()));
    }

    @Override
    public Node getNode () {
        return this;
    }
}
