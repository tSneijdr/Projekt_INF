
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Graph;

public class MainApp extends Application {

	private Stage primaryStage;
	private AnchorPane rootLayout;

	Graph graph = new Graph();

	@Override
	public void start(Stage primaryStage) {
		BorderPane root = new BorderPane();

		{
			graph = new Graph();
			graph.random(10);
			root.setCenter(graph.getPane(500, 500, 1));
		}
		/*
		 * { double min = 1; double max = 100; double step = 1;
		 * 
		 * final Slider slider = new Slider(min, max, step);
		 * slider.setShowTickMarks(true); slider.setValue(1);
		 * 
		 * slider.valueProperty().addListener(new ChangeListener<Number>() {
		 * 
		 * @Override public void changed(ObservableValue<? extends Number>
		 * observableValue, Number oldValue, Number newValue) {
		 * root.setCenter(graph.getPane(500, 500, slider.getValue())); } });
		 * 
		 * root.setTop(slider);
		 * 
		 * // Bereite das zoomen per Mausrad vor { //root.getCenter().set
		 * 
		 * }
		 * 
		 * }
		 */

		Scene scene = new Scene(root, 600, 600);
		scene.setRoot(root);
		primaryStage.setScene(scene);

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
