import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
    private static final int FRAME_HEIGHT = 580;
    private static final int FRAME_WIDTH = 500;

    int R = 10;
    Form startForm = new Form(R);
    private List<Dot> puntos = new ArrayList<>();
    private List<Dot> targets = new ArrayList<>();
    Dot mainDot = new Dot(0, 0);

    private JPanel dataPanel;
    private JPanel xListPanel;
    private JPanel yRaddioPanel;
    private JPanel rSliderPanel;
    private DrawPanel drawPanel;

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

    private JPanel  buttonPanel;
    private JButton clearButton;
    private JButton drawButton;

    List<Thread> threadList = new ArrayList<>();
    List<DotAnimation> dotAnimations = new ArrayList<>();

    public Lab4() {
        frameSettings();
        addListX();
        addRadioButtonY();
        addSliderR();
        addTextArea();
        addDrawPanel();
        addButtonPanel();

        dataPanel = new JPanel(new GridLayout(0, 4));
        dataPanel.add(xListPanel);
        dataPanel.add(yRaddioPanel);
        dataPanel.add(nowDot);
        dataPanel.add(buttonPanel);

        add(rSliderPanel, BorderLayout.WEST);
        add(dataPanel, BorderLayout.SOUTH);
        add(drawPanel, BorderLayout.CENTER);
    }

    public void addButtonPanel() {
        buttonPanel = new JPanel(new GridLayout(2,0));
        addDrawButton();
        addClearButton();
    }

    public void addClearButton() {
        clearButton = new JButton("CLEAR");

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
                puntos.clear();
            }
        };

        buttonPanel.add(clearButton);
        clearButton.addActionListener(actionListener);
    }

    public void addDrawButton() {
        drawButton = new JButton("DRAW");
        ActionListener actionListener = e -> {
           // drawPanel.paint(drawPanel.getGraphics());
            puntos.add(mainDot);
            nowDot.setText(mainDot.toString());
            if(startForm.isContain(mainDot)) {
                targets.add(mainDot);
//                DotAnimation nextDot = new DotAnimation(drawPanel.getGraphics(), drawPanel, mainDot);
//                Thread animationThread = new Thread(nextDot);
//                animationThread.start();
                drawPanel.startAnimation(drawPanel.getGraphics());
            }
            showDots(drawPanel.getGraphics(), puntos, startForm, R);
            showDots(drawPanel.getGraphics(), targets, startForm, R);
        };

        buttonPanel.add(drawButton);
        drawButton.addActionListener(actionListener);
    }

    public void addListX() {
        xListPanel = new JPanel(new GridLayout(0, 1));
        int[] x = new int[]{0, 4, -2, 10, 5};
        xList = new JList<>((new String[]{"x = 0", "x = 4", "x = -2", "x = 10", "x = 5"}));

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
        rSlider = new JSlider(JSlider.VERTICAL, 0, 50, 5);
        rLabel = new JLabel("R");

        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                drawPanel.paint(drawPanel.getGraphics());
                R = ((JSlider)e.getSource()).getValue();
                startForm = new Form(R);
                for(Dot dots:puntos) {
                    if(startForm.isContain(dots)) {
//                        DotAnimation nextDot = new DotAnimation(drawPanel.getGraphics(), drawPanel, dots);
//                        Thread animationThread = new Thread(nextDot);
//                        animationThread.start();
                        drawPanel.startAnimation(drawPanel.getGraphics());
                        break;
                    }
                }
                showDots(drawPanel.getGraphics(), puntos, startForm, R);
            }
        };

        rSlider.addChangeListener(changeListener);
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

        y0.setSelected(true);

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
        nowDot.setText(mainDot.toString());
    }

    public void addDrawPanel() {
        drawPanel = new DrawPanel();
        drawPanel.addMouseListener(new DrawMouseListener());
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

    public double toDecartX(double x, double R) { return (x - DrawPanel.CENTER_X) * R / DrawPanel.START_RADIUS;}

    public double toDecartY(double y, double R) {
        return (-y + DrawPanel.CENTER_Y) * R / DrawPanel.START_RADIUS;
    }

    public void showDots(Graphics g, List<Dot> dots, Form form, int R) {
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

    private class DrawMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            Graphics g;
            startForm.setR(R);
            g = ((DrawPanel) e.getSource()).getGraphics();
            Dot dots = new Dot(toDecartX(e.getX(), R), toDecartY(e.getY(), R));
            if(!puntos.contains(dots)) {
                puntos.add(dots);
                showDots(g, puntos, startForm, R);
                nowDot.setText(dots.toString());
            }
        }
    }
}

