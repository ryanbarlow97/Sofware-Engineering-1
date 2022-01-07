package swan.group7.dragon;

/**
 * Weapon.java This class is used to represent a Weapon entity. Each weapon has
 * a type, a location coordinate and an int of current health points.
 * 
 * @author Ching Man Wong (JavaDoc Mollie Stamp)
 * @version 1.9
 */
public class Weapon extends Entity {

	private WeaponTypeEnum weaponType;

	/**
	 * Constructs a weapon using attributes given.
	 * 
	 * @param HP         Health of entity.
	 * @param x          Horizontal location on game map.
	 * @param y          Vertical location on game map.
	 * @param weaponType Type of weapon.
	 */
	public Weapon(int HP, int x, int y, WeaponTypeEnum weaponType) {
		super(HP, x, y);
		this.weaponType = weaponType;
	}

	/**
	 * This retrieves weapon type.
	 * 
	 * @return weaponType Type of weapon.
	 */
	public WeaponTypeEnum getWeaponType() {
		return weaponType;
	}

	/**
	 * This method assigns weapon type.
	 * 
	 * @param weaponType Type of weapon.
	 */
	public void setWeaponType(WeaponTypeEnum weaponType) {
		this.weaponType = weaponType;
	}

	/**
	 * This method checks the tiles around a dragon and returns a string of
	 * directions that the dragon can now move to.
	 * 
	 * @param tilegrid Tile[][] Array of all tiles.
	 * @param x        Horizontal location on game map.
	 * @param y        Vertical location on game map.
	 * @return availableDirections String of all directions dragon can now move.
	 */
	public String getWizardDirections(Tile[][] tilegrid, int x, int y) {
		String availableDirections = "";

		if (tilegrid[x - 1][y].getTileType().equals(TileTypeEnum.PATH)
				|| tilegrid[x - 1][y].getTileType().equals(TileTypeEnum.TUNNEL_STONE)) {
			availableDirections += "LEFT ";
		}
		if (tilegrid[x + 1][y].getTileType().equals(TileTypeEnum.PATH)
				|| tilegrid[x + 1][y].getTileType().equals(TileTypeEnum.TUNNEL_STONE)) {
			availableDirections += "RIGHT ";
		}
		if (tilegrid[x][y + 1].getTileType().equals(TileTypeEnum.PATH)
				|| tilegrid[x][y + 1].getTileType().equals(TileTypeEnum.TUNNEL_STONE)) {
			availableDirections += "DOWN ";
		}
		if (tilegrid[x][y - 1].getTileType().equals(TileTypeEnum.PATH)
				|| tilegrid[x][y - 1].getTileType().equals(TileTypeEnum.TUNNEL_STONE)) {
			availableDirections += "UP ";
		}

		return availableDirections;

	}

	/**
	 * This method enables the wizard to see which tiles needs to be 'blown up' and
	 * any dragons on those tiles are killed.
	 * 
	 * @param tilegrid Tile[][] Array of all tiles.
	 * @param x        Horizontal location on game map.
	 * @param y        Vertical location on game map.
	 * @return lastDirection String of direction dragon previously moved.
	 * @return .. 0 or recursively call method if there are still more directions
	 *         the wizard could explode on.
	 */
	public int wizardExplosion(Tile[][] tilegrid, int x, int y, String lastDirection) {
		String availableDirections = getWizardDirections(tilegrid, x, y);
		if (availableDirections.contains("UP ") && lastDirection.equals("UP ")) {
			return (wizardExplosion(tilegrid, x, y - 1, lastDirection)) + 1;
		} else if (availableDirections.contains("DOWN ") && lastDirection.equals("DOWN ")) {
			return (wizardExplosion(tilegrid, x, y + 1, lastDirection)) + 1;
		} else if (availableDirections.contains("RIGHT ") && lastDirection.equals("RIGHT ")) {
			return (wizardExplosion(tilegrid, x + 1, y, lastDirection)) + 1;
		} else if (availableDirections.contains("LEFT ") && lastDirection.equals("LEFT ")) {
			return (wizardExplosion(tilegrid, x - 1, y, lastDirection)) + 1;
		} else {
			return 0;
		}
	}
}
