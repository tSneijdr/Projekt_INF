package core;

public class Matrix {
	public final int MAX_X;
	public final int MAX_Y;
	public final int CELLS;

	private final int[] VALUES;

	// ----------------------------------------------------
	// Konstruktoren --------------------------------------
	// ----------------------------------------------------
	public Matrix(int maxX, int maxY) {
		MAX_X = maxX;
		MAX_Y = maxY;

		CELLS = MAX_X * MAX_Y;

		VALUES = new int[MAX_X * MAX_Y];
		for (int i = 0; i < VALUES.length; i++) {
			VALUES[i] = 0;
		}
	}

	// ----------------------------------------------------
	// Zugriffsmethoden -----------------------------------
	// ----------------------------------------------------
	public void set(int x, int y, int value) {
		if (0 <= x && MAX_X > x && 0 <= y && y < MAX_Y) {
			VALUES[y * MAX_X + x] = value;
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	public void increment(int x, int y) {
		if (0 <= x && MAX_X > x && 0 <= y && y < MAX_Y) {
			VALUES[y * MAX_X + x]++;
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	public int get(int x, int y) {
		if (0 <= x && MAX_X > x && 0 <= y && y < MAX_Y) {
			return VALUES[y * MAX_X + x];
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	// ----------------------------------------------------
	// Matrixoperationen ----------------------------------
	// ----------------------------------------------------
	public static Matrix add(Matrix m1, Matrix m2) {
		if (!(m1.MAX_X == m2.MAX_X && m1.MAX_Y == m2.MAX_Y)) {
			throw new IllegalArgumentException(
					"Die Daten der Matrizen stimmen nicht überein");
		}

		Matrix m = new Matrix(m1.MAX_Y, m2.MAX_X);

		for (int i = 0; i < m.CELLS; i++) {
			m.VALUES[i] = m1.VALUES[i] + m2.VALUES[i];
		}
		return m;
	}

	public static Matrix multiply(Matrix m1, Matrix m2) {
		if (!(m1.MAX_Y == m2.MAX_X)) {
			throw new IllegalArgumentException(
					"Die Matrizen können nicht multipliziert werden.");
		}

		Matrix m = new Matrix(m1.MAX_Y, m2.MAX_X);

		for (int x = 0; x < m.MAX_X; x++) {
			for (int y = 0; y < m.MAX_Y; y++) {
				int res = 0;
				for (int i = 0; i < m1.MAX_Y; i++) {
					res += m1.get(i, y) * m2.get(x, m.MAX_Y - i - 1);
				}
				m.set(x, y, res);

			}
		}
		return m;

	}
}
