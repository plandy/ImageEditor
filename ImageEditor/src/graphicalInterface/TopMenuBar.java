package graphicalInterface;

import graphicalInterface.menus.fileMenu.FileMenu;
import javafx.scene.control.MenuBar;

public class TopMenuBar extends MenuBar {
	public TopMenuBar( MainWindow p_parentWindow ) {
		this.getMenus().add( new FileMenu(p_parentWindow) );
	}
}