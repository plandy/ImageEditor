package graphicalInterface;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import applicationConstants.StringConstants;
import graphicalInterface.image.ImageTab;
import graphicalInterface.menus.fileMenu.fileDialog.FileExtensionFilterList;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
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
		imageTabPane.setTabMinHeight( ImageTab.THUMBNAIL_HEIGHT );
		imageTabPane.setTabMaxHeight( ImageTab.THUMBNAIL_HEIGHT );
		imageTabPane.setTabMinWidth( ImageTab.THUMBNAIL_WIDTH );
		imageTabPane.setTabMaxWidth( ImageTab.THUMBNAIL_WIDTH );
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
		File imageFile = fileChooser.showOpenDialog( stage );
		if ( imageFile != null ) {
			Image image = readImageFromFile( imageFile );
			openLoadedImage( image );
		}
	}
	
	private static Image readImageFromFile( File p_file ) {
		BufferedImage l_bufferedImage;
		try {
			l_bufferedImage = ImageIO.read( p_file );
		} catch (IOException e) {
			throw new RuntimeException();
		}
		Image image = SwingFXUtils.toFXImage( l_bufferedImage, null );
		
		return image;
	}
	
	private void openLoadedImage( Image p_image ) {
		ImageTab imageTab = new ImageTab( p_image );
		imageTabPane.getTabs().add( imageTab );
	}
	
	public void saveFileAction() {
		
	}
	
}
