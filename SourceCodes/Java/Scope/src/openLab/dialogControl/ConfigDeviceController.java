package openLab.dialogControl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * @author Markus Lechner
 *
 */
public class ConfigDeviceController implements Initializable {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ImageView ivLogo;

    @FXML
    private Label lblSerialPort;

    @FXML
    private Pane paneSettings;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnConnect;

    private String serialPort = null;
    
    private List<String> portList = Collections.synchronizedList(new ArrayList<String>(5));

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		/*** Snapshot Logo ***/		
		InputStream isImage = ConfigDeviceController.class.getResourceAsStream("/images/USB_Logo.png");			
		final Image imgSnapshotIcon = new Image(isImage);			

		try {
			isImage.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ivLogo.setImage(imgSnapshotIcon);

		//Highlight (Bold) the first selected parameter
		lblSerialPort.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		
		/*** Serial Port Parameter ***/
		GridPane gridSerialPort = new GridPane();
		gridSerialPort.setHgap(15.0);
		gridSerialPort.setVgap(15.0);
		gridSerialPort.setPadding(new Insets(35.0, 10.0, 10.0, 15.0));

		/*** Section probe attenuation ***/
		Label lblSerialPort = new Label("Select Serial Port:");
		gridSerialPort.add(lblSerialPort, 0, 0);	
		
		final ComboBox<String> cbSerialPortSel = new ComboBox<String>();
		
		if(portList != null) {
			cbSerialPortSel.getItems().addAll(portList);
			cbSerialPortSel.getSelectionModel().selectFirst();
		}

		gridSerialPort.add(cbSerialPortSel, 1, 0);
		
		Button btnRefresh = new Button("Refresh");
		gridSerialPort.add(btnRefresh, 2, 0);	
		
		/*** Action Listener - Serial Port List Refresh Button ***/
		btnRefresh.setOnAction((actionEvent)-> {
			if(portList != null) {
				cbSerialPortSel.getItems().clear();
				cbSerialPortSel.getItems().addAll(portList);
				cbSerialPortSel.getSelectionModel().selectFirst();
			}
		});		
		
		//Default: Settings for the color are loaded and shown
		paneSettings.getChildren().add(gridSerialPort);		
		
		/*** Action Listener - Apply ***/
		btnConnect.setOnAction((ActionEvent)-> {
			if(cbSerialPortSel.getSelectionModel().getSelectedItem() != null) {
				serialPort = new String(cbSerialPortSel.getSelectionModel().getSelectedItem());	
			}					
			btnCancel.fire();
		});

		/*** Action Listener for AnchorPane Window ***/
		btnCancel.setOnAction((ActionEvent)-> {
			Stage stage = (Stage) anchorPane.getScene().getWindow();
			stage.close();
		});		
	}

	/**
	 * @return the serialPort
	 */
	public String getSerialPort() {
		return serialPort;
	}

	/**
	 * @param portList the portList to set
	 */
	public void setPortList(List<String> portList) {
		this.portList = portList;
	}
}

/* EOF */