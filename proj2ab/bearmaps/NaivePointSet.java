package bearmaps;

import java.util.List;

/**
 * @ClassName NaivePointSet
 * @Description TODO
 * @Author hao6
 * @Data 11/14/19 5:50 PM
 * @Version 1.0
 **/
public class NaivePointSet implements PointSet{

    private List<Point> points;

    public NaivePointSet(List<Point> points) {
        points = points;
    }
    @Override
    public Point nearest(double x, double y) {
        Point nearestPoint = null;
        double minDistance = Double.MAX_VALUE;
        Point targetPoint = new Point(x, y);

        for (Point point : points) {
            if (Point.distance(point, targetPoint) < minDistance) {
                minDistance = Point.distance(point, targetPoint);
                nearestPoint = point;
            }
        }
        return nearestPoint;
    }
}
