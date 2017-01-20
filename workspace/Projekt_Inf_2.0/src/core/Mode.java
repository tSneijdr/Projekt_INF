package core;

/**
 * Enum das die verschiedenen Modi zur Darstellung von Datensätzen speichert
 * @author tobi
 *
 */
public enum Mode {
	Standard("Standard"),
	TemporalX("Auslenkung in X."),
	TemporalY("Auslenkung in Y.");

	private final String description;

	Mode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}