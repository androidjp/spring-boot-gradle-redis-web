package com.jp.springbootgradleredisweb.redis.services;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.io.IOException;

/**
 * 简单限流器
 * 整体思路就是：每一个行为到来时，都维护一次时间窗口。将时间窗口外的记录全部清理掉，只保留窗口内的记录。
 * zset 集合中只有 score 值非常重要，value 值没有特别的意义，只需要保证它是唯一的就可以了
 *
 * 因为这几个连续的 Redis 操作都是针对同一个 key 的，使用 pipeline 可以显著提升 Redis 存取效率
 */
public class SimpleRateLimiter {
    private Jedis jedis;

    public SimpleRateLimiter(Jedis jedis) {
        this.jedis = jedis;
    }

    public boolean isActionAllowed(String userId, String actionKey, int period, int maxCount) throws IOException {
        String key = String.format("hist:%s:%s", userId, actionKey);
        long nowTs = System.currentTimeMillis();
        Pipeline pipe = jedis.pipelined();
        pipe.multi();
        // 记录行为（value和score都用毫秒时间戳）
        pipe.zadd(key, nowTs, "" + nowTs);
        // 移除时间窗口之前的行为记录，剩下都是时间窗口内的
        pipe.zremrangeByScore(key, 0, nowTs - period * 1000);
        // 获取窗口内的行为数量
        Response<Long> count = pipe.zcard(key);
        // 设置zset过期时间，避免 冷用户 持续占用内存
        pipe.expire(key, period + 1); // 过期时间 = 时间窗口的长度 + 1s
        // 批量执行
        pipe.exec();
        pipe.close();
        return count.get() <= maxCount;
    }
}
