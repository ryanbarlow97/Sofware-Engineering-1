package swan.group7.dragon.controllers;

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
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import swan.group7.dragon.HttpDataManipulation;

/**
 * MainMenuGUIController.java This class deals with the Menu screen.
 * 
 * @author Ryan Barlow (JavaDoc Mollie Stamp)
 * @version 1.5
 */
public class MainMenuGUIController {

	private static final String MAIN_MENU_MUSIC = "MainMenuMusic";

	@FXML
	private Text lblMOTD;
	@FXML
	private Text txtUsername;
	@FXML
	private Button btnLeaderboard;
	@FXML
	private ImageView imgBackground;
	@FXML
	private Button btnPlay;
	@FXML
	private Button btnToggleMute;
	@FXML
	private Button btnLogOut;
	@FXML
	private Slider sliderVolumeSlider;
	@FXML
	private ImageView muteBtnImage;
	@FXML
	private StackPane mainMenuStackPane;
	private static AudioPlayer ap;

	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	void leaderboard(ActionEvent event) throws Exception {
		ap.stop();
		// AudioPlayer.stop();
		Stage stage = (Stage) btnLeaderboard.getScene().getWindow();
		new LeaderboardGUIController(stage, txtUsername.getText());
	}

	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	void play(ActionEvent event) throws Exception {
		ap.stop();
		// AudioPlayer.stop();
		Stage stage = (Stage) btnPlay.getScene().getWindow();
		stage.close();
		new LevelLoadController(txtUsername.getText());
	}

	/**
	 * This method is used to mute the music player and change the icon to reflect
	 * that.
	 */
	void muteMusic() {
		ap.mute();
		// AudioPlayer.mute();
		String var = "unmute";
		btnToggleMute.setAccessibleText(var);
		muteBtnImage.setImage(new Image("/artwork/mute.png"));
	}

	/**
	 * This method is used to unmute the music player and change the icon to reflect
	 * that.
	 */
	void unmuteMusic() {
		double roundOff = (double) Math.round(sliderVolumeSlider.getValue() / 100 * 100) / 100;
		ap.setVolume(roundOff);
		// AudioPlayer.setVolume(roundOff);
		String var = "mute";
		btnToggleMute.setAccessibleText(var);
		muteBtnImage.setImage(new Image("/artwork/unmute.png"));
	}

	/**
	 * 
	 * @param event The drag event itself which contains data about the drag that
	 *              occurred.
	 * @throws Exception Exception thrown to satisfy LoginGUIController.
	 */
	@FXML
	void logOut(ActionEvent event) throws Exception {
		ap.stop();
		// AudioPlayer.stop();
		Stage stage = (Stage) btnLogOut.getScene().getWindow();
		new LoginGUIController(stage);
	}

	/**
	 * This method is used to deal with the volume button being clicked, and calls
	 * the correct method to mute or unmute the music.
	 * 
	 * @param event The drag event itself which contains data about the drag that
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
	 * 
	 * This method sets up the main menu window and populates it with given details.
	 * 
	 * @param mainmenuStage Stage with the mainmenu on it.
	 * @param username      String name given by user to log in.
	 * @throws Exception Exception thrown to satisfy .readMOTD
	 */
	public MainMenuGUIController(Stage mainmenuStage, String username) throws Exception {
		ap = new AudioPlayer(MAIN_MENU_MUSIC);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenuGUI.fxml"));
		loader.setController(this);
		Pane pane = loader.load();
		Scene scene = new Scene(pane);
		mainmenuStage.setScene(scene);
		mainmenuStage.setTitle("The Welsh Dragon");
		mainmenuStage.setMinHeight(480);
		mainmenuStage.setMinWidth(720);
		mainmenuStage.centerOnScreen();
		imgBackground.fitHeightProperty().bind(mainMenuStackPane.heightProperty());
		imgBackground.fitWidthProperty().bind(mainMenuStackPane.widthProperty());
		txtUsername.setText(username);
		mainmenuStage.show();
		try {
			HttpDataManipulation htmlData = new HttpDataManipulation();

			lblMOTD.setText(htmlData.readMOTD());
		} catch (Exception e) { // System.out.println("code is changing");
			HttpDataManipulation htmlData = new HttpDataManipulation();

			lblMOTD.setText(htmlData.readMOTD());
		}
		sliderVolumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_value, Number new_value) {

				double roundOff = (double) Math.round(sliderVolumeSlider.getValue() / 100 * 100) / 100;
				System.out.println(roundOff);
				ap.setVolume(roundOff);
			}
		});
	}
}