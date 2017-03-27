/**
 * 
 */
package openLab.oscilloscope;

import java.io.InputStream;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import openLab.controller.Controller;
import openLab.controller.InitializeOscilloscope;

/**
 * @author Markus Lechner
 *
 */
public class Main extends Application {
	private InitializeOscilloscope initScope = null;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {			
			initScope = new InitializeOscilloscope();
			initScope.createDirectory();
			
			Controller controller = new Controller(primaryStage);
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OscilloscopeGUI.fxml"));
			fxmlLoader.setController(controller);
			
			AnchorPane root = fxmlLoader.load();
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("Oscilloscope.css").toExternalForm());
			primaryStage.setTitle("OpenLab Oscilloscope");				
			
			InputStream isIcon = Main.class.getResourceAsStream("/images/OpenLab.png");			
			primaryStage.getIcons().add(new Image(isIcon));			
			isIcon.close();
			
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);		
			
			controller.installKeyListener(primaryStage);

			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					initScope.disconnectSerialDevice();
					
					if(initScope.introduceFeedback()) {
						event.consume();
						return;
					}
					
					initScope.saveScopeConfiguration();					
					System.exit(0);
				}
			});
			primaryStage.show();			

			initScope.startRequestProcessing();
			
		} catch(Exception e) {
			e.printStackTrace();
		}		
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
//AnchorPane.b

//Scale scale = new Scale(1.6,1.6);
//scale.setPivotX(0.0);
//scale.setPivotY(0.0);
//
//root.setPrefWidth(870.0 * 0.8);//);
//root.setPrefHeight(630.0 * 0.8);
//
//root.getTransforms().setAll(scale);
//root.getRoot().getTransforms().setAll(scale);
//root.getRoot().getTransforms().setAll(scale);

/* EOF */