/**
 *
 */
package desktop_support;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import dialog.StandardDialog;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert.AlertType;

/**
 * @author Markus Lechner
 *
 */
public class WebBrowser {
	private String webSite;


	/**
	 * @param website
	 */
	public WebBrowser(String webSite) {
		this.webSite = webSite;
	}


	/**
	 * 
	 */
	public void executeWebBrowser() {
		Thread t = new Thread(taskWebBrowser);
		t.setName("Web.Browser");
		t.setDaemon(true);
		t.start();

		return;
	}

	
	/**
	 * 
	 */
	private final Task<Void> taskWebBrowser = new Task<Void>()	{
		@Override
		protected Void call() throws Exception {
			if(webSite == null) {
				System.out.println("Web.Browser: Invalid File - Operation aborted!");
				return null;	
			}			
			
			if (Desktop.isDesktopSupported() && (Desktop.getDesktop()).isSupported(Desktop.Action.BROWSE)) {
				URI websiteURI = null;

				try {
					websiteURI = new URI(webSite);
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					Desktop.getDesktop().browse(websiteURI);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch(Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null; 
		}

		@Override
		protected void setException(Throwable t) {
			// TODO Auto-generated method stub
			super.setException(t);
			System.out.println("Web.Browser:" + t);
		}

		@Override
		protected void done() {
			// TODO Auto-generated method stub
			super.done();			
			System.out.println("Web.Browser: Operation was exectued successfully!");
		}

		@Override
		protected void failed() {
			// TODO Auto-generated method stub
			super.failed();
			showErrorDialog();
			System.out.println("Web.Browser: Operation failed!");
		}
		
		
		/**
		 * 
		 */
		private void showErrorDialog() {
			Platform.runLater(() -> {
				StandardDialog standardDialog = new StandardDialog(null);					
				
				standardDialog.setTitle("Error: Web Browser");
				standardDialog.setContentText("The Operation failed!\nWeb site:" + webSite);
				standardDialog.createDialog(AlertType.ERROR);

				throw new RuntimeException("The operation can not be executed!");
			});	
			return;
		}
	};	
}


/* EOF */