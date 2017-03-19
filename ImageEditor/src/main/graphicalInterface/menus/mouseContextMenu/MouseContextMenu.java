package main.graphicalInterface.menus.mouseContextMenu;

import javafx.scene.control.ContextMenu;
import main.graphicalInterface.image.ImageCanvas;
import main.graphicalInterface.menus.mouseContextMenu.mouseContextMenuItems.ContextCopyItem;

public class MouseContextMenu extends ContextMenu {
	
	public MouseContextMenu( ImageCanvas p_canvas ) {
		super();
		
		super.getItems().add( new ContextCopyItem() );
	}

}
