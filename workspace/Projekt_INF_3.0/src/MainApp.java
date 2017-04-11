

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.RootLayoutController;

public class MainApp extends Application {

	private Stage primaryStage;
	private AnchorPane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		
		this.primaryStage.setTitle("Graphvisualisierung");
		
		// Lade das StandardLayout
		{
			try{
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
				rootLayout = (AnchorPane) loader.load();
				
				Scene scene = new Scene(rootLayout);
				primaryStage.setScene(scene);
				primaryStage.show();
				
				RootLayoutController test = loader.getController();
				test.setUpAccordion(null);
				
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
