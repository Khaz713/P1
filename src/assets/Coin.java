package assets;

/**
 * Class used to create coin sprite.
 *
 * @see Sprite
 */
public class Coin extends Sprite {
    /**
     * Speed at which coin moves per one timer tick
     *
     * @see game.Game#mainTimer
     */
    private final int SPRITE_SPEED = 15;

    /**
     * Constructor of Coin class
     *
     * @param x position of sprite on x axis
     * @param y position of sprite on y axis
     */
    public Coin(int x, int y) {

        super(x, y);


        initCoin();
    }

    /**
     * Loads image of the coin from resources and gets it's dimensions.
     */
    private void initCoin() {
        loadImage("/coin.png");
        getImageDimensions();
        y -= 60 + height;
    }

    /**
     * Moves the coin sprite and sets it's {@link Sprite#visible} to false if it's of screen.
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
