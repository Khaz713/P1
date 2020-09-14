package assets;
/**
 * Class used to create heart sprite
 *
 * @see Sprite
 */
public class Heart extends Sprite{
    /**
     * Constructor of Heart class.
     *
     * @param x position of sprite on x axis
     * @param y position of sprite on y axis
     * @param shield boolean from {@link game.Game#shieldOn} checking if shield has been bought
     */
    public Heart(int x, int y, boolean shield){
        super (x, y);

        initHeart(shield);
    }
    /**
     * Loads image of heart or shield(if bought) from resources and gets it's dimensions.
     * @param shield boolean from {@link #Heart(int, int, boolean)} which determines which sprite to load
     */
    private void initHeart(boolean shield){

        if(shield){
            loadImage("/shield.png");
        }
        else{
            loadImage("/heart.png");
        }

        getImageDimensions();
    }

}