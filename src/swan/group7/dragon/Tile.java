package swan.group7.dragon;

/**
 * Tile.java: This class represents a Tile on the board. Each tile will be of
 * type Grass, Path or Tunnel (defined by TileTypeEnum). It can either be walked
 * on by a dragon or not, and can either have weapons placed on it or not.
 * 
 * @author Ryan Barlow, Lukas Kundelis, Corben Thompson (javaDoc Mollie Stamp)
 * @version 1.13
 */
public class Tile {

	private TileTypeEnum tileType;
	private boolean isWalkable; // dragons can walk on the tile if true
	private boolean isPlaceable; // weapons can be placed at the tile if true

	/**
	 * Constructor used to represent a tile using three values.
	 * 
	 * @param tileType    This represents one of the 3 available tiles (Grass, Path,
	 *                    Tunnel).
	 * @param isWalkable  A boolean representing if entities are allowed to
	 *                    'walk'/move on.
	 * @param isPlaceable A boolean representing if objects can be placed on top.
	 */
	public Tile(TileTypeEnum tileType, boolean isWalkable, boolean isPlaceable) {
		this.tileType = tileType;
		this.isWalkable = isWalkable;
		this.isPlaceable = isPlaceable;
	}

	/**
	 * This returns boolean if an item can be placed.
	 * 
	 * @return isPlaceable Boolean if an item can be placed on top.
	 */
	public boolean isPlaceable() {
		return isPlaceable;
	}

	/**
	 * This returns boolean if entities can move on top.
	 * 
	 * @return isWalkable Boolean if an entity can walk on top.
	 */
	public boolean isWalkable() {
		return isWalkable;
	}

	/**
	 * This method sets the value of isWalkabale.
	 * 
	 * @param walkable Boolean if a tile can be walked on.
	 */
	public void setWalkable(boolean walkable) {
		isWalkable = walkable;
	}

	/**
	 * This returns the type of tile.
	 * 
	 * @return tileType Type of tile being queried.
	 */
	public TileTypeEnum getTileType() {
		return tileType;
	}
}
