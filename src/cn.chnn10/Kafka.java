package cn.chnn10;

public class Kafka {
    /**
     * Kafka基本概念
     *      什么是kafka
     *      Kakfa为什么这么快
     *      Kafka局限性
     *      Kafka有哪些组件
     *      kakfa的架构
     *      为什么使用消息系统，MySQL不能吗
     *      Zookeeper对于Kafka的作用是什么？可以不用zookeeper么
     *      kafka中的broker 是干什么的
     *      Consumer与topic关系
     *      请说明Kafka相对传统技术有什么优势?
     *
     * Kafka生产和消费
     *      批量发送
     *      压缩
     *      如何获取topic主题的列表
     *      生产者和消费者的命令行是什么？
     *      你知道 Kafka 是如何做到消息的有序性？
     *      kafka分布式（不是单机）的情况下，如何保证消息的顺序消费？
     *      kafka如何减少数据丢失？
     *      kafka如何不消费重复数据？比如扣款，我们不能重复的扣？
     *      消费者如何不自动提交偏移量，由应用提交？
     *      消费者故障，出现活锁问题如何解决？
     *      如何控制消费的位置？
     *      消费者和消费者组有什么关系？
     *      Kafka消息是采用Pull模式，还是Push模式？
     *      谈谈你对 Kafka 幂等的了解？
     *      如何设置Kafka能接收的最大消息的大小？
     *      当Kafka消息数据出现了积压，应该怎么处理？
     **
     * Kakfa主题
     *      Kafka创建Topic时如何将分区放置到不同的Broker中
     *
     * Kafka分区
     *      谈谈 Kafka 分区分配策略
     *      Kafka 的每个分区只能被一个消费者线程，如何做到多个线程同时消费一个分区？
     *      Kafka 消费者是否可以消费指定分区消息？
     *      Kafka新建的分区会在哪个目录下创建
     *
     * Kafka集群
     *      说一下主从同步
     *      kafka的高可用机制是什么？
     *
     *
     * Kafka原理
     *      讲一讲kafka的ack的三种机制
     *      说一下Kafka的rebalance
     *      负载均衡（partition会均衡分布到不同broker上）
     *      请谈一谈 Kafka 数据一致性原理
     *      怎么尽可能保证 Kafka 的可靠性
     *      数据传输的事务有几种？
     *      Kafka 偏移量的演变清楚吗？
     *      谈一谈 Kafka 的再均衡
     *      Kafka 是如何实现高吞吐率的？
     *      谈谈你对 Kafka 事务的了解？
     *
     * Kafka应用
     *      你们是怎么对Kafka进行压测的？
     *      当Kafka消息数据出现了积压，应该怎么处理？
     *      你知道kafka是怎么维护offset的吗？
     *      解释一下，在数据制作过程中，你如何能从Kafka得到准确的信息?
     *      请简述下你在哪些场景下会选择 Kafka？
     *
     */
}
