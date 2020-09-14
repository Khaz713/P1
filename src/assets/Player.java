package assets;

import javax.imageio.ImageIO;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;

import game.Game;

/**
 * Class used to create player's character
 *
 * @see Sprite
 */
public class Player extends Sprite {
    /**
     * Height at which floor is.
     */
    private final int floorLevel = 60;
    /**
     * Variable for imageList to determine which sprite to use.
     */
    private int standingOrDucking = 0;
    /**
     * Secondary variable for imageList to determine which sprite to use.
     */
    private int animationFrame = 0;
    /**
     * ArrayList containing all other ArrayLists with frames of animation of the player.
     */
    private final ArrayList<ArrayList<Image>> imageList = new ArrayList<>();
    /**
     * ArrayList containing all frames of animation for standing player sprites.
     */
    private final ArrayList<Image> standingSprites = new ArrayList<>();
    /**
     * ArrayList containing all frames of animation for ducking player sprites.
     */
    private final ArrayList<Image> duckingSprites = new ArrayList<>();
    /**
     * ArrayList containing all frames of animation for jumping player sprites.
     */
    private final ArrayList<Image> jumpingSprites = new ArrayList<>();
    /**
     * Jump key(Arrow UP) pressed.
     */
    private boolean jumpKey;
    /**
     * Player can jump.
     */
    private boolean jumpAvailable;
    /**
     * Sound made while jumping.
     *
     * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/applet/AudioClip.html">AudiClip</a>
     */
    private AudioClip jumpSound;
    /**
     * Height of player while standing.
     */
    private int standingHeight;
    /**
     * Height of player while ducking.
     */
    private int duckingHeight;
    /**
     * Program's window height.
     *
     * @see Game
     */
    private final int gameHeight = Game.getWindowHeight();
    /**
     * Program's window width.
     *
     * @see Game
     */
    private final int gameWidth = Game.getWindowWidth();
    /**
     * Player is standing up from ducking.
     */
    private boolean standingUP;

    /**
     * Constructor for Player class.
     * Sets starting position of the player and uses {@link #initPlayer() initPlayer} and {@link #setPlayerSound() setPlayerSound} method.
     */
    public Player() {

        super(0, 0); //starting x and y position of the player

        setPlayerSound();

        initPlayer();

        x = gameWidth / 3;
        y = gameHeight - floorLevel - standingHeight;

    }

    /**
     * Loads images from resources folder and adds them to appropriate ArrayLists and ads those list to {@link #imageList imageList} array.
     */
    private void initPlayer() {

        BufferedImage ii;
        URL url;
        String urlS;


        for (int i = 0; i < 43; i++) {                          //loop for standing loading images to list, right now there is only one picture but the loop is ready for the future

            try {
                if (i < 10)
                    urlS = "/player/run/run_00" + i + ".png";
                else
                    urlS = "/player/run/run_0" + i + ".png";
                url = this.getClass().getResource(urlS);
                ii = ImageIO.read(url);
                standingSprites.add(ii);

            } catch (Exception ignored) {

            }
        }
        standingHeight = standingSprites.get(0).getHeight(null);


        for (int i = 8; i < 20; i++) {   //loop for loading ducking images to list

            try {
                if (i < 10)
                    urlS = "/player/slide/slide_0" + i + ".png";
                else
                    urlS = "/player/slide/slide_" + i + ".png";
                url = this.getClass().getResource(urlS);
                ii = ImageIO.read(url);
                duckingSprites.add(ii);
            } catch (Exception ignored) {

            }
        }

        for (int i = 0; i < 37; i++) {
            try {
                if (i < 10)
                    urlS = "/player/jump/jump_00" + i + ".png";
                else
                    urlS = "/player/jump/jump_0" + i + ".png";
                url = this.getClass().getResource(urlS);
                ii = ImageIO.read(url);
                jumpingSprites.add(ii);
            } catch (Exception ignored) {

            }
        }

        duckingHeight = duckingSprites.get(4).getHeight(null);

        imageList.add(standingSprites);     //adding 2 lists to imageList list
        imageList.add(duckingSprites);
        imageList.add(jumpingSprites);
        image = imageList.get(standingOrDucking).get(animationFrame); //setting the first frame of standing player to image variable

    }

    /**
     * Sets sound of jumping based on game options {@link Game#getSoundOption()}.
     *
     * @see Game
     */
    private void setPlayerSound() {

        URL jumpUrl;
        if (Game.getSoundOption()) {
            jumpUrl = this.getClass().getResource("/jump.wav"); //URL address to jump sound
        } else {
            jumpUrl = this.getClass().getResource("/soundoff.wav"); //URL address to jump sound
        }
        jumpSound = Applet.newAudioClip(jumpUrl);     //jump audio clip
    }

    /**
     * Sets image from Array list for each frame based on {@link #standingOrDucking } and {@link #animationFrame}.
     */
    private void setImage() {

        image = imageList.get(standingOrDucking).get(animationFrame);
        getImageDimensions();
    }

    /**
     * Changes {@link #animationFrame} variable, based on {@link Game#mainTimer} and sets new image using {@link #setImage()} afterwards.
     */
    public void animationFrame() {

        switch (standingOrDucking) {
            case 0:
                if (animationFrame < imageList.get(0).size() - 1) {
                    animationFrame++;

                } else {
                    animationFrame = 0;
                }
                break;
            case 1:
                if (animationFrame < 7) {
                    if (animationFrame >= 0 && animationFrame < 3) {
                        y += (standingHeight - duckingHeight) / 3;
                    }
                    animationFrame++;
                } else if (animationFrame == 7) {
                    animationFrame = 3;
                }
                if (standingUP) {
                    if (animationFrame < 7) {
                        animationFrame = 7;
                    }
                    if (animationFrame < 11)
                        y -= (standingHeight - duckingHeight) / 3;
                    animationFrame++;
                    if (animationFrame == 11) {
                        standingUP = false;
                        standingOrDucking = 0;
                        animationFrame = 0;
                    }
                }
                break;
            case 2:
                if (animationFrame < imageList.get(2).size() - 1) {
                    animationFrame++;

                } else {
                    standingOrDucking = 0;
                    animationFrame = 0;
                }
                if (y >= gameHeight - floorLevel - standingHeight) {
                    standingOrDucking = 0;
                    animationFrame = 0;
                }

                break;
        }
        setImage();
    }

    /**
     * Changes y position of the player, sets limits on how high he can jump and makes sure player will come back to {@link #floorLevel} after jumping.
     */
    public void move() {     //changes y position of player

        if (jumpKey && jumpAvailable) {    //jump key is pressed and highest jump position is not reached
            int jumpSpeed = -15;
            y += jumpSpeed;
        } else if (y < gameHeight - floorLevel - standingHeight) {   //else player will start falling down
            int fallingSpeed = 10;
            y += fallingSpeed;
        }
        if (standingOrDucking == 0 && y > gameHeight - floorLevel - standingHeight) {
            y = gameHeight - floorLevel - standingHeight;
        }
        if (y == gameHeight - floorLevel - standingHeight) {       //if player touches the ground he can jump again
            jumpAvailable = true;
        }
        if (y <= gameHeight - floorLevel - (standingHeight * 2)) {  //if player reaches highest point of jump
            jumpAvailable = false;

        }
    }

    /**
     * Moves player back after he comes in contact with an Obstacle.
     *
     * @see Game#checkCollisions()
     * @see Obstacle
     */
    public void obstacleHit() {  //player is moved 40 pixels back if he hits the obstacle

        x -= 40;
    }

    /**
     * Moves player forward after he comes in contact with a Powerup.
     *
     * @see Game#checkCollisions()
     * @see Powerup
     */
    public void powerup() {
        if (x < gameWidth / 2) {
            x += 40;
        }
    }

    /**
     * This method detects when user presses the key and takes actions based on the key pressed and other variables in class.
     *
     * @param e parameter provided by KeyListener
     */
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP && jumpAvailable && standingOrDucking == 0 && isVisible()) { //up key pressed
            jumpKey = true;
            jumpSound.play();
            standingOrDucking = 2;
            animationFrame = 0;
        }

        if (key == KeyEvent.VK_DOWN) { //down key pressed
            if (jumpAvailable && y == gameHeight - floorLevel - standingHeight && standingOrDucking == 0) {
                standingOrDucking = 1;
                animationFrame = 0;
                setImage();

            }
        }

    }

    /**
     * This method detects when user releases the key and takes actions based on the key pressed and other variables in class.
     *
     * @param e parameter provided by KeyListener
     */
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP) { //up key released

            jumpKey = false;
            jumpAvailable = false;
        }

        if (key == KeyEvent.VK_DOWN) { //down key released
            if (jumpAvailable && standingOrDucking == 1) {
                standingUP = true;
            }

        }
    }

}
