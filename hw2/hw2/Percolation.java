package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private short[][] perGrid;
    private int numOfOpenSites = 0;
    private int size;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N应该是一个大于0的整数");
        }
        perGrid = new short[N][N];
        size = N;
        for (int i = 0; i< N;i += 1) {
            for (int j = 0; j < N; j += 1) {
                perGrid[i][j] = 0; // 表示blocked
            }
        }
    }
    private boolean isValidIds(int row, int col) {
        return (row >= 0 && row < size && col >= 0 && col < size);
    }

    public boolean isOpen(int row, int col) {
        if (!isValidIds(row, col)) {
            throw new IllegalArgumentException("row与col越界");
        }
        return perGrid[row][col] >= 1;
    }

    public boolean isFull(int row, int col) {
        if (!isValidIds(row, col)) {
            throw new IllegalArgumentException("row与col越界");
        }
        return perGrid[row][col] == 2;
    }

    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            perGrid[row][col] = 1;
            numOfOpenSites += 1;
        }
    }

    public void full(int col) {
        if (isOpen(0, col)) {
            perGrid[0][col] = 2;
        }
    }

    public int numberOfOpenSites() {
        return numOfOpenSites;
    }

    public boolean percolates() {
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(size*size);
        int root = -1;
        for (int i = 0; i < size-1; i += 1) {
            if (isFull(0, i)) {
                if (root == -1) { // 如果并查集的根（代表）还没有选出
                    root = i; // 并查集的根（代表）
                }else{ // 讲该节点加入并查集
                    uf.union(root, i);
                }
            }
        }
        if (root == -1) { //第一行不存在full状态的grid
            return false;
        }else{
            if (size == 1) { //只有一层
                return true;
            }
        }

        for (int i = 1; i < size; i += 1) {
            int ufSize = uf.count();
            // 从左向右扫描，根据left,up确定是否加入并查集
            for (int j = 0; j < size; j += 1) {
                if (isOpen(i, j)) {
                    if (uf.connected((i-1)*size+j, root) ||
                            isValidIds(i,j-1) && uf.connected(root, i*size+j-1)) {
                        uf.union(root, i*size+j);
                    }
                }
            }
            //从右向左扫描，
            for (int j = size-2; j >= 0; j -= 1) { // 根据right确定是否加入并查集
                if (isOpen(i, j)) {
                    if (uf.connected(root, i*size+j+1) && ! uf.connected(root, i*size+j)) {
                        uf.union(root, i*size+1);
                    }
                }
            }
            if (ufSize == uf.count()) { // 没有发生union,说明当前层没有激活的full状态
                return false;
            }
        }
        // 最后一行，从左向右扫描，当前状态是激活态且up一格是激活态，则返回true
        for (int i = 0; i < size; i += 1) {
            if (isOpen(size-1, i) && uf.connected(root, (size-2)*size+i)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

    }





}
