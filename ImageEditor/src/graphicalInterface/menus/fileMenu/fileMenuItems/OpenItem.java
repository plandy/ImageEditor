package graphicalInterface.menus.fileMenu.fileMenuItems;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import applicationConstants.StringConstants;
import graphicalInterface.MainWindow;
import graphicalInterface.menus.fileMenu.fileDialog.FileExtensionFilterList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

public class OpenItem extends MenuItem {
	
	private final MainWindow parentWindow;
	
	public OpenItem ( MainWindow p_parentWindow ) {
		super( StringConstants.FILELOAD_TITLE );
		
		super.setOnAction( e -> {
			openFileAction();
		});
		
		parentWindow = p_parentWindow;
	}
	
	private void openFileAction() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle( StringConstants.FILELOAD_TITLE );
		fileChooser.getExtensionFilters().addAll( new FileExtensionFilterList() );
		File imageFile = fileChooser.showOpenDialog( parentWindow );
		if ( imageFile != null ) {
			Image image = readImageFromFile( imageFile );
			openLoadedImage( image );
		}
	}
	
	private static Image readImageFromFile( File p_file ) {
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read( p_file );
		} catch (IOException e) {
			throw new RuntimeException();
		}
		Image image = SwingFXUtils.toFXImage( bufferedImage, null );
		
		return image;
	}
	
	private void openLoadedImage( Image p_image ) {		
		parentWindow.imageTabPane.addTab( p_image );
	}
}
