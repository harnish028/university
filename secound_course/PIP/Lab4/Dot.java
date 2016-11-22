public class Dot {

    private double x;
    private double y;

    public Dot(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() { return y; }

    public void setY(int y) {
        this.y = y;
    }

    public double toPixelsX(double R) { return (x * DrawPanel.START_RADIUS) / R + DrawPanel.CENTER_X;}

    public double toPixelsY(double R) { return (-y * DrawPanel.START_RADIUS) / R + DrawPanel.CENTER_Y;}

    @Override
    public String toString() {
        return "Dot{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
