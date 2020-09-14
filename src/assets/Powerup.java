package assets;


/**
 * Class used to create powerup sprite.
 *
 * @see Sprite
 */
public class Powerup extends Sprite {
    /**
     * Speed at which powerup moves per one timer tick
     *
     * @see game.Game#mainTimer
     */
    private final int SPRITE_SPEED = 15;

    /**
     * Constructor of Powerup class.
     *
     * @param x     position of sprite on x axis
     * @param y     position of sprite on y axis
     * @param index random number from 1 to 3 selected in {@link game.Game#timers()}, it determines which powerup is created.
     */
    public Powerup(int x, int y, int index) {

        super(x, y);


        initPowerup(index);
    }

    /**
     * Loads image of powerup from resources and gets it's dimensions.
     *
     * @param i random number from 1 to 3 provided in {@link #Powerup(int, int, int)}, the image of powerup is based on it.
     */
    private void initPowerup(int i) {

        switch (i) {
            case 1:
                loadImage("/banana.png");
                break;
            case 2:
                loadImage("/drink.png");
                break;
            case 3:
                loadImage("/skate.png");
                break;
        }
        getImageDimensions();
        y -= 60 + height;
    }

    /**
     * Moves the powerup sprite and sets it's {@link Sprite#visible} to false if it's of screen.
     *
     * @see game.Game#updateSprites()
     */
    public void move() {

        x -= SPRITE_SPEED;

        if (x < 0 - getWidth()) {
            visible = false;
        }
    }
}
