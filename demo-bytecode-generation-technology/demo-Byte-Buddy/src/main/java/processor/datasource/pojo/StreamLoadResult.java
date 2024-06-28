package processor.datasource.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StreamLoadResult {
    @JsonProperty("TxnId")
    private long txnId; // 交易ID

    @JsonProperty("Label")
    private String label; // 标签

    @JsonProperty("Comment")
    private String comment; // 注释

    @JsonProperty("TwoPhaseCommit")
    private String twoPhaseCommit; // 是否两阶段提交

    @JsonProperty("Status")
    private String status; // 状态

    @JsonProperty("Message")
    private String message; // 消息

    @JsonProperty("NumberTotalRows")
    private int numberTotalRows; // 总行数

    @JsonProperty("NumberLoadedRows")
    private int numberLoadedRows; // 已加载行数

    @JsonProperty("NumberFilteredRows")
    private int numberFilteredRows; // 已过滤行数

    @JsonProperty("NumberUnselectedRows")
    private int numberUnselectedRows; // 未选择行数

    @JsonProperty("LoadBytes")
    private long loadBytes; // 加载字节数

    @JsonProperty("LoadTimeMs")
    private long loadTimeMs; // 加载时间（毫秒）

    @JsonProperty("BeginTxnTimeMs")
    private long beginTxnTimeMs; // 开始事务时间（毫秒）

    @JsonProperty("StreamLoadPutTimeMs")
    private long streamLoadPutTimeMs; // 流加载PUT时间（毫秒）

    @JsonProperty("ReadDataTimeMs")
    private long readDataTimeMs; // 读取数据时间（毫秒）

    @JsonProperty("WriteDataTimeMs")
    private long writeDataTimeMs; // 写入数据时间（毫秒）

    @JsonProperty("CommitAndPublishTimeMs")
    private long commitAndPublishTimeMs; // 提交和发布时间（毫秒）
}