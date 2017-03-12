package main.graphicalInterface.menus.fileMenu.fileMenuItems;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import main.applicationConstants.StringConstants;
import main.graphicalInterface.MainWindow;
import main.graphicalInterface.menus.fileMenu.fileDialog.FileExtensionFilterList;

public class SaveItem extends MenuItem {
	
	private final MainWindow parentWindow;
	
	public SaveItem( MainWindow p_parentWindow ) {
		super( StringConstants.FILESAVE_TITLE );
		
		super.setOnAction( e -> {
			saveFileAction();
		});
		
		parentWindow = p_parentWindow;
	}
	
	private void saveFileAction() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle( StringConstants.FILESAVE_TITLE );
		fileChooser.getExtensionFilters().addAll( new FileExtensionFilterList() );
		
		File saveFile = fileChooser.showSaveDialog( parentWindow );
		
		Image imageToSave = parentWindow.imageTabPane.getFocusedTab().getCompositeImage();
		
        try {
            ImageIO.write(SwingFXUtils.fromFXImage( imageToSave,null), "png", saveFile );
        } catch (IOException ex) {
        	throw new RuntimeException();
        }
	}
}
