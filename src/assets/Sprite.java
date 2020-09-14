package assets;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.net.URL;

/**
 * Class used to create all the sprites in this program.
 */
@SuppressWarnings("ALL")
public abstract class Sprite {
    /**
     * x position of the sprite
     */
    protected int x;
    /**
     * y position of the sprite
     */
    protected int y;
    /**
     * Width of the sprite
     */
    protected int width;

    /**
     * Height of the sprite
     */
    protected int height;
    /**
     * Sprite visible on the screen
     */
    protected boolean visible;
    /**
     * Image of the sprite
     *
     * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/awt/Image.html">Image</a>
     */
    protected Image image;

    /**
     * Default constructor of this class
     *
     * @param x position of the sprite on x axis
     * @param y position of the sprite on y axis
     */
    public Sprite(int x, int y) {    //main method of class Sprite

        this.x = x;
        this.y = y;
        visible = true;
    }

    /**
     * Loads image from resource folder
     *
     * @param imageName path to image in resource folder
     */
    protected void loadImage(String imageName) {            //method loading image from resources

        URL url = this.getClass().getResource(imageName);
        try {
            BufferedImage ii = ImageIO.read(url);
            image = ii;
        } catch (Exception ignored) {
        }

    }

    /**
     * Gets dimensions of the image and changes values of width and height variables based on that.
     */
    protected void getImageDimensions() {       //method returning width and height of object's sprite

        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    /**
     * Gets image.
     *
     * @return image
     */
    public Image getImage() {       //method to get image from object
        return image;
    }


    /**
     * Gets x
     *
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * Gets y
     *
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * Gets visible boolean
     *
     * @return visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Gets width
     *
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets height
     *
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Changes value of visible boolean
     *
     * @param visible new value for visible boolean
     */
    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    /**
     * Gets x, y position and dimensions of the image as Rectangle
     *
     * @return new Rectangle()
     * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/awt/Rectangle.html">Rectangle</a>
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
