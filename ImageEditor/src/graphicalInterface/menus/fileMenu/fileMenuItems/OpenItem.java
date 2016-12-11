package graphicalInterface.menus.fileMenu.fileMenuItems;

import applicationConstants.StringConstants;
import graphicalInterface.MainWindow;
import javafx.scene.control.MenuItem;

public class OpenItem extends MenuItem {
	
	public OpenItem ( MainWindow p_parentWindow ) {
		super( StringConstants.FILELOAD_TITLE );
		
		this.setOnAction( e -> {
			p_parentWindow.openFileAction();
		});
	}
}
