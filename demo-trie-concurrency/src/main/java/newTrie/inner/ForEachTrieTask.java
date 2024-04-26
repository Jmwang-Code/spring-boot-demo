package newTrie.inner;


import java.util.concurrent.RecursiveAction;
import java.util.concurrent.Semaphore;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 用于并行遍历前缀树的任务。
 */
public final class ForEachTrieTask<U> extends RecursiveAction {
    private final TrieNode node; // 节点
    private final Function<TrieNode, U> transformer; // 转换器
    private final Consumer<U> action; // 操作
    private final Semaphore semaphore; // 信号量


    public ForEachTrieTask(TrieNode node, Function<TrieNode, U> transformer, Consumer<U> action, Semaphore semaphore) {
        this.node = node;
        this.action = action;
        this.semaphore = semaphore;
        this.transformer = transformer;
    }

    @Override
    protected void compute() {
        try {
            // 获取许可
            semaphore.acquire();

            // 对当前节点应用操作
            action.accept(transformer.apply(node));

            // 对每个子节点创建并执行新的任务
            for (TrieNode child : node.children) {
                if (child != null) {
                    new ForEachTrieTask<>(child, transformer, action, semaphore).fork();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            // 释放许可
            semaphore.release();
        }
    }
}
