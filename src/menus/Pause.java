package menus;
import game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.InputStream;

/**
 * This class was created to allow user pause the game during his performance
 *
 */
public class Pause {

    /**
     * An array of several Buttons
     * @see Button
     */
    private final Button[] Options2;

    /**
     * The background image of the menu
     */
    private Image menuOne;
    /**
     * Variable for checking which button is user currently choosing
     */
    public int currentSelection;
    /**
     * Variable checking if its 1, then currentSelection goes up and if its1 currentSelection goes down
     */
    private int xd;
    /**
     * If it is true the class could not be updated
     */
    private boolean stopHere = false;

    /**
     * Method used to load font from the source folder
     * @see Fonts
     * @return
     */
    public Font loadFont(){
        try {
            String fName = "/DK Cool Crayon.ttf";
            InputStream is = this.getClass().getResourceAsStream(fName);
            return Font.createFont(Font.TRUETYPE_FONT, is);
        }
        catch (Exception e){
            return null;
        }
    }


    /**
     * In constructor the class makes options array
     * and initialize each member of array (which is a Button object) with it special value.
     * The font also would be load by calling the loadFont method.
     * The font is to be used in the each button's constructor.
     * @see Fonts
     * @see Button
     */
    public Pause() {
        Options2 = new Button[2];

        Font fontSmall= loadFont();
        fontSmall = fontSmall.deriveFont(Font.BOLD, 70);
        Font fontBig = loadFont();
        fontBig = fontBig.deriveFont(Font.BOLD, 80);

        // Options array would be made here, remember that TTF file for the "DK Cool Crayon"
        // Font should be added to JVM fonts folder

        Options2[0] = new Button("Resume", 300,
                fontSmall, // The text format when it's not selected
                fontBig,// The text format when it's selected,
                // the only change is the size
                Color.WHITE, Color.WHITE); // The colors don't change but we can change them
        // if it's was to be.

        Options2[1] = new Button("Back to menu", 300 + 76,
                fontSmall,
                fontBig, Color.WHITE, Color.WHITE);
    }

    /**
     * This method updates the Pause class.
     * The method is called at the ActionPerformed Method at Game.java.
     * Here the the program checks if the xd variable is -1 or +1.
     * (if xd is -1 it means the user has clicked on UP arrow key and so currentSelection variable reduces by 1 and vice versa for the Down button)
     * We use stopHere variable to stop the hovering when the user's finger is still on the keyboard.
     * So we will have only one move (up or down) for one click.
     * @see Game
     */
    public void tick() {

        if (!stopHere) {
            if (xd == -1) { // means it goes to an upper button
                currentSelection--;
                stopHere = true;
            }
            if (xd == 1){
                currentSelection++;
                stopHere = true;
            }

        }
        if (currentSelection < 0) {
            currentSelection = Options2.length - 1;
        }
        if (currentSelection >= Options2.length) {
            currentSelection = 0;
        }
    }

    /**
     *
     * This method get the keyEvent e and based on that knows which keys the user has inputted.
     * And it will navigate the rest of the program by having conditions for each key.
     *
     * if Key Up is pressed xd becomes -1.
     * if Key Down is pressed xd becomes +1.
     *
     * @param e parameter provided by KeyAdapter class
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP){ //Up key pressed

            xd = -1;
        }

        if (key == KeyEvent.VK_DOWN){ //Down key pressed
            //currentSelection++;
            //wasPressed2 = true;
            xd = 1;
        }
    }

    /**
     * This method is used to detect when the key is released by user and act accordingly.
     *
     * @param e parameter provided by KeyAdapter class
     */
    public void keyReleased(KeyEvent e){

        int key = e.getKeyCode();
        xd = 0;
        stopHere = false;
    }

    /**
     * In this method the program will render all the buttons in the options array, by using Graphics g as an input.
     * It also draw the title of the Pause class, background and two guns besides the selected button.
     * When we draw the buttons in by going through a for loop. Whenever it reaches currentSelection button the "selected"
     * parameter of that objects will be set to true
     * @see Button
     * @param g a parameter provided by internals of the GUI system
     */
    public void render(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        ImageIcon ii = new ImageIcon(this.getClass().getResource("/bboard.png"));   //assigns image icon based on given url
        menuOne = ii.getImage();  //ImageIcon method to assign image icon from ii object to image
        g2d.drawImage(menuOne, 0, 0, null);

        Font fontBig = loadFont();
        fontBig = fontBig.deriveFont(Font.BOLD, 80);


        // Drawing the title OPTIONS
        Fonts.drawString(g, fontBig,
                Color.WHITE, "PAUSE", 100);


        // Rendering every Button, And implementing the "currentSelection"
        // to the button by changing the selected variable of that button
        for (int i = 0; i < Options2.length; i++) {

            if (i == currentSelection)
                Options2[i].setSelected(true);
            else
                Options2[i].setSelected(false);
            Options2[i].render(g);
        }
    }
}
