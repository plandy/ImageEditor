package graphicalInterface.image;

import java.util.Random;

import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ImageCanvas extends Canvas {
	
	private final GraphicsContext gc = this.getGraphicsContext2D();
	
	/**
	 * Creates an ImageCanvas using the given Image
	 */
	public ImageCanvas( Image p_image ) {
		super( p_image.getWidth(), p_image.getHeight() );
		
		//GraphicsContext gc = this.getGraphicsContext2D();
		gc.drawImage( p_image, 0, 0 );
		
		setDefaultCursor();
		
		gc.setFill(randColor());
	}
	
	/**
	 * Creates a blank ImageCanvas with the given width and height. Pixels will
	 * initially be transparent.
	 */
	public ImageCanvas( double p_width, double p_height ) {
		super( p_width, p_height );
		
		//GraphicsContext gc = this.getGraphicsContext2D();
		gc.drawImage( new WritableImage((int)p_width, (int)p_height), 0, 0 );
		
		setDefaultCursor();
		
	}
	
	private Color randColor() {
		Color color;
		
		Random random = new Random();
		int num = random.nextInt(50);
		
		if ( num < 5 ) {
			color = Color.RED;
		} else if ( num < 10 ) {
			color = Color.YELLOW;
		} else if ( num < 15 ) {
			color = Color.GREEN;
		} else if ( num < 20 ) {
			color = Color.BLUE;
		} else if ( num < 25 ) {
			color = Color.PINK;
		} else if ( num < 30 ) {
			color = Color.ORANGE;
		} else if ( num < 35 ) {
			color = Color.BLACK;
		} else if ( num < 40 ) {
			color = Color.PURPLE;
		} else if ( num < 45 ) {
			color = Color.CYAN;
		} else {
			color = Color.DARKSEAGREEN;
		}
		
		
		return color;
	}
	
	private void setDefaultCursor() {
		super.setCursor( Cursor.CROSSHAIR );
	}
	
	public void enableLayer() {
		super.setMouseTransparent(false);
	}
	
	public void disableLayer() {
		super.setMouseTransparent(true);
	}
	
	public Image snapshotCanvas() {
		return super.snapshot( null, null );
	}
	
}
