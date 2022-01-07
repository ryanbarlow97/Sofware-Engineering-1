package swan.group7.dragon.controllers;

import java.io.IOException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * LoginGUIController.java
 * This class deals with the login screen. 
 * 
 * @author Ryan Barlow (JavaDoc Mollie Stamp)
 * @version 1.4
 *
 */
public class LoginGUIController {

	private static final String LEADERBOARD_MUSIC = "LoginScreenMusic";

	@FXML
	private Button btnLogin;
	@FXML
	private ImageView imgBackground;
	@FXML
	private StackPane spLogin;
	@FXML
	private TextField txtUsername;
	private static AudioPlayer ap;

	/**
	 * This method deals with btnLogin being clicked.
	 * 
	 * @param event The drag event itself which contains data about the drag that
	 *              occurred.
	 * @throws Exception Exception thrown to satisfy MainMenuGUIController.java.
	 */
	@FXML
	void btnLogon(ActionEvent event) throws Exception {
		String playername = txtUsername.getText();
		URL filechecker = getClass().getResource("/playerdata/" + playername + ".txt");
		if (filechecker != null) {
			ap.stop();
			System.out.println();
			Stage stage = (Stage) btnLogin.getScene().getWindow();
			new MainMenuGUIController(stage, playername);
		} else if (txtUsername.getText().isEmpty()) {
			System.out.println("Please enter a name");
		} else {
			ap.stop();
			Stage stage = (Stage) btnLogin.getScene().getWindow();
			AlertBox.alert(playername, stage);
		}
	}

	/**
	 * This method deals with the login screen. It uses the FXML file to construct
	 * it.
	 * 
	 * @param loginStage Stage contains the login screen.
	 * @throws IOException Exception thrown to satisfy loader.
	 */
	public LoginGUIController(Stage loginStage) throws IOException {
		ap = new AudioPlayer(LEADERBOARD_MUSIC);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginFXML.fxml"));
		loader.setController(this);
		Pane pane = loader.load();
		Scene scene = new Scene(pane);
		loginStage.setScene(scene);
		loginStage.setMinHeight(480);
		loginStage.setMinWidth(852);
		loginStage.setTitle("The Welsh Dragon - Login");
		imgBackground.fitHeightProperty().bind(spLogin.heightProperty());
		imgBackground.fitWidthProperty().bind(spLogin.widthProperty());
		loginStage.centerOnScreen();
		loginStage.show();
		pane.requestFocus();
	}
}