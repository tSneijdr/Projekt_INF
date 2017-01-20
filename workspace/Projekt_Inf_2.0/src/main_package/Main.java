package main_package;

import java.util.HashSet;

import javax.swing.JPanel;

import gui.MainFrame;
import utils.Range2D;
import core.Mode;
import core.Record;
import core.RecordDisplaySettings;
import core_gui.StandardGraphPanel;
import datastructures.Matrix;

public class Main {

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		// TODO Auto-generated method stub
		Range2D r = new Range2D(0, 100, 0, 100);
		Matrix m = new Matrix(r, 10, 10);

		StandardGraphPanel p = new StandardGraphPanel(500, 500, m);

		HashSet<RecordDisplaySettings> set = new HashSet<RecordDisplaySettings>();
		
		// g.add(p);
		// ---------------
		Record rec = new Record("Test", null);
		RecordDisplaySettings s = new RecordDisplaySettings(rec);
		s = new RecordDisplaySettings(rec);
		set.add(s);
		MainFrame g = new MainFrame(this, "Test", set);
		// ---------------
		g.display();
	}
	
	public JPanel getContent(Mode mode){
		Range2D r = new Range2D(0, 100, 0, 100);
		Matrix m = new Matrix(r, 10, 10);

		StandardGraphPanel p = new StandardGraphPanel(500, 500, m);
		return p;
	}

}
