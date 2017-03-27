package openLab.dialogControl;

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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Callback;
import openLab.controller.ScopeSettings;

/**
 * @author Markus Lechner
 *
 */
public class ChannelSetController implements Initializable {
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private ImageView ivLogo;
	@FXML
	private Label lblCH1;
	@FXML
	private Label lblCH2;
	@FXML
	private Label lblHorizontal;
	@FXML
	private Label lblCompensation;
	@FXML
	private Pane paneSettings;
	@FXML
	private Button btCancel;
	@FXML
	private Button btApply;

	private Label selectedLbl = new Label();

	private final String WARNING_PROBE_ATT = new String("Attention: Setting the probe attenuation to 1:1\n"
			+ "will limit the input range to 2Vpp!");

	private final String HINT_PROBE_COMP = new String("Low frequency response can be matched to the oscilloscope\n"
			+ "by adjusting the compensation trimmer on the head of\nthe probe. Connect the probe to the oscilloscope"
			+ " and set\nthe switch to the X10 position. See follow illustrations.");

	private Map <String, String> channelConfig = new HashMap<String, String>();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		/*** Channel Configuration Logo ***/		

		InputStream isImage = ChannelSetController.class.getResourceAsStream("/images/CHConfigIcon.png");			
		final Image imgExportCSVIcon = new Image(isImage);			

		try {
			isImage.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ivLogo.setImage(imgExportCSVIcon);

		//Highlight (Bold) the selected channel 
		lblCH1.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		selectedLbl = lblCH1;

		/*** Channel 1 Parameter ***/
		VBox vboxCH1 = new VBox();
		vboxCH1.setSpacing(15.0);
		vboxCH1.setPadding(new Insets(10.0, 10.0, 10.0, 15.0));		

		CheckBox cbInvertCH1 = new CheckBox("Invert CH1");

		if (Boolean.parseBoolean(channelConfig.get(ScopeSettings.CFG_INVERT_CH1))) {
			cbInvertCH1.setSelected(true);
		} else {
			cbInvertCH1.setSelected(false);
		}	

		vboxCH1.getChildren().add(cbInvertCH1);		

		GridPane gridProbeAttCH1= new GridPane();
		gridProbeAttCH1.setPadding(new Insets(10.0, 0.0, 10.0, 0.0));	
		gridProbeAttCH1.setHgap(10.0);

		/*** Section probe attenuation ***/
		Label lblProbeAttCH1 = new Label("Probe Attenuation:");
		gridProbeAttCH1.add(lblProbeAttCH1, 0, 0);		

		final ComboBox<String> cbProbeAttCH1 = new ComboBox<String>();		
		cbProbeAttCH1.getItems().addAll(
				"1:1",
				"10:1"	    
				);
		cbProbeAttCH1.selectionModelProperty().get().select(channelConfig.get(ScopeSettings.CFG_PROBE_ATT_CH1).concat(":1"));
		gridProbeAttCH1.add(cbProbeAttCH1, 1, 0);

		/*** Section signal color selection ***/
		GridPane gridSignalColorCH1 = new GridPane();
		gridSignalColorCH1.setHgap(10.0);

		Label lblColorCH1 = new Label("Signal Color:");
		gridSignalColorCH1.add(lblColorCH1, 0, 0);	

		ComboBox<String> cbColorCH1 = new ComboBox<>();
		configureCheckBoxColor(cbColorCH1, channelConfig.get(ScopeSettings.CFG_SIG_COLOR_CH1));
		gridSignalColorCH1.add(cbColorCH1, 1, 0);

		/*** Section Signal Label ***/
		HBox hBoxSigLabelCH1 = new HBox();
		hBoxSigLabelCH1.setSpacing(15.0);
		hBoxSigLabelCH1.setPadding(new Insets(10.0, 10.0, 10.0, 0.0));		

		CheckBox cbSigLabelCH1 = new CheckBox("CH1 Label:");
		cbSigLabelCH1.setDisable(true);

		TextField tfSigLabelCH1 = new TextField();
		tfSigLabelCH1.setMaxWidth(70.0);
		tfSigLabelCH1.setDisable(true);

		hBoxSigLabelCH1.getChildren().addAll(cbSigLabelCH1, tfSigLabelCH1);

		vboxCH1.getChildren().addAll(gridProbeAttCH1, gridSignalColorCH1, hBoxSigLabelCH1);

		/*** CH1 parameters are loaded at first per default. ***/		
		paneSettings.getChildren().add(vboxCH1);

		/*** Channel 2 Parameter ***/		
		VBox vboxCH2 = new VBox();
		vboxCH2.setSpacing(15.0);
		vboxCH2.setPadding(new Insets(10.0, 10.0, 10.0, 15.0));		

		CheckBox cbInvertCH2 = new CheckBox("Invert CH2");

		if(Boolean.parseBoolean(channelConfig.get(ScopeSettings.CFG_INVERT_CH2))) {
			cbInvertCH2.setSelected(true);
		} else {
			cbInvertCH2.setSelected(false);
		}	

		vboxCH2.getChildren().add(cbInvertCH2);		

		GridPane gridProbeAttCH2 = new GridPane();
		gridProbeAttCH2.setPadding(new Insets(10.0, 0.0, 10.0, 0.0));	
		gridProbeAttCH2.setHgap(10.0);

		/*** Section probe attenuation ***/
		Label lblProbeAttCH2 = new Label("Probe Attenuation:");
		gridProbeAttCH2.add(lblProbeAttCH2, 0, 0);		

		final ComboBox<String> cbProbeAttCH2 = new ComboBox<String>();		
		cbProbeAttCH2.getItems().addAll(
				"1:1",
				"10:1"	    
				);
		cbProbeAttCH2.selectionModelProperty().get().select(channelConfig.get(ScopeSettings.CFG_PROBE_ATT_CH2).concat(":1"));
		gridProbeAttCH2.add(cbProbeAttCH2, 1, 0);

		/*** Section signal color selection ***/
		GridPane gridSignalColorCH2 = new GridPane();
		gridSignalColorCH2.setHgap(10.0);

		Label lblColorCH2 = new Label("Signal Color:");
		gridSignalColorCH2.add(lblColorCH2, 0, 0);	

		ComboBox<String> cbColorCH2 = new ComboBox<>();
		configureCheckBoxColor(cbColorCH2, channelConfig.get(ScopeSettings.CFG_SIG_COLOR_CH2));
		gridSignalColorCH2.add(cbColorCH2, 1, 0);

		/*** Section Signal Label ***/
		HBox hBoxSigLabelCH2 = new HBox();
		hBoxSigLabelCH2.setSpacing(15.0);
		hBoxSigLabelCH2.setPadding(new Insets(10.0, 10.0, 10.0, 0.0));		

		CheckBox cbSigLabelCH2 = new CheckBox("CH2 Label:");
		cbSigLabelCH2.setDisable(true);

		TextField tfSigLabelCH2 = new TextField();
		tfSigLabelCH2.setMaxWidth(70.0);
		tfSigLabelCH2.setDisable(true);

		hBoxSigLabelCH2.getChildren().addAll(cbSigLabelCH2, tfSigLabelCH2);

		vboxCH2.getChildren().addAll(gridProbeAttCH2, gridSignalColorCH2, hBoxSigLabelCH2);

		/*** Horizontal Parameter ***/
		VBox vboxHorizontal = new VBox();
		vboxCH2.setSpacing(15.0);
		vboxCH2.setPadding(new Insets(10.0, 10.0, 10.0, 15.0));		

		/*** File Name Parameter ***/
		GridPane gridParamHorizontal = new GridPane();
		gridParamHorizontal.setHgap(15);
		gridParamHorizontal.setVgap(15);
		gridParamHorizontal.setPadding(new Insets(25, 0, 10, 15));

		final Label LabelHrzSteps = new Label("Horizontal Steps:");
		gridParamHorizontal.add(LabelHrzSteps, 0, 0);

		TextField txtfStepValue = new TextField();
		txtfStepValue.setMaxWidth(60.0);

		if(channelConfig.get(ScopeSettings.CFG_HRZ_STEP_VALUE) != null) {
			txtfStepValue.setText(channelConfig.get(ScopeSettings.CFG_HRZ_STEP_VALUE));
		}

		gridParamHorizontal.add(txtfStepValue, 1, 0);		

		GridPane gridParamHrzTimeMode = new GridPane();
		gridParamHrzTimeMode.setHgap(15);
		gridParamHrzTimeMode.setVgap(15);
		gridParamHrzTimeMode.setPadding(new Insets(25, 0, 10, 15));


		final Label LabelTimeMode = new Label("Time Mode:");
		gridParamHrzTimeMode.add(LabelTimeMode, 0, 0);

		final ComboBox<String> cbTimeMode = new ComboBox<String>();		
		cbTimeMode.getItems().addAll(
				"Normal",
				"XY"	    
				);

		cbTimeMode.setDisable(true);

		if(channelConfig.get(ScopeSettings.CFG_HRZ_TIME_MODE) != null) {
			cbTimeMode.selectionModelProperty().get().select(channelConfig.get(ScopeSettings.CFG_HRZ_TIME_MODE));
		}

		gridParamHrzTimeMode.add(cbTimeMode, 1, 0);

		vboxHorizontal.getChildren().addAll(gridParamHorizontal, gridParamHrzTimeMode);

		/*** Compensation Parameter ***/

		VBox vboxProbeComp = new VBox();
		vboxProbeComp.setPadding(new Insets(15.0, 10.0, 10.0, 10.0));

		Label lblProbeCompHint = new Label(HINT_PROBE_COMP);
		vboxProbeComp.getChildren().add(lblProbeCompHint);

		isImage = ChannelSetController.class.getResourceAsStream("/images/ProbeComp.png");			
		final Image imgProbeComp = new Image(isImage, 340.0, 150.0, true, true);			

		try {
			isImage.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ImageView ivProbeComp = new ImageView(imgProbeComp);
		vboxProbeComp.getChildren().add(ivProbeComp);

		VBox vboxProbeCompBtnStart = new VBox();
		vboxProbeCompBtnStart.setPadding(new Insets(15.0, 0.0, 10.0, 90.0));	

		CheckBox cbStartProbeComp = new CheckBox("Start Probe Compensation");
		cbStartProbeComp.setSelected(false);
		vboxProbeCompBtnStart.getChildren().add(cbStartProbeComp);

		vboxProbeComp.getChildren().add(vboxProbeCompBtnStart);

		activateLabelListener(vboxCH1, vboxCH2, vboxProbeComp, vboxHorizontal);		

		/*** Action Listener - Apply ***/		
		btApply.setOnAction((ActionEvent)-> {
			channelConfig.put(ScopeSettings.CFG_PROBE_ATT_APPLY, "true");
			channelConfig.put(ScopeSettings.CFG_INVERT_CH1, Boolean.toString(cbInvertCH1.isSelected()));
			channelConfig.put(ScopeSettings.CFG_INVERT_CH2, Boolean.toString(cbInvertCH2.isSelected()));
			channelConfig.put(ScopeSettings.CFG_PROBE_ATT_CH1, cbProbeAttCH1.getValue().substring(0, cbProbeAttCH1.getValue().indexOf(":")));
			channelConfig.put(ScopeSettings.CFG_PROBE_ATT_CH2, cbProbeAttCH2.getValue().substring(0, cbProbeAttCH2.getValue().indexOf(":")));	
			channelConfig.put(ScopeSettings.CFG_SIG_COLOR_CH1, cbColorCH1.getValue());
			channelConfig.put(ScopeSettings.CFG_SIG_COLOR_CH2, cbColorCH2.getValue());		
			channelConfig.put(ScopeSettings.CFG_HRZ_TIME_MODE, cbTimeMode.getValue());	
			channelConfig.put(ScopeSettings.CFG_PROBE_COMP, Boolean.toString(cbStartProbeComp.isSelected()));	
			
			if(!cbProbeAttCH1.getValue().equals("10:1") || !cbProbeAttCH2.getValue().equals("10:1")) {
				StandardDialog standardDialog = new StandardDialog(anchorPane.getScene().getWindow());

				standardDialog.setTitle("Warning: Probe Attenuation");
				standardDialog.setContentText(WARNING_PROBE_ATT);
				standardDialog.createDialog(AlertType.WARNING);
			}		

			//The text field is verfied if invalid characters or numbers are entered
			if(txtfStepValue.getText().isEmpty()) {
				//If the text field is empty the default value (50) is set
				channelConfig.put(ScopeSettings.CFG_HRZ_STEP_VALUE, "50");
			} else {
				Pattern pattern = Pattern.compile("[0-9]*");
				Matcher matcher = pattern.matcher(txtfStepValue.getText());

				if (matcher.matches()) {
					channelConfig.put(ScopeSettings.CFG_HRZ_STEP_VALUE, txtfStepValue.getText());
				} else {
					StandardDialog standardDialog = new StandardDialog(null);

					standardDialog.setTitle("Error: Invalid Horizontal Step Value");
					standardDialog.setContentText("The entered Horizontal-Step-Value contains invalid characters or numbers! "
							+ "Only positive integer values are allowed!");
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


	private void activateLabelListener(VBox vboxCH1, VBox vboxCH2, VBox vboxProbeComp, VBox vboxHorizontal) {
		/*** Action Listener for the corresponding Settings***/

		lblCH1.setOnMouseClicked((ActionEvent)-> {
			paneSettings.getChildren().clear();
			paneSettings.getChildren().add(vboxCH1);

			selectedLbl.setFont(Font.getDefault());			
			lblCH1.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
			selectedLbl = lblCH1;
		});

		lblCH2.setOnMouseClicked((ActionEvent)-> {
			paneSettings.getChildren().clear();
			paneSettings.getChildren().add(vboxCH2);

			selectedLbl.setFont(Font.getDefault());			
			lblCH2.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
			selectedLbl = lblCH2;
		});

		lblHorizontal.setOnMouseClicked((ActionEvent)-> {
			paneSettings.getChildren().clear();
			paneSettings.getChildren().add(vboxHorizontal);

			selectedLbl.setFont(Font.getDefault());			
			lblHorizontal.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
			selectedLbl = lblHorizontal;
		});

		lblCompensation.setOnMouseClicked((ActionEvent)-> {
			paneSettings.getChildren().clear();
			paneSettings.getChildren().add(vboxProbeComp);

			selectedLbl.setFont(Font.getDefault());			
			lblCompensation.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
			selectedLbl = lblCompensation;
		});


		return;
	}

	public void configureCheckBoxColor(ComboBox<String> cbColor, String defaultColor) {
		cbColor.getItems().addAll("Red","Green","Purple","Blue","Orange");

		cbColor.setValue(defaultColor);		
		cbColor.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			@Override 
			public ListCell<String> call(ListView<String> param) {
				final ListCell<String> cell = new ListCell<String>() {
					{						
						super.setPrefWidth(cbColor.getPrefWidth());
					}
					@Override 
					public void updateItem(String item,	boolean empty) {
						super.updateItem(item, empty);
						if (item != null) {
							setText(item);
							if (item.contains("Red")) {
								setTextFill(Color.RED);
							} else if (item.contains("Green")){
								setTextFill(Color.DARKGREEN);
							} else if (item.contains("Purple")){
								setTextFill(Color.PURPLE);
							} else if (item.contains("Blue")){
								setTextFill(Color.BLUE);
							} else if (item.contains("Orange")){
								setTextFill(Color.ORANGE);
							} else {
								setTextFill(Color.BLACK);
							}
						} else {
							setText(null);
						}
					}
				};
				return cell;
			}
		});		
		return;
	}


	/**
	 * @param channelConfig the channelConfig to set
	 */
	public void setChannelConfig(Map<String, String> channelConfig) {
		this.channelConfig = channelConfig;
	}
}

/* EOF */