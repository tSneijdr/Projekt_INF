import gui.MainFrame;
import gui.RecordDisplaySettings;
import utils.Range2D;
import core.Record;
import core_gui.StandardGraphPanel;
import datastructures.Matrix;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Range2D r = new Range2D(0, 100, 0, 100);
		Matrix m = new Matrix(r, 10, 10);

		StandardGraphPanel p = new StandardGraphPanel(500, 500, m);

		MainFrame g = new MainFrame("Test");
		g.add(p);
		// ---------------
		Record rec = new Record("Test", null);
		RecordDisplaySettings s = new RecordDisplaySettings(rec);
		s = new RecordDisplaySettings(rec);
		g.SELECTION.addRecordDisplaySettings(s);
		s = new RecordDisplaySettings(rec);
		g.SELECTION.addRecordDisplaySettings(s);
		s = new RecordDisplaySettings(rec);
		g.SELECTION.addRecordDisplaySettings(s);
		s = new RecordDisplaySettings(rec);
		g.SELECTION.addRecordDisplaySettings(s);
		s = new RecordDisplaySettings(rec);
		g.SELECTION.addRecordDisplaySettings(s);
		s = new RecordDisplaySettings(rec);
		g.SELECTION.addRecordDisplaySettings(s);
		s = new RecordDisplaySettings(rec);
		g.SELECTION.addRecordDisplaySettings(s);
		s = new RecordDisplaySettings(rec);
		g.SELECTION.addRecordDisplaySettings(s);
		s = new RecordDisplaySettings(rec);
		g.SELECTION.addRecordDisplaySettings(s);
		s = new RecordDisplaySettings(rec);
		g.SELECTION.addRecordDisplaySettings(s);
		s = new RecordDisplaySettings(rec);
		g.SELECTION.addRecordDisplaySettings(s);
		s = new RecordDisplaySettings(rec);
		g.SELECTION.addRecordDisplaySettings(s);
		s = new RecordDisplaySettings(rec);
		g.SELECTION.addRecordDisplaySettings(s);
		s = new RecordDisplaySettings(rec);
		g.SELECTION.addRecordDisplaySettings(s);
		s = new RecordDisplaySettings(rec);
		g.SELECTION.addRecordDisplaySettings(s);
		s = new RecordDisplaySettings(rec);
		g.SELECTION.addRecordDisplaySettings(s);
		s = new RecordDisplaySettings(rec);
		g.SELECTION.addRecordDisplaySettings(s);
		s = new RecordDisplaySettings(rec);
		g.SELECTION.addRecordDisplaySettings(s);
		s = new RecordDisplaySettings(rec);
		g.SELECTION.addRecordDisplaySettings(s);
		s = new RecordDisplaySettings(rec);
		g.SELECTION.addRecordDisplaySettings(s);
		g.SELECTION.addRecordDisplaySettings(s);
		g.SELECTION.addRecordDisplaySettings(s);
		g.SELECTION.addRecordDisplaySettings(s);
		g.SELECTION.addRecordDisplaySettings(s);
		// ---------------
		g.display();
	}

}
