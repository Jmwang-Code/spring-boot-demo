package newTrie.inter;

import newTrie.inner.TrieNode;

import java.util.function.Predicate;

/**
 * 主要Trie中支持ForkJoin的函数
 */
public interface SearchForkJoinInterface extends ForkJoinInterface {

    //SearchTrieTask

    public TrieNode searchParallel(int parallelismThreshold);
}
