package graphicalInterface.image;

import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ImageCanvas extends Canvas {
	public ImageCanvas( Image p_image ) {
		super( p_image.getWidth(), p_image.getHeight() );
		
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.drawImage( p_image, 0, 0 );
		
		setDefaultCursor();
	}
	
	private void setDefaultCursor() {
		super.setCursor( Cursor.CROSSHAIR );
	}
}
