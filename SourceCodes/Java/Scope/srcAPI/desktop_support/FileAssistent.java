/**
 * 
 */
package desktop_support;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import dialog.StandardDialog;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert.AlertType;

/**
 * @author Markus Lechner
 *
 */
public class FileAssistent {
	File file = null;


	/**
	 * @param file
	 */
	public FileAssistent(File file) {
		this.file = file;		
	}


	/**
	 * 
	 */
	public void executeFileAssistent() {
		Thread t = new Thread(taskFileAssistent);
		t.setName("File.Assistent");
		t.setDaemon(true);
		t.start();
		return;
	}


	/**
	 * 
	 */
	private final Task<Void> taskFileAssistent = new Task<Void>()	{
		@Override
		protected Void call() throws Exception {
			if(file == null) {
				System.out.println("File.Assitent: Invalid File - Operation aborted!");
				return null;	
			}

			if (Desktop.isDesktopSupported() && (Desktop.getDesktop()).isSupported(Desktop.Action.OPEN)) {
				try {
					Desktop.getDesktop().open(file);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				showErrorDialog();
				System.out.println("File.Assistent: Operation is not supported on the OS!");
			}
			return null; 
		}

		@Override
		protected void setException(Throwable t) {
			// TODO Auto-generated method stub
			super.setException(t);
			System.out.println("File.Assistent:" + t);
		}

		@Override
		protected void done() {
			// TODO Auto-generated method stub
			super.done();			
			System.out.println("File.Assistent: Operation was exectued successfully!");
		}

		@Override
		protected void failed() {
			// TODO Auto-generated method stub
			super.failed();
			showErrorDialog();
			System.out.println("File.Assistent: Operation failed!");
		}
		
		
		/**
		 * 
		 */
		private void showErrorDialog() {
			Platform.runLater(() -> {
				StandardDialog standardDialog = new StandardDialog(null);					
				
				standardDialog.setTitle("Error: File Assistent");
				standardDialog.setContentText("The Operation failed!\nVideo Directory:" + file.getAbsolutePath());
				standardDialog.createDialog(AlertType.ERROR);

				throw new RuntimeException("The operation can not be executed!");
			});	
			return;
		}
	};
}

/* EOF */
