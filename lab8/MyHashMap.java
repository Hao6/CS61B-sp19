import java.util.*;

/**
 * @ClassName MyHashMap
 * @Description TODO
 * @Author hao6
 * @Data 11/12/19 2:54 PM
 * @Version 1.0
 **/
public class MyHashMap<K, V> implements Map61B<K, V>{
    private int size;
    private int bucketSize;
    private double loadFactor;
    private LinkedList<Entry>[] buckets;


    public MyHashMap() {
        this(16, 0.75);
    }
    public MyHashMap(int initialSize) {
        this(initialSize, 0.75);
    }

    public MyHashMap(int initialSize, double loadFactor) {
        bucketSize = initialSize;
        this.loadFactor = loadFactor;
        size = 0;
        buckets = new LinkedList[initialSize];
    }
    private class Entry {
        K k;
        V v;
        public Entry(K key, V value) {
            k = key;
            v = value;
        }
    }
    @Override
    public void clear() {
        for (int i = 0; i < bucketSize; i += 1) {
            buckets[i] = null;
        }
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        int hashCode = key.hashCode();
        int bucketIndex = Math.floorMod(hashCode , bucketSize);
        if (buckets[bucketIndex] != null){
            for (Entry entry : buckets[bucketIndex]) {
                if (entry.k.equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        int hashCode = key.hashCode();
        int bucketIndex = Math.floorMod(hashCode , bucketSize);
        if (buckets[bucketIndex] != null){
            for (Entry entry : buckets[bucketIndex]) {
                if (entry.k.equals(key)) {
                    return entry.v;
                }
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        if (key != null) {
            int hashCode = key.hashCode();
            int bucketIndex = Math.floorMod(hashCode , bucketSize);
            if (buckets[bucketIndex] == null) {
                buckets[bucketIndex] = new LinkedList<>();
            }
            for (Entry entry : buckets[bucketIndex]) { // 如果已经存在，则更新key对应的value
                if (entry.k.equals(key)) {
                    entry.v = value;
                    return;
                }
            }
            buckets[bucketIndex].addFirst(new Entry(key, value));
            size += 1;
            if (size*1.0 / bucketSize >= loadFactor) { // 需要扩展（double）
                int newBucketSize = bucketSize * 2;
                LinkedList<Entry>[] newBuckets = new LinkedList[bucketSize*2];
                for (K k : this) {
                    V v = get(k);
                    int hC = k.hashCode();
                    int newIndex = Math.floorMod(hC , newBucketSize);
                    if (newBuckets[newIndex] == null) {
                        newBuckets[newIndex] = new LinkedList<>();
                    }
                    newBuckets[newIndex].addFirst(new Entry(k, v));
                }
                buckets = newBuckets;
                bucketSize = newBucketSize;
            }
        }
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySets = new HashSet<>(size);
        for (K k : this) {
            keySets.add(k);
        }
        return keySets;
    }

    @Override
    public Iterator iterator() {
        return new HashMapIter();
    }
    @Override
    public V remove(K key, V value) {
//        throw new UnsupportedOperationException("暂时不支持remove操作");
        int hashCode = key.hashCode();
        int bucketIndex = Math.floorMod(hashCode , bucketSize);
        if (buckets[bucketIndex] != null){
            for (Entry entry : buckets[bucketIndex]) {
                if (entry.k.equals(key)) {
                    if (entry.v.equals(value)) {
                        V returnVal = entry.v;
                        buckets[bucketIndex].remove(entry);
                        return returnVal;
                    }
                    return null;
                }
            }
        }
        return null;
    }

    @Override
    public V remove(K key) {
//        throw new UnsupportedOperationException("暂时不支持remove操作");
        int hashCode = key.hashCode();
        int bucketIndex = Math.floorMod(hashCode , bucketSize);
        if (buckets[bucketIndex] != null){
            for (Entry entry : buckets[bucketIndex]) {
                if (entry.k.equals(key)) {
                    V returnVal = entry.v;
                    buckets[bucketIndex].remove(entry);
                    return returnVal;
                }
            }
        }
        return null;
    }

    private class HashMapIter implements Iterator<K> {
        private int count = 0;
        private int curBucketIndex = 0; //当前的bucket下标
        private int curIndex = 0; //当前linkedList中的下标
        @Override
        public boolean hasNext() {
            return count < size;
        }

        @Override
        public K next() {
            while (curBucketIndex < bucketSize) {
                if (buckets[curBucketIndex] != null) {
                    K returnK = buckets[curBucketIndex].get(curIndex).k;
                    count += 1;
                    curIndex += 1;
                    if (curIndex == buckets[curBucketIndex].size()) {
                        curIndex = 0;
                        curBucketIndex += 1;
                    }
                    return returnK;
                }
                curBucketIndex += 1;
            }
            return null;
        }
    }
}
