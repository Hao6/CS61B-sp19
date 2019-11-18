package bearmaps;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {

    @Test
    public void testNearest() {
        int N =10000; // 构造kd树
        int C = 1000; // 用于查询
        double minPointX = -1000.0;
        double maxPointX = 2000.0;
        double minPointY = -1500.0;
        double maxPointY = 1500.0;
        List<Point> kdArr= new ArrayList<>(N);
        List<Point> testArr = new ArrayList<>(C);

        for (int i = 0; i < N; i += 1) {
            kdArr.add(new Point(StdRandom.uniform(minPointX, maxPointX),
                    StdRandom.uniform(minPointY, maxPointY)));
        }
        for (int i = 0; i < C; i += 1) {
            testArr.add(new Point(StdRandom.uniform(minPointX, maxPointX),
                    StdRandom.uniform(minPointY, maxPointY)));
        }

        KDTree kd = new KDTree(kdArr);
        NaivePointSet na = new NaivePointSet(kdArr);

        for (int i = 0; i < C; i += 1) {
            assertEquals(na.nearest(testArr.get(i).getX(), testArr.get(i).getY()),
                    kd.nearest(testArr.get(i).getX(), testArr.get(i).getY()));
        }
    }
}
