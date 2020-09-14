package assets;

import game.Game;

/**
 * Class used to create bullet sprite
 *
 * @see Sprite
 */
public class Bullet extends Sprite {
    /**
     * Speed at which bullet moves per one timer tick
     *
     * @see Game#mainTimer
     */
    private final int BULLET_SPEED = 20; //speed of the bullet in pixels per frame

    /**
     * Constructor of Bullet class.
     *
     * @param x position of sprite on x axis
     * @param y position of sprite on y axis
     */
    public Bullet(int x, int y) {

        super(x, y);    //invokes main method of super class(Sprite)

        initBullet();
    }

    /**
     * Loads image of the bullet from resources and gets it's dimensions.
     */
    private void initBullet() {

        loadImage("/bullet.png");   //gives path to the file with bullet to method from Sprite class that will load the image
        getImageDimensions();   //method from Sprite class
    }

    /**
     * Moves the bullet sprite and sets it's {@link Sprite#visible} to false if it's of screen.
     *
     * @see Game#updateSprites()
     */
    public void move() {     //changes x position of bullet

        x += BULLET_SPEED;

        if (x > Game.getWindowWidth()) {  //checks if the image is still on the screen
            visible = false;
        }
    }
}
