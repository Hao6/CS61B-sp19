import edu.princeton.cs.introcs.StdRandom;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hug.
 */
public class Experiments {
    private static List<Integer> randomIntList(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("size应该是大于0的整数");
        }
        List<Integer> inorderList = new ArrayList<>(size);
        List<Integer> randomList = new ArrayList<>(size);
        for (int i = 1; i <= size; i += 1) {
            inorderList.add(i);
        }

        for (int i = 0; i < size; i += 1) {
            int index = StdRandom.uniform(size-i);
            randomList.add(inorderList.remove(index));
        }
        return randomList;
    }

    /**
     * 随机混乱list,返回一个新的list
     * @param sourceList
     * @return
     */
    private static List<Integer> shuffleIntList(List<Integer> sourceList) {
        int size = sourceList.size();
        List<Integer> shuffleList = new ArrayList<>(size);
        for (int i = 0; i < size; i += 1) {
            int index = StdRandom.uniform(size-i);
            shuffleList.add(sourceList.get(index));
        }
        return shuffleList;
    }

    public static void experiment1() {
        BST<Integer> simpleBst = new BST<>();
        int size = 10000;
        List<Integer> randomXArr = randomIntList(size);
        List<Double> randomYArr = new ArrayList<>();
        List<Double> optimalYArr = new ArrayList<>();
        List<Integer> newXArr = new ArrayList<>();

        for (int i = 0; i < size; i += 1) {
            simpleBst.add(randomXArr.get(i));
            if ((i+1) % 50 == 0) {
                newXArr.add(i+1);
                randomYArr.add(simpleBst.calAverageDepth());
                optimalYArr.add(ExperimentHelper.optimalAverageDepth(i + 1));
            }
        }
        XYChart chart = new XYChartBuilder().width(1600).height(600).xAxisTitle("x label").
                yAxisTitle("y label").build();
        chart.addSeries("item's num vs random insert avg-depth", newXArr, randomYArr);
        chart.addSeries("item's num vs optimal insert avg-depth", newXArr, optimalYArr);

        new SwingWrapper(chart).displayChart();
    }
    public static void experiment2() {
        int m = 500; // 起始BST的node数量
        BST<Integer> simpleBst = new BST<>();
        int size = 1000;
        List<Integer> randomXArr = randomIntList(size);
        for (int i = 0; i < m; i += 1) {
            simpleBst.add(randomXArr.get(i));
        }

        List<Double> randomYArr = new ArrayList<>(m+1);
        List<Integer> newXArr = new ArrayList<>(m+1);

        newXArr.add(0);
        randomYArr.add(simpleBst.calAverageDepth());

        List<Integer> shuffleList = shuffleIntList(randomXArr);

        for (int i = 0; i < m; i += 1) {
            simpleBst.deleteTakingSuccessor(shuffleList.get(i)); // 随机删除
            simpleBst.add(randomXArr.get(i+m)); // 随机添加

            newXArr.add(i+1);
            randomYArr.add(simpleBst.calAverageDepth()); // 计算avg-depth
        }

        XYChart chart = new XYChartBuilder().width(1600).height(600).xAxisTitle("x label").
                yAxisTitle("y label").build();
        chart.addSeries("[random or del insert]'s num vs  avg-depth", newXArr, randomYArr);
        new SwingWrapper(chart).displayChart();
    }

    public static void experiment3() {
        int m = 500; // 起始BST的node数量
        BST<Integer> simpleBst = new BST<>();
        int size = 1000;
        List<Integer> randomXArr = randomIntList(size);
        for (int i = 0; i < m; i += 1) {
            simpleBst.add(randomXArr.get(i));
        }

        List<Double> randomYArr = new ArrayList<>(m+1);
        List<Integer> newXArr = new ArrayList<>(m+1);

        newXArr.add(0);
        randomYArr.add(simpleBst.calAverageDepth());

        List<Integer> shuffleList = shuffleIntList(randomXArr);

        for (int i = 0; i < m; i += 1) {
            simpleBst.deleteTakingRandom(shuffleList.get(i)); // 随机删除
            simpleBst.add(randomXArr.get(i+m)); // 随机添加

            newXArr.add(i+1);
            randomYArr.add(simpleBst.calAverageDepth()); // 计算avg-depth
        }

        XYChart chart = new XYChartBuilder().width(1600).height(600).xAxisTitle("x label").
                yAxisTitle("y label").build();
        chart.addSeries("[random or del insert(random)]'s num vs  avg-depth", newXArr, randomYArr);
        new SwingWrapper(chart).displayChart();
    }

    public static void main(String[] args) {
//        experiment1();
        experiment2();
        experiment3();
    }

}
