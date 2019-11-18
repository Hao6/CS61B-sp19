package bearmaps;

import javax.swing.plaf.IconUIResource;
import java.util.*;

/**
 * @ClassName ArrayHeapMinPQ
 * @Description TODO
 * @Author hao6
 * @Data 11/14/19 10:52 AM
 * @Version 1.0
 **/
public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private int size;
    private List<Entry> heap; // 堆
    private Map<T, Integer> keyMap; //记录元素在堆（arraylist）中的下标

    public ArrayHeapMinPQ() {
        size = 0;
        heap = new ArrayList<>(16); // 初始容量为16
        keyMap = new HashMap<>();
    }

    private class Entry implements Comparable<Entry>{
        T data;
        double priority;

        public Entry(T t, double p) {
            data = t;
            priority = p;
        }

        @Override
        public int compareTo(Entry entry) {
            return Double.compare(priority, entry.priority);
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }
            if (this == o) {
                return true;
            }
            Entry other = (Entry)o;
            if (getClass() != other.getClass()) {
                return false;
            }
            return data.equals(other.data);
        }
    }

    private void swap(int idx1, int idx2) {
        Entry tempEntry = heap.get(idx1);
        keyMap.put(heap.get(idx1).data, idx2);
        keyMap.put(heap.get(idx2).data, idx1);
        heap.set(idx1, heap.get(idx2));
        heap.set(idx2, tempEntry);
    }

    private int adjustUpper(int curIdx) {
        while (curIdx != 0) {
            if (heap.get((curIdx-1) / 2).compareTo(heap.get(curIdx)) > 0) {
                swap(curIdx, (curIdx-1) / 2);
                curIdx = (curIdx-1) / 2;
            } else {
                break;
            }
        }
        return curIdx;
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("该item已经存在");
        }
        heap.add(size, new Entry(item, priority));
        //向上调整
        int curIdx = adjustUpper(size);
        size += 1;
        keyMap.put(item, curIdx);
    }

    @Override
    public boolean contains(T item) {
        return keyMap.containsKey(item);
    }

    @Override
    public T getSmallest() {
        if (size == 0) {
            throw new NoSuchElementException("PQ is empty");
        }
        return heap.get(0).data;

    }

    private void adjustDown(int curIdx) {
        while (curIdx < size-1) {
            boolean left = false;
            boolean right = false;
            if (curIdx * 2 + 1 < size - 1 &&
                    heap.get(curIdx).compareTo(heap.get(curIdx * 2 + 1)) > 0) {
                left = true;
            }
            if (curIdx * 2 + 2 < size - 1 &&
                    heap.get(curIdx).compareTo(heap.get(curIdx * 2 + 2)) > 0) {
                right = true;
            }
            if (left && right) { // 在左右中选择一个较小的置换
                if (heap.get(curIdx * 2 + 1).compareTo(heap.get(curIdx * 2 + 2)) > 0) {
                    swap(curIdx, curIdx * 2 + 2);
                    curIdx = curIdx * 2 + 2;
                } else {
                    swap(curIdx, curIdx * 2 + 1);
                    curIdx = curIdx * 2 + 1;
                }
            } else if (left) { // 与左边置换
                swap(curIdx, curIdx * 2 + 1);
                curIdx = curIdx * 2 + 1;
            } else if (right) { // 与右边置换
                swap(curIdx, curIdx * 2 + 2);
                curIdx = curIdx * 2 + 2;
            } else { // 不用置换
                break;
            }
        }
    }

    @Override
    public T removeSmallest() {
        if (size == 0) {
            throw new NoSuchElementException("PQ is empty");
        }
        Entry retEntry = heap.get(0);
        heap.set(0, heap.get(size-1));
        // 向下调整
        adjustDown(0);
        heap.set(size-1, null);
        size -= 1;
        keyMap.remove(retEntry.data);
        return retEntry.data;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ is empty");
        }
        int curIdx = keyMap.get(item); // 获取当前元素在heap中的下标
        if (heap.get(curIdx).priority < priority) { // 向下调整
            heap.get(curIdx).priority = priority;
            adjustDown(curIdx);
        } else if (heap.get(curIdx).priority > priority) { //向上调整
            heap.get(curIdx).priority = priority;
            adjustUpper(curIdx);
        }
    }

    public void printPQ() {
        for (int i = 0; i < size; i += 1) {
            System.out.print(String.valueOf(heap.get(i).data) +" "+ heap.get(i).priority + " ");
        }
        System.out.println();
    }
}
