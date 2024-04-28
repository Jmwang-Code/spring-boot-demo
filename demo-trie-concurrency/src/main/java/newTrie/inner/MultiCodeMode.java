package newTrie.inner;

/**
 * @author jmw
 * @Description 节点代码处理器
 */
public enum MultiCodeMode {

    /**
     * 追加模式 (通过codetype的扩展，从而进行一词多码的支持)
     */
    Append,

    /**
     * 替换模式（后加的码替换先加的码）
     */
    Replace,

    /**
     * 丢弃模式（后加的码被丢弃）
     */
    Drop,

    /**
     * 小的码值保留模式（保留码小的）
     */
    Small,

    /**
     * 大的码值保留模式（保留码大的）
     */
    Big,

    /**
     * 抛异常
     */
    ThrowException
}