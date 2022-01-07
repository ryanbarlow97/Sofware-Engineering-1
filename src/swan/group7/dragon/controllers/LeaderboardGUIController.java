package swan.group7.dragon.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.json.JSONObject;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LeaderboardGUIController {

    private static final String LEADERBOARD_MUSIC = "LeaderboardMusic";

    @FXML
    private Button btnToggleMute;

    @FXML
    private TableView<LeaderboardRow> leaderTable;

    @FXML
    private Text txtUsername;

    @FXML
    private Button btnMainMenu;

    @FXML
    private ChoiceBox<String> cbMapSelector;

    @FXML
    private StackPane spLeaderboard;

    @FXML
    private ImageView imgBackground;

    @FXML
    private ImageView muteBtnImage;

    @FXML
    private Slider sliderVolumeSlider;

    private static AudioPlayer ap;

    TableColumn<LeaderboardRow, String> nameColumn = new TableColumn<LeaderboardRow, String>("Name");
    TableColumn<LeaderboardRow, String> scoreColumn = new TableColumn<LeaderboardRow, String>("Score");

    @FXML
    protected void changeVolume(double roundOff) {
        // double d = sliderVolumeSlider.getValue()/100;
        // DecimalFormat f = new DecimalFormat("0.00");
        ap.setVolume(roundOff);
        // AudioPlayer.setVolume(roundOff);
    }

    protected void updateScoreboard(String levelSelection) throws FileNotFoundException {
        File leaderboardFile = new File(".\\resources\\highscores\\" + levelSelection + ".json");
        ObservableList<LeaderboardRow> data = FXCollections.observableArrayList();
        leaderTable.getItems().clear();
        nameColumn.setPrefWidth(325.0);
        scoreColumn.setPrefWidth(174.0);
        nameColumn.setCellValueFactory(new PropertyValueFactory<LeaderboardRow, String>("Name"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<LeaderboardRow, String>("Score"));
        if (leaderboardFile.exists()) {
            Scanner scanner = new Scanner(leaderboardFile);
            while (scanner.hasNextLine()) {
                JSONObject json = new JSONObject(scanner.nextLine());
                data.add(new LeaderboardRow(json.keys().next().toString(), json.get(json.keys().next()).toString()));
            }
            leaderTable.setItems(data);
            scanner.close();
        }

    }

    void muteMusic() {
        ap.mute();
        // AudioPlayer.mute();
        String var = "unmute";
        btnToggleMute.setAccessibleText(var);
        muteBtnImage.setImage(new Image("/artwork/mute.png"));
    }

    void unmuteMusic() {
        double roundOff = (double) Math.round(sliderVolumeSlider.getValue() / 100 * 100) / 100;
        ap.setVolume(roundOff);
        // AudioPlayer.setVolume(roundOff);
        String var = "mute";
        btnToggleMute.setAccessibleText(var);
        muteBtnImage.setImage(new Image("/artwork/unmute.png"));
    }

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

    @FXML
    void mainmenu(ActionEvent event) throws Exception {
        ap.stop();
        // AudioPlayer.stop();
        Stage stage = (Stage) btnMainMenu.getScene().getWindow();
        new MainMenuGUIController(stage, txtUsername.getText());
    }

    public LeaderboardGUIController(Stage leaderboardStage, String username) throws IOException {
        ap = new AudioPlayer(LEADERBOARD_MUSIC);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LeaderboardGUI.fxml"));
        loader.setController(this);

        Pane pane = loader.load();
        Scene scene = new Scene(pane);
        leaderboardStage.setScene(scene);
        leaderboardStage.setMinHeight(480);
        leaderboardStage.setMinWidth(852);
        leaderboardStage.setTitle("The Welsh Dragon");
        leaderboardStage.setResizable(true);

        imgBackground.fitHeightProperty().bind(spLeaderboard.heightProperty());
        imgBackground.fitWidthProperty().bind(spLeaderboard.widthProperty());

        // getItems retruns the ObserableList object which you can add items to
        cbMapSelector.getItems().addAll("Level One", "Level Two", "Level Three", "Level Four", "Level Five");

        // sets default value for choicebox
        cbMapSelector.setValue("Please select a level");

        leaderboardStage.show();
        txtUsername.setText(username);
        leaderTable.getColumns().add(nameColumn);
        leaderTable.getColumns().add(scoreColumn);
        leaderTable.setOnMouseClicked( (MouseEvent event) -> {
            leaderTable.sort();
            leaderTable.refresh();
        });
        cbMapSelector.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String old_value, String new_value) {
                try {
                    updateScoreboard(new_value);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        // adds a listener to the slider, if the slider volume changes, change the
        // volume to the new value
        sliderVolumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_value, Number new_value) {
                double roundOff = (double) Math.round(sliderVolumeSlider.getValue() / 100 * 100) / 100;
                changeVolume(roundOff);
            }
        });
    }
}