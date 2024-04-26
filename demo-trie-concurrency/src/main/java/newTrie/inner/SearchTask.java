package newTrie.inner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;


public class SearchTask extends RecursiveTask<TrieNode> {
    private final TrieNode node;
    private final List<Integer> path;  // The path from the root to the current node

    public SearchTask(TrieNode node, List<Integer> path) {
        this.node = node;
        this.path = new ArrayList<>(path);  // Create a copy of the path
    }

    @Override
    protected TrieNode compute() {
        if (node.isEnd == 1 || node.isEnd == 2) {
            // If the current node satisfies the condition, return it
            return node;
        } else {
            // Otherwise, create new tasks for all children
            List<SearchTask> tasks = new ArrayList<>();
            for (int i = 0; i < node.children.length; i++) {
                TrieNode child = node.children[i];
                if (child != null) {
                    List<Integer> childPath = new ArrayList<>(path);
                    childPath.add(i);  // Add the character of the child to the path
                    SearchTask task = new SearchTask(child, childPath);
                    task.fork();  // Start the task in a separate thread
                    tasks.add(task);
                }
            }
            // Wait for all tasks to finish and check their results
            for (SearchTask task : tasks) {
                TrieNode result = task.join();  // Wait for the task to finish and get its result
                if (result != null) {
                    // If a task found a node that satisfies the condition, return it
                    return result;
                }
            }
            // If no tasks found a node that satisfies the condition, return null
            return null;
        }
    }
}