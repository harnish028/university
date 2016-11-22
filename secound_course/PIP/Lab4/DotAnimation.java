import java.awt.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DotAnimation implements Runnable {

    Graphics g;
    DrawPanel drawPanel;
    Form form;
    Dot dot;
    int radius;

    public DotAnimation(Graphics g, Form form, Dot dot, int radius){
        this.g = g;
        this.radius = radius;
        this.dot = dot;
        this.form = form;
        System.out.println("JOPA");
    }
    @Override
    public void run() {
        if(form.isContain(dot)) {
            System.out.println("POPAL");
            g.setColor(Color.BLACK);
            g.fillOval((int)dot.getX() + 200, (int)dot.getY() + 200, (int) (radius / 20), (int) (radius / 20));
        } else {
            System.out.println("ne popal");
        }

        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            Logger.getLogger(DotAnimation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
