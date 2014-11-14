package gameAuthoring.scenes;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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


	private static final String TITLE = "Enemy Building";


	private BorderPane myPane;
	private ListView<String> list;

	public EnemyBuildingScene (BorderPane root) {
		super(root, TITLE);
		myPane = root;

		createEnemyListView();
		myPane.setLeft(list);
		VBox centerVBox = new VBox();
		centerVBox.setPadding(new Insets(20));
		centerVBox.getChildren().addAll(nameSetup(), createRectangle(),createAttackSetup(), createMovementSetup(), createSaveButton());
		centerVBox.setSpacing(30);		
		myPane.setCenter(centerVBox);

	}


	private void createEnemyListView(){
		list = new ListView<String>();
		ObservableList<String> items = FXCollections.observableArrayList(      		
				"Enemy1", "Enemy2", "Enemy3"
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
	

	private VBox createAttackSetup(){

		ComboBox attackCB = new ComboBox();
		ObservableList<String> attackOptions = FXCollections.observableArrayList(   			
				"Attack1", "Attack2", "Attack3"   			
				);	
		attackCB.setItems(attackOptions);
		Label attackLabel = new Label("Attack: ");
		VBox attackVBox = new VBox();
		attackVBox.getChildren().addAll(attackLabel, attackCB);
		attackVBox.setSpacing(10);
		
		return attackVBox;	

	}
	
	private VBox createMovementSetup(){
		ComboBox movementCB = new ComboBox();
		ObservableList<String> movementOptions = FXCollections.observableArrayList(
				"Movement1", "Movement2", "Movement3"
				);
		movementCB.setItems(movementOptions);
		Label movementLabel = new Label("Movement:");
		VBox movementVBox = new VBox();
		movementVBox.getChildren().addAll(movementLabel, movementCB);
		movementVBox.setSpacing(10);
		
		return movementVBox;
	}
	
	
	private Button createSaveButton(){
		Button saveButton = new Button("Save");
		return saveButton;		
	}
	
	
	
	private Rectangle createRectangle(){
		
		Rectangle rect = new Rectangle();
		rect.setHeight(150);
		rect.setWidth(120);			
		return rect;
	}
	
	

}
