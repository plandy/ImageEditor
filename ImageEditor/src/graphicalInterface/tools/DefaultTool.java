package graphicalInterface.tools;

import graphicalInterface.image.ImageCanvas;
import graphicalInterface.image.ImageTab;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

public class DefaultTool extends AbstractToolButton {
	
	private final ImageTab imageTab;
	private EventHandler<MouseEvent> currentEventHandler;
	
	public DefaultTool( ImageTab p_imageTab, ToggleGroup p_toggleGroup ) {
		super( "Default Tool", p_toggleGroup );
		
		imageTab = p_imageTab;
	}

	@Override
	protected void onSelectedAction() {
		ImageCanvas selectedImageLayer = imageTab.getSelectedLayer();
		currentEventHandler = new Tool( selectedImageLayer );
		selectedImageLayer.addEventHandler(MouseEvent.ANY, currentEventHandler );
	}

	@Override
	protected void onDeselectedAction() {
		ImageCanvas selectedImageLayer = imageTab.getSelectedLayer();
		selectedImageLayer.removeEventHandler( MouseEvent.ANY, currentEventHandler );
	}
	
	private class Tool implements EventHandler<MouseEvent>{
		
		private final ImageCanvas imageLayer;
		private final GraphicsContext gc;
		
		public Tool( ImageCanvas p_imageLayer ) {
			imageLayer = p_imageLayer;
			gc = imageLayer.getGraphicsContext2D();
		}

		@Override
		public void handle(MouseEvent event) {
			
			if ( MouseEvent.MOUSE_DRAGGED.equals(event.getEventType()) ) {
				
				gc.fillRect(event.getX(), event.getY(), 5.0, 5.0);
			}
			
		}
		
	};
}
