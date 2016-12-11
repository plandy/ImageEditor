package graphicalInterface;

import java.io.File;

import applicationConstants.StringConstants;
import graphicalInterface.menus.fileMenu.fileDialog.FileExtensionFilterList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utility.SystemInformationUtility;

public class MainWindow {
	
	private final Stage stage;
	private final Scene scene;
	private final VBox rootNode;
	private final TabPane imageTabPane;
	
	public MainWindow() {
		
		stage = new Stage();
		stage.setTitle( StringConstants.APPLICATION_TITLE );
		
		rootNode = new VBox();
		rootNode.setAlignment( Pos.TOP_CENTER );
		
		scene = new Scene( rootNode );
		
		stage.setScene( scene );
		stage.setMaximized( true );
		
		initializeWindowDimensions();
		
		imageTabPane = new TabPane();
		rootNode.getChildren().addAll( new TopMenuBar(this), imageTabPane );
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
	
	public void openFileAction() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle( StringConstants.FILELOAD_TITLE );
		fileChooser.getExtensionFilters().addAll( new FileExtensionFilterList() );
		File l_returnFile = fileChooser.showOpenDialog( stage );
		if ( l_returnFile != null ) {
			
		}
	}
	
	public void saveFileAction() {
		
	}
	
}
