/*
 * Datart
 * <p>
 * Copyright 2021
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cn.jmw.utils;

import com.cn.jmw.bean.Source;
import com.cn.jmw.service.SourceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import java.io.Closeable;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Slf4j
public class SchemaSyncJob implements Job, Closeable {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static final String SOURCE_ID = "SOURCE_ID";

    @Override
    public void close() throws IOException {
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String sourceId = (String) context.getMergedJobDataMap().get(SOURCE_ID);
        try {
            Source source = null;
            try {
                source = Application.getBean(SourceService.class).retrieve(sourceId, false);
            } catch (Exception ignored) {
            }
            // remove job if source not exists
            if (source == null) {
                JobKey key = context.getJobDetail().getKey();
                Application.getBean(Scheduler.class).deleteJob(key);
                log.warn("source {} not exists , the job has been deleted ", sourceId);
                return;
            }
            execute(sourceId);
        } catch (Exception e) {
            log.error("source schema sync error ", e);
        }
    }

    public boolean execute(String sourceId) throws Exception {
        return upsertSchemaInfo(sourceId);
    }

    private boolean upsertSchemaInfo(String sourceId) {
        TransactionStatus transaction = TransactionHelper.getTransaction(TransactionDefinition.PROPAGATION_REQUIRES_NEW, TransactionDefinition.ISOLATION_REPEATABLE_READ);
        try {
            TransactionHelper.commit(transaction);
            return true;
        } catch (Exception e) {
            TransactionHelper.rollback(transaction);
            log.error("source schema parse error ", e);
            return false;
        }
    }

}
