package main.graphicalInterface.tools;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import main.graphicalInterface.image.ImageCanvas;
import main.graphicalInterface.image.ImageTab;
import main.utility.GeometryUtility;

public class LassoSelectorTool extends AbstractToolButton {
	
	private final ImageTab imageTab;
	
	private ImageCanvas drawingCanvas;
	private EventHandler<MouseEvent> currentEventHandler;
	
	public LassoSelectorTool( ImageTab p_imageTab, ToggleGroup p_toggleGroup ) {
		super( "Lasso Selector", p_toggleGroup );
		
		imageTab = p_imageTab;
	}

	@Override
	protected void onSelectedAction() {		
		ImageCanvas selectedImageLayer = imageTab.getSelectedLayer();
		drawingCanvas = imageTab.addFakeCanvas();
		currentEventHandler = new Tool(drawingCanvas, selectedImageLayer);
		drawingCanvas.addEventHandler( MouseEvent.ANY, currentEventHandler );
	}

	@Override
	protected void onDeselectedAction() {
		imageTab.destroyFakeCanvas( drawingCanvas );
		currentEventHandler = null;
		drawingCanvas = null;
	}
	
	private class Tool implements EventHandler<MouseEvent> {
		
		private final ImageCanvas selectedImageLayer;
		
		private final ImageCanvas fakeCanvas;
		private final GraphicsContext gc;
		
		private Image selectedPixels;
		
		private double xMin, xMax, yMin, yMax;
		
		private long startTime, endTime;
		
		//time in Seconds
		private long minDragTime = 1 * 1000000000;
		
		private ArrayList<Point2D> selectedPointsList = new ArrayList<Point2D>( 400 );
		
		public Tool( ImageCanvas p_imageLayer, ImageCanvas p_selectedImageLayer ) {
			selectedImageLayer = p_selectedImageLayer;
			
			fakeCanvas = p_imageLayer;
			gc = fakeCanvas.getGraphicsContext2D();
		}

		@Override
		public void handle(MouseEvent event) {
			
			if ( MouseButton.PRIMARY.equals(event.getButton()) ) {
				
				if ( MouseEvent.MOUSE_PRESSED.equals(event.getEventType()) ) {
					
					startTime = System.nanoTime();
					
					gc.clearRect( 0, 0, fakeCanvas.getWidth(), fakeCanvas.getHeight() );
					gc.beginPath();
					selectedPointsList.clear();
					selectedPointsList.add( new Point2D(event.getX(), event.getY()) );
					xMin = event.getX();
					xMax = event.getX();
					yMin = event.getY();
					yMax = event.getY();
				}
				
				if ( MouseEvent.MOUSE_DRAGGED.equals(event.getEventType()) ) {
					
					Point2D previousPoint = selectedPointsList.get(selectedPointsList.size() - 1);
					updateBounds( event.getX(), event.getY() );
					updateBounds( previousPoint.getX(), previousPoint.getY() );
					ArrayList<Point2D> newPoints = GeometryUtility.linearInterpolate2D( previousPoint.getX(), event.getX(), previousPoint.getY(), event.getY());
					selectedPointsList.addAll(newPoints);
					selectedPointsList.add( new Point2D(event.getX(), event.getY()) );
					gc.setFill( Color.RED );
					gc.fillOval( event.getX(), event.getY(), 1, 1 );
					
					gc.lineTo(event.getX(), event.getY());
					gc.setStroke( Color.RED );
					gc.setLineDashes(1,10);
					gc.stroke();			
			
				}
				
				if ( MouseEvent.MOUSE_RELEASED.equals(event.getEventType()) ) {
					
					endTime = System.nanoTime();
					
					if ( isRealSelection() == true ) {
						Point2D beginPoint = selectedPointsList.get(0);
						Point2D endPoint = selectedPointsList.get( selectedPointsList.size() - 1 );
						
						ArrayList<Point2D> extraPoints = GeometryUtility.linearInterpolate2D( endPoint.getX(), beginPoint.getX(), endPoint.getY(), beginPoint.getY());
						selectedPointsList.addAll( extraPoints );
						
						selectedPixels = createSelectionImage( selectedImageLayer, selectedPointsList, xMin, xMax, yMin, yMax );
						
					} else {
						gc.clearRect( 0, 0, fakeCanvas.getWidth(), fakeCanvas.getHeight() );
					}
				}//end if ( MouseEvent.MOUSE_RELEASED.equals(event.getEventType()) )
			}
			
		}
		
		private Image createSelectionImage( ImageCanvas p_canvasLayer, ArrayList<Point2D> p_selectionBoundary, double xMin, double xMax, double yMin, double yMax ) {
			WritableImage selectionImage = new WritableImage( (int)(xMax - xMin), (int)(yMax - yMin) );
			PixelWriter l_writer = selectionImage.getPixelWriter();
			
			int size = 0;
			
			PixelReader l_reader = p_canvasLayer.snapshotCanvas().getPixelReader();
			for ( int x = (int) xMin, newX = 0; x < xMax; x++, newX++ ) {
				for ( int y = (int) yMin, newY = 0; y< yMax; y++, newY++ ) {
					if ( GeometryUtility.containsPoint(new Point2D(x,y), p_selectionBoundary) ) {
						Color l_pixelColor = l_reader.getColor(x, y);
						l_writer.setColor(newX, newY, l_pixelColor);
						size++;
					}
				}
			}
			
			System.out.println( "Num pixels in selected polygon: " + size );
			
			return selectionImage;
		}
		
		private void updateBounds( double x, double y ) {
			if ( x > xMax ) {
				xMax = x;
			} else if ( x < xMin ) {
				xMin = x;
			}
			
			if ( y > yMax ) {
				yMax = y;
			} else if ( y < yMin ) {
				yMin = y;
			}
		}
		
		private boolean isLongDrag() {
			return ( (endTime - startTime) > minDragTime );
		}
		
		private boolean isSignificantSizedRegion() {
			boolean isSignificantSizedRegion = true;
			
			if ( (xMax - xMin) < 1 ) {
				isSignificantSizedRegion = false;
			}
			
			if ( (yMax - yMin) < 1 ) {
				isSignificantSizedRegion = false;
			}
			
			return isSignificantSizedRegion;
		}
		
		private boolean isRealSelection() {
			boolean isReal = true;
			
			if ( isLongDrag() == false || isSignificantSizedRegion() == false ) {
				isReal = false;
			}
			
			return isReal;
		}
		
	};
	
}
