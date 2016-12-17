package datastructures;

public class Pair<U, V> {
	private final U u;
	private final V v;
	
	public Pair(U u, V v){
		this.u = u;
		this.v = v;
	}
	
	public U getFirst(){
		return u;
	}
	
	public V getSecond(){
		return v;
	}
}
