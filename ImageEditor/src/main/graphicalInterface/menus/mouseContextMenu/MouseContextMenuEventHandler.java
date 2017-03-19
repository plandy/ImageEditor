package main.graphicalInterface.menus.mouseContextMenu;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.graphicalInterface.image.ImageCanvas;

public class MouseContextMenuEventHandler implements EventHandler<MouseEvent> {
	
	private final MouseContextMenu contextMenu;
	private final ImageCanvas imageCanvas;
	
	public MouseContextMenuEventHandler( ImageCanvas p_canvas ) {
		super();
		
		imageCanvas = p_canvas;
		contextMenu = new MouseContextMenu( imageCanvas );
	}

	@Override
	public void handle(MouseEvent event) {
		
		if ( event.getButton() == MouseButton.SECONDARY ) {
			contextMenu.show( imageCanvas, event.getScreenX(), event.getScreenY() );	
		}
	}

}
