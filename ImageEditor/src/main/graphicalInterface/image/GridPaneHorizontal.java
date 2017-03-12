package main.graphicalInterface.image;

import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class GridPaneHorizontal extends GridPane {
	
	private int numChildren = 0;
	
	public GridPaneHorizontal() {
		super();
	}
	
	public void addNode( Node p_node, double p_widthConstraint ) {
		super.add( p_node, numChildren, 0 );
		ColumnConstraints colConstraint = new ColumnConstraints();
		colConstraint.setPercentWidth( p_widthConstraint );
		super.getColumnConstraints().add( numChildren, colConstraint );
		numChildren++;
	}

}
