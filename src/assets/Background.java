package assets;


/**
 * Class used to create background sprite.
 *
 * @see Sprite
 */
public class Background extends Sprite {
    /**
     * Speed at which background moves per one timer tick
     *
     * @see game.Game#mainTimer
     */
    private final int BACKGROUND_SPEED = 8;

    /**
     * Constructor of Background class.
     *
     * @param x     position of sprite on x axis
     * @param index random number from 1 to 5 selected in {@link Game.Game#updateSprites()}, it determines which background is created.
     */
    public Background(int x, int index) {

        super(x, 257);
        initBackground(index);
    }

    /**
     * Loads image of background from resources and gets it's dimensions.
     *
     * @param index random number from 1 to 5 provided in {@link #Background(int, int)}, the image of background is based on it.
     */
    private void initBackground(int index) {

        String name = "/bg" + index + ".png";

        loadImage(name);
        getImageDimensions();
    }

    /**
     * Moves the powerup sprite and sets it's {@link Sprite#visible} to false if it's of screen.
     *
     * @see game.Game#updateSprites()
     */
    public void move() {

        x -= BACKGROUND_SPEED;

        if (x + width < 0) {
            setVisible(false);
        }
    }

    /**
     * Gets speed of the background
     *
     * @return BACKGROUND_SPEED
     */
    public int getSpeed() {
        return BACKGROUND_SPEED;
    }
}
