package outdated;

public class Range4D {
	public final int MIN_X;
	public final int MIN_Y;
	public final int MIN_TIME;
	public final int MAX_X;
	public final int MAX_Y;
	public final int MAX_TIME;

	public Range4D(int minX, int minY, int minTime, int maxX, int maxY,
			int maxTime) {
		MIN_X = minX;
		MIN_Y = minY;
		MIN_TIME = minTime;

		MAX_X = maxX;
		MAX_Y = maxY;
		MAX_TIME = maxTime;
	}
}
