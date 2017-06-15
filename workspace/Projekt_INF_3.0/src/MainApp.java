
import java.io.File;
import java.util.Map;

import controller.Controller;
import controller.graph.synthesis.SynthesisType;
import controller.points.DataReader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.points.Store;
import view.RootLayoutController;

/**
 * Hauptklasse, von hier werden Interfaces initialisiert und mit ihren
 * controllern verbunden
 * 
 * @author tobias meisel
 *
 */
public class MainApp extends Application {

	// Objekte für GUI
	private Stage primaryStage;
	private AnchorPane rootLayout;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage parameterPrimaryStage) {
		String pictureUrl = null;
		String sourceUrl = null;
		int numColumns = 10;
		int numRows = 10;
		Image img = null;

		{ // Einlesen der Parameter
			Parameters param = this.getParameters();

			{ // Validieren der Parameter

				try {
					Map<String, String> params = param.getNamed();

					System.out.println("   Parameter:");
					for (String s : params.keySet()) {
						System.out.println("      " + s + "=" + params.get(s));
					}

					if (params.size() != 4) {
						throw new Exception("There are " + params.size() + " arguments provided.");
					}

					pictureUrl = params.get("picture");
					sourceUrl = params.get("source");

					numColumns = Integer.parseInt(params.get("columns"));
					numRows = Integer.parseInt(params.get("rows"));

					File f;
					{
						f = new File(sourceUrl);
						if (!(f.exists() && f.isFile())) {
							throw new Exception();
						}

						f = new File(pictureUrl);
						if (!(f.exists() && f.isFile())) {
							throw new Exception();
						}

					}

					img = new Image(f.toURI().toString());
				} catch (Exception e) {
					System.err.println("An argument was invalid.");
					e.printStackTrace();
					System.exit(-1);
				}
			}
		}

		// Laden der Dateien
		final Store store; // Benutzt zum speichern von Datensätzen
		{
			System.out.println("Load data from file...");
			store = DataReader.loadStoreFromFile(sourceUrl).getURLStore(pictureUrl);
			System.out.println("Loading finished successfully...");
		}

		// Laden des Controllers
		final Controller controller;
		{
			System.out.println("Initialize Controller...");
			controller = new Controller(store, SynthesisType.STANDARD, img, numColumns, numRows);
			System.out.println("Loading of controller finished successfully...");
		}

		// Lade das StandardLayout
		final RootLayoutController rootController;
		{
			try {
				// Bereite Stage vor
				this.primaryStage = parameterPrimaryStage;
				this.primaryStage.setTitle("Graph");

				// Bereite rootLoader vor
				FXMLLoader rootLoader = new FXMLLoader();
				rootLoader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
				rootLayout = (AnchorPane) rootLoader.load();

				// Setze inhalt des Fensters
				Scene scene = new Scene(rootLayout);
				primaryStage.setScene(scene);
				primaryStage.setMaximized(true);
				primaryStage.show();

				// Verlange rootController
				rootController = rootLoader.getController();
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}

		// Weiteres Vorbereiten
		rootController.setUp(controller, store);

	}

	/**
	 * main, übergibt nur an JavaFX
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
	