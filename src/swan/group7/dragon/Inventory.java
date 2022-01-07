package swan.group7.dragon;

import java.util.HashMap;

/**
 * Inventory.java This class represents the inventory and all weapons currently
 * in the inventory and able to be used by the player.
 * 
 * @author ____ (JavaDoc Mollie Stamp)
 * @version 1.6
 *
 */
public class Inventory {

	private HashMap<String, Integer> weapons;

	/**
	 * Constructor used to create new hashmap to store weapons.
	 */
	public Inventory() {
		weapons = new HashMap<>();
	}

	/**
	 * This is used to retrieve all weapons currently able to be played by user.
	 * 
	 * @return weapons Hashmap of all current weapons.
	 */
	public HashMap<String, Integer> getWeapons() {
		return weapons;
	}

	/**
	 * This method assigns the weapons.
	 * 
	 * @param weapons Hashmap of all current weapons.
	 */
	public void setWeapons(HashMap<String, Integer> weapons) {
		this.weapons = weapons;
	}
}
