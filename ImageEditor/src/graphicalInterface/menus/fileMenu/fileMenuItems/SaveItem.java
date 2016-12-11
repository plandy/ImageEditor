package graphicalInterface.menus.fileMenu.fileMenuItems;

import applicationConstants.StringConstants;
import graphicalInterface.MainWindow;
import javafx.scene.control.MenuItem;

public class SaveItem extends MenuItem {
	
	public SaveItem( MainWindow p_parentWindow ) {
		super( StringConstants.FILESAVE_TITLE );
		
		this.setOnAction( e -> {
			p_parentWindow.saveFileAction();
		});
	}
}
