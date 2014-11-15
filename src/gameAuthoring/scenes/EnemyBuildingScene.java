package gameAuthoring.scenes;



import java.io.File;

import utilities.XMLParsing.XMLParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;



public class EnemyBuildingScene extends BuildingScene {


	//private static final String MOVE_BEHAVIOR = "movement";
	private static final String TITLE = "Enemy Building";
	private static final String ENEMY_FILE = "./src/gameAuthoring/Resources/EnemyBehaviors.xml";
	private static final String SPAWN = "spawn";
	private static final String ATTACK = "attack";
	private static final String DEFEND = "defend";
	private static final String MOVEMENT = "movement";
	private static final String TERMINATE = "terminate";
	
	private XMLParser myXMLParser;
	private BorderPane myPane;
	private ListView<String> list;
	
	
	private ComboBox<String> mySpawn;
	private ComboBox<String> myAttack;
	private ComboBox<String> myDefend;
	private ComboBox<String> myMovement;
	private ComboBox<String> myTerminate;

	public EnemyBuildingScene (BorderPane root) {
		super(root, TITLE);
		myPane = root;
		myXMLParser = new XMLParser(new File(ENEMY_FILE));
		createEnemyListView();
		myPane.setLeft(list);
		createComboBoxes();
		createCenterDisplay();

	}
	
	


	private void createComboBoxes() {
		mySpawn = createComboBox(SPAWN);
		myAttack = createComboBox(ATTACK);
		myDefend = createComboBox(DEFEND);
		myMovement = createComboBox(MOVEMENT);
		myTerminate = createComboBox(TERMINATE);		
	}




	private void createCenterDisplay() {
		VBox centerVBox = new VBox();
		centerVBox.setPadding(new Insets(10));
//		centerVBox.getChildren().addAll(nameSetup(), createRectangle(),createAttackSetup(), createMovementSetup(), createSaveButton());
//		centerVBox.getChildren().addAll(nameSetup(), createRectangle(),createSetup("spawn", mySpawn), createSetup("attack", myAttack),
//				createSetup("defend",myDefend),createSetup("movement",myMovement),createSetup("terminate", myTerminate),createSaveButton());
		
		centerVBox.getChildren().addAll(nameSetup(), createRectangle(), 
				createVBoxWithComboBoxAndTitle(mySpawn,SPAWN), 				
				createVBoxWithComboBoxAndTitle(myAttack, ATTACK),
				createVBoxWithComboBoxAndTitle(myDefend, DEFEND),
				createVBoxWithComboBoxAndTitle(myMovement, MOVEMENT),
				createVBoxWithComboBoxAndTitle(myTerminate, TERMINATE),
				createSaveButton());
		centerVBox.setSpacing(20);		
		myPane.setCenter(centerVBox);
	}

	
	private void createEnemyListView(){
		list = new ListView<String>();
		ObservableList<String> items = FXCollections.observableArrayList(      		
				"Enemy 1", "Enemy 2"
				);
		list.setItems(items);  
		list.setPrefWidth(100);
		//return list;
	}

	private VBox nameSetup(){
		Label nameLabel = new Label("Name:");	
		TextField nameTextField = new TextField();
		VBox nameVBox = new VBox();
		nameVBox.getChildren().addAll(nameLabel, nameTextField);
		nameVBox.setSpacing(10);	
		return nameVBox;
	}
	
	private Rectangle createRectangle(){
		
		Rectangle rect = new Rectangle();
		rect.setHeight(75);
		rect.setWidth(60);			
		return rect;
	}
	
	private ComboBox<String> createComboBox(String comboBoxTitle){	
		//ComboBox<String> CB = new ComboBox<String>();
		ComboBox<String> CB = new ComboBox<String>();
		//CB.setItems((ObservableList<String>) myXMLParser.getValuesFromTag(comboBoxTitle));
		CB.getItems().addAll(myXMLParser.getValuesFromTag(comboBoxTitle));
		//comboBoxSelection = CB;
//		Label label = new Label(comboBoxTitle);
//		VBox movementVBox = new VBox();
//		movementVBox.getChildren().addAll(label, comboBoxSelection);
//		movementVBox.setSpacing(5);
		return CB;
	}
	
	private VBox createVBoxWithComboBoxAndTitle(ComboBox comboBox, String comboBoxTitle){
		VBox VB = new VBox();
		Label titleLabel = new Label(comboBoxTitle);
		VB.getChildren().addAll(titleLabel,comboBox);
		VB.setSpacing(5);
		return VB;
	}

	private Button createSaveButton(){
		Button saveButton = new Button("Save");
		
		saveButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println(mySpawn.getValue());
				System.out.println(myAttack.getValue());
				System.out.println(myDefend.getValue());
				System.out.println(myMovement.getValue());
				System.out.println(myTerminate.getValue());				
			}
			
		});
		
		return saveButton;		
	}
	
	

	

}
