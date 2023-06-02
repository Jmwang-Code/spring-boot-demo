package com.cn.jmw.service;

import com.cn.jmw.bean.Source;
import org.springframework.stereotype.Service;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年06月02日 16:43
 * @Version 1.0
 */
@Service
public class SourceService {

    public Source retrieve(String sourceId, boolean b) {
        Source source = new Source(sourceId,b);
        System.out.println("test");
        return source;
    }
}
