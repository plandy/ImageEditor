package graphicalInterface.tools;

import graphicalInterface.image.ImageTab;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

public class LassoSelectorTool extends AbstractToolButton {
	
	private final ImageTab imageTab;
	
	public LassoSelectorTool( ImageTab p_imageTab, ToggleGroup p_toggleGroup ) {
		super( "Lasso Selector", p_toggleGroup );
		
		imageTab = p_imageTab;
	}

	@Override
	protected void onSelectedAction() {
		//imageTab.setActiveTool( toolHandler );
		boolean isSelected = super.isSelected();
	}

	@Override
	protected void onDeselectedAction() {
		boolean isSelected = super.isSelected();
	}
	
	private final EventHandler<MouseEvent> toolHandler = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent event) {
			
			if ( MouseEvent.MOUSE_DRAGGED.equals(event.getEventType()) ) {
				
				//gc.fillRect(event.getX(), event.getY(), 5.0, 5.0);
			}
			
		}
		
	};
	
}
