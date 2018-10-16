package com.jp.springbootgradleredisweb.redis.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Type;
import java.util.Set;
import java.util.UUID;

public class RedisDelayingQueue<T> {
    static class TaskItem<T> {
        public String id;
        public T msg;
    }

    private Type TaskType = new TypeReference<TaskItem<T>>() {
    }.getType();

    private Jedis jedis;
    private String queueKey;

    public RedisDelayingQueue(Jedis jedis, String queueKey) {
        this.jedis = jedis;
        this.queueKey = queueKey;
    }

    public void delay(T msg) {
        TaskItem<T> task = new TaskItem<>();
        task.id = UUID.randomUUID().toString();
        task.msg = msg;
        String taskSerializedString = JSON.toJSONString(task);
        // 阻塞队列  入队， 5s 后再试
        jedis.zadd(queueKey, System.currentTimeMillis() + 5000, taskSerializedString);
    }

    public void loop() {
        while (!Thread.interrupted()) {
            // 取 [0, 当前时间] 区间中的一条
            Set<String> values = jedis.zrangeByScore(queueKey, 0, System.currentTimeMillis(), 0, 1);
            if (values.isEmpty()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    break;
                }
                continue;
            }
            String taskSerializedString = values.iterator().next();
            if (jedis.zrem(queueKey, taskSerializedString) > 0) {
                // 反序列化
                TaskItem<T> task = JSON.parseObject(taskSerializedString, TaskType);
                this.handleMsg(task.msg);
            }
        }
    }

    private void handleMsg(T msg) {
        System.out.println("Msg:" + msg);
    }
}
