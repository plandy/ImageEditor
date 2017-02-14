package graphicalInterface.menus.selectorMenu.selectorMenuItems;

import applicationConstants.StringConstants;
import graphicalInterface.MainWindow;
import javafx.scene.control.MenuItem;

public class LassoSelectorItem extends MenuItem {
	
	public LassoSelectorItem( MainWindow p_parentWindow ) {
		super( StringConstants.LASSOSELECTOR_TITLE);
		
		super.setOnAction( e -> {
			lassoSelectorAction();
		});
	}
	
	private void lassoSelectorAction() {
		
	}

}
