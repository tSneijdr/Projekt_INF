package datastructures;

public class Triple <U, V, W> {
	private final U u;
	private final V v;
	private final W w;
	
	public Triple(U u, V v, W w){
		this.u = u;
		this.v = v;
		this.w = w;
	}
	
	public U getFirst(){
		return u;
	}
	
	public V getSecond(){
		return v;
	}
	
	public W getThird(){
		return w;
	}

}
