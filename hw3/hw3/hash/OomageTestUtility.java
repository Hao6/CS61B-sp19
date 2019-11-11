package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        int N = oomages.size();
        int[] buckets = new int[M];
        double lower = N / 50.0;
        double higher = N / 2.5;
        for (Oomage oomage : oomages) {
            buckets[(oomage.hashCode() & 0x7FFFFFFF)%M] += 1;
        }
        for (int i = 0; i < M; i += 1) {
            if (!(buckets[i] > lower && buckets[i] < higher)) {
                return false;
            }
        }
        return true;
    }
}
