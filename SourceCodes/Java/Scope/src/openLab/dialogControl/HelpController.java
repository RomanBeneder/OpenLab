/**
 * 
 */
package openLab.dialogControl;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * @author Markus Lechner
 *
 */
public class HelpController implements Initializable {
	@FXML
	private WebView webView;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		WebEngine webEngine = webView.getEngine();
		webEngine.load(getClass().getResource("/help/HelpContent.html").toExternalForm());
		System.out.println("Help.Controller: WebEngine started help menu successfully!");
	}
}

/* EOF */
