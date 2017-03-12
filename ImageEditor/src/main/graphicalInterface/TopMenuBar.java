package main.graphicalInterface;

import javafx.scene.control.MenuBar;
import main.graphicalInterface.menus.fileMenu.FileMenu;

public class TopMenuBar extends MenuBar {
	public TopMenuBar( MainWindow p_parentWindow ) {
		this.getMenus().add( new FileMenu(p_parentWindow) );
	}
}