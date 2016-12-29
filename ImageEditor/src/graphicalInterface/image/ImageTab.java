package graphicalInterface.image;

import javafx.beans.property.DoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class ImageTab extends Tab {
	
	private final ObservableList<ImageCanvas> imageLayers = FXCollections.observableArrayList();
	//private final ScrollPane scrollPane;
	
	private double thumbnailHeight = 50;
	private double thumbnailWidth = 50;
	
	private ListView<ImageView> imageLayersSelectionView = new ListView<ImageView>();
	
	private StackPane imageLayersStackPane = new StackPane();
	
	private GridPaneHorizontal baseLayoutPane = new GridPaneHorizontal();
	
	public ImageTab( Image p_image ) {
		super.setContent( baseLayoutPane );
		setThumbnail( p_image );
		setBaseImage( p_image );
	}
	
	private void addImageLayer( ImageCanvas p_imageCanvasLayer ) {
		imageLayers.add( p_imageCanvasLayer );
		imageLayersStackPane.getChildren().add( p_imageCanvasLayer );
	}
	
	private void setBaseImage( Image p_image ) {
		//imageLayersStackPane.getChildren().add(e)
		
		ImageCanvas baseCanvas = new ImageCanvas( p_image );
		addImageLayer( baseCanvas );
		
	}
	
	public void doBaseLayout() {
		//baseLayoutPane.setAlignment( Pos.CENTER );
		
		DoubleProperty heightProperty = imageLayersSelectionView.prefHeightProperty();// = this.getTabPane().heightProperty();
		heightProperty.setValue( this.getTabPane().heightProperty().getValue() );
		
		baseLayoutPane.addNode( new ListView<ImageView>(), 15 );
		baseLayoutPane.addNode( imageLayersStackPane, 70 );
		baseLayoutPane.addNode( imageLayersSelectionView, 15 );
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
	
}
