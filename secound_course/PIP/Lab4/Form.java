public class Form {

    private double r;

    public Form(double r) {
        this.r = r;
    }

    public double getR() { return r; }

    public void setR(double r) { this.r = r; }

    public boolean isContain(Dot dot) {
        return (((Math.pow(dot.getX(), 2) + Math.pow(dot.getY(), 2) <= Math.pow(r / 2, 2)) && (dot.getX() >= 0 && dot.getY() >= 0)) ||
                ((dot.getX() >= -r && dot.getX() <= 0) && (dot.getY() <= r && dot.getY() >= 0)) ||
                ((dot.getX() >= 0 && dot.getX() <= r) && (dot.getY() <= 0 && dot.getY() >= -r)));
    }
}
