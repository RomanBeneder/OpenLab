/**
 * 
 */
package openLab.controller;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import controls.SteppedKnob;
import desktop_support.WebBrowser;
import embeddedMediaCenter.MediaCenterController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import openLab.dialogControl.AboutController;
import openLab.dialogControl.ChannelSetController;
import openLab.dialogControl.ConfigDeviceController;
import openLab.dialogControl.ExportCSVController;
import openLab.dialogControl.ExtendedViewController;
import openLab.dialogControl.FeedbackController;
import openLab.dialogControl.HelpController;
import openLab.dialogControl.SnapshotController;
import openLab.message.packet.ChannelControlMSG;
import openLab.message.packet.DeviceMSG;
import openLab.message.packet.ExportCSVMSG;
import openLab.message.packet.GenericEventMSG;
import openLab.message.packet.GenericEventMSG.GenericEvent;
import openLab.message.packet.GenericMessage;
import openLab.message.packet.Measurement;
import openLab.message.packet.MeasurementMSG;
import openLab.message.packet.MessageCode;
import openLab.message.packet.SampleRateMSG;
import openLab.message.packet.SnapshotMSG;
import openLab.message.packet.TriggerMSG;
import openLab.message.packet.UIControlMSG;
import openLab.message.packet.UIControlMSG.Components;
import openLab.message.packet.UIControlMSG.ProcState;
import openLab.properties.PropIndex;
import openLab.properties.ScopeConfig;
import openLab.resource.MSQ_Handler;
import openLab.resource.OpenLabDevice;


/**
 * @author Markus Lechner */
public class Controller implements Initializable {
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private MenuItem menuItemExportCSV;
	@FXML
	private MenuItem menuItemExit;
	@FXML
	private MenuItem menuItemChannel;
	@FXML
	private CheckMenuItem subMenuGND;
	@FXML
	private CheckMenuItem subMenuTriggerLine;
	@FXML 
	private CheckMenuItem menuItemAdvancedFunctions;
	@FXML
	private MenuItem menuItemSnapshot;
	@FXML
	private MenuItem menuItemCSVExport;
	@FXML
	private MenuItem menuItemConfigDevice;
	@FXML
	private MenuItem menuItemMediaCenter;
	@FXML
	private MenuItem menuItemFeedback;
	@FXML
	private MenuItem menuItemHelp;
	@FXML
	private MenuItem menuItemAbout;
	@FXML
	private VBox vbGraphCtrl;
	@FXML
	private Label lblVoltDivCH1;
	@FXML
	private Label lblVoltDivCH1Value;
	@FXML
	private Label lblVoltDivCH2;
	@FXML
	private Label lblVoltDivCH2Value;
	@FXML
	private ImageView ivTriggerSlope;
	@FXML
	private Label lblTriggerSource;
	@FXML
	private Label lblTriggerLvl;
	@FXML
	private Label lblTimeDiv;
	@FXML
	private Label lblETS;
	@FXML
	private Label lblCH1ProbeValue;
	@FXML
	private Label lblCH2ProbeValue;
	@FXML
	private Label lblSampleRateValue;	
	@FXML
	private Button btnSnapshot;
	@FXML
	private TextArea taStatusField;
	@FXML
	private ImageView ivUAS;
	@FXML
	private ImageView ivOpenLab;
	@FXML
	private ImageView ivVertScalMinCH1;
	@FXML
	private ImageView ivVertScalMaxCH1;
	@FXML
	private ImageView ivVertScalMinCH2;
	@FXML
	private ImageView ivVertScalMaxCH2;
	@FXML
	private ImageView ivVertPosCH1;
	@FXML
	private ImageView ivVertPosCH2;
	@FXML
	private ImageView ivSnapshotIcon;
	@FXML
	private ImageView imvLampETS;
	@FXML
	private Button btnHorzPosRight;
	@FXML
	private Button btnHorzPosLeft;
	@FXML
	private GridPane gpHorizontal;    
	@FXML
	private GridPane gpVerticalVoltDiv;    
	@FXML
	private GridPane gpVerticalPosCH1;
	@FXML
	private GridPane gpVerticalPosCH2;    
	@FXML
	private LineChart<Number, Number> lcSignalGraph;    
	@FXML
	private NumberAxis axisX;
	@FXML
	private NumberAxis axisY;	
	@FXML
	private ToggleButton tbSingle;
	@FXML
	private ToggleButton tbRunStop;
	@FXML
	private ToggleButton tbCH1ctrl;
	@FXML
	private ToggleButton tbCH2ctrl;
	@FXML
	private Button btnDefault;
	@FXML
	private Button btnAuto;    
	@FXML
	private ComboBox<String> cbTriggerSource;
	@FXML
	private ComboBox<Image> cbTriggerSlope;
	@FXML
	private Spinner<Double> spTriggerLvl;
	@FXML
	private Spinner<Double> spVoltDivCH1Fine;
	@FXML
	private Spinner<Double> spVoltDivCH2Fine;

	private SteppedKnob knobTimeDiv;
	private SteppedKnob knobVoltDivCH1;    
	private SteppedKnob knobVoltDivCH2;   
	private SteppedKnob knobVerticalPosCH1;    
	private SteppedKnob knobVerticalPosCH2;   

	private Label lblIndMeasCH1;
	private Label lblMeasCH1Type0;
	private Label lblMeasCH1Type0Val0;
	private Label lblMeasCH1Type1;
	private Label lblMeasCH1Type1Val1;
	private Label lblMeasCH1Type2;
	private Label lblMeasCH1Type2Val2;
	private Label lblMeasCH1Type3;
	private Label lblMeasCH1Type3Val3;
	private GridPane gpMeasurementFieldCH1;

	private Label lblIndMeasCH2;
	private Label lblMeasCH2Type0;
	private Label lblMeasCH2Type0Val0;
	private Label lblMeasCH2Type1;
	private Label lblMeasCH2Type1Val1;
	private Label lblMeasCH2Type2;
	private Label lblMeasCH2Type2Val2;
	private Label lblMeasCH2Type3;
	private Label lblMeasCH2Type3Val3;
	private GridPane gpMeasurementFieldCH2;	

	private Stage primaryStage = null;
	private Stage stageExtendedView = null;

	private SignalGraph signalGraph = null;

	private ArrayList<Label> measurementLabelsCH1 = null;
	private ArrayList<Label> measurementLabelsCH2 = null;	

	private ArrayList<Label> aColorLabelListCH1 = null;
	private ArrayList<Label> aColorLabelListCH2 = null;

	private ScopeSettings scopeSettings = null;
	private UserInterfaceProc userInterfaceProc = null;

	private InputStream isImage = null;	

	private Image imgLampETS_OFF = null;
	private Image imgLampETS_ON = null;

	private boolean exportCSV = false;
	private boolean etsModeUpdated = false;
	private boolean rtsModeUpdated = false;

	private List<String> portList = Collections.synchronizedList(new ArrayList<String>(5));

	private volatile ProcState singleShotState = ProcState.NONE;

	int horizontalStepValue = 0;

	public Controller(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Load scope properties
		ScopeConfig scopeConfiguration = ScopeConfig.getInstance();
		scopeConfiguration.loadConfiguration();

		userInterfaceProc = new UserInterfaceProc();
		scopeSettings = new ScopeSettings();

		signalGraph = new SignalGraph();
		ControlUtilities ctrlUtil = new ControlUtilities();								

		//Timeline to debounce the knob time division to suppress multiple message transmission
		Timeline tlKnobTDivDebounce = new Timeline(new KeyFrame(Duration.millis(75),
				ae -> transmitSampleRatesetting()));	

		//		Timeline tlSampleDataAcqState = new Timeline(new KeyFrame(Duration.millis(100),
		//				ae -> updateSampleDataAcqState()));	

		//Update the horizontal Step Value variable with the saved default value
		horizontalStepValue = Integer.parseInt(scopeSettings.getChannelConfig().get(ScopeSettings.CFG_HRZ_STEP_VALUE));

		/* ArrayList with measurement labels for CH1 */
		measurementLabelsCH1 = new ArrayList<Label>();

		/* ArrayList with measurement labels for CH2 */
		measurementLabelsCH2 = new ArrayList<Label>();

		/* Initialize ArrayLists which contain labels whose color has to be adjusted */
		aColorLabelListCH1 = new ArrayList<Label>();
		aColorLabelListCH1.add(lblVoltDivCH1);
		aColorLabelListCH1.add(lblVoltDivCH1Value);

		aColorLabelListCH2 = new ArrayList<Label>();		
		aColorLabelListCH2.add(lblVoltDivCH2);
		aColorLabelListCH2.add(lblVoltDivCH2Value);		

		/* Initialize the entire menu bar */
		menuItemExportCSV.setOnAction((ActionEvent)-> {
			if(!tbRunStop.isSelected()) {
				exportCSV = true;				
				tbRunStop.fire();		
				userInterfaceProc.sendGenericEventMSG(GenericEvent.RUN_STOP_PROC);
			}

			if(scopeSettings.getExportCSVSetting().get(ScopeSettings.CSV_FILE_PATH).equals(ScopeSettings.UNKNOWN)) {
				DirectoryChooser dirChooser = new DirectoryChooser();
				File selectedDir = dirChooser.showDialog(primaryStage);

				if(selectedDir == null) {
					return;
				}
				scopeSettings.getExportCSVSetting().put(ScopeSettings.CSV_FILE_PATH, selectedDir.getAbsolutePath().toString());
			}

			userInterfaceProc.sendExportCSVMSG(scopeSettings.getExportCSVSetting().get(ScopeSettings.CSV_FILE_NAME),
					scopeSettings.getExportCSVSetting().get(ScopeSettings.CSV_FILE_PATH),
					scopeSettings.getExportCSVSetting().get(ScopeSettings.CSV_LANG_FORMAT));
		});

		menuItemExit.setOnAction((ActionEvent)-> {
			primaryStage.fireEvent(new WindowEvent(primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST));			
		});		

		subMenuGND.setOnAction((ActionEvent)-> {
			if(!subMenuGND.isSelected()) {
				signalGraph.clearGNDlines(OpenLabDevice.BOTH_CHANNELS);
			} else {
				if(tbCH1ctrl.isSelected()) {				
					signalGraph.indicateGNDlines(OpenLabDevice.CH1, knobVerticalPosCH1.getValue(), axisX.getLowerBound(), axisX.getUpperBound());
				}

				if(tbCH2ctrl.isSelected()) {				
					signalGraph.indicateGNDlines(OpenLabDevice.CH2, knobVerticalPosCH2.getValue(), axisX.getLowerBound(), axisX.getUpperBound());
				}
			}
		});

		subMenuTriggerLine.setOnAction((ActionEvent)-> {
			updateTriggerLvl();
		});

		menuItemChannel.setOnAction((ActionEvent)-> {
			processActionEventMenuItemChannel(ctrlUtil);
		});

		// Move attached window with main window
		primaryStage.xProperty().addListener((ActionEvent)-> {
			if(stageExtendedView != null)
				stageExtendedView.setX(primaryStage.getX() + primaryStage.getWidth() + 2.0);
		});

		primaryStage.yProperty().addListener((ActionEvent)-> {
			if(stageExtendedView != null)
				stageExtendedView.setY(primaryStage.getY());
		});

		menuItemAdvancedFunctions.setDisable(true);
		menuItemAdvancedFunctions.setOnAction((ActionEvent) -> {
			if(menuItemAdvancedFunctions.isSelected()) {
				stageExtendedView = new Stage();
				stageExtendedView.initOwner(primaryStage);
				stageExtendedView.setScene(ctrlUtil.loadGui("/dialogFXML/ExtendedView.fxml", new ExtendedViewController()));
				stageExtendedView.setTitle("Extended View");
				stageExtendedView.setResizable(false);

				stageExtendedView.setX(primaryStage.getX() + primaryStage.getWidth()+2.0);
				stageExtendedView.setY(primaryStage.getY());

				isImage = Controller.class.getResourceAsStream("/images/ExtendedViewIcon.png");			
				final Image imgExtendedViewIcon = new Image(isImage);		
				ctrlUtil.closeIamgeStream(isImage);

				stageExtendedView.getIcons().add(imgExtendedViewIcon);	

				stageExtendedView.setOnHidden(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent event) {
						menuItemAdvancedFunctions.setSelected(false);
						stageExtendedView = null;
					}
				});

				stageExtendedView.show();
			} else {
				if(stageExtendedView != null) {
					stageExtendedView.close();
				}
			}
		});

		menuItemSnapshot.setOnAction((ActionEvent)-> {		
			SnapshotController snapshotController = new SnapshotController();
			snapshotController.setSnapShotSetting(scopeSettings.getSnpSetting());

			Stage stage = new Stage();
			stage.initOwner(primaryStage);
			stage.setScene(ctrlUtil.loadGui("/dialogFXML/Snapshot.fxml", snapshotController));
			stage.setTitle("Snapshot");
			stage.setResizable(false);

			isImage = Controller.class.getResourceAsStream("/images/SnapshotSymbol.png");			
			final Image imgSnapshotIcon = new Image(isImage);		
			ctrlUtil.closeIamgeStream(isImage);

			stage.getIcons().add(imgSnapshotIcon);			
			stage.show();	
		});	

		menuItemCSVExport.setOnAction((ActionEvent)-> {		
			ExportCSVController exportCSVController = new ExportCSVController();
			exportCSVController.setExportCSVSetting(scopeSettings.getExportCSVSetting());

			Stage stage = new Stage();
			stage.initOwner(primaryStage);
			stage.setScene(ctrlUtil.loadGui("/dialogFXML/ExportCSV.fxml", exportCSVController));
			stage.setTitle("CSV Export Settings");	
			stage.setResizable(false);

			isImage = Controller.class.getResourceAsStream("/images/ExportCSV_Icon.png");			
			final Image imgExportCSVIcon = new Image(isImage);		
			ctrlUtil.closeIamgeStream(isImage);

			stage.getIcons().add(imgExportCSVIcon);			
			stage.show();			
		});	

		menuItemConfigDevice.setOnAction((ActionEvent)-> {		
			ConfigDeviceController configDeviceController = new ConfigDeviceController();
			configDeviceController.setPortList(this.portList);

			Stage stage = new Stage();
			stage.initOwner(primaryStage);
			stage.setScene(ctrlUtil.loadGui("/dialogFXML/ConfigDevice.fxml", configDeviceController));
			stage.setTitle("OpenLab Device Configuration");		
			stage.setResizable(false);

			isImage = Controller.class.getResourceAsStream("/images/USB_Logo.png");			
			final Image imgUSBLogo = new Image(isImage);		
			ctrlUtil.closeIamgeStream(isImage);

			stage.getIcons().add(imgUSBLogo);	
			stage.setOnHidden(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					if(configDeviceController.getSerialPort() != null) {
						userInterfaceProc.sendDeviceInterface(configDeviceController.getSerialPort());
					}
				}
			});
			stage.show();			
		});			

		menuItemMediaCenter.setOnAction((ActionEvent)-> {	
			ScopeConfig scopeConfig = ScopeConfig.getInstance();
			Map <String, ArrayList<String>> videoFileName = new HashMap<String, ArrayList<String>>();

			File fileBasic = new File(System.getProperty("user.dir")+File.separator+
					scopeConfig.getScopeProperties().get(PropIndex.INTRO_VIDEO_FOLDER_NAME)+File.separator+
					scopeConfig.getScopeProperties().get(PropIndex.INTRO_VIDEO_SUB_FOLDER_NAME_BASIC));

			ctrlUtil.getVideoFileNames(videoFileName, fileBasic, MediaCenterController.BASIC_VID);

			File fileAdv = new File(System.getProperty("user.dir")+File.separator+
					scopeConfig.getScopeProperties().get(PropIndex.INTRO_VIDEO_FOLDER_NAME)+File.separator+
					scopeConfig.getScopeProperties().get(PropIndex.INTRO_VIDEO_SUB_FOLDER_NAME_ADV));

			ctrlUtil.getVideoFileNames(videoFileName, fileAdv, MediaCenterController.ADV_VID);

			MediaCenterController mediaCenterController = new MediaCenterController(scopeConfig.getScopeProperties(), videoFileName);

			Stage stage = new Stage();
			stage.initOwner(primaryStage);
			stage.setScene(ctrlUtil.loadGui("/embeddedMediaCenter/MediaCenter.fxml", mediaCenterController));
			stage.setTitle("OpenLab Media Center");		
			stage.setResizable(false);
			stage.initStyle(StageStyle.DECORATED);

			isImage = Controller.class.getResourceAsStream("/images/MediaCenter.png");			
			final Image imgMediaCenterIcon = new Image(isImage);		
			ctrlUtil.closeIamgeStream(isImage);

			stage.getIcons().add(imgMediaCenterIcon);
			stage.show();
		});	

		menuItemFeedback.setOnAction((ActionEvent)-> {	
			FeedbackController feedbackController = new FeedbackController();

			Stage stage = new Stage();
			stage.initOwner(primaryStage);
			stage.setScene(ctrlUtil.loadGui("/dialogFXML/Feedback.fxml", feedbackController));
			stage.setTitle("Feedback");		
			stage.setResizable(false);

			isImage = Controller.class.getResourceAsStream("/images/FeedbackLogo.png");			
			final Image imgFeedbackIcon = new Image(isImage);		
			ctrlUtil.closeIamgeStream(isImage);

			stage.getIcons().add(imgFeedbackIcon);
			stage.show();
		});	

		//		menuItemHelp.setDisable(true);
		menuItemHelp.setOnAction((ActionEvent)-> {
			Stage stage = new Stage();
			stage.initOwner(primaryStage);
			stage.setScene(ctrlUtil.loadGui("/dialogFXML/Help.fxml", new HelpController()));
			stage.setTitle("Help");		
			stage.setResizable(true);

			isImage = Controller.class.getResourceAsStream("/images/HelpIcon.png");			
			final Image imgHelpIcon = new Image(isImage);		
			ctrlUtil.closeIamgeStream(isImage);

			stage.getIcons().add(imgHelpIcon);
			stage.show();
		});	

		menuItemAbout.setOnAction((ActionEvent)-> {	
			AboutController aboutController = new AboutController();
			aboutController.setSystemSetting(scopeSettings.getSystemSetting());

			Stage stage = new Stage();
			stage.initOwner(primaryStage);
			stage.setScene(ctrlUtil.loadGui("/dialogFXML/About.fxml", aboutController));
			stage.setTitle("About");	
			stage.setResizable(false);

			isImage = Controller.class.getResourceAsStream("/images/AboutLogo.png");			
			final Image imgFeedbackIcon = new Image(isImage);		
			ctrlUtil.closeIamgeStream(isImage);

			stage.getIcons().add(imgFeedbackIcon);
			stage.show();
		});	

		/* Initialize the upper bar of the signal graph */

		ImageComboBox imageComboBox = new ImageComboBox(cbTriggerSlope);
		ivTriggerSlope.setImage(imageComboBox.getSlopeImage().get(ImageComboBox.IMAGE_RISING_EDGE));

		lblTriggerLvl.setText(ctrlUtil.formatTriggerLevelValue(0.0));


		/* Configuration for the measurement field */

		//Creation of measurement field channel 1		
		gpMeasurementFieldCH1 = new GridPane();
		gpMeasurementFieldCH1.setAlignment(Pos.CENTER_LEFT);
		gpMeasurementFieldCH1.setPadding(new Insets(0, 0, 5, 18));

		lblIndMeasCH1 = new Label("CH1:");
		aColorLabelListCH1.add(lblIndMeasCH1);
		lblIndMeasCH1.setPrefWidth(50.0);
		gpMeasurementFieldCH1.add(lblIndMeasCH1, 0, 0);		

		lblMeasCH1Type0 = new Label("***: ");
		measurementLabelsCH1.add(lblMeasCH1Type0);
		gpMeasurementFieldCH1.add(lblMeasCH1Type0, 1, 0);

		lblMeasCH1Type0Val0 = new Label("***,***");
		measurementLabelsCH1.add(lblMeasCH1Type0Val0);
		lblMeasCH1Type0Val0.setPrefWidth(80.0);
		lblMeasCH1Type0Val0.setMaxWidth(100.0);
		gpMeasurementFieldCH1.add(lblMeasCH1Type0Val0, 2, 0);		

		lblMeasCH1Type1 = new Label("***: ");
		measurementLabelsCH1.add(lblMeasCH1Type1);
		gpMeasurementFieldCH1.add(lblMeasCH1Type1, 3, 0);

		lblMeasCH1Type1Val1 = new Label("***,***");
		measurementLabelsCH1.add(lblMeasCH1Type1Val1);
		lblMeasCH1Type1Val1.setPrefWidth(80.0);
		lblMeasCH1Type1Val1.setMaxWidth(100.0);
		gpMeasurementFieldCH1.add(lblMeasCH1Type1Val1, 4, 0);

		lblMeasCH1Type2 = new Label("***: ");	
		measurementLabelsCH1.add(lblMeasCH1Type2);
		gpMeasurementFieldCH1.add(lblMeasCH1Type2, 5, 0);

		lblMeasCH1Type2Val2 = new Label("***,***");	
		measurementLabelsCH1.add(lblMeasCH1Type2Val2);
		lblMeasCH1Type2Val2.setPrefWidth(80.0);
		lblMeasCH1Type2Val2.setMaxWidth(100.0);
		gpMeasurementFieldCH1.add(lblMeasCH1Type2Val2, 6, 0);

		lblMeasCH1Type3 = new Label("***: ");	
		measurementLabelsCH1.add(lblMeasCH1Type3);
		gpMeasurementFieldCH1.add(lblMeasCH1Type3, 7, 0);

		lblMeasCH1Type3Val3 = new Label("***,***");	
		measurementLabelsCH1.add(lblMeasCH1Type3Val3);
		lblMeasCH1Type3Val3.setPrefWidth(80.0);
		lblMeasCH1Type3Val3.setMaxWidth(100.0);
		gpMeasurementFieldCH1.add(lblMeasCH1Type3Val3, 8, 0);

		//Creation of measurement field channel 2		
		gpMeasurementFieldCH2 = new GridPane();
		gpMeasurementFieldCH2.setAlignment(Pos.CENTER_LEFT);
		gpMeasurementFieldCH2.setPadding(new Insets(0, 0, 5, 18));

		lblIndMeasCH2 = new Label("CH2:");
		aColorLabelListCH2.add(lblIndMeasCH2);
		lblIndMeasCH2.setPrefWidth(50.0);
		gpMeasurementFieldCH2.add(lblIndMeasCH2, 0, 0);		

		lblMeasCH2Type0 = new Label("***: ");
		measurementLabelsCH2.add(lblMeasCH2Type0);
		gpMeasurementFieldCH2.add(lblMeasCH2Type0, 1, 0);

		lblMeasCH2Type0Val0 = new Label("***,***");
		measurementLabelsCH2.add(lblMeasCH2Type0Val0);
		lblMeasCH2Type0Val0.setPrefWidth(80.0);
		lblMeasCH2Type0Val0.setMaxWidth(100.0);
		gpMeasurementFieldCH2.add(lblMeasCH2Type0Val0, 2, 0);		

		lblMeasCH2Type1 = new Label("***: ");
		measurementLabelsCH2.add(lblMeasCH2Type1);
		gpMeasurementFieldCH2.add(lblMeasCH2Type1, 3, 0);

		lblMeasCH2Type1Val1 = new Label("***,***");
		measurementLabelsCH2.add(lblMeasCH2Type1Val1);
		lblMeasCH2Type1Val1.setPrefWidth(80.0);
		lblMeasCH2Type1Val1.setMaxWidth(100.0);
		gpMeasurementFieldCH2.add(lblMeasCH2Type1Val1, 4, 0);

		lblMeasCH2Type2 = new Label("***: ");	
		measurementLabelsCH2.add(lblMeasCH2Type2);
		gpMeasurementFieldCH2.add(lblMeasCH2Type2, 5, 0);

		lblMeasCH2Type2Val2 = new Label("***,***");	
		measurementLabelsCH2.add(lblMeasCH2Type2Val2);
		lblMeasCH2Type2Val2.setPrefWidth(80.0);
		lblMeasCH2Type2Val2.setMaxWidth(100.0);
		gpMeasurementFieldCH2.add(lblMeasCH2Type2Val2, 6, 0);	

		lblMeasCH2Type3 = new Label("***: ");	
		measurementLabelsCH2.add(lblMeasCH2Type3);
		gpMeasurementFieldCH2.add(lblMeasCH2Type3, 7, 0);

		lblMeasCH2Type3Val3 = new Label("***,***");	
		measurementLabelsCH2.add(lblMeasCH2Type3Val3);
		lblMeasCH2Type3Val3.setPrefWidth(80.0);
		lblMeasCH2Type3Val3.setMaxWidth(100.0);
		gpMeasurementFieldCH2.add(lblMeasCH2Type3Val3, 8, 0);

		/* Copy all measurement labels to the color handled ArrayList */
		aColorLabelListCH1.addAll(measurementLabelsCH1);
		aColorLabelListCH2.addAll(measurementLabelsCH2);

		/* Configuration for the section 'Trigger'  */

		ctrlUtil.configureTriggerSection(cbTriggerSource);
		cbTriggerSource.setOnAction((ActionEvent event)-> {
			if(cbTriggerSource.getValue().equals("CH1")){
				lblTriggerSource.setTextFill(lblVoltDivCH1.getTextFill());
				lblTriggerSource.setText("1");
			} else if(cbTriggerSource.getValue().equals("CH2")){
				lblTriggerSource.setTextFill(lblVoltDivCH2.getTextFill());
				lblTriggerSource.setText("2");
			} else if(cbTriggerSource.getValue().equals("OFF")){
				lblTriggerSource.setTextFill(Color.web("#000000"));
				lblTriggerSource.setText("N");
			}
			updateTriggerLvl();
			userInterfaceProc.sendTriggerSettingsMSG();	
		});

		imageComboBox.createImageComboBox();
		cbTriggerSlope.setOnAction((ActionEvent)-> {
			ivTriggerSlope.setImage(imageComboBox.getSlopeImage().get(cbTriggerSlope.getSelectionModel().getSelectedIndex()));
			userInterfaceProc.sendTriggerSettingsMSG();	
		});

		spTriggerLvl.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(-40.0,+40.0,0.0,0.05));
		spTriggerLvl.getValueFactory().setValue(0.0);
		spTriggerLvl.valueProperty().addListener((obs, oldValue, newValue) -> {
			updateTriggerLvl();	
			userInterfaceProc.sendTriggerSettingsMSG();
			lblTriggerLvl.setText(ctrlUtil.formatTriggerLevelValue(newValue));
		});

		/* Configuration for the section 'Signal Graph'  */
		signalGraph.configureSignalGraph(lcSignalGraph);

		/* Configuration for the section 'Signal Graph' settings  */

		/* Snapshot implementation with Icon */
		isImage = Controller.class.getResourceAsStream("/images/SnapshotSymbol.png");			
		final Image imgSnapshotIcon = new Image(isImage);		
		ctrlUtil.closeIamgeStream(isImage);
		ivSnapshotIcon.setImage(imgSnapshotIcon);

		btnSnapshot.setOnAction((ActionEvent event)-> {
			if(scopeSettings.getSnpSetting().get(ScopeSettings.SNP_FILE_PATH).equals(ScopeSettings.UNKNOWN)) {
				DirectoryChooser dirChooser = new DirectoryChooser();
				File selectedDir = dirChooser.showDialog(primaryStage);

				if(selectedDir == null) {
					return;
				}
				scopeSettings.getSnpSetting().put(ScopeSettings.SNP_FILE_PATH, selectedDir.getAbsolutePath().toString());
			}

			WritableImage image = vbGraphCtrl.snapshot(new SnapshotParameters(), null);			
			userInterfaceProc.sendSnapShotMSG(scopeSettings.getSnpSetting().get(ScopeSettings.SNP_FILE_NAME),
					scopeSettings.getSnpSetting().get(ScopeSettings.SNP_FILE_PATH),
					scopeSettings.getSnpSetting().get(ScopeSettings.SNP_FILE_FORMAT),
					scopeSettings.getSnpSetting().get(ScopeSettings.SNP_COLOR),
					image);
		});

		/* Configuration for the section status field and logos */

		isImage = Controller.class.getResourceAsStream("/images/FHTW.png");			
		final Image imgFHTW = new Image(isImage, 100, 50, false, true);
		ctrlUtil.closeIamgeStream(isImage);
		ivUAS.setImage(imgFHTW);

		setImageListener(ivUAS, scopeConfiguration.getScopeProperties().get(PropIndex.WEBSITE_FH_TECHNIKUM).toString());		

		isImage = Controller.class.getResourceAsStream("/images/OpenLab.png");		
		final Image imgOpenLab = new Image(isImage, 110, 50, false, false);
		ctrlUtil.closeIamgeStream(isImage);
		ivOpenLab.setImage(imgOpenLab);

		setImageListener(ivOpenLab, scopeConfiguration.getScopeProperties().get(PropIndex.WEBSITE_OPENLAB).toString());

		/* Configuration for the section 'Run Control' */

		tbSingle.setOnAction((ActionEvent event)-> {
			if(tbSingle.isSelected()) {
				DropShadow shadow = new DropShadow();
				tbSingle.setEffect(shadow);
				tbSingle.getStyleClass().add("button-Single-pressed");				

				//
				//tlSampleDataAcqState.setCycleCount(Animation.INDEFINITE);
				//tlSampleDataAcqState.play();

				//The Signal Graph and the measurement value labels are 
				//immediately cleared after this event
				signalGraph.clearSeries(SignalGraph.CLEAR_SIGNAL_SERIES);
				setMeasToNoSignal(measurementLabelsCH1);
				setMeasToNoSignal(measurementLabelsCH2);				

				if(tbRunStop.isSelected()) {
					singleShotState = ProcState.PROC;
					userInterfaceProc.sendGenericEventMSG(GenericEvent.RUN_STOP_ABRT);
					tbRunStop.fire();
				}

				userInterfaceProc.sendGenericEventMSG(GenericEvent.SINGLE_SHOT_PROC);				
			} else {
				tbSingle.getStyleClass().remove("button-Single-pressed");
				tbSingle.setEffect(null);

				//
				//tlSampleDataAcqState.stop();

				//If the user aborts the Single-Shot action the UI needs to inform the
				//RequestProcessing unit to continue the Execution mode.
				if (singleShotState != ProcState.DONE) {
					userInterfaceProc.sendGenericEventMSG(GenericEvent.SINGLE_SHOT_ABRT);
				} else {
					if(!tbRunStop.isSelected()) {
						tbRunStop.fire();
					}
				}
			}
		});

		tbRunStop.setStyle("-fx-base: #98e698;");
		tbRunStop.setOnAction((ActionEvent event)-> {			
			if(tbRunStop.isSelected()) {
				DropShadow shadow = new DropShadow();
				tbRunStop.setEffect(shadow);
				tbRunStop.setStyle("-fx-base: #ff9980;");				

				if(!exportCSV) {
					userInterfaceProc.sendGenericEventMSG(GenericEvent.RUN_STOP_PROC);
					exportCSV = false;
				}				

				//Reset the state of Single-Shot to NONE, due to the Single-Shot
				//was executed correctly
				if(singleShotState == ProcState.DONE) {
					singleShotState = ProcState.NONE;
				}

				//If the Run/Stop button was pressed due to Single-Shot operation,
				//(Forbidden state) the Single-Shot state is leaved by default.
				if(tbSingle.isSelected()) {
					singleShotState = ProcState.NONE;
					tbSingle.fire();
				}

			} else {
				tbRunStop.setEffect(null);
				tbRunStop.setStyle("-fx-base: #98e698;");

				if (singleShotState != ProcState.PROC) {
					userInterfaceProc.sendGenericEventMSG(GenericEvent.RUN_STOP_ABRT);
				}				
				transmitSampleRatesetting();
			}
		});

		btnDefault.setOnAction((ActionEvent event)-> {			
			restoreDefaultSettings(ctrlUtil);			
		});

		btnAuto.setOnAction((ActionEvent event)-> {		
			initAutoScalingParam();
			userInterfaceProc.sendGenericEventMSG(GenericEvent.AUTO_SCALE);			
		});

		/* Configuration for the section 'Horizontal' */		

		knobTimeDiv = new SteppedKnob("horizontal-knob", "knob" , 85.0 , ctrlUtil.getTimeDivSteps());		
		knobTimeDiv.setValue(1.0e-3);

		knobTimeDiv.valueProperty().addListener((obs, oldValue, newValue) -> {				
			Animation.Status animState = tlKnobTDivDebounce.getStatus();

			if(animState == Animation.Status.RUNNING) {
				tlKnobTDivDebounce.stop();
				tlKnobTDivDebounce.playFromStart();
			} else {
				tlKnobTDivDebounce.playFromStart();
			}

			adjustSignalGraphAxis(false);	
			setHorizontalPosition(0.0);
			lblTimeDiv.setText(ctrlUtil.formatTDivValue(knobTimeDiv.getValue()));			
		});
		knobTimeDiv.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if(mouseEvent.getButton() == MouseButton.PRIMARY){
					if(mouseEvent.getClickCount() == 2){
						knobTimeDiv.setValue(1.0e-3);
					}
				}
			}
		});

		gpHorizontal.add(knobTimeDiv, 0, 0);				

		//TODO implement horz pos
		btnHorzPosLeft.setOnAction((ActionEvent)-> {
			setHorizontalPosition(horizontalStepValue);
		});

		btnHorzPosRight.setOnAction((ActionEvent)-> {
			setHorizontalPosition(horizontalStepValue * (-1));
		});		

		setImageListener(lblETS, scopeConfiguration.getScopeProperties().get(PropIndex.WEBSITE_WIKI).toString());

		//Load the image for the indication of the ETS-Mode
		isImage = Controller.class.getResourceAsStream("/images/LampETS_OFF.png");			
		imgLampETS_OFF = new Image(isImage);
		ctrlUtil.closeIamgeStream(isImage);
		imvLampETS.setImage(imgLampETS_OFF);

		isImage = Controller.class.getResourceAsStream("/images/LampETS_ON.png");			
		imgLampETS_ON = new Image(isImage);
		ctrlUtil.closeIamgeStream(isImage);	

		/* Configuration for the section 'Vertical' */

		//Load the vertical scaling images
		isImage = Controller.class.getResourceAsStream("/images/sinMinAmpl.png");
		final Image imgSinMinAmpl = new Image(isImage);
		ctrlUtil.closeIamgeStream(isImage);

		isImage = Controller.class.getResourceAsStream("/images/sinMaxAmpl.png");		
		final Image imgSinMaxAmpl = new Image(isImage);
		ctrlUtil.closeIamgeStream(isImage);		

		ivVertScalMinCH1.setImage(imgSinMinAmpl);
		ivVertScalMinCH2.setImage(imgSinMinAmpl);
		ivVertScalMaxCH1.setImage(imgSinMaxAmpl);
		ivVertScalMaxCH2.setImage(imgSinMaxAmpl);

		//Load the vertical positioning image
		isImage = Controller.class.getResourceAsStream("/images/VerticalPosArrow.png");
		final Image imgVertPos = new Image(isImage);
		ctrlUtil.closeIamgeStream(isImage);

		ivVertPosCH1.setImage(imgVertPos);
		ivVertPosCH2.setImage(imgVertPos);

		spVoltDivCH1Fine.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.1,10.0,5.0,0.10));
		spVoltDivCH1Fine.valueProperty().addListener((obs, oldValue, newValue) -> {
			userInterfaceProc.sendChannelControlMSG(OpenLabDevice.CH1);
			lblVoltDivCH1Value.setText(ctrlUtil.formatVDivValue(newValue));

			updateTriggerLvl();
		});

		spVoltDivCH2Fine.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.1,10.0,5.0,0.10));
		spVoltDivCH2Fine.valueProperty().addListener((obs, oldValue, newValue) -> {
			userInterfaceProc.sendChannelControlMSG(OpenLabDevice.CH2);
			lblVoltDivCH2Value.setText(ctrlUtil.formatVDivValue(newValue));

			updateTriggerLvl();
		});		

		knobVoltDivCH1 = new SteppedKnob("vertical-knob", "knob" ,75, ctrlUtil.getVoltDivSteps());			
		//knobTimeDiv.setDisable(true);
		knobVoltDivCH1.setInverted(true);
		knobVoltDivCH1.setValue(5.0);
		knobVoltDivCH1.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if(mouseEvent.getButton() == MouseButton.PRIMARY){
					if(mouseEvent.getClickCount() == 2){
						knobVoltDivCH1.setValue(5.0);
					}
				}
			}
		});		
		knobVoltDivCH1.valueProperty().addListener((obs, oldValue, newValue) -> {
			spVoltDivCH1Fine.getValueFactory().setValue(knobVoltDivCH1.getValue());
		});
		gpVerticalVoltDiv.add(knobVoltDivCH1, 0, 0);		

		knobVoltDivCH2 = new SteppedKnob("vertical-knob", "knob" ,75, ctrlUtil.getVoltDivSteps());	
		//knobTimeDiv.setDisable(true);
		knobVoltDivCH2.setInverted(true);
		knobVoltDivCH2.setValue(5.0);
		knobVoltDivCH2.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if(mouseEvent.getButton() == MouseButton.PRIMARY){
					if(mouseEvent.getClickCount() == 2){
						knobVoltDivCH2.setValue(5.0);
					}
				}
			}
		});		
		knobVoltDivCH2.valueProperty().addListener((obs, oldValue, newValue) -> {
			spVoltDivCH2Fine.getValueFactory().setValue(knobVoltDivCH2.getValue());
		});
		gpVerticalVoltDiv.add(knobVoltDivCH2, 1, 0);

		tbCH1ctrl.setOnAction((ActionEvent)-> {
			if(tbCH1ctrl.isSelected()) {
				DropShadow shadow = new DropShadow();
				tbCH1ctrl.setEffect(shadow);
				tbCH1ctrl.setStyle(null);
				tbCH1ctrl.getStyleClass().add("button-CHxctrl-pressed");					

				//Indicate the corresponding GND line in  the signal graph
				if(tbCH1ctrl.isSelected() && (subMenuGND.isSelected())) {				
					signalGraph.indicateGNDlines(OpenLabDevice.CH1, knobVerticalPosCH1.getValue(), axisX.getLowerBound(), axisX.getUpperBound());
				}

				//Show the measurement field for channel 1
				vbGraphCtrl.getChildren().add(vbGraphCtrl.getChildren().size(), gpMeasurementFieldCH1);

				//Start the animation timer for channel 1 to
				//display the received sample data
				signalGraph.startAnimTimerCH1();
				userInterfaceProc.sendChannelControlMSG(OpenLabDevice.CH1);			
			} else {
				signalGraph.clearGNDlines(OpenLabDevice.CH1);
				tbCH1ctrl.setEffect(null);
				tbCH1ctrl.getStyleClass().remove("button-CHxctrl-pressed");
				tbCH1ctrl.getStyleClass().add("button-CHxctrl-released");

				//Hide the measurement field for channel 1
				vbGraphCtrl.getChildren().remove(gpMeasurementFieldCH1);

				//Stop the animation timer and remove old displayed 
				//sample data
				signalGraph.stopAnimTimerCH1(true);
				userInterfaceProc.sendChannelControlMSG(OpenLabDevice.CH1);
			}
			updateTriggerLvl();
		});

		tbCH2ctrl.setOnAction((ActionEvent)-> {
			if(tbCH2ctrl.isSelected()) {
				DropShadow shadow = new DropShadow();
				tbCH2ctrl.setEffect(shadow);
				tbCH2ctrl.setStyle(null);
				tbCH2ctrl.getStyleClass().add("button-CHxctrl-pressed");	

				//Indicate the corresponding GND line in  the signal graph
				if(tbCH2ctrl.isSelected() && (subMenuGND.isSelected())) {				
					signalGraph.indicateGNDlines(OpenLabDevice.CH2, knobVerticalPosCH2.getValue(), axisX.getLowerBound(), axisX.getUpperBound());
				}

				//Show the measurement field for channel 1
				vbGraphCtrl.getChildren().add(vbGraphCtrl.getChildren().size(), gpMeasurementFieldCH2);

				//Start the animation timer for channel 2 to
				//display the received sample data
				signalGraph.startAnimTimerCH2();
				userInterfaceProc.sendChannelControlMSG(OpenLabDevice.CH2);
			} else {
				signalGraph.clearGNDlines(OpenLabDevice.CH2);
				tbCH2ctrl.setEffect(null);
				tbCH2ctrl.getStyleClass().remove("button-CHxctrl-pressed");
				tbCH2ctrl.getStyleClass().add("button-CHxctrl-released");

				//Hide the measurement field for channel 1
				vbGraphCtrl.getChildren().remove(gpMeasurementFieldCH2);

				//Stop the animation timer and remove old displayed 
				//sample data
				signalGraph.stopAnimTimerCH2(true);
				userInterfaceProc.sendChannelControlMSG(OpenLabDevice.CH2);
			}
			updateTriggerLvl();
		});

		knobVerticalPosCH1 = new SteppedKnob("vertical-knob", "knob" , 60, ctrlUtil.getVerticalPosSteps());	
		//knobTimeDiv.setDisable(true);
		knobVerticalPosCH1.setValue(4.0);
		knobVerticalPosCH1.valueProperty().addListener((obs, oldValue, newValue) -> {
			if(tbCH1ctrl.isSelected() && subMenuGND.isSelected()) {	
				signalGraph.indicateGNDlines(OpenLabDevice.CH1, knobVerticalPosCH1.getValue(), axisX.getLowerBound(), axisX.getUpperBound());
			}
			userInterfaceProc.sendChannelControlMSG(OpenLabDevice.CH1);			
			updateTriggerLvl();
		});

		knobVerticalPosCH1.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if(mouseEvent.getButton() == MouseButton.PRIMARY){
					if(mouseEvent.getClickCount() == 2){
						if(tbCH1ctrl.isSelected() && tbCH2ctrl.isSelected()) {
							knobVerticalPosCH1.setValue(6.0);
						} else {
							knobVerticalPosCH1.setValue(4.0);
						}
					}
				}
			}
		});
		gpVerticalPosCH1.add(knobVerticalPosCH1, 0, 0);

		knobVerticalPosCH2 = new SteppedKnob("vertical-knob", "knob" , 60, ctrlUtil.getVerticalPosSteps());	
		//knobTimeDiv.setDisable(true);
		knobVerticalPosCH2.setValue(4.0);
		knobVerticalPosCH2.valueProperty().addListener((obs, oldValue, newValue) -> {
			if(tbCH2ctrl.isSelected() && subMenuGND.isSelected()) {	
				signalGraph.indicateGNDlines(OpenLabDevice.CH2, knobVerticalPosCH2.getValue(), axisX.getLowerBound(), axisX.getUpperBound());			
			}
			userInterfaceProc.sendChannelControlMSG(OpenLabDevice.CH2);
			updateTriggerLvl();
		});

		knobVerticalPosCH2.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if(mouseEvent.getButton() == MouseButton.PRIMARY){
					if(mouseEvent.getClickCount() == 2){
						if(tbCH1ctrl.isSelected() && tbCH2ctrl.isSelected()) {
							knobVerticalPosCH2.setValue(2.0);
						} else {
							knobVerticalPosCH2.setValue(4.0);
						}
					}
				}
			}
		});
		gpVerticalPosCH2.add(knobVerticalPosCH2, 0, 0);

		//Load the default color -Red "#FF0000"- for channel 1  */
		ctrlUtil.setMeasurementColor(OpenLabDevice.CH1,  "#FF0000", aColorLabelListCH1, lcSignalGraph);
		//Load the default color -Blue  "#0000FF"- for channel 2  */
		ctrlUtil.setMeasurementColor(OpenLabDevice.CH2,  "#0000FF", aColorLabelListCH2, lcSignalGraph);		

		//Load the menuItem icons
		loadMenuItemIcons(isImage, ctrlUtil);

		//Initialize Trigger Level indication within the signal graph
		updateTriggerLvl();

		//Disable the user components
		disableUIComponents();

		//Start the user interface thread 
		userInterfaceProc.initUserInterfaceThread();
	}


	/**
	 * @param posValue
	 */
	private void setHorizontalPosition(double posValue) {
		axisX.setUpperBound(axisX.getUpperBound() + (posValue));
		axisX.setLowerBound(axisX.getLowerBound() + (posValue));

		double xAxisLowerBound = axisX.getLowerBound();		
		double xAxisUpperBound = axisX.getUpperBound();

		if(Double.compare(axisX.getLowerBound(), 0.0) <= 0) {
			xAxisLowerBound = 0.0;
			xAxisUpperBound += Math.abs(xAxisLowerBound);
		} 

		if(tbCH1ctrl.isSelected()) {
			signalGraph.indicateGNDlines(OpenLabDevice.CH1, knobVerticalPosCH1.getValue(), xAxisLowerBound, xAxisUpperBound);
		}

		if(tbCH2ctrl.isSelected()) {
			signalGraph.indicateGNDlines(OpenLabDevice.CH2, knobVerticalPosCH2.getValue(), xAxisLowerBound, xAxisUpperBound);
		}			

		updateTriggerLvl();		
		return;
	}


	/**
	 * @param primaryStage
	 */
	public void installKeyListener(Stage primaryStage) {
		primaryStage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				if(keyEvent.getCode().getName().equals("F12")) {
					btnSnapshot.fire();
				}
			}
		});
		return;
	}

	private void setImageListener(Node iv, String webSite) {
		iv.setOnMouseEntered((value) -> {
			primaryStage.getScene().setCursor(Cursor.HAND);
		});

		iv.setOnMouseExited((value) -> {
			primaryStage.getScene().setCursor(Cursor.DEFAULT);
		});

		iv.setOnMouseClicked((value) -> {
			WebBrowser webBrowser = new WebBrowser(webSite);
			webBrowser.executeWebBrowser();
		});

		return;
	}


	/**
	 * @param ctrlUtil
	 */
	private void processActionEventMenuItemChannel(ControlUtilities ctrlUtil) {
		ChannelSetController channelSetController = new ChannelSetController();
		channelSetController.setChannelConfig(scopeSettings.getChannelConfig());			

		Stage stage = new Stage();
		stage.initOwner(primaryStage);
		stage.setScene(ctrlUtil.loadGui("/dialogFXML/ChannelSettings.fxml", channelSetController));
		stage.setTitle("Channel Settings");
		stage.setResizable(false);

		isImage = Controller.class.getResourceAsStream("/images/CHConfigIcon.png");			
		final Image imgChannelSetCtrlIcon = new Image(isImage);		
		ctrlUtil.closeIamgeStream(isImage);

		stage.getIcons().add(imgChannelSetCtrlIcon);
		stage.setResizable(false);
		stage.setOnHidden(new EventHandler<WindowEvent>(){
			@Override
			public void handle(WindowEvent event) {
				if(Boolean.parseBoolean(scopeSettings.getChannelConfig().get(ScopeSettings.CFG_PROBE_ATT_APPLY))) {
					if(Boolean.parseBoolean(scopeSettings.getChannelConfig().get(ScopeSettings.CFG_INVERT_CH1))) {
						if(!lblVoltDivCH1.getText().equals("~CH1:")) {
							lblVoltDivCH1.setText("~CH1:");
							invertTriggerSlope("CH1");
						}
					} else {
						if(!lblVoltDivCH1.getText().equals("CH1:")) {
							lblVoltDivCH1.setText("CH1:");
							invertTriggerSlope("CH1");								
						}
					}

					if(Boolean.parseBoolean(scopeSettings.getChannelConfig().get(ScopeSettings.CFG_INVERT_CH2))) {
						if(!lblVoltDivCH2.getText().equals("~CH2:")) {
							lblVoltDivCH2.setText("~CH2:");
							invertTriggerSlope("CH2");
						}
					} else {
						if(!lblVoltDivCH2.getText().equals("CH2:")) {
							lblVoltDivCH2.setText("CH2:");
							invertTriggerSlope("CH2");								
						}
					}					

					ctrlUtil.changeSeriesColor(scopeSettings.getChannelConfig().get(ScopeSettings.CFG_SIG_COLOR_CH1),
							OpenLabDevice.CH1, aColorLabelListCH1, lcSignalGraph);

					ctrlUtil.changeSeriesColor(scopeSettings.getChannelConfig().get(ScopeSettings.CFG_SIG_COLOR_CH2),
							OpenLabDevice.CH2, aColorLabelListCH2, lcSignalGraph);

					if(cbTriggerSource.getValue().equals("CH1")) {
						lblTriggerSource.setTextFill(lblVoltDivCH1.getTextFill());
					} else if(cbTriggerSource.getValue().equals("CH2")) {
						lblTriggerSource.setTextFill(lblVoltDivCH2.getTextFill());
					}

					lblCH1ProbeValue.setText(scopeSettings.getChannelConfig().get(ScopeSettings.CFG_PROBE_ATT_CH1).concat(":1"));
					lblCH2ProbeValue.setText(scopeSettings.getChannelConfig().get(ScopeSettings.CFG_PROBE_ATT_CH2).concat(":1"));

					//Inform the Request processing unit to update the signal graph
					//according to the adjusted changes
					userInterfaceProc.sendChannelControlMSG(OpenLabDevice.CH1);
					userInterfaceProc.sendChannelControlMSG(OpenLabDevice.CH2);

					//Update the horizontal Step Value variable with the saved default value
					horizontalStepValue = Integer.parseInt(scopeSettings.getChannelConfig().get(ScopeSettings.CFG_HRZ_STEP_VALUE));

					//Adjust the oscilloscope channel parameters for the Probe Compensation process
					if (Boolean.parseBoolean(scopeSettings.getChannelConfig().get(ScopeSettings.CFG_PROBE_COMP))) {
						probeCompAdjustment();
					}

					//Reset the Apply flag - All settings were applied
					scopeSettings.getChannelConfig().put(ScopeSettings.CFG_PROBE_ATT_APPLY, "false");
				}
			}
		});
		stage.show();
		return;
	}


	/**
	 * 
	 */
	private void probeCompAdjustment() {
		knobTimeDiv.setValue(200.0e-6);
		spTriggerLvl.getValueFactory().setValue(1.7);
		spVoltDivCH1Fine.getValueFactory().setValue(1.2);
		spVoltDivCH2Fine.getValueFactory().setValue(1.2);
		knobVerticalPosCH1.setValue(4.2);
		knobVerticalPosCH2.setValue(0.2);
		return;
	}


	private void updateTriggerLvl() {		
		double verticalPos;
		double verticalScale;
		double virtualTriggerValue;

		if(cbTriggerSource.getValue().equals("CH1") && subMenuTriggerLine.isSelected() && tbCH1ctrl.isSelected()) {
			verticalPos = knobVerticalPosCH1.getValue();
			verticalScale = spVoltDivCH1Fine.getValue();
		} else if(cbTriggerSource.getValue().equals("CH2") && subMenuTriggerLine.isSelected() && tbCH2ctrl.isSelected()) {
			verticalPos = knobVerticalPosCH2.getValue();
			verticalScale = spVoltDivCH2Fine.getValue();
		} else {
			signalGraph.clearTriggerLvlLine();
			return;
		}

		virtualTriggerValue = (spTriggerLvl.getValue() / verticalScale) + verticalPos;

		double xAxisLowerBound = axisX.getLowerBound();		
		double xAxisUpperBound = axisX.getUpperBound();

		if(Double.compare(axisX.getLowerBound(), 0.0) <= 0) {
			xAxisLowerBound = 0.0;
			xAxisUpperBound += Math.abs(xAxisLowerBound);
		} 

		signalGraph.updateTriggerLvlInd(virtualTriggerValue, xAxisLowerBound, xAxisUpperBound);
		return;
	}


	private void adjustSignalGraphAxis(boolean updateReq) {
		double sampleRate = 50.0 / knobTimeDiv.getValue();			 
		OpenLabDevice openLabDevice = OpenLabDevice.getInstance();

		if(sampleRate > openLabDevice.getMaxSampleRateRTS() && (!etsModeUpdated || updateReq)) {
			axisX.setTickUnit(60.0);
			axisX.setUpperBound(600.0);
			axisX.setLowerBound(0.0);
			etsModeUpdated = true;
			rtsModeUpdated = false;
		} else if (sampleRate <= openLabDevice.getMaxSampleRateRTS() && (!rtsModeUpdated || updateReq)) {
			axisX.setTickUnit(49.7);
			axisX.setUpperBound(497);
			axisX.setLowerBound(0.0);
			rtsModeUpdated = true;
			etsModeUpdated = false;
		}
		return;
	}


	private void initAutoScalingParam() {
		if(tbRunStop.isSelected()) {
			tbRunStop.fire();
		}		

		if(tbSingle.isSelected()) {
			tbSingle.fire();
		}		

		if(!tbCH1ctrl.isSelected()) {
			tbCH1ctrl.fire();
		}

		if(!tbCH2ctrl.isSelected()) {
			tbCH2ctrl.fire();
		}

		cbTriggerSource.getSelectionModel().select("OFF");
		knobTimeDiv.setValue(10.0e-3);

		//Set all signals in the horizontal start position
		adjustSignalGraphAxis(true);

		System.out.println("Auto-Scale: UI-Initialization is done!");
		return;
	}


	private void invertTriggerSlope(String selectedSource)	{
		if(cbTriggerSource.getValue().equals(selectedSource))	{
			int  tempImage = cbTriggerSlope.getSelectionModel().getSelectedIndex();

			if(spTriggerLvl.getValue() != 0.0) {
				spTriggerLvl.getValueFactory().setValue(spTriggerLvl.getValue() * (-1));
			}

			if(tempImage == ImageComboBox.IMAGE_RISING_EDGE) {
				cbTriggerSlope.getSelectionModel().select(ImageComboBox.IMAGE_FALLING_EDGE);				
			} else if(tempImage == ImageComboBox.IMAGE_FALLING_EDGE) {
				cbTriggerSlope.getSelectionModel().select(ImageComboBox.IMAGE_RISING_EDGE);
			}
		}
		return;
	}


	private void transmitSampleRatesetting() {		
		if(!tbRunStop.isSelected()) {
			//Calculate if the device is in ETS-Mode
			double sampleRate = 50.0 / knobTimeDiv.getValue();		
			OpenLabDevice openLabDevice = OpenLabDevice.getInstance();

			//and if so turn-on the ETS_LED for ETS-Mode indication
			if(sampleRate > openLabDevice.getMaxSampleRateRTS()) {
				imvLampETS.setImage(imgLampETS_ON);
			} else {
				imvLampETS.setImage(imgLampETS_OFF);
			}
		}		

		userInterfaceProc.sendSampleRateSettingsMSG();		
		return;
	}


	private void restoreDefaultSettings(ControlUtilities ctrlUtil) {
		//Restore default horizontal scaling
		knobTimeDiv.setValue(1.0e-3);
		
		//Reset the Channel Inversion
		scopeSettings.getChannelConfig().put(ScopeSettings.CFG_INVERT_CH1, "false");
		scopeSettings.getChannelConfig().put(ScopeSettings.CFG_INVERT_CH2, "false");

		if(lblVoltDivCH1.getText().equals("~CH1:")) {
			lblVoltDivCH1.setText("CH1:");
			invertTriggerSlope("CH1");
		}

		if(lblVoltDivCH1.getText().equals("~CH2:")) {
			lblVoltDivCH1.setText("CH2:");
			invertTriggerSlope("CH2");
		}

		//Reset the Probe attenuation to 10:1
		scopeSettings.getChannelConfig().put(ScopeSettings.CFG_PROBE_ATT_CH1, "10");
		scopeSettings.getChannelConfig().put(ScopeSettings.CFG_PROBE_ATT_CH2, "10");

		lblCH1ProbeValue.setText(scopeSettings.getChannelConfig().get(ScopeSettings.CFG_PROBE_ATT_CH1).concat(":1"));
		lblCH2ProbeValue.setText(scopeSettings.getChannelConfig().get(ScopeSettings.CFG_PROBE_ATT_CH2).concat(":1"));

		//Restore default channel colors
		scopeSettings.getChannelConfig().put(ScopeSettings.CFG_SIG_COLOR_CH1, "Red");
		scopeSettings.getChannelConfig().put(ScopeSettings.CFG_SIG_COLOR_CH2, "Blue");

		//Set all signals in the horizontal start position
		adjustSignalGraphAxis(true);
		
		//Restore default horizontal step value and Time Mode
		scopeSettings.getChannelConfig().put(ScopeSettings.CFG_HRZ_STEP_VALUE, "50");
		horizontalStepValue = 50;
		setHorizontalPosition(0.0);
		scopeSettings.getChannelConfig().put(ScopeSettings.CFG_HRZ_TIME_MODE, "Normal");

		ctrlUtil.changeSeriesColor(scopeSettings.getChannelConfig().get(ScopeSettings.CFG_SIG_COLOR_CH1),
				OpenLabDevice.CH1, aColorLabelListCH1, lcSignalGraph);

		ctrlUtil.changeSeriesColor(scopeSettings.getChannelConfig().get(ScopeSettings.CFG_SIG_COLOR_CH2),
				OpenLabDevice.CH2, aColorLabelListCH2, lcSignalGraph);

		//Restore default trigger settings
		cbTriggerSource.getSelectionModel().selectFirst();
		cbTriggerSlope.getSelectionModel().selectFirst();
		spTriggerLvl.getValueFactory().setValue(0.0);

		//Restore default vertical scal./pos. settings
		knobVoltDivCH1.setValue(5.0);
		knobVoltDivCH2.setValue(5.0);
		knobVerticalPosCH1.setValue(4.0);
		knobVerticalPosCH2.setValue(4.0);

		//Restore default vertical configuration settings
		if(!subMenuGND.isSelected()) {
			subMenuGND.setSelected(true);
			subMenuGND.fire();
		}

		if(!subMenuTriggerLine.isSelected()) {
			subMenuTriggerLine.setSelected(true);
			subMenuTriggerLine.fire();
		}

		if(tbSingle.isSelected()) {
			tbSingle.fire();
		}

		if(tbRunStop.isSelected()) {
			tbRunStop.fire();
		}

		//clear the status field
		taStatusField.clear();
		writeToStatusField("Default setup loaded");
	}


	private void AccessUIComponents(Components components) {
		if(!Platform.isFxApplicationThread()) {
			Platform.runLater(() -> {
				verifyUIComponentsAction(components);
			});	
		} else {
			verifyUIComponentsAction(components);			
		}

		return;
	}


	private void verifyUIComponentsAction(Components components) {
		if(components == Components.ENABLE) {
			enableUIComponents();
		} else if(components == Components.DISABLE) {
			disableUIComponents();
		} else {
			return;
		}

		return;
	}


	private void disableUIComponents() {
		//Horizontal
		knobTimeDiv.setDisable(true);
		//Run Control
		tbSingle.setDisable(true);
		tbRunStop.setDisable(true);
		btnDefault.setDisable(true);
		btnAuto.setDisable(true);
		//Trigger
		cbTriggerSource.setDisable(true);
		cbTriggerSlope.setDisable(true);
		spTriggerLvl.setDisable(true);
		//Vertical
		knobVoltDivCH1.setDisable(true);
		knobVoltDivCH2.setDisable(true);
		spVoltDivCH1Fine.setDisable(true);
		spVoltDivCH2Fine.setDisable(true);
		tbCH1ctrl.setDisable(true);
		tbCH2ctrl.setDisable(true);
		knobVerticalPosCH1.setDisable(true);
		knobVerticalPosCH2.setDisable(true);
		//Menu Item
		menuItemExportCSV.setDisable(true);
		//Configuration
		subMenuGND.setDisable(true);
		subMenuTriggerLine.setDisable(true);
		//Snapshot
		btnSnapshot.setDisable(true);

		return;
	}


	private void enableUIComponents() {
		//Horizontal
		knobTimeDiv.setDisable(false);
		//Run Control
		tbSingle.setDisable(false);
		tbRunStop.setDisable(false);
		btnDefault.setDisable(false);
		btnAuto.setDisable(false);
		//Trigger
		cbTriggerSource.setDisable(false);
		cbTriggerSlope.setDisable(false);
		spTriggerLvl.setDisable(false);
		//Vertical
		knobVoltDivCH1.setDisable(false);
		knobVoltDivCH2.setDisable(false);
		spVoltDivCH1Fine.setDisable(false);
		spVoltDivCH2Fine.setDisable(false);
		tbCH1ctrl.setDisable(false);
		tbCH2ctrl.setDisable(false);
		knobVerticalPosCH1.setDisable(false);
		knobVerticalPosCH2.setDisable(false);
		//Menu Item
		menuItemExportCSV.setDisable(false);
		//Configuration
		subMenuGND.setDisable(false);
		subMenuTriggerLine.setDisable(false);
		//subMenuChannel.setDisable(false);
		//Snapshot
		btnSnapshot.setDisable(false);

		return;
	}


	private void loadMenuItemIcons(InputStream isImage, ControlUtilities ctrlUtil) {		
		isImage = Controller.class.getResourceAsStream("/images/ExitIcon.png");		
		menuItemExit.setGraphic(new ImageView(new Image(isImage, 15, 15, true, true)));		
		ctrlUtil.closeIamgeStream(isImage);	

		isImage = Controller.class.getResourceAsStream("/images/HelpIcon.png");		
		menuItemHelp.setGraphic(new ImageView(new Image(isImage, 15, 15, true, true)));		
		ctrlUtil.closeIamgeStream(isImage);	

		isImage = Controller.class.getResourceAsStream("/images/ExportCSV_Icon.png");		
		menuItemExportCSV.setGraphic(new ImageView(new Image(isImage, 15, 15, true, true)));	
		ctrlUtil.closeIamgeStream(isImage);		

		isImage = Controller.class.getResourceAsStream("/images/ExtendedViewIcon.png");		
		menuItemAdvancedFunctions.setGraphic(new ImageView(new Image(isImage, 15, 15, true, true)));		
		ctrlUtil.closeIamgeStream(isImage);

		isImage = Controller.class.getResourceAsStream("/images/MediaCenter.png");		
		menuItemMediaCenter.setGraphic(new ImageView(new Image(isImage, 15, 15, true, true)));		
		ctrlUtil.closeIamgeStream(isImage);

		isImage = Controller.class.getResourceAsStream("/images/AboutLogo.png");		
		menuItemAbout.setGraphic(new ImageView(new Image(isImage, 15, 15, true, true)));		
		ctrlUtil.closeIamgeStream(isImage);

		isImage = Controller.class.getResourceAsStream("/images/FeedbackLogo.png");		
		menuItemFeedback.setGraphic(new ImageView(new Image(isImage, 15, 15, true, true)));		
		ctrlUtil.closeIamgeStream(isImage);

		isImage = Controller.class.getResourceAsStream("/images/CHConfigIcon.png");		
		menuItemChannel.setGraphic(new ImageView(new Image(isImage, 15, 15, true, true)));		
		ctrlUtil.closeIamgeStream(isImage);

		isImage = Controller.class.getResourceAsStream("/images/USB_Logo.png");		
		menuItemConfigDevice.setGraphic(new ImageView(new Image(isImage, 15, 15, true, true)));		
		ctrlUtil.closeIamgeStream(isImage);

		isImage = Controller.class.getResourceAsStream("/images/SnapshotSymbol.png");		
		menuItemSnapshot.setGraphic(new ImageView(new Image(isImage, 15, 15, true, true)));		
		ctrlUtil.closeIamgeStream(isImage);

		isImage = Controller.class.getResourceAsStream("/images/ExportCSV_Icon.png");	
		menuItemCSVExport.setGraphic(new ImageView(new Image(isImage, 15, 15, true, true)));
		ctrlUtil.closeIamgeStream(isImage);			
	}


	private void setMeasToNoSignal(ArrayList<Label> item) {
		for(int i=1; i<item.size(); i+=2) {
			item.get(i).setText("No Sig.");
		}		
		return;
	}


	/**
	 * @param text
	 */
	private void writeToStatusField(String text) {
		if(text != null) {
			if(!Platform.isFxApplicationThread()) {
				Platform.runLater(() -> {
					taStatusField.appendText(text + "\n");
				});				
			} else {
				taStatusField.appendText(text + "\n");
			}
		}
		return;
	}

	/**
	 * @return the scopeSettings
	 */
	public ScopeSettings getScopeSettings() {
		return scopeSettings;
	}


	/**
	 * @author Markus Lechner
	 *
	 */
	private class UserInterfaceProc implements Runnable {	
		private final int MAX_MES_MSQ_DUMP = 10;

		private boolean isUIdisabled = true;

		private Thread controllerUIThread = null;	
		private MSQ_Handler msq_Handler = null;

		private MeasurementMSG measurementMSG = new MeasurementMSG();
		private UIControlMSG uiControlMSG = new UIControlMSG();

		/**
		 * 
		 */
		public UserInterfaceProc() {
			msq_Handler = MSQ_Handler.getInstance();			
		}


		@Override
		public void run() {			
			MSQ_Handler msq_Handler = MSQ_Handler.getInstance();
			GenericMessage genericMessage;

			introduceMediaCenter();			
			sendInitData();		

			while(true) {				
				if(!msq_Handler.getMsqUIReq().isEmpty()) {
					genericMessage = msq_Handler.getMsqUIReq().remove();

					switch (genericMessage.getMsgCode()) {
					case MEASUREMENT_RESP:
						processMeasurementResponse(genericMessage);
						break;

					case UIControl:
						processUIControl(genericMessage);
						break;

					case DEVICE_INTER_UPD:
						DeviceMSG deviceMSG = new DeviceMSG();
						deviceMSG = (DeviceMSG)genericMessage;

						//Check if the serial port list has changed
						if(deviceMSG.getPortList() != null) {
							if(!deviceMSG.getPortList().equals(portList)) {
								portList.clear();
								portList.addAll(deviceMSG.getPortList());
								System.out.println("Serial Port: New connected Device(s) detected: " + portList);
							}
						}
						break;

					default:
						System.err.println("INVALID MSG");
						break;
					}
				} 

				try {
					Thread.sleep(25);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
		}


		/**
		 * @param genericMessage
		 */
		private void processUIControl(GenericMessage genericMessage) {
			FutureTask<Void> userInterfaceProcTask = new FutureTask<Void>(() -> {
				uiControlMSG = (UIControlMSG)genericMessage;

				if(isUIdisabled) {
					AccessUIComponents(Components.ENABLE);	
				}

				if(uiControlMSG.getStatus() != null) {
					if(uiControlMSG.getStatus().contains("Sample Rate:")) {
						String sampleRate = uiControlMSG.getStatus().replaceAll("Sample Rate: ", "");						
						lblSampleRateValue.setText(sampleRate);
					} else {
						writeToStatusField(uiControlMSG.getStatus());
					}					
				}

				if(uiControlMSG.isClearSignalSeries()) {
					if(!lcSignalGraph.getData().get(SignalGraph.SERIES_CH1_POS).getData().isEmpty() || 
							!lcSignalGraph.getData().get(SignalGraph.SERIES_CH2_POS).getData().isEmpty()) {

						signalGraph.clearSeries(SignalGraph.CLEAR_SIGNAL_SERIES);
					}

					if(tbCH1ctrl.isSelected()) {
						setMeasToNoSignal(measurementLabelsCH1);
					}

					if(tbCH2ctrl.isSelected()) {
						setMeasToNoSignal(measurementLabelsCH2);
					}
				}

				if(uiControlMSG.getSingleShot() == ProcState.DONE) {
					singleShotState = ProcState.DONE;

					if(tbSingle.isSelected()) {
						tbSingle.fire();
					}					
				}

				if(uiControlMSG.getActivityCH1() != Components.NONE) {
					if((uiControlMSG.getActivityCH1() == Components.ENABLE && !tbCH1ctrl.isSelected()) ||
							(uiControlMSG.getActivityCH1() == Components.DISABLE && tbCH1ctrl.isSelected())) {
						tbCH1ctrl.fire();	
					}
				}

				if(uiControlMSG.getActivityCH2() != Components.NONE) {
					if((uiControlMSG.getActivityCH2() == Components.ENABLE && !tbCH2ctrl.isSelected()) ||
							(uiControlMSG.getActivityCH2() == Components.DISABLE && tbCH2ctrl.isSelected())) {
						tbCH2ctrl.fire();	
					}
				}

				if(uiControlMSG.getVerticalPosCH1() != uiControlMSG.UNINITIALIZED_DOUB) {
					knobVerticalPosCH1.setValue(uiControlMSG.getVerticalPosCH1());
				}

				if(uiControlMSG.getVerticalPosCH2() != uiControlMSG.UNINITIALIZED_DOUB) {
					knobVerticalPosCH2.setValue(uiControlMSG.getVerticalPosCH2());
				}

				if(uiControlMSG.getVerticalScaleCH1() != uiControlMSG.UNINITIALIZED_DOUB) {
					spVoltDivCH1Fine.getValueFactory().setValue(uiControlMSG.getVerticalScaleCH1());
				}				

				if(uiControlMSG.getVerticalScaleCH2() != uiControlMSG.UNINITIALIZED_DOUB) {
					spVoltDivCH2Fine.getValueFactory().setValue(uiControlMSG.getVerticalScaleCH2());
				}	

				if (uiControlMSG.getHorizontalScale() != uiControlMSG.UNINITIALIZED_DOUB) {
					knobTimeDiv.setValue(uiControlMSG.getHorizontalScale());	
				}

				if(uiControlMSG.getTriggerSource() != null) {
					cbTriggerSource.getSelectionModel().select(uiControlMSG.getTriggerSource());			
				}

				if(uiControlMSG.getTriggerSlope() != uiControlMSG.UNINITIALIZED_INT) {
					cbTriggerSlope.getSelectionModel().select(uiControlMSG.getTriggerSlope());
				}

				if(uiControlMSG.getTriggerValue() != uiControlMSG.UNINITIALIZED_DOUB) {
					spTriggerLvl.getValueFactory().setValue(uiControlMSG.getTriggerValue());
				}

				if(isUIdisabled) {
					AccessUIComponents(Components.DISABLE);
				}

				if(uiControlMSG.getComponents() != Components.NONE) {
					if(uiControlMSG.getComponents() == Components.DISABLE) {
						isUIdisabled = true;
					} else if(uiControlMSG.getComponents() == Components.ENABLE) {
						isUIdisabled = false;
					}
					AccessUIComponents(uiControlMSG.getComponents());
				}

				if(uiControlMSG.isIntroduceFeedback()) {
					menuItemFeedback.fire();
				}
			}, null);

			Platform.runLater(userInterfaceProcTask);

			try {
				userInterfaceProcTask.get();
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return;
		}


		private void processMeasurementResponse(GenericMessage genericMessage) {
			//If too many messages were received, all old measurement messages (dump) are removed.
			if(msq_Handler.getMsqUIReq().size() > MAX_MES_MSQ_DUMP) {
				while(!msq_Handler.getMsqUIReq().isEmpty()) {						
					if(msq_Handler.getMsqUIReq().element().getMsgCode() == MessageCode.MEASUREMENT_RESP) {
						genericMessage = msq_Handler.getMsqUIReq().remove();
					} else {
						break;
					}											
				}
			}

			measurementMSG = (MeasurementMSG)genericMessage;

			FutureTask<Void> userInterfaceMeasurementTask = new FutureTask<Void>(() -> {
				int index = 0;

				if((measurementMSG.getChannelNr() == OpenLabDevice.CH1) && (measurementMSG.getMeasurement().size() <= measurementLabelsCH1.size())) {
					for(HashMap.Entry<Measurement, String> entry : measurementMSG.getMeasurement().entrySet()) {
						measurementLabelsCH1.get(index).setText(entry.getKey().getValue());
						index++;	
						measurementLabelsCH1.get(index).setText(entry.getValue());
						index++;
					}
				} else if ((measurementMSG.getChannelNr() == OpenLabDevice.CH2) && (measurementMSG.getMeasurement().size() <= measurementLabelsCH2.size())) {
					for(HashMap.Entry<Measurement, String> entry : measurementMSG.getMeasurement().entrySet()) {
						measurementLabelsCH2.get(index).setText(entry.getKey().getValue());
						index++;	
						measurementLabelsCH2.get(index).setText(entry.getValue());
						index++;
					}
				}

			}, null);

			Platform.runLater(userInterfaceMeasurementTask);

			try {
				userInterfaceMeasurementTask.get();
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return;
		}


		private void initUserInterfaceThread() {
			controllerUIThread = new Thread(this);
			controllerUIThread.setName("UserInterface.Proc");
			controllerUIThread.setDaemon(true);
			controllerUIThread.start();
			return;
		}


		private void sendInitData() {
			sendSampleRateSettingsMSG();
			sendChannelControlMSG(OpenLabDevice.CH1);
			sendChannelControlMSG(OpenLabDevice.CH2);
			sendTriggerSettingsMSG();
			sendMeasurementMSG(OpenLabDevice.CH1);
			sendMeasurementMSG(OpenLabDevice.CH2);
			return;
		}


		private void sendDeviceInterface(String selectedPort) {
			DeviceMSG deviceMSG = new DeviceMSG();

			deviceMSG.setMsgCode(MessageCode.DEVICE_INTER_REQ);
			deviceMSG.setSelectedPort(selectedPort);

			msq_Handler.getMsqProcessReq().add(deviceMSG);
			return;
		}


		private void sendChannelControlMSG(int channelNr) {			
			ChannelControlMSG channelControl = new ChannelControlMSG();

			if (channelNr == OpenLabDevice.CH1) {
				channelControl.setSignalInverted(Boolean.parseBoolean(getScopeSettings().
						getChannelConfig().get(ScopeSettings.CFG_INVERT_CH1)));	

				channelControl.setProbeAtten(Double.parseDouble(getScopeSettings().
						getChannelConfig().get(ScopeSettings.CFG_PROBE_ATT_CH1)));

				channelControl.setVerticalScale(spVoltDivCH1Fine.getValue());
				channelControl.setVerticalPos(knobVerticalPosCH1.getValue());
				channelControl.setChannelActive(tbCH1ctrl.isSelected());
			} else if (channelNr == OpenLabDevice.CH2) {
				channelControl.setSignalInverted(Boolean.parseBoolean(getScopeSettings().
						getChannelConfig().get(ScopeSettings.CFG_INVERT_CH2)));	

				channelControl.setProbeAtten(Double.parseDouble(getScopeSettings().
						getChannelConfig().get(ScopeSettings.CFG_PROBE_ATT_CH2)));

				channelControl.setVerticalScale(spVoltDivCH2Fine.getValue());
				channelControl.setVerticalPos(knobVerticalPosCH2.getValue());
				channelControl.setChannelActive(tbCH2ctrl.isSelected());
			} else {
				return;
			}

			channelControl.setMsgCode(MessageCode.CHANNEL_CTRL);
			channelControl.setChannelNr(channelNr);

			msq_Handler.getMsqProcessReq().add(channelControl);
			return;
		}


		private void sendTriggerSettingsMSG() {
			TriggerMSG triggerSettings = new TriggerMSG();

			triggerSettings.setMsgCode(MessageCode.TRIGGER);			
			triggerSettings.setTriggerSlope(cbTriggerSlope.getSelectionModel().getSelectedIndex());
			triggerSettings.setHoldOff(0);
			triggerSettings.setTriggerValue(spTriggerLvl.getValue());
			triggerSettings.setTriggerSorce(cbTriggerSource.getValue());

			if(cbTriggerSource.getValue().equals("CH1")) {
				triggerSettings.setSignalInverted(Boolean.parseBoolean(getScopeSettings().
						getChannelConfig().get(ScopeSettings.CFG_INVERT_CH1)));
			} else if (cbTriggerSource.getValue().equals("CH2")) {
				triggerSettings.setSignalInverted(Boolean.parseBoolean(getScopeSettings().
						getChannelConfig().get(ScopeSettings.CFG_INVERT_CH2)));
			} else {
				triggerSettings.setSignalInverted(false);
			}

			msq_Handler.getMsqProcessReq().add(triggerSettings);
			return;
		}


		private void sendSampleRateSettingsMSG() {
			SampleRateMSG sampleRateSettings = new SampleRateMSG();

			sampleRateSettings.setMsgCode(MessageCode.SAMPLE_RATE);
			sampleRateSettings.setHorizontalScaling(knobTimeDiv.getValue());			

			msq_Handler.getMsqProcessReq().add(sampleRateSettings);
			return;
		}


		private void sendMeasurementMSG(int channelNr) {
			MeasurementMSG measurementMSG = new MeasurementMSG();

			if(channelNr == OpenLabDevice.CH1) {
				measurementMSG.setChannelNr(channelNr);
			} else if (channelNr == OpenLabDevice.CH2) {
				measurementMSG.setChannelNr(channelNr);
			} else {
				return;
			}

			measurementMSG.setMsgCode(MessageCode.MEASUREMENT);		
			measurementMSG.getMeasurement().put(Measurement.Vpp, "");
			measurementMSG.getMeasurement().put(Measurement.Vmin, "");
			measurementMSG.getMeasurement().put(Measurement.Vmax, "");
			measurementMSG.getMeasurement().put(Measurement.Freq, "");

			msq_Handler.getMsqProcessReq().add(measurementMSG);	
			return;
		}	


		private void sendSnapShotMSG(String name, String path, String format, String color, WritableImage image) {
			SnapshotMSG snapshotMSG = new SnapshotMSG();

			snapshotMSG.setMsgCode(MessageCode.SNAPSHOT);			
			snapshotMSG.setFileName(name);
			snapshotMSG.setFilePath(path);
			snapshotMSG.setFileFormat(format);
			snapshotMSG.setColor(color);
			snapshotMSG.setImage(image);

			msq_Handler.getMsqProcessReq().add(snapshotMSG);	
			return;
		}


		/**
		 * @param genericEvent
		 */
		private void sendGenericEventMSG(GenericEvent genericEvent) {
			GenericEventMSG genericEventMSG = new GenericEventMSG();

			genericEventMSG.setMsgCode(MessageCode.GENERIC_EVENT);
			genericEventMSG.setGenericEvent(genericEvent);

			msq_Handler.getMsqProcessReq().add(genericEventMSG);
			return;
		}


		/**
		 * @param name
		 * @param path
		 * @param langFormat
		 */
		private void sendExportCSVMSG(String name, String path, String langFormat) {
			ExportCSVMSG exportCSVMSG = new ExportCSVMSG();

			exportCSVMSG.setMsgCode(MessageCode.EXPORT_CSV);			
			exportCSVMSG.setFileName(name);
			exportCSVMSG.setFilePath(path);
			exportCSVMSG.setLangFormat(langFormat);

			msq_Handler.getMsqProcessReq().add(exportCSVMSG);	
			return;
		}


		/**
		 * 
		 */
		private void introduceMediaCenter() {
			ScopeConfig scopeConfiguration = ScopeConfig.getInstance();

			//Check if the Media Center has to be introduced
			if(Boolean.parseBoolean(scopeConfiguration.getScopeProperties().get(PropIndex.INTRODUCE_MEDIA_CENTER_DIALOG).toString())) {
				Platform.runLater(() -> {
					menuItemMediaCenter.fire();
				});						
			}			
			return;
		}
	}
}

/* EOF */