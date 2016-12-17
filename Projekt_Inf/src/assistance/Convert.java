package assistance;

import datastructures.Pair;

public abstract class Convert {

	public static Pair<Double, Double>[] formatGraph(double[] list) {
		if (list.length % 2 != 0) {
			throw new IllegalArgumentException();
		}

		@SuppressWarnings("unchecked")
		Pair<Double, Double>[] res = new Pair[list.length/2];
		for (int i = 0; i < list.length / 2; i++) {
			Double x = list[i*2];
			Double y = list[i*2 + 1];

			Pair<Double, Double> p = new Pair<Double, Double>(x, y);

			res[i] = p;

		}
		return res;
	}
}
