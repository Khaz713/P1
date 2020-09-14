package menus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.InputStream;

/**
 * The main Class for executing the Menu.
 * except some small calls in the Game.java,
 * everything related to Menu would happen here.
 */

@SuppressWarnings("Duplicates")
public class Menu {

    /**
     * An array of several Buttons for the main menu
     * They would be the "PLAY" "HOW TO PLAY" "OPTIONS" "SHOP" and "EXIT" buttons.
     * check the "Button.java" class for more details on this object.
     */
    private final Button[] options; // options is an array of several Buttons for the main menu
    // They would be the "PLAY" "OPTIONS" "SHOP" & "EXIT" buttons.
    // check the "Button.java" class for more details on this object. (button)

    /**
     * The background Image of the menu
     */

    private Image menuOne; //
    /**
     * Which button the user is on now
     * Numbers would be 0 for "PLAY",1 for "HOW TO PLAY", 2 for "OPTIONS", 3 for "SHOPS" and 4 for "EXIT"
     */
    public int currentSelection;// Which button the user is on now
    // Numbers would be 0 for "PLAY", 1 for "OPTIONS", 2 for "SHOPS" & 3 for "EXIT"
    /**
     * If it's +1 the current Selection would go up and if it's -1, it goes down
     */
    private int xd; // if it's +1 the current Selection would go up and if it's -1, it goes down
    /**
     * If it's true, the menu could not be updated
     * (Used for not changing the currentselected when user's finger is still on the keyboard.)
     */
    private boolean stopHere = false; // If it's true, the menu could not be updated
    // (used for not changing the currentselected when user's finger is still on the keyboard.)


    /**
     * This method is used to load font from source folder
     *
     * @return int This returns sum of numA and numB.
     */
    public Font loadFont() {
        try {
            String fName = "/DK Cool Crayon.ttf";
            InputStream is = this.getClass().getResourceAsStream(fName);
            return Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * In constructor the class makes options array
     * and initialize each member of array (which is a Button object) with it special value.
     * The font also would be load by calling the loadFont method.
     * The font is to be used in the each button's constructor.
     */
    // Constructor
    public Menu() {
        options = new Button[5];

        // Options array would be made here, remember that TTF file for the "DK Cool Crayon"
        // Font should be added to JVM fonts folder
        Font fontSmall = loadFont();
        fontSmall = fontSmall.deriveFont(Font.BOLD, 70);
        Font fontBig = loadFont();
        fontBig = fontBig.deriveFont(Font.BOLD, 80);


        options[0] = new Button("PLAY", 300,
                //new Font("DK Cool Crayon", Font.BOLD, 70), // The text format when it's not selected
                fontSmall,
                fontBig,// The text format when it's selected,

                // the only change is the size
                Color.WHITE, Color.WHITE); // The colors don't change but we can change them
        // if it's was to be.

        options[1] = new Button("HOW TO PLAY", 300 + 76,
                fontSmall,
                fontBig, Color.WHITE, Color.WHITE);

        options[2] = new Button("OPTIONS", 300 + 2 * 76,
                fontSmall,
                fontBig, Color.WHITE, Color.WHITE);

        options[3] = new Button("SHOP", 300 + 3 * 76,
                fontSmall,
                fontBig, Color.WHITE, Color.WHITE);

        options[4] = new Button("EXIT", 300 + 4 * 76,
                fontSmall,
                fontBig, Color.WHITE, Color.WHITE);
    }

    /**
     * This method updates the menu (pretty much like the Player's move method).
     * The method is called at the ActionPerformed Method at Game.java.
     * Here the the program checks if the xd variable is -1 or +1.
     * (if xd is -1 it means the user has clicked on UP arrow key and so currentselection variable reduces by 1 and vice versa for the Down button)
     * If the current Selection is less than zero then it should be revalued to 4, so it would point to the "Exit" Button.
     * And also if it reached to 5 it would be revalued to 0.
     * We use stopHere variable to stop the hovering when the user's finger is still on the keyboard.
     * So we will have only one move (up or down) for one click.
     */
    public void tick() {
        if (!stopHere) {
            if (xd == -1) {
                currentSelection--;
                stopHere = true;
            }
            if (xd == 1) {
                currentSelection++;
                stopHere = true;
            }

        }

        if (currentSelection < 0) {
            currentSelection = options.length - 1;
        }

        if (currentSelection >= options.length) {
            currentSelection = 0;
        }

    }

    /**
     * This method get the keyEvent e and based on that knows which keys the user has inputted.
     * And it will navigate the rest of the program by having conditions for each key.
     * <p>
     * if Key Up is pressed xd becomes -1.
     * if Key Down is pressed xd becomes +1.
     *
     * @param e Having all the information about the key that has been pressed by the user
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP) { //Up key pressed

            xd = -1;

        }

        if (key == KeyEvent.VK_DOWN) { //Down key pressed
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
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();
        xd = 0;
        stopHere = false;
    }

    /**
     * In this method the program will render all the buttons in the options array, by using Graphics g as an input.
     * It also draw the title of the menu. and Also background and two guns besides the selected button.
     * When we draw the buttons in by going through a for loop. Whenever it reaches currentselection button the "selected"
     * parameter of that objects will be set to true,Check button class for details and see how this parameter affect the rendering of objects)
     *
     * @param g
     */

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        ImageIcon ii = new ImageIcon(this.getClass().getResource("/bboard.png"));
        menuOne = ii.getImage();
        g2d.drawImage(menuOne, 0, 0, null);

        Font fontBig = loadFont();
        fontBig = fontBig.deriveFont(Font.BOLD, 80);

        Fonts.drawString(g, fontBig,
                Color.WHITE, " \"THE QUIET KID\" ", 100);

        for (int i = 0; i < options.length; i++) {

            if (i == currentSelection)
                options[i].setSelected(true);
            else
                options[i].setSelected(false);
            options[i].render(g);
        }
    }
}
