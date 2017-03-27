package embeddedMediaCenter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import desktop_support.FileAssistent;
import desktop_support.WebBrowser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import openLab.properties.PropIndex;


/**
 * @author Markus Lechner
 *
 */
public class MediaCenterController implements Initializable {
	@FXML
	private AnchorPane anchorPane;

	@FXML
	private ImageView ivLogo;

	@FXML
	private CheckBox cbSkipDia;

	@FXML
	private Button btnClose;

	@FXML
	private Button btnStartVid;

	@FXML
	private AnchorPane apBasicVid;

	@FXML
	private AnchorPane apAdvancedVid;

	public final static String BASIC_VID = new String("BASIC_VID");
	public final static String ADV_VID = new String("ADV_VID");
	public final String BASIC_VIDEO_TOOLTIP_CONTENT = new String("Video Content:\n"
			+ "Hardware set-up\n"+"Device connection\n"+"Probe compensation\n"+"Y-Axis variation\n"
			+ "Volt/Div and Time/Div\n"+"Trigger settings\n"+"Run Controls");
	
	public final String ADV_VIDEO_TOOLTIP_CONTENT = new String("Video Content:\n"
			+ "Equivalent Time Sampling\n"+"View Adjustment\n"+"Channel Settings\n"+"Snapshot\n"
			+ "CSV Export & Import\n"+"Feedback\n");

	private  Map <String, ArrayList<String>> videoFiles = new HashMap<String, ArrayList<String>>();

	private ArrayList<String> vidFilesBasic = null;
	private ArrayList<String> vidFilesAdv = null;

	private InputStream isImage = null;
	private Properties scopeProperties = null;
	private Label selectedLabel = null;

	public MediaCenterController(Properties scopeProperties, Map <String, ArrayList<String>> videoFiles) {
		this.scopeProperties = scopeProperties;
		this.videoFiles = videoFiles;
	}


	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		/*** Media Center Logo ***/		
		isImage = MediaCenterController.class.getResourceAsStream("/images/MediaCenter.png");			
		final Image imgMediaCenterIcon = new Image(isImage);			

		ivLogo.setImage(imgMediaCenterIcon);
		closeIamgeStream(isImage);		

		GridPane gridBasicVideo = new GridPane();
		gridBasicVideo.setHgap(10.0);
		gridBasicVideo.setVgap(5.0);
		gridBasicVideo.setPadding(new Insets(5.0, 5.0, 5.0, 5.0));	

		vidFilesBasic = new ArrayList<String>();

		//Disable the Start Video Button if no videos were found
		if(videoFiles.get(BASIC_VID) == null && videoFiles.get(ADV_VID) == null) {
			btnStartVid.setDisable(true);
		}

		if(videoFiles.get(BASIC_VID) != null) {
			vidFilesBasic.addAll(videoFiles.get(BASIC_VID));
		}

		showAvailVideo(vidFilesBasic, gridBasicVideo);
		apBasicVid.getChildren().add(gridBasicVideo);

		GridPane gridAdvVideo = new GridPane();	
		gridAdvVideo.setHgap(10.0);
		gridAdvVideo.setVgap(5.0);
		gridAdvVideo.setPadding(new Insets(5.0, 5.0, 5.0, 5.0));	

		vidFilesAdv = new ArrayList<String>();

		if(videoFiles.get(ADV_VID) != null) {
			vidFilesAdv.addAll(videoFiles.get(ADV_VID));
		}

		showAvailVideo(vidFilesAdv, gridAdvVideo);
		apAdvancedVid.getChildren().add(gridAdvVideo);

		if(!Boolean.parseBoolean(scopeProperties.get(PropIndex.INTRODUCE_MEDIA_CENTER_DIALOG).toString())) {
			cbSkipDia.setSelected(true);
		} 		

		/*** Action Listener for Start Video ***/
		btnStartVid.setOnAction((ActionEvent)-> {
			if(getSelectedVideoName() == null) {
				return;
			}

			File file = new File(getSelectedVideoName());
			FileAssistent fileAssitent = new FileAssistent(file);
			fileAssitent.executeFileAssistent();
		});


		/*** Action Listener for AnchorPane Window ***/
		btnClose.setOnAction((ActionEvent)-> {
			if(cbSkipDia.isSelected()) {
				scopeProperties.put(PropIndex.INTRODUCE_MEDIA_CENTER_DIALOG, "false");
			} else {
				scopeProperties.put(PropIndex.INTRODUCE_MEDIA_CENTER_DIALOG, "true");
			}

			Stage stage = (Stage) anchorPane.getScene().getWindow();
			stage.close();		
		});
	}


	/**
	 * @param vidFilesBasic
	 * @param gridVideo
	 */
	private void showAvailVideo(ArrayList<String> vidFilesBasic, GridPane gridVideo) {
		for(int i=0; i<vidFilesBasic.size(); i++) {
			String fileName = vidFilesBasic.get(i);
			int idx = fileName.replaceAll("\\\\", "/").lastIndexOf("/");

			if(idx >= 0) {
				fileName = fileName.substring(idx + 1);
			} 

			final Label lblVidItem = new Label(fileName);			
			lblVidItem.setPrefWidth(420.0);
			setSelectionListener(lblVidItem);			
			gridVideo.add(lblVidItem, 0, i);

			if(fileName.equals(scopeProperties.get(PropIndex.LOCAL_VIDEO_BASIC_INTRO))) {
				Label lblVidLink = new Label("ONLINE in FHD");
				lblVidItem.setTooltip(new Tooltip(BASIC_VIDEO_TOOLTIP_CONTENT));
				setHyperlinkListener(lblVidLink, scopeProperties.get(PropIndex.ONLINE_VIDEO_BASIC_INTRO).toString());
				gridVideo.add(lblVidLink, 1, i);
			} else if(fileName.equals(scopeProperties.get(PropIndex.LOCAL_VIDEO_ADV_INTRO))) {
				Label lblVidLink = new Label("ONLINE in FHD");
				lblVidItem.setTooltip(new Tooltip(ADV_VIDEO_TOOLTIP_CONTENT));
				setHyperlinkListener(lblVidLink, scopeProperties.get(PropIndex.ONLINE_VIDEO_ADV_INTRO).toString());
				gridVideo.add(lblVidLink, 1, i);
			}
		}
		return;
	}


	/**
	 * @param label
	 */
	private void setSelectionListener(Label label) {
		label.setOnMouseClicked((ActionEvent)-> {
			if(selectedLabel != null) {
				selectedLabel.setStyle(null);
			}    	

			label.setStyle("-fx-border-color:#0000A0; -fx-border-width: 2;");
			selectedLabel = label;
		});
		return;
	}


	/**
	 * @param label
	 * @param WebSite
	 */
	private void setHyperlinkListener(Label label, String WebSite) {
		label.setStyle("-fx-text-fill: #0000A0; -fx-underline: true;");

		label.setOnMouseEntered((value) -> {
			anchorPane.getScene().getWindow().getScene().setCursor(Cursor.HAND);
		});

		label.setOnMouseExited((value) -> {
			anchorPane.getScene().getWindow().getScene().setCursor(Cursor.DEFAULT);
		});

		label.setOnMouseClicked((value) -> {
			WebBrowser webBrowser = new WebBrowser(WebSite);
			webBrowser.executeWebBrowser();
		});

		return;
	}


	/**
	 * @return
	 */
	private String getSelectedVideoName() {
		String selVideo;

		if((selVideo = getSelectedItem(vidFilesBasic)) != null) {
			return selVideo;
		} else if((selVideo = getSelectedItem(vidFilesAdv)) != null) {
			return selVideo;
		}		
		return null;
	}


	/**
	 * @param videoFiles
	 * @return
	 */
	private String getSelectedItem(ArrayList<String> videoFiles) {
		if(selectedLabel != null) {
			for(int i=0; i<videoFiles.size(); i++) {
				if(videoFiles.get(i).contains(selectedLabel.getText())) {
					return videoFiles.get(i);
				}
			}	
		}	
		return null;
	}


	/**
	 * @param is
	 */
	private void closeIamgeStream(InputStream is) {
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
}

/* EOF */