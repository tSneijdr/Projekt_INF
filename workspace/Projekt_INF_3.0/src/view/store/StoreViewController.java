package view.store;

import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import model.points.Point;
import model.points.Record;
import model.points.Store;
import view.synthesis.InputController;

public class StoreViewController {
	@FXML
	private Accordion accordion;

	@FXML
	private BorderPane mainPane;

	public void fill(Store store) {
		if (store == null || accordion == null || store.getAllRecords().size() == 0)
			return;

		accordion.getPanes().clear();
		List<Record> records = store.getAllRecords();

		for (Record record : records) {
			if (accordion.getChildrenUnmodifiable().size() >= 100)
				break;

			String title = record.getURL();
			if (title.length() >= 20) {
				title = "..."
						+ record.getURL().substring(record.getURL().length() - 17, record.getURL().length());
			}
			{
				FlowPane pane = new FlowPane(Orientation.VERTICAL);
				pane.setColumnHalignment(HPos.LEFT);

				// Statische Daten
				{
					pane.getChildren().add(new Label(title));
					pane.getChildren().add(new Label("Teilnehmer: " + record.getParticipant()));
					pane.getChildren().add(new Label("Farbe: " + record.getColor().toString()));
					pane.getChildren().add(new Label("Erster Punkt: " + record.getFirstPoint().toString()));
					pane.getChildren().add(new Label((record.isActive()) ? "Aktiv" : "Nicht Aktiv"));
					pane.getChildren().add(new Label("Anzahl der Punkte: " + record.getAllPoints().size()));
				}

				// Button
				{
					Button button = new Button("Zeige alle Punkte");
					button.setOnAction((ActionEvent event) -> {
						FlowPane flow = new FlowPane(Orientation.VERTICAL);
						Point p = record.getFirstPoint();
						while (p != null) {
							flow.getChildren().add(new Label(p.toString()));
							p = p.getNextNode();
						}
						this.mainPane.setCenter(new ScrollPane(flow));

					});
					pane.getChildren().add(button);
				}

				TitledPane titledPane = new TitledPane(title, pane);
				Tooltip t = new Tooltip(record.getURL());
				Tooltip.install(titledPane, t);

				accordion.getPanes().add(titledPane);
			}
		}

	}

	public static void run(Store store) {
		FXMLLoader fxmlLoader = null;
		StoreViewController controller = null;
		Scene scene = null;

		try {
			fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(StoreViewController.class.getResource("StoreView.fxml"));

			scene = new Scene(fxmlLoader.load());
			controller = fxmlLoader.getController();
		} catch (IOException e) {
			System.err.println("Beim Laden des Popup-Menüs ist ein Fehler aufgetreten.");
			e.printStackTrace();
			System.exit(-1);
		}
		controller.fill(store);

		Stage stage = new Stage();
		stage.setTitle("Store");
		stage.setScene(scene);
		stage.setAlwaysOnTop(true);
		stage.show();
	}
}
