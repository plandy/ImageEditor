package graphicalInterface.tools;

import javafx.scene.control.ToggleGroup;

public class LassoSelectorTool extends AbstractToolButton {
	
	public LassoSelectorTool( ToggleGroup p_toggleGroup ) {
		super( "Lasso Selector", p_toggleGroup );
	}

	@Override
	protected void onSelectedAction() {
		boolean isSelected = super.isSelected();
	}

	@Override
	protected void onDeselectedAction() {
		boolean isSelected = super.isSelected();
	}
	
}
