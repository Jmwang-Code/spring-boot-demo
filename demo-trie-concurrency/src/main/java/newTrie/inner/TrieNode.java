package newTrie.inner;

public class TrieNode {
    public char c;
    public TrieNode[] children;

    // Node的其他代码...

    public TrieNode(){}

    // 复制构造函数
    public TrieNode(TrieNode node) {
        this.c = node.c;
        //通过数组递归
        if (node.children != null) {
            this.children = new TrieNode[node.children.length];
            for (int i = 0; i < node.children.length; i++) {
                if (node.children[i] != null) {
                    this.children[i] = new TrieNode(node.children[i]);
                }
            }
        }
    }
}
