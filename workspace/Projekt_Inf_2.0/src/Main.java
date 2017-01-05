import gui.MainFrame;
import gui.RecordDisplaySettings;
import gui.StandardGraphPanel;
import utils.Range2D;
import core.Point;
import core.Record;
import datastructures.Matrix;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Range2D r = new Range2D(0, 100, 0, 100);
		Matrix m = new Matrix(r, 10, 10);
		m.addPoint(new Point(1, 1, 1, 1));

		StandardGraphPanel p = new StandardGraphPanel(500, 500, m);

		MainFrame g = new MainFrame("Test");
		g.add(p);
		// ---------------
		Record rec = new Record("Test", null);
		RecordDisplaySettings s = new RecordDisplaySettings(rec);
		g.SELECTION.addRecordDisplaySettings(s);
		g.SELECTION.addRecordDisplaySettings(s);
		g.SELECTION.addRecordDisplaySettings(s);
		g.SELECTION.addRecordDisplaySettings(s);
		g.SELECTION.addRecordDisplaySettings(s);
		g.SELECTION.addRecordDisplaySettings(s);
		g.SELECTION.addRecordDisplaySettings(s);
		g.SELECTION.addRecordDisplaySettings(s);
		g.SELECTION.addRecordDisplaySettings(s);
		g.SELECTION.addRecordDisplaySettings(s);
		g.SELECTION.addRecordDisplaySettings(s);
		g.SELECTION.addRecordDisplaySettings(s);
		g.SELECTION.addRecordDisplaySettings(s);
		g.SELECTION.addRecordDisplaySettings(s);
		g.SELECTION.addRecordDisplaySettings(s);
		g.SELECTION.addRecordDisplaySettings(s);
		g.SELECTION.addRecordDisplaySettings(s);
		g.SELECTION.addRecordDisplaySettings(s);
		g.SELECTION.addRecordDisplaySettings(s);
		g.SELECTION.addRecordDisplaySettings(s);
		g.SELECTION.addRecordDisplaySettings(s);
		g.SELECTION.addRecordDisplaySettings(s);
		g.SELECTION.addRecordDisplaySettings(s);
		g.SELECTION.addRecordDisplaySettings(s);
		// ---------------
		g.display();
	}

}
