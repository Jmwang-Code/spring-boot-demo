INFO org.apache.hadoop.mapreduce.JobSubmitter - number of splits:4
说明分为4个分片

1. 比如我们有4个小文件3M、4M、5M、6M，正常使用默认方式分片，每个文件都会分成一个分片，一共4个分片，每个分片都会启动一个map任务，一共4个map任务，这样就会产生4个map任务，
2. 但是我们使用CombineTextInputFormat，设置分片为6M，那么3M和4M的文件会合并成一个分片，5M和6M的文件会合并成一个分片，一共2个分片，每个分片都会启动一个map任务，一共2个map任务，这样就会产生2个map任务，这样就会减少map任务的数量，提高效率。

## CombineTextInputFormat切片是在什么MapReduce什么阶段？
在InputFormat阶段，也就是Map和Shuffle之间。

