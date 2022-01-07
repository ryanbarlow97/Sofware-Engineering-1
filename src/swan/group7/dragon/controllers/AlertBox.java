package swan.group7.dragon.controllers;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import swan.group7.dragon.PlayerFileWriter;
import swan.group7.dragon.PlayerProfile;

/**
 * 
 * @author _____ (JavaDoc Mollie Stamp)
 * @version 1.5
 */
public class AlertBox {

	/**
	 * This method is used to pop up if the inputed name doesn't have a profile.
	 * Asks user for details and then creates the profile.
	 * 
	 * @param username   String of inputed name
	 * @param logonStage The stage of the logon screen
	 */
	public static void alert(String username, Stage logonStage) {
		Stage popup = new Stage();
		popup.initModality(Modality.APPLICATION_MODAL);
		popup.setTitle("The Welsh Dragon Profile Creator");
		popup.setMinWidth(250);
		TextFlow warningMessage = new TextFlow();
		Text normalTextOne = new Text("The username ");
		normalTextOne.setFill(Color.BLACK);
		Text usernamewarn = new Text(username);
		usernamewarn.setFill(Color.RED);
		Text normalTextTwo = new Text(" doesn't exist, do you want to create this user?");
		normalTextTwo.setFill(Color.BLACK);
		warningMessage.getChildren().addAll(normalTextOne, usernamewarn, normalTextTwo);
		Button create = new Button("Create User");
		Button cancel = new Button("Cancel");
		create.setOnAction(e -> { // if the user decides to make a new profile with the name they entered
			try {
				PlayerProfile player = new PlayerProfile(username); // creates a new playerProfile and writes it to a
																	// player file
				PlayerFileWriter.writeFile(player);
				logonStage.close();
				new MainMenuGUIController(popup, username);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		cancel.setOnAction(e -> popup.close());
		VBox layout = new VBox(2);
		HBox buttonLayout = new HBox(2);
		buttonLayout.setAlignment(Pos.CENTER);
		buttonLayout.getChildren().addAll(create, cancel);
		layout.getChildren().addAll(warningMessage, buttonLayout);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		popup.setScene(scene);
		popup.setResizable(false);
		popup.centerOnScreen();
		popup.show();
	}

}
