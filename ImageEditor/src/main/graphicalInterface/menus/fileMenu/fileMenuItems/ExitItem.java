package main.graphicalInterface.menus.fileMenu.fileMenuItems;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import main.applicationConstants.StringConstants;

public class ExitItem extends MenuItem {
	
	public ExitItem() {
		super( StringConstants.FILEEXIT_TITLE );
		this.setOnAction( new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Platform.exit();
			}
			
		});
	}

}