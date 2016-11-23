import java.awt.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DotAnimation implements Runnable {
    public  final Color COLOR_BLACK = new Color(0, 0, 0);
    Graphics g;
    DrawPanel drawPanel;
    Dot dot;

    public DotAnimation(Graphics g, DrawPanel drawPanel, Dot dot){
        this.g = g;
        this.drawPanel = drawPanel;
        this.dot = dot;
        System.out.println("POSHEL");
    }
    @Override
    public void run() {
        try {
            int main_red = COLOR_BLACK.getRed();
            int main_green = COLOR_BLACK.getGreen();
            int main_blue = COLOR_BLACK.getBlue();

            int RED = main_red;
            int GREEN = main_green;
            int BLUE = main_blue;
            int N = 50;

            for (int i = 0; i < N; i++) {
                RED += (255 - main_red) / N;
                GREEN += (255 - main_green) / N;
                drawPanel.drawArea(g, new Color(RED, GREEN, BLUE));
                Thread.sleep(1000 / 50);
                //drawPanel.repaint();
            }
            for (int i = 0; i < N; i++) {
                RED -= (255 - main_red) / N;
                GREEN -= (255 - main_green) / N;
                drawPanel.drawArea(g, new Color(RED, GREEN, BLUE));
                Thread.sleep(1000 / 50);
               // drawPanel.repaint();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(DotAnimation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
