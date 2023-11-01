package com.cn.jmw;

import cn.hutool.core.util.StrUtil;
import com.cn.jmw.aspect.Loggable;
import com.cn.jmw.utils.IPUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpRequest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 一只小小狗
 * @version 1.0.0
 * @ClassName SpringBootDemoHelloworldApplication.java
 * @Description
 * @createTime 2023年02月21日 01:30:00
 */
@SpringBootApplication
@RestController
@EnableScheduling
@EnableAspectJAutoProxy
public class SpringBootDemoHelloworldApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoHelloworldApplication.class, args);
    }

    /**
     * Hello，World
     *
     * @param who 参数，非必须
     * @return Hello, ${who}
     * http://localhost:8080/demo/hello?who=jmw
     */
    @GetMapping("/hello")
    @Loggable
    public String sayHello(@RequestParam(required = false, name = "who") String who
    , HttpServletRequest request) {
        if (StrUtil.isBlank(who)) {
            who = "World";
        }
        String ipAddress = IPUtils.getIpAddress(request);
        return StrUtil.format("Hello, {}!", who);
    }

    /**
     * 定时器冻结检查，检查REDIS和DB消息
     *
     * @Date 2023/3/13 16:21
     */
    @Scheduled(cron = "* * * * * ?")
    public void freezeCheck() {
//        System.out.println(1);
    }

}
