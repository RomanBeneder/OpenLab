/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.schroen.embsysscope.gui;

import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;

/**
 *
 * @author embsys
 */
public class EmbSysScope extends Application {
	
	@Override
	public void start(Stage primaryStage) {

		try {
			BorderPane root =(BorderPane) FXMLLoader.load(EmbSysScope.class.getResource("EmbSysScope.fxml"));
			javafx.geometry.Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
			Scene scene = new Scene(root,primScreenBounds.getMaxX()/2,primScreenBounds.getMaxY()/2);
			scene.getStylesheets().addAll(this.getClass().getResource("EmbSysScope.css").toExternalForm());
			primaryStage.setTitle("EmbSysScope-1.0");
			primaryStage.setScene(scene);
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
			
				@Override
				public void handle(WindowEvent event) {
					System.exit(0);
				}	
			});
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		Application.launch(EmbSysScope.class, new String[0]);
	}

}