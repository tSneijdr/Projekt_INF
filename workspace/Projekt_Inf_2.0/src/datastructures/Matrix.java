package datastructures;

import java.util.Set;

import core.Point;
import core.Record;
import utils.Range2D;

public class Matrix {
	public final Range2D IMAGE_RANGE;
	public final int NUMBER_OF_COLUMNS;
	public final int NUMBER_OF_ROWS;

	public final int SIZE_OF_ROW;
	public final int SIZE_OF_COLUMN;
	
	private final int[] values;

	public Matrix(Range2D range, int numberOfColumns, int numberOfRows) {
		IMAGE_RANGE = range;

		NUMBER_OF_COLUMNS = numberOfColumns;
		NUMBER_OF_ROWS = numberOfRows;

		SIZE_OF_ROW = (int) Math
				.ceil(((double) IMAGE_RANGE.HEIGHT / (double) NUMBER_OF_ROWS));
		SIZE_OF_COLUMN = (int) Math
				.ceil(((double) IMAGE_RANGE.WIDTH / (double) NUMBER_OF_COLUMNS));
		
		values = new int[NUMBER_OF_COLUMNS * NUMBER_OF_ROWS];
		for (int i = 0; i < values.length; i++){
			values[i] = 0;
		}
	}
	
	public int getNumberOfPointsAt(int x, int y){
		if (x < 0 || NUMBER_OF_COLUMNS <= x){
			throw new ArrayIndexOutOfBoundsException("Der x-Wert stimmt hier nicht.");
		}
		if (y < 0 || NUMBER_OF_ROWS <= y){
			throw new ArrayIndexOutOfBoundsException("Der y-Wert stimmt hier nicht.");
		}
		
		return values[y*NUMBER_OF_ROWS + x];
	}
	
	public void addPoint(Point p){
		int x = p.X / SIZE_OF_COLUMN;
		int y = p.Y / SIZE_OF_ROW;
		
		if (x < 0 || NUMBER_OF_COLUMNS <= x){
			throw new ArrayIndexOutOfBoundsException("Der x-Wert stimmt hier nicht.");
		}
		if (y < 0 || NUMBER_OF_ROWS <= y){
			throw new ArrayIndexOutOfBoundsException("Der y-Wert stimmt hier nicht.");
		}
		
		values[y*NUMBER_OF_ROWS + x]++;
	}
	
	public void addPoints(Set<Point> points){
		for (Point p : points){
			addPoint(p);
		}
	}
	
	public void addrecord(Record r){
		addPoints(r.getAllPoints());
	}
}