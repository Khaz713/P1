package assets;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import game.Game;

/**
 * Class used to load and and play music.
 *
 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/applet/AudioClip.html">AudiClip</a>
 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/applet/Applet.html">Applet</a>
 */
public class Music {
    /**
     * AudioClip parameter used to store sound file of music and play it.
     */
    private AudioClip backgroundMusic; // music playing in the background

    /**
     * Constructor of Music class.
     */
    public Music(){
        initMusic();
    }

    /**
     * Finds audio file based on {@link Game#musicOption} and loads it to {@link #backgroundMusic}.
     */
    public void initMusic() { // method to load background music
        URL url;
        if (Game.getMusicOption()) {
            url = this.getClass().getResource("/backgroundMusic.wav"); // url to background Music file
        } else {
            url = this.getClass().getResource("/soundoff.wav"); // url to background Music file
        }
        backgroundMusic = Applet.newAudioClip(url); // background music object

    }

    /**
     * Plays or stops the music based on given boolean
     * @param on boolean provided in {@link Game}
     */
    public void music(boolean on){
        if(on){
            backgroundMusic.loop();
        }
        else{
            backgroundMusic.stop();
        }
    }
}
