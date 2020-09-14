package assets;

/**
 * Class used to create danger sprite
 *
 * @see Sprite
 */
public class Danger extends Sprite {
    /**
     * Constructor of Danger class.
     *
     * @param x position of sprite on x axis
     * @param y position of sprite on y axis
     */
    public Danger(int x, int y) {

        super(x, y);    //invokes main method of super class(Sprite)

        initDanger();

        super.y -= height;
    }

    /**
     * Loads image of the danger sign from resources and gets it's dimensions.
     */
    private void initDanger() {

        loadImage("/danger.png");   //gives path to the file with picture to method from Sprite class that will load the image
        getImageDimensions();        //method from Sprite class
    }
}
