import java.util.ArrayList;
import java.util.List;

public class Quadrilateral implements TwoDShape, Positionable {

    private double leastX;

    List<TwoDPoint> vertices;

    public Quadrilateral(List<TwoDPoint> vertices) {
        setPosition(vertices);
    }

    /**
     * Sets the position of this quadrilateral according to the first four elements in the specified list of points. The
     * quadrilateral is formed on the basis of these four points taken in a clockwise manner on the two-dimensional
     * x-y plane. If the input list has more than four elements, the subsequent elements are ignored.
     *
     * @param points the specified list of points.
     */
    @Override
    public void setPosition(List<? extends Point> points) throws IllegalArgumentException{
        try {
            if (this.vertices == null)
                vertices = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                if (points.get(i) instanceof TwoDPoint) {
                    vertices.add((TwoDPoint) points.get(i));
                }
                else throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: The list must consist of TwoDPoint instances.");
        }
        sortVertices();
        leastX = vertices.get(0).getX();
    }

    /**
     * Retrieve the position of an object as a list of points. The points are be retrieved and added to the returned
     * list in a clockwise manner on the two-dimensional x-y plane, starting with the point with the least x-value. If
     * two points have the same least x-value, then the clockwise direction starts with the point with the lower y-value.
     *
     * @return the retrieved list of points.
     */
    @Override
    public List<? extends Point> getPosition() {return vertices;}

    /**
     * @return the number of sides of this quadrilateral, which is always set to four
     */
    @Override
    public int numSides() {
        return 4;
    }

    /**
     * Checks whether or not a list of vertices forms a valid quadrilateral. The <i>trivial</i> quadrilateral, where all
     * four corner vertices are the same point, is considered to be an invalid quadrilateral.
     *
     * @param vertices the list of vertices to check against, where each vertex is a <code>Point</code> type.
     * @return <code>true</code> if <code>vertices</code> is a valid collection of points for a quadrilateral, and
     * <code>false</code> otherwise. For example, if three of the four vertices are in a straight line is invalid.
     */
    @Override
    public boolean isMember(List<? extends Point> vertices) {
        TwoDPoint p1 = (TwoDPoint) vertices.get(0),
                  p2 = (TwoDPoint) vertices.get(1),
                  p3 = (TwoDPoint) vertices.get(2),
                  p4 = (TwoDPoint) vertices.get(3);

        if (p1.getX() == p2.getX() && p2.getX() == p3.getX() && p3.getX() == p4.getX())
            return false;
        else if (p1.getY() == p2.getY() && p2.getY() == p3.getY() && p3.getY() == p4.getY())
            return false;
        else return slope(p1, p2) != slope(p2, p3) && slope(p2, p3) != slope(p3, p4);
    }

    @Override
    public double getLeastX() {return leastX;}

    @Override
    public int compareTo(TwoDShape s) {
        return Double.compare(this.area(), s.area());
    }

    /**
     * This method snaps each vertex of this quadrilateral to its nearest integer-valued x-y coordinate. For example, if
     * a corner is at (0.8, -0.1), it will be snapped to (1,0). The resultant quadrilateral will thus have all four
     * vertices in positions with integer x and y values. If the snapping procedure described above results in this
     * quadrilateral becoming invalid (e.g., all four corners collapse to a single point), then it is left unchanged.
     * Snapping is an in-place procedure, and the current instance is modified.
     */
    public void snap() {
        TwoDPoint o1 = vertices.get(0),
                  o2 = vertices.get(1),
                  o3 = vertices.get(2),
                  o4 = vertices.get(3);


        TwoDPoint p1 = new TwoDPoint(Math.round(o1.getX()), Math.round(o1.getY())),
                  p2 = new TwoDPoint(Math.round(o2.getX()), Math.round(o2.getY())),
                  p3 = new TwoDPoint(Math.round(o3.getX()), Math.round(o3.getY())),
                  p4 = new TwoDPoint(Math.round(o4.getX()), Math.round(o4.getY()));

        List<TwoDPoint> temp = new ArrayList<>(3);
        temp.add(p1);
        temp.add(p2);
        temp.add(p3);
        temp.add(p4);

        if (isMember(temp)) {
            vertices = temp;
            leastX = temp.get(0).getX();
        }
    }

    /**
     * @return the area of this quadrilateral
     */
    @Override
    public double area() {
        List<TwoDPoint> h1 = new ArrayList<>();
        h1.add(vertices.get(0));
        h1.add(vertices.get(1));
        h1.add(vertices.get(2));

        List<TwoDPoint> h2 = new ArrayList<>();
        h2.add(vertices.get(0));
        h2.add(vertices.get(3));
        h2.add(vertices.get(2));

        Triangle t1 = new Triangle(h1),
                 t2 = new Triangle(h2);

        return t1.area() + t2.area();
    }

    /**
     * @return the perimeter (i.e., the total length of the boundary) of this quadrilateral
     */
    public double perimeter() {
        TwoDPoint p1 = vertices.get(0),
                  p2 = vertices.get(1),
                  p3 = vertices.get(2),
                  p4 = vertices.get(3);

        double a = distance(p1, p2);
        double b = distance(p2, p3);
        double c = distance(p3, p4);
        double d = distance(p4, p1);

        return a + b + c + d;
    }

    public List<TwoDPoint> sortVertices() {
        if (vertices.get(0).getX() < vertices.get(1).getX() && vertices.get(1).getX() < vertices.get(2).getX()
                && vertices.get(2).getX() < vertices.get(3).getX())
            return vertices;
        else {
            for (int i = 0; i < 3; i++)
                if (compare(vertices.get(i), vertices.get(i + 1)) == 0) {
                    TwoDPoint temp = vertices.get(i + 1);
                    vertices.set(i + 1, vertices.get(i));
                    vertices.set(i, temp);
                }
        }
        return vertices;
    }

    public int compare(TwoDPoint o1, TwoDPoint o2) {
        if (o1.getX() < o2.getX())
            return 1;
        else if (o1.getX() > o2.getX())
            return 0;
        else if (o1.getX() == o2.getX()) {
            if (o1.getY() < o2.getY())
                return 1;
            else if (o1.getY() > o2.getY())
                return 0;
            else return -1;
        }
        else return -1;
    }

    public double slope (TwoDPoint p1, TwoDPoint p2) {
        return (p2.getY() - p1.getY())/(p2.getX() - p1.getX());
    }

    public double distance (TwoDPoint p1, TwoDPoint p2) {
        double a = p2.getX() - p1.getX();
        double b = p2.getY() - p1.getY();
        return Math.sqrt((a*a)+(b*b));
    }

    @Override
    public String toString() {
        return "Quadrilateral[(" + String.format("%.2f", vertices.get(0).getX()) + ","
                + String.format("%.2f", vertices.get(0).getY()) + "), ("
                + String.format("%.2f", vertices.get(1).getX()) + ","
                + String.format("%.2f", vertices.get(1).getY()) + "), ("
                + String.format("%.2f", vertices.get(2).getX()) + ","
                + String.format("%.2f", vertices.get(2).getY()) + "), ("
                + String.format("%.2f", vertices.get(3).getX()) + ","
                + String.format("%.2f", vertices.get(3).getY()) + ")]";
    }
}
