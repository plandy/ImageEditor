package main.graphicalInterface;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.applicationConstants.StringConstants;
import main.graphicalInterface.image.ImageTab;
import main.graphicalInterface.image.ImageTabPane;
import main.utility.SystemInformationUtility;

public class MainWindow extends Stage {
	
	//private final Stage stage;
	private final Scene scene;
	private final VBox rootNode;
	public final ImageTabPane imageTabPane;
	
	public MainWindow() {
		
		super();
		super.setTitle( StringConstants.APPLICATION_TITLE );
		
		rootNode = new VBox();
		rootNode.setAlignment( Pos.TOP_CENTER );
		
		scene = new Scene( rootNode );
		
		super.setScene( scene );
		super.setMaximized( true );
		
		initializeWindowDimensions();
		
		imageTabPane = new ImageTabPane();
		rootNode.getChildren().addAll( new TopMenuBar(this), imageTabPane );
		
	}
	
	private void initializeWindowDimensions() {
		
		//set window to the size of the currently focused monitor
		int screenWidth = SystemInformationUtility.getScreenWidth();
		int screenHeight = SystemInformationUtility.getScreenHeight();
		super.setWidth( screenWidth );
		super.setHeight( screenHeight );
	}

	public ImageTab getSelectedImageTab() {
		return imageTabPane.getFocusedTab();
	}
	
}
