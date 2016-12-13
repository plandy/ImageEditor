package graphicalInterface.image;

import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageTab extends Tab {
	
	//private final ObservableList<ImageCanvas> imageLayers;
	//private final ScrollPane scrollPane;
	
	private double thumbnailHeight = 50;
	private double thumbnailWidth = 50;
	
	public ImageTab( Image p_image ) {
		this.setContent( new ImageCanvas(p_image) );
		ImageView thumbnail = new ImageView( p_image );
		thumbnail.setPreserveRatio( true );
		this.setGraphic( thumbnail );
	}
	
	public void setThumbnailDimensions( double p_height, double p_width ) {
		thumbnailHeight = p_height;
		thumbnailWidth = p_width;
		( (ImageView) this.getGraphic() ).setFitHeight( thumbnailHeight );
		( (ImageView) this.getGraphic() ).setFitWidth( thumbnailWidth );
	}
	
}
