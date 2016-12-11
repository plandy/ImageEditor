package graphicalInterface.image;

import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;

public class ImageTab extends Tab {
	
	//private final ObservableList<ImageCanvas> imageLayers;
	//private final ScrollPane scrollPane;
	
	public ImageTab( Image p_image ) {
		this.setContent( new ImageCanvas(p_image) );
	}
	
}
