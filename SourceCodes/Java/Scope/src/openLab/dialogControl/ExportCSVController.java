package openLab.dialogControl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dialog.StandardDialog;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import openLab.controller.ScopeSettings;

/**
 * @author Markus Lechner
 *
 */
public class ExportCSVController implements Initializable {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ImageView ivLogo;

    @FXML
    private Label lblPointComma;

    @FXML
    private Label lblFileName;

    @FXML
    private Label lblFilePath;

    @FXML
    private Pane paneSettings;

    @FXML
    private Button btCancel;

    @FXML
    private Button btApply;

	private final String NO_DIRECTORY_SELECTED = new String("No Directory selected");

	private Map <String, String> exportCSVSetting = new HashMap<String, String>();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		/*** Snapshot Logo ***/		
		InputStream isImage = ExportCSVController.class.getResourceAsStream("/images/ExportCSV_Icon.png");			
		final Image imgExportCSVIcon = new Image(isImage);			

		try {
			isImage.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ivLogo.setImage(imgExportCSVIcon);
		
		//Highlight (Bold) the first selected parameter
		lblPointComma.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

		/*** Color Parameter ***/
		GridPane gridParamPointComma = new GridPane();
		gridParamPointComma.setHgap(15);
		gridParamPointComma.setVgap(15);
		gridParamPointComma.setPadding(new Insets(10, 10, 10, 15));

		final ToggleGroup groupPointComma = new ToggleGroup();
		RadioButton rbENG = new RadioButton("7.0 ENG");
		rbENG.setToggleGroup(groupPointComma);
		rbENG.setSelected(true);
		gridParamPointComma.add(rbENG, 0, 0);

		RadioButton rbGER = new RadioButton("7,0 GER");
		rbGER.setToggleGroup(groupPointComma);
		gridParamPointComma.add(rbGER, 1, 0);

		//Default: Settings for the Point or Comma selection are loaded and shown
		paneSettings.getChildren().add(gridParamPointComma);

		/*** File Name Parameter ***/
		GridPane gridParamFileName = new GridPane();
		gridParamFileName.setHgap(15);
		gridParamFileName.setVgap(15);
		gridParamFileName.setPadding(new Insets(25, 0, 10, 15));

		Label LabelFileNameInfo = new Label("Special characters are permitted!");
		gridParamFileName.add(LabelFileNameInfo, 0, 0);

		TextField txtfFileName = new TextField();
		txtfFileName.setMinWidth(250.0);

		if(exportCSVSetting.get(ScopeSettings.CSV_FILE_NAME) != null) {
			txtfFileName.setText(exportCSVSetting.get(ScopeSettings.CSV_FILE_NAME));
		}

		gridParamFileName.add(txtfFileName, 0, 1);
		
		/*** File Path Parameter ***/
		GridPane gridParamFilePath = new GridPane();
		gridParamFilePath.setHgap(15);
		gridParamFilePath.setVgap(15);
		gridParamFilePath.setPadding(new Insets(25, 0, 10, 15));

		Button btnSelectDir = new Button("Choose Directory");
		gridParamFilePath.add(btnSelectDir, 0, 0);

		TextField txtfDirectory = new TextField();
		txtfDirectory.setMinWidth(250.0);

		if(exportCSVSetting.get(ScopeSettings.CSV_FILE_PATH) != null) {
			if(exportCSVSetting.get(ScopeSettings.CSV_FILE_PATH).equals(ScopeSettings.UNKNOWN)) {
				txtfDirectory.setText(NO_DIRECTORY_SELECTED);
			} else {
				txtfDirectory.setText(exportCSVSetting.get(ScopeSettings.CSV_FILE_PATH));
			}
		} else {
			txtfDirectory.setText(NO_DIRECTORY_SELECTED);
		}

		txtfDirectory.setEditable(false);
		gridParamFilePath.add(txtfDirectory, 0, 1);

		/*** Action Listener for the corresponding Settings***/
		lblPointComma.setOnMouseClicked((ActionEvent)-> {
			paneSettings.getChildren().clear();
			paneSettings.getChildren().add(gridParamPointComma);
			
			lblFileName.setFont(Font.getDefault());
			lblFilePath.setFont(Font.getDefault());
			lblPointComma.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		});

		lblFileName.setOnMouseClicked((ActionEvent)-> {
			paneSettings.getChildren().clear();
			paneSettings.getChildren().add(gridParamFileName);
			
			lblPointComma.setFont(Font.getDefault());
			lblFilePath.setFont(Font.getDefault());
			lblFileName.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		});

		lblFilePath.setOnMouseClicked((ActionEvent)-> {
			paneSettings.getChildren().clear();
			paneSettings.getChildren().add(gridParamFilePath);
			
			lblPointComma.setFont(Font.getDefault());
			lblFileName.setFont(Font.getDefault());
			lblFilePath.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		});

		/*** Action Listener for File Directory ***/
		btnSelectDir.setOnAction((ActionEvent)-> {
			DirectoryChooser directoryChooser = new DirectoryChooser();
			File selectedDirectory = directoryChooser.showDialog(anchorPane.getScene().getWindow());

			if(selectedDirectory == null) {
				txtfDirectory.setText(NO_DIRECTORY_SELECTED);
			} else {
				txtfDirectory.setText(selectedDirectory.getAbsolutePath());
			}
		});

		/*** Action Listener - Apply ***/
		btApply.setOnAction((ActionEvent)-> {
			RadioButton rdTemp = new RadioButton();

			rdTemp = (RadioButton) groupPointComma.getSelectedToggle();			
			exportCSVSetting.put(ScopeSettings.CSV_LANG_FORMAT, rdTemp.getText().substring(4, 7));

			if(txtfDirectory.getText().equals(NO_DIRECTORY_SELECTED)) {
				exportCSVSetting.put(ScopeSettings.CSV_FILE_PATH, ScopeSettings.UNKNOWN);
			} else {
				exportCSVSetting.put(ScopeSettings.CSV_FILE_PATH, txtfDirectory.getText());
			}

			if(txtfFileName.getText().isEmpty()) {
				exportCSVSetting.put(ScopeSettings.CSV_FILE_NAME, txtfFileName.getText());
			} else {
				Pattern pattern = Pattern.compile("[a-zA-Z0-9_]*");
				Matcher matcher = pattern.matcher(txtfFileName.getText());

				if (matcher.matches()) {
					exportCSVSetting.put(ScopeSettings.CSV_FILE_NAME, txtfFileName.getText());
				} else {
					StandardDialog standardDialog = new StandardDialog(null);

					standardDialog.setTitle("Error: Invalid File Name");
					standardDialog.setContentText("The entered File Name contains special characters and can not be applied!");
					standardDialog.createDialog(AlertType.ERROR);
					return;
				}
			}
			btCancel.fire();
		});

		/*** Action Listener for AnchorPane Window ***/
		btCancel.setOnAction((ActionEvent)-> {
			Stage stage = (Stage) anchorPane.getScene().getWindow();
			stage.close();
		});
	}

	/**
	 * @param exportCSVSetting the exportCSVSetting to set
	 */
	public void setExportCSVSetting(Map<String, String> exportCSVSetting) {
		this.exportCSVSetting = exportCSVSetting;
	}
}

/* EOF */