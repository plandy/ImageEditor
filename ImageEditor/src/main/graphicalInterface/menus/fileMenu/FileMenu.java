package main.graphicalInterface.menus.fileMenu;

import javafx.scene.control.Menu;
import main.applicationConstants.StringConstants;
import main.graphicalInterface.MainWindow;
import main.graphicalInterface.menus.fileMenu.fileMenuItems.ExitItem;
import main.graphicalInterface.menus.fileMenu.fileMenuItems.OpenItem;
import main.graphicalInterface.menus.fileMenu.fileMenuItems.SaveItem;

public class FileMenu extends Menu {
	
	public FileMenu( MainWindow p_parentWindow ) {
		super( StringConstants.FILE_TITLE );
		
		this.getItems().add( new OpenItem(p_parentWindow) );
		this.getItems().add( new SaveItem(p_parentWindow) );
		this.getItems().add( new ExitItem() );
	}
	
}
