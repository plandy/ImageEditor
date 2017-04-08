package main.graphicalInterface.menus.mouseContextMenu.mouseContextMenuItems;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import main.Clipboard;
import main.graphicalInterface.image.ImageCanvas;

public class ContextCopyItem extends MenuItem {
	
	public ContextCopyItem() {
		super( "Copy" );
		
		super.setOnAction( new ContextCopyAction() );
	}
	
	private class ContextCopyAction implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			
			Object object = event.getSource();
			if ( object instanceof MenuItem ) {
				if ( object instanceof ContextCopyItem ) {
					ContextCopyItem copyItem = (ContextCopyItem) object;
					ContextMenu menu = copyItem.getParentPopup();
					Node node = menu.getOwnerNode();
					if ( node instanceof ImageCanvas ) {
						ImageCanvas imageCanvas = (ImageCanvas) node;
						Image selectedImage = imageCanvas.getSelectionImage();
						if ( selectedImage != null ) {
							copy( selectedImage );
						}
					}
				}
			}
			
		}
		
		private void copy( Image p_selectionImage ) {
			Clipboard clipboard = Clipboard.INSTANCE;
			clipboard.storeToClipboard( p_selectionImage );
		}
		
	}

}
