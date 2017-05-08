
import controller.Controller;
import controller.points.DataReader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.points.Store;
import view.RootLayoutController;
import view.synthesis.InputController;

public class MainApp extends Application {

	// Objekte für GUI
	private Stage primaryStage;
	private AnchorPane rootLayout;

	// Objekte für Logik
	private Store store;

	@Override
	public void start(Stage parameterPrimaryStage) {

		// Setup
		this.primaryStage = parameterPrimaryStage;
		this.primaryStage.setTitle("Graphvisualisierung");

		// Starten des Abfragebildschirms
		InputController con;
		{
			con = InputController.run();
		}

		// Laden der Dateien
		{
			System.out.println("Lade die Daten aus der Datei...");
			this.store = DataReader.loadStoreFromFile("all_data_small.txt");
			this.store = this.store.getURLStore(con.getURL());
			System.out.println("Laden aus Datei abgeschlossen...");
		}

		// Laden des Controllers
		Controller controller;
		{
			System.out.println("Initialisiere den Controller...");
			controller = new Controller(store, con);
			System.out.println("Initialisieren des Kontrollers abgeschlossen...");
		}

		// Lade das StandardLayout
		RootLayoutController rootController;
		{
			try {
				// Bereite rootLoader vor
				FXMLLoader rootLoader = new FXMLLoader();
				rootLoader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
				rootLayout = (AnchorPane) rootLoader.load();

				// Setze inhalt des Fensters
				Scene scene = new Scene(rootLayout);
				primaryStage.setScene(scene);
				primaryStage.show();

				// Verlange rootController
				rootController = rootLoader.getController();
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}

		// Weiteres Vorbereiten
		{
			rootController.setUp(controller, store);
			rootController.setUpAccordion(store);
		}

	}

	public static void main(String[] args) {
		launch(args);
	}
}
