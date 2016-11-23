import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DrawPanel extends JPanel {

    public  final Color COLOR_BLACK = new Color(0, 0, 0);
    public  final Color LIGHT_GREEN = new Color(129, 192, 187);
    public  Color areaColor = COLOR_BLACK;

    public static int CENTER_X;
    public static int CENTER_Y;
    public static final int START_RADIUS = 100;
    public static final int POINT_RADIUS = 5;
    public static final int MARGIN = 9;
    public List<Dot> dots = new ArrayList<>();
    public Form form = new Form(10);
    int r;

    public DrawPanel() {
        setBackground(LIGHT_GREEN);
        setSize(462, 462);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int w = this.getWidth();
        int h = this.getHeight();

        CENTER_X = this.getWidth() >> 1;
        CENTER_Y = (this.getHeight() + 1) >> 1;

        drawCoordinate(g, h, w);
        drawHatching(g, h, w);
        drawArea(g, areaColor);
        showDots(g, dots, form, r);
    }

    public void drawArea(Graphics g, Color color) {
        g.setColor(color);
        drawRectangle(g, color);
        drawTriangle(g, color);
        drawArc(g, color);
    }

    private void drawArrow(Graphics g, int h, int w) {
        g.drawLine(w / 2, 0, w / 2 - 5, 10);
        g.drawLine(w / 2, 0, w / 2 + 5, 10);
        g.drawLine(w, h / 2, w - 10, h / 2 + 5);
        g.drawLine(w, h / 2, w - 10, h / 2 - 5);
    }

    private void drawCoordinate(Graphics g, int h, int w) {
        g.setColor(Color.BLACK);
        g.drawLine(w / 2, 0, w / 2, h);
        g.drawString("Y", w / 2 + MARGIN, MARGIN);

        g.drawLine(0, h / 2, w, h / 2);
        g.drawString("X", w - MARGIN / 2, h / 2 + MARGIN);

        g.drawString("R", w / 2 + 5, h / 2 - START_RADIUS);
        g.drawString("R / 2", w / 2 + 5, h / 2 - START_RADIUS / 2);
        g.drawString("0", w / 2 - 7, h / 2 + 10);

        g.drawString("R", w / 2 + START_RADIUS + 2, h / 2 + 15);
        g.drawString("-R", w / 2 - START_RADIUS - 5, h / 2 + 15);
        g.drawString("-R / 2", w / 2 - START_RADIUS / 2 - 18, h / 2 + 15);

        g.drawString("-R", w / 2 - 15, h / 2 + START_RADIUS);
        g.drawString("-R / 2", w / 2 - 30, h / 2 + START_RADIUS / 2);

        drawArrow(g, h, w);
    }

    private void drawRectangle(Graphics g, Color color) {
        g.drawLine(CENTER_X, CENTER_Y, CENTER_X + START_RADIUS, CENTER_Y);
        g.fillRect(CENTER_X, CENTER_Y, START_RADIUS, START_RADIUS);
    }

    private void drawArc(Graphics g, Color color) {
        g.drawArc(CENTER_X - START_RADIUS / 2, CENTER_Y - START_RADIUS / 2, START_RADIUS, START_RADIUS, 0, 90);
        g.fillArc(CENTER_X - START_RADIUS / 2, CENTER_Y - START_RADIUS / 2, START_RADIUS, START_RADIUS, 0, 90);
    }

    private void drawTriangle(Graphics g, Color color) {
        int[] xCoordinate = {CENTER_X, CENTER_X - START_RADIUS, CENTER_X};
        int[] yCoordinate = {CENTER_Y - START_RADIUS, CENTER_Y, CENTER_Y};
        g.fillPolygon(xCoordinate, yCoordinate, 3);
    }

    public void drawHatching(Graphics g, int h, int w) {
        g.setColor(Color.WHITE);
        g.drawLine(w / 2 - 3, h / 2 - START_RADIUS, w / 2 + 3, h / 2 - START_RADIUS);
        g.drawLine(w / 2 - 3, h / 2 - START_RADIUS / 2, w / 2 + 3, h / 2 - START_RADIUS / 2);
        g.drawLine(w / 2 - START_RADIUS, h / 2 - 3, w / 2 - START_RADIUS, h / 2 + 3);
        g.drawLine(w / 2 - START_RADIUS / 2, h / 2 - 3, w / 2 - START_RADIUS / 2, h / 2 + 3);
        g.drawLine(w / 2 + START_RADIUS / 2, h / 2 - 3, w / 2 + START_RADIUS / 2, h / 2 + 3);
        g.drawLine(w / 2 + START_RADIUS, h / 2 - 3, w / 2 + START_RADIUS, h / 2 + 3);
        g.drawLine(w / 2 - 3, h / 2 + START_RADIUS, w / 2 + 3, h / 2 + START_RADIUS);
        g.drawLine(w / 2 - 3, h / 2 + START_RADIUS / 2, w / 2 + 3, h / 2 + START_RADIUS / 2);
    }

    public void showDots(Graphics g, java.util.List<Dot> dots, Form form, int R) {
        for(Dot dot : dots) {
            if(form.isContain(dot)) {
                g.setColor(Color.GREEN);
                g.fillOval((int) dot.toPixelsX(R) - 2, (int) dot.toPixelsY(R) - 2, DrawPanel.POINT_RADIUS, DrawPanel.POINT_RADIUS);
            } else {
                g.setColor(Color.RED);
                g.fillOval((int) dot.toPixelsX(R) - 2, (int) dot.toPixelsY(R) - 2, DrawPanel.POINT_RADIUS, DrawPanel.POINT_RADIUS);
            }
        }
    }

    public void startAnimation(Graphics g) {
        new Thread(new Runnable() {
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
                        areaColor = new Color(RED, GREEN, BLUE);
                        repaint();
                        // drawArea(g, new Color(RED, GREEN, BLUE));
                        Thread.sleep(1000 / 50);
                        //drawPanel.repaint();
                    }
                    for (int i = 0; i < N; i++) {
                        RED -= (255 - main_red) / N;
                        GREEN -= (255 - main_green) / N;
                        areaColor = new Color(RED, GREEN, BLUE);
                        repaint();
//                        drawArea(g, new Color(RED, GREEN, BLUE));
                        Thread.sleep(1000 / 50);
                        // drawPanel.repaint();
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(DotAnimation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
