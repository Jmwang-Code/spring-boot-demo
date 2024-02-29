# 项目准备

# 1. 实体识别树
| \                                   | <span style="color:purple">实体识别树                                                      | 
|-------------------------------------|--------------------------------------------------------------------------------------|
| <span style="color:red">**组件理论**  | 缓存机制 日志先行机制 数据源内核（这个可以优化自动加载还是手动都是加锁在查询器上，如果让锁粒度细化到每个节点上，并行效率就大大提高了） 荷官（哨兵机制） 备用主从机制 |
| <span style="color:red">**问题定位**  | 1.实体识别树补全实体识别树之外的空缺 2.希望减少在结构化中的耗时，将耗时功能都简化 3.树之间的数据一致性 4.数据恢复问题                     |
| <span style="color:red">**调优配置**  | 使用G1解决大内存FULL GC的问题                                                                  |
| <span style="color:red">**基础理论**  | 字典树、前缀树核心算法（以空间换时间）                                                                  |
| <span style="color:red">**项目场景**  | 在大数据的场景下需要更快的检索速度。              |
| <span style="color:red">**特色问题1** | 这个东西会比那ES快么？    并不是他的效率远超ES。内存占用也远超，因为不做磁盘IO。                                        |
| <span style="color:red">**特色问题2** | 这个节点会不会形成环？    目前没有见过这个问题，如果出现会优化算法。                                                 |

G1 :java -XX:+UseG1GC -Xmx40g -XX:ConcGCThreads=8 -XX:G1HeapRegionSize=32m -XX:MaxGCPauseMillis=200 -XX:G1NewSizePercent=40
垃圾回收时间控制在200ms左右，内存分配率控制在 40% 左右，

**G1 垃圾回收器相对于 JDK8 默认垃圾回收器（Parallel GC）的好处主要有以下几点：**
- 高效：G1 垃圾回收器是一种面向大堆的垃圾回收器，可以有效地处理大型内存和多核处理器的情况，具有高效、低延迟、可预测的特点。
- 低延迟：G1 垃圾回收器采用分代回收和并发标记-整理算法，可以在不影响应用程序运行的情况下进行垃圾回收，从而实现低延迟的垃圾回收。
- 可预测：G1 垃圾回收器可以根据实际情况动态调整垃圾回收策略和参数，从而实现可预测的垃圾回收。
- 避免全局停顿：G1 垃圾回收器可以将堆内存分成多个区域，每个区域独立进行垃圾回收，从而避免全局停顿的情况。
- 避免内存碎片：G1 垃圾回收器采用分代回收和并发标记-整理算法，可以避免内存碎片的情况，从而提高了内存利用率和性能。

# 2. 数据报表

| \                                   | <span style="color:purple">数据报表                                  | 
|-------------------------------------|------------------------------------------------------------------|
| <span style="color:red">**组件理论**  | SPI机制（方便实现和扩展） 关系型和非关系型抽象多层（加亿层） 包容各种数据源                         |
| <span style="color:red">**问题定位**  | 1.各种数据源的扩展抽象设计 2.新增一种也可以直接通过简单实现SPI接口进行插槽对接 3.非关系型转换成关系型展示，并且持久化 |
| <span style="color:red">**调优配置**  | 简单的非标参数最大最小堆设置                                                   |
| <span style="color:red">**基础理论**  | 工厂+单例+策略+SPI                                                     |
| <span style="color:red">**项目场景**  | 1.为报表提供各种各样的数据源                                                  |
| <span style="color:red">**特色问题1** |                                                                  |
| <span style="color:red">**特色问题2** |                                                                  |

# 3. 中台数据调度

| \                                   | <span style="color:purple">中台数据调度 | 
|-------------------------------------|---------------------------------|
| <span style="color:red">**组件理论**  |                                 |
| <span style="color:red">**问题定位**  |                                 |
| <span style="color:red">**调优配置**  |                                 |
| <span style="color:red">**基础理论**  |                                 |
| <span style="color:red">**项目场景**  |                                 |
| <span style="color:red">**特色问题1** |                                 |
| <span style="color:red">**特色问题2** |                                 |

1. 通过Aviator Flink定时批 和 Flink SQL流任务，通过算子拼接，实现数据的实时和离线的同步。
2. Nebula数据入ck，完成CMDB大宽表的数据处理
   将多个关系的图数据库转换为大宽表导入 ClickHouse，可以采用以下两种方式：

   1. 使用 ETL 工具进行转换：可以使用 ETL 工具（例如 Apache Spark、Apache Flink 等）将多个关系的图数据库转换为大宽表，然后再将大宽表导入 ClickHouse 中。具体来说，可以将多个关系的图数据库中的节点和边转换为表格形式，然后将这些表格进行合并和关联，最终得到大宽表。然后，可以使用 ClickHouse 的数据导入工具将大宽表导入到 ClickHouse 中。
   2. 使用图数据库的导出工具进行转换：可以使用多个关系的图数据库的导出工具将数据导出为 CSV 格式的文件，然后使用 ETL 工具将这些 CSV 文件转换为大宽表，最终将大宽表导入 ClickHouse 中。具体来说，可以使用多个关系的图数据库的导出工具将节点和边导出为 CSV 格式的文件，然后使用 ETL 工具将这些 CSV 文件进行合并和关联，最终得到大宽表。然后，可以使用 ClickHouse 的数据导入工具将大宽表导入到 ClickHouse 中。

3. Flink-CDC数据迁移
   具体来说，Flink-CDC 可以通过以下步骤实现数据迁移：

   1. 监听源数据库的变更：Flink-CDC 可以通过监听源数据库的 binlog（MySQL）或者 oplog（MongoDB）来实时捕获源数据库的变更。
   2. 将变更数据转换为流数据：Flink-CDC 可以将捕获的变更数据转换为流数据，并进行格式化和清洗，以便于后续的处理和传输。
   3. 将流数据传输到目标数据库中：Flink-CDC 可以将转换后的流数据传输到目标数据库中，并进行数据的插入、更新、删除等操作，从而实现数据的实时同步和迁移。


# 1. 具体实现
## 1.1 Nebula数据入ck，完成CMDB大宽表的数据处理
**使用 Flink 将 Nebula Graph 中的数据导入到 ClickHouse 中，可以采用以下步骤实现：**

1. 使用 Nebula Graph 的导出工具将数据导出为 CSV 格式的文件。

2. 使用 Flink 的 CSV 格式读取器将 CSV 文件读取为 Flink 的 DataStream。

3. 使用 Flink 的转换算子对 DataStream 进行转换，将 Nebula Graph 中的数据转换为 ClickHouse 中的数据格式。

4. 使用 Flink 的 ClickHouse 格式写入器将转换后的数据写入到 ClickHouse 中。

**具体的实现步骤如下：**

1. 使用 Nebula Graph 的导出工具将数据导出为 CSV 格式的文件。可以使用 Nebula Graph 的命令行工具 nebula-exporter 来导出数据。例如，可以使用以下命令将 Nebula Graph 中的数据导出为 CSV 格式的文件：

```java
nebula-exporter --config /path/to/nebula-exporter.conf --type csv --output /path/to/output/dir
```
其中，/path/to/nebula-exporter.conf 是 Nebula Graph 的配置文件路径，--type csv 表示导出为 CSV 格式，/path/to/output/dir 是导出文件的输出目录。

2. 使用 Flink 的 CSV 格式读取器将 CSV 文件读取为 Flink 的 DataStream。可以使用 Flink 的 CsvSource 算子来读取 CSV 文件。例如，可以使用以下代码将 CSV 文件读取为 Flink 的 DataStream：

```java
DataStream<String> csvStream = env.readTextFile("/path/to/csv/file");
```
其中，env 是 Flink 的执行环境，/path/to/csv/file 是导出的 CSV 文件路径。

3. 使用 Flink 的转换算子对 DataStream 进行转换，将 Nebula Graph 中的数据转换为 ClickHouse 中的数据格式。具体的转换方式需要根据具体的业务需求和场景进行选择和配置。例如，可以使用 Flink 的 Map 算子将 CSV 格式的数据转换为 ClickHouse 格式的数据。例如，可以使用以下代码将 CSV 格式的数据转换为 ClickHouse 格式的数据：

```java
DataStream<Row> clickHouseStream = csvStream.map(new MapFunction<String, Row>() {
    @Override
    public Row map(String value) throws Exception {
        // 将 CSV 格式的数据转换为 ClickHouse 格式的数据
        // ...
        return row;
    }
});
```

4. 使用 Flink 的 ClickHouse 格式写入器将转换后的数据写入到 ClickHouse 中。可以使用 Flink 的 ClickHouseOutputFormat 来将数据写入到 ClickHouse 中。例如，可以使用以下代码将转换后的数据写入到 ClickHouse 中：

```java
clickHouseStream.writeUsingOutputFormat(new ClickHouseOutputFormat(url, username, password));
```

其中，url、username 和 password 分别是 ClickHouse 的连接信息。

## 1.2 Flink-CDC数据迁移

**使用 Flink-CDC 将数据从源数据库迁移到目标数据库，可以采用以下步骤实现：**

1. 配置 Flink-CDC，实时捕获源数据库的变更。

2. 将捕获的变更数据转换为流数据，并进行格式化和清洗。

3. 将转换后的流数据传输到目标数据库中，并进行数据的插入、更新、删除等操作。

**具体的实现步骤如下：**

1. 配置 Flink-CDC，实时捕获源数据库的变更。可以使用 Flink-CDC 的官方提供的 connectors，例如 MySQL、PostgreSQL、MongoDB 等。以 MySQL 为例，可以使用以下代码配置 Flink-CDC：
```java
Properties properties = new Properties();
properties.setProperty("connector", "mysql-cdc");
properties.setProperty("hostname", "localhost");
properties.setProperty("port", "3306");
properties.setProperty("username", "root");
properties.setProperty("password", "password");
properties.setProperty("database-name", "database_name");

DataStream<Row> stream = env.addSource(new FlinkCDCSource(properties));
```
其中，env 是 Flink 的执行环境，properties 是 MySQL 的连接信息。

2. 将捕获的变更数据转换为流数据，并进行格式化和清洗。可以使用 Flink 的转换算子对 DataStream 进行转换，将变更数据转换为目标数据库中的数据格式。例如，可以使用 Flink 的 Map 算子将 MySQL 中的数据转换为 ClickHouse 中的数据格式。例如，可以使用以下代码将 MySQL 中的数据转换为 ClickHouse 中的数据格式：
```java
DataStream<Row> clickHouseStream = stream.map(new MapFunction<Row, Row>() {
    @Override
    public Row map(Row value) throws Exception {
        // 将 MySQL 中的数据转换为 ClickHouse 中的数据格式
        // ...
        return row;
    }
});
```
3. 将转换后的流数据传输到目标数据库中，并进行数据的插入、更新、删除等操作。可以使用 Flink 的 ClickHouse 格式写入器将转换后的数据写入到 ClickHouse 中。例如，可以使用以下代码将转换后的数据写入到 ClickHouse 中：
```java
clickHouseStream.writeUsingOutputFormat(new ClickHouseOutputFormat(url, username, password));
```
其中，url、username 和 password 分别是 ClickHouse 的连接信息。



# 5. 大数据法律监督平台-线索模块
| \                                   | <span style="color:purple">大数据法律监督平台-线索模块                                | 
|-------------------------------------|------------------------------------------------------------------|
| <span style="color:red">**组件理论**  | SPI机制（方便实现和扩展） 关系型和非关系型抽象多层（加亿层） 包容各种数据源                         |
| <span style="color:red">**问题定位**  | 1.各种数据源的扩展抽象设计 2.新增一种也可以直接通过简单实现SPI接口进行插槽对接 3.非关系型转换成关系型展示，并且持久化 |
| <span style="color:red">**调优配置**  | 简单的非标参数最大最小堆设置                                                   |
| <span style="color:red">**基础理论**  | 工厂+单例+策略+SPI                                                     |
| <span style="color:red">**项目场景**  | 1.为报表提供各种各样的数据源                                                  |
| <span style="color:red">**特色问题1** |                                                                  |
| <span style="color:red">**特色问题2** |                                                                  |
