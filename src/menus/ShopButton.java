package menus;
import java.awt.*;


/**
 *  This class is made to be used during the Shop processing. (GameState = 0)
 *  Each ShopButton contains a picture of the element to which it refers in the Shop menu.
 *
 *  Each button has a boolean "selected" if it's True then
 *  the button needs to be shown in a bigger size (Selected Item would be drawn instead of item)
 *  The class is inherited from Rectangle class to be able to use the related methods regarding size and position.
 */

public class ShopButton extends Rectangle {
    private boolean selected; //each button has a boolean "selected" if it's True then
    // the program would compile the button in a different way (for example bigger font or some extra graphics)
    /**
     *  First item is the initiative item (before being selected)
     */
    private final Image item;

    /**
     *  Second Item , it is only a bigger version of item image and should be drawn when the button is selected.
     */
    private final Image selectemItem; // The background Image of the menu

    /**
     * The horizontal position of the button
     */
    private final int picX;
    /**
     * The vertical position of the button
     */
    private final int picY;

    /**
     * It's a costructor and initialize every variable.
     * @param item
     * @param selectemItem
     * @param picX
     * @param picY
     */
    public ShopButton(Image item, Image selectemItem, int picX, int picY) {
        this.item = item;
        this.selectemItem = selectemItem;
        this.picX = picX;
        this.picY = picY;
    }

    /**
     * The method that would change the "selected" variable to the given input.
     * @param selected A boolean that is given as an input
     */

    public void setSelected(boolean selected) { // The method that would change the "selected" variable
        this.selected = selected;
    }

    /**
     * Drawing the button, which would be called at the shop class
     * If the button's selected parameter is true then
     * the selected item is to be drawn(second item has a bigger size)
     * @param g a parameter provided by internals of GUI system.
     */
    public void render(Graphics g) {   // The method for drawing button , which would be called at the menu class

        Graphics2D g2d = (Graphics2D) g;

        if (selected) { // If the button is selected then draw the second font and second color (second font has a bigger size)
         //   Image k = selectemItem;
            int p1 = item.getHeight(null) - selectemItem.getHeight(null);
            g2d.drawImage(selectemItem, picX , picY + p1, null);
            //Fonts.drawString(g, selectedFont, selectedColor,   text , textY);
        }
        else
        g2d.drawImage(selectemItem, picX , picY, null);
    }

}
