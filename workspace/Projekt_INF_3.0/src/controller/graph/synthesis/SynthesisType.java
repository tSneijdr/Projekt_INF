package controller.graph.synthesis;

import controller.graph.synthesis.random.RandomSynthesis;
import controller.graph.synthesis.standard.StandardSynthesis;

public enum SynthesisType{
	RANDOM("Erzeugt eine zufälligen Graphen"),
	STANDARD("Erzeugt einen Graphen durch Rasterisierung");
	
	private final String description;
	
	private SynthesisType(String description){
		this.description = description;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public Synthesis getSynthesis(){
		switch(this){
		case RANDOM:
			return new RandomSynthesis();
		case STANDARD:
			return new StandardSynthesis();
		default:
			return new RandomSynthesis();
		}
	}
}
