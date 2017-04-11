package utils.datastructures;

/**
 * Praktische Datenstruktur, stellt ein Quadrupel von beliebigen Werten dar
 * 
 * @author tobi
 *
 * @param <A>
 * @param <B>
 * @param <C>
 * @param <D>
 */
public class Quadrupel<A, B, C, D> {
	public final A FIRST;
	public final B SECOND;
	public final C THIRD;
	public final D FOURTH;

	public Quadrupel(A a, B b, C c, D d) {
		FIRST = a;
		SECOND = b;
		THIRD = c;
		FOURTH = d;
	}

	public boolean equals(Quadrupel<A, B, C, D> q) {
		return FIRST.equals(q.FIRST) && SECOND.equals(q.SECOND)
				&& THIRD.equals(q.THIRD) && FOURTH.equals(q.FOURTH);
	}

}
