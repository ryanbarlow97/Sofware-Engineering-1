package swan.group7.dragon;

import java.io.FileWriter;
import java.io.IOException;

/**
 * PlayerFileWriter.java To create files that store player infomation when the
 * game isn't being played.
 * 
 * @author Corben Thompson (JavaDoc Mollie Stamp)
 * @version 1.2
 */
public class PlayerFileWriter {

	/**
	 * Takes profiles and creates text files that store player data, adds relevant
	 * information to it.
	 * 
	 * @param p The playerProfile
	 */
	public static void writeFile(PlayerProfile p) {
		String fileName = "../Group7/resources/playerdata/" + p.getName() + ".txt";
		try {
			FileWriter newWriter = new FileWriter(fileName);
			newWriter.write(p.getMaxlvl() + "");
			newWriter.close();
		} catch (IOException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}

	/**
	 * This method updates the playerProfile.
	 * 
	 * @param p PlayerProfile the profile that needs to be updated.
	 */
	public void updatePlayer(PlayerProfile p) {
		String fileName = "../Group7/resources/playerdata/" + p.getName() + ".txt";
		try {
			FileWriter newWriter = new FileWriter(fileName);
			newWriter.write(p.getMaxlvl() + "");
			newWriter.close();
		} catch (IOException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}
}
