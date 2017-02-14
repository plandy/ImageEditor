package graphicalInterface.tools;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public abstract class AbstractToolButton extends ToggleButton {
	
	public AbstractToolButton( String p_title, ToggleGroup p_toggleGroup ) {
		super( p_title );
		super.setToggleGroup( p_toggleGroup );
	}
	
	{
		super.selectedProperty().addListener(e -> {
			if ( super.isSelected() == true ) {
				onSelectedAction();
			} else if ( super.isSelected() == false ) {
				onDeselectedAction();
			}
		});
	}
	
	protected abstract void onSelectedAction();
	
	protected abstract void onDeselectedAction();

}
