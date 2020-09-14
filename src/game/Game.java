package game;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.event.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import assets.*;
import menus.*;
import menus.Menu;

/**
 * This class contains everything that makes the game work, such as drawing graphics, checking objects collision and updating objects position.
 *
 * @see <a href="https://docs.oracle.com/javase/7/docs/api/javax/swing/JPanel.html">JPanel</a>
 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/awt/event/ActionListener.html">ActionListener</a>
 */

public class Game extends JPanel implements ActionListener {


    /**
     * A static value of game's window width
     */
    private static final int windowWidth = 1024;  //window's width
    /**
     * A static value of game's window height
     */
    private static final int windowHeight = 720;  //window's height
    /**
     * Variable used as time needed to complete the level
     */
    private int levelTime;  //default time for level, will be multiplied by levelNum
    /**
     * Variable used to determine the number of selected level, also used as multiplier to levelTime,
     * as to achieve different times need for completions of each level
     */
    private int levelNum;   //number of chosen level
    /**
     * An object created using Player class
     *
     * @see Player
     */
    private Player player;  //player object
    /**
     * An object created using Shooter class
     *
     * @see Shooter
     */
    private Shooter shooter;    //shooter object
    /**
     * Variable used to determine in which part of the game the user is currently in
     * 0-Menu, 1-Game, 2-Game-Over, 3-Congratulation screen, 4-Pause game
     */
    private int gameState = 0;  //0-game menu, 1-game, 2-game over screen, 3-congrats screen
    /**
     * ArrayList of all the sprites visible on the screen other then Shooter and Player
     *
     * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html">ArrayList</a>
     */
    private ArrayList<Sprite> sprites;
    /**
     * Swing timer used as internal clock for the game
     *
     * @see <a href="https://docs.oracle.com/javase/7/docs/api/javax/swing/Timer.html">Timer</a>
     */
    private Timer mainTimer;    //main timer for the game
    /**
     * Variable holding number of lives the player has left
     */
    private int livesNum;
    /**
     * Variable used to count seconds based on mainTimer
     */
    private int levelTimer;
    /**
     * Variable used to create new obstacles, based on mainTimer and spritesRandomTimer
     *
     * @see Obstacle
     */
    private int obstacleTimer;
    /**
     * Variable used to create new danger sign and bullet, based on mainTimer and shooterRandomTimer
     *
     * @see Danger
     * @see Bullet
     */
    private int shooterTimer;
    /**
     * Boolean used to pause the timers if needed
     */
    private boolean timersOn;
    /**
     * Variable used to determine at random when new danger sign and bullet is created
     */
    private int shooterRandomTimer;
    /**
     * Variable used to determine at random when new {@link Obstacle} is created
     */
    private int spritesRandomTimer;
    /**
     * Variable used to determine at random when new {@link Powerup} is created
     *
     * @see Powerup
     */
    private int powerupRandomTimer;
    /**
     * Boolean used to check if music is turned on or off
     *
     * @see Music
     */
    private static boolean musicOption;
    /**
     * Boolean used to check if sounds are turned on or off
     *
     * @see Sound
     */
    private static boolean soundOption;
    /**
     * An object created using Menu class
     *
     * @see Menu
     */
    private menus.Menu menu; // menu object
    /**
     * An object created using CongratsScreen class
     *
     * @see CongratsScreen
     */
    private CongratsScreen congratsScreen; // congrats screen object
    /**
     * An object created using Shop class
     *
     * @see Shop
     */
    private Shop shop; // shop object
    /**
     * An object created using Options class
     *
     * @see Options
     */
    private Options options; // options object
    /**
     * An object created using Pause class
     *
     * @see Pause
     */
    private Pause pause; // pause object
    /**
     * An object created using GameOverScreen class
     *
     * @see GameOverScreen
     */
    private GameOverScreen gameOverScreen; //game over screen object
    /**
     * An object created using Levels class
     *
     * @see Levels
     */
    private Levels levels; // levels object
    /**
     * An object created using Sound class
     *
     * @see Sound
     */
    private Sound sounds;
    /**
     * An object created using Music class
     *
     * @see Music
     */
    private Music music;
    /**
     * An object created using Tutorial class
     */
    private Tutorial tutorial;
    /**
     * A variable used to identify in which sub menu the user is currently in.
     * 0-Main menu, 1-Level selection, 2-Options, 3-Shop
     */
    private int menuState = 0;
    /**
     * A variable used to determinate how many levels are unlocked
     */
    private static int levelsUnlocked = 1;
    /**
     * A variable used to store player's coins
     */
    private static int coins = 5;
    /**
     * Boolean used to determinate if the shield has been bought in shop.
     */
    private boolean shieldOn = false;

    /**
     * Game class's constructor
     */
    public Game() {
        initGame();
    }

    /**
     * This method initializes the game for the first time when it's started.
     * Other than methods from this class it also uses methods from JPanel and ActionListener.
     * It creates new {@link TAdapter} so the game can detect keys pressed by user.
     * Creates new {@link Music} and {@link Sound} class objects and loads save file. It also starts the {@link #mainTimer} and creates new Menu.
     */
    private void initGame() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(new Color(45, 138, 78));  //when we have our background ready it will be deleted
        setDoubleBuffered(true);
        musicOption = true;
        soundOption = true;
        sounds = new Sound();

        music = new Music();
        loadSave();


        //creates timer with delay of our DELAY variable,
        // It's the main Timer which should be created separately at the top of the program
        // (Other timers regarding sprites are created at initTime() method
        //delay between each frame
        mainTimer = new Timer(33, this);
        mainTimer.start();  //starts mainTimer

        newMenu();
    }


    /**
     * This method creates every object required for the game itself to run, such as pause, congrats and gameover screens,
     * new Player and Shooter objects and also new ArrayList in which all the other sprites will be stored.
     */
    private void newGame() {
        pause = new Pause();

        congratsScreen = new CongratsScreen(); //congrats screen object created

        gameOverScreen = new GameOverScreen(); // game over screen object created

        levelTime *= levelNum;  //this will make levelTime scale up incredibly fast //time needed to complete level, levelNum will be decided in the menu

        player = new Player();  //creates new Player object

        shooter = new Shooter(0, player.getY() - 55);    //creates new Shooter object on the edge of the screen and the same floor level as player

        sprites = new ArrayList<>(11);
        for (int i = 0; i < 11; i++) {
            sprites.add(null);
        }
        initLives();
        sprites.set(4, new Background(0, new Random().nextInt(5) + 1));
        sprites.set(5, new Background(0, new Random().nextInt(5) + 1));

    }

    /**
     * This method creates every object required for the menu to function.
     */
    private void newMenu() {
        menu = new Menu(); // menu object from Menu class is created

        shop = new Shop();

        options = new Options();

        levels = new Levels(); // levels object

        tutorial = new Tutorial();
    }

    /**
     * This method nullifies all the objects required to run the game, it's used when going back to menu after playing the game.
     * It's purpose is to save memory on computer by getting rid of things that are not in use.
     */
    private void closeGame() {
        pause = null;

        congratsScreen = null;

        gameOverScreen = null;

        levelTime = 60;

        player = null;

        shooter = null;

        sprites = null;
    }

    /**
     * This method nullifies all the object required for menu to function, it's used when starting the game from menu.
     * It's purpose is to save memory on computer by getting rid of things that are not in use.
     */
    private void closeMenu() {
        menu = null;

        shop = null;

        options = null;

        levels = null;

        tutorial = null;
    }

    /**
     * Overridden method from JPanel, it's used to paint all images that are on the screen, based on current gameState.
     *
     * @param g a parameter provided by internals of the GUI system
     * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/awt/Graphics.html">Graphics</a>
     */
    @Override
    public void paintComponent(Graphics g) {     //draws everything on screen
        super.paintComponent(g);

        if (gameState == 0) {

            if (menuState == 0) {
                menu.render(g);
            } else if (menuState == 1) {
                levels.render(g);
            } else if (menuState == 2) {
                tutorial.render(g);
            } else if (menuState == 3) {
                options.render(g);
            } else if (menuState == 4) {
                shop.render(g);
            }
        } else if (gameState == 1) {

            drawGame(g);
        } else if (gameState == 2) {

            drawGameOverScreen(g);
        } else if (gameState == 3) {

            congratsScreen.render(g);
        } else if (gameState == 4) {
            pause.render(g);
        }


        Toolkit.getDefaultToolkit().sync();
    }


    /**
     * This method draws all images used while playing the game,
     * such as floor of the level, background, player, shooter, obstacles, lives, danger sign, bullet, powerups, and game timer.
     *
     * @param g parameter taken from paintComponent method
     */
    private void drawGame(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g.setColor(new Color(127, 127, 127));
        g.fillRect(0, windowHeight - 164, windowWidth, 164);

        //draw Background
        g2d.drawImage(sprites.get(livesNum + 1).getImage(), sprites.get(livesNum + 1).getX(), sprites.get(livesNum + 1).getY(), this);
        g2d.drawImage(sprites.get(livesNum + 2).getImage(), sprites.get(livesNum + 2).getX(), sprites.get(livesNum + 2).getY(), this);

        //draw player
        g2d.drawImage(player.getImage(), player.getX(), player.getY(), this);

        //draw shooter
        g2d.drawImage(shooter.getImage(), shooter.getX(), shooter.getY(), this);

        //draw obstacles
        if (sprites.get(livesNum + 4) != null)
            g2d.drawImage(sprites.get(livesNum + 4).getImage(), sprites.get(livesNum + 4).getX(), sprites.get(livesNum + 4).getY(), this);
        if (sprites.get(livesNum + 5) != null)
            g2d.drawImage(sprites.get(livesNum + 5).getImage(), sprites.get(livesNum + 5).getX(), sprites.get(livesNum + 5).getY(), this);

        //draw lives
        for (int i = 0; i < livesNum; i++) {
            g2d.drawImage(sprites.get(i).getImage(), sprites.get(i).getX(), sprites.get(i).getY(), this);
            if (shieldOn && i == 2) {
                g2d.drawImage(sprites.get(livesNum).getImage(), sprites.get(livesNum).getX(), sprites.get(livesNum).getY(), this);
            }
        }
        if (sprites.get(livesNum + 3) != null) {
            g2d.drawImage(sprites.get(livesNum + 3).getImage(), sprites.get(livesNum + 3).getX(), sprites.get(livesNum + 3).getY(), this);
        }

        if (sprites.get(livesNum + 6) != null) {
            g2d.drawImage(sprites.get(livesNum + 6).getImage(), sprites.get(livesNum + 6).getX(), sprites.get(livesNum + 6).getY(), this);
        }

        //draw bullets/danger
        if (sprites.get(livesNum + 7) != null)
            g2d.drawImage(sprites.get(livesNum + 7).getImage(), sprites.get(livesNum + 7).getX(), sprites.get(livesNum + 7).getY(), this);


        //draw timer
        g.setColor(Color.black);
        Font small = pause.loadFont();  //creating new font object
        small = small.deriveFont(Font.BOLD, 30);
        g.setFont(small);
        String msg = "Time: " + String.valueOf((levelTime / 60)) + ":" + String.valueOf((levelTime % 60));   //time in format M:SS
        FontMetrics fm = getFontMetrics(small);


        g.drawString(msg, 0, fm.getHeight());
    }

    /**
     * This method draws game over screen, it's here because the game over screen shows how much time was left till the end of the level
     * and this requires easy access to levelTime variable.
     *
     * @param g parameter taken from paintComponent method
     */
    private void drawGameOverScreen(Graphics g) {  //draws game over screen

        gameOverScreen.render(g);
        int tempTime = levelTime;
        String msg = "Time: " + String.valueOf((tempTime / 60)) + ":" + String.valueOf((tempTime % 60));
        FontMetrics fm = g.getFontMetrics();
        int width = fm.stringWidth(msg) - msg.length();
        g.drawString(msg, width, 200);
    }


    /**
     * Overridden method from ActionListener, depending on the gameState, it will do different tasks for each tick of the mainTimer.
     * It also calls for repainting of the images on the screen.
     */

    @Override
    public void actionPerformed(ActionEvent e) {


        if (gameState == 0) {
            menu.tick();
            if (menuState == 1) {
                levels.tick();
            } else if (menuState == 2) {
                tutorial.tick();
            } else if (menuState == 3) {
                options.tick();
            } else if (menuState == 4) {
                shop.tick();
            }
        } else if (gameState == 1) {

            timers();
            updateSprites();
            checkCollisions();
            checkTimer();

        } else if (gameState == 2) {

            gameOverScreen.tick();
            music.music(false);
            saveGame();

        } else if (gameState == 3) {
            if (levelsUnlocked == levelNum) {
                levelsUnlocked++;
            }
            saveGame();
            congratsScreen.tick();
            music.music(false);
        } else if (gameState == 4) {
            pause.tick();
        }
        repaint();

    }

    /**
     * This method is used to update the position of all the sprites and background on the screen and delete them if they are no longer visible.
     */
    private void updateSprites() {


        //updates player position and animation frame
        player.move();
        player.animationFrame();
        shooter.animationFrame();

        //updates position of background


        if (sprites.get(livesNum + 2).getX() == 0) {
            sprites.remove(livesNum + 1);
            sprites.add(livesNum + 2, new Background(windowWidth - ((Background) sprites.get(livesNum + 1)).getSpeed(), new Random().nextInt(5) + 1));
        }
        ((Background) sprites.get(livesNum + 1)).move();
        ((Background) sprites.get(livesNum + 2)).move();


        //updates position of obstacles and deletes ones that are out of screen

        if (sprites.get(livesNum + 4) != null) {
            if (sprites.get(livesNum + 4).isVisible()) {
                ((Obstacle) sprites.get(livesNum + 4)).move();
            } else {
                sprites.set(livesNum + 4, null);
            }
        }
        if (sprites.get(livesNum + 5) != null) {
            if (sprites.get(livesNum + 5).isVisible()) {
                ((Obstacle) sprites.get(livesNum + 5)).move();
            } else {
                sprites.set(livesNum + 5, null);
            }
        }

        if (sprites.get(livesNum + 6) != null) {
            if (sprites.get(livesNum + 6).isVisible()) {
                ((Powerup) sprites.get(livesNum + 6)).move();
            } else {
                sprites.set(livesNum + 6, null);
            }
        }
        if (sprites.get(livesNum + 3) != null) {
            if (sprites.get(livesNum + 3).isVisible()) {
                ((Coin) sprites.get(livesNum + 3)).move();
            } else {
                sprites.set(livesNum + 3, null);
            }
        }


        //updates position of bullets and deletes ones that are out of screen
        if (sprites.get(livesNum + 7) instanceof Bullet) {

            if (sprites.get(livesNum + 7).isVisible()) {

                ((Bullet) sprites.get(livesNum + 7)).move();
            } else {
                sprites.set(livesNum + 7, null);
            }
        }


    }

    /**
     * This method checks if Player or Shooter objects touched any of Obstacle, Powerup or Bullet sprites.
     * In case Shooter comes into contact with Obstacle, the Obstacle is removed and in case it comes into contact with Player, the game ends.
     * If Player comes into contact with {@link Player#obstacleHit() Obstacle} or {@link Player#powerup() Powerup}, appropriate method from Player class will be called and the object will be removed.
     * If Player comes int contact with Bullet, one of the lives will be removed and the Bullet will be removed. Once there is no more lives the game will end.
     */
    private void checkCollisions() {


        Rectangle rP = player.getBounds(); //creates hit box for player

        //checks collision with shooter
        Rectangle rS = shooter.getBounds(); //creates hit box for shooter

        if (rP.intersects(rS)) {  //if player touches the shooter

            sounds.damage(false);
            gameState = 2;    //game over screen
            sounds.gameover(true);
        }

        //checks collision with obstacles

        if (sprites.get(livesNum + 4) != null) {
            Rectangle rO = sprites.get(livesNum + 4).getBounds();    //creates hit box for obstacle

            if (rP.intersects(rO)) {   //if player touches obstacle
                player.obstacleHit();
                sprites.set(livesNum + 4, null);
                sounds.damage(true);
            }


        }
        if (sprites.get(livesNum + 5) != null) {
            Rectangle rO = sprites.get(livesNum + 5).getBounds();    //creates hit box for obstacle

            if (rP.intersects(rO)) {   //if player touches obstacle
                player.obstacleHit();
                sprites.set(livesNum + 5, null);
                sounds.damage(true);
            }


        }
        if (sprites.get(livesNum + 6) != null) {
            Rectangle rPow = sprites.get(livesNum + 6).getBounds();

            if (rP.intersects(rPow)) {
                player.powerup();
                sprites.set(livesNum + 6, null);
            }
        }
        if (sprites.get(livesNum + 3) != null) {
            Rectangle rPow = sprites.get(livesNum + 3).getBounds();

            if (rP.intersects(rPow)) {
                coins++;
                sprites.set(livesNum + 3, null);
            }
        }


        //checks collision with bullet
        if (sprites.get(livesNum + 7) != null) {

            Rectangle rB = sprites.get(livesNum + 7).getBounds();  //creates hit box for bullet

            if (rP.intersects(rB)) {  //if player touches bullet

                sprites.set(livesNum + 7, null);
                if (shieldOn) {
                    sprites.set(livesNum, null);
                    shieldOn = false;
                } else {
                    sprites.remove(livesNum - 1);  //removes one life
                    sounds.damage(true);
                    livesNum--;
                }

                if (livesNum == 0) {  //checks if player is still alive
                    gameState = 2;    //game over screen
                    sounds.damage(false);
                    sounds.gameover(true);
                }

            }
        }


    }

    /**
     * This method checks if the levelTime is equal or less then 0, if it is the level ends and the user is moved to Congrats Screen
     */
    private void checkTimer() {
        if (levelTime <= 0) {
            gameState = 3;
            sounds.congrats(true);
            stopTimers();
        }
    }

    /**
     * This method is used while creating new game objects to create 3 Heart objects(or 4 if user bought a shield in the shop) and add them do sprites ArrayList
     */
    private void initLives() {   //creates 3 heart objects and adds them to lives list

        for (int i = 0; i < 3; i++) {
            sprites.set(i, new Heart(windowWidth - (55 + (i * 55)), 0, false));
            if (shieldOn && i == 2) {
                sprites.set(i + 1, new Heart(windowWidth - (55 + ((i + 1) * 55)), 0, true));
            }
        }
        livesNum = 3;
    }


    /**
     * This method sets levelTime based on level selected by the user and creates random numbers for all random timers.
     */
    private void initTimers() {   //starts all timers except main timer which is started at the first steps

        levelTime = 20;  //default time for level, will be multiplied by levelNum
        levelTime *= levelNum;

        timersOn = true;

        shooterRandomTimer = new Random().nextInt(210) + 90;

        spritesRandomTimer = new Random().nextInt(40) + 50;

        powerupRandomTimer = new Random().nextInt(5) + 3;


        levelTimer = 0;
        obstacleTimer = 0;
        shooterTimer = 0;


    }

    /**
     * This method creates new {@link Obstacle Obstacles}, {@link Powerup Powerups}, {@link Coin Coins}, shoots the {@link Bullet} at the {@link Player} and counts time passed, based on {@link #mainTimer}.
     */
    private void timers() {

        if (timersOn) {
            levelTimer += 1;
            obstacleTimer += 1;
            shooterTimer += 1;
            if (obstacleTimer == spritesRandomTimer) {
                if (sprites.get(livesNum + 4) == null)
                    sprites.set(livesNum + 4, new Obstacle(windowWidth, windowHeight, new Random().nextInt(4) + 1));
                else
                    sprites.set(livesNum + 5, new Obstacle(windowWidth, windowHeight, new Random().nextInt(4) + 1));
                obstacleTimer = 0;
                spritesRandomTimer = new Random().nextInt(40) + 50;
                powerupRandomTimer--;
                if (powerupRandomTimer == 0) {
                    powerupRandomTimer = new Random().nextInt(5) + 3;
                    sprites.set(livesNum + 6, new Powerup(windowWidth + 100, windowHeight, new Random().nextInt(3) + 1));
                } else if (new Random().nextInt(8) + 1 <= 2) {
                    sprites.set(livesNum + 3, new Coin(windowWidth + 100 + new Random().nextInt(400), windowHeight - (new Random().nextInt(250))));
                }
            }
            if (shooterTimer == shooterRandomTimer) {
                sprites.set(livesNum + 7, new Danger(35, shooter.getY() - 15));    //ads danger icon above the shooter
                sounds.danger(true);      //plays danger sounds
            }
            if (shooterTimer == shooterRandomTimer + 30) {
                sprites.set(livesNum + 7, new Bullet(shooter.getWidth(), windowHeight - 180));
                shooterRandomTimer = new Random().nextInt(210) + 90;
                shooterTimer = 0;
            }
            if (levelTimer == 30) {
                levelTime -= 1;
                levelTimer = 0;
            }
        }

    }

    /**
     * This method stops all timers.
     */
    private void stopTimers() {  //method that stops all timers

        if (gameState != 3)
            mainTimer.stop();

        timersOn = false;
    }


    /**
     * This method loads save from the saveFile.txt located in program's folder or creates the new one if there is no saveFile there.
     */
    private void loadSave() {
        File file = new File("./saveFile.txt");
        try {
            Scanner in = new Scanner(file);
            levelsUnlocked = in.nextInt();
            coins = in.nextInt();
        } catch (Exception e) {
            try {
                PrintWriter writer = new PrintWriter("saveFile.txt", "UTF-8");
                writer.println("1");
                writer.println("5");
                writer.close();
                levelsUnlocked = 1;

            } catch (Exception f) {
                System.out.println("FILE NOT FOUND");
            }
        }

    }

    /**
     * This method saves progress to saveFile.txt that is located in program's folder
     */
    private void saveGame() {
        try {
            PrintWriter writer = new PrintWriter("saveFile.txt", "UTF-8");
            writer.println(levelsUnlocked);
            writer.println(coins);
            writer.close();

        } catch (Exception e) {
            System.out.println("FILE NOT FOUND");
        }

    }

    /**
     * Static method used to give the value of windowWidth to other classes.
     *
     * @return windowWidth variable
     */
    public static int getWindowWidth() {
        return windowWidth;
    }

    /**
     * Static method used to give the value of windowHeight to other classes.
     *
     * @return windowHeight variable
     */
    public static int getWindowHeight() {
        return windowHeight;
    }

    /**
     * Static method used to give the value of levelsUnlocked variable to other classes.
     *
     * @return windowWidth variable
     */
    public static int getLevelsUnlocked() {
        return levelsUnlocked;
    }

    /**
     * Static method used to give the value of soundOption boolean to other classes.
     *
     * @return soundOption boolean
     */
    public static boolean getSoundOption() {
        return soundOption;
    }

    /**
     * Static method used to give the value of musicOption boolean to other classes.
     *
     * @return musicOption boolean
     */
    public static boolean getMusicOption() {
        return musicOption;
    }

    /**
     * Static method used to give the value of coins variable to other classes.
     *
     * @return coins variable
     */
    public static int getCoins() {
        return coins;
    }

    /**
     * This class is used to detect key inputs made by the user.
     *
     * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/awt/event/KeyAdapter.html">KeyAdapter</a>
     */
    private class TAdapter extends KeyAdapter {  //checks for key inputs

        /**
         * This method is used to detect when the key is released by user and act accordingly depending on gameState.
         *
         * @param e parameter provided by KeyAdapter class
         */
        @Override
        public void keyReleased(KeyEvent e) {

            if (gameState == 0) {
                if (menuState == 0) {
                    menu.keyReleased(e);
                } else if (menuState == 1) {
                    levels.keyReleased(e);
                } else if (menuState == 2) {
                    tutorial.keyReleased(e);
                } else if (menuState == 3) {
                    options.keyReleased(e);
                } else if (menuState == 4) {
                    shop.keyRelesed(e);
                }

            } else if (gameState == 1) {
                player.keyReleased(e);
            } else if (gameState == 2) {
                gameOverScreen.keyReleased(e);
            } else if (gameState == 3) {
                congratsScreen.keyReleased(e);
            } else if (gameState == 4) {
                pause.keyReleased(e);
            }


        }

        /**
         * This method is used to detect when the key is pressed by user and act accordingly depending on gameState.
         *
         * @param e parameter provided by KeyAdapter class
         */
        @Override
        public void keyPressed(KeyEvent e) {

            if (gameState == 0) {
                if (menuState == 0) {
                    menu.keyPressed(e);

                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                        if (menu.currentSelection == 0) {
                            menuState = 1;
                            levels.currentSelection = 0;
                            e.setKeyCode(KeyEvent.VK_Z);
                        } else if (menu.currentSelection == 1) {
                            menuState = 2;
                            e.setKeyCode(KeyEvent.VK_Z);
                            tutorial.currentSelection = 1;

                        } else if (menu.currentSelection == 2) { // OPTIONS
                            menuState = 3;
                            e.setKeyCode(KeyEvent.VK_Z);
                            options.currentSelection = 0;
                        } else if (menu.currentSelection == 3) {
                            menuState = 4;
                            e.setKeyCode(KeyEvent.VK_Z);
                            shop.currentSelection = 0;
                        } else if (menu.currentSelection == 4) { // CLICK ON EXIT
                            System.exit(1);
                        }
                    }
                } else if (menuState == 1) {
                    levels.keyPressed(e);
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        if (levels.currentSelection == 0) {
                            levelNum = levels.currentSelection + 1;
                        } else if (levels.currentSelection == 1) {
                            levelNum = levels.currentSelection + 1;
                        } else if (levels.currentSelection == 2) {
                            levelNum = levels.currentSelection + 1;
                        } else if (levels.currentSelection == 3) {

                            levelNum = levels.currentSelection + 1;
                        } else if (levels.currentSelection == 4) {

                            levelNum = levels.currentSelection + 1;
                        } else if (levels.currentSelection == 5) {

                            levelNum = levels.currentSelection + 1;
                        } else if (levels.currentSelection == 6) {

                            levelNum = levels.currentSelection + 1;
                        } else if (levels.currentSelection == 7) {
                            gameState = 1;
                        } else if (levels.currentSelection == 8) {
                            menuState = 0;
                        }

                        if (levels.currentSelection != 8) {
                            gameState = 1;
                            newGame();
                            closeMenu();
                            music.music(true);
                            initTimers();
                        }


                    }
                } else if (menuState == 2) {
                    tutorial.keyPressed(e);
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        if (tutorial.currentSelection == 1) {
                            menuState = 0;
                        }
                    }
                } else if (menuState == 3) {
                    options.keyPressed(e);
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                        if (options.currentSelection == 0) { // turn off background music
                            musicOption = !musicOption;
                            music.initMusic();
                        } else if (options.currentSelection == 1) { // turn off sound effects
                            soundOption = !soundOption;
                            sounds.initSounds();
                        } else if (options.currentSelection == 2) { // goes back
                            menuState = 0;
                        }
                    }
                } else if (menuState == 4) {
                    shop.keyPressed(e);
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                        if (shop.currentSelection == 0) {
                            if (coins >= 10 && !shieldOn) {
                                shieldOn = true;
                                coins -= 10;// The number of coins gets reduced by the price of one shield.
                            }
                        } else if (shop.currentSelection == 1) {
                            menuState = 0;
                        }
                    }

                }
            } else if (gameState == 1) {
                player.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    gameState = 4;
                    timersOn = false;
                    music.music(false);
                }
            } else if (gameState == 2) {
                gameOverScreen.keyPressed(e);

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                    if (gameOverScreen.currentSelection == 0) {
                        sounds.gameover(false);
                        gameState = 1;
                        music.music(true);
                        newGame();
                        initTimers();
                    } else if (gameOverScreen.currentSelection == 1) {
                        gameState = 0;
                        newMenu();
                        closeGame();
                    } else if (gameOverScreen.currentSelection == 2) {
                        System.exit(1);
                    }
                }
            } else if (gameState == 3) {
                congratsScreen.keyPressed(e);

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                    if (congratsScreen.currentSelection == 0) { // CLICK ON NextLevel
                        gameState = 1;
                        levelNum++;
                        newGame();
                        initTimers();
                        music.music(true);

                    } else if (congratsScreen.currentSelection == 1) { // CLICK ON Menu
                        gameState = 0;
                        menuState = 0;
                        newMenu();
                        closeGame();
                        menu.currentSelection = 0;
                        e.setKeyCode(KeyEvent.VK_Z);
                    }
                }

            } else if (gameState == 4) {
                pause.keyPressed(e);

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                    if (pause.currentSelection == 0) {
                        gameState = 1;
                        music.music(true);
                        timersOn = true;
                    } else if (pause.currentSelection == 1) {
                        gameState = 0;
                        menuState = 0;
                        newMenu();
                        menu.currentSelection = 0;
                        closeGame();
                        e.setKeyCode(KeyEvent.VK_Z);
                    } else if (pause.currentSelection == 2) {
                        System.exit(1);
                    }
                }
            }
        }
    }
}


