import edu.princeton.cs.algs4.Queue;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<Integer> tas = new Queue<>();
        int N = 1000000;
        int lower = 2;
        int high = 300000;
        for (int i = 0; i < N; i += 1) {
            tas.enqueue(StdRandom.uniform(lower, high));
        }
        long startTime = System.currentTimeMillis();
        Queue<Integer> inorderQueue = QuickSort.quickSort(tas);
        assertTrue(isSorted(inorderQueue));
        System.out.println((System.currentTimeMillis() - startTime)/1000.0);
    }

    @Test
    public void testMergeSort() {
        Queue<Integer> tas = new Queue<>();
        int N = 1000000;
        int lower = 2;
        int high = 300000;
        for (int i = 0; i < N; i += 1) {
            tas.enqueue(StdRandom.uniform(lower, high));
        }
        long startTime = System.currentTimeMillis();
        Queue<Integer> inorderQueue = MergeSort.mergeSort(tas);
        assertTrue(isSorted(inorderQueue));
        System.out.println((System.currentTimeMillis() - startTime)/1000.0);
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
