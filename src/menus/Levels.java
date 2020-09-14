package menus;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.util.Objects;
import game.Game;

/**
 * This class is made for creating a possibility for user to choose level he wants to play
 * most parts are similar to the menu class
 * @see Menu
 */
public class Levels {

     /**
     *
     */
        private final Button backToMenu, endlessMode;
     /**
     * An array of several LevelButtons for the Levels class
     * @see LevelButton
     */
        private final LevelButton[] options;

     /**
     * The background image of the menu and lock image for blocked levels
     */
        private Image menuOne, lock;

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
    private Font loadFont(){
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
     * and initialize each member of array (which is a LevelButton object) with it special value.
     * The font also would be load by calling the loadFont method.
     * The font is to be used in the each button's constructor.
     * @see Fonts
     * @see LevelButton
     */
    public Levels() {
            options = new LevelButton[7];

            Font fontSmall= loadFont();
            fontSmall = Objects.requireNonNull(fontSmall).deriveFont(Font.BOLD, 70);
            Font fontBig = loadFont();
            fontBig = Objects.requireNonNull(fontBig).deriveFont(Font.BOLD, 80);

            endlessMode = new Button("Endless mode", 500,
                    fontSmall,
                    fontBig, Color.WHITE, Color.WHITE);

            backToMenu = new Button("Back to menu", 300 + 4 * 76,
                    fontSmall,
                    fontBig, Color.WHITE, Color.WHITE);


            options[0] = new LevelButton("1", 206, 260,
                        fontSmall,
                        fontBig, Color.GRAY, Color.WHITE);

                options[1] = new LevelButton("2", 206 * 2, 260,
                        fontSmall,
                        fontBig, Color.GRAY, Color.WHITE);

                options[2] = new LevelButton("3", 206 * 3, 260,
                        fontSmall,
                        fontBig, Color.GRAY, Color.WHITE);

                options[3] = new LevelButton("4", 206 * 4, 260,
                        fontSmall,
                        fontBig, Color.GRAY, Color.WHITE);

                options[4] = new LevelButton("5", 309, 360,
                        fontSmall,
                        fontBig, Color.GRAY, Color.WHITE);

                options[5] = new LevelButton("6", 309 + 206, 360,
                        fontSmall,
                        fontBig, Color.GRAY, Color.WHITE);

                options[6] = new LevelButton("7", 309 + 412, 360,
                        fontSmall,
                        fontBig, Color.GRAY, Color.WHITE);


            }

    /**
     * This method updates the Levels class.
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
                        currentSelection ++;
                        stopHere = true;
                }

            }

            if (currentSelection < 0) {
                currentSelection = options.length+1;
            }
            if (currentSelection > options.length+1) {
                currentSelection = 0;
            }
            if(currentSelection>=Game.getLevelsUnlocked() && currentSelection<options.length && xd==1){
                currentSelection=options.length;
            }
            if(currentSelection>=Game.getLevelsUnlocked() && currentSelection<options.length && xd==-1){
                currentSelection=Game.getLevelsUnlocked()-1;
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

            xd = 0;
            stopHere = false;
        }

    /**
     * In this method the program will render all the buttons in the options array, by using Graphics g as an input.
     * It also draw the title of the Levels class, background and two guns besides the selected button.
     * When we draw the buttons in by going through a for loop. Whenever it reaches currentSelection button the "selected"
     * parameter of that objects will be set to true
     * @see LevelButton
     * @param g a parameter provided by internals of the GUI system
     */
        public void render(Graphics g) { // This method draws the whole menu
            // (is called at the drawMenu method of Game.java)

            Graphics2D g2d = (Graphics2D) g;

            ImageIcon ii = new ImageIcon(this.getClass().getResource("/bboard.png"));   //assigns image icon based on given url
            menuOne = ii.getImage();  //ImageIcon method to assign image icon from ii object to image
            g2d.drawImage(menuOne, 0, 0, null);




            //  int tempTime = levelTime;
            //  msg = "Time: " +String.valueOf((tempTime/60))+":"+String.valueOf((tempTime%60));


            Font fontBig = loadFont();
            fontBig = Objects.requireNonNull(fontBig).deriveFont(Font.BOLD, 80);


            // Drawing the title OPTIONS
            Fonts.drawString(g, fontBig,
                    Color.WHITE, "Level selection", 100);


            // Rendering every Button, And implementing the "currentSelection"
            // to the button by changing the selected variable of that button
            for (int i = 0; i <= options.length+1; i++) {

                if (i<7&&i == currentSelection)
                    options[i].setSelected(true);
                else if(i<7)
                    options[i].setSelected(false);
                if(i<7)
                options[i].render(g);
            }
            if (currentSelection == options.length) {
                endlessMode.setSelected(true);
            }
                else
                    endlessMode.setSelected(false);
                endlessMode.render(g);

            if (currentSelection == options.length+1) {
                backToMenu.setSelected(true);
            }
            else
                backToMenu.setSelected(false);
            backToMenu.render(g);

            ImageIcon i1 = new ImageIcon((this.getClass().getResource("/lock.png")));
            lock = i1.getImage();
          //  g2d.drawImage(lock, 176, 190, null);
            switch (Game.getLevelsUnlocked()) {
                case 1:
                    g2d.drawImage(lock, 182 + 206, 190, null);
                case 2:
                    g2d.drawImage(lock, 188 + 2 * 206, 190, null);
                case 3:
                    g2d.drawImage(lock, 190 + 3 * 206, 190, null);
                case 4:
                    g2d.drawImage(lock, 286, 290, null);
                case 5:
                    g2d.drawImage(lock, 288 + 206, 290, null);
                case 6:
                    g2d.drawImage(lock, 290 + 206 * 2, 290, null);
                case 7:
            }
        }
    }
