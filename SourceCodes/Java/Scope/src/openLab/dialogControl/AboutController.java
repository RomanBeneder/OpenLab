package openLab.dialogControl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import desktop_support.WebBrowser;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import openLab.controller.ScopeSettings;
import openLab.properties.PropIndex;
import openLab.properties.ScopeConfig;
import openLab.resource.OpenLabDevice;


/**
 * @author Markus Lechner
 *
 */
public class AboutController implements Initializable {
	@FXML
	private AnchorPane anchorPane;

	@FXML
	private ImageView ivLogo;

	@FXML
	private Label lblVersion;

	@FXML
	private Label lblProjectTeam;

	@FXML
	private Label lblSponsor;

	@FXML
	private Label lblProjectDesc;

	@FXML
	private Label lblDisclaimer;

	@FXML
	private Label lblSpecification;

	@FXML
	private ScrollPane spSettings;

	private final String SPONSOR_CONTENT = ("The project is financed by public funds of MA23 City of Vienna,\n"
			+ "under grant number MA23-Projekt 17-08.\n\n"  
			+ "The project OpenLab is located at the Department of\nEmbedded Systems, "
			+ "University of Applied Sciences Technikum\nWien.");


	private Label selectedLbl = new Label();
	private Map <String, String> systemSetting = new HashMap<String, String>();

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		/*** Snapshot Logo ***/		
		InputStream isImage = AboutController.class.getResourceAsStream("/images/OpenLab.png");			
		final Image imgExportCSVIcon = new Image(isImage);			

		ScopeConfig scopeConfiguration = ScopeConfig.getInstance();

		ivLogo.setImage(imgExportCSVIcon);
		setNodeListener(ivLogo, scopeConfiguration.getScopeProperties().get(PropIndex.WEBSITE_OPENLAB).toString());
		closeIamgeStream(isImage);	

		//Highlight (Bold) the first selected parameter
		lblVersion.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		selectedLbl = lblVersion;

		/*** Version Section ***/	
		VBox vbVersionParam = new VBox();
		createVersionSection(vbVersionParam);

		//Per default the Version is selected first and shown in the split pane
		spSettings.setContent(vbVersionParam);

		/*** Project Team Section ***/	
		VBox vbProjectTeam = new VBox();
		createProjectTeamSection(vbProjectTeam);

		/*** Sponsored By Section ***/		
		GridPane gridSponsor = new GridPane();
		createSponsoredBySection(gridSponsor, scopeConfiguration);

		/*** Disclaimer Section ***/	
		VBox vBoxDisclaimer = new VBox();
		createDisclaimerSection(vBoxDisclaimer);

		/*** Specification Section ***/	
		VBox vBoxSpec = new VBox();
		createSpecificationSection(vBoxSpec);
		
		/*** Project Specification Section ***/	
		VBox vBoxProjDesc = new VBox();
		createProjectDescSection(vBoxProjDesc);


		/*** Action Listener for the corresponding Settings***/
		lblVersion.setOnMouseClicked((ActionEvent)-> {
			spSettings.setContent(vbVersionParam);

			selectedLbl.setFont(Font.getDefault());
			lblVersion.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
			selectedLbl = lblVersion;
		});

		lblProjectTeam.setOnMouseClicked((ActionEvent)-> {
			spSettings.setContent(vbProjectTeam);

			selectedLbl.setFont(Font.getDefault());
			lblProjectTeam.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
			selectedLbl = lblProjectTeam;
		});

		lblSponsor.setOnMouseClicked((ActionEvent)-> {
			spSettings.setContent(gridSponsor);

			selectedLbl.setFont(Font.getDefault());
			lblSponsor.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
			selectedLbl = lblSponsor;
		});

		lblProjectDesc.setOnMouseClicked((ActionEvent)-> {
			spSettings.setContent(vBoxProjDesc);

			selectedLbl.setFont(Font.getDefault());
			lblProjectDesc.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
			selectedLbl = lblProjectDesc;
		});

		lblDisclaimer.setOnMouseClicked((ActionEvent)-> {
			spSettings.setContent(vBoxDisclaimer);

			selectedLbl.setFont(Font.getDefault());
			lblDisclaimer.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
			selectedLbl = lblDisclaimer;
		});

		lblSpecification.setOnMouseClicked((ActionEvent)-> {
			spSettings.setContent(vBoxSpec);

			selectedLbl.setFont(Font.getDefault());
			lblSpecification.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
			selectedLbl = lblSpecification;
		});
	}

	private void setNodeListener(Node node, String WebSite) {
		node.setOnMouseEntered((value) -> {
			anchorPane.getScene().getWindow().getScene().setCursor(Cursor.HAND);
		});

		node.setOnMouseExited((value) -> {
			anchorPane.getScene().getWindow().getScene().setCursor(Cursor.DEFAULT);
		});

		node.setOnMouseClicked((value) -> {
			WebBrowser webBrowser = new WebBrowser(WebSite);
			webBrowser.executeWebBrowser();
		});

		return;
	}


	private void createVersionSection(VBox vbVersionParam) {
		//Read the properties of the device
		OpenLabDevice openLabDevice = OpenLabDevice.getInstance();

		/*** GUI Version Section ***/
		GridPane gridVersionGUI = new GridPane();
		gridVersionGUI.setHgap(35.0);
		gridVersionGUI.setVgap(0.0);
		gridVersionGUI.setPadding(new Insets(10.0, 10.0, 10.0, 15.0));

		HBox hbGUIVersion = new HBox();

		final Label lblGUIVer = new Label("GUI Version: ");
		hbGUIVersion.getChildren().add(lblGUIVer);

		final Label lblGUIVerValue = new Label(systemSetting.get(ScopeSettings.GUI_VERSION));
		lblGUIVerValue.setContentDisplay(ContentDisplay.BOTTOM);
		lblGUIVerValue.setMaxWidth(Double.POSITIVE_INFINITY);
		lblGUIVerValue.setMaxHeight(Double.POSITIVE_INFINITY);
		lblGUIVerValue.setFont(Font.font("Verdana", FontWeight.BOLD, 11));

		hbGUIVersion.getChildren().add(lblGUIVerValue);

		HBox hbGUIBuildID = new HBox();

		final Label lblGUIBuildID = new Label("Build ID: ");
		hbGUIBuildID.getChildren().add(lblGUIBuildID);

		final Label lblGUIBuildIDValue = new Label(systemSetting.get(ScopeSettings.GUI_BUILD_ID));
		lblGUIBuildIDValue.setContentDisplay(ContentDisplay.BOTTOM);
		lblGUIBuildIDValue.setMaxWidth(Double.POSITIVE_INFINITY);
		lblGUIBuildIDValue.setMaxHeight(Double.POSITIVE_INFINITY);
		lblGUIBuildIDValue.setFont(Font.font("Verdana", FontWeight.BOLD, 11));

		hbGUIBuildID.getChildren().add(lblGUIBuildIDValue);

		HBox hbGUICodeName = new HBox();
		hbGUICodeName.setPadding(new Insets(10.0, 0.0, 0.0, 0.0));

		final Label lblCodeName = new Label("Code Name: ");
		final Label lblCodeCont = new Label("Goofy-Alpha");
		lblCodeCont.setMaxWidth(Double.POSITIVE_INFINITY);
		lblCodeCont.setMaxHeight(Double.POSITIVE_INFINITY);
		lblCodeCont.setFont(Font.font("Verdana", FontWeight.BOLD, 11));

		lblCodeCont.setOnMouseClicked((value) -> {
			Stage stage = new Stage();			
			StackPane root = new StackPane();

			InputStream isImageX = AboutController.class.getResourceAsStream("/images/OpenLabMaster.png");	
			root.getChildren().add(new ImageView(new Image(isImageX)));
			closeIamgeStream(isImageX);

			Scene scene = new Scene(root, 500, 300);
			stage.setScene(scene);

			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {					
					InputStream isImageX = AboutController.class.getResourceAsStream("/images/OpenLabIntro.png");	
					root.getChildren().add(new ImageView(new Image(isImageX, 500, 300, true, true)));
					closeIamgeStream(isImageX);
					event.consume();
					return;
				}
			});	        
			stage.show();
		});

		hbGUICodeName.getChildren().addAll(lblCodeName, lblCodeCont);

		gridVersionGUI.add(hbGUIVersion, 0, 0);
		gridVersionGUI.add(hbGUICodeName, 0, 1);
		gridVersionGUI.add(hbGUIBuildID, 1, 0);

		/*** Device Version Section ***/
		GridPane gridVersionDev = new GridPane();
		gridVersionDev.setHgap(35.0);
		gridVersionDev.setVgap(5.0);
		gridVersionDev.setPadding(new Insets(25.0, 10.0, 10.0, 15.0));

		HBox hbDetDev = new HBox();		

		final Label lblDetDev = new Label("Detected Device: ");
		hbDetDev.getChildren().add(lblDetDev);

		final Label lblDetDevVal = new Label(openLabDevice.getDevice());
		lblDetDevVal.setContentDisplay(ContentDisplay.BOTTOM);
		lblDetDevVal.setMaxWidth(Double.POSITIVE_INFINITY);
		lblDetDevVal.setMaxHeight(Double.POSITIVE_INFINITY);
		lblDetDevVal.setFont(Font.font("Verdana", FontWeight.BOLD, 11));

		hbDetDev.getChildren().add(lblDetDevVal);

		HBox hbHWVersionDev = new HBox();		

		final Label lblHWVer = new Label("Hardware Version: ");
		hbHWVersionDev.getChildren().add(lblHWVer);

		final Label lblHWDev = new Label(openLabDevice.getHwVersion());
		lblHWDev.setContentDisplay(ContentDisplay.BOTTOM);
		lblHWDev.setMaxWidth(Double.POSITIVE_INFINITY);
		lblHWDev.setMaxHeight(Double.POSITIVE_INFINITY);
		lblHWDev.setFont(Font.font("Verdana", FontWeight.BOLD, 11));

		hbHWVersionDev.getChildren().add(lblHWDev);

		HBox hbSWVersionDev = new HBox();

		final Label lblSWVer = new Label("Software Version: ");
		hbSWVersionDev.getChildren().add(lblSWVer);

		final Label lblSWDev = new Label(openLabDevice.getSwVersion());
		lblSWDev.setContentDisplay(ContentDisplay.BOTTOM);
		lblSWDev.setMaxWidth(Double.POSITIVE_INFINITY);
		lblSWDev.setMaxHeight(Double.POSITIVE_INFINITY);
		lblSWDev.setFont(Font.font("Verdana", FontWeight.BOLD, 11));

		hbSWVersionDev.getChildren().add(lblSWDev);

		gridVersionDev.add(hbDetDev, 0, 1);	
		gridVersionDev.add(hbHWVersionDev, 0, 2);		
		gridVersionDev.add(hbSWVersionDev, 1, 2);

		vbVersionParam.getChildren().addAll(gridVersionGUI, gridVersionDev);		
		return;
	}


	private void createProjectTeamSection(VBox vBProjectTeam) {
		/*** Project Member Roman Beneder ***/
		GridPane gridTeamMember1 = new GridPane();
		gridTeamMember1.setHgap(10.0);
		gridTeamMember1.setVgap(0.0);
		gridTeamMember1.setPadding(new Insets(10.0, 0.0, 10.0, 15.0));

		InputStream isImage = AboutController.class.getResourceAsStream("/images/ProjectTeamBeneder.png");		
		gridTeamMember1.add(new ImageView(new Image(isImage, 100, 150, true, true)),0,0);		
		closeIamgeStream(isImage);	

		GridPane gridTeamMember1Pos = new GridPane();
		gridTeamMember1Pos.setHgap(10.0);
		gridTeamMember1Pos.setVgap(5.0);
		gridTeamMember1Pos.setPadding(new Insets(10.0, 0.0, 0.0, 0.0));

		Label lblTeamMember1Name = new Label("Roman Beneder");
		lblTeamMember1Name.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		setNodeListener(lblTeamMember1Name, "https://embsys.technikum-wien.at/staff/beneder/index.php");

		Label lblTeamMember1Mail = new Label("roman.beneder@technikum-wien.at");
		Label lblTeamMember1PPos = new Label("Projectmanager, R&D, Lecturer");
		Label lblTeamMember1PWP = new Label("Embedded Systems");
		Label lblTeamMember1PWPA = new Label("Electronic Design");

		gridTeamMember1Pos.add(lblTeamMember1Name, 0, 0);
		gridTeamMember1Pos.add(lblTeamMember1Mail, 0, 1);
		gridTeamMember1Pos.add(lblTeamMember1PPos, 0, 2);
		gridTeamMember1Pos.add(lblTeamMember1PWP, 0, 3);
		gridTeamMember1Pos.add(lblTeamMember1PWPA, 0, 4);

		gridTeamMember1.add(gridTeamMember1Pos, 1, 0);

		/*** Project Member Markus Lechner ***/
		GridPane gridTeamMember2 = new GridPane();
		gridTeamMember2.setHgap(10.0);
		gridTeamMember2.setVgap(0.0);
		gridTeamMember2.setPadding(new Insets(10.0, 0.0, 10.0, 15.0));

		isImage = AboutController.class.getResourceAsStream("/images/ProjectTeamLechner.png");		
		gridTeamMember2.add(new ImageView(new Image(isImage, 100, 150, true, true)),0,0);		
		closeIamgeStream(isImage);	

		GridPane gridTeamMember2Pos = new GridPane();
		gridTeamMember2Pos.setHgap(10.0);
		gridTeamMember2Pos.setVgap(5.0);
		gridTeamMember2Pos.setPadding(new Insets(10.0, 0.0, 0.0, 0.0));

		Label lblTeamMember2Name = new Label("Markus Lechner");
		lblTeamMember2Name.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		setNodeListener(lblTeamMember2Name, "https://embsys.technikum-wien.at/staff/lechner/index.php");

		Label lblTeamMember2Mail = new Label("markus.lechner@technikum-wien.at");
		Label lblTeamMember2PPos = new Label("Junior Researcher, R&D");
		Label lblTeamMember2PWP = new Label("Embedded Systems & Measurement Equipment");
		Label lblTeamMember2PWPA = new Label("UI Back-End and Front-End Development");

		gridTeamMember2Pos.add(lblTeamMember2Name, 0, 0);
		gridTeamMember2Pos.add(lblTeamMember2Mail, 0, 1);
		gridTeamMember2Pos.add(lblTeamMember2PPos, 0, 2);
		gridTeamMember2Pos.add(lblTeamMember2PWP, 0, 3);
		gridTeamMember2Pos.add(lblTeamMember2PWPA, 0, 4);

		gridTeamMember2.add(gridTeamMember2Pos, 1, 0);

		/*** Project Member Patrick Schmitt ***/
		GridPane gridTeamMember3 = new GridPane();
		gridTeamMember3.setHgap(10.0);
		gridTeamMember3.setVgap(0.0);
		gridTeamMember3.setPadding(new Insets(10.0, 0.0, 10.0, 15.0));

		isImage = AboutController.class.getResourceAsStream("/images/ProjectTeamSchmitt.png");		
		gridTeamMember3.add(new ImageView(new Image(isImage, 100, 150, true, true)),0,0);		
		closeIamgeStream(isImage);	

		GridPane gridTeamMember3Pos = new GridPane();
		gridTeamMember3Pos.setHgap(10.0);
		gridTeamMember3Pos.setVgap(5.0);
		gridTeamMember3Pos.setPadding(new Insets(10.0, 0.0, 0.0, 0.0));

		Label lblTeamMember3Name = new Label("Patrick Schmitt");
		lblTeamMember3Name.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		setNodeListener(lblTeamMember3Name, "https://embsys.technikum-wien.at/staff/schmitt/index.php");

		Label lblTeamMember3Mail = new Label("patrick.schmitt@technikum-wien.at");
		Label lblTeamMember3PPos = new Label("Junior Researcher, R&D");
		Label lblTeamMember3PWP = new Label("Embedded Systems & Measurement Equipment");
		Label lblTeamMember3PWPA = new Label("FPGA and Hardware Design");

		gridTeamMember3Pos.add(lblTeamMember3Name, 0, 0);
		gridTeamMember3Pos.add(lblTeamMember3Mail, 0, 1);
		gridTeamMember3Pos.add(lblTeamMember3PPos, 0, 2);
		gridTeamMember3Pos.add(lblTeamMember3PWP, 0, 3);
		gridTeamMember3Pos.add(lblTeamMember3PWPA, 0, 4);

		gridTeamMember3.add(gridTeamMember3Pos, 1, 0);

		vBProjectTeam.getChildren().addAll(gridTeamMember1, gridTeamMember2, gridTeamMember3);

		return;
	}


	private void createSponsoredBySection(GridPane gridSponsor, ScopeConfig scopeConfiguration) {
		gridSponsor.setVgap(10.0);
		gridSponsor.setPadding(new Insets(15.0, 10.0, 15.0, 10.0));

		HBox hbSponsorImg = new HBox();
		hbSponsorImg.setSpacing(35.0);

		InputStream isImage = AboutController.class.getResourceAsStream("/images/MA23.png");			
		final Image imgMA23 = new Image(isImage, 72, 70, true, true);			

		ImageView ivMA23 = new ImageView(imgMA23);
		setNodeListener(ivMA23, scopeConfiguration.getScopeProperties().get(PropIndex.WEBSITE_MA23).toString());
		closeIamgeStream(isImage);		

		hbSponsorImg.getChildren().add(ivMA23);

		isImage = AboutController.class.getResourceAsStream("/images/FHTW.png");			
		final Image imgFHTW = new Image(isImage, 160, 70, true, true);			

		ImageView ivimgFHTW = new ImageView(imgFHTW);
		setNodeListener(ivimgFHTW, scopeConfiguration.getScopeProperties().get(PropIndex.WEBSITE_FH_TECHNIKUM).toString());
		closeIamgeStream(isImage);

		hbSponsorImg.getChildren().add(ivimgFHTW);		
		gridSponsor.add(hbSponsorImg, 0, 0);

		Label lblSponsorCont = new Label(SPONSOR_CONTENT);

		gridSponsor.add(lblSponsorCont, 0, 1);		
		return;
	}


	private void createDisclaimerSection(VBox vBoxDisclaimer) {
		vBoxDisclaimer.setPadding(new Insets(15.0, 10.0, 15.0, 10.0));

		Text txtDisclaimer = new Text();
		txtDisclaimer.setText(readFileContent("/message/Disclaimer.txt").toString());
		txtDisclaimer.wrappingWidthProperty().bind(spSettings.widthProperty());

		vBoxDisclaimer.getChildren().add(txtDisclaimer);
		return;
	}


	private void createSpecificationSection(VBox vBoxSpec) {
		vBoxSpec.setPadding(new Insets(15.0, 10.0, 15.0, 10.0));

		Text txtSpec = new Text();
		txtSpec.setText(readFileContent("/message/Specification.txt").toString());
		txtSpec.wrappingWidthProperty().bind(spSettings.widthProperty());

		vBoxSpec.getChildren().add(txtSpec);
		return;
	}
	
	
	private void createProjectDescSection(VBox vBoxProjDesc) {
		vBoxProjDesc.setPadding(new Insets(15.0, 10.0, 15.0, 10.0));

		Text txtProjDesc = new Text();
		txtProjDesc.setText(readFileContent("/message/ProjectDescription.txt").toString());
		txtProjDesc.wrappingWidthProperty().bind(spSettings.widthProperty());

		vBoxProjDesc.getChildren().add(txtProjDesc);
		return;
	}


	/**
	 * @param filePath
	 * @return
	 */
	private StringBuilder readFileContent(String filePath) {		
		String fileLine = new String();	
		StringBuilder fileCont = new StringBuilder();

		//Setup buffered file reader for performant file reading
		InputStream is = AboutController.class.getResourceAsStream(filePath);
		BufferedReader bufReader = new BufferedReader(new InputStreamReader(is));

		try {
			while ((fileLine = bufReader.readLine()) != null) {				
				if(fileLine.contains("#")) {
					fileLine = fileLine.replace('#', '\n');
				}				
				fileCont.append(fileLine);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return fileCont;
	}




	private void closeIamgeStream(InputStream is) {
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}

	/**
	 * @param systemSetting the systemSetting to set
	 */
	public void setSystemSetting(Map<String, String> systemSetting) {
		this.systemSetting = systemSetting;
	}
}

/* EOF */