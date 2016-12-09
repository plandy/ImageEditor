package graphicalInterface.menus.fileMenu;

import applicationConstants.StringConstants;
import graphicalInterface.menus.fileMenu.fileMenuItems.ExitItem;
import graphicalInterface.menus.fileMenu.fileMenuItems.OpenItem;
import graphicalInterface.menus.fileMenu.fileMenuItems.SaveItem;
import javafx.scene.control.Menu;

public class FileMenu extends Menu {
	
	public FileMenu() {
		super( StringConstants.FILE_TITLE );
		
		this.getItems().add( new OpenItem() );
		this.getItems().add( new SaveItem() );
		this.getItems().add( new ExitItem() );
	}
	
}
