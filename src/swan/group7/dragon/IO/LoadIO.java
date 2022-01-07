package swan.group7.dragon.IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CopyOnWriteArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.scene.text.Text;
import swan.group7.dragon.Dragon;
import swan.group7.dragon.DragonTypeEnum;
import swan.group7.dragon.Weapon;
import swan.group7.dragon.WeaponTypeEnum;

/**
 * Loads game data from level and save files.
 * 
 * @author Ryan Barlow & Lukas Kundelis (908032 & 2014672)
 *
 */

public class LoadIO {
    public LoadIO() {
    }

    
    /** 
     * Loads a save file from the saves folder.
     * 
     * @param levelName Name of the level to be loaded, also the name of the level file.
     * @param txtUsername Username of the active player.
     * @return Path The relative path of the save file.
     * @throws FileNotFoundException Exception thrown if the save file isn't found.
     */
    private Path getSaveFile(String levelName, Text txtUsername) throws FileNotFoundException {
        return (new File(".\\saves\\" + txtUsername.getText() + "-" + levelName + ".json")).toPath();
    }

    
    /** 
     * Checks if a save file exists in the saves folder.
     * 
     * @param levelName Name of the level to be checked, also the name of the level file.
     * @param txtUsername Username of the active player.
     * @return boolean Returns true if the requested save file is found.
     */
    public boolean doesSaveExist(String levelName, Text txtUsername) {
        return (new File(".\\saves\\" + txtUsername.getText() + "-" + levelName + ".json")).exists();
    }

    
    /** 
     * Loads dragons from a save file.
     * 
     * @param levelName Name of the level to load dragons from.
     * @param txtUsername Username of the active player.
     * @return CopyOnWriteArrayList<Dragon> List of dragons
     * @throws JSONException Exception thrown if the JSON data is invalid.
     * @throws IOException Exception thrown if there are issues with opening the file.
     */
    public CopyOnWriteArrayList<Dragon> loadOldDragons(String levelName, Text txtUsername)
            throws JSONException, IOException {
        return loadDragons(getSaveFile(levelName, txtUsername));

    }

    
    /** 
     * Loads weapons from a save file.
     * 
     * @param levelName Name of the level to load weapons from.
     * @param txtUsername Username of the active player.
     * @return CopyOnWriteArrayList<Weapon> List of weapons.
     * @throws JSONException Exception thrown if the JSON data is invalid.
     * @throws IOException Exception thrown if there are issues with opening the file.
     */
    public CopyOnWriteArrayList<Weapon> loadOldWeapons(String levelName, Text txtUsername)
            throws JSONException, IOException {
        return loadOldWeapons(getSaveFile(levelName, txtUsername));
    }

    
    /** 
     * Loads score from a save file.
     * 
     * @param levelName Name of the level to load score from.
     * @param txtUsername Username of the active player.
     * @return int Value of score
     * @throws JSONException Exception thrown if the JSON data is invalid.
     * @throws IOException Exception hrown if there are issues with opening the file.
     */
    public int loadOldScore(String levelName, Text txtUsername) throws JSONException, IOException {
        return (new JSONObject(Files.readString(getSaveFile(levelName, txtUsername)))).getInt("score");
    }

    
    /** 
     * Loads time from a save file.
     * 
     * @param levelName Name of the level to load time from.
     * @param txtUsername Username of the active player.
     * @return String Elapsed time loaded from file.
     * @throws JSONException Exception thrown if the JSON data is invalid.
     * @throws IOException Exception thrown if there are issues with opening the file.
     */
    public String loadOldTime(String levelName, Text txtUsername) throws JSONException, IOException {
        return (new JSONObject(Files.readString(getSaveFile(levelName, txtUsername)))).getString("time");
    }

    
    /** 
     * Loads dragons from a save file.
     * 
     * @param levelName Name of the level to load from.
     * @return CopyOnWriteArrayList<Dragon> List of dragons.
     * @throws JSONException Exception thrown if the JSON data is invalid.
     * @throws IOException Exception thrown if there are issues with opening the file.
     */
    public CopyOnWriteArrayList<Dragon> loadNewGame(String levelName) throws JSONException, IOException {
        Path saveFile = (new File(".\\resources\\entityspawner\\" + levelName + ".json")).toPath();
        return loadDragons(saveFile);
    }

    
    /** 
     * Loads dragons from a level file.
     * 
     * @param saveFile Name of the level to load dragons from.
     * @return CopyOnWriteArrayList<Dragon> List of dragons loaded from file.
     * @throws JSONException Exception thrown if the JSON data is invalid.
     * @throws IOException Exception thrown if there are issues with opening the file.
     */
    private CopyOnWriteArrayList<Dragon> loadDragons(Path saveFile) throws JSONException, IOException {
        JSONObject jsonObject = new JSONObject(Files.readString(saveFile));
        JSONArray jsonArray = jsonObject.getJSONArray("dragons");
        CopyOnWriteArrayList<Dragon> dragons = new CopyOnWriteArrayList<Dragon>();
        for (Object jsonObj : jsonArray) {
            JSONObject json = (JSONObject) jsonObj;
            DragonTypeEnum dragonType = null;

            switch (json.getString("dragonType")) {
                case "BABY_DRAGON_F":
                    dragonType = DragonTypeEnum.BABY_DRAGON_F;
                    break;
                case "BABY_DRAGON_M":
                    dragonType = DragonTypeEnum.BABY_DRAGON_M;
                    break;
                case "MALE_DRAGON":
                    dragonType = DragonTypeEnum.MALE_DRAGON;
                    break;
                case "FEMALE_DRAGON":
                    dragonType = DragonTypeEnum.FEMALE_DRAGON;
                    break;
            }

            Dragon dragon = new Dragon(json.getInt("HP"), json.getInt("X"), json.getInt("Y"),
                    dragonType);

            dragon.setMating(json.getBoolean("isMating"));
            dragon.setRecentlyPregnant(json.getInt("recentlyPregnant"));
            dragon.setBreedable(json.getBoolean("isBreedable"));
            dragon.setGrowth(json.getInt("growthProgress"));
            dragon.setBabiesToSpawn(json.getInt("babiesToSpawn"));
            dragon.setMatingTime(json.getInt("matingTime"));
            dragon.setPregnant(json.getBoolean("isPregnant"));
            dragon.setSterile(json.getBoolean("isSterile"));
            dragons.add(dragon);
        }
        return dragons;

    }

    
    /** 
     * Loads weapons from a level file.
     * 
     * @param saveFile Name of the level to load weapons from.
     * @return CopyOnWriteArrayList<Weapon> List of weapons loaded from file.
     * @throws JSONException Exception thrown if the JSON data is invalid.
     * @throws IOException Exception thrown if there are issues with opening the file.
     */
    private CopyOnWriteArrayList<Weapon> loadOldWeapons(Path saveFile) throws JSONException, IOException {
        JSONObject jsonObject = new JSONObject(Files.readString(saveFile));
        JSONArray jsonArray = jsonObject.getJSONArray("weapons");
        CopyOnWriteArrayList<Weapon> weapons = new CopyOnWriteArrayList<Weapon>();
        for (Object jsonObj : jsonArray) {
            JSONObject json = (JSONObject) jsonObj;
            WeaponTypeEnum weaponType = null;
            switch (json.getString("weaponType")) {
                case "COA":
                    weaponType = WeaponTypeEnum.COA;
                    break;
                case "CASTLE":
                    weaponType = WeaponTypeEnum.CASTLE;
                    break;
                case "WIZARD":
                    weaponType = WeaponTypeEnum.WIZARD;
                    break;
                case "MALE_SPELL":
                    weaponType = WeaponTypeEnum.MALE_SPELL;
                    break;
                case "FEMALE_SPELL":
                    weaponType = WeaponTypeEnum.FEMALE_SPELL;
                    break;
                case "BAD_POTION":
                    weaponType = WeaponTypeEnum.BAD_POTION;
                    break;
                case "FIRE_SMOKE":
                    weaponType = WeaponTypeEnum.FIRE_SMOKE;
                    break;
                case "DEATH_DRAGON":
                    weaponType = WeaponTypeEnum.DEATH_DRAGON;
                    break;
                case "LIGHTNING":
                    weaponType = WeaponTypeEnum.LIGHTNING;
                    break;
            }
            Weapon weapon = new Weapon(json.getInt("HP"), json.getInt("X"), json.getInt("Y"),
                    weaponType);
            weapons.add(weapon);
        }
        return weapons;

    }

}
