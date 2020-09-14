package menus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.InputStream;

/**
 * The main Class for executing the Tutorial.
 * except some small calls in the Game.java,
 * everything related to Tutorial would happen here.
 */


public class Tutorial extends Menu {

    /**
     * An array of two Buttons for the Tutorial
     */
    private final Button[] Tutorial2; // options is an array of several Buttons for the main menu
    // They would be the "PLAY" "OPTIONS" "SHOP" & "EXIT" buttons.
    // check the "Button.java" class for more details on this object. (button)


    /**
     * A background for tutorial which contains all the data in it in form of pictures.
     */
    private Image tutImg; //Image of the tutorial

    /**
     * Which button the user is on now
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
    // (used for not changing the currentselection when user's finger is still on the keyboard.)

    /**
     * This method is used to load font from source folder
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


    // Constructor
    /**
     * In constructor the class makes Tutorial2 array
     * and initialize each member of array (which is a Button object) with it special value.
     * The font also would be load by calling the loadFont method.
     * The font is to be used in the each button's constructor.
     */
    public Tutorial() {
        Tutorial2 = new Button[2];

        Font fontSmall = loadFont();
        fontSmall = fontSmall.deriveFont(Font.BOLD, 70);
        Font fontBig = loadFont();
        fontBig = fontBig.deriveFont(Font.BOLD, 80);

        // Options array would be made here, remember that TTF file for the "DK Cool Crayon"
        // Font should be added to JVM fonts folder

        Tutorial2[0] = new Button("", 300 + 8 * 76,
                fontSmall,
                fontBig, Color.WHITE, Color.WHITE);

        Tutorial2[1] = new Button("Back To Menu", 300 + 4 * 76,
                fontSmall,
                fontBig, Color.WHITE, Color.WHITE);
    }


    /**
     * This method updates the shop (pretty much like the Player's move method).
     * The method is called at the ActionPerformed Method at Game.java.
     * Here the the program checks if the xd variable is -1 or +1.
     * (if xd is -1 it means the user has clicked on UP arrow key and so currentselection variable reduces by 1 and vice versa for the Down button)
     * If the current Selection is less than zero then it should be revalued to the last element.
     * And also if it reached to last element it would be revalued to 0.
     * We use stopHere variable to stop the hovering when the user's finger is still on the keyboard.
     * So we will have only one move (up or down) for one click.
     *
     */
    public void tick() { // Update the menu (pretty much like the Player's move method)
        // , the method is called at the ActionPerformed Method at Game.java

        if (!stopHere) {
            if (xd == -1) { // means it goes to an upper button
                currentSelection--;
                stopHere = true;
            }
            if (xd == 1) {
                currentSelection++;
                stopHere = true;
            }

        }

        // If the current Selection is less than Zero
        // (i.e it points to a button which is upper than the first button ,"PLAY")
        // then it should be revalued to 3, so it would point to the "Exit" Button.
        if (currentSelection < 0) {
            currentSelection = Tutorial2.length - 1;
        }

        // The same should be done for the other way
        if (currentSelection >= Tutorial2.length) {
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
     * In this method the program will render all the buttons in the array, by using Graphics g as an input.
     * It also draws background.
     * When we draw the buttons in by going through a for loop. Whenever it reaches currentselection button the "selected"
     * parameter of that objects will be set to true,Check button class for details and see how this parameter affect the rendering of objects)
     * @param g a parameter provided by internals of GUI system
     */
    public void render(Graphics g) { // This method draws the whole menu
        // (is called at the drawMenu method of Game.java)

        Graphics2D g2d = (Graphics2D) g;

        ImageIcon ii = new ImageIcon(this.getClass().getResource("/tutImg.png"));   //assigns image icon based on given url
        tutImg = ii.getImage();  //ImageIcon method to assign image icon from ii object to image
        g2d.drawImage(tutImg, 0, 0, null);

        Font fontBig = loadFont();
        fontBig = fontBig.deriveFont(Font.BOLD, 80);

        // Rendering every Button, And implementing the "currentSelection"
        // to the button by changing the selected variable of that button
        for (int i = 0; i < Tutorial2.length; i++) {

            if (i == currentSelection)
                Tutorial2[i].setSelected(true);
            else
                Tutorial2[i].setSelected(false);
            Tutorial2[i].render(g);
        }
    }
}
