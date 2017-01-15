import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static javax.swing.SwingUtilities.invokeLater;

public class Lab4 extends JFrame{
    public static final int START_RADIUS = 30;
    private static final int FRAME_HEIGHT = 760;
    private static final int FRAME_WIDTH = 760;
    private JPanel yRaddioPanel;
    private JPanel dataPanel;
    private JRadioButton yPolinom;
    private JRadioButton ySin;
    private JRadioButton yE;
    private JRadioButton yClear;
    private JLabel yLabel;

    double powerE;
    private DrawPanel drawPanel;
    List<Double> coefficients;
    List<Dot> dots;

    public Lab4() {
        frameSettings();
        addDrawPanel();
        addRadioButtonY();

        dataPanel = new JPanel(new GridLayout(0, 4));
        dataPanel.add(yRaddioPanel);
        add(drawPanel, BorderLayout.CENTER);
        add(dataPanel, BorderLayout.NORTH);
    }

    public void addDrawPanel() {
        drawPanel = new DrawPanel(START_RADIUS, coefficients);

    }
    public void draw() {
        dots = DrawPanel.miln(0.001, 0, 0.7, 1, 0.1);
        double[] x = new double[dots.size()];
        double[] y = new double[dots.size()];
        for(int i = 0; i < dots.size(); i++){
            x[i] = dots.get(i).getX();
            y[i] = dots.get(i).getY();
        }
        drawPanel.drawODU(drawPanel.getGraphics(), x[0], x, y);
    }
    private void frameSettings(){
        setTitle("Lab4");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLayout(new BorderLayout());
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public void addRadioButtonY() {
        yLabel = new JLabel("Y");
        yRaddioPanel = new JPanel(new GridLayout(2, 0));
        ButtonGroup yGroup = new ButtonGroup();
        yClear = new JRadioButton("Clear");
        yPolinom = new JRadioButton("Poli");
        ySin = new JRadioButton("Sin");
        yE = new JRadioButton("e");

        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JRadioButton) {
                    JRadioButton radioButton = (JRadioButton) e.getSource();
                    if (radioButton.isSelected()) {
                        if (radioButton == yClear) {
                            repaint();
                        }
                        else if (radioButton == yPolinom) {
                            draw();
//                            drawPanel.drawSecondFunction(drawPanel.getGraphics(), -100, 100, coefficients);
                        }
                    }
                }
            }
        };

        yClear.setSelected(true);

        yClear.addActionListener(actionListener);
        yPolinom.addActionListener(actionListener);
        ySin.addActionListener(actionListener);
        yE.addActionListener(actionListener);

        yGroup.add(yClear);
        yGroup.add(yPolinom);
        yGroup.add(ySin);
        yGroup.add(yE);


        yRaddioPanel.add(yLabel);
        yRaddioPanel.add(yClear);
        yRaddioPanel.add(yPolinom);
        yRaddioPanel.add(ySin);
        yRaddioPanel.add(yE);
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
}
