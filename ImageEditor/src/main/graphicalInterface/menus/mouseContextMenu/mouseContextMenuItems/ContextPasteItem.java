package main.graphicalInterface.menus.mouseContextMenu.mouseContextMenuItems;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import main.Clipboard;
import main.graphicalInterface.image.ImageCanvas;

public class ContextPasteItem extends MenuItem {
	
	public ContextPasteItem() {
		super( "Paste" );
		
		super.setOnAction( new ContextPasteAction() );
	}
	
	private class ContextPasteAction implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			
			Object object = event.getSource();
			if ( object instanceof MenuItem ) {
				if ( object instanceof ContextPasteItem ) {
					ContextPasteItem copyItem = (ContextPasteItem) object;
					ContextMenu menu = copyItem.getParentPopup();
					Node node = menu.getOwnerNode();
					if ( node instanceof ImageCanvas ) {
						ImageCanvas imageCanvas = (ImageCanvas) node;
						Image selectedImage = Clipboard.INSTANCE.loadFromClipboard();
						if ( selectedImage != null ) {
							paste( imageCanvas, selectedImage, menu, event );
						}
					}
				}
			}
			
		}
		
		private class Wrapper<T> { T value; }
		
		private void paste( ImageCanvas p_imageCanvas, Image p_selection, ContextMenu p_menu, ActionEvent p_event ){
			
			double yOffset = calculateVerticalPasteOffset( p_menu );
			double xOffset = calculateHorizontalPasteOffset( p_menu );
			
			Rectangle dragRect = new Rectangle( 20, 20, Color.WHITE );
			Rectangle resizeRectNW = new Rectangle( 20,20, Color.BLUE );
			Rectangle rotateRect = new Rectangle( 20,20,Color.HOTPINK );
			Rectangle imageRect = new Rectangle( p_selection.getWidth(), p_selection.getHeight(), new ImagePattern(p_selection) );
			Image cursor_drag_arrow = new Image( "assets/Cursor_Drag_Arrow_whiteBack.png" );
			dragRect.setFill( new ImagePattern(cursor_drag_arrow) );
			
			Image resize_cursor = new Image("assets/resize-5.png");
			resizeRectNW.setFill( new ImagePattern(resize_cursor) );
			
			Wrapper<Point2D> mouseLocation = new Wrapper<>();
			setUpDragging(dragRect, mouseLocation);
			setUpDragging(resizeRectNW, mouseLocation);
			setUpDragging(rotateRect, mouseLocation);
			
			Pane stackPane = new Pane();
			
			stackPane.setMinSize( p_imageCanvas.getWidth(), p_imageCanvas.getHeight() );
			stackPane.setMaxSize( p_imageCanvas.getWidth(), p_imageCanvas.getHeight() );
			stackPane.setPrefSize( p_imageCanvas.getWidth(), p_imageCanvas.getHeight() );
			stackPane.getChildren().addAll( imageRect, dragRect, resizeRectNW, rotateRect );
			
			p_imageCanvas.getChildren().add(stackPane);
			
			imageRect.setManaged(false);
			dragRect.setManaged(false);
			resizeRectNW.setManaged(false);
			rotateRect.setManaged(false);
			
			imageRect.setX( p_menu.getX() );
			imageRect.setY( p_menu.getY() - yOffset );
			
			dragRect.xProperty().bind( imageRect.xProperty().add(imageRect.widthProperty().divide(2)) );
			dragRect.yProperty().bind( imageRect.yProperty().add(imageRect.heightProperty()) );
			
			resizeRectNW.xProperty().bind( imageRect.xProperty().subtract(resizeRectNW.widthProperty()) );
			resizeRectNW.yProperty().bind( imageRect.yProperty() );
			
			rotateRect.xProperty().bind( imageRect.xProperty().add(imageRect.widthProperty()) );
			rotateRect.yProperty().bind( imageRect.yProperty() );
			
			dragRect.setOnMouseDragged( new EventHandler<MouseEvent>()  {
				@Override
				public void handle(MouseEvent event) {
					if ( mouseLocation.value != null ) {
		                double deltaX = event.getSceneX() - mouseLocation.value.getX();
		                double deltaY = event.getSceneY() - mouseLocation.value.getY();
		                double newX = imageRect.getX() + deltaX ;
		                double newMaxX = newX + imageRect.getWidth();
		                if (newX >= dragRect.getWidth() 
		                        && newMaxX <= imageRect.getParent().getBoundsInLocal().getWidth() - dragRect.getWidth() ) {
		                	imageRect.setX(newX);
		                }
		                double newY = imageRect.getY() + deltaY ;
		                double newMaxY = newY + imageRect.getHeight();
		                if (newY >= dragRect.getHeight()  
		                        && newMaxY <= imageRect.getParent().getBoundsInLocal().getHeight() - dragRect.getHeight() ) {
		                	imageRect.setY(newY);
		                }
		                mouseLocation.value = new Point2D( event.getSceneX(), event.getSceneY() );
		            }
				}
	        });
			
			resizeRectNW.setOnMouseDragged(dragEvent -> {
	            if ( mouseLocation.value != null ) {
	                double deltaX = dragEvent.getSceneX() - mouseLocation.value.getX();
	                double deltaY = dragEvent.getSceneY() - mouseLocation.value.getY();
	                double newX = imageRect.getX() + deltaX ;
	                if (newX >= resizeRectNW.getWidth() / 2 
	                        && newX <= imageRect.getX() + imageRect.getWidth() - resizeRectNW.getWidth() / 2) {
	                	imageRect.setX(newX);
	                    imageRect.setWidth(imageRect.getWidth() - deltaX);
	                }
	                double newY = imageRect.getY() + deltaY ;
	                if (newY >= resizeRectNW.getWidth() / 2 
	                        && newY <= imageRect.getY() + imageRect.getHeight() - resizeRectNW.getWidth() / 2) {
	                	imageRect.setY(newY);
	                    imageRect.setHeight( imageRect.getHeight() - deltaY );
	                }
	                mouseLocation.value = new Point2D( dragEvent.getSceneX(), dragEvent.getSceneY() );
	            }
	        });
			
			rotateRect.setOnMouseDragged(dragEvent -> {
	            if ( mouseLocation.value != null ) {
	                double deltaX = dragEvent.getSceneX() - mouseLocation.value.getX();
	                double deltaY = dragEvent.getSceneY() - mouseLocation.value.getY();
	                
	                Double rotationRadians = -1 * ( Math.atan2(deltaX, deltaY) );
	                Double rotationDegrees = Math.toDegrees(rotationRadians) + 180;
	                
	                imageRect.setRotate(rotationDegrees);
	                
	                mouseLocation.value = new Point2D(dragEvent.getSceneX(), dragEvent.getSceneY());
	            }
	        });
			
		}
		
		private void setUpDragging( Rectangle p_rect, Wrapper<Point2D> mouseLocation ) {

			p_rect.setOnDragDetected(event -> {
				p_rect.getParent().setCursor(Cursor.CLOSED_HAND);
	            mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
	        });

			p_rect.setOnMouseReleased(event -> {
				p_rect.getParent().setCursor(Cursor.DEFAULT);
	            mouseLocation.value = null ;
	        });
	    }
		
		private double calculateVerticalPasteOffset( ContextMenu p_menu ) {
			
			double l_offset =  p_menu.getHeight() ;
			
			return l_offset;
			
		}
		
		private double calculateHorizontalPasteOffset( ContextMenu p_menu ) {
			
			double l_offset =  p_menu.getWidth() ;
			
			return l_offset;
			
		}
		
	}
	
}
