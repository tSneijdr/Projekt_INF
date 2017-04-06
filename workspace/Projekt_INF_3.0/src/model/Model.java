package model;

import model.structure.Active;
import model.structure.Store;
import utils.Range3D;

public class Model {
	private final Active active;
	private final Store loaded;
	
	/**
	 * Spezifiziert zusätzlich zum einfacheren Konstruktor noch die maximale
	 * Anzahl von Punkten pro Quadtree-/Octree-Knoten
	 * 
	 * @param range
	 * @param n
	 */
	public Model(Range3D range) {
		
		loaded = new Store();
		active = new Active();
		
		active.setRange(range);
	}
	
	public Active getActive(){
		return active;
	}
	
	public Store getStore(){
		return loaded;
	}
}
