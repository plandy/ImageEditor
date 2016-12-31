package graphicalInterface.image;

import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class ImageCanvas extends Canvas {
	
	/**
	 * Creates an ImageCanvas using the given Image
	 */
	public ImageCanvas( Image p_image ) {
		super( p_image.getWidth(), p_image.getHeight() );
		
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.drawImage( p_image, 0, 0 );
		
		setDefaultCursor();
	}
	
	/**
	 * Creates a blank ImageCanvas with the given width and height. Pixels will
	 * initially be transparent.
	 */
	public ImageCanvas( double p_width, double p_height ) {
		super( p_width, p_height );
		
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.drawImage( new WritableImage((int)p_width, (int)p_height), 0, 0 );
		
		setDefaultCursor();
	}
	
	private void setDefaultCursor() {
		super.setCursor( Cursor.CROSSHAIR );
	}
}
