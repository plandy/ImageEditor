package graphicalInterface.image;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ImageLayerSelectionView extends VBox {
	
	//private final ListView<ImageView> imageLayersListView = new ListView<ImageView>();
	private final ListView<Pane> imageLayersListView = new ListView<Pane>();
	
	private final Button addLayerButton = new Button( "Add layer" );
	
	private final ImageTab parentImageTab;
	
	public ImageLayerSelectionView( ImageTab p_imageTab ) {
		super();
		
		parentImageTab = p_imageTab;
		
		super.getChildren().add( addLayerButton );
		super.getChildren().add( imageLayersListView );
		
		setListeners();
	}
	
	public void addLayerToView( ImageCanvas p_imageLayerCanvas ) {
		Pane pane = new Pane();
		pane.setStyle("-fx-background-color: white");
		ImageView layerThumbnail = createLayerThumbnail( p_imageLayerCanvas );
		pane.getChildren().add(layerThumbnail);
		pane.setMaxHeight(layerThumbnail.getFitHeight());
		pane.setMaxWidth(layerThumbnail.getFitWidth());
		imageLayersListView.getItems().add( pane );
	}
	
	private ImageView createLayerThumbnail( ImageCanvas p_imageLayerCanvas ) {
		Image tempImage = p_imageLayerCanvas.snapshot(null, null);
		ImageView layerThumbnail = new ImageView( tempImage );
		layerThumbnail.setPreserveRatio( true );
		layerThumbnail.setFitHeight( parentImageTab.getLayerThumbnailHeightProperty() );
		layerThumbnail.setFitWidth( parentImageTab.getLayerThumbnailWidthProperty() );
		
		return layerThumbnail;
	}
	
	private void setListeners() {
		addLayerButton.setOnAction(e -> {
			parentImageTab.addImageLayerButtonAction();
		});
	}

}
