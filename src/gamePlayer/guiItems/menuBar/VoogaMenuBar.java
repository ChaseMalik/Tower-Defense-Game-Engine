package gamePlayer.guiItems.menuBar;

import gamePlayer.guiItemListenerInterfaces.VoogaMenuBarListenerInterface;
import gamePlayer.guiItems.GuiItemInterface;
import gamePlayer.guiItems.menuBar.menus.LoadMenu;
import gamePlayer.mainClasses.GuiConstants;
import gamePlayer.textGeneration.Text;
import gamePlayer.textGeneration.TextGenerator;
import gamePlayer.utilityFunctions.FileLoader;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import Utilities.XMLParsing.XMLParser;
import Utilities.XMLParsing.XMLParserInstantiator;

public class VoogaMenuBar extends MenuBar implements GuiItemInterface {
    private VoogaMenuBarListenerInterface myListener;
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
        GuiConstants.GUI_LISTENER.readLoadedFile(FileLoader.getInstance()
                                                 .load(GuiConstants.GUI_LISTENER.getStage()));
    }

    @Override
    public Node getNode () {
        return this;
    }
}
