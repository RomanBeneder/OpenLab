/**
 * 
 */
package openLab.dialogControl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author Markus Lechner
 *
 */
public class FeedbackFileParser {
	public final static String FEEDBACK_INDIVIDUAL = new String("FeedbackInd");
	public final static String FEEDBACK_QUESTIONNAIRE = new String("FeedbackQuest");
	public final int MAXIMUM_LETTERS_PER_LINE = 55;

	private String filePath;

	private BufferedReader fileReaderFeedback = null;
	private InputStream inputStream = null;


	/**
	 * @param filePath
	 */
	public FeedbackFileParser(String filePath) {
		this.filePath = filePath;
	}


	/**
	 * @param feedbackContent
	 * @return
	 */
	public ArrayList<String> readFeedbackFile(Map<String, ArrayList<String>> feedbackContent) {
		int posEnd = 0;
		int readLine = 0;
		int posPoint = 0;		
		int readQuestions = 0;
		String question;
		StringBuilder sbTempQuest = new StringBuilder();
		ArrayList<String> formatedQuest = new ArrayList<String>(30);		
		
		if(filePath == null) {
			return null;
		}
		
		//Setup buffered file reader for performant file reading
		inputStream = FeedbackFileParser.class.getResourceAsStream(filePath);
		fileReaderFeedback = new BufferedReader(new InputStreamReader(inputStream));

		try {
			while((question = fileReaderFeedback.readLine()) != null) {
				
				readQuestions++;
				feedbackContent.get(FEEDBACK_QUESTIONNAIRE).add(readLine, question);
				
				posPoint = 0;
				posEnd = 0;
				
				while(posEnd != -1) {
					posEnd = question.indexOf(" ");

					if(posEnd == -1) {
						posPoint += question.length();
					} else {
						posPoint += posEnd+1;
					}

					if(posPoint <= MAXIMUM_LETTERS_PER_LINE) {
						if(posEnd == -1) {
							sbTempQuest.append(question.substring(0, question.length()));
						} else {
							sbTempQuest.append(question.substring(0, posEnd+1));
							question = question.substring(posEnd+1,question.length());
						}
					} else {
						if(readQuestions >= 10) {
							sbTempQuest.append("\n     ");
						} else {
							sbTempQuest.append("\n   ");
						}

						if(posEnd == -1) {
							sbTempQuest.append(question.substring(0, question.length()));
						} else {
							sbTempQuest.append(question.substring(0, posEnd+1));
							question = question.substring(posEnd+1,question.length());
							posPoint = posEnd + 1;
						}
					}
				}
				formatedQuest.add(readLine, sbTempQuest.toString());
				sbTempQuest.delete(0, sbTempQuest.length());

				readLine++;			
			}
			closeStreams();
			return formatedQuest;
					
		} catch (IOException e) {
			e.printStackTrace();
			closeStreams();			
		}
		return null;
	}


	/**
	 * 
	 */
	private void closeStreams() {
		try	{
			if(fileReaderFeedback != null) {
				fileReaderFeedback.close();
			}

			if (inputStream != null) {
				inputStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}

}

/* EOF */
