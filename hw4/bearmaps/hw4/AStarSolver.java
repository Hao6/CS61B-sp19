package bearmaps.hw4;
import bearmaps.proj2ab.DoubleMapPQ;

import java.util.*;

/**
 * @ClassName AStarSolver
 * @Description TODO
 * @Author hao6
 * @Data 11/15/19 6:01 PM
 * @Version 1.0
 **/
public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {

    private SolverOutcome result;
    private List<Vertex> solution;
    private double solutionWeight;
    private int numStatesExplored;
    private double explorationTime;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        long startTime = System.currentTimeMillis();
        // step1,初始化状态参数
        result = SolverOutcome.UNSOLVABLE;
        solution = new LinkedList<>();
        solutionWeight = 0;
        numStatesExplored = 0;
        explorationTime = 0;

        // step2,
        DoubleMapPQ<Vertex> pq = new DoubleMapPQ<>(); //初始化优先队列，按照d(s,v)+h(v,goal)的优先级存储
        Map<Vertex, Double> distTo = new HashMap<>(); // 存储当前vertex到start的实际distance
        Map<Vertex, Vertex> edgeTo = new HashMap<>(); // 存储当前vertex的上一级vertex

        distTo.put(start, 0.0);
        edgeTo.put(start, start);
        pq.add(start, input.estimatedDistanceToGoal(start, end)); // 开始结点加入

        while (pq.size() > 0) {
            if ((System.currentTimeMillis() - startTime) / 1000 > timeout) { // 超时
                result = SolverOutcome.TIMEOUT;
                break;
            }
            Vertex p = pq.removeSmallest();
            numStatesExplored += 1;
            if (p.equals(end)) { // 找到了vertex end，计算解答需要的属性
                result = SolverOutcome.SOLVED;
                break;
            }
            List<WeightedEdge<Vertex>> WeightedEdges = input.neighbors(p); // 获取当前vertex的输出边
            for (WeightedEdge e : WeightedEdges) {
                Vertex q = (Vertex) e.to();
                double w = e.weight();
                if (!distTo.containsKey(q) || distTo.get(q) > distTo.get(p) + w) {
                    distTo.put(q, distTo.get(p) + w);
                    edgeTo.put(q, p); //存储路径信息
                    if (!pq.contains(q)) {
                        pq.add(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
                    } else {
                        pq.changePriority(q,
                                distTo.get(q) + input.estimatedDistanceToGoal(q, end));
                    }
                }
            }
        }

        if (result == SolverOutcome.SOLVED) {
            // 添加vertex到solution中
            Vertex curVertex = end;
            solution.add(end);
            while (!edgeTo.get(curVertex).equals(curVertex)) {
                solution.add(0, edgeTo.get(curVertex));
                curVertex = edgeTo.get(curVertex);
            }

            // 得到solutionWeight
            solutionWeight = distTo.get(end);
        }
        explorationTime = (System.currentTimeMillis() - startTime) / 1000;
        numStatesExplored -= 1;
    }

    @Override
    public SolverOutcome outcome() {
        return result;
    }


    @Override
    public List<Vertex> solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {
        return solutionWeight;
    }

    @Override
    public int numStatesExplored() {
        return numStatesExplored;
    }

    @Override
    public double explorationTime() {
        return explorationTime;
    }
}
