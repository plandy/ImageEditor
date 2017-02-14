package graphicalInterface;

import applicationConstants.StringConstants;
import graphicalInterface.image.ImageTabPane;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utility.SystemInformationUtility;

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
		int l_screenWidth = SystemInformationUtility.getScreenWidth();
		int l_screenHeight = SystemInformationUtility.getScreenHeight();
		super.setWidth( l_screenWidth );
		super.setHeight( l_screenHeight );
	}
	
}
