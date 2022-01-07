package swan.group7.dragon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * HttpDataManipulation.java Takes in a random string of chars and decodes them.
 * Also used to decode Message of the Day.
 * 
 * @author Ryan Barlow (JavaDoc Mollie Stamp, Ryan Barlow)
 * @version 1.3
 */
public class HttpDataManipulation {

	/**
	 * This method gets the HTML data from a given URL, and creates the connection.
	 * 
	 * @return con Connection to the URL.
	 * @throws Exception Exception thrown when connection to URL cannot be made.
	 */
	public HttpURLConnection getHTMLData() throws Exception {
		URL url = new URL("http://cswebcat.swansea.ac.uk/puzzle");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		return con;
	}

	/**
	 * This method reads the data from the HTMl file and returns cypher.
	 * 
	 * @return length6, decrypted, "CS-230" returns the decrypted cyhper and can be
	 *         called to find the MOTD.
	 * @throws Exception Exception thrown when getHTMLData cannot retrive the URL
	 *                   data.
	 */
	public String readData() throws Exception {
		Integer currentPos = 0;
		Integer maxPos = getHTMLData().getContentLength();
		StringBuilder decrypted = new StringBuilder();
		String length6 = String.valueOf(getHTMLData().getContentLength() + "CS-230".length());
		while (currentPos < maxPos) {
			decrypted.append(dataOutput(currentPos));
			currentPos++;
		}
		return length6 + decrypted + "CS-230";
	}

	/**
	 * This method takes the descrambled letter and appends to a stringbuilder.
	 * 
	 * @param currentPos Current position in the input string of characters.
	 * @return scrambled Returns the scrambled character in question.
	 * @throws Exception Exception thrown when either the HTML data isn't
	 *                   retrievable or it cannot read more lines.
	 */
	public String dataOutput(Integer currentPos) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(getHTMLData().getInputStream()));
		StringBuilder scrambled = new StringBuilder();
		if (currentPos % 2 != 1) {
			scrambled.append(motdChanger(br.readLine(), -(currentPos + 1)).charAt(currentPos));
		} else {
			scrambled.append(motdChanger(br.readLine(), +(currentPos + 1)).charAt(currentPos));
		}
		return scrambled.toString();
	}

	/**
	 * This method does the decryption.
	 * 
	 * @param enc    String reads every character individually from dataOutput.
	 * @param offset int which is set to 26 for number of letters (starts from 0).
	 * @return encoded StringBuilder which appends all of the decoded characters one
	 *         by one.
	 */
	public static String motdChanger(String enc, int offset) {
		offset = offset % 26 + 26;
		StringBuilder encoded = new StringBuilder();
		for (char i : enc.toCharArray()) {
			encoded.append((char) ('A' + (i + 'A' + offset) % 26));
		}
		return encoded.toString();
	}

	/**
	 * This method sets up a connection to where Message of the Day is and reads the
	 * content.
	 * 
	 * @return br Buffered reader which contains the input stream from the
	 *         connection.
	 * @throws Exception Exception thrown when connection to URL cannot be made.
	 */
	public String readMOTD() throws Exception {
		URL url = new URL("http://cswebcat.swansea.ac.uk/message?solution=".concat(readData()));
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		return br.readLine().toString();
	}
}