/**
 * Created by hug.
 */
public class ExperimentHelper {

    /** Returns the internal path length for an optimum binary search tree of
     *  size N. Examples:
     *  N = 1, OIPL: 0
     *  N = 2, OIPL: 1
     *  N = 3, OIPL: 2
     *  N = 4, OIPL: 4
     *  N = 5, OIPL: 6
     *  N = 6, OIPL: 8
     *  N = 7, OIPL: 10
     *  N = 8, OIPL: 13
     */
    public static int optimalIPL(int N) {
        return (int)Math.round(N*optimalAverageDepth(N));
    }

    /** Returns the average depth for nodes in an optimal BST of
     *  size N.
     *  Examples:
     *  N = 1, OAD: 0
     *  N = 5, OAD: 1.2
     *  N = 8, OAD: 1.625
     * @return
     */
    public static double optimalAverageDepth(int N) {
        int a = 1;
        int b = 0;
        int c = 0;
        double res = 0;
        while (N >= a + c) {
            res += a*b;
            c += a;
            b += 1;
            a *= 2;
        }
        res += (N - c)*b;
        return res / N;
    }

    public static void main(String[] args) {
        int N = 2;
        System.out.println(optimalAverageDepth(N));
    }
}
