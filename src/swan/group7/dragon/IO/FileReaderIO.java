package swan.group7.dragon.IO;

import swan.group7.dragon.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import static swan.group7.dragon.controllers.CanvasController.GRID_HEIGHT;
import static swan.group7.dragon.controllers.CanvasController.GRID_WIDTH;

/**
 * IO for various file types.
 * 
 * @author Lewis North, Mollie Stamp, Ching Man Wong (JavaDoc Mollie Stamp)
 * @version 1.8
 */

public class FileReaderIO {

	private static BufferedReader bReader;

	/**
	 * takes a file and type and outputs its contents as a output stream in correct
	 * order
	 * 
	 * @param fileName String to be read in.
	 * @param type     of file to be read
	 * @throws IOException
	 */
	public FileReaderIO(String fileName, String type) throws IOException {
		try {
			if (type.equals("Map")) {
				InputStream inputStream = getClass().getResourceAsStream("/maps/" + fileName + ".txt");
				output(inputStream);
			} else if (type.equals("Profile")) {
				InputStream inputStream = getClass().getResourceAsStream("/profile/" + fileName + ".txt");
				output(inputStream);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public FileReaderIO() {
	}

	/**
	 * This method reads a player profile and creates a playerProfile object with
	 * the data.
	 * 
	 * @param fileName The fileName to be read.
	 * @return PlayerProfile loaded with the data from the playerFile.
	 * @throws FileNotFoundException if file doesn't exist.
	 */
	public PlayerProfile readPlayerProfile(String fileName) throws FileNotFoundException {
		try {
			File file = new File("/profiles/" + fileName + ".txt");
			Scanner myScanner = new Scanner(file);
			String name = myScanner.nextLine();
			int maxLvl = myScanner.nextInt();
			myScanner.next(); // to clear newline character
			ArrayList<Integer> highScores = new ArrayList<Integer>();
			while (myScanner.hasNextInt()) {
				highScores.add(myScanner.nextInt());
			}
			PlayerProfile profile = new PlayerProfile(name, maxLvl);
			myScanner.close();
			return profile;
		} catch (FileNotFoundException e) {
			throw e;
		}
	}

	/**
	 * This method reads a levelFile and returns a level object loaded with the data
	 * from the file.
	 * 
	 * @param path String name of path of file to be read.
	 * @return level Level with all the data loaded.
	 * @throws FileNotFoundException     Exception thrown if the file isn't found.
	 * @throws InvalidLevelFileException Exception thrown if the file given doesn't
	 *                                   comply to the levelFileFormat.
	 */
	

	/**
	 * This method takes the data and outputs it as a stream.
	 * 
	 * @param inputStream InputStream data that has been read.
	 * @throws IOException Exception thrown to satisfry bReader.
	 */
	public static void output(InputStream inputStream) throws IOException {

		bReader = new BufferedReader(new InputStreamReader(inputStream));

		String line = bReader.readLine();

		while (line != null) {
			System.out.println(line);
			line = bReader.readLine();
		}
		bReader.close();
	}

	/**
	 * This method reads the level layout part of a levelFile.
	 * 
	 * @param filePath String path name to be read.
	 * @return tileGrid Array of tiles with the same layout as the tiles in the
	 *         levelFile.
	 * @throws IOException Exception thrown to satisfry bReader.
	 */
	public Tile[][] getTileGrid(String filePath) throws IOException {
		InputStream inputStream = getClass().getResourceAsStream(filePath);
		bReader = new BufferedReader(new InputStreamReader(inputStream));
		bReader.readLine(); // skip the first line
		String line = bReader.readLine();

		int y = 0; // index number of row
		Tile[][] tileGrid = new Tile[GRID_WIDTH][GRID_HEIGHT];
		TileTypeEnum tileType = null;
		boolean isWalkable = true;
		boolean isPlaceable = true;

		while (line != null) {
			for (int x = 0; x < line.length(); x++) {
				char character = line.charAt(x); // read every single character
				if (character == 'G') {
					tileType = TileTypeEnum.GRASS;
					isWalkable = false;
					isPlaceable = false;
				} else if (character == 'P') {
					tileType = TileTypeEnum.PATH;
					isWalkable = true;
					isPlaceable = true;
				} else if (character == 'S') {
					tileType = TileTypeEnum.TUNNEL_STONE;
					isWalkable = true;
					isPlaceable = false;
				} else {
					break;
				}

				tileGrid[x][y] = new Tile(tileType, isWalkable, isPlaceable);
			}
			y++;
			line = bReader.readLine();
		}
		bReader.close();
		return tileGrid;
	}
}
