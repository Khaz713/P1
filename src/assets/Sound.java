package assets;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

import game.Game;

/**
 * Class used to load and play sounds.
 *
 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/applet/AudioClip.html">AudiClip</a>
 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/applet/Applet.html">Applet</a>
 */
public class Sound {
    /**
     * AudioClip parameter used to store audio file of damage sound and play it.
     */
    private AudioClip damageSound;   //audio of damage sound
    /**
     * AudioClip parameter used to store audio file of danger sound and play it.
     */
    private AudioClip dangerSound;  //audio of danger sound
    /**
     * AudioClip parameter used to store audio file of game over sound and play it.
     */
    private AudioClip gameOverSound;//audio of game over screen
    /**
     * AudioClip parameter used to store audio file of congratulation sound and play it.
     */
    private AudioClip congratsSound; //audio of when you finish a level

    /**
     * Constructor of Sound class.
     */
    public Sound() {  //method to load all game sounds

        initSounds();
    }

    /**
     * Loads audio files based on {@link Game#musicOption}.
     */
    public void initSounds() {
        if (Game.getSoundOption()) {

            URL url = this.getClass().getResource("/damage.wav");   //url to damage sound
            damageSound = Applet.newAudioClip(url);     //damage sound object

            url = this.getClass().getResource("/danger.wav");
            dangerSound = Applet.newAudioClip(url);

            url = this.getClass().getResource("/gameover.wav");
            gameOverSound = Applet.newAudioClip(url);

            url = this.getClass().getResource("/congratsSound.wav");
            congratsSound = Applet.newAudioClip(url);
        } else {
            URL url = this.getClass().getResource("/soundoff.wav");
            damageSound = Applet.newAudioClip(url);     //damage sound object

            dangerSound = Applet.newAudioClip(url);

            gameOverSound = Applet.newAudioClip(url);

            congratsSound = Applet.newAudioClip(url);
        }
    }

    /**
     * Plays or stops the damage sound based on given boolean
     *
     * @param on boolean provided in {@link Game}
     */
    public void damage(boolean on) {
        if (on) {
            damageSound.play();
        } else {
            damageSound.stop();
        }
    }

    /**
     * Plays or stops the danger sound based on given boolean
     *
     * @param on boolean provided in {@link Game}
     */
    public void danger(boolean on) {
        if (on) {
            dangerSound.play();
        } else {
            dangerSound.stop();
        }
    }

    /**
     * Plays or stops the game over sound based on given boolean
     *
     * @param on boolean provided in {@link Game}
     */
    public void gameover(boolean on) {
        if (on) {
            gameOverSound.play();
        } else {
            gameOverSound.stop();
        }
    }

    /**
     * Plays or stops the congratulations sound based on given boolean
     *
     * @param on boolean provided in {@link Game}
     */
    public void congrats(boolean on) {
        if (on) {
            congratsSound.play();
        } else {
            congratsSound.stop();
        }
    }
}
