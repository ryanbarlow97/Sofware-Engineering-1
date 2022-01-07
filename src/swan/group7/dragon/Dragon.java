package swan.group7.dragon;

/**
 * Dragon.java This class is used to represent a dragon entity within the game.
 * Extends Entity to take characteristics and adds more that are specific only
 * to dragon.
 * 
 * @author Ryan Barlow, Lukas Kundelis (JavaDoc Mollie Stamp)
 * @version 1.8
 */

public class Dragon extends Entity {

	protected DragonTypeEnum dragonType;
	private boolean isBreedable;
	private int growthProgress = 0;
	private boolean isMating;
	private int matingTime = 0;
	private int recentlyPregnant = 0;
	private boolean isSterile;
	private boolean isPregnant;
	private int babiesToSpawn = 0;

	/**
	 * This constructor is used to create an dragon object with the specified heath,
	 * position and type.
	 * 
	 * @param HP         The health of the dragon.
	 * @param x          The location of x-coordinate on map.
	 * @param y          The location of y-coordinate on map.
	 * @param dragonType The type of dragon (Male, Female, Baby ..).
	 */
	public Dragon(int HP, int x, int y, DragonTypeEnum dragonType) {
		super(HP, x, y);
		this.dragonType = dragonType;
	}

	/**
	 * This method is used to retrieve type of dragon.
	 * 
	 * @return dragonType The type of dragon (Male, Female, Baby ..).
	 */
	public DragonTypeEnum getDragonType() {
		return dragonType;
	}

	/**
	 * This method assigns or change current dragons type.
	 * 
	 * @param dragonType The type of dragon (Male, Female, Baby ..).
	 */
	public void setDragonType(DragonTypeEnum dragonType) {
		this.dragonType = dragonType;
	}

	/**
	 * This method sets the number of babies left for the mother to birth.
	 * 
	 * @param babiesToSpawn The number of babies that are left to spawn.
	 */
	public void setBabiesToSpawn(int babiesToSpawn) {
		this.babiesToSpawn = babiesToSpawn;
	}

	/**
	 * This method retrieves how many babies are left for the mother to birth.
	 * 
	 * @return babiesToSpawn The number of babies that are left to spawn.
	 */
	public int getBabiesToSpawn() {
		return babiesToSpawn;
	}

	/**
	 * When baby has been a baby for set amount of time, this method ages it up to
	 * it's adult equivalent.
	 */
	public void growUp() {
		if (growthProgress == 10) {
			if (dragonType == DragonTypeEnum.BABY_DRAGON_F) {
				setDragonType(DragonTypeEnum.FEMALE_DRAGON);
				setBreedable(true);
			} else {
				setDragonType(DragonTypeEnum.MALE_DRAGON);
				setBreedable(true);
			}

		}
	}

	/**
	 * This method returns a boolean if the dragon is currently pregnant or not.
	 * 
	 * @return isPregnant boolean representing if mother dragon is pregnant.
	 */
	public boolean getPregnant() {
		return isPregnant;

	}

	/**
	 * This method sets whether dragon is pregnant or not.
	 * 
	 * @param isPregnant boolean representing if mother dragon is pregnant.
	 */
	public void setPregnant(boolean isPregnant) {
		this.isPregnant = isPregnant;

	}

	/**
	 * This method is used to find out if the dragon is sterile.
	 * 
	 * @return isSterile Boolean value if dragon is sterile (from use of weapon).
	 */
	public boolean getSterile() {
		return isSterile;
	}

	/**
	 * This method assigns or change dragons sterilisation.
	 * 
	 * @param isSterile value if dragon is sterile (from use of weapon).
	 */
	public void setSterile(boolean isSterile) {
		this.isSterile = isSterile;
	}

	/**
	 * If dragon has been recently pregnant, cannot be pregnant straight away.
	 * Returns value which indicates how recently pregnant dragon was.
	 * 
	 * @return recentlyPregnant int showing time since dragon was pregnant.
	 */
	public int getRecentlyPregnant() {
		return recentlyPregnant;
	}

	/**
	 * Sets value of recently pregnant to indicate how long ago dragon was pregnant.
	 * 
	 * @param recentlyPregnant Int showing time since dragon was pregnant.
	 */
	public void setRecentlyPregnant(int recentlyPregnant) {
		this.recentlyPregnant = recentlyPregnant;
	}

	/**
	 * Returns how long baby dragon has been 'growing' into an adult.
	 * 
	 * @return growthProgress Int representing the time a baby has been a baby.
	 */
	public int getGrowth() {
		return growthProgress;
	}

	/**
	 * Sets the growth speed of baby dragon.
	 * 
	 * @param growth Int representing the time a baby has been a baby.
	 */
	public void setGrowth(int grow) {
		this.growthProgress = grow;
	}

	/**
	 * Returns time dragon has been mating for.
	 * 
	 * @return matingTime Int representing the total time mating.
	 */
	public int getMatingTime() {
		return matingTime;
	}

	/**
	 * Set the time dragons have been mating for,
	 * 
	 * @param matingTime Int representing the total time mating.
	 */
	public void setMatingTime(int matingTime) {
		this.matingTime = matingTime;
	}

	/**
	 * Returns if dragon is able to breed.
	 * 
	 * @return isBreedable Boolean current value if dragon can breed or not.
	 */
	public boolean getBreedable() {
		return isBreedable;
	}

	/**
	 * Assign or change dragons breedable value.
	 * 
	 * @param isBreedable New boolean value if dragon can breed or not.
	 */
	public void setBreedable(boolean isBreedable) {
		this.isBreedable = isBreedable;
	}

	/**
	 * Returns if dragon is currently mating.
	 * 
	 * @return getMating Current boolean value representing dragons status of
	 *         mating.
	 */
	public boolean getMating() {
		return isMating;
	}

	/**
	 * Sets the dragon to currently mating.
	 * 
	 * @param isMating New boolean value representing dragons status of mating.
	 */
	public void setMating(boolean isMating) {
		this.isMating = isMating;
	}

	/**
	 * If SwapGender spell is used, switches gender from M to F and vice versa.
	 */
	public void swapGender() {
		if (dragonType == DragonTypeEnum.MALE_DRAGON) {
			dragonType = DragonTypeEnum.FEMALE_DRAGON;
		} else if (dragonType == DragonTypeEnum.FEMALE_DRAGON) {
			dragonType = DragonTypeEnum.MALE_DRAGON;
		}
	}

}
