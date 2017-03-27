/*
 * Copyright (c) 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package embeddedMediaPlayer;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.media.Track;
import javafx.util.Duration;


public class EmbeddedMediaPlayerController implements Initializable {
	@FXML
	private AnchorPane anchorPane;

	@FXML
	private Pane paneMedia;

	@FXML
	private Button btnPlay;

	@FXML
	private Slider slTime;

	@FXML
	private Label lblTime;

	@FXML
	private Slider slVol;

	private final boolean repeat = false;
	private boolean atEndOfMedia = false;
	private boolean stopRequested = false;

	private String mediaFilePath;
	private Duration duration;
	private MediaPlayer mediaPlayer = null;


	public EmbeddedMediaPlayerController(String mediaFilePath) {
		this.mediaFilePath = mediaFilePath;
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		File file = new File(mediaFilePath);
		
		try {
			Desktop.getDesktop().open(file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Media media = new Media(file.toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setAutoPlay(true);

		media.getTracks().addListener(new ListChangeListener<Track>() {
			public void onChanged(Change<? extends Track> change) {
				System.out.println("Track> "+change.getList());
			}
		});

		media.getMetadata().addListener(new MapChangeListener<String,Object>() {
			public void onChanged(MapChangeListener.Change<? extends String, ? extends Object> change) {
				System.out.println("Metadata> "+change.getKey()+" -> "+change.getValueAdded());
			}
		});

		MediaView mediaView = new MediaView(mediaPlayer);
		mediaView.setFitWidth(800); mediaView.setFitHeight(560);
		mediaView.setPreserveRatio(false);

		paneMedia.getChildren().add(mediaView);

		btnPlay.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				Status status = mediaPlayer.getStatus();

				if (status == Status.UNKNOWN || status == Status.HALTED) {
					// don't do anything in these states
					return;
				}

				if (status == Status.PAUSED	|| status == Status.READY || status == Status.STOPPED) {
					// rewind the movie if we're sitting at the end
					if (atEndOfMedia) {
						mediaPlayer.seek(mediaPlayer.getStartTime());
						atEndOfMedia = false;
					}
					mediaPlayer.play();
				} else {
					mediaPlayer.pause();
				}
			}
		});

		mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
			public void invalidated(Observable ov) {
				updateValues();
			}
		});

		mediaPlayer.setOnPlaying(new Runnable() {
			public void run() {
				if (stopRequested) {
					mediaPlayer.pause();
					stopRequested = false;
				} else {
					btnPlay.setText("||");
				}
			}
		});

		mediaPlayer.setOnPaused(new Runnable() {
			public void run() {
				System.out.println("onPaused");
				btnPlay.setText(">");
			}
		});

		mediaPlayer.setOnReady(new Runnable() {
			public void run() {
				duration = mediaPlayer.getMedia().getDuration();
				updateValues();
			}
		});

		mediaPlayer.setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);
		mediaPlayer.setOnEndOfMedia(new Runnable() {
			public void run() {
				if (!repeat) {
					btnPlay.setText(">");
					stopRequested = true;
					atEndOfMedia = true;
				}
			}
		});

		slTime.valueProperty().addListener(new InvalidationListener() {
			public void invalidated(Observable ov) {
				if (slTime.isValueChanging()) {
					// multiply duration by percentage calculated by slider position
					mediaPlayer.seek(duration.multiply(slTime.getValue() / 100.0));
				}
			}
		});

		slVol.valueProperty().addListener(new InvalidationListener() {
			public void invalidated(Observable ov) {
				if (slVol.isValueChanging()) {
					mediaPlayer.setVolume(slVol.getValue() / 100.0);
				}
			}
		});
	}


	private void updateValues() {
		if ((lblTime != null) && (slTime != null) && (slVol != null)) {
			Platform.runLater(new Runnable() {
				@SuppressWarnings("deprecation")
				public void run() {
					Duration currentTime = mediaPlayer.getCurrentTime();
					lblTime.setText(formatTime(currentTime, duration));
					slTime.setDisable(duration.isUnknown());
					if (!slTime.isDisabled()
							&& duration.greaterThan(Duration.ZERO)
							&& !slTime.isValueChanging()) {
						slTime.setValue(currentTime.divide(duration).toMillis() * 100.0);
					}
					if (!slVol.isValueChanging()) {
						slVol.setValue((int) Math.round(mediaPlayer.getVolume() * 100));
					}
				}
			});
		}
	}


	private static String formatTime(Duration elapsed, Duration duration) {
		int intElapsed = (int) Math.floor(elapsed.toSeconds());
		int elapsedHours = intElapsed / (60 * 60);
		if (elapsedHours > 0) {
			intElapsed -= elapsedHours * 60 * 60;
		}
		
		int elapsedMinutes = intElapsed / 60;
		int elapsedSeconds = intElapsed - elapsedHours * 60 * 60
				- elapsedMinutes * 60;

		if (duration.greaterThan(Duration.ZERO)) {
			int intDuration = (int) Math.floor(duration.toSeconds());
			int durationHours = intDuration / (60 * 60);
			if (durationHours > 0) {
				intDuration -= durationHours * 60 * 60;
			}
			
			int durationMinutes = intDuration / 60;
			int durationSeconds = intDuration - durationHours * 60 * 60
					- durationMinutes * 60;
			if (durationHours > 0) {
				return String.format("%d:%02d:%02d/%d:%02d:%02d",
						elapsedHours, elapsedMinutes, elapsedSeconds,
						durationHours, durationMinutes, durationSeconds);
			}
			
			return String.format("%02d:%02d/%02d:%02d",
					elapsedMinutes, elapsedSeconds, durationMinutes,
					durationSeconds);
		}
		
		if (elapsedHours > 0) {
			return String.format("%d:%02d:%02d", elapsedHours,
					elapsedMinutes, elapsedSeconds);
		}
		
		return String.format("%02d:%02d", elapsedMinutes,
				elapsedSeconds);
	}
}

/* EOF */
