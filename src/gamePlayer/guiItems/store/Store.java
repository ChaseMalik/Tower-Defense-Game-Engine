package gamePlayer.guiItems.store;

import gamePlayer.guiItems.GuiItem;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TitledPane;

public class Store implements GuiItem {
	
	private Accordion myAccordion;
	private ScrollPane myScrollPane;

	@Override
	public void initialize(Dimension2D containerSize) {
		myAccordion = new Accordion();
		buildTitledPanes(containerSize);
		myScrollPane = new ScrollPane();
		myScrollPane.setContent(myAccordion);
		myScrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		myScrollPane.setMinHeight(containerSize.getHeight());
		myScrollPane.setMaxHeight(containerSize.getHeight());
	}

	@Override
	public Node getNode() {
		return myScrollPane;
	}
	
	private void buildTitledPanes(Dimension2D size) {
		TitledPane towerPane = new TitledPane();
		towerPane.setMinSize(size.getWidth(), size.getHeight());
		towerPane.setText("Towers");
		myAccordion.getPanes().add(towerPane);
	}
	
}
