package utilities.JavaFXutilities;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class NumericalTextField extends TextField {
    public NumericalTextField(double width) {
        setPrefWidth(width);
        
        this.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                try {
                    Integer.parseInt(newValue);
                } catch (NumberFormatException e) {
                    if(NumericalTextField.this.getText().isEmpty()) {
                        NumericalTextField.this.setText("");
                    }
                    else {
                        NumericalTextField.this.setText(oldValue);
                    }
                }
            }
        });
    }

    public int getNumber () {
        return Integer.parseInt(this.getText());
    }  
}
