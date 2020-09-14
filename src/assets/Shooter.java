package assets;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;

/**
 * Class used to create shooter character.
 *
 * @see Sprite
 */
public class Shooter extends Sprite {
    /**
     * ArrayList containing all frames of animation for shooter sprites.
     */
    ArrayList<Image> shooterRun;
    /**
     * Variable for {@link #shooterRun} to determine which sprite to use.
     */
    int animationFrame = 0;

    /**
     * Constructor of Shooter class.
     *
     * @param x position of sprite on x axis
     * @param y position of sprite on y axis
     */
    public Shooter(int x, int y) {

        super(x, y);     //invokes main method of super class(Sprite)

        initShooter();
    }

    /**
     * Loads images from resources folder and adds them to {@link #shooterRun}.
     */
    private void initShooter() {

        BufferedImage ii;
        shooterRun = new ArrayList<>();
        URL url;
        String urlS;

        for (int i = 0; i < 43; i++) {
            try {
                if (i < 10)
                    urlS = "/shooter/run_0" + i + ".png";
                else
                    urlS = "/shooter/run_" + i + ".png";
                url = this.getClass().getResource(urlS);
                ii = ImageIO.read(url);

                shooterRun.add(ii);
            } catch (Exception ignored) {

            }
        }
        image = shooterRun.get(0);
        getImageDimensions();
    }

    /**
     * Changes {@link #animationFrame} variable each frame, based on {@link game.Game#mainTimer} and sets new image using {@link #setImage()} afterwards.
     */
    public void animationFrame() {
        if (animationFrame < shooterRun.size() - 1) {
            animationFrame++;

        } else {
            animationFrame = 0;
        }
        setImage();
    }

    /**
     * Sets image from Array list for each frame based on {@link #animationFrame}.
     */

    private void setImage() {
        image = shooterRun.get(animationFrame);
    }
}
