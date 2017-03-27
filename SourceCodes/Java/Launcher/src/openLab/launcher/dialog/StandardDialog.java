package openLab.launcher.dialog;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Window;

/**
 * @author mLechner
 *
 */
public class StandardDialog {	
	private Window window;
	private String title;
	private String headerText;
	private String contentText;

	public enum DIALOG_STATE {
		ERROR,
		NOT_CONFIRMED,
		CONFIRMED
	}

	/**
	 * @param window
	 */
	public StandardDialog(Window window) {
		this.window = window;
	}


	/**
	 * @param alertType
	 * @return
	 */
	public boolean createDialog(AlertType alertType) {
		Alert alert = new Alert(alertType);	

		if(this.window != null) {
			alert.initOwner(this.window);
		}
		
		alert.setResizable(true);
		alert.setTitle(this.title);
		alert.setHeaderText(this.headerText);
		alert.setContentText(this.contentText);
		alert.showAndWait();		
		return true;
	}


	/**
	 * @return
	 */
	public DIALOG_STATE createConfirmDialog() {
		Alert alert = new Alert(AlertType.CONFIRMATION);

		if(this.window != null) {
			alert.initOwner(this.window);
		}

		alert.setTitle(this.title);
		alert.setHeaderText(this.headerText);
		alert.setContentText(this.contentText);

		Optional<ButtonType> result = alert.showAndWait();

		if (result.get().equals(ButtonType.OK)){
			return DIALOG_STATE.CONFIRMED;
		} 
		return DIALOG_STATE.NOT_CONFIRMED;
	}


	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}


	/**
	 * @param headerText the headerText to set
	 */
	public void setHeaderText(String headerText) {
		this.headerText = headerText;
	}


	/**
	 * @param contentText the contentText to set
	 */
	public void setContentText(String contentText) {
		this.contentText = contentText;
	}
}

/* EOF */
