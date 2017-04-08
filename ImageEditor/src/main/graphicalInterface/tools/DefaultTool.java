package main.graphicalInterface.tools;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.graphicalInterface.image.ImageCanvas;
import main.graphicalInterface.image.ImageTab;

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
		currentEventHandler = null;
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
			if ( MouseButton.PRIMARY.equals(event.getButton()) ) {
				if ( MouseEvent.MOUSE_DRAGGED.equals(event.getEventType()) ) {
					
					gc.fillRect(event.getX(), event.getY(), 5.0, 5.0);
				}
			}			
		}
		
	}
}
