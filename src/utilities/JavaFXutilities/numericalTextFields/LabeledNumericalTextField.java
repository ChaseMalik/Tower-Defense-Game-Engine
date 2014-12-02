package utilities.JavaFXutilities.numericalTextFields;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class LabeledNumericalTextField extends VBox {
    private static final int SPACING = 10;
    
    private NumericalTextField myTextField;

    public LabeledNumericalTextField(String label, double textFieldWidth) {
        this.setSpacing(SPACING);
        myTextField = new NumericalTextField(textFieldWidth);
        this.getChildren().addAll(new Label(label), myTextField);
    }
    
    public int getNumberEntered() {
        return myTextField.getNumber();
    }

    public boolean isValueEntered () {
        return myTextField.isValueEntered();
    }
    
    public void clearField() {
        myTextField.clear();
    }
}
