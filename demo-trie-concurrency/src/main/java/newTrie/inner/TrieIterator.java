package newTrie.inner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
        if (node.branches != null) {
            for (TrieNode child : node.branches) {
                if (child != null) {
                    stack.push(child);
                }
            }
        }
        return node;
    }
}

//public class TrieIterator implements Iterator<TrieNodePath> {
//    private Stack<TrieNodePath> stack;
//
//    public TrieIterator(TrieNode root) {
//        stack = new Stack<>();
//        if (root != null) {
//            TrieNodePath rootPath = new TrieNodePath();
//            rootPath.addNode(root);
//            stack.push(rootPath);
//        }
//    }
//
//    @Override
//    public boolean hasNext() {
//        return !stack.isEmpty();
//    }
//
//    @Override
//    public TrieNodePath next() {
//        TrieNodePath nodePath = stack.pop();
//        TrieNode node = nodePath.getPath().get(nodePath.getPath().size() - 1);
//        //通过数组
//        if (node.branches != null) {
//            for (TrieNode child : node.branches) {
//                if (child != null) {
//                    TrieNodePath childPath = new TrieNodePath();
//                    childPath.getPath().addAll(nodePath.getPath());
//                    childPath.addNode(child);
//                    stack.push(childPath);
//                }
//            }
//        }
//        return nodePath;
//    }
//
//    class TrieNodePath {
//        private List<TrieNode> path;
//
//        public TrieNodePath() {
//            this.path = new ArrayList<>();
//        }
//
//        public void addNode(TrieNode node) {
//            path.add(node);
//        }
//
//        public List<TrieNode> getPath() {
//            return path;
//        }
//
//        // 其他可能需要的方法，例如获取路径上的所有字符，转换为字符串等
//    }
//}