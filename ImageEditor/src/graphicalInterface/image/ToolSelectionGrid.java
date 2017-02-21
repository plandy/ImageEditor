package graphicalInterface.image;

import graphicalInterface.tools.DefaultTool;
import graphicalInterface.tools.LassoSelectorTool;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

public class ToolSelectionGrid extends GridPane {
	
	private final ImageTab imageTab;
	private final ToggleGroup toggleGroup = new ToggleGroup();
	
	public ToolSelectionGrid( ImageTab p_imageTab ) {
		
		imageTab = p_imageTab;
		
		super.add( new DefaultTool(imageTab, toggleGroup), 0, 0 );
		super.add( new LassoSelectorTool(imageTab, toggleGroup), 0, 1 );
	}
	
	public void deselectCurrentTool() {
		toggleGroup.selectToggle( null );
	}
	
}
