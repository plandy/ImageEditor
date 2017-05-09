package main.graphicalInterface.image;

import java.util.Random;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import main.graphicalInterface.menus.mouseContextMenu.MouseContextMenu;
import main.graphicalInterface.menus.mouseContextMenu.mouseContextMenuItems.ContextPasteItem;

public class ImageCanvas extends StackPane {
	
	private final Canvas canvas;
	private final GraphicsContext gc;
	
	private Image selectionImage;

	private final MouseContextMenu contextMenu = new MouseContextMenu( this );

	EventHandler<MouseEvent>  basicMouseEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if ( MouseButton.PRIMARY.equals(event.getButton()) ) {
                contextMenu.hide();
            }
        }
    };
    {
        addEventHandler( MouseEvent.ANY, basicMouseEventHandler );
    }

	private ContextPasteItem.PasteHarness pastedNode = null;
	
	/**
	 * Creates an ImageCanvas using the given Image
	 */
	public ImageCanvas( Image p_image ) {
		//super( p_image.getWidth(), p_image.getHeight() );
		
		canvas = new Canvas( p_image.getWidth(), p_image.getHeight() );
		super.getChildren().add( canvas );
		gc = canvas.getGraphicsContext2D();
		
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
		//super( p_width, p_height );
		canvas = new Canvas( p_width, p_height );
		super.getChildren().add( canvas );
		gc = canvas.getGraphicsContext2D();
		
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

	public void pasteSelectionOntoLayer( ContextPasteItem.PasteHarness p_paste ) {

		super.getChildren().remove( pastedNode );

		pastedNode = p_paste;
		super.getChildren().add( pastedNode );
	}

	public ContextPasteItem.PasteHarness getPastedNode() {
		return pastedNode;
	}

	public void affixSelectionToLayer() {
        pastedNode.removeToolThingies();
		Image layerSnapshot = this.snapshotCanvas();
		gc.drawImage( layerSnapshot, 0, 0 );

        super.getChildren().remove( pastedNode );

		pastedNode = null;
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
		SnapshotParameters parameters = new SnapshotParameters();
		parameters.setFill(Color.TRANSPARENT);

		return super.snapshot( parameters, null );
	}
	
	public void setSelectionImage( Image p_newSelection ) {
		selectionImage = p_newSelection;
	}
	
	public Image getSelectionImage() {
		return selectionImage;
	}
	
	public GraphicsContext getGraphicsContext2D() {
		return gc;
	}
	
}
