import java.util.*;

public class Triangle implements TwoDShape, Positionable {

    private double leastX;

    List<TwoDPoint> vertices;

    public Triangle(List<TwoDPoint> vertices) {
        setPosition(vertices);
    }

    /**
     * Sets the position of this triangle according to the first three elements in the specified list of points. The
     * triangle is formed on the basis of these three points taken in a clockwise manner on the two-dimensional
     * x-y plane. If the input list has more than three elements, the subsequent elements are ignored.
     *
     * @param points the specified list of points.
     */
    @Override
    public void setPosition(List<? extends Point> points) throws IllegalArgumentException {
        try {
            if (this.vertices == null)
                vertices = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
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
     * @return the number of sides of this triangle, which is always set to three
     */
    @Override
    public int numSides() {
        return 3;
    }

    /**
     * Checks whether or not a list of vertices forms a valid triangle. The <i>trivial</i> triangle, where all three
     * corner vertices are the same point, is considered to be an invalid triangle.
     *
     * @param vertices the list of vertices to check against, where each vertex is a <code>Point</code> type.
     * @return <code>true</code> if <code>vertices</code> is a valid collection of points for a triangle, and
     * <code>false</code> otherwise. For example, three vertices are in a straight line is invalid.
     */

    @Override
    public boolean isMember(List<? extends Point> vertices) {
        TwoDPoint p1 = (TwoDPoint) vertices.get(0),
                  p2 = (TwoDPoint) vertices.get(1),
                  p3 = (TwoDPoint) vertices.get(2);

        if (p1.getX() == p2.getX() && p2.getX() == p3.getX())
            return false;
        else if (p1.getY() == p2.getY() && p2.getY() == p3.getY())
            return false;
        else return slope(p1, p2) != slope(p2, p3);
    }

    @Override
    public double getLeastX() {return leastX;}

    @Override
    public int compareTo(TwoDShape s) {
        return Double.compare(this.area(), s.area());
    }

    /**
     * This method snaps each vertex of this triangle to its nearest integer-valued x-y coordinate. For example, if
     * a corner is at (0.8, -0.1), it will be snapped to (1,0). The resultant triangle will thus have all four
     * vertices in positions with integer x and y values. If the snapping procedure described above results in this
     * triangle becoming invalid (e.g., all corners collapse to a single point), then it is left unchanged. Snapping is
     * an in-place procedure, and the current instance is modified.
     */
    public void snap() {
        TwoDPoint o1 = vertices.get(0),
                  o2 = vertices.get(1),
                  o3 = vertices.get(2);


        TwoDPoint p1 = new TwoDPoint(Math.round(o1.getX()), Math.round(o1.getY())),
                  p2 = new TwoDPoint(Math.round(o2.getX()), Math.round(o2.getY())),
                  p3 = new TwoDPoint(Math.round(o3.getX()), Math.round(o3.getY()));

        List<TwoDPoint> temp = new ArrayList<>(3);
        temp.add(p1);
        temp.add(p2);
        temp.add(p3);

        if (isMember(temp)) {
            vertices = temp;
            leastX = temp.get(0).getX();
        }
    }

    /**
     * @return the area of this triangle
     */
    @Override
    public double area() {
        TwoDPoint p1 = vertices.get(0),
                  p2 = vertices.get(1),
                  p3 = vertices.get(2);

        double a = distance(p1, p2);
        double b = distance(p2, p3);
        double c = distance(p3, p1);

        double p = perimeter();
        double s = p/2;

        return Math.sqrt(s * (s-a) * (s-b) * (s-c));
    }

    /**
     * @return the perimeter (i.e., the total length of the boundary) of this triangle
     */
    public double perimeter() {
        TwoDPoint p1 = vertices.get(0),
                  p2 = vertices.get(1),
                  p3 = vertices.get(2);

        double a = distance(p1, p2);
        double b = distance(p2, p3);
        double c = distance(p3, p1);

        return a + b + c;
    }

    public List<TwoDPoint> sortVertices() {
        if (vertices.get(0).getX() < vertices.get(1).getX() && vertices.get(1).getX() < vertices.get(2).getX())
            return vertices;
        else {
            for (int i = 0; i < 2; i++)
                if (compare(vertices.get(i), vertices.get(i + 1)) == 0) {
                    TwoDPoint temp = vertices.get(i + 1);
                    vertices.set(i + 1, vertices.get(i));
                    vertices.set(i, temp);
                }
        }
        return vertices;
    }

    public double slope (TwoDPoint p1, TwoDPoint p2) {
        return (p2.getY() - p1.getY())/(p2.getX() - p1.getX());
    }

    public double distance (TwoDPoint p1, TwoDPoint p2) {
        double a = p2.getX() - p1.getX();
        double b = p2.getY() - p1.getY();
        return Math.sqrt((a*a)+(b*b));
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

    @Override
    public String toString() {
        return "Triangle[(" + String.format("%.2f", vertices.get(0).getX()) + ","
                + String.format("%.2f", vertices.get(0).getY()) + "), ("
                + String.format("%.2f", vertices.get(1).getX()) + ","
                + String.format("%.2f", vertices.get(1).getY()) + "), ("
                + String.format("%.2f", vertices.get(2).getX()) + ","
                + String.format("%.2f", vertices.get(2).getY()) + ")]";
    }
}
