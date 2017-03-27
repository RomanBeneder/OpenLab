package openLab.launcher.gui;

import java.io.InputStream;
import java.util.Properties;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import openLab.launcher.controller.Controller;
import openLab.launcher.dialog.StandardDialog;
import openLab.launcher.process.ProcessSupervisor;
import openLab.launcher.property.LauncherConfig;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//Load system properties
			Properties launcherProperties = new Properties();
			
			LauncherConfig launcherConfig = new LauncherConfig();
			launcherConfig.loadConfiguration(launcherProperties);
			
			//Initialize Process Supervisor
			ProcessSupervisor processSupervisor = new ProcessSupervisor(launcherProperties);
			processSupervisor.executeProcessSupervisor();
			
			Controller controller = new Controller(primaryStage, launcherProperties);
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LauncherGUI.fxml"));
			fxmlLoader.setController(controller);

			AnchorPane root = fxmlLoader.load();

			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("OpenLab Signal-Toolkit Launcher");
			
			InputStream isIcon = Main.class.getResourceAsStream("OpenLab.png");			
			primaryStage.getIcons().add(new Image(isIcon));			
			isIcon.close();
			
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);

			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
				@Override
				public void handle(WindowEvent event) {
					if(processSupervisor.getProcessMonitorMap().isEmpty()) {
						System.exit(0);
					} else {
						StandardDialog standardDialog = new StandardDialog(primaryStage);
						standardDialog.setTitle("Confirm Exit");
						standardDialog.setContentText("Attention: All started Signal-Tools will be terminated!");
						StandardDialog.DIALOG_STATE dialogState = standardDialog.createConfirmDialog();

						if(dialogState.equals(StandardDialog.DIALOG_STATE.CONFIRMED)) {
							processSupervisor.terminateSubProcess(null, true);
							System.exit(0);
						} else if(dialogState.equals(StandardDialog.DIALOG_STATE.NOT_CONFIRMED)) {
							event.consume();
							return;
						} else {
							event.consume();
							return;
						}	
					}
				}
			});
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}

/* EOF */