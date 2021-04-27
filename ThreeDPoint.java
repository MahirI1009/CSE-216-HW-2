/**
 * An unmodifiable point in the three-dimensional space. The coordinates are specified by exactly three doubles (its
 * <code>x</code>, <code>y</code>, and <code>z</code> values).
 */
public class ThreeDPoint implements Point {

    private double x, y, z;

    public ThreeDPoint(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setX(double x) {this.x = x;}

    public void setY(double y) {this.y = y;}

    public void setZ(double z) {this.z = z;}

    public double getX() {return x;}

    public double getY() {return y;}

    public double getZ() {return z;}

    /**
     * @return the (x,y,z) coordinates of this point as a <code>double[]</code>.
     */
    @Override
    public double[] coordinates() {return new double[]{x, y , z};}

    @Override
    public double getLeastX() {
        return 0;
    }

    @Override
    public int compareTo(Point p) {
        if (Math.abs(this.getX()) > Math.abs(p.getX()))
            return 1;
        else if (Math.abs(this.getX()) < Math.abs(p.getX()))
            return -1;
        else return 0;
    }

}
