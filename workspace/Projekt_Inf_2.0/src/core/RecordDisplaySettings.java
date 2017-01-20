package core;

import java.awt.Color;

/**
 * Speichert Einstellungen zur Darstellung von Records
 * 
 * @author tobi
 *
 */
public class RecordDisplaySettings {
	public final Record RECORD;
	public boolean active = true;
	public Color color = Color.BLACK;

	public RecordDisplaySettings(Record record) {
		RECORD = record;
	}
}