package newTrie.inter;

import java.util.function.Consumer;
import java.util.function.Function;

import newTrie.inner.TrieNode;

/**
 * 主要Trie中支持ForkJoin的函数
 */
public interface TrieForkJoinInterface {

    //ForEachTrieTask

    /**
     * @param parallelismThreshold 并行阈值
     * @param transformer          转换器
     * @param action               操作
     * @param <U>                  泛型
     */
    public <U> void forEachParallel(int parallelismThreshold, Function<TrieNode, U> transformer, Consumer<U> action);

}
