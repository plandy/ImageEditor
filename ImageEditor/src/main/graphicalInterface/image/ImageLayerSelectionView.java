package main.graphicalInterface.image;

import java.util.HashMap;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ImageLayerSelectionView extends VBox {
	
	private final ListView<Pane> imageLayersListView = new ListView<Pane>();
	private final Button addLayerButton = new Button( "Add layer" );
	private final ImageTab parentImageTab;
	private final ObservableList<LayerTuple> layerTupleList = FXCollections.observableArrayList();
	
	private final HashMap<ImageCanvas, ImageView> map = new HashMap<>();
	
	//number of seconds between updates
	private final int thumbnailUpdateDelay = 10;
	
	public ImageLayerSelectionView( ImageTab p_imageTab ) {
		super();
		
		parentImageTab = p_imageTab;
		
		super.getChildren().add( addLayerButton );
		super.getChildren().add( imageLayersListView );
		
		setListeners();
		
		ThumbnailUpdaterService service = new ThumbnailUpdaterService();
		service.start();
	}
	
	public void addLayerToView( ImageCanvas p_imageLayerCanvas ) {
		
		LayerTuple layerTuple = new LayerTuple( p_imageLayerCanvas );
		layerTupleList.add( layerTuple );
		
		imageLayersListView.getItems().add( layerTuple.layerViewPane );
		imageLayersListView.getSelectionModel().select( layerTuple.layerViewPane );
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
		
		imageLayersListView.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> {
			LayerTuple layerTuple = findLayer( newValue );
			ImageCanvas imageLayerCanvas = layerTuple.imageLayerCanvas;
			
			parentImageTab.imageLayerSelectionAction( imageLayerCanvas );
		});
	}
	
	private final class LayerTuple {
		public final ImageCanvas imageLayerCanvas;
		public final Pane layerViewPane;
		
		public LayerTuple( ImageCanvas p_imageLayerCanvas ) {
			imageLayerCanvas = p_imageLayerCanvas;
			
			layerViewPane = new Pane();
			layerViewPane.setStyle("-fx-background-color: white");
			ImageView layerThumbnail = createLayerThumbnail( p_imageLayerCanvas );
			layerViewPane.getChildren().add( layerThumbnail );
			layerViewPane.setMaxHeight( layerThumbnail.getFitHeight() );
			layerViewPane.setMaxWidth( layerThumbnail.getFitWidth() );
		}
		
	}
	
	private LayerTuple findLayer( Pane p_pane ) {
		LayerTuple layer = null;
		
		for ( LayerTuple layerTuple : layerTupleList ) {
			if ( layerTuple.layerViewPane == p_pane ) {
				layer = layerTuple;
				break;
			}
		}
		
		return layer;
	}
	
	private void updateThumbnails() {
		for (LayerTuple layerTuple : layerTupleList ) {
			ImageView thumbnail = map.get( layerTuple.imageLayerCanvas );
			for ( Pane pane : imageLayersListView.getItems() ) {
				if ( pane == layerTuple.layerViewPane ) {
					pane.getChildren().clear();
					pane.getChildren().add( thumbnail );
				}
			}
		}
	}
	
	private final class ThumbnailUpdaterService extends Thread {
		
		@Override
		public void run() {
			while ( true ) {
				try {
					Thread.sleep( 1000 * thumbnailUpdateDelay );
					
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							for ( LayerTuple layerTuple : layerTupleList ) {
								ImageView imageView = createLayerThumbnail( layerTuple.imageLayerCanvas );
								map.put( layerTuple.imageLayerCanvas, imageView );
							}
						}	
					});
						
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							updateThumbnails();
						}	
					});
				} catch (InterruptedException e) {
					throw new RuntimeException();
				}
			}
		}
	}

}
