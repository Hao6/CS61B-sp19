import java.util.Iterator;
import java.util.Set;

/**
 * @ClassName BSTMap
 * @Description TODO
 * @Author hao6
 * @Data 11/9/19 3:03 PM
 * @Version 1.0
 **/
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>{

    private int size; // 保存已存key-value的数量
    private Entry tree; // 根结点

    private class Entry {
        K key;
        V val;
        Entry left;
        Entry right;

        Entry(K k, V v, Entry l, Entry r) {
            key = k;
            val = v;
            left = l;
            right = r;
        }

        Entry(K k, V v) {
            this(k, v, null, null);
        }

        Entry get(K k) {
            if (k == null) {
                return null;
            } else {
                if (k.compareTo(key) == 0) {
                    return this;
                }
                if (left != null && k.compareTo(key) < 0) {
                    return left.get(k);
                } else if (right != null && k.compareTo(key) > 0) {
                    return right.get(k);
                }
                return null;
            }
        }

        void printInOrder() { // 中序遍历
            if (left != null) {
                left.printInOrder();
            }
            System.out.printf(val+" ");
            if (right != null) {
                right.printInOrder();
            }
        }

        void put(K k, V v) {
            if (k.compareTo(key) == 0) {
                val = v;
                return;
            } else if (k.compareTo(key) < 0) {
                if (left == null) {
                    left = new Entry(k, v);
                    size += 1;
                    return;
                } else {
                    left.put(k, v);
                }
            } else {
                if (right == null) {
                    right = new Entry(k, v);
                    size += 1;
                    return;
                } else {
                    right.put(k, v);
                }
            }
        }
    }

    private class BSTMapIter implements Iterator<K> {
        @Override
        public boolean hasNext () {
            return false;
        }

        @Override
        public K next() {
            return null;
        }
    }

    public BSTMap() {
        tree = null;
        size = 0;
    }

    public void printInOrder() {
        // TODO
        // 按照key值的升序打印value
        tree.printInOrder();
    }

    @Override
    public void clear() {
        size = 0;
        tree = null;
    }

    @Override
    public boolean containsKey(K key) {
        if (tree == null) {
            return false;
        }
        return tree.get(key) != null;
    }

    @Override
    public V get(K key) {
        if (tree == null) {
            return null;
        }
        Entry lookup = tree.get(key);

        if (lookup == null) {
            return null;
        }
        return lookup.val;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        if (tree == null) {
            tree = new Entry(key, value);
            size += 1;
        }else {
            tree.put(key, value);
        }

    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException("暂时不支持keySet操作");
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("暂时不支持remove操作");
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException("暂时不支持remove操作");
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTMapIter();
    }
}
