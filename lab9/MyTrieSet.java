import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName MyTrieSet
 * @Description TODO
 * @Author hao6
 * @Data 11/13/19 7:56 PM
 * @Version 1.0
 **/
public class MyTrieSet implements TrieSet61B {

    private int size;
    private Node root;

    private static class Node {
        private Map<Character, Node> next; // 用来存储下一代的引用
        private boolean isKey;
        public Node() {
            next = new HashMap<>();
            isKey = false;
        }

        public Node(boolean isK) {
            isKey = isK;
            next = new HashMap<>();
        }
    }

    public MyTrieSet() {
        size = 0;
        root = new Node();
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean contains(String key) {
        Node curNode = root;
        if (key != null) {
            int keyLens = key.length();
            for (int i = 0; i < keyLens - 1; i += 1) {
                if (curNode != null && curNode.next.containsKey(key.charAt(i))) {
                    curNode = curNode.next.get(key.charAt(i));
                } else {
                    return false;
                }
            }
            if (curNode.next != null && curNode.next.containsKey(key.charAt(keyLens-1))
                    && curNode.next.get(key.charAt(keyLens-1)).isKey) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void add(String key) {
        Node curNode = root;
        if (key != null) {
            int keyLens = key.length();
            for (int i = 0; i < keyLens - 1; i += 1) {
                if (!curNode.next.containsKey(key.charAt(i))) {
                    curNode.next.put(key.charAt(i), new Node());
                }
                curNode = curNode.next.get(key.charAt(i));
            }
            if (!curNode.next.containsKey(key.charAt(keyLens-1))) {
                curNode.next.put(key.charAt(keyLens-1), new Node(true));
            } else {
                curNode.next.get(key.charAt(keyLens-1)).isKey = true;
            }
            size += 1;
        }
    }

    private void colHelp(String s, List<String> x, Node n) {
        if (n.isKey) {
            x.add(s);
        }
        for (char c : n.next.keySet()) {
            colHelp(s+String.valueOf(c), x, n.next.get(c));
        }
    }
    private List<String> collect(String prefix, Node curNode) {
        List<String> x = new ArrayList<>();
        colHelp(prefix, x, curNode);
        return x;
    }

    @Override
    public List<String> keysWithPrefix(String prefix) {
        List<String> prefixList = new ArrayList<>();
        if (prefix != null) {
            Node curNode = root;
            int prefixLen = prefix.length();
            for (int i = 0; i < prefixLen; i += 1) {
                if (curNode.next.containsKey(prefix.charAt(i))) {
                    curNode = curNode.next.get(prefix.charAt(i));
                }
            }
            prefixList = collect(prefix, curNode);
            if (curNode.isKey) {
                prefixList.add(prefix);
            }
        }
        return prefixList;
    }

    @Override
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException("暂时不支持该操作");
    }
}
