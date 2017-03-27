/**
 *
 */
package openLab.controller;


import java.io.IOException;
import java.io.InputStream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Markus Lechner
 *
 */
public class ImageComboBox {
	public final static int IMAGE_RISING_EDGE = 0;
	public final static int IMAGE_FALLING_EDGE = 1;
	public final static int IMAGE_BOTH_EDGES = 2;

	private ComboBox<Image> comboBox = null;
	private ObservableList<Image> slopeImage = null;


	/**
	 * @param comboBox
	 */
	public ImageComboBox(ComboBox<Image> comboBox) {
		this.comboBox = comboBox;
		slopeImage = loadImages();
		return;
	}

	/**
	 * @return
	 */
	public void createImageComboBox() {
		if(comboBox != null) {
			comboBox.getItems().addAll(slopeImage);
			comboBox.setButtonCell(new ImageListCell());
			comboBox.setCellFactory(listView -> new ImageListCell());
			comboBox.getSelectionModel().select(0);
		}
		return;
	}


	/**
	 * @return
	 */
	public ObservableList<Image> loadImages() {
		final ObservableList<Image> slopeImages = FXCollections.observableArrayList();
		
		InputStream isIcon = null;		
		
		isIcon = ImageComboBox.class.getResourceAsStream("/images/RisingEdge.png");
		Image risingEdge = new Image(isIcon,20,20,true,true);		
		closeImageStream(isIcon);
		
		isIcon = ImageComboBox.class.getResourceAsStream("/images/FallingEdge.png");		
		Image fallingEdge = new Image(isIcon,20,20,true,true);
		closeImageStream(isIcon);

		isIcon = ImageComboBox.class.getResourceAsStream("/images/BothEdges.png");
		Image bothEdges = new Image(isIcon,20,20,true,true);
		closeImageStream(isIcon);
		
		slopeImages.addAll(risingEdge, fallingEdge, bothEdges);

		return slopeImages;
	}


	/**
	 * @param is
	 */
	private void closeImageStream(InputStream is) {
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
	
	
	/**
	 * @author Markus Lechner
	 *
	 */
	private class ImageListCell extends ListCell<Image> {
		private final ImageView view;

		ImageListCell() {
			setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			view = new ImageView();
		}

		@Override
		protected void updateItem(Image item, boolean isEmpty) {
			super.updateItem(item, isEmpty);
			if (item == null || isEmpty) {
				setGraphic(null);
			} else {
				view.setImage(item);
				setGraphic(view);
			}
		}
	}


	/**
	 * @return the slopeImage
	 */
	public ObservableList<Image> getSlopeImage() {
		return slopeImage;
	}
}

/* EOF */
