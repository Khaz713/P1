package assets;


/**
 * Class used to create obstacle sprite.
 *
 * @see Sprite
 */
public class Obstacle extends Sprite {
    /**
     * Speed at which obstacle moves per one timer tick
     *
     * @see game.Game#mainTimer
     */
    private final int OBSTACLE_SPEED = 15;

    /**
     * Constructor of Obstacle class.
     *
     * @param x     position of sprite on x axis
     * @param y     position of sprite on y axis
     * @param index random number from 1 to 4 selected in {@link game.Game#updateSprites()}, it determines which obstacle is created.
     */
    public Obstacle(int x, int y, int index) {

        super(x, y);   //invokes main method of super class(Sprite)

        initSpike(index);
    }

    /**
     * Loads image of obstacle from resources and gets it's dimensions.
     *
     * @param i random number from 1 to 4 provided in {@link #Obstacle(int, int, int)}, the image of obstacle is based on it.
     */
    private void initSpike(int i) {

        switch (i) {
            case 1:
                loadImage("/table.png");      //gives path to the file with picture to method from Sprite class that will load the image
                break;
            case 2:
                loadImage("/tableBooks.png");
                break;
            case 3:
                loadImage("/books.png");
                break;
            case 4:
                loadImage("/backpack.png");
                break;
        }
        getImageDimensions();    //method from Sprite class
        y -= 60 + height;
        if (i == 1 || i == 2) {
            height -= 110;
        }
    }

    /**
     * Moves the obstacle sprite and sets it's {@link Sprite#visible} to false if it's of screen.
     *
     * @see game.Game#updateSprites()
     */
    public void move() {     //changes x position of obstacle

        x -= OBSTACLE_SPEED;

        if (x < 0 - getWidth()) {     //checks if the image is still on the screen
            visible = false;
        }
    }
}