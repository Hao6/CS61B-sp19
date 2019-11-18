package bearmaps;

import java.util.List;

/**
 * @ClassName KDTree
 * @Description TODO
 * @Author hao6
 * @Data 11/14/19 5:56 PM
 * @Version 1.0
 **/
public class KDTree {

    private Node root;
    private static int DIM_1 = 0;
    private static int DIM_2 = 1;

    private static class Node {
        private Point point;
        private int dim; // 分割维度，若是0,则是第一维度，1,即为第二维度,以此类推
        Node left;
        Node right;

        public Node(Point p, int d) {
            point = p;
            dim = d;
            left = null;
            right = null;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }
    }

    private void insert(Point point) {
        if (point != null) {
            Node temp = root;
            while (temp != null) {
                if (temp.dim == DIM_1) {
                    if (Double.compare(temp.point.getX(), point.getX()) > 0) {
                        if (temp.left != null) {
                            temp = temp.left;
                        } else {
                            temp.left = new Node(point, DIM_2);
                            break;
                        }
                    } else {
                        if (temp.right != null) {
                            temp = temp.right;
                        } else {
                            temp.right = new Node(point, DIM_2);
                            break;
                        }
                    }
                } else {
                    if (Double.compare(temp.point.getY(), point.getY()) > 0) {
                        if (temp.left != null) {
                            temp = temp.left;
                        } else {
                            temp.left = new Node(point, DIM_1);
                            break;
                        }
                    } else {
                        if (temp.right != null) {
                            temp = temp.right;
                        } else {
                            temp.right = new Node(point, DIM_1);
                            break;
                        }
                    }
                }
            }
        }
    }
    public KDTree(List<Point> points) {
        if (points != null && points.size() > 0) {
            root = new Node(points.get(0), DIM_1);

            for (int i = 1; i < points.size(); i += 1) {
                insert(points.get(i));
            }
        }
    }

    private boolean badSideIsUseful(Node curNode, Point goal, Node best) {
        double minDis = Point.distance(goal, best.point);
        if (curNode.dim == DIM_1) {
            if (minDis >= Math.abs(goal.getX() - curNode.point.getX())) {
                return true;
            }
        } else {
            if (minDis >= Math.abs(goal.getY() - curNode.point.getY())) {
                return true;
            }
        }
        return false;
    }

    private Node nearestHelp(Node curNode, Point goal, Node best) {
        if (curNode == null) {
            return best;
        }
        if (best == null || Point.distance(curNode.point, goal) < Point.distance(best.point, goal)) {
            best = curNode;
        }
        Node goodSide = null;
        Node badSide = null;
        if (curNode.dim == DIM_1) {
            if (Double.compare(curNode.point.getX(), goal.getX()) > 0) {
                goodSide = curNode.left;
                badSide = curNode.right;
            } else {
                badSide = curNode.left;
                goodSide = curNode.right;
            }
        } else {
            if (Double.compare(curNode.point.getY(), goal.getY()) > 0) {
                goodSide = curNode.left;
                badSide = curNode.right;
            } else {
                badSide = curNode.left;
                goodSide = curNode.right;
            }
        }

        best = nearestHelp(goodSide, goal, best);
        if (badSideIsUseful(curNode, goal, best)) {
            best = nearestHelp(badSide, goal, best);
        }

        return best;
    }

    public Point nearest(double x, double y) {
        return nearestHelp(root, new Point(x, y), null).point;
    }
}
