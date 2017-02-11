package graphicalInterface.image;

import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ImageTabPane extends TabPane {
	
	private static final double TAB_THUMBNAIL_HEIGHT = 75;
	private static final double TAB_THUMBNAIL_WIDTH = 75;
	
	public ImageTabPane() {
		super();
		
		this.setTabMinHeight( TAB_THUMBNAIL_HEIGHT );
		this.setTabMaxHeight( TAB_THUMBNAIL_HEIGHT );
		this.setTabMinWidth( TAB_THUMBNAIL_WIDTH );
		this.setTabMaxWidth( TAB_THUMBNAIL_WIDTH );
		
		VBox.setVgrow( this, Priority.ALWAYS );
	}
	
	public void addTab( Image p_image ) {
		ImageTab newTab = new ImageTab( p_image );
		this.getTabs().add( newTab );
		newTab.setThumbnailDimensions( TAB_THUMBNAIL_HEIGHT, TAB_THUMBNAIL_WIDTH );
		newTab.doBaseLayout();
		super.getSelectionModel().select( newTab );
	}
	
	public ImageTab getFocusedTab() {
		return (ImageTab)super.getSelectionModel().getSelectedItem();
	}
	
}
