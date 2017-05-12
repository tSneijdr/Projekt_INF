package view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.Controller;
import controller.graph.transformation.TransformationType;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import model.points.Record;
import model.points.Store;

public class RootLayoutController {
	@FXML
	private Accordion accordion;
	@FXML
	private AnchorPane upperLeftPane;
	@FXML
	private AnchorPane lowerLeftPane;
	@FXML
	private AnchorPane upperRightPane;
	@FXML
	private AnchorPane lowerRightPane;
	@FXML
	private Menu ulMenu, urMenu, llMenu, lrMenu;
	@FXML
	private MenuItem showStore;

	// Kontroll - Daten
	private Controller controller;
	private Map<Pane, Pane> panePaneMap;

	// Hilfs -Datenstrukt
	private Pane[] panes;

	public void initialize() {
		panePaneMap = new HashMap<Pane, Pane>();
		panes = new Pane[] { upperLeftPane, upperRightPane, lowerLeftPane, lowerRightPane };

		// ----------------------------------------------------------------------------------
		// Menus
		{
			Menu[] menus = { ulMenu, urMenu, llMenu, lrMenu };

			// Setzt die Untermenüs und deren Funktion
			for (int i = 0; i < menus.length; i++) {
				for (TransformationType trans : TransformationType.values()) {
					MenuItem item = new MenuItem(trans.toString());
					Integer index = new Integer(i);

					item.setOnAction(new EventHandler<ActionEvent>() {
						final int j = index.intValue();

						@Override
						public void handle(ActionEvent event) {
							setPaneContent(j, trans);
						}

					});
					menus[i].getItems().add(item);
				}
			}
		}

		// ----------------------------------------------------------------------------------
		// Store
		{
			showStore.setOnAction((ActionEvent event) -> {
				// StoreViewController.run(store);
			});
		}

		// ----------------------------------------------------------------------------------
		// Panes
		{
			for (Pane p : panes) {
				addActionListeners(p);
			}
		}

	}

	private void addActionListeners(Pane pa) {
		final Double moveSpeed = 10.0;
		final Double zoomSpeed = 0.001;

		// Zoomevent setzen
		pa.setOnScroll((ScrollEvent event) -> {
			Pane pane = panePaneMap.get(pa);

			final double deltaFactor = event.getDeltaY() * zoomSpeed;
			final double oldScale = pane.getScaleX();

			final double minScale = Math.min(pane.getWidth() / pane.getScaleX() / pa.getWidth(),
					pane.getHeight() / pane.getScaleX() / pa.getHeight());
			final double newScale = ((oldScale + deltaFactor) < minScale) ? minScale : oldScale + deltaFactor;

			final double x = event.getSceneX();
			final double y = event.getSceneY();

			double vecX = x * (newScale / oldScale) - x;
			double vecY = y * (newScale / oldScale) - y;

			pane.setScaleX(newScale);
			pane.setScaleY(newScale);

			DoubleProperty xProp = pane.translateXProperty();
			DoubleProperty yProp = pane.translateXProperty();

			xProp.set(xProp.get() * (newScale / oldScale) - vecX);
			yProp.set(yProp.get() * (newScale / oldScale) - vecY);

			event.consume();
		});

		// ActionListener für alle Tastendrücke
		// pa.setOnKeyPressed((KeyEvent event) -> {
		// TODO: Fehler hier
		/*
		 * // Funktionalität für Bewegung in Pane final Pane pane =
		 * panePaneMap.get(pa);
		 * 
		 * final DoubleProperty propX = pane.translateXProperty(); final
		 * DoubleProperty propY = pane.translateYProperty();
		 * 
		 * if (event.isControlDown()) { pane.translateXProperty().set(0);
		 * pane.translateYProperty().set(0);
		 * pane.setScaleX(Math.min(pane.getWidth() / pa.getWidth(),
		 * pane.getHeight() / pa.getHeight()));
		 * pane.setScaleY(Math.min(pane.getWidth() / pa.getWidth(),
		 * pane.getHeight() / pa.getHeight()));
		 * 
		 * System.out.println(pane); System.out.println(event.getSource()); }
		 * else { if (event.getCode() == KeyCode.LEFT) { propX.set(propX.get() +
		 * moveSpeed); } else if (event.getCode() == KeyCode.RIGHT) {
		 * propX.set(propX.get() - moveSpeed); } else if (event.getCode() ==
		 * KeyCode.UP) { propY.set(propY.get() + moveSpeed); } else if
		 * (event.getCode() == KeyCode.DOWN) { propY.set(propY.get() -
		 * moveSpeed); } }
		 * 
		 * event.consume();
		 */
		// });

		// ActionListener für alle Mausdrücke
		MousePoint point = new MousePoint(0.0, 0.0);

		pa.setOnMousePressed((MouseEvent event) -> {
			// Updatet den oldXund oldY Wert notwendig für dragging
			point.setX(event.getSceneX());
			point.setY(event.getSceneY());

			event.consume();

		});

		pa.setOnMouseReleased((MouseEvent event) -> {
			// Maus-Dragging Funktionen
			final Pane pane = panePaneMap.get(pa);

			if (event.isControlDown()) {
				pane.translateXProperty().set(0);
				pane.translateYProperty().set(0);
				pane.setScaleX(Math.min(pane.getWidth() / pa.getWidth(), pane.getHeight() / pa.getHeight()));
				pane.setScaleY(Math.min(pane.getWidth() / pa.getWidth(), pane.getHeight() / pa.getHeight()));
			}
			// event.get
			final double newX = event.getSceneX();
			final double newY = event.getSceneY();
			final double oldX = point.getX();
			final double oldY = point.getY();

			if (!(oldX == 0.0 && oldY == 0.0)) {
				DoubleProperty propX = pane.translateXProperty();
				DoubleProperty propY = pane.translateYProperty();

				Double diffX = newX - oldX;
				Double diffY = newY - oldY;

				double cutOffRange = 100;
				if (cutOffRange >= Math.sqrt(Math.pow(diffY, 2.0) + Math.pow(diffX, 2.0))) {
					propX.set(propX.get() + diffX);
					propY.set(propY.get() + diffY);

				}

				point.setX(newX);
				point.setY(newY);
			}

			event.consume();
		});

		// Mousddrag
		pa.setOnMouseDragged((MouseEvent event) -> {
			// Maus-Dragging Funktionen
			final Pane pane = panePaneMap.get(pa);

			// event.get
			final double newX = event.getSceneX();
			final double newY = event.getSceneY();
			final double oldX = point.getX();
			final double oldY = point.getY();

			if (!(oldX == 0.0 && oldY == 0.0)) {
				DoubleProperty propX = pane.translateXProperty();
				DoubleProperty propY = pane.translateYProperty();

				Double diffX = newX - oldX;
				Double diffY = newY - oldY;

				double cutOffRange = 100;
				if (cutOffRange >= Math.sqrt(Math.pow(diffY, 2.0) + Math.pow(diffX, 2.0))) {
					propX.set(propX.get() + diffX);
					propY.set(propY.get() + diffY);
				}

				point.setX(newX);
				point.setY(newY);
			}

			event.consume();
		});

	}

	public void setUp(Controller controller, Store store) {
		this.controller = controller;

		// Fülle Akkordeon
		{
			if (store == null || accordion == null || store.getAllRecords().size() == 0)
				return;

			accordion.getPanes().clear();
			List<Record> records = store.getAllRecords();

			for (Record record : records) {
				if (accordion.getChildrenUnmodifiable().size() >= 100)
					break;

				String title = record.getURL();
				if (title.length() >= 20) {
					title = "..." + record.getURL().substring(record.getURL().length() - 17, record.getURL().length());
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
					Tooltip t = new Tooltip(record.getURL());
					Tooltip.install(titledPane, t);

					accordion.getPanes().add(titledPane);
				}
			}
		}

		setPaneContent(0, TransformationType.ORIGINAL);
		setPaneContent(1, TransformationType.CIRCULAR);
		setPaneContent(2, TransformationType.FORCEDIRECTED);
		setPaneContent(3, TransformationType.HIERARCHICAL);

	}

	private void setPaneContent(int pane, TransformationType trans) {
		if (!(0 <= pane && pane < panes.length)) {
			System.exit(-1);
			return;
		}

		final int width = (int) panes[pane].getWidth();
		final int height = (int) panes[pane].getHeight();

		Pane content = controller.generatePane(width, height, trans);

		panes[pane].getChildren().clear();
		panes[pane].getChildren().add(content);

		if (panePaneMap.containsKey(panes[pane])) {
			panePaneMap.remove(panes[pane]);
		}
		panePaneMap.put(panes[pane], content);
	}

	protected class MousePoint {
		private double x;
		private double y;

		public MousePoint(double x, double y) {
			this.x = x;
			this.y = y;
		}

		// -------------------------------------------------
		// Getter und Setter -------------------------------
		// -------------------------------------------------
		public double getX() {
			return x;
		}

		public double getY() {
			return y;
		}

		public void setX(double x) {
			this.x = x;
		}

		public void setY(double y) {
			this.y = y;
		}
	}
}
