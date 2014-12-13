package gamePlayer.guiFeatures;

import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class WinStatusProperty extends SimpleDoubleProperty{
	
	public static final double WIN = 1.0;
	public static final double LOSS = -1.0;
	public static final double RUNNING = 0.0;

	public WinStatusProperty() {
		super();
	}

	@Override
	public void bind(ObservableValue<? extends Number> observable) {
		super.bind(observable);
	}

	@Override
	public void unbind() {
		super.unbind();
	}

	@Override
	public boolean isBound() {
		return super.isBound();
	}

	@Override
	public Object getBean() {
		return null;
	}

	@Override
	public String getName() {
		return "";
	}

	@Override
	public void addListener(ChangeListener<? super Number> listener) {
		super.addListener(listener);
	}

	@Override
	public void removeListener(ChangeListener<? super Number> listener) {
		super.removeListener(listener);
	}

	@Override
	public void addListener(InvalidationListener listener) {
		super.addListener(listener);
	}

	@Override
	public void removeListener(InvalidationListener listener) {
		super.removeListener(listener);
	}

	@Override
	public double get() {
		return super.getValue();
	}

	@Override
	public void set(double value) {
		if (value == WIN || value == LOSS)
			super.setValue(value);
	}

}
