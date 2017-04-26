package view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.Controller;
import controller.graph.transformation.TransformationType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import model.points.Record;
import model.points.Store;
import view.store.StoreViewController;

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
	private Menu ulMenu, urMenu, llMenu, lrMenu;
	@FXML
	private MenuItem showStore;

	// Kontroll - Daten
	private Controller controller;
	private Store store;
	
	public void initialize() {
		// Menus
		{
			// Pane Setzer
			{
				Menu[] menus = { ulMenu, urMenu, llMenu, lrMenu };
				Map<MenuItem, BorderPane> map = new HashMap<MenuItem, BorderPane>();
				map.put(ulMenu, upperLeftPane);
				map.put(urMenu, upperRightPane);
				map.put(llMenu, lowerLeftPane);
				map.put(lrMenu, lowerRightPane);

				// Setzt die Untermenüs und deren Funktion
				for (Menu menu : menus) {
					for (TransformationType trans : TransformationType.values()) {
						MenuItem item = new MenuItem(trans.toString());
						menu.getItems().add(item);

						item.setOnAction((ActionEvent e) -> {
							BorderPane pane = map.get(item.getParentMenu());
							int width = (int) pane.getWidth();
							int height = (int) pane.getHeight();
							
							BorderPane newPane = controller.generatePane(width, height, trans);
							System.out.println("   Setze Pane " + pane + " auf " + newPane);
							
							pane.setCenter(newPane);

						});
					}
				}
			}

			// Store
			{
				showStore.setOnAction((ActionEvent event) -> {
					StoreViewController.run(store);
				});
			}
		}

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

	// ------------------------------------------------------------------
	// Getter und Setter
	// ------------------------------------------------------------------
	public void setUp(Controller controller, Store store) {
		this.controller = controller;
		this.store = store;
	}
}
