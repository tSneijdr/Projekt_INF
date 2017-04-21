package view;

import java.util.List;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import model.points.Record;
import model.points.Store;

public class RootLayoutController {
	@FXML
	private Accordion accordion;
	@FXML
	private BorderPane upperLeftPane;
	@FXML
	private BorderPane lowerLeftPane;
	@FXML
	private BorderPane upperRightPane;
	@FXML
	private BorderPane lowerRightPane;

	@FXML
	private MenuItem ulButton, urButton, llButton, lrButton;

	public void initialize() {

	}

	public void setUpButtons(Controller controller, boolean showEdges) {
		ulButton.setOnAction((ActionEvent event) -> {
			BorderPane p = upperLeftPane;
			p.setCenter(controller.generatePane((int) p.getWidth(), (int) p.getHeight(), showEdges));
		});

		urButton.setOnAction((ActionEvent event) -> {
			BorderPane p = upperRightPane;
			p.setCenter(controller.generatePane((int) p.getWidth(), (int) p.getHeight(), showEdges));

		});

		llButton.setOnAction((ActionEvent event) -> {
			BorderPane p = lowerLeftPane;
			p.setCenter(controller.generatePane((int) p.getWidth(), (int) p.getHeight(), showEdges));
		});

		lrButton.setOnAction((ActionEvent event) -> {
			BorderPane p = lowerRightPane;
			p.setCenter(controller.generatePane((int) p.getWidth(), (int) p.getHeight(), showEdges));
		});

	}

	public void actualizePanels() {
		//BorderPane p = lowerLeftPane;
		// p.setCenter
	}

	public void setUpAccordion(Store store) {
		if (store == null || accordion == null || store.getAllRecords().size() == 0)
			return;

		accordion.getPanes().clear();
		List<Record> records = store.getActiveRecords();

		for (Record record : records) {
			if (accordion.getChildrenUnmodifiable().size() >= 100)
				break;

			String title = record.getTitle();
			if (title.length() >= 20) {
				title = "..."
						+ record.getTitle().substring(record.getTitle().length() - 17, record.getTitle().length());
			}
			{
				FlowPane pane = new FlowPane(Orientation.VERTICAL);
				pane.setColumnHalignment(HPos.LEFT);

				pane.getChildren().add(new Label(title));
				pane.getChildren().add(new Label("Teilnehmer: " + record.getParticipant()));
				pane.getChildren().add(new Label("Farbe: " + record.getColor().toString()));
				pane.getChildren().add(new Label("Erster Punkt: " + record.getFirstPoint().toString()));
				pane.getChildren().add(new Label((record.isActive()) ? "Aktiv" : "Nicht Aktiv"));

				TitledPane titledPane = new TitledPane(title, pane);
				Tooltip t = new Tooltip(record.getTitle());
				Tooltip.install(titledPane, t);

				accordion.getPanes().add(titledPane);
			}
		}

	}
}
