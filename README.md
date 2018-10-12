## Redis
* 定义：是一个开源的使用 ANSI C 语言编写、支持网络、可基于内存亦可持久化的日志型、Key-Value、NoSql 数据库，并提供多种语言的 API。

## SpringBoot Redis基本用法
`spring-boot-starter-data-redis`中的`RedisTemplate`对Jedis的相关redis操作方法进行了高度封装。
* 基本操作类（每次做操作都显示声明key）：
  * ValueOperation: 简单k-v操作
  * SetOperations: set集合操作
  * ZSetOperations, HashOperations, ListOperations
* 一次bound key即可各种操作的操作类：
  * BoundKeyOperations, BoundSetXxxx, BoundListXxxx, BoundHashXxxx
* 默认不开启事务

## 知识点
* `spring-boot-starter-data-redis` 与 `spring-boot-starter-redis` 的区别
  答：SpringBoot从1.4版本开始，`spring-boot-starter-redis` 就改名了，两者都是依赖以下三个库，没有区别：
  1. `org.springframework.boot.spring-boot-starter`
  2. `org.springframework.data.spring-data-redis`
  3. `redis.clients.jedis`

* Jedis与JedisTemplate区别
  * Jedis是Redis官方推荐的面向Java的操作Redis的客户端，而RedisTemplate是SpringDataRedis中对JedisApi的高度封装。
  * SpringDataRedis相对于Jedis来说可以方便地更换Redis的Java客户端，比Jedis多了自动管理连接池的特性，方便与其他Spring框架进行搭配使用如：SpringCache

## 相关库
* 序列化库protostuff（号称比fastjson还要快，大小75%，速度50%）
  * `io.protostuff:protostuff-core:1.6.0`
  * `io.protostuff:protostuff-runtime:1.6.0`

## 效率对比
|Redis input/output方式|写入速度|读取速度|
|:----:|:----:|:----:|
|JedisTemplate原生对象序列化存Redis字符串| ~200ms/1.5MB | 90~120ms |
|Protostuff库序列化存Redis字符串| ~200ms/1.5MB | firstTime:500ms, 45~90ms |
|Fastjson库序列化存Redis字符串| ~200ms/1.5MB | firstTime:1000ms, 110~300ms |

## Redis持久化
> 目的：保证Redis的数据不会因故障而丢失

持久化机制：
![](./images/rdb.png)
* 快照（RDB）
  * 一次全量备份
  * 快照是内存数据的二进制序列化形式，在存储上非常紧凑。
* AOF日志
  * 连续增量备份
  * AOF日志记录的是内存数据修改的指令记录文本。
  * AOF日志 在长期运行过程中，会变得无比庞大，数据库重启时需要加载AOF日志进行指令重放，这个时间相当漫长， 所以需要定期进行AOF重写，给AOF日志进行瘦身。
  
  
 