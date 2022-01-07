package swan.group7.dragon.launcher;

import javafx.application.Application;
import javafx.stage.Stage;
import swan.group7.dragon.controllers.LoginGUIController;

public class Launcher extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		new LoginGUIController(stage);
	}

	public static void main(String[] args) {
		launch(args);
	}
}