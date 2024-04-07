package allAlgorithm.并查集;

public class UnionFindSet {

    private int[] parent;
    private int[] rank;

    public UnionFindSet(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
    }

    // find方法是并查集的核心，用于查找元素x的根节点
    public int find(int x) {
        if (x != parent[x]) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    // union方法是并查集的核心，用于合并两个元素x和y
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX != rootY) {
            if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }

    // isConnected方法是并查集的核心，用于判断两个元素x和y是否属于同一个集合
    public boolean isSameSet(int x, int y) {
        return find(x) == find(y);
    }
}
