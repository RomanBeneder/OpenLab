/**
 * 
 */
package openLab.dialogControl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import FileUtility.FileUtil;
import dialog.StandardDialog;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import openLab.controller.InitializeOscilloscope;

/**
 * @author Markus Lechner
 *
 */
public class FeedbackController implements Initializable {
	@FXML
	private AnchorPane anchorPane;

	@FXML
	private ImageView ivFeedbackLogo;

	@FXML
	private Label lblIndividual;

	@FXML
	private Label lblQuestionaire;

	@FXML
	private StackPane spContentSection;

	@FXML
	private TextArea taIndividual;

	@FXML
	private Button btnCancel;

	@FXML
	private Button btnGenerateFile;

	private Map<String, ArrayList<String>> feedbackContent = new HashMap<String, ArrayList<String>>();
	

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//Load the feedback icon
		InputStream isImage = FeedbackController.class.getResourceAsStream("/images/FeedbackLogo.png");
		final Image imgFeedbackLogo = new Image(isImage);

		ivFeedbackLogo.setImage(imgFeedbackLogo);

		try {
			isImage.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//Highlight (Bold) the selected channel 
		lblIndividual.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

		GridPane gridQuestions = new GridPane();
		ArrayList<ToggleGroup> questionnaireAnswers = new ArrayList<ToggleGroup>();
		
		ArrayList<String> formatedQuest = new ArrayList<String>();
		
		/*** Action Listener for the corresponding Settings***/

		lblIndividual.setOnMouseClicked((ActionEvent)->	{
			spContentSection.getChildren().clear();
			spContentSection.getChildren().add(taIndividual);

			lblQuestionaire.setFont(Font.getDefault());
			lblIndividual.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		});

		lblQuestionaire.setOnMouseClicked((ActionEvent)-> {
			spContentSection.getChildren().clear();
			spContentSection.getChildren().add(gridQuestions);

			lblIndividual.setFont(Font.getDefault());
			lblQuestionaire.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		});

		/*** Action Listener for Generate File ***/

		btnGenerateFile.setOnAction((ActionEvent)-> {
			StringBuilder tempAnswere = new StringBuilder();
			RadioButton rbSelectedAns = new RadioButton();

			ArrayList<String> tempIndFeedback = new ArrayList<String>();
			tempIndFeedback.add(taIndividual.getText());

			feedbackContent.put(FeedbackFileParser.FEEDBACK_INDIVIDUAL, tempIndFeedback);

			//Parse the given answers and add them into a list
			for(int i=0; i<questionnaireAnswers.size(); i++){
				rbSelectedAns = (RadioButton) questionnaireAnswers.get(i).getSelectedToggle();
				tempAnswere.append(feedbackContent.get(FeedbackFileParser.FEEDBACK_QUESTIONNAIRE).get(i));

				if(rbSelectedAns == null || rbSelectedAns.getText().equals("n.A")) {
					tempAnswere.append("||").append("6");
				} else {
					tempAnswere.append("||").append(rbSelectedAns.getText());
				}
				feedbackContent.get(FeedbackFileParser.FEEDBACK_QUESTIONNAIRE).set(i, tempAnswere.toString());
				tempAnswere.delete(0, tempAnswere.length());
			}

			executeFileGeneration();
		});

		/*** Action Listener for AnchorPane Window ***/

		btnCancel.setOnAction((ActionEvent)-> {
			Stage stage = (Stage) anchorPane.getScene().getWindow();
			stage.close();
		});

		/*** Feedback file reading and text formating***/		

		FeedbackFileParser feedbackFileParser = new FeedbackFileParser("/FeedbackQuestions/Questions.txt");
		initFeedbackContent(feedbackFileParser);
		formatedQuest.addAll(feedbackFileParser.readFeedbackFile(feedbackContent));

		//Create the necessary quesitonnaire elements 
		createQuestionaireField(gridQuestions, questionnaireAnswers, formatedQuest);
	}


	/**
	 * @param gridQuestions
	 * @param questionnaireAnswers
	 * @param formatedQuest
	 */
	private void createQuestionaireField(GridPane gridQuestions, ArrayList<ToggleGroup> questionnaireAnswers, ArrayList<String> formatedQuest) {
		gridQuestions.setHgap(10);
		gridQuestions.setVgap(10);
		gridQuestions.setPadding(new Insets(10, 10, 10, 10));

		final Label QuestionsHeader = new Label("1 = fully agree / Very good\t5 = completely disagree / Bad\n\n");
		QuestionsHeader.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		gridQuestions.add(QuestionsHeader, 0, 0);

		for(int i=1,j=0,x=1,y=2; j < formatedQuest.size(); i++,j++,x+=2,y+=2)	{
			GridPane gridAnswers = new GridPane();
			gridAnswers.setHgap(20);
			gridAnswers.setVgap(10);
			gridAnswers.setPadding(new Insets(10, 10, 10, 10));

			final Label question = new Label();
			question.setText(i + "." + formatedQuest.get(j));
			gridQuestions.add(question, 0, x);

			final ToggleGroup tgAnswere = new ToggleGroup();

			RadioButton rb1 = new RadioButton("1");
			rb1.setToggleGroup(tgAnswere);
			gridAnswers.add(rb1, 0,0);

			RadioButton rb2 = new RadioButton("2");
			rb2.setToggleGroup(tgAnswere);
			gridAnswers.add(rb2, 1,0);

			RadioButton rb3 = new RadioButton("3");
			rb3.setToggleGroup(tgAnswere);
			gridAnswers.add(rb3, 2,0);

			RadioButton rb4 = new RadioButton("4");
			rb4.setToggleGroup(tgAnswere);
			gridAnswers.add(rb4, 3,0);

			RadioButton rb5 = new RadioButton("5");
			rb5.setToggleGroup(tgAnswere);
			gridAnswers.add(rb5, 4,0);

			RadioButton rbnA = new RadioButton("n.A");
			rbnA.setToggleGroup(tgAnswere);
			gridAnswers.add(rbnA, 5,0);

			gridQuestions.add(gridAnswers, 0, y);
			questionnaireAnswers.add(tgAnswere);
		}

		return;
	}


	/**
	 * @param feedbackFileParser
	 */
	private void initFeedbackContent(FeedbackFileParser feedbackFileParser) {
		if(feedbackContent.get(FeedbackFileParser.FEEDBACK_INDIVIDUAL) == null) {
			feedbackContent.put(FeedbackFileParser.FEEDBACK_INDIVIDUAL, new ArrayList<String>(1));
		}

		if(feedbackContent.get(FeedbackFileParser.FEEDBACK_QUESTIONNAIRE) == null){
			feedbackContent.put(FeedbackFileParser.FEEDBACK_QUESTIONNAIRE, new ArrayList<String>(30));
		}
		return;
	}


	/**
	 * 
	 */
	private void executeFileGeneration() {
		Thread t = new Thread(taskGenerateFeedbackFile);
		t.setName("Thread:FeebackFileGen");
		t.setDaemon(true);
		t.start();

		return;
	}


	/**
	 * 
	 */
	final Task<Void> taskGenerateFeedbackFile = new Task<Void>() {
		File fileFeedback = null;

		@Override
		protected Void call() throws Exception {
			StringBuilder sbFeedbackContent = new StringBuilder("--- INDIVIDUAL FEEDBACK ---\n");

			if (feedbackContent == null) {
				return null;
			}

			sbFeedbackContent.append(feedbackContent.get(FeedbackFileParser.FEEDBACK_INDIVIDUAL).get(0).toString());				
			sbFeedbackContent.append("\n\n--- QUESTIONNAIRE ---\n");				

			for(int i=0; i<feedbackContent.get(FeedbackFileParser.FEEDBACK_QUESTIONNAIRE).size(); i++) {					
				sbFeedbackContent.append(feedbackContent.get(FeedbackFileParser.FEEDBACK_QUESTIONNAIRE).get(i)).append("\n");
			}

			FileUtil fileUtil = new FileUtil("Feedback", ".txt");

			fileFeedback = new File(fileUtil.getFileName(InitializeOscilloscope.DIR_FEEDBACK));
			FileWriter feedbackFileWriter = new FileWriter(fileFeedback);

			feedbackFileWriter.write(sbFeedbackContent.toString());
			feedbackFileWriter.flush();
			feedbackFileWriter.close();
			return null;
		}

		@Override
		protected void setException(Throwable t) {
			// TODO Auto-generated method stub
			super.setException(t);
			System.err.println("Feedback File: " + t);
		}

		@Override
		protected void done() {
			// TODO Auto-generated method stub
			super.done();

			StandardDialog standardDialog = new StandardDialog(null);
			Platform.runLater(() -> {
				standardDialog.setTitle("Feedback File");
				standardDialog.setContentText("The Feedback file was exported successfully!\nDirectory: "
						+ fileFeedback.getAbsolutePath());

				standardDialog.createDialog(AlertType.INFORMATION);
				btnCancel.fire();
			});			

			System.out.println("Feedback File: Exported Feedback data to: " + fileFeedback.getAbsolutePath());
		}

		@Override
		protected void failed() {
			// TODO Auto-generated method stub
			super.failed();
			System.err.println("Feedback File: Generation of Feedback file failed!");
		}
	};
}

/* EOF */