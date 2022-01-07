package swan.group7.dragon.controllers;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * AudioPlayer.java This class is used to set, control and adjust the music
 * player that plays during the game, login screen and level selectors.
 * 
 * @author ___ (JavaDoc Mollie Stamp)
 * @version 1.6
 *
 */
public class AudioPlayer {

	private static MediaPlayer player;

	private static Double volume = 0.5;

	/**
	 * 
	 * @param songname
	 */
	public AudioPlayer(String songname) {
		Media song = new Media(getClass().getResource("/music/" + songname + ".mp3").toExternalForm());
		player = new MediaPlayer(song);
		player.setCycleCount(1);
		player.setVolume(volume);
		player.play();
	}

	/**
	 * This stops the music player.
	 */
	public void stop() {
		player.stop();
	}

	/**
	 * This unmutes the music player by setting the volume.
	 */
	public void unmute() {
		player.setVolume(volume);
	}

	/**
	 * This plays the music.
	 */
	public void playmusic() {
		player.setVolume(volume);
		player.play();
	}

	/**
	 * This method mutes the music by setting the volume to 0.
	 */
	public void mute() {
		player.setVolume(0);
	}

	/**
	 * This method sets the volume to a given number.
	 * 
	 * @param val Double value which is the volume level of the music player.
	 */
	public void setVolume(double val) {
		player.setVolume(val);
	}

	/**
	 * This method selects the song to be played.
	 * 
	 * @param songname Name of song to be played
	 */
	public void setSong(String songname) {
		Media song = new Media(getClass().getResource("/music/" + songname + ".mp3").toExternalForm());
		player = new MediaPlayer(song);
		player.setCycleCount(1);
		player.play();
	}
}
