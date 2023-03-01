package com.cn.jmw;

import com.cn.jmw.config.TrieConfig;
import com.cn.jmw.property.myreader.ProfileSelector;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Properties;

/**
 * @author jmw
 * @Description 配置文件选择器单元测试
 * @date 2022年12月12日 20:15
 * @Version 1.0
 */
public class ProfileSelectorTest {

    @Test
    public void test() {
        //YML只能转换成任意（Hashmap）
        HashMap choose = ProfileSelector.choose("/properties/application.yml", HashMap.class);
        System.out.println(choose);
        //Properties只能转换成(Properties)
        Properties choose1 = ProfileSelector.choose("/properties/hello.properties", Properties.class);
        System.out.println(choose1);
        //xml转换成任意对象需要和注解一起使用，转换成Map、JSON不需要
        HashMap choose2 = ProfileSelector.choose("/properties/triedata-config.xml", HashMap.class);
        System.out.println(choose2);

        TrieConfig trieConfig = ProfileSelector.choose("/properties/triedata-config.xml", TrieConfig.class);
        System.out.println(trieConfig);
    }
}
