package main.graphicalInterface.menus.mouseContextMenu;

import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.Clipboard;
import main.graphicalInterface.image.ImageCanvas;
import main.graphicalInterface.menus.mouseContextMenu.mouseContextMenuItems.ContextAffixPastedSelectionItem;
import main.graphicalInterface.menus.mouseContextMenu.mouseContextMenuItems.ContextPasteItem;

public class MouseContextMenu extends ContextMenu {

	private ContextPasteItem contextPasteItem = new ContextPasteItem();
	private ContextAffixPastedSelectionItem contextAffixPastedSelectionItem = new ContextAffixPastedSelectionItem();
	
	public MouseContextMenu( ImageCanvas p_canvas ) {
		super();

		super.getItems().add( contextPasteItem );
		super.getItems().add( contextAffixPastedSelectionItem );

		p_canvas.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				if ( MouseButton.SECONDARY.equals(event.getButton()) ) {
					if ( MouseEvent.MOUSE_CLICKED.equals(event.getEventType()) ) {
						Clipboard clipboard = Clipboard.INSTANCE;
						Image clipboardImage = clipboard.loadFromClipboard();
						if ( clipboardImage != null ) {
							contextPasteItem.setVisible(true);
						} else {
							contextPasteItem.setVisible(false);
						}

						show( p_canvas, event.getScreenX(), event.getScreenY() );
					}
				}

			}

		});
	}

}
