package game;

import javax.swing.*;
import java.awt.*;

/**
 * Main class that starts the program and set few options for program's window
 */
public class Start extends JFrame {
    /**
     * Constructor of Start class.
     */
    public Start(){
        initWindow();
    }

    /**
     * This method initializes {@link Game} class, blocks option to change size of the program's window by the user,
     * sets size of the window based on static values in Game class:{@link Game#windowWidth} and {@link Game#windowHeight},
     * sets the name of the application that will be displayed at top of the window, sets the window to always start at the center of the screen
     * and enables the option to close the application by pressing X in top right corner.
     */
    private void initWindow(){
        add(new Game());    //creates new object Game where all of our game coding is
        setResizable(false); //windows size can't be changed
        setSize(Game.getWindowWidth(), Game.getWindowHeight()); //sets size of the window based on static variables in Game class
        setTitle("The Quiet Kid");  //sets the name of the application
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //the program will end when you close it with X
        setLocationRelativeTo(null);    //window with game will always start at the center of the screen

    }

    /**
     *Program starting method.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Start ex = new Start();
            ex.setVisible(true);
        });
    }
}
