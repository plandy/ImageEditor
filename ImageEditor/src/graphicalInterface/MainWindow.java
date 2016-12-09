package graphicalInterface;

import applicationConstants.StringConstants;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utility.SystemInformationUtility;

public class MainWindow {
	
	private Stage stage;
	private Scene scene;
	private VBox rootNode;
	
	public MainWindow() {
		
		stage = new Stage();
		stage.setTitle( StringConstants.APPLICATION_TITLE );
		
		rootNode = new VBox();
		rootNode.setAlignment( Pos.TOP_CENTER );
		
		scene = new Scene( rootNode );
		
		stage.setScene( scene );
		stage.setMaximized( true );
		
		initializeWindowDimensions();
		
		rootNode.getChildren().add( new TopMenuBar() );
	}
	
	private void initializeWindowDimensions() {
		
		//set window to the size of the currently focused monitor
		int l_screenWidth = SystemInformationUtility.getScreenWidth();
		int l_screenHeight = SystemInformationUtility.getScreenHeight();
		stage.setWidth( l_screenWidth );
		stage.setHeight( l_screenHeight );
	}
	
	public void show() {
		stage.show();
	}
	
}
