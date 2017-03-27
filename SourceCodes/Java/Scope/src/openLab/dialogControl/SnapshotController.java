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
public class SnapshotController implements Initializable {
	@FXML
	private AnchorPane anchorPane;

	@FXML
	private ImageView ivLogo;

	@FXML
	private Label lblColor;

	@FXML
	private Label lblFileFormat;

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

	private Map <String, String> snapShotSetting = new HashMap<String, String>();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		/*** Snapshot Logo ***/		
		InputStream isImage = SnapshotController.class.getResourceAsStream("/images/SnapshotSymbol.png");			
		final Image imgSnapshotIcon = new Image(isImage);			

		try {
			isImage.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ivLogo.setImage(imgSnapshotIcon);

		//Highlight (Bold) the first selected parameter
		lblColor.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		
		/*** Color Parameter ***/
		GridPane gridParamColor = new GridPane();
		gridParamColor.setHgap(15);
		gridParamColor.setVgap(15);
		gridParamColor.setPadding(new Insets(10, 10, 10, 15));

		final ToggleGroup groupColor = new ToggleGroup();
		RadioButton rbColor = new RadioButton("Color");
		rbColor.setToggleGroup(groupColor);
		rbColor.setSelected(true);
		gridParamColor.add(rbColor, 0, 0);

		RadioButton rbGreyscale = new RadioButton("Grayscale");
		rbGreyscale.setToggleGroup(groupColor);
		gridParamColor.add(rbGreyscale, 1, 0);

		//Default: Settings for the color are loaded and shown
		paneSettings.getChildren().add(gridParamColor);

		/*** File Format Parameter ***/
		GridPane gridParamFileFormat = new GridPane();
		gridParamFileFormat.setHgap(15);
		gridParamFileFormat.setVgap(15);
		gridParamFileFormat.setPadding(new Insets(10, 0, 10, 15));

		final ToggleGroup groupFileFormat = new ToggleGroup();
		RadioButton rbPNG = new RadioButton(".png");
		rbPNG.setToggleGroup(groupFileFormat);
		rbPNG.setSelected(true);
		gridParamFileFormat.add(rbPNG, 0, 0);

		RadioButton rbGIF = new RadioButton(".gif");
		rbGIF.setToggleGroup(groupFileFormat);
		gridParamFileFormat.add(rbGIF, 1, 0);

		/*** File Name Parameter ***/
		GridPane gridParamFileName = new GridPane();
		gridParamFileName.setHgap(15);
		gridParamFileName.setVgap(15);
		gridParamFileName.setPadding(new Insets(25, 0, 10, 15));

		Label LabelFileNameInfo = new Label("Special characters are permitted!");
		gridParamFileName.add(LabelFileNameInfo, 0, 0);

		TextField txtfFileName = new TextField();
		txtfFileName.setMinWidth(250.0);

		if(snapShotSetting.get(ScopeSettings.SNP_FILE_NAME) != null) {
			txtfFileName.setText(snapShotSetting.get(ScopeSettings.SNP_FILE_NAME));
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

		if(snapShotSetting.get(ScopeSettings.SNP_FILE_PATH) != null) {
			if(snapShotSetting.get(ScopeSettings.SNP_FILE_PATH).equals(ScopeSettings.UNKNOWN)) {
				txtfDirectory.setText(NO_DIRECTORY_SELECTED);
			} else {
				txtfDirectory.setText(snapShotSetting.get(ScopeSettings.SNP_FILE_PATH));
			}
		} else {
			txtfDirectory.setText(NO_DIRECTORY_SELECTED);
		}

		txtfDirectory.setEditable(false);
		gridParamFilePath.add(txtfDirectory, 0, 1);

		/*** Action Listener for the corresponding Settings***/
		lblColor.setOnMouseClicked((ActionEvent)-> {
			paneSettings.getChildren().clear();
			paneSettings.getChildren().add(gridParamColor);			
			
			lblFileFormat.setFont(Font.getDefault());
			lblFileName.setFont(Font.getDefault());
			lblFilePath.setFont(Font.getDefault());
			lblColor.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		});

		lblFileFormat.setOnMouseClicked((ActionEvent)-> {
			paneSettings.getChildren().clear();
			paneSettings.getChildren().add(gridParamFileFormat);
			
			lblColor.setFont(Font.getDefault());
			lblFileName.setFont(Font.getDefault());
			lblFilePath.setFont(Font.getDefault());
			lblFileFormat.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		});

		lblFileName.setOnMouseClicked((ActionEvent)-> {
			paneSettings.getChildren().clear();
			paneSettings.getChildren().add(gridParamFileName);
			
			lblColor.setFont(Font.getDefault());
			lblFileFormat.setFont(Font.getDefault());
			lblFilePath.setFont(Font.getDefault());
			lblFileName.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		});

		lblFilePath.setOnMouseClicked((ActionEvent)-> {
			paneSettings.getChildren().clear();
			paneSettings.getChildren().add(gridParamFilePath);
			
			lblColor.setFont(Font.getDefault());
			lblFileFormat.setFont(Font.getDefault());
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

			rdTemp = (RadioButton) groupColor.getSelectedToggle();
			snapShotSetting.put(ScopeSettings.SNP_COLOR, rdTemp.getText());

			rdTemp = (RadioButton) groupFileFormat.getSelectedToggle();
			StringBuilder tempTxtMod = new StringBuilder(rdTemp.getText());

			//Remove the dot
			tempTxtMod.deleteCharAt(0);
			snapShotSetting.put(ScopeSettings.SNP_FILE_FORMAT, tempTxtMod.toString());

			if(txtfDirectory.getText().equals(NO_DIRECTORY_SELECTED)) {
				snapShotSetting.put(ScopeSettings.SNP_FILE_PATH, ScopeSettings.UNKNOWN);
			} else {
				snapShotSetting.put(ScopeSettings.SNP_FILE_PATH, txtfDirectory.getText());
			}

			if(txtfFileName.getText().isEmpty()) {
				snapShotSetting.put(ScopeSettings.SNP_FILE_NAME, txtfFileName.getText());
			} else {
				Pattern pattern = Pattern.compile("[a-zA-Z0-9_]*");
				Matcher matcher = pattern.matcher(txtfFileName.getText());

				if (matcher.matches()) {
					snapShotSetting.put(ScopeSettings.SNP_FILE_NAME, txtfFileName.getText());
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
	 * @param snapShotSetting the snapShotSetting to set
	 */
	public void setSnapShotSetting(Map<String, String> snapShotSetting) {
		this.snapShotSetting = snapShotSetting;
	}
}

/* EOF */