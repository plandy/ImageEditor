package graphicalInterface.image;

import javafx.beans.property.DoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;

public class ImageTab extends Tab {
	
	private final ObservableList<ImageCanvas> imageLayers = FXCollections.observableArrayList();
	//private final ScrollPane scrollPane;
	
	private double thumbnailHeight = 50;
	private double thumbnailWidth = 50;
	
	private final double imageCanvasHeight;
	private final double imageCanvasWidth;
	
	private final ImageLayerSelectionView imageLayerSelectionView = new ImageLayerSelectionView( this );
	private StackPane imageLayersStackPane = new StackPane();
	private final GridPaneHorizontal baseLayoutPane = new GridPaneHorizontal();
	
	private double layerThumbnailHeightProperty = 150.0;
	private double layerThumbnailWidthProperty = 150.0;
	
	public ImageTab( Image p_image ) {
		super.setContent( baseLayoutPane );
		setThumbnail( p_image );
		setBaseImage( p_image );
		
		imageCanvasHeight = p_image.getHeight();
		imageCanvasWidth = p_image.getWidth();
	}
	
	private void addImageLayer( Image p_image ) {
		ImageCanvas imageLayerCanvas = new ImageCanvas( p_image );
		imageLayers.add( imageLayerCanvas );
		imageLayersStackPane.getChildren().add( imageLayerCanvas );
		addLayerToViewer( imageLayerCanvas );
	}
	
	private void setBaseImage( Image p_image ) {		
		addImageLayer( p_image );
	}
	
	public void addImageLayerButtonAction() {
		addImageLayer( new WritableImage((int)imageCanvasWidth, (int)imageCanvasHeight) );
	}
	
	public void imageLayerSelectionAction( ImageCanvas p_selectedImageLayerCanvas ) {
		
		for ( ImageCanvas imageLayer : imageLayers ) {
			imageLayer.disableLayer();
		}
		
		p_selectedImageLayerCanvas.enableLayer();
		
	}
	
	private void addLayerToViewer( ImageCanvas p_imageLayerCanvas ) {
		imageLayerSelectionView.addLayerToView( p_imageLayerCanvas );
	}
	
	public void doBaseLayout() {
		//baseLayoutPane.setAlignment( Pos.CENTER );
		
		//DoubleProperty heightProperty = imageLayersSelectionView.prefHeightProperty();// = this.getTabPane().heightProperty();
		DoubleProperty heightProperty = imageLayerSelectionView.prefHeightProperty();
		heightProperty.setValue( super.getTabPane().heightProperty().getValue() );
		
		baseLayoutPane.addNode( new ListView<ImageView>(), 15 );
		baseLayoutPane.addNode( imageLayersStackPane, 70 );
		baseLayoutPane.addNode( imageLayerSelectionView, 15 );
	}
	
	private void setThumbnail( Image p_image ) {
		ImageView thumbnail = new ImageView( p_image );
		thumbnail.setPreserveRatio( true );
		super.setGraphic( thumbnail );
	}
	
	public void setThumbnailDimensions( double p_height, double p_width ) {
		thumbnailHeight = p_height;
		thumbnailWidth = p_width;
		( (ImageView) super.getGraphic() ).setFitHeight( thumbnailHeight );
		( (ImageView) super.getGraphic() ).setFitWidth( thumbnailWidth );
	}

	public double getLayerThumbnailHeightProperty() {
		return layerThumbnailHeightProperty;
	}

	public double getLayerThumbnailWidthProperty() {
		return layerThumbnailWidthProperty;
	}
	
}
