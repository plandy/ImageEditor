package graphicalInterface.image;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
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
	
	public void addLayerToView( ImageView p_imageView ) {
		Pane pane = new Pane();
		pane.setStyle("-fx-background-color: white");
		pane.getChildren().add(p_imageView);
		pane.setMaxHeight(p_imageView.getFitHeight());
		pane.setMaxWidth(p_imageView.getFitWidth());
		imageLayersListView.getItems().add( pane );
	}
	
	private void setListeners() {
		addLayerButton.setOnAction(e -> {
			parentImageTab.addImageLayerButtonAction();
		});
	}

}
