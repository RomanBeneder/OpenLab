package openLab.launcher.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import desktop_support.WebBrowser;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import openLab.launcher.dialog.StandardDialog;
import openLab.launcher.property.PropertyKey;
import openLab.launcher.resource.MSQ_Management;
import openLab.packet.ErrorCode;
import openLab.packet.GenericMessage;
import openLab.packet.MessageCode;
import openLab.packet.OpenLabSignalTools;
import openLab.packet.SignalToolCMD;
import openLab.packet.SignalToolCMD_MSG;

public class Controller implements Initializable {
	@FXML
	private MenuBar menuBar;

	@FXML
	private MenuItem menuItemExit;

	@FXML
	private Button btnOscilloscope;

	@FXML
	private Button btnSignalGen;

	@FXML
	private Button btnMultimeter;

	@FXML
	private Button btnCurVoltSource;

	@FXML
	private Button btnLogicanalyzer;

	@FXML
	private ImageView ivOpenLab;

	@FXML
	private ImageView ivFHTW;

	@FXML
	private ImageView ivMA23;

	@FXML
	private TextArea taStatusField;

	private Stage primaryStage = null;    
	private Properties launcherProperties = new Properties();
	private UserInterfaceProc userInterfaceProc = null;

	/**
	 * @param primaryStage
	 */
	public Controller(Stage primaryStage, Properties launcherProperties) {
		this.primaryStage = primaryStage;
		this.launcherProperties = launcherProperties;
	}


	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		userInterfaceProc = new UserInterfaceProc();

		InputStream isImage = Controller.class.getResourceAsStream("/image/ExitIcon.png");		
		menuItemExit.setGraphic(new ImageView(new Image(isImage, 15, 15, true, true)));		
		closeIamgeStream(isImage);

		menuItemExit.setOnAction((ActionEvent)-> {
			Stage primaryStage = (Stage)menuBar.getScene().getWindow();
			primaryStage.fireEvent(new WindowEvent(primaryStage,WindowEvent.WINDOW_CLOSE_REQUEST));
		});

		btnOscilloscope.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {				
				userInterfaceProc.sendMSGtoProcessSupervisor(OpenLabSignalTools.SignalTool.OSCILLOSCOPE, SignalToolCMD.EXECUTE);				
			}
		});

		btnSignalGen.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				userInterfaceProc.sendMSGtoProcessSupervisor(OpenLabSignalTools.SignalTool.SIGNALGENERATOR, SignalToolCMD.EXECUTE);
			}
		});

		setImageListener(ivOpenLab, launcherProperties.getProperty(PropertyKey.WEBSITE_OPENLAB));
		setImageListener(ivFHTW, launcherProperties.getProperty(PropertyKey.WEBSITE_FH_TECHNIKUM));
		setImageListener(ivMA23, launcherProperties.getProperty(PropertyKey.WEBSITE_MA23));

		writeToStatusField("  ***** OpenLab Signal-Toolkit *****");

		userInterfaceProc.executeUserInterfaceController();
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
	 * @param is
	 */
	public void closeIamgeStream(InputStream is) {
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}


	private class UserInterfaceProc implements Runnable {
		private Thread controllerUIThread = null;


		@Override
		public void run() {
			/*
			 * Create an instance of the MSQ Management Service
			 * This Object is utilized for the message passing
			 * between the Controller and the ProcessSupervisor class
			 */
			MSQ_Management msqManagement = MSQ_Management.getInstance();

			while(true) {
				checkControllerUIMessageQueue(msqManagement);
				try	{
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}


		public void executeUserInterfaceController() {
			controllerUIThread = new Thread(this);
			controllerUIThread.setName("User.Interface.Controller");
			controllerUIThread.setDaemon(true);
			controllerUIThread.start();	
		}


		/**
		 * @param msqManagement
		 */
		private void checkControllerUIMessageQueue(MSQ_Management msqManagement) {
			if(!msqManagement.getMsqControllerUI().isEmpty()) {
				GenericMessage genericMessage = msqManagement.getMsqControllerUI().remove();


				switch (genericMessage.getMsgCode()) {
				case SIGNAL_TOOL_CMD:
					SignalToolCMD_MSG signalToolCMD_MSG = new SignalToolCMD_MSG();
					signalToolCMD_MSG = (SignalToolCMD_MSG)genericMessage;									

					if(signalToolCMD_MSG.getSignalToolCommand() != null) {
						executeRequest(signalToolCMD_MSG);	
					}									

					if(signalToolCMD_MSG.getStatus() != null) {
						writeToStatusField(signalToolCMD_MSG.getStatus());
					}

					if(signalToolCMD_MSG.getErrorCode() != null && signalToolCMD_MSG.getSignalTool() != null) {
						evaluateError(signalToolCMD_MSG.getSignalTool(), signalToolCMD_MSG.getErrorCode());
					}

					break;

				default:
					break;
				}
			}
			return;
		}		


		private void executeRequest(SignalToolCMD_MSG signalToolCMD_MSG) {
			switch (signalToolCMD_MSG.getSignalToolCommand()) {
			case ENABLE:			
				setButtonState(signalToolCMD_MSG.getSignalTool(), false);				
				break;
			case DISABLE:
				setButtonState(signalToolCMD_MSG.getSignalTool(), true);	
				break;

			default:
				break;
			}			
			return;
		}


		private void setButtonState(OpenLabSignalTools.SignalTool signalTool, boolean setDisable) {		
			Platform.runLater(() -> {
				Button tempButton = null;

				switch (signalTool) {
				case OSCILLOSCOPE:
					tempButton = btnOscilloscope;
					break;
				case SIGNALGENERATOR:
					tempButton = btnSignalGen;
					break;
				case MULTIMETER:
					tempButton = btnMultimeter;
					break;
				case CURRENT_VOLTAGE_SOURCE:
					tempButton = btnCurVoltSource;
					break;
				case LOGICANALYZER:
					tempButton = btnLogicanalyzer;
					break;
				default:
					return;
				}			
				tempButton.setDisable(setDisable);								
			});
		}


		private void evaluateError(OpenLabSignalTools.SignalTool signalTool, ErrorCode errorCode) {
			Stage primaryStage = (Stage)menuBar.getScene().getWindow();
			StandardDialog standardDialog = new StandardDialog(primaryStage);

			switch (errorCode) {
			case TOOL_NOT_AVAILABLE:
				standardDialog.setTitle("Error: No such File");
				standardDialog.setHeaderText(signalTool.toString());
				standardDialog.setContentText("The corresponding Signal-Toolkit can not be found!");					
				showErrorDialog(standardDialog);
				break;
			case PERMISSION_DENIED:
				standardDialog.setTitle("Error: Permisson Denied");
				standardDialog.setHeaderText(signalTool.toString());
				standardDialog.setContentText("The corresponding Signal-Toolkit can not be launched!");					
				showErrorDialog(standardDialog);						
				break;
			case PROCESS_CRASHED:
				standardDialog.setTitle("Fatal Error: Process Crashed");
				standardDialog.setHeaderText(signalTool.toString());
				standardDialog.setContentText("Consider the operating manual for further details!");					
				showErrorDialog(standardDialog);						
				break;
			default:
				break;
			}		
			return;
		}


		/**
		 * @param standardDialog
		 */
		private void showErrorDialog(StandardDialog standardDialog) {
			Platform.runLater(() -> {
				standardDialog.createDialog(AlertType.ERROR);
			});
			return;
		}


		/**
		 * @param signalTool
		 * @param command
		 */
		private void sendMSGtoProcessSupervisor(OpenLabSignalTools.SignalTool signalTool, SignalToolCMD signalToolCMD) {
			SignalToolCMD_MSG signalToolCMD_MSG = new SignalToolCMD_MSG();
			signalToolCMD_MSG.setMsgCode(MessageCode.SIGNAL_TOOL_CMD);

			signalToolCMD_MSG.setSignalTool(signalTool);
			signalToolCMD_MSG.setSignalToolCommand(signalToolCMD);

			MSQ_Management msqManagement = MSQ_Management.getInstance();
			msqManagement.getMsqProcessSupervisor().add(signalToolCMD_MSG);		
			return;
		}
	}
}

/* EOF */
