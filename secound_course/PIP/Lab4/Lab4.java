import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.SwingUtilities.invokeLater;

public class Lab4 extends JFrame {
    Graphics g;
    public static final int FRAME_WIDTH = 500;
    public static final int FRAME_HEIGHT = 580;
    Dot mainDot = new Dot(0, 0);
    List<Dot> dots = new ArrayList<>();
    private JPanel dataPanel;
    private JPanel xListPanel;
    private JPanel yRaddioPanel;
    private JPanel rSliderPanel;
    private DrawPanel drawPanel;
    private JButton drawDotButton;
    private JPanel forDrawPanel;


    private JTextArea nowDot;
    private JList<String> xList;

    private JLabel yLabel;
    private JRadioButton y0;
    private JRadioButton y1;
    private JRadioButton y2;
    private JRadioButton y3;
    private JRadioButton y4;
    private JLabel rLabel;
    private JSlider rSlider;

    public Lab4() {
        frameSettings();
        addListX();
        addRadioButtonY();
        addSliderR();
        addTextArea();
        addButton();
        addDrawPanel();

        dataPanel = new JPanel(new GridLayout(0, 4));
        dataPanel.add(xListPanel);
        dataPanel.add(yRaddioPanel);
        dataPanel.add(nowDot);
        dataPanel.add(drawDotButton);


        forDrawPanel = new JPanel();
        forDrawPanel.add(drawPanel);
        add(rSliderPanel, BorderLayout.WEST);
        add(dataPanel, BorderLayout.SOUTH);
        add(drawPanel, BorderLayout.CENTER);
        // add(forDrawPanel, BorderLayout.CENTER);

    }

    public void addListX() {
        xListPanel = new JPanel(new GridLayout(0, 1));
        int[] x = new int[]{0, 4, -2, 10, 5};
        xList = new JList<String>((new String[]{"x = 0", "x = 4", "x = -2", "x = 10", "x = 5"}));

        ListSelectionListener listSelectionListener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                mainDot.setX(x[xList.getSelectedIndex()]);
            }
        };

        xList.addListSelectionListener(listSelectionListener);
        xListPanel.add(xList);
    }

    public void addSliderR() {
        rSliderPanel = new JPanel(new GridLayout(0, 2));
        rSlider = new JSlider(JSlider.VERTICAL, 0, 30, 5);
        rLabel = new JLabel("R");

        rSliderPanel.add(rSlider);
        rSliderPanel.add(rLabel);
    }

    public void addRadioButtonY() {
        yLabel = new JLabel("Y");
        yRaddioPanel = new JPanel(new GridLayout(2, 0));
        ButtonGroup yGroup = new ButtonGroup();

        y0 = new JRadioButton("-3");
        y1 = new JRadioButton("0");
        y2 = new JRadioButton("5");
        y3 = new JRadioButton("-6");
        y4 = new JRadioButton("7");

        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JRadioButton) {
                    JRadioButton radioButton = (JRadioButton) e.getSource();
                    if (radioButton.isSelected()) {
                        if (radioButton == y0)
                            mainDot.setY(-3);
                        else if (radioButton == y1)
                            mainDot.setY(0);
                        else if (radioButton == y2)
                            mainDot.setY(5);
                        else if (radioButton == y3)
                            mainDot.setY(-6);
                        else if (radioButton == y4)
                            mainDot.setY(7);
                    }
                }
            }
        };

        y0.addActionListener(actionListener);
        y1.addActionListener(actionListener);
        y2.addActionListener(actionListener);
        y3.addActionListener(actionListener);
        y4.addActionListener(actionListener);

        yGroup.add(y0);
        yGroup.add(y1);
        yGroup.add(y2);
        yGroup.add(y3);
        yGroup.add(y4);

        yRaddioPanel.add(yLabel);
        yRaddioPanel.add(y0);
        yRaddioPanel.add(y1);
        yRaddioPanel.add(y2);
        yRaddioPanel.add(y3);
        yRaddioPanel.add(y4);

        //yRaddioPanel.setBorder(new EmptyBorder(MARGIN, MARGIN, 0, MARGIN));
    }

    public void addTextArea() {
        nowDot = new JTextArea();
    }

    public JTextArea getTextAr() {
        return nowDot;
    }

    public void addDrawPanel() {
        drawPanel = new DrawPanel();
    }

    public void addButton() {
        drawDotButton = new JButton("DRAW");
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                g = ((DrawPanel) e.getSource()).getGraphics();
                g.setColor(Color.BLACK);
                dots.add(mainDot);
            }
        };

        drawDotButton.addActionListener(actionListener);
    }

    public static void main(String[] args) {
        Lab4 mainFrame = new Lab4();
        invokeLater(new Runnable() {
            @Override
            public void run() {
                mainFrame.setVisible(true);
            }
        });
    }

    private void frameSettings() {
        setTitle("Lab4");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLayout(new BorderLayout());
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private class DrawPanel extends JPanel {

        public  final Color COLOR_BLACK = new Color(0, 0, 0);
        public  final Color LIGHT_GREEN = new Color(129, 192, 187);
        public  final Color COLOR_BROWN = new Color(150, 75, 0);
        public  Color areaColor = COLOR_BLACK;


        public static final int HEIGHT = 9;
        public static final int WEIGHT = 9;
        public  int CENTER_X;
        public  int CENTER_Y;
        public static final int START_RADIUS = 100;
        public static final int POINT_RADIUS = 5;
        public static final int MARGIN = 9;
        public JTextArea a = new JTextArea();
        private List<Dot> puntos = new ArrayList<Dot>();
        DotAnimation dotAnimation;
        Form start = new Form(10);

        public DrawPanel() {
            setBackground(LIGHT_GREEN);
            setSize(462, 462);
            addMouseListener(new DrawMouseListener());
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            int w = this.getWidth();
            int h = this.getHeight();

            CENTER_X = this.getWidth() >> 1;
            CENTER_Y = (this.getHeight() + 1) >> 1;

            drawCoordinate(g, h, w);
            drawHatching(g, h, w);
            drawArea(g, Color.BLUE);

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
            System.out.println(puntos);
        }

        private void drawRectangle(Graphics g, Color color) {
            g.fillRect(CENTER_X, CENTER_Y, START_RADIUS, START_RADIUS);
        }

        private void drawArc(Graphics g, Color color) {
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

        public double toDecartX(double x, double R) {
            return (x - CENTER_X) * R / DrawPanel.START_RADIUS;
        }

        public double toDecartY(double y, double R) {
            return (-y + CENTER_Y) * R / DrawPanel.START_RADIUS;
        }

        public void startAnimation(Graphics g, int radius, List<Dot> puntos) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        int r0 = 1;
                        int g0 = 0;
                        int b0 = 0;

                        int RED = r0;
                        int GREEN = g0;
                        int BLUE = b0;
                        int N = 50;

                        start.setR(radius);
                        for (Dot punto : puntos) {
                            System.out.println((int) punto.toPixelsX(10) - 5);
                            g.fillOval((int) punto.toPixelsX(10) - 2, (int) punto.toPixelsY(10) - 2, 5, 5);
                            if (start.isContain(punto)) {
                                g.setColor(Color.GREEN);
                                g.fillOval((int) punto.toPixelsX(10) - 2, (int) punto.toPixelsY(10) - 2, 5, 5);
                                for (int i = 0; i < 50; i++) {
                                    RED += (255 - r0) / N;
                                    GREEN += (255 - g0) / N;
                                    BLUE += (255 - b0) / N;
                                    drawArea(g, new Color(RED, GREEN, BLUE));
                                    repaint();
                                    Thread.sleep(1000 / 60);
                                }

                                for (int i = 0; i < 50; i++) {
                                    RED -= (255 - r0) / N;
                                    GREEN -= (255 - g0) / N;
                                    BLUE -= (255 - b0) / N;
                                    drawArea(g, new Color(RED, GREEN, BLUE));
                                    repaint();
                                    Thread.sleep(1000 / 60);
                                }

                            } else {
                                g.setColor(Color.RED);
                                g.fillOval((int) punto.toPixelsX(10) - 2, (int) punto.toPixelsY(10) - 2, 5, 5);
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        private class DrawMouseListener extends MouseAdapter {
            @Override
            public void mouseClicked(MouseEvent e) {
                Graphics g;
                //start.setR(radius);
                g = ((DrawPanel) e.getSource()).getGraphics();
                Dot dots = new Dot(toDecartX(e.getX(), 10), toDecartY(e.getY(), 10));
                System.out.println("Mouse Clicked: (" + e.getX() + ", " + e.getY() + ")");
                puntos.add(dots);
                ((DrawPanel) e.getSource()).startAnimation(g, 10, puntos);
                System.out.println(puntos);
                nowDot.setText(dots.toString());
            }
        }
    }
}

