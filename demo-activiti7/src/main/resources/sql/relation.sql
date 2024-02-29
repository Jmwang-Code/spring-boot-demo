--  1.流程定义信息表
CREATE TABLE process_definition
(
    process_id    VARCHAR(64) PRIMARY KEY,
    process_name  VARCHAR(255) NOT NULL,
    version       INT          NOT NULL,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    custom_field1 VARCHAR(255),
    custom_field2 INT
);

-- process_id: Activiti 7 流程定义的唯一标识符。
-- process_name: Activiti 7 流程定义的名称。
-- version: Activiti 7 流程定义的版本。
-- created_at: Activiti 7 流程定义的创建时间。
-- custom_field1, custom_field2: 您自定义的业务字段，根据需要添加。

-- 2. 任务信息表
CREATE TABLE task_info
(
    task_id       VARCHAR(64) PRIMARY KEY,
    process_id    VARCHAR(64)  NOT NULL,
    task_name     VARCHAR(255) NOT NULL,
    assignee      VARCHAR(64),
    status        VARCHAR(20)  NOT NULL,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    custom_field3 TEXT,
    custom_field4 BOOLEAN
);

-- task_id: Activiti 7 任务的唯一标识符。
-- process_id: 任务所属流程定义的唯一标识符。
-- task_name: 任务的名称。
-- assignee: 任务的执行人。
-- status: 任务的状态（例如，待处理、已完成等）。
-- created_at: 任务创建时间。
-- custom_field3, custom_field4: 您自定义的业务字段，根据需要添加。

-- 3. 历史记录表
CREATE TABLE workflow_history
(
    history_id    VARCHAR(64) PRIMARY KEY,
    process_id    VARCHAR(64) NOT NULL,
    task_id       VARCHAR(64) NOT NULL,
    action        VARCHAR(20) NOT NULL,
    comment       TEXT,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    custom_field5 INT,
    custom_field6 VARCHAR(100)
);

-- history_id: 历史记录的唯一标识符。
-- process_id: 相关流程的唯一标识符。
-- task_id: 相关任务的唯一标识符。
-- action: 执行的动作（例如，启动、审批通过、审批拒绝等）。
-- comment: 相关评论或备注。
-- created_at: 记录创建时间。
-- custom_field5, custom_field6: 您自定义的业务字段，根据需要添加。

-- 表关系
-- process_definition.process_id 与 task_info.process_id 之间是一对多关系，表示一个流程定义可以有多个任务。
-- task_info.task_id 与 workflow_history.task_id 之间是一对多关系，表示一个任务可以有多条历史记录。