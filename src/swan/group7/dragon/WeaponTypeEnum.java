package swan.group7.dragon;

/** WeaponTypeEnum.java
 * This class is used to represent the different Weapon type enums. Associates images
 * for each with the correct type.
 * 
 * @author Ching Man Wong (JavaDoc Mollie)
 * @version 1.9
 */

/**
 * Specifies weapon types that can exist
 *
 */
public enum WeaponTypeEnum {
	/**
	 * {@link #COA}
	 */
	COA("/artwork/COA_Sword.png"),
	/**
	 * {@link #CASTLE}
	 */
	CASTLE("/artwork/Castle_5.png"),
	/**
	 * {@link #WIZARD}
	 */
	WIZARD("/artwork/Wizard.png"),
	/**
	 * {@link #MALE_SPELL}
	 */
	MALE_SPELL("/artwork/Male_SpellBook.png"),
	/**
	 * {@link #FEMALE_SPELL}
	 */
	FEMALE_SPELL("/artwork/Female_SpellBook.png"),
	/**
	 * {@link #BAD_POTION}
	 */
	BAD_POTION("/artwork/Bad_Potion.png"),
	/**
	 * {@link #FIRE_SMOKE}
	 */
	FIRE_SMOKE("/artwork/Fire_and_Smoke.png"),
	/**
	 * {@link #DEATH_DRAGON}
	 */
	DEATH_DRAGON("/artwork/Grey_Dragon.png"),
	/**
	 * {@link #LIGHTNING}
	 */
	LIGHTNING("/artwork/Lightning_Bolt.png");

	private final String imagePath;

	/**
	 * Sets up imagePath using string given.
	 * 
	 * @param imagePath String of image location.
	 */
	WeaponTypeEnum(String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * This method retrieves imagePath.
	 * 
	 * @return imagePath String of image location.
	 */
	public String getImagePath() {
		return imagePath;
	}
}
