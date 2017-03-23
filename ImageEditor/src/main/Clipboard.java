package main;
import javafx.scene.image.Image;
import main.utility.ImageUtility;

public enum Clipboard {
	
	INSTANCE;
	
	private Image thing;
	
	public void storeToClipboard( Image p_image ) {
		if ( p_image != null ) {
			thing = ImageUtility.cloneImage( p_image );
		}
	}
	
	public Image loadFromClipboard() {
		if ( thing != null ) {
			return ImageUtility.cloneImage( thing );
		}
		return null;
	}
	

}
