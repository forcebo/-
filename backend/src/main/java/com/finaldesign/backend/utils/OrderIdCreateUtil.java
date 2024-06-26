package com.finaldesign.backend.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class OrderIdCreateUtil {
    //开始时间戳
    private static final long BEGIN_TIMESTAMP = 1672531200L;
    //序列号位数
    private static final int  COUNT_BITS = 16;
    private static long count = 0;

    public static long nextId() {
        // 1. 生成时间戳
        LocalDateTime now = LocalDateTime.now();
        long nowSecond = now.toEpochSecond(ZoneOffset.UTC);
        long timestamp = nowSecond - BEGIN_TIMESTAMP;
        // 2. 生成序列号
        // 2.2 自增长
        count++;
        // 3. 拼接并返回
        return timestamp << COUNT_BITS | count;
    }
}