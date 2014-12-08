package gamePlayer.guiItems.voogaMenuBar;

import gamePlayer.guiItems.GuiItem;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import gamePlayer.mainClasses.guiBuilder.GuiText;
import java.io.File;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import utilities.XMLParsing.XMLParser;
import utilities.reflection.Reflection;

/**
 * This GuiItem initializes and manages the menu bar
 * 
 * @author allankiplagat
 *
 */
public class VoogaMenuBar implements GuiItem {
	private XMLParser myParser;
	private MenuBar myMenuBar;
	private Dimension2D myMenuBarSize;

	@Override
	public void initialize(Dimension2D containerSize) {
		String propertiesPath = GuiConstants.GUI_ELEMENT_PROPERTIES_PATH
				+ myPropertiesPath + this.getClass().getSimpleName() + ".XML";
		myParser = new XMLParser(new File(propertiesPath));

		myMenuBar = new MenuBar();
		Dimension2D sizeRatio = myParser.getDimension("SizeRatio");
		myMenuBarSize = new Dimension2D(sizeRatio.getWidth()
				* containerSize.getWidth(), sizeRatio.getHeight()
				* containerSize.getHeight());

		myMenuBar.setMinSize(myMenuBarSize.getWidth(),
				myMenuBarSize.getHeight());
		myMenuBar.setPrefSize(myMenuBarSize.getWidth(),
				myMenuBarSize.getHeight());
		myMenuBar.getStyleClass().add("VoogaMenuBar");
		initializeMenus();
	}

	private void initializeMenus() {
		Menu fileMenu = createMenu("FileMenu");
		fileMenu.textProperty().bind(GuiConstants.MULTILANGUAGE.getStringProperty(GuiText.FILE));;
		myMenuBar.getMenus().add(fileMenu);

	}

	private Menu createMenu(String menuName) {
		Menu menu = new Menu();

		List<String> myItems = myParser.getValuesFromTag(menuName);
		for (String item : myItems) {
			VoogaMenuItem menuItem = (VoogaMenuItem) Reflection
					.createInstance(item);
			menuItem.initialize();
			menu.getItems().add(menuItem);
		}
		return menu;
	}
	
	@Override
	public Node getNode() {
		return myMenuBar;
	}
}
