package graphicalInterface.menus.selectorMenu;

import applicationConstants.StringConstants;
import graphicalInterface.MainWindow;
import graphicalInterface.menus.selectorMenu.selectorMenuItems.LassoSelectorItem;
import javafx.scene.control.Menu;

public class SelectorMenu extends Menu {
	
	public SelectorMenu( MainWindow p_parentWindow ) {
		super( StringConstants.SELECTOR_TITLE );
		
		this.getItems().add( new LassoSelectorItem(p_parentWindow) );
	}
}
