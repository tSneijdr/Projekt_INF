

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Model;
import utils.Range3D;

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
				
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		
	}

	public static void main(String[] args) {
		Range3D range = new Range3D(0, 10, 0, 10, 0, 10);
		Model m = new Model(range);
		
		
		launch(args);
	}
}
