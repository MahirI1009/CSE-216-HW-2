import java.util.ArrayList;
import java.util.List;

/**
 * An unmodifiable point in the standard two-dimensional Euclidean space. The coordinates of such a point is given by
 * exactly two doubles specifying its <code>x</code> and <code>y</code> values.
 */
public class TwoDPoint implements Point {

    private double x;
    private double y;

    public TwoDPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return the coordinates of this point as a <code>double[]</code>.
     */
    @Override
    public double[] coordinates() {
        return new double[]{x, y};
    }

    @Override
    public double getLeastX() {
        return 0;
    }

    public void setX(double x) {this.x = x;}

    public void setY(double y) {this.y = y;}

    public double getX() {return x;}

    public double getY() {return y;}


    /**
     * Returns a list of <code>TwoDPoint</code>s based on the specified array of doubles. A valid argument must always
     * be an even number of doubles so that every pair can be used to form a single <code>TwoDPoint</code> to be added
     * to the returned list of points.
     *
     * @param coordinates the specified array of doubles.
     * @return a list of two-dimensional point objects.
     * @throws IllegalArgumentException if the input array has an odd number of doubles.
     */
    public static List<TwoDPoint> ofDoubles(double[] coordinates) throws IllegalArgumentException {
        List<TwoDPoint> twoDPoints = new ArrayList<>(coordinates.length/2);
        try {
            if ((coordinates.length - 1)%2 == 0) {
                for (int i = 0; i < coordinates.length - 1; i++)
                    twoDPoints.set(i, new TwoDPoint(coordinates[i], coordinates[i + 1]));
            }
            else throw new IllegalArgumentException();
        } catch (IllegalArgumentException e) {
            System.out.println("Error: The array of doubles must have an even length.");
        }
            return twoDPoints;
    }

    @Override
    public int compareTo(Point p) {
        return Double.compare(Math.abs(this.getX()), Math.abs(p.getX()));
    }
}
