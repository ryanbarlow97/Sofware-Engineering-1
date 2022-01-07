package swan.group7.dragon.IO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.scene.text.Text;
import swan.group7.dragon.Dragon;
import swan.group7.dragon.Weapon;

/**
 * SaveIO.java Converts game data to JSON format and saves it to a file.
 * 
 * @author Ryan Barlow, Lukas Kundelis (JavaDoc Mollie Stamp and Lewis North)
 * @version 1.3
 */

public class SaveIO {

	/**
	 * This is an empty constructor.
	 */
	public SaveIO() {
	}

	/**
	 * This method is used to take all relevant details and overwrite existing game
	 * saves or save the game.
	 * 
	 * @param levelName   The name of the level to load from.
	 * @param txtUsername The username of the active player.
	 * @param weapons     A list of weapons to save.
	 * @param dragons     A list of dragons to store.
	 * @param score       The current score.
	 * @param TickSeconds The time elapsed since the start of the level.
	 * @throws IOException Exception thrown if there are issues with loading the
	 *                     file.
	 */
	public void saveGame(String levelName, Text txtUsername, CopyOnWriteArrayList<Weapon> weapons,
			CopyOnWriteArrayList<Dragon> dragons, int score, Text TickSeconds) throws IOException {
		File directory = new File(".\\saves\\");
		if (!directory.exists()) {
			directory.mkdir();
		}
		File saveFile = new File(".\\saves\\" + txtUsername.getText() + "-" + levelName + ".json");
		if (saveFile.exists()) {
			saveFile.delete();
			saveFile.createNewFile();
		} else {
			saveFile.createNewFile();
		}
		FileWriter writer = new FileWriter(saveFile);
		writer.write(getSaveJson(weapons, dragons, score, TickSeconds));
		writer.close();
	}

	/**
	 * This method gets the saved Json files.
	 * 
	 * @param weapons     A list of weapons to save.
	 * @param dragons     A list of dragons to store.
	 * @param score       The current score.
	 * @param TickSeconds THe time elapsed since the start of the level.
	 * @return JsonObject String JSON data to save in string format.
	 */
	private String getSaveJson(CopyOnWriteArrayList<Weapon> weapons, CopyOnWriteArrayList<Dragon> dragons, int score,
			Text TickSeconds) {
		JSONObject saveJson = new JSONObject().put("score", score).put("time", TickSeconds.getText())
				.put("dragons", getJsonDragons(dragons)).put("weapons", getJsonWeapons(weapons));
		return JSONObject.valueToString(saveJson);
	}

	/**
	 * This method puts the information about dragons into a json file.
	 * 
	 * @param dragons The list of dragons to save.
	 * @return jsonArray A JSON object of dragons to save.
	 */
	private JSONArray getJsonDragons(CopyOnWriteArrayList<Dragon> dragons) {
		JSONArray jsonArray = new JSONArray();
		for (Dragon dragon : dragons) {
			jsonArray.put(new JSONObject().put("HP", dragon.getHP()).put("X", dragon.getX()).put("Y", dragon.getY())
					.put("dragonType", dragon.getDragonType()).put("isBreedable", dragon.getBreedable())
					.put("growthProgress", dragon.getGrowth()).put("isMating", dragon.getMating())
					.put("matingTime", dragon.getMatingTime()).put("recentlyPregnant", dragon.getRecentlyPregnant())
					.put("isSterile", dragon.getSterile()).put("isPregnant", dragon.getPregnant())
					.put("babiesToSpawn", dragon.getBabiesToSpawn()));
		}
		return jsonArray;
	}

	/**
	 * This method puts the information about dragons into a json file.
	 * 
	 * @param weapons A list of weapons to save.
	 * @return JSONArray The JSON object of weapons to save.
	 */
	private JSONArray getJsonWeapons(CopyOnWriteArrayList<Weapon> weapons) {
		JSONArray jsonArray = new JSONArray();
		for (Weapon weapon : weapons) {
			jsonArray.put(new JSONObject().put("HP", weapon.getHP()).put("X", weapon.getX()).put("Y", weapon.getY())
					.put("weaponType", weapon.getWeaponType()));
		}
		return jsonArray;
	}

}