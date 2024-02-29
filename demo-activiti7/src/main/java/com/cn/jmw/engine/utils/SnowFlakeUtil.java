package com.cn.jmw.engine.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

import java.net.Inet4Address;

/**
 * 雪花算法
 * 生成唯一ID
 */
public class SnowFlakeUtil {
    private static Snowflake secondSnow = IdUtil.getSnowflake(getWorkId(),getDataCenterId());

    public static void main(String[] args) {
        System.out.println(getSecondId());
    }

    public static Long getNumberId(){
        return secondSnow.nextId();
    }

    public static String getSecondId(){
        return secondSnow.nextIdStr();
    }

    private static Long getWorkId() {
        try {
            String hostAddress = Inet4Address.getLocalHost().getHostAddress();
            int[] ints = StringUtils.toCodePoints(hostAddress);
            int sums = 0;
            for (int b : ints) {
                sums = sums + b;
            }
            return (long) (sums % 32);
        }
        catch (Exception e) {
            // 失败就随机
            return RandomUtils.nextLong(0, 31);
        }
    }


    /**
     * dataCenterId使用hostName生成
     * @return dataCenterId
     */
    private static Long getDataCenterId() {
        try {
            String hostName = SystemUtils.getHostName();
            int[] ints = StringUtils.toCodePoints(hostName);
            int sums = 0;
            for (int i: ints) {
                sums = sums + i;
            }
            return (long) (sums % 32);
        }
        catch (Exception e) {
            // 失败就随机
            return RandomUtils.nextLong(0, 31);
        }
    }
}
