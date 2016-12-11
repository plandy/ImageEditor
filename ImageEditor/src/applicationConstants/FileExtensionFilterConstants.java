package applicationConstants;

import javafx.stage.FileChooser;

public class FileExtensionFilterConstants {
	
//	private class FileFilter {
//		public final String key;
//		public final String extension;
//		
//		public FileFilter( String p_key, String p_extension ) {
//			key = p_key;
//			extension = p_extension;
//		}
//	}
	
	public static final FileChooser.ExtensionFilter FILEEXTENSION_ALL = new FileChooser.ExtensionFilter( "All Files", "*" );
	public static final FileChooser.ExtensionFilter FILEEXTENSION_PNG = new FileChooser.ExtensionFilter( "PNG", "*.png" );
	public static final FileChooser.ExtensionFilter FILEEXTENSION_JPEG = new FileChooser.ExtensionFilter( "JPEG", "*.jpg" );
	public static final FileChooser.ExtensionFilter FILEEXTENSION_BMP = new FileChooser.ExtensionFilter( "BMP", "*.bmp" );
	
}
