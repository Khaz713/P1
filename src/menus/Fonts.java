package menus;
import java.awt.*;
import game.Game;


/**
 * This Class would provide some easier access to relevant
 * methods for editing texts. For example changing color, font or size and positions
 * There are 3 methodes in the class the only difference they have is in their positional input.
 * If we didn't had x it automatically would consider the middle of the screen as the x's position.
 * And the same for y. but we should at least have on of them.
  */
//
public class Fonts {

    public static void drawString(Graphics g, Font f, Color c, String text, int x, int y) {
        g.setColor(c);
        g.setFont(f);
        g.drawString(text, x, y);
    }

    public static void drawString(Graphics g, Font f, Color c, String text) {
        FontMetrics fm = g.getFontMetrics(f);
        int x = (Game.getWindowWidth() - fm.stringWidth(text)) / 2;
        int y = (Game.getWindowHeight() - fm.getHeight() / 2) + fm.getAscent();
        drawString(g, f, c, text, x, y);

    }

    public static void drawString(Graphics g, Font f, Color c, String text, int y) {
        FontMetrics fm = g.getFontMetrics(f);
        int x = (Game.getWindowWidth() - fm.stringWidth(text)) / 2;
        drawString(g, f, c, text, x, y);

    }
}
