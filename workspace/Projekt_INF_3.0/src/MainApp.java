
import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.points.Store;
import view.RootLayoutController;

public class MainApp extends Application {

	// Objekte für GUI
	private Stage primaryStage;
	private AnchorPane rootLayout;

	// Objekte für Logik
	private Store store;

	@Override
	public void start(Stage parameterPrimaryStage) {
		this.primaryStage = parameterPrimaryStage;
		this.primaryStage.setTitle("Graphvisualisierung");
		System.out.println("Lade die Daten aus der Datei...");
		this.store = Controller.loadStoreFromFile("all_data_small.txt");
		System.out.println("Laden aus Datei abgeschlossen...");

		FXMLLoader rootLoader;
		RootLayoutController rootController;

		// Lade das StandardLayout
		{
			try {
				// Setup rootLoader
				rootLoader = new FXMLLoader();
				rootLoader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
				rootLayout = (AnchorPane) rootLoader.load();

				// Setup content of root window
				Scene scene = new Scene(rootLayout);
				primaryStage.setScene(scene);
				primaryStage.show();

				// Setup rootController
				rootController = rootLoader.getController();

			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		// Setze die Logik
		{
			System.out.println("Setze Funtionen der Knöpfe...");
			rootController.setUpButtons(store, false);
			System.out.println("Funktionen der Knöpfe gesetzt...");
			System.out.println("Setze " + store.getAllRecords().size() + " Einträge in Akkordeon...");
			rootController.setUpAccordion(store);
			System.out.println("Akkordeon gesetzt...");
		}

	}

	public static void main(String[] args) {
		launch(args);
	}
}
