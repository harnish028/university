import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;


public class DrawPanel extends JPanel {
    public static final int START_RADIUS = 100;
    public static int CENTER_X;
    public static int CENTER_Y;
    public static final int POINT_RADIUS = 5;
    private static final int MARGIN = 9;
    private double R;
    List<Double> coefficients;

    public DrawPanel(double R, List<Double> coefficients) {
        Color LIGHT_GREEN = new Color(129, 192, 187);
        setBackground(LIGHT_GREEN);
        setSize(462, 462);
        this.coefficients = coefficients;
        this.R = R;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int w = this.getWidth();
        int h = this.getHeight();
        CENTER_X = this.getWidth() >> 1;
        CENTER_Y = (this.getHeight() + 1) >> 1;
        drawCoordinate(g, h, w);
    }
    public void drawCoordinate(Graphics g, int h, int w) {
        g.setColor(Color.BLACK);
        g.drawLine(w / 2, 0, w / 2, h);
        g.drawString("Y", w / 2 + MARGIN, MARGIN);

        g.drawLine(0, h / 2, w, h / 2);
        g.drawString("X", w - MARGIN / 2, h / 2 + MARGIN);

        g.drawString("0", w / 2 - 7, h / 2 + 10);
        drawArrow(g, h, w);
    }
    private void drawArrow(Graphics g, int h, int w) {
        g.drawLine(w / 2, 0, w / 2 - 5, 10);
        g.drawLine(w / 2, 0, w / 2 + 5, 10);
        g.drawLine(w, h / 2, w - 10, h / 2 + 5);
        g.drawLine(w, h / 2, w - 10, h / 2 - 5);
    }
    public void drawNodes(Graphics g, int n, double[] masX, double[] masY) {
        g.setColor(Color.RED);
        for(int i = 0; i < n; i++) {
            g.fillOval((int) new Dot(masX[i],masY[i]).toPixelsX(R) - 2,
                    (int) new Dot(masX[i],masY[i]).toPixelsY(R) - 2, DrawPanel.POINT_RADIUS, DrawPanel.POINT_RADIUS);
        }
    }
    public static double newton(double x, int n, double[] masX, double[] masY) {
        double step = Math.abs(masX[1] - masX[0]);
        //заполяем 1-ую строку х
        // 2-ую y
        double[][] mas = new double[n + 2][n];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0) {
                    mas[i][j] = masX[j];
                } else if (i == 1) {
                    mas[i][j] = masY[j];
                }
            }
        }

        //находим разности
        int m = n;
        for (int i = 2; i < n + 2; i++) {
            for (int j = 0; j < m - 1; j++) {
                mas[i][j] = mas[i - 1][j + 1] - mas[i - 1][j];
            }
            m--;
        }

        //разности y0
        double[] dy0 = new double[n + 1];
        for (int i = 0; i < n + 1; i++) {
            dy0[i] = mas[i + 1][0];
        }

        double res = dy0[0];
        double[] xn = new double[n];
        xn[0] = x - mas[0][0];

        //xn[0] = (x-x0)
        //формула ньютона xn1 = (x-x0)(x-x1)
        //xn[2] = xn1 * (x-x2)
        for (int i = 1; i < n; i++) {
            double ans = xn[i - 1] * (x - mas[0][i]);
            xn[i] = ans;
        }

        //
        int m1 = n + 1;
        int fact = 1;
        for (int i = 1; i < m1; i++) {
            fact = fact * i;
            res = res + (dy0[i] * xn[i - 1]) / (fact * Math.pow(step, i));
        }
        return res;
    }
    public static List<Dot> miln(double eps, double x0, double y0, double xn, double h){
        List<Dot> dots = new ArrayList<>();
        List<Double> solutions = new ArrayList<>();
        dots.add(new Dot(x0, y0));
        solutions.add(f1(x0,y0));
        int countOfNode = (int)(Math.abs(xn - x0) / h);
        for(int i = 1; i < 4; i++) {
            double k0 = h*f1(dots.get(i-1).getX(), dots.get(i-1).getY());
            double k1 = h*f1(dots.get(i-1).getX() + h /2, dots.get(i-1).getY() +  1/2 * k0);
            double k2 = h*f1(dots.get(i-1).getX() + h/2, dots.get(i-1).getY() + 1/2*k1);
            double k3 = h*f1(dots.get(i-1).getX() + h, dots.get(i-1).getY() + k2);

            dots.add(new Dot(dots.get(i-1).getX() + h, dots.get(i-1).getY() + (k0 + 2*k1 + 2 * k2 + k3)/6));
            solutions.add(f1(dots.get(i).getX(), dots.get(i).getY()));
        }

        boolean correctEps = false;
        double step = h;
        boolean needToContinue = false;

            double yPred = 0;
            double yCorr = 0;
            double fPred = 0;
            double xi = 0;
            for (int i = 4; i < countOfNode; i++) {
                xi = dots.get(i - 1).getX() + h;
                yPred = dots.get(i - 4).getY() + 4 * h / 3 * (2 * solutions.get(i - 3) - solutions.get(i - 2) + 2 * solutions.get(i - 1));
                fPred = f1(xi, yPred);
                yCorr = dots.get(i - 2).getY() + h / 3 * (solutions.get(i - 2) + 4 * solutions.get(i - 1) + fPred);
                /*if (Math.abs(yCorr - yPred) / 29 > eps) {
                    step /= 2;
                    countOfNode *= 2;
                    needToContinue = true;
                } else {*/
                    dots.add(new Dot(xi, yCorr));
                    solutions.add(f1(dots.get(i).getX(), dots.get(i).getY()));
            }
        return dots;
    }

    public static double f1(double x, double y){
        return x * x + 0.1 * y;
    }
    public  void drawODU(Graphics g, double x0, double[] masx, double[] masy){
        double start2 = x0;
        double step = Math.abs(masx[0] - masx[9])/ 100;
        for (int i = 0; i < 100; i++) {
            double x11 = start2;
            double x22 = start2 + step;
            double y3 = newton(x11, 10, masx, masy);
            double y4 = newton(x22, 10, masx, masy);
            start2 = x22;
            g.setColor(Color.BLACK);
            g.drawLine((int) (new Dot(x11, y3).toPixelsX(START_RADIUS)), (int) (new Dot(x11, y3).toPixelsY(START_RADIUS)),
                    (int) (new Dot(x22, y4).toPixelsX(START_RADIUS)), (int) (new Dot(x22, y4).toPixelsY(START_RADIUS)));
        }
    }
}
