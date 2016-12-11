package graphicalInterface.menus.fileMenu;

import applicationConstants.StringConstants;
import graphicalInterface.MainWindow;
import graphicalInterface.menus.fileMenu.fileMenuItems.ExitItem;
import graphicalInterface.menus.fileMenu.fileMenuItems.OpenItem;
import graphicalInterface.menus.fileMenu.fileMenuItems.SaveItem;
import javafx.scene.control.Menu;

public class FileMenu extends Menu {
	
	public FileMenu( MainWindow p_parentWindow ) {
		super( StringConstants.FILE_TITLE );
		
		this.getItems().add( new OpenItem(p_parentWindow) );
		this.getItems().add( new SaveItem(p_parentWindow) );
		this.getItems().add( new ExitItem() );
	}
	
}
