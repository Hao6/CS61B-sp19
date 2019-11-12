package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private double[] fraction;
    private int experimentTimes;
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <=0 || T <= 0) {
            throw new IllegalArgumentException("N 与 T 应该是大于0的整数");
        }
        fraction = new double[T]; // 存储T次的阈值
        experimentTimes = T;
        for (int i = 0; i < T; i += 1) {
            System.out.printf("正在进行实验[%d]\n",i+1);
            Percolation percolation = pf.make(N);
            while (!percolation.percolates()) {
                int curUnitIdx;
                do {
                    curUnitIdx = StdRandom.uniform(N*N);
                }while (percolation.isOpen(curUnitIdx / N, curUnitIdx % N));

                percolation.open(curUnitIdx / N, curUnitIdx % N);

                if (curUnitIdx / N == 0) {
                    percolation.full(curUnitIdx % N);
                }
            }
            fraction[i] = percolation.numberOfOpenSites()*1.0 / (N*N);
            System.out.printf("实验[%d]完成\n",i+1);
        }
    }

    public double mean() {
        return StdStats.mean(fraction);
    }

    public double stddev() {
        return StdStats.stddev(fraction);
    }

    public double confidenceLow() {
        return mean() - 1.96*Math.sqrt(stddev()) / Math.sqrt(experimentTimes);
    }

    public double confidenceHigh() {
        return mean() + 1.96*Math.sqrt(stddev()) / Math.sqrt(experimentTimes);
    }

    public static void main(String[] args) {
        int T = 5000;
        int N = 10;
        PercolationStats ps = new PercolationStats(N, T, new PercolationFactory());
        System.out.printf("经过 %d 次实验， 在 %d × %d 大小的grid上，" +
                "阈值的平均值为 %f， 标准差为 %f， 置信区间为[%f,%f]",
                T, N, N, ps.mean(), ps.stddev(), ps.confidenceLow(), ps.confidenceHigh());
    }

}
