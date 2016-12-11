package graphicalInterface.image;

import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageTab extends Tab {
	
	//private final ObservableList<ImageCanvas> imageLayers;
	//private final ScrollPane scrollPane;
	
	public static final double THUMBNAIL_HEIGHT = 75;
	public static final double THUMBNAIL_WIDTH = 75;
	
	public ImageTab( Image p_image ) {
		this.setContent( new ImageCanvas(p_image) );
		ImageView thumbnail = new ImageView( p_image );
		thumbnail.setFitHeight( THUMBNAIL_HEIGHT );
		thumbnail.setFitWidth( THUMBNAIL_WIDTH );
		thumbnail.setPreserveRatio( true );
		this.setGraphic( thumbnail );
	}
	
}
