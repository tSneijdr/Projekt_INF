package view;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
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
		ulButton.setOnAction((ActionEvent event) -> {
			BorderPane p = upperLeftPane;
			p.setCenter(Controller.generatePane((int) p.getWidth(), (int) p.getHeight(), null));
			
			System.out.println(p.getHeight());
			System.out.println(p.heightProperty().get());
		});

		urButton.setOnAction((ActionEvent event) -> {
			BorderPane p = upperRightPane;
			p.setCenter(Controller.generatePane((int) p.getWidth(), (int) p.getHeight(), null));
			
		});

		llButton.setOnAction((ActionEvent event) -> {
			BorderPane p = lowerLeftPane;
			p.setCenter(Controller.generatePane((int) p.getWidth(), (int) p.getHeight(), null));
		});

		lrButton.setOnAction((ActionEvent event) -> {
			BorderPane p = lowerRightPane;
			p.setCenter(Controller.generatePane((int) p.getWidth(), (int) p.getHeight(), null));
		});

	}

	public void setUpAccordion(Store store) {
		if (store == null || accordion == null)
			return;

		accordion.getPanes().clear();
		Record[] records = store.getActiveRecords();

		for (Record record : records) {
			FlowPane pane = new FlowPane();

			pane.getChildren().add(new Label(record.getTitle()));
			pane.getChildren().add(new Label(record.getParticipant()));
			pane.getChildren().add(new Label(record.getColor().toString()));
			pane.getChildren().add(new Label((record.isActive()) ? "Aktiv" : "Nicht Aktiv"));

			TitledPane titledPane = new TitledPane(record.getTitle(), pane);
			accordion.getPanes().add(titledPane);
		}

	}
}
