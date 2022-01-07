package swan.group7.dragon;

/**
 * InvalidLevelFileExcpetion.java
 * 
 *
 * @author ____ (javadoc Mollie Stamp)
 *
 */
public class InvalidLevelFileException extends Exception {
	/**
	 * This method is run when the level file is invalid.
	 * 
	 * @param errorMessage String which outputs the specific error.
	 */
	public InvalidLevelFileException(String errorMessage) {
		super(errorMessage);
	}
}