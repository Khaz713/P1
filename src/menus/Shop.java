package menus;

import game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.InputStream;

@SuppressWarnings("Duplicates")

/**
 * The main Class for executing the Shop.
 * except some small calls in the Game.java,
 * everything related to Shop would happen here.
 */
public class Shop {

    /**
     * An array of several Buttons for the Shop options
     *
     * @see "ShopButton.java" class for more details on this object.
     */
    private final ShopButton[] options;

    /**
     * One Button for going back to the main menu
     *
     * @see "Button.java"
     */
    private final Button backToMenu;

    /**
     * The background Image of the menu
     */
    private Image shopPic; // The background Image of the menu

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
    // (used for not changing the currentselected when user's finger is still on the keyboard.)


//    private Image powerUpPic;
//    private Image newImage;

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
     * In constructor the class makes options array (At this stage of Shop we only have one Item so the
     * options array would only have 1 element.
     * we also initialize the Button with the "Back To Menu" text.
     * The font also would be load by calling the loadFont method.
     * The font is to be used in the button's constructor.
     * We also make an ImageIcon for the Shop element.
     */
    public Shop() {
        options = new ShopButton[1]; // Here Options Sizes can be changed.
        Font fontSmall = loadFont();
        fontSmall = fontSmall.deriveFont(Font.BOLD, 70);
        Font fontBig = loadFont();
        fontBig = fontBig.deriveFont(Font.BOLD, 80);


        backToMenu = new Button("Back To Menu", 300 + 4 * 76,
                //new Font("DK Cool Crayon", Font.BOLD, 70), // The text format when it's not selected
                fontSmall,
                fontBig,// The text format when it's selected,

                // the only change is the size
                Color.WHITE, Color.WHITE);

        ImageIcon i2 = new ImageIcon(this.getClass().getResource("/shopshield1.png"));
        Image Picture = i2.getImage();
        // 49 * 120
        //g2d.drawImage(powerUpPic, 500, 380, null);
        //New Image;
        ImageIcon icon = new ImageIcon(this.getClass().getResource("/shopshield2.png"));
        Image SelectedPicture = icon.getImage();

        options[0] = new ShopButton(Picture, SelectedPicture, 350 - Picture.getWidth(null) / 2, 160);


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
            currentSelection = options.length;
        }

        // The same should be done for the other way

        if (currentSelection > options.length) {
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
    public void keyRelesed(KeyEvent e) {

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
    public void render(Graphics g) { // This method draws the whole menu
        // (is called at the drawMenu method of Game.java)

        Graphics2D g2d = (Graphics2D) g;

        ImageIcon ii = new ImageIcon(this.getClass().getResource("/shopNew2.png"));   //assigns image icon based on given url
        shopPic = ii.getImage();  //ImageIcon method to assign image icon from ii object to image
        g2d.drawImage(shopPic, 0, 0, null);

        ImageIcon iii = new ImageIcon(this.getClass().getResource("/coin.png"));   //assigns image icon based on given url
        shopPic = iii.getImage();  //ImageIcon method to assign image icon from ii object to image
        g2d.drawImage(shopPic, 20, 300, null);

        Font fontCoin = loadFont();
        fontCoin = fontCoin.deriveFont(Font.BOLD, 40);

        int numCoins = Game.getCoins();

        Fonts.drawString(g, fontCoin,
                Color.WHITE, " : " + numCoins, 30 + 35, 300 + 50);
        Font fontPrice = loadFont();
        fontPrice = fontPrice.deriveFont(Font.BOLD, 25);

        Fonts.drawString(g, fontPrice,
                Color.WHITE, "Shield's price:\n 5", 10, 200);


        // drawing The title "THE QUITE KID"
        //Font f = new Font("");


        Font fontBig = loadFont();
        fontBig = fontBig.deriveFont(Font.BOLD, 80);

        Fonts.drawString(g, fontBig,
                Color.WHITE, " \"THE SHOP\" ", 87);
        for (int i = 0; i < options.length; i++) {
            if (i == currentSelection)
                options[i].setSelected(true);
            else
                options[i].setSelected(false);
            options[i].render(g);
        }

        if (currentSelection == options.length) {
            backToMenu.setSelected(true);
        } else
            backToMenu.setSelected(false);
        backToMenu.render(g);
    }

}
