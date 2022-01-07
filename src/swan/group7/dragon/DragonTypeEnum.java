package swan.group7.dragon;

/**
 * DragonTypeEnum.java: Represents four types of Dragons; female, male, female
 * baby and male baby. Associates images for each with the correct type.
 * 
 * @author ____, ____, ____ (JavaDoc Mollie Stamp)
 * @version 1.8
 *
 */
public enum DragonTypeEnum {
	/**
	 * Dragon types that can exist {@link #MALE_DRAGON}
	 */
	MALE_DRAGON("/artwork/Male_Dragon.png"),
	/**
	 * {@link #FEMALE_DRAGON}
	 */
	FEMALE_DRAGON("/artwork/Female_Dragon.png"),
	/**
	 * {@link #BABY_DRAGON_M}
	 */
	BABY_DRAGON_M("/artwork/Baby_Dragon.png"),
	/**
	 * {@link #BABY_DRAGON_F}
	 */
	BABY_DRAGON_F("/artwork/Baby_Dragon.png");

	private final String imagePath;

	/**
	 * This method sets up the image path for the dragons.
	 * 
	 * @param imagePath String of image location.
	 */
	DragonTypeEnum(String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * This method retrieves the path of the image
	 * 
	 * @return imagePath String of image location.
	 */
	public String getImagePath() {
		return imagePath;
	}
}
