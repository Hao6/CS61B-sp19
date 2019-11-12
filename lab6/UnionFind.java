public class UnionFind {

    // TODO - Add instance variables?
    private int[] parent;
    private int[] size;
    private int buffSize;

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        // TODO
        parent = new int[n];
        size = new int[n];
        buffSize = n;
        for (int i = 0; i < n; i += 1) {
            parent[i] = -1;
            size[i] = 1;
        }
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex){
        // TODO
        if (vertex < 0 || vertex >= buffSize) {
            throw new IllegalArgumentException();
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int v1) {
        validate(v1);
        int temp = v1;
        while (parent[v1] != -1) {
            v1 = parent[v1];
        }
        int temp1;
        if (temp != v1) {
            while (parent[temp] != v1) { // 路径压缩
                temp1 = parent[temp];
                parent[temp] = v1;
                temp = temp1;
            }
        }
        return v1;
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        // TODO
        return size[find(v1)];
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        validate(v1);
        if (parent[v1] == -1) {
            return -size[v1];
        }
        return parent[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        // TODO
        return find(v1) == find(v2);
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        int root1 = find(v1);
        int root2 = find(v2);

        if (size[root1] > size[root2]) {
            size[root1] += size[root2];
            parent[root2] = root1;
        }else{
            size[root2] += size[root1];
            parent[root1] = root2;
        }


    }

}
