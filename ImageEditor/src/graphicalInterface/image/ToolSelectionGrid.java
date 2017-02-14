package graphicalInterface.image;

import graphicalInterface.tools.LassoSelectorTool;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

public class ToolSelectionGrid extends GridPane {
	
	ToggleGroup toggleGroup = new ToggleGroup();
	
	public ToolSelectionGrid() {	
		super.add( new LassoSelectorTool(toggleGroup), 0, 0 );
	}
	
}
