package view.synthesis;

import java.io.IOException;

import controller.graph.synthesis.SynthesisType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utils.ranges.Range2D;

public class InputController {

	@FXML
	private Button exitBtn;
	@FXML
	private TextField heightTxtFld;
	@FXML
	private TextField widthTxtFld;
	@FXML
	private ChoiceBox<String> synthesisChoice;
	@FXML
	private TextField additionalInfoTxtFld1;
	@FXML
	private TextField additionalInfoTxtFld2;
	@FXML
	private Label infoLbl;

	private SynthesisType type = SynthesisType.STANDARD;
	private Range2D range = new Range2D(0, 1000, 0, 1000);
	private int numberOfColumns = 10;
	private int numberOfRows = 10;

	public void initialize() {
		// Choice Box
		{
			for (SynthesisType synthType : SynthesisType.values()) {
				synthesisChoice.getItems().add(synthType.toString());
			}

			synthesisChoice.setOnAction((ActionEvent event) -> {
				infoLbl.setText(
						SynthesisType.valueOf(synthesisChoice.getSelectionModel().getSelectedItem()).getDescription());
				if (synthesisChoice.getSelectionModel().getSelectedItem().equals(SynthesisType.STANDARD.toString())) {
					this.additionalInfoTxtFld1.setText("Anzahl der Spalten");
					this.additionalInfoTxtFld2.setText("Anzahl der Reihen");
					this.additionalInfoTxtFld1.setEditable(true);
					this.additionalInfoTxtFld2.setEditable(true);
				} else {
					this.additionalInfoTxtFld1.setEditable(false);
					this.additionalInfoTxtFld2.setEditable(false);
					this.additionalInfoTxtFld1.setText("-");
					this.additionalInfoTxtFld2.setText("-");
				}
			});
		}

		// Zusätzliche Textfelder
		{
			this.additionalInfoTxtFld1.setEditable(false);
			this.additionalInfoTxtFld2.setEditable(false);
			this.additionalInfoTxtFld1.setText("-");
			this.additionalInfoTxtFld2.setText("-");
		}

		// Info Label
		{
			infoLbl.setText("");
		}

		// Exit Button
		{
			exitBtn.setOnAction((ActionEvent event) -> {
				try {
					int height = Integer.valueOf(this.heightTxtFld.getText());
					int width = Integer.valueOf(this.widthTxtFld.getText());

					if (synthesisChoice.getSelectionModel().getSelectedItem()
							.equals(SynthesisType.STANDARD.toString())) {
						this.numberOfColumns = Integer.valueOf(this.additionalInfoTxtFld1.getText());
						this.numberOfRows = Integer.valueOf(this.additionalInfoTxtFld2.getText());
					}
					this.type = SynthesisType.valueOf(this.synthesisChoice.getSelectionModel().getSelectedItem());
					this.range = new Range2D(0, width, 0, height);
				} catch (Exception e) {
					System.err.println("Es ist ein Fehler aufgtetreten, das Programm wird beendet");
					e.printStackTrace();
					System.exit(-1);
				}

				// Normales Beenden des Fensters
				Stage stage = (Stage) exitBtn.getScene().getWindow();
				stage.close();
			});
		}
	}

	// ------------------------------------------------------------------------
	// Getter und Setter
	// ------------------------------------------------------------------------
	public SynthesisType getType() {
		return type;
	}

	public Range2D getRange() {
		return range;
	}

	public int getNumberOfColumns() {
		return numberOfColumns;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public static InputController run() {
		FXMLLoader fxmlLoader = null;
		InputController controller = null;
		Scene scene = null;

		try {
			fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(InputController.class.getResource("Input.fxml"));

			scene = new Scene(fxmlLoader.load());
			controller = fxmlLoader.getController();
		} catch (IOException e) {
			System.err.println("Beim Laden des Popup-Menüs ist ein Fehler aufgetreten.");
			e.printStackTrace();
			System.exit(-1);
		}

		Stage stage = new Stage();
		stage.setTitle("Art der Synthese");
		stage.setScene(scene);
		stage.setAlwaysOnTop(true);
		stage.setOnCloseRequest((WindowEvent e) -> {
			System.exit(0);
		});
		stage.showAndWait();

		return controller;
	}

}
