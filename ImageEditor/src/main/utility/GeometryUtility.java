package main.utility;

import java.util.ArrayList;

import javafx.geometry.Point2D;

public class GeometryUtility {
	
	/**
	 * implementation of Bresenham's algorithm
	 */
	public static ArrayList<Point2D> linearInterpolate2D( Double x_current, Double x_last, Double y_current, Double y_last ) {
		ArrayList<Point2D> interpolatedPointList = new ArrayList<Point2D>();
		
		int error = 0;
		
		final int dx = (int) Math.abs( x_last - x_current );
        final int dx2 = (dx << 1);
		
        final int dy = (int) Math.abs( y_last - y_current );
        final int dy2 = (dy << 1);
 
        final int slopeDirectionX = x_current < x_last ? 1 : -1;
        final int slopeDirectionY = y_current < y_last ? 1 : -1;
        
        boolean firstRun = true;
 
        if (dy <= dx) {
            while (true) {
            	
                if ( x_current.equals(x_last) ) {
                	break;
                }
                    
                if ( firstRun == false ) {
                    interpolatedPointList.add( new Point2D(x_current, y_current) );
                }
                
                x_current += slopeDirectionX;
                error += dy2;
                if ( error > dx ) {
                	y_current += slopeDirectionY;
                    error -= dx2;
                }
                firstRun = false;
            }
        } else {
            while (true) {
            	
                if (y_current.equals(y_last) ) {
                	break;
                }
                if ( firstRun == false ) {
                    interpolatedPointList.add( new Point2D(x_current, y_current) );
                }
                
                y_current += slopeDirectionY;
                error += dx2;
                if ( error > dy ) {
                	x_current += slopeDirectionX;
                    error -= dy2;
                }
                firstRun = false;
            }
        }
		
		return interpolatedPointList;
	}
	
	/**
	 * See: https://www.ecse.rpi.edu/Homepages/wrf/Research/Short_Notes/pnpoly.html
	 * <br>
	 * This method is an adaptation of the "pnpoly" algorithm documented there
	 * 
	 * @param p_point
	 * @param p_polygonBoundary
	 * @return
	 */
	public static boolean containsPoint(Point2D p_point, ArrayList<Point2D> p_polygonBoundary) {
	      int i;
	      int j;
	      boolean result = false;
	      for (i = 0, j = p_polygonBoundary.size() - 1; i < p_polygonBoundary.size(); j = i++) {
	        if ((p_polygonBoundary.get(i).getY() > p_point.getY()) != (p_polygonBoundary.get(j).getY() > p_point.getY()) &&
	            (p_point.getX() < (p_polygonBoundary.get(j).getX() - p_polygonBoundary.get(i).getX()) * (p_point.getY() - p_polygonBoundary.get(i).getY()) / (p_polygonBoundary.get(j).getY() - p_polygonBoundary.get(i).getY()) + p_polygonBoundary.get(i).getX())) {
	          result = !result;
	         }
	      }
	      return result;
	}
}
