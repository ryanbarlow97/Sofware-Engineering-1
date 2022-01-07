package swan.group7.dragon.controllers;

import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.io.FileWriter;
import java.io.File;

import org.json.JSONObject;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import swan.group7.dragon.*;
import swan.group7.dragon.IO.FileReaderIO;
import swan.group7.dragon.IO.LoadIO;
import swan.group7.dragon.IO.SaveIO;

public class CanvasController {

	private static final String ITEM_HOVER = "hoverOverMenuItem";
	// private static final String ITEM_CLICK = "clickOnMenuItem";
	private static final String ITEM_HOVER_BACKGROUND = "dropshadow(gaussian, rgba(255,255,0,0.6), 10, 0.9, 0.0, 0.0)";

	private CopyOnWriteArrayList<Weapon> weapons = new CopyOnWriteArrayList<Weapon>();
	private CopyOnWriteArrayList<Dragon> dragons = new CopyOnWriteArrayList<Dragon>();

	@FXML
	private Canvas adultDragonCanvas;
	@FXML
	private Canvas tileCanvas;
	@FXML
	private Canvas babyDragonCanvas;
	@FXML
	private Canvas weaponCanvas;
	@FXML
	private Canvas explosionCanvas;
	@FXML
	private Canvas tunnelCanvas;

	@FXML
	private Text txtScore;
	@FXML
	private Text txtCompletedLv;
	@FXML
	private Text txtFinalScore;

	@FXML
	private Text txtPotion;
	@FXML
	private Text txtCOA;
	@FXML
	private Text txtBomb;
	@FXML
	private Text txtMaleSpell;
	@FXML
	private Text txtFemaleSpell;
	@FXML
	private Text txtCastle;
	@FXML
	private Text txtGas;
	@FXML
	private Text txtDeathDragon;

	private GraphicsContext gcTile;
	private GraphicsContext gcEntities;
	private GraphicsContext gcWeapons;
	private GraphicsContext gcExplosion;
	private GraphicsContext gcTunnel;

	private ImageView selectedImg;

	// Timeline which will cause tick method to be called periodically.
	private Timeline tickGame;
	private Timeline tickBabyDragons;
	private Timeline tickSeconds;
	private Timeline tickFiveSeconds;
	// The width and height (in pixels) of each cell that makes up the game.
	private static final int GRID_CELL_WIDTH = 50;
	private static final int GRID_CELL_HEIGHT = 50;

	// The width of the grid in number of cells.
	public static final int GRID_WIDTH = 22;

	// The height of the grid in number of cells.
	public static final int GRID_HEIGHT = 12;
	private static final String MAIN_MENU_MUSIC = "LoginScreenMusic";

	private int score;

	private SaveIO saveIO = new SaveIO();
	private LoadIO loadIO = new LoadIO();
	private Inventory inventory = new Inventory();

	@FXML
	private Pane levelSelector;

	@FXML
	private Button btnSaveAndQuit;

	@FXML
	private Text txtCurrTime;

	@FXML
	private Text txtFinishTime;

	@FXML
	private Button btnToggleMute;

	@FXML
	private Button startTickTimelineButton;

	@FXML
	private Button stopTickTimelineButton;

	@FXML
	private ImageView toggleMute;

	@FXML
	private Text txtDragonPop;

	@FXML
	private Text txtMaxDragonPop;

	@FXML
	private Text txtMaleDragons;

	@FXML
	private Text txtFemaleDragons;

	@FXML
	private Slider sliderVolumeSlider;

	@FXML
	private Text txtUsername;

	@FXML
	private BorderPane bpCenrte;

	private String levelName;

	private AudioPlayer ap;

	private void setSelectedImage(ImageView selectedImg) {
		this.selectedImg = selectedImg;
	}

	public ImageView getSelectedImage(ImageView selectedImg) {
		return selectedImg;
	}

	@FXML
	void mainmenu(ActionEvent event) throws Exception {
		ap.stop();
		// AudioPlayer.stop();
		tickBabyDragons.stop();
		tickGame.stop();
		tickSeconds.stop();
		tickFiveSeconds.stop();

		saveIO.saveGame(levelName, txtUsername, weapons, dragons, score, txtCurrTime);
		Stage stage = (Stage) txtUsername.getScene().getWindow();
		new MainMenuGUIController(stage, txtUsername.getText());
	}

	void muteMusic() {
		String var = "unmute";
		btnToggleMute.setAccessibleText(var);
		toggleMute.setImage(new Image("/artwork/mute.png"));
		sliderVolumeSlider.setDisable(true);

	}

	void unmuteMusic() {
		double roundOff = (double) Math.round(sliderVolumeSlider.getValue() / 100 * 100) / 100;
		ap.setVolume(roundOff);
		String var = "mute";
		btnToggleMute.setAccessibleText(var);
		toggleMute.setImage(new Image("/artwork/unmute.png"));
		sliderVolumeSlider.setDisable(false);

	}

	@FXML
	void toggleMute(ActionEvent event) {
		if (btnToggleMute.getAccessibleText().toString().equals("unmute")) {
			unmuteMusic();
		} else if (btnToggleMute.getAccessibleText().toString().equals("mute")) {
			muteMusic();
		}
	}

	@FXML
	void btnStart(ActionEvent event) {
		startTickTimelineButton.setDisable(true);

		tickGame.play();
		tickBabyDragons.play();
		tickFiveSeconds.play();
		tickSeconds.play();
		stopTickTimelineButton.setDisable(false);
		btnSaveAndQuit.setDisable(true);
		ap.stop();

		ap.setSong("level" + getLevelName() + "Music");

	}

	@FXML
	void btnStop(ActionEvent event) {
		stopTickTimelineButton.setDisable(true);
		tickSeconds.stop();
		tickFiveSeconds.stop();

		tickGame.stop();
		startTickTimelineButton.setDisable(false);
		btnSaveAndQuit.setDisable(false);
		ap.stop();
		// AudioPlayer.stop();
		ap.setSong(MAIN_MENU_MUSIC);
	}

	@FXML
	void itemHover(MouseEvent event) {
		if (btnToggleMute.getAccessibleText().toString().equals("mute")) {
			new AudioPlayer(ITEM_HOVER);
		}
		ImageView imgName = (ImageView) event.getSource();
		imgName.setStyle("-fx-effect: " + ITEM_HOVER_BACKGROUND);
	}

	@FXML
	void itemHoverExit(MouseEvent event) {
		ImageView imgName = (ImageView) event.getSource();
		imgName.setStyle("");
	}

	public CanvasController(Stage gameCanvas, String levelName, String username, String maxTimeAllowed,
			String maxDragons) throws Exception {
		ap = new AudioPlayer(MAIN_MENU_MUSIC);

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CanvasGUI.fxml"));
		loader.setController(this);
		setLevelName(levelName);
		Pane pane = loader.load();
		Scene scene = new Scene(pane);
		gameCanvas.setScene(scene);
		gameCanvas.setMinHeight(720);
		gameCanvas.setMinWidth(1280);
		gameCanvas.setTitle("The Welsh Dragon - Level " + levelName + "!");
		gameCanvas.centerOnScreen();
		gameCanvas.setResizable(true);
		gameCanvas.show();

		txtUsername.setText(username);
		if (loadIO.doesSaveExist(levelName, txtUsername)) {
			txtCurrTime.setText(loadIO.loadOldTime(levelName, txtUsername));
			score = loadIO.loadOldScore(levelName, txtUsername);
			txtScore.setText(Integer.toString(score));
		} else {
			txtCurrTime.setText("0");
			score = 0;
		}
		txtFinishTime.setText(maxTimeAllowed);
		txtMaxDragonPop.setText(maxDragons);

		drawGame(levelName);

		tickGame = new Timeline(new KeyFrame(Duration.millis(500), event -> tickGameEngine()));
		// Loop the timeline forever
		tickGame.setCycleCount(Animation.INDEFINITE);

		tickBabyDragons = new Timeline(new KeyFrame(Duration.millis(250), event -> babyDragonLogic()));
		// Loop the timeline forever
		tickBabyDragons.setCycleCount(Animation.INDEFINITE);

		tickSeconds = new Timeline(new KeyFrame(Duration.millis(1000), event -> {
			try {
				TickSeconds();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}));
		// Loop the timeline forever ****************** NEEDS CHANGING TO: stop when
		tickSeconds.setCycleCount(Animation.INDEFINITE);
		// (currenttime >=
		// finishtimeTickSecondsload.setCycleCount(Animation.INDEFINITE);

		tickFiveSeconds = new Timeline(new KeyFrame(Duration.millis(5000), event -> tickFiveSeconds()));
		// Loop the timeline forever ****************** NEEDS CHANGING TO: stop when
		tickFiveSeconds.setCycleCount(Animation.INDEFINITE);

		// adds a listener to the slider, if the slider volume changes, change the
		// volume to the new value
		sliderVolumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_value, Number new_value) {
				double roundOff = (double) Math.round(sliderVolumeSlider.getValue() / 100 * 100) / 100;
				changeVolume(roundOff);
			}

			private void changeVolume(double roundOff) {
				ap.setVolume(roundOff);
			}
		});

		// LoadIO doesn't return how many weapon quantity is left
		// so....assume all quantity to be 0 atm
		HashMap<String, Integer> weaponsInv = new HashMap<String, Integer>();

		// initialise all quantity of weapon inventory to be 0
		for (WeaponTypeEnum weapoonType : WeaponTypeEnum.values()) {
			weaponsInv.put(weapoonType.name(), 0);
		}
		weaponsInv.remove("LIGHTNING");
		inventory.setWeapons(weaponsInv);
	}

	private void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getLevelName() {
		return levelName;
	}

	private void reloadWeapon() {
		for (HashMap.Entry<String, Integer> tempWeapon : inventory.getWeapons().entrySet()) {
			String key = tempWeapon.getKey();
			Integer qty = tempWeapon.getValue();

			// maximum quantity of each weapon is set to 4
			if (qty < 4) {
				qty++;
				inventory.getWeapons().replace(key, qty);

				if (key.equals("COA")) {
					txtCOA.setText(Integer.toString(qty));
				} else if (key.equals("CASTLE")) {
					txtCastle.setText(Integer.toString(qty));
				} else if (key.equals("WIZARD")) {
					txtBomb.setText(Integer.toString(qty));
				} else if (key.equals("MALE_SPELL")) {
					txtMaleSpell.setText(Integer.toString(qty));
				} else if (key.equals("FEMALE_SPELL")) {
					txtFemaleSpell.setText(Integer.toString(qty));
				} else if (key.equals("BAD_POTION")) {
					txtPotion.setText(Integer.toString(qty));
				} else if (key.equals("FIRE_SMOKE")) {
					txtGas.setText(Integer.toString(qty));
				} else {
					txtDeathDragon.setText(Integer.toString(qty));
				}
			} else {

			}

		}
	}

	private void tickFiveSeconds() {
		reloadWeapon();
	}

	private void TickSeconds() throws IOException {
		giveBirth();
		if (!txtCurrTime.getText().equals("overtime")) {
			if (Integer.valueOf(txtCurrTime.getText()) >= Integer.valueOf(txtFinishTime.getText())) {
				txtCurrTime.setText("overtime");
				tickSeconds.stop();
				tickBabyDragons.stop();
				tickGame.stop();
				ap.stop();

				showResult("failed", (Stage) txtCurrTime.getScene().getWindow(), txtUsername.getText().toString());

			} else {
				txtCurrTime.setText((Integer.valueOf(txtCurrTime.getText()) + 1) + "");
			}
		}

		// check whether all dragons are killed
		if (dragons.size() == 0) {
			// kill all the dragon
			// update PlayerProfile

			tickSeconds.stop();
			tickBabyDragons.stop();
			tickGame.stop();
			tickFiveSeconds.stop();
			ap.stop();
			showResult("complete", (Stage) txtCurrTime.getScene().getWindow(), txtUsername.getText().toString());

		}
		countDragons();

	}

	private void countDragons() {
		int maleDragons = 0;
		int femaleDragons = 0;

		for (Dragon dragon : dragons) {
			if (dragon.getDragonType() == DragonTypeEnum.MALE_DRAGON) {
				maleDragons++;
			}

		}

		for (Dragon dragon : dragons) {
			if (dragon.getDragonType() == DragonTypeEnum.FEMALE_DRAGON) {
				femaleDragons++;
			}

		}
		txtMaleDragons.setText(Integer.valueOf(maleDragons).toString());
		txtFemaleDragons.setText(Integer.valueOf(femaleDragons).toString());
	}

	private void checkDragonPop() {
		txtDragonPop.setText(Integer.valueOf(dragons.size()).toString());
		if (dragons.size() > Integer.valueOf(txtMaxDragonPop.getText())) {
			tickSeconds.stop();
			tickGame.stop();
			tickBabyDragons.stop();
			tickFiveSeconds.stop();
			ap.stop();
			new AudioPlayer("missionfailed");

			try {
				showResult("failed", (Stage) txtDragonPop.getScene().getWindow(), txtUsername.getText().toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	Tile[][] tileGrid1;

	public void drawGame(String levelName) throws IOException {
		FileReaderIO fr = new FileReaderIO();
		Tile[][] tileGrid = fr.getTileGrid("/maps/" + levelName + ".txt");

		// game load logic
		if (loadIO.doesSaveExist(levelName, txtUsername)) {
			dragons = loadIO.loadOldDragons(levelName, txtUsername);
			weapons = loadIO.loadOldWeapons(levelName, txtUsername);
		} else {
			// there are only dragons needed to load at the beginning of the game
			dragons = loadIO.loadNewGame(levelName);
		}

		gcTile = tileCanvas.getGraphicsContext2D();
		gcTunnel = tunnelCanvas.getGraphicsContext2D();

		gcTile.clearRect(0, 0, tileCanvas.getWidth(), tileCanvas.getHeight());
		gcTunnel.clearRect(0, 0, tunnelCanvas.getWidth(), tunnelCanvas.getHeight());
		// Draw rows & columns of dirt images
		// We multiply by the cell width and height to turn a coordinate in our grid
		// into a pixel coordinate.
		// We draw the row at y value 2.
		String imgPath = "";
		TileTypeEnum tileType = null;

		for (int x = 0; x < GRID_WIDTH; x++) {
			for (int y = 0; y < GRID_HEIGHT; y++) {
				tileType = tileGrid[x][y].getTileType();
				imgPath = tileType.getImagePath();

				if (tileType == TileTypeEnum.TUNNEL_STONE) {
					gcTunnel.drawImage(new Image(imgPath), x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT, 49, 49);
				} else {
					gcTile.drawImage(new Image(imgPath), x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT, 49, 49);
				}

			}
		}
		tileGrid1 = tileGrid;
	}

	public void tickGameEngine() {
		shouldSpawnBabies();
		drawDragons();
		drawWeapons();
		drawExplosion();
		dragonLogic();
		wizardLogic();
		weaponLogic();
		fireLogic();
		checkDragonPop();
	}

	private void babyDragonLogic() {
		for (Dragon dragon : dragons) {
			if (dragon.getMating() == false && (dragon.getDragonType() == DragonTypeEnum.BABY_DRAGON_F
					|| dragon.getDragonType() == DragonTypeEnum.BABY_DRAGON_M)) {
				dragon.moveAround(tileGrid1, weapons);
			}
		}
	}

	private void drawDragons() {
		gcEntities = adultDragonCanvas.getGraphicsContext2D();
		gcEntities.clearRect(0, 0, adultDragonCanvas.getWidth(), adultDragonCanvas.getHeight());
	}

	private void drawWeapons() {
		gcWeapons = weaponCanvas.getGraphicsContext2D();
		gcWeapons.clearRect(0, 0, weaponCanvas.getWidth(), weaponCanvas.getHeight());
	}

	private void drawExplosion() {
		gcExplosion = explosionCanvas.getGraphicsContext2D();
		gcExplosion.clearRect(0, 0, explosionCanvas.getWidth(), explosionCanvas.getHeight());
	}

	private void dragonLogic() {
		for (Dragon dragon : dragons) {
			int recentlyPregnant = dragon.getRecentlyPregnant();
			if (dragon.getHP() <= 0) {
				dragons.remove(dragon);
			} else {
				if (dragon.getRecentlyPregnant() > 0 && dragon.getRecentlyPregnant() < 20) {
					dragon.setRecentlyPregnant(recentlyPregnant + 1);
				}
				if (dragon.getRecentlyPregnant() == 20) {
					dragon.setRecentlyPregnant(0);
					dragon.setBreedable(true);
				}

				if (dragon.getDragonType() == DragonTypeEnum.BABY_DRAGON_F
						|| dragon.getDragonType() == DragonTypeEnum.BABY_DRAGON_M) {
					if (dragon.getGrowth() < 10) {
						dragon.setGrowth(dragon.getGrowth() + 1);
					} else {
						dragon.growUp();
					}
				}
				gcEntities.drawImage(dragon.applyRotation(new Image(dragon.getDragonType().getImagePath()), dragon),
						dragon.getX() * GRID_CELL_WIDTH, dragon.getY() * GRID_CELL_HEIGHT, 50, 50);
				if (dragon.getMating() == false && (dragon.getDragonType() == DragonTypeEnum.MALE_DRAGON
						|| dragon.getDragonType() == DragonTypeEnum.FEMALE_DRAGON)) {
					dragon.moveAround(tileGrid1, weapons);

				}
			}
		}
	}

	private void fireLogic() {
		for (Weapon weapon : weapons) {
			if (weapon.getWeaponType() == WeaponTypeEnum.FIRE_SMOKE) {
				if (weapon.getHP() <= 10 && weapon.getHP() > 1) {
					weapon.setHP(weapon.getHP() - 1);
					Random rand = new Random();
					int randx = rand.nextInt(4);
					if (randx == 4) {
						weapons.add(
								new Weapon(2, (int) weapon.getX() + 1, (int) weapon.getY(), WeaponTypeEnum.FIRE_SMOKE));
						gcExplosion.drawImage(new Image(WeaponTypeEnum.FIRE_SMOKE.getImagePath()),
								weapon.getX() * GRID_CELL_WIDTH, weapon.getY() * GRID_CELL_HEIGHT, 50, 50);
					} else if (randx == 3) {
						weapons.add(
								new Weapon(2, (int) weapon.getX() - 1, (int) weapon.getY(), WeaponTypeEnum.FIRE_SMOKE));
						gcExplosion.drawImage(new Image(WeaponTypeEnum.FIRE_SMOKE.getImagePath()),
								weapon.getX() * GRID_CELL_WIDTH, weapon.getY() * GRID_CELL_HEIGHT, 50, 50);
					} else if (randx == 2) {
						weapons.add(
								new Weapon(2, (int) weapon.getX(), (int) weapon.getY() + 1, WeaponTypeEnum.FIRE_SMOKE));
						gcExplosion.drawImage(new Image(WeaponTypeEnum.FIRE_SMOKE.getImagePath()),
								weapon.getX() * GRID_CELL_WIDTH, weapon.getY() * GRID_CELL_HEIGHT, 50, 50);
					} else if (randx == 1) {
						weapons.add(
								new Weapon(2, (int) weapon.getX(), (int) weapon.getY() - 1, WeaponTypeEnum.FIRE_SMOKE));
						gcExplosion.drawImage(new Image(WeaponTypeEnum.FIRE_SMOKE.getImagePath()),
								weapon.getX() * GRID_CELL_WIDTH, weapon.getY() * GRID_CELL_HEIGHT, 50, 50);
					}
				} else if (weapon.getHP() == 1) {
					weapon.setHP(0);
				}
			}
		}
	}

	private void wizardLogic() {
		for (Weapon weapon : weapons) {
			if (weapon.getWeaponType() == WeaponTypeEnum.WIZARD) {
				if (weapon.getHP() <= 10 && weapon.getHP() > 1) {
					weapon.setHP(weapon.getHP() - 1);

					if (weapon.getHP() == 10 || weapon.getHP() == 9) {
						gcWeapons.drawImage(new Image("" + getClass().getResource("/artwork/Wizard_4.png")),
								weapon.getX() * GRID_CELL_WIDTH,
								weapon.getY() * GRID_CELL_HEIGHT, 50, 50);

					} else if (weapon.getHP() == 8 || weapon.getHP() == 7) {
						gcWeapons.drawImage(new Image("" + getClass().getResource("/artwork/Wizard_3.png")),
								weapon.getX() * GRID_CELL_WIDTH,
								weapon.getY() * GRID_CELL_HEIGHT, 50, 50);

					} else if (weapon.getHP() == 6 || weapon.getHP() == 5) {
						gcWeapons.drawImage(new Image("" + getClass().getResource("/artwork/Wizard_2.png")),
								weapon.getX() * GRID_CELL_WIDTH,
								weapon.getY() * GRID_CELL_HEIGHT, 50, 50);

					} else if (weapon.getHP() == 4 || weapon.getHP() == 3) {
						gcWeapons.drawImage(new Image("" + getClass().getResource("/artwork/Wizard_1.png")),
								weapon.getX() * GRID_CELL_WIDTH,
								weapon.getY() * GRID_CELL_HEIGHT, 50, 50);

					}
				} else if (weapon.getHP() <= 2) {
					gcWeapons.drawImage(new Image("" + getClass().getResource("/artwork/Wizard.png")),
							weapon.getX() * GRID_CELL_WIDTH,
							weapon.getY() * GRID_CELL_HEIGHT, 50, 50);
					weapon.setHP(0);
					new AudioPlayer("bomb");
					wizardMovement(weapon);
				}
			}
		}
	}

	private void wizardMovement(Weapon wizard) {
		int left = wizard.wizardExplosion(tileGrid1, (int) wizard.getX(), (int) wizard.getY(), "LEFT ");
		int right = wizard.wizardExplosion(tileGrid1, (int) wizard.getX(), (int) wizard.getY(), "RIGHT ");
		int up = wizard.wizardExplosion(tileGrid1, (int) wizard.getX(), (int) wizard.getY(), "UP ");
		int down = wizard.wizardExplosion(tileGrid1, (int) wizard.getX(), (int) wizard.getY(), "DOWN ");

		weapons.add(new Weapon(1, (int) wizard.getX(), (int) wizard.getY(), WeaponTypeEnum.LIGHTNING));

		explode(left, (int) wizard.getX() - 1, (int) wizard.getY(), "LEFT ");
		explode(right, (int) wizard.getX() + 1, (int) wizard.getY(), "RIGHT ");
		explode(up, (int) wizard.getX(), ((int) wizard.getY()) - 1, "UP ");
		explode(down, (int) wizard.getX(), ((int) wizard.getY()) + 1, "DOWN ");
	}

	private void explode(int count, int x, int y, String direction) {
		if (count <= 0) {
			// do nothing and end
		} else {
			count--;
			int xx = GRID_CELL_HEIGHT * x;
			int yy = GRID_CELL_HEIGHT * y;

			weapons.add(new Weapon(1, x, y, WeaponTypeEnum.LIGHTNING));
			gcExplosion.drawImage(new Image(WeaponTypeEnum.LIGHTNING.getImagePath()), xx, yy, 50, 50);

			if (direction.equals("LEFT ")) {
				explode(count, x - 1, y, "LEFT ");
			} else if (direction.equals("RIGHT ")) {
				explode(count, x + 1, y, "RIGHT ");
			} else if (direction.equals("UP ")) {
				explode(count, x, y - 1, "UP ");
			} else if (direction.equals("DOWN ")) {
				explode(count, x, y + 1, "DOWN ");
			}
		}
	}

	private void weaponLogic() {
		for (Weapon weapon : weapons) {
			if (weapon.getHP() == 0) {
				weapons.remove(weapon);
			} else {
				if (weapon.getWeaponType() == WeaponTypeEnum.DEATH_DRAGON) {
					gcWeapons.drawImage(weapon.applyRotation(new Image(weapon.getWeaponType().getImagePath()), weapon),
							weapon.getX() * GRID_CELL_WIDTH, weapon.getY() * GRID_CELL_HEIGHT, 50, 50);
					weapon.moveAround(tileGrid1, weapons);
				} else {
					gcWeapons.drawImage(new Image(weapon.getWeaponType().getImagePath()),
							weapon.getX() * GRID_CELL_WIDTH,
							weapon.getY() * GRID_CELL_HEIGHT, 50, 50);
				}
				for (int j = 0; j < weapons.size(); j++) {
					for (int i = 0; i < dragons.size(); i++) {
						if (dragons.get(i).getX() == weapons.get(j).getX()
								&& dragons.get(i).getY() == weapons.get(j).getY()) {
							if (weapons.get(j).getWeaponType() == WeaponTypeEnum.COA) {
								new AudioPlayer("sword");
								weapons.get(j).setHP(0);
								dragons.get(i).setHP(0);

								score += 10;
								txtScore.setText(Integer.toString(score));

							} else if (weapons.get(j).getWeaponType() == WeaponTypeEnum.FEMALE_SPELL
									&& (dragons.get(i).getDragonType() == DragonTypeEnum.FEMALE_DRAGON ||
											dragons.get(i).getDragonType() == DragonTypeEnum.MALE_DRAGON)) {
								dragons.get(i).setDragonType(DragonTypeEnum.FEMALE_DRAGON);
								new AudioPlayer("spell");
								weapons.get(j).setHP(0);
								score += 5;
								txtScore.setText(Integer.toString(score));

							} else if (weapons.get(j).getWeaponType() == WeaponTypeEnum.MALE_SPELL
									&& (dragons.get(i).getDragonType() == DragonTypeEnum.FEMALE_DRAGON ||
											dragons.get(i).getDragonType() == DragonTypeEnum.MALE_DRAGON)) {
								dragons.get(i).setDragonType(DragonTypeEnum.MALE_DRAGON);
								new AudioPlayer("spell");
								weapons.get(j).setHP(0);
								score += 5;
								txtScore.setText(Integer.toString(score));

							} else if (weapons.get(j).getWeaponType() == WeaponTypeEnum.BAD_POTION
									&& dragons.get(i).getSterile() == false) {
								dragons.get(i).setSterile(true);
								weapons.get(j).setHP(0);
								score += 10;
								txtScore.setText(Integer.toString(score));

							} else if (weapons.get(j).getWeaponType() == WeaponTypeEnum.DEATH_DRAGON) {
								weapons.get(j).setHP(weapons.get(j).getHP() - 1);
								new AudioPlayer("dragonDeath");
								score += 10;
								dragons.get(i).setHP(0);
								txtScore.setText(Integer.toString(score));

							} else if (weapons.get(j).getWeaponType() == WeaponTypeEnum.LIGHTNING) {
								dragons.get(i).setHP(0);
								score += 10;
								txtScore.setText(Integer.toString(score));
							} else if (weapons.get(j).getWeaponType() == WeaponTypeEnum.CASTLE) {
								new AudioPlayer("wallcrumble");
								weapons.get(j).setHP(weapons.get(j).getHP() - 1);

							} else if (weapons.get(j).getWeaponType() == WeaponTypeEnum.FIRE_SMOKE) {
								new AudioPlayer("fire");
								int hp = dragons.get(i).getHP();
								if (dragons.get(i).getHP() == 1) {
									dragons.get(i).setHP(0);
									score += 10;
									txtScore.setText(Integer.toString(score));
								} else {
									dragons.get(i).setHP(hp - 1);
									score += 2;
									txtScore.setText(Integer.toString(score));
								}
							} else if (weapons.get(j).getWeaponType() == WeaponTypeEnum.BAD_POTION
									&& dragons.get(i).getSterile() == false) {
								if ((dragons.get(i).getX() >= (weapons.get(j).getX() - 2)
										&& dragons.get(i).getX() <= (weapons.get(j).getX() + 2))
										|| (dragons.get(i).getY() >= (weapons.get(j).getY() - 2)
												&& dragons.get(i).getY() <= (weapons.get(j).getY() + 2))) {
									dragons.get(i).setSterile(true);
									score += 10;
									txtScore.setText(Integer.toString(score));
								}
							}
						}
					}
				}

				for (Weapon b : weapons) {
					if (b.getWeaponType() == WeaponTypeEnum.LIGHTNING) {
						for (Weapon e : weapons) {
							if (b.getX() == e.getX() && b.getY() == e.getY()
									&& e.getWeaponType() != WeaponTypeEnum.LIGHTNING) {
								weapons.remove(e);
								// e.setHP(0); enables bomb chaining
							}

						}
					}
				}

				for (Weapon weap : weapons) {
					if (weap.getWeaponType() == WeaponTypeEnum.LIGHTNING) {
						weapons.remove(weap);
					}
					if (weap.getWeaponType() == WeaponTypeEnum.CASTLE) {
						if (weap.getHP() == 4) {
							gcWeapons.drawImage(new Image("" + getClass().getResource("/artwork/Castle_4.png")),
									weap.getX() * GRID_CELL_WIDTH,
									weap.getY() * GRID_CELL_HEIGHT, 50, 50);
						} else if (weap.getHP() == 3) {
							gcWeapons.drawImage(new Image("" + getClass().getResource("/artwork/Castle_3.png")),
									weap.getX() * GRID_CELL_WIDTH,
									weap.getY() * GRID_CELL_HEIGHT, 50, 50);
						} else if (weap.getHP() == 2) {
							gcWeapons.drawImage(new Image("" + getClass().getResource("/artwork/Castle_2.png")),
									weap.getX() * GRID_CELL_WIDTH,
									weap.getY() * GRID_CELL_HEIGHT, 50, 50);
						} else if (weap.getHP() == 1) {
							gcWeapons.drawImage(new Image("" + getClass().getResource("/artwork/Castle_1.png")),
									weap.getX() * GRID_CELL_WIDTH,
									weap.getY() * GRID_CELL_HEIGHT, 50, 50);
						}
					}
				}
			}
		}
	}

	private void shouldSpawnBabies() {
		for (int i = 0; i < dragons.size(); i++) {
			for (int j = 0; j < dragons.size(); j++) {
				if (dragons.get(i).getX() == dragons.get(j).getX() && dragons.get(i).getY() == dragons.get(j).getY()
						&& i != j) {
					if (dragons.get(i).getBreedable() == true && dragons.get(j).getBreedable() == true
							|| dragons.get(i).getMating() == true && dragons.get(j).getMating() == true) {
						if (dragons.get(i).getSterile() == false && dragons.get(j).getSterile() == false) {
							if ((dragons.get(i).getDragonType() == DragonTypeEnum.FEMALE_DRAGON
									&& dragons.get(j).getDragonType() == DragonTypeEnum.MALE_DRAGON)) {

								mating(i, j);
							}
						}
					}
				}
			}
		}
	}

	private void mating(int i, int j) {
		dragons.get(i).setMating(true);
		dragons.get(j).setMating(true);
		dragons.get(i).setBreedable(false);
		dragons.get(j).setBreedable(false);
		if (dragons.get(i).getMating() == true || dragons.get(j).getMating() == true) {
			dragons.get(i).setMatingTime(dragons.get(i).getMatingTime() + 1);
			dragons.get(j).setMatingTime(dragons.get(j).getMatingTime() + 1);
			if (dragons.get(i).getMatingTime() == 5 || dragons.get(j).getMatingTime() == 5) {
				dragons.get(i).setMating(false);
				dragons.get(j).setMating(false);
				dragons.get(j).setMatingTime(0);
				dragons.get(i).setMatingTime(0);

				dragons.get(i).setRecentlyPregnant(1);
				dragons.get(j).setRecentlyPregnant(1);
				dragons.get(i).setBabiesToSpawn(2);

			}
		}
	}

	private void giveBirth() {
		Random rand = new Random();
		int randx = rand.nextInt(1000);
		for (Dragon dragon : dragons) {
			if (dragon.getBabiesToSpawn() > 0) {
				int x = (int) dragon.getX();
				int y = (int) dragon.getY();
				if (randx < 500) {
					dragons.add(new Dragon(3, x, y,
							DragonTypeEnum.BABY_DRAGON_M));
				} else {
					dragons.add(new Dragon(3, x, y,
							DragonTypeEnum.BABY_DRAGON_F));
				}
				dragon.setBabiesToSpawn(dragon.getBabiesToSpawn() - 1);
			}
		}
	}

	@FXML
	void weaponDrag(MouseEvent event) {
		ImageView ivSelected = (ImageView) event.getSource();
		setSelectedImage(ivSelected);
		// Mark the drag as started.
		// We do not use the transfer mode (this can be used to indicate different forms
		// of drags operations, for example, moving files or copying files).
		Dragboard db = ivSelected.startDragAndDrop(TransferMode.ANY);

		// We have to put some content in the clipboard of the drag event.
		// We do not use this, but we could use it to store extra data if we wished.
		ClipboardContent content = new ClipboardContent();

		SnapshotParameters sp = new SnapshotParameters();
		sp.setFill(Color.TRANSPARENT);
		ivSelected.setFitWidth(50);
		content.putImage(ivSelected.snapshot(sp, null));
		db.setContent(content);
		// Consume the event. This means we mark it as dealt with.
		event.consume();
	}

	@FXML
	void canvasDragOver(DragEvent event) {
		// Mark the drag as acceptable if the source was the draggable image.
		// (for example, we don't want to allow the user to drag things or files into
		// our application)
		if (event.getGestureSource() == getSelectedImage(selectedImg)) {
			// Mark the drag event as acceptable by the canvas.
			event.acceptTransferModes(TransferMode.ANY);
			// Consume the event. This means we mark it as dealt with.
			event.consume();
		}
	}

	@FXML
	void canvasDragDropped(DragEvent event) {
		// We call this method which is where the bulk of the behaviour takes place.
		canvasDragDroppedOccured(event);
		// Consume the event. This means we mark it as dealt with.
		event.consume();
	}

	/**
	 * React when an object is dragged onto the canvas.
	 * 
	 * @param event The drag event itself which contains data about the drag that
	 *              occurred.
	 */
	public void canvasDragDroppedOccured(DragEvent event) {
		double x = event.getX();
		double y = event.getY();
		int gridX = (int) (x / GRID_CELL_WIDTH);
		int gridY = (int) (y / GRID_CELL_HEIGHT);
		int xx = GRID_CELL_HEIGHT * gridX;
		int yy = GRID_CELL_HEIGHT * gridY;
		String printImg = getSelectedImage(selectedImg).getImage().getUrl();

		int weaponQty = 0;
		String weaponName = "";
		// restrict a weapon cannot be placed on grid if its quantity is 0
		for (WeaponTypeEnum weaponType : WeaponTypeEnum.values()) {

			// LIGHTNING doesn't have to count quantity
			if (weaponType == WeaponTypeEnum.LIGHTNING) {
				continue;
			}
			String imgPath = weaponType.getImagePath();
			weaponQty = inventory.getWeapons().get(weaponType.name());

			if (printImg.contains(imgPath) && weaponQty <= 0) {
				return;
			}
		}

		// Draw an icon at the dropped location.

		TileTypeEnum tileType = tileGrid1[gridX][gridY].getTileType();
		if (tileType == TileTypeEnum.PATH) {

			if (printImg.contains("Bad_potion.png")) {
				weapons.add(new Weapon(6, gridX, gridY, WeaponTypeEnum.BAD_POTION));
				gcWeapons.drawImage(new Image(WeaponTypeEnum.BAD_POTION.getImagePath()), xx, yy, 50, 50);
				weaponName = WeaponTypeEnum.BAD_POTION.name();

				if (gridX > 1) {
					weapons.add(new Weapon(6, gridX - 1, gridY, WeaponTypeEnum.BAD_POTION));
					if (gridY > 1) {
						weapons.add(new Weapon(6, gridX - 1, gridY - 1, WeaponTypeEnum.BAD_POTION));
					}
					if (gridY < GRID_HEIGHT) {
						weapons.add(new Weapon(6, gridX - 1, gridY + 1, WeaponTypeEnum.BAD_POTION));
					}
				}
				if (gridY < GRID_HEIGHT) {
					weapons.add(new Weapon(6, gridX, gridY + 1, WeaponTypeEnum.BAD_POTION));
				}
				if (gridY > 1) {
					weapons.add(new Weapon(6, gridX, gridY - 1, WeaponTypeEnum.BAD_POTION));
				}
				if (gridX < GRID_WIDTH) {
					weapons.add(new Weapon(6, gridX - 1, gridY, WeaponTypeEnum.BAD_POTION));
					if (gridY > 1) {
						weapons.add(new Weapon(6, gridX - 1, gridY - 1, WeaponTypeEnum.BAD_POTION));
					}
					if (gridY < GRID_HEIGHT) {
						weapons.add(new Weapon(6, gridX - 1, gridY + 1, WeaponTypeEnum.BAD_POTION));
					}
				}

			} else if (printImg.contains("COA_Sword.png")) {
				weapons.add(new Weapon(1, gridX, gridY, WeaponTypeEnum.COA));
				gcWeapons.drawImage(new Image(WeaponTypeEnum.COA.getImagePath()), xx, yy, 50, 50);
				weaponName = WeaponTypeEnum.COA.name();

			} else if (printImg.contains("Wizard.png")) {
				// check all paths in all direction
				weapons.add(new Weapon(10, gridX, gridY, WeaponTypeEnum.WIZARD));
				gcExplosion.drawImage(new Image(WeaponTypeEnum.WIZARD.getImagePath()), xx, yy, 50, 50);
				weaponName = WeaponTypeEnum.WIZARD.name();

			} else if (printImg.contains("Male_SpellBook.png")) {
				weapons.add(new Weapon(1, gridX, gridY, WeaponTypeEnum.MALE_SPELL));
				gcWeapons.drawImage(new Image(WeaponTypeEnum.MALE_SPELL.getImagePath()), xx, yy, 50, 50);
				weaponName = WeaponTypeEnum.MALE_SPELL.name();

			} else if (printImg.contains("Female_SpellBook.png")) {
				weapons.add(new Weapon(1, gridX, gridY, WeaponTypeEnum.FEMALE_SPELL));
				gcWeapons.drawImage(new Image(WeaponTypeEnum.FEMALE_SPELL.getImagePath()), xx, yy, 50, 50);
				weaponName = WeaponTypeEnum.FEMALE_SPELL.name();

			} else if (printImg.contains("Castle_5.png")) {
				weapons.add(new Weapon(5, gridX, gridY, WeaponTypeEnum.CASTLE));
				gcWeapons.drawImage(new Image(WeaponTypeEnum.CASTLE.getImagePath()), xx, yy, 50, 50);
				weaponName = WeaponTypeEnum.CASTLE.name();

			} else if (printImg.contains("Fire_and_Smoke.png")) {
				weapons.add(new Weapon(10, gridX, gridY, WeaponTypeEnum.FIRE_SMOKE));
				gcWeapons.drawImage(new Image(WeaponTypeEnum.FIRE_SMOKE.getImagePath()), xx, yy, 50, 50);
				weaponName = WeaponTypeEnum.FIRE_SMOKE.name();

			} else if (printImg.contains("Grey_Dragon.png")) {
				weapons.add(new Weapon(5, gridX, gridY, WeaponTypeEnum.DEATH_DRAGON));
				gcWeapons.drawImage(new Image(WeaponTypeEnum.DEATH_DRAGON.getImagePath()), xx, yy, 50, 50);
				weaponName = WeaponTypeEnum.DEATH_DRAGON.name();

			}
			weaponQty = inventory.getWeapons().get(weaponName);
			weaponQty -= 1;
			inventory.getWeapons().replace(weaponName, weaponQty);

			if (weaponName.equals("COA")) {
				txtCOA.setText(String.valueOf(weaponQty));
			} else if (weaponName.equals("CASTLE")) {
				txtCastle.setText(String.valueOf(weaponQty));
			} else if (weaponName.equals("WIZARD")) {
				txtBomb.setText(String.valueOf(weaponQty));
			} else if (weaponName.equals("MALE_SPELL")) {
				txtMaleSpell.setText(String.valueOf(weaponQty));
			} else if (weaponName.equals("FEMALE_SPELL")) {
				txtFemaleSpell.setText(String.valueOf(weaponQty));
			} else if (weaponName.equals("BAD_POTION")) {
				txtPotion.setText(String.valueOf(weaponQty));
			} else if (weaponName.equals("FIRE_SMOKE")) {
				txtGas.setText(String.valueOf(weaponQty));
			} else {
				txtDeathDragon.setText(String.valueOf(weaponQty));
			}
		}
	}

	private void updateOnFinish() throws IOException {
		URL filechecker = getClass().getResource("/playerdata/" + txtUsername.getText() + ".txt");

		if (filechecker != null) {
			BufferedReader in = new BufferedReader(new InputStreamReader(filechecker.openStream()));
			String inputLine;
			int lvlNum = 1;
			while ((inputLine = in.readLine()) != null) {
				if (Integer.valueOf(inputLine) > 0 && Integer.valueOf(inputLine) < 5) {
					lvlNum = Integer.valueOf(inputLine);
				}
			}

			if (levelName.equalsIgnoreCase("one") && lvlNum == 1) {
				lvlNum = 2;
			} else if (levelName.equalsIgnoreCase("two") && lvlNum == 2) {
				lvlNum = 3;
			} else if (levelName.equalsIgnoreCase("three") && lvlNum == 3) {
				lvlNum = 4;
			} else if (levelName.equalsIgnoreCase("four") && lvlNum == 4) {
				lvlNum = 5;
			}

			PlayerProfile p = new PlayerProfile(txtUsername.getText(), lvlNum);
			PlayerFileWriter writer = new PlayerFileWriter();
			writer.updatePlayer(p);
			in.close();
		}
	}

	private void showResult(String result, Stage stage2, String username) throws IOException {
		FXMLLoader loader;

		Button create = new Button("CONTINUE");
		create.setStyle("-fx-text-base-color: #ffffff; -fx-font-size: 16px; " +
				"-fx-background-color: #1f618d; -fx-font-weight: bold");
		create.setLayoutX(204);
		create.setLayoutY(349);

		if (result.equals("complete")) { // level complete
			new AudioPlayer("welldone");
			stopTickTimelineButton.setDisable(true);
			loader = new FXMLLoader(getClass().getResource("/fxml/LevelCompleteGUI.fxml"));
		} else { // level failed
			new AudioPlayer("missionfailed");
			stopTickTimelineButton.setDisable(true);

			loader = new FXMLLoader(getClass().getResource("/fxml/LevelFailedGUI.fxml"));
		}
		loader.setController(this);
		Pane pane = loader.load();
		Scene scene = new Scene(pane);
		scene.setFill(Color.TRANSPARENT);

		Stage stage = new Stage();
		pane.getChildren().add(create);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.initModality(Modality.APPLICATION_MODAL);
		txtCompletedLv.setText("LEVEL " + levelName.toUpperCase());
		txtFinalScore.setText("YOUR SCORE: " + Integer.toString(score));
		stage.show();

		create.setOnAction(e -> {
			stage2.close();
			stage.close();
			if (result.equals("welldone")) {
				try {
					updateOnFinish();
					writeToLeaderboard(txtUsername, score, levelName);

				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			try {
				new LevelLoadController(username);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

	}

	private void writeToLeaderboard(Text txtUsername, int score, String levelName) throws IOException {
		File leaderboardFile = new File(".\\resources\\highscores\\Level " + levelName + ".json");
		File directory = new File(".\\resources\\highscores\\");
		if (!directory.exists()) {
			directory.mkdir();
		}
		if (leaderboardFile.exists()) {
			handleJsonWriting(leaderboardFile);
		} else {
			leaderboardFile.createNewFile();
			handleJsonWriting(leaderboardFile);
		}

	}

	private void handleJsonWriting(File leaderboardFile) throws IOException {
		FileWriter writer = new FileWriter(leaderboardFile, true);
		JSONObject json = new JSONObject()
				.put(txtUsername.getText(), score);
		writer.append(json.toString() + "\n");
		writer.close();
	}
}
