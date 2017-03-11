package graphicalInterface.image;

import javafx.beans.property.DoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class ImageTab extends Tab {
	
	private final ObservableList<ImageCanvas> imageLayers = FXCollections.observableArrayList();
	private ImageCanvas selectedLayer;
	//private final ScrollPane scrollPane;
	
	private double thumbnailHeight = 50;
	private double thumbnailWidth = 50;
	
	private final double imageCanvasHeight;
	private final double imageCanvasWidth;
	
	private final ImageLayerSelectionView imageLayerSelectionView = new ImageLayerSelectionView( this );
	private final ToolSelectionGrid toolSelectionGrid = new ToolSelectionGrid( this );
	private final StackPane imageLayersStackPane = new StackPane();
	private final GridPaneHorizontal baseLayoutPane = new GridPaneHorizontal();
	private final Group imageLayerGroup = new Group();
	
	private double layerThumbnailHeightProperty = 150.0;
	private double layerThumbnailWidthProperty = 150.0;
	
	public ImageTab( Image p_image ) {
		super.setContent( baseLayoutPane );
		setThumbnail( p_image );
		setBaseImage( p_image );
		
		imageCanvasHeight = p_image.getHeight();
		imageCanvasWidth = p_image.getWidth();
		
		imageLayersStackPane.getChildren().add( imageLayerGroup );
		
	}
	
	private void addImageLayer( Image p_image ) {
		ImageCanvas imageLayerCanvas = new ImageCanvas( p_image );
		imageLayers.add( imageLayerCanvas );
		imageLayerGroup.getChildren().add( imageLayerCanvas );
		addLayerToViewer( imageLayerCanvas );
	}
	
	private void setBaseImage( Image p_image ) {		
		addImageLayer( p_image );
	}
	
	public void addImageLayerButtonAction() {
		addImageLayer( new WritableImage((int)imageCanvasWidth, (int)imageCanvasHeight) );
	}
	
	public void imageLayerSelectionAction( ImageCanvas p_selectedImageLayerCanvas ) {
		
		toolSelectionGrid.deselectCurrentTool();
		
		for ( ImageCanvas imageLayer : imageLayers ) {
			imageLayer.disableLayer();
		}
		
		p_selectedImageLayerCanvas.enableLayer();
		
		setSelectedLayer( p_selectedImageLayerCanvas );
		
	}
	
	private void addLayerToViewer( ImageCanvas p_imageLayerCanvas ) {
		imageLayerSelectionView.addLayerToView( p_imageLayerCanvas );
	}
	
	public void doBaseLayout() {
		//baseLayoutPane.setAlignment( Pos.CENTER );
		
		//DoubleProperty heightProperty = imageLayersSelectionView.prefHeightProperty();// = this.getTabPane().heightProperty();
		DoubleProperty heightProperty = imageLayerSelectionView.prefHeightProperty();
		heightProperty.setValue( super.getTabPane().heightProperty().getValue() );
		
		baseLayoutPane.addNode( toolSelectionGrid, 15 );
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
	
	public Image getCompositeImage() {
		return imageLayerGroup.snapshot(null, null);
	}

	public double getLayerThumbnailHeightProperty() {
		return layerThumbnailHeightProperty;
	}

	public double getLayerThumbnailWidthProperty() {
		return layerThumbnailWidthProperty;
	}
	
	private void setSelectedLayer( ImageCanvas p_imageLayer ) {
		selectedLayer = p_imageLayer;
	}
	
	public ImageCanvas getSelectedLayer() {
		return selectedLayer;
	}
	
	/**
	 * 
	 * Creates a temporary canvas to be used by tools as a visual aid to the user.
	 * 
	 * Not added to the layer system, but placed geometrically on top of all other canvases; ie. all mouse events reach this canvas before any other.
	 * 
	 * @return the newly created canvas
	 */
	public ImageCanvas addFakeCanvas() {
		
		ImageCanvas fakeCanvas = new ImageCanvas( imageCanvasWidth, imageCanvasHeight );
		
		imageLayerGroup.getChildren().add( fakeCanvas );
		
		return fakeCanvas;
	}
	
	public void destroyFakeCanvas( ImageCanvas p_fakeCanvas ) {
		boolean containedCanvas = imageLayerGroup.getChildren().remove( p_fakeCanvas );
		
		if ( containedCanvas == false ) {
			throw new RuntimeException();
		}
	}
	
}
