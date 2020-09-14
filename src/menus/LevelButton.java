package menus;
import java.awt.*;

/**
 *  This class is made to be used during the Menu processing. (GameState = 0)
 *  Each button has a boolean "selected" if it's True then
 *  the button needs to be shown in a bigger font and with two guns drawn at it's sides
 *  The class is inherited from Rectangle class to be able to use the related methods regarding size and position.
 */
public class LevelButton extends Rectangle {
    /**
     * Each button has a boolean "selected" if it's True then
     * The program would compile the button in a different way (for example bigger font or some extra graphics)
     */
    private boolean selected;
    /**
     * The horizontal position of the button
     */
    private final int X;
    /**
     * The vertical position of the button
     */
    private final int Y;
    /**
     * The text for the button . it can be "Endless mode" or anything that is specified
     * during initializing the button at the main class
     */
    private final String text;
    /**
     *  First font is the initiative font (before being selected)
     */
    private final Font font;
    /**
     *  Second font, which would be used if we want to use a different font for our selected button.
     */
    private final Font selectedFont;
    /**
     * is the initiative color (before being selected)
     */
    private final Color color;
    /**
     *  would be used if we want to use a different color for our selected button.
     */
    private final Color selectedColor;
    /**
     * It is a constructor and initialize every variable
     * @param text
     * @param X
     * @param Y
     * @param font
     * @param selectedFont
     * @param color
     * @param selectedColor
     */
    public LevelButton(String text, int X, int Y, Font font, Font selectedFont, Color color,
                       Color selectedColor) {
        this.font = font;
        this.selectedFont = selectedFont;
        this.color = color;
        this.selectedColor = selectedColor;
        this.text = text;
        this.X = X;
        this.Y = Y;
    }

    /**
     * The method that would change the "selected" variable to the given input.
     * @param selected A boolean that is given as an input
     */
    public void setSelected(boolean selected) { // The method that would change the "selected" variable
        this.selected = selected;
    }

    /**
     * Drawing the buttons, which would be called at the levels class
     * If the button's selected parameter is true then
     * the second font and second color are to be drawn(second font has a bigger size)
     * And at the end two guns would be drawn around the selected button.
     * @param g a parameter provided by internals of the GUI system
     * @see Levels
     */
    public void render(Graphics g) {   // The method for drawing button , which would be called at the menu class


        if (selected) { // If the button is selected then draw the second font and second color (second font has a bigger size)
            Fonts.drawString(g, selectedFont, selectedColor, text, X, Y);
        } else
            Fonts.drawString(g, font, color, text, X, Y);
    }
}
