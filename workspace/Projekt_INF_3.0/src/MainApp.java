
import controller.Controller;
import controller.graph.synthesis.SynthesisType;
import controller.points.DataReader;
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

		// Setup
		this.primaryStage = parameterPrimaryStage;
		this.primaryStage.setTitle("Graphvisualisierung");

		// Laden der Dateien
		System.out.println("Lade die Daten aus der Datei...");
		this.store = DataReader.loadStoreFromFile("all_data_small.txt");
		System.out.println("Laden aus Datei abgeschlossen...");

		// Laden des Controllers
		System.out.println("Initialisiere den Controller...");
		Controller controller = new Controller(store);
		System.out.println("Initialisieren des Kontrollers abgeschlossen...");

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
			System.out.println("Setze " + store.getAllRecords().size() + " Einträge in Akkordeon...");
			rootController.setUpAccordion(store);
			System.out.println("Akkordeon gesetzt...");
		}
		
		// Verlinken der beiden Controller
		{
			rootController.setUp(controller, store);
		}

	}

	public static void main(String[] args) {
		launch(args);
	}
}
