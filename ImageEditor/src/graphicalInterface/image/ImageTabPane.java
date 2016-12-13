package graphicalInterface.image;

import javafx.scene.control.TabPane;

public class ImageTabPane extends TabPane {
	
	private static final double TAB_THUMBNAIL_HEIGHT = 75;
	private static final double TAB_THUMBNAIL_WIDTH = 75;
	
	public ImageTabPane() {
		super();
		
		this.setTabMinHeight( TAB_THUMBNAIL_HEIGHT );
		this.setTabMaxHeight( TAB_THUMBNAIL_HEIGHT );
		this.setTabMinWidth( TAB_THUMBNAIL_WIDTH );
		this.setTabMaxWidth( TAB_THUMBNAIL_WIDTH );
	}
	
	public void addTab( ImageTab p_imageTab ) {
		this.getTabs().add( p_imageTab );
		p_imageTab.setThumbnailDimensions( TAB_THUMBNAIL_HEIGHT, TAB_THUMBNAIL_WIDTH );
	}
	
}
