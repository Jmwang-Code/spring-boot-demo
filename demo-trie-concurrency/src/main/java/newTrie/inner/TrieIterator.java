package newTrie.inner;

import java.util.Iterator;
import java.util.Stack;

public class TrieIterator implements Iterator<TrieNode> {
    private Stack<TrieNode> stack;

    public TrieIterator(TrieNode root) {
        stack = new Stack<>();
        if (root != null) {
            stack.push(root);
        }
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public TrieNode next() {
        TrieNode node = stack.pop();
        //通过数组
        if (node.children != null) {
            for (TrieNode child : node.children) {
                if (child != null) {
                    stack.push(child);
                }
            }
        }
        return node;
    }
}