package allAlgorithm.并查集;

/**
 * 正常情况下并查集
 * 1. 扁平化
 * 2. 小挂大
 */

/**
 * 演示的是 简化扁平化的并查集
 */
public class UnionFindSet {

    //表示指针,一开始都是指向自己
    private int[] parent;

    public UnionFindSet(int n) {
        parent = new int[n+1];
        for (int i = 0; i < n+1; i++) {
            parent[i] = i;
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
        parent[find(x)] = find(y);
    }

    // isConnected方法是并查集的核心，用于判断两个元素x和y是否属于同一个集合
    public boolean isSameSet(int x, int y) {
        return find(x) == find(y);
    }
}
