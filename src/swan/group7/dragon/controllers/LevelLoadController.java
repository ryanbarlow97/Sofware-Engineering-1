package swan.group7.dragon.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.CubicCurve;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * LevelLoadController.java This class controls the Level options screen.
 * 
 * @author Ryan Barlow, Corben Thompson, Mollie Stamp (JavaDoc Mollie Stamp)
 * @version 1.19
 *
 */
public class LevelLoadController {

	@FXML
	private Button btnLevelFive;
	@FXML
	private Button btnLevelFour;
	@FXML
	private Button btnLevelOne;
	@FXML
	private Button btnLevelThree;
	@FXML
	private Button btnLevelTwo;
	@FXML
	private Button btnMainMenu;
	@FXML
	private Button btnToggleMute;
	@FXML
	private Button btnLogOut;
	@FXML
	private ImageView muteBtnImage;
	@FXML
	private Slider sliderVolumeSlider;
	@FXML
	private CubicCurve curve1;
	@FXML
	private CubicCurve curve2;
	@FXML
	private CubicCurve curve3;
	@FXML
	private CubicCurve curve4;
	@FXML
	private CubicCurve curve5;
	@FXML
	private Text txtUsername;

	private static String LEVEL_SELECTOR_MUSIC = "levelselector";
	private static AudioPlayer ap;
	private Timeline tickGame;



	/**
	 * This method changes the volume to whatever value is given.
	 * 
	 * @param roundOff Double value given to assign to volume.
	 */
	void changeVolume(double roundOff) {
		ap.setVolume(roundOff);
	}

	/**
	 * This method deals with Level One being selected by stopping the AudioPlayer
	 * and creating a new stage with the corresponding window.
	 * 
	 * @param event The event itself which contains data about the event that
	 *              occurred.
	 * @throws Exception Exception thrown for CanvasController.java
	 */
	@FXML
	void levelOne(ActionEvent event) throws Exception {
		ap.stop();
		tickGame.stop();
		Stage stage = (Stage) btnLevelOne.getScene().getWindow();
		new CanvasController(stage, "One", txtUsername.getText(), "60", "20");
	}

	/**
	 * This method deals with Level Two being selected by stopping the AudioPlayer
	 * and creating a new stage with the corresponding window.
	 * 
	 * @param event The event itself which contains data about the event that
	 *              occurred.
	 * @throws Exception Exception thrown for CanvasController.java
	 */
	@FXML
	void levelTwo(ActionEvent event) throws Exception {
		ap.stop();
		tickGame.stop();
		Stage stage = (Stage) btnLevelTwo.getScene().getWindow();
		new CanvasController(stage, "Two", txtUsername.getText(), "50", "15");
	}

	/**
	 * This method deals with Level Three being selected by stopping the AudioPlayer
	 * and creating a new stage with the corresponding window.
	 * 
	 * @param event The event itself which contains data about the event that
	 *              occurred.
	 * @throws Exception Exception thrown for CanvasController.java
	 */
	@FXML
	void levelThree(ActionEvent event) throws Exception {
		ap.stop();
		tickGame.stop();
		Stage stage = (Stage) btnLevelThree.getScene().getWindow();
		new CanvasController(stage, "Three", txtUsername.getText(), "40", "12");
	}

	/**
	 * This method deals with Level Four being selected by stopping the AudioPlayer
	 * and creating a new stage with the corresponding window.
	 * 
	 * @param event The event itself which contains data about the event that
	 *              occurred.
	 * @throws Exception Exception thrown for CanvasController.java
	 */
	@FXML
	void levelFour(ActionEvent event) throws Exception {
		ap.stop();
		tickGame.stop();
		Stage stage = (Stage) btnLevelFour.getScene().getWindow();
		new CanvasController(stage, "Four", txtUsername.getText(), "30", "10");
	}

	/**
	 * This method deals with Level Five being selected by stopping the AudioPlayer
	 * and creating a new stage with the corresponding window.
	 * 
	 * @param event The event itself which contains data about the event that
	 *              occurred.
	 * @throws Exception Exception thrown for CanvasController.java
	 */
	@FXML
	void levelFive(ActionEvent event) throws Exception {
		ap.stop();
		tickGame.stop();
		Stage stage = (Stage) btnLevelFive.getScene().getWindow();
		new CanvasController(stage, "Five", txtUsername.getText(), "20", "9");
	}

	/**
	 * This method deals with Main Menu being selected by stopping the AudioPlayer
	 * and creating a new stage with the corresponding window.
	 * 
	 * @param event The event itself which contains data about the event that
	 *              occurred.
	 * @throws Exception Exception thrown for MainMenuGUIController.java
	 */
	@FXML
	void mainmenu(ActionEvent event) throws Exception {
		ap.stop();
		Stage stage = (Stage) btnMainMenu.getScene().getWindow();
		new MainMenuGUIController(stage, txtUsername.getText());
	}

	/**
	 * This method deals with LogOut being selected by stopping the AudioPlayer and
	 * creating a new stage with the corresponding window.
	 * 
	 * @param event The event itself which contains data about the event that
	 *              occurred.
	 * @throws Exception Exception thrown for LoginGUIController.java
	 */
	@FXML
	void logOut(ActionEvent event) throws Exception {
		ap.stop();
		Stage stage = (Stage) btnLogOut.getScene().getWindow();
		new LoginGUIController(stage);
	}

	/**
	 * This method deals with the music when the mute/unmute buttons are pressed by
	 * calling the opposite method. If the user has muted the music it also disables
	 * the volume slider bar from being used.
	 * 
	 * @param event The event itself which contains data about the event that
	 *              occurred.
	 */
	@FXML
	void toggleMute(ActionEvent event) {
		if (btnToggleMute.getAccessibleText().toString().equals("unmute")) {
			unmuteMusic();
			sliderVolumeSlider.setDisable(false);
		} else if (btnToggleMute.getAccessibleText().toString().equals("mute")) {
			muteMusic();
			sliderVolumeSlider.setDisable(true);
		}
	}

	/**
	 * This method mutes the music and changes the icon to match this.
	 */
	void muteMusic() {
		ap.mute();
		String var = "unmute";
		btnToggleMute.setAccessibleText(var);
		muteBtnImage.setImage(new Image("/artwork/mute.png"));
	}

	/**
	 * This method unmutes the music and changes the icon to the unmute.
	 */
	void unmuteMusic() {
		double roundOff = (double) Math.round(sliderVolumeSlider.getValue() / 100 * 100) / 100;
		ap.setVolume(roundOff);
		String var = "mute";
		btnToggleMute.setAccessibleText(var);
		muteBtnImage.setImage(new Image("/artwork/unmute.png"));
	}

	/**
	 * This method sets up the scene and stage for the level selector screen.
	 * 
	 * @param username String name that the player logged in with.
	 * @throws Exception Exception thrown to satisfy levelUnlocker.
	 */
	public LevelLoadController(String username) throws Exception {
		ap = new AudioPlayer(LEVEL_SELECTOR_MUSIC);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LevelSelectionGUI.fxml"));
		loader.setController(this);
		Stage levelselectStage = new Stage();
		Pane pane = loader.load();
		Scene scene = new Scene(pane);
		levelselectStage.setScene(scene);
		levelselectStage.setTitle("The Welsh Dragon - Level Selector");
		levelselectStage.setResizable(false);
		levelselectStage.setMinHeight(400);
		levelselectStage.setMinWidth(600);
		levelselectStage.show();
		levelselectStage.centerOnScreen();
		txtUsername.setText(username);
		tickGame = new Timeline(new KeyFrame(Duration.millis(1000), event -> {
			try {
				levelUnlocker(username);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}));
		// Loop the timeline forever
		tickGame.setCycleCount(Animation.INDEFINITE);
		
		tickGame.play();
		

		sliderVolumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_value, Number new_value) {
				double roundOff = (double) Math.round(sliderVolumeSlider.getValue() / 100 * 100) / 100;
				changeVolume(roundOff);
			}
		});
	}

	/**
	 * 
	 * @param username
	 * @throws IOException
	 */
	private void levelUnlocker(String username) throws IOException {
		URL filechecker = getClass().getResource("/playerdata/" + username + ".txt");
		if (filechecker != null) {
			BufferedReader in = new BufferedReader(new InputStreamReader(filechecker.openStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.toString().contains("2")) {
					curve1.setOpacity(100);
					curve2.setOpacity(100);
					btnLevelTwo.setOpacity(100);
					btnLevelTwo.setDisable(false);
				} else if (inputLine.toString().contains("3")) {
					curve1.setOpacity(100);
					curve2.setOpacity(100);
					curve3.setOpacity(100);
					btnLevelTwo.setOpacity(100);
					btnLevelThree.setOpacity(100);
					btnLevelTwo.setDisable(false);
					btnLevelThree.setDisable(false);
				} else if (inputLine.toString().contains("4")) {
					curve1.setOpacity(100);
					curve2.setOpacity(100);
					curve3.setOpacity(100);
					curve4.setOpacity(100);
					btnLevelTwo.setOpacity(100);
					btnLevelThree.setOpacity(100);
					btnLevelFour.setOpacity(100);
					btnLevelTwo.setDisable(false);
					btnLevelThree.setDisable(false);
					btnLevelFour.setDisable(false);
				} else if (inputLine.toString().contains("5")) {
					curve1.setOpacity(100);
					curve2.setOpacity(100);
					curve3.setOpacity(100);
					curve4.setOpacity(100);
					curve5.setOpacity(100);
					btnLevelTwo.setOpacity(100);
					btnLevelThree.setOpacity(100);
					btnLevelFour.setOpacity(100);
					btnLevelFive.setOpacity(100);
					btnLevelTwo.setDisable(false);
					btnLevelThree.setDisable(false);
					btnLevelFour.setDisable(false);
					btnLevelFive.setDisable(false);
				}
			}
			in.close();
		}
	}
}