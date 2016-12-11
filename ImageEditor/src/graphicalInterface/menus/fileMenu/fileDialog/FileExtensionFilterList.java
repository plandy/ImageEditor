package graphicalInterface.menus.fileMenu.fileDialog;

import java.util.ArrayList;

import applicationConstants.FileExtensionFilterConstants;
import javafx.stage.FileChooser;

public class FileExtensionFilterList extends ArrayList<FileChooser.ExtensionFilter> {
	public FileExtensionFilterList() {
		this.add( FileExtensionFilterConstants.FILEEXTENSION_ALL );
		this.add( FileExtensionFilterConstants.FILEEXTENSION_BMP );
		this.add( FileExtensionFilterConstants.FILEEXTENSION_JPEG );
		this.add( FileExtensionFilterConstants.FILEEXTENSION_PNG );
	}
}
