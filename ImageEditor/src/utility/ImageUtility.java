package utility;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ImageUtility {
	public static Image cloneImage( Image p_image ) {
		WritableImage clone = new WritableImage( (int)p_image.getWidth(), (int)p_image.getHeight() );
		
		int width = (int)p_image.getWidth();
        int height = (int)p_image.getHeight();
        
        PixelWriter pixelWriter = clone.getPixelWriter();
        PixelReader pixelReader = p_image.getPixelReader();
        
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                Color color = pixelReader.getColor(x, y);
                pixelWriter.setColor(x, y, color);
            }
        }
		
		return clone;
	}
}
