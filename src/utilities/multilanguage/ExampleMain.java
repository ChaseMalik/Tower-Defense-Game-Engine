package utilities.multilanguage;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


/**
 * An example class to show how the load style utility works.
 * Disclaimer: The code here for the GUI is poorly written (everything is done in this class in
 * code), and is merely to demonstrate the function calls of the load style utility.
 * 
 * @author Jonathan Tseng
 *
 */
public class ExampleMain extends Application {

    @Override
    public void start (Stage primaryStage) {
        try {
            primaryStage.setTitle("MultiLanguage Utility Example Application");
            BorderPane borderPane = new BorderPane();

            // Initializes MultiLanguageUtility and sets the languages it holds
            MultiLanguageUtility util = MultiLanguageUtility.getInstance();
            util.initLanguages("multilanguage.resources.Chinese.properties",
                               "multilanguage.resources.English.properties");

            // Creates a selector for choosing language and sets it such that when the dropdown
            // selection changes it makes a call to setLanguage for the utility
            ComboBox<String> languageSelector = new ComboBox<>();
            languageSelector.itemsProperty().bind(util.getSupportedLanguages());
            languageSelector.getSelectionModel().select(util.getCurrentLanguage());
            languageSelector.getSelectionModel().selectedItemProperty()
                    .addListener(new ChangeListener<String>() {
                        @Override
                        public void changed (ObservableValue<? extends String> arg0,
                                             String oldVal,
                                             String newVal) {
                            try {
                                util.setLanguage(newVal);
                            }
                            catch (Exception e) {
                                System.out.println(e.toString());
                            }
                        }
                    });
            BorderPane.setAlignment(languageSelector, Pos.CENTER);
            borderPane.setTop(languageSelector);

            // Example: Creates a button and attaches a single string property
            Button b = new Button();
            b.textProperty().bind(util.getStringProperty("Button"));
            BorderPane.setAlignment(b, Pos.CENTER);
            borderPane.setBottom(b);

            // Example: Creates a dropdown and attaches a string list property
            ComboBox<String> fruits = new ComboBox<>();
            fruits.itemsProperty().bind(util.getStringListProperty("Fruits"));
            fruits.promptTextProperty().bind(util.getStringProperty("Fruit"));
            BorderPane.setAlignment(fruits, Pos.CENTER);
            borderPane.setCenter(fruits);

            // Creates the scene and shows it
            Scene scene = new Scene(borderPane, 600, 300);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main (String[] args) {
        launch(args);
    }
}
