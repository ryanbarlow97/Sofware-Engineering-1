package swan.group7.dragon;

/**
 * TileTypeEnum.java: To represent the three types of Tile. Associates images
 * for each with the correct type.
 * 
 * @author Ryan Barlow, Corben Thompson, Lukas Kundelis, Lewis North (JavaDoc
 *         Mollie Stamp)
 * @version 1.6
 */

public enum TileTypeEnum {
	/**
	 * Tile types that can exist {@link #GRASS} {@link #PATH} {@link #TUNNEL_STONE}
	 */
	GRASS("/artwork/Just_Grass.png"), PATH("/artwork/Dirt.png"), TUNNEL_STONE("/artwork/Tunnel_Stone.png");

	private final String imagePath;

	/**
	 * Sets up the image path for the tiles.
	 * 
	 * @param imagePath String of image location.
	 */
	TileTypeEnum(String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * Retrieves the path of the image.
	 * 
	 * @return imagePath String of image location.
	 */
	public String getImagePath() {
		return imagePath;
	}
}
