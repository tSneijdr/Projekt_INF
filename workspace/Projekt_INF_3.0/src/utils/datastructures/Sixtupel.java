package utils.datastructures;

/**
 * Praktische Datenstruktur, stellt ein F�nftupel von beliebigen Werten dar
 *
 * @author tobi
 *
 * @param <A>
 * @param <B>
 * @param <C>
 * @param <D>
 * @param <E>
 * @param <F>
 */
public class Sixtupel<A, B, C, D, E, F> {
	public final A FIRST;
	public final B SECOND;
	public final C THIRD;
	public final D FOURTH;
	public final E FIFTH;
	public final F SIXTH;

	public Sixtupel(A a, B b, C c, D d, E e, F f) {
		FIRST = a;
		SECOND = b;
		THIRD = c;
		FOURTH = d;
		FIFTH = e;
		SIXTH = f;
	}

	public boolean equals(Sixtupel<A, B, C, D, E, F> q) {
		return FIRST.equals(q.FIRST) && SECOND.equals(q.SECOND)
				&& THIRD.equals(q.THIRD) && FOURTH.equals(q.FOURTH)
				&& FIFTH.equals(q.FIFTH) && SIXTH.equals(q.SIXTH);
	}

}
