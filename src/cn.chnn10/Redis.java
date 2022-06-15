package cn.chnn10;

/**
 * Redis持久化
 */
public class Redis {
    /**
     * Redis持久化
     * 	    Redis提供了哪几种持久化方式？
     * 	    RDB和AOF的优缺点
     * 	    Redis持久化的底层如何实现的？有什么优点缺点？
     *  	如何选择合适的持久化方式？
     *
     *  Redis集群
     *      说一下Redis的主从复制
     *      讲一下复制的过程
     *
     *      说一下Redis哨兵
     *      哨兵工作原理
     *
     *      Redis 集群方案应该怎么做？都有哪些方案？
     *      Redis 集群方案什么情况下会导致整个集群不可用？
     *      Redis 集群的主从复制模型是怎样的？
     *      Redis 集群会有写操作丢失吗？为什么？
     *      Redis 集群之间是如何复制的？
     *      Redis 集群最大节点个数是多少？
     *      Redis 集群如何选择数据库？
     *
     *  Redis缓存
     *      什么是缓存穿透？怎么解决？如何避免？
     *      什么是缓存雪崩？怎么解决？何如避免？
     *      什么是缓存击穿？怎么解决？如何避免？
     *      如何保证缓存与数据库双写时的数据一致性?
     *
     *  Redis事务
     *      怎么理解 Redis 事务？
     *      Redis事务的三个阶段
     *      说一下Redis的WATCH机制
     *
     *  Redis内存优化
     *      Redis 有哪几种数据淘汰策略？
     *      Redis 如何做内存优化？
     *      Redis 回收进程如何工作的？
     *      redis 过期策略都有哪些？LRU 算法知道吗？
     *      Reids三种不同删除策略
     *      定时删除策略
     *      定期删除策略
     *      惰性删除策略
     *
     *  Redis锁
     *      什么是分布式锁
     *      分布式锁具备什么条件
     *      分布式锁的实现的实现方式
     *      加锁机制
     *      锁互斥机制
     *      使用 redis 如何设计分布式锁？说一下实现思路？使用 zk 可以吗？如何实现？这两种有什么区别？
     *
     *  Redis数据类型
     *      Redis的数据类型，以及每种数据类型的使用场景
     *      Redis 当中有哪些数据结构
     *
     *  Redis概念
     *      什么是 Redis？简述它的优缺点？
     *      Redis 与 memcached 相比有哪些优势？
     *      Redis 有哪些适合的场景？
     *      Redis 为什么是单线程的
     *      讲解下Redis线程模型
     *
     *  Redis管道
     *      Redis 中的管道有什么用？
     *
     *  Redis订阅模式
     *
     *
     *
     *
     *  ---------------------------------------------------------持久化--------------------------------------------------
     * 	1：Redis提供了哪几种持久化方式？（2022-6-13 第一次）
     * 	我们知道Redis事运行在内存中的数据库，它的数据是保存在内存中的，当Redis宕机或者断电的时候，这些数据就会丢失。
     * 	为了防止数据丢失，Redis提供了持久化的机制，Redis持久化的方式有RDB和AOF。
     * 	RDB就是内存快照嘛，就是在某个时间点保存Redis数据库所有的数据到磁盘中，当Redis服务重启的时候，通过读取RDB文件来恢复数据。
     * 	AOF就是服务端接收到写请求之后，会将这个命令追加到aof文件中，然后再保存到磁盘，Redis服务重启时，通过重新执行AOF文件的命令来恢复数据。
     * 	这就是Redis的两种持久化方式。
     *
     *
     * 	2：RDB和AOF的优缺点（2022-6-13 第一次）
     * 	因为RDB文件可以由子进程来实现的，生产RDB文件并不会影响主服务处理客户端的请求。
     * 	当服务重启的时候，直接读取RDB文件就可以恢复数据，所有RDB的性能比较号，恢复数据比较快。
     *
     * 	但是RDB容易丢失数据，发生断电的时候，就会丢失上一次快照到断电这个时间段的数据。
     * 	其实丢失的数据也取决于执行快照的执行时间，间隔时间越长数据就会丢失越多。但是如果间隔时间太短也不行，虽然子进程生产RDB文件并不会阻塞
     * 	主进程，但是fork操作是会阻塞主进程的，频繁地fork子进程是会影响到Redis服务的性能的，所有也不行太频繁。这就是RDB的缺点。
     *
     * 	AOF的优点就是安全行比较高，数据丢失比较少，缺点就是性能方面不是很高，重启服务时通过执行AOF文件的的命令来恢复数据的，因此AOF恢复数据是比较慢的。
     * 	我们知道服务端执行完写命令之后会将写命令保存到磁盘中，断电或宕机的时候会丢失这些命令，但是这些命令比较少的，所以AOF的安全性是比较高的。
     *
     *
     * 	3：如何让选择合适的持久化方式（2022-6-13 第一次）
     * 	如何选择合适的持久化的方式，取决于我们的业务，如果我们更多考虑的是性能，就可以使用RDB，如果我们考虑更多的是数据的安全，那么就是用AOF。
     * 	当然也有一个mix的方式，混合是使用AOF和RDB，就是隔一段时间使用RDB，在这段时间的期间使用AOF，这样可以中和性能和安全的问题。
     *
     *
     * 	4：Redis持久化的底层实现（2022-6-13 第一次）
     * 	持久化有两种实现方式嘛，分别是RDB和AOF。
     * 	RDB实现是这样的，我们中执行了SAVE或者BGSAVE，都可是实现RDB。
     * 	我们执行SAVE命令时，主进程会自己去生产RDB文件，期间不能处理客户端的请求，知道完成RDB的生产才可以继续请求命令。
     *
     * 	当我们执行BGSAVE命令时，主进程会fork一个子进程，由子进程去执行快照去生产RDB文件，然后主进程继续去处理客户端的请求。
     * 	当子进程创建完RDB文件之后会向主进程发送一个异步通知，主进程再做处理。
     * 	这就是RDB的底层实现。
     *
     * 	我们可以在配置文件中设置 appendsync yes来开启AOF功能。
     * 	当AOF功能打开时，服务端执行完写命令之后，就会讲命令放到缓冲区中，然后再从缓冲区刷回到磁盘中。
     * 	怎么讲缓存区的命令数据刷回磁盘，Redis也提供了配置选项。分别是appendsync always no everysec
     * 	如果时always，表示的是没执行一条命令就是刷回磁盘，这样做的安全性是比较高的，但是性能不是很好。
     * 	如果是noi，表示的是执行完命令之后放到缓存区，至于什么时候讲缓存区的数据刷回磁盘，取决于操作系统，这方式数据安全性不高但是性能比较好。
     * 	如果是evrysec，表示的是每隔一秒钟，就会讲缓存区的数据刷回到磁盘中，这样做是一个折中方法，中和了性能和数据的安全性。
     * 	这就是我对Redis持久化底层实现的理解。
     *
     *
     *
     * 	---------------------------------------------------------缓存--------------------------------------------------
     * 	1：什么是缓存穿透？怎么解决？如何避免？
     * 	缓存穿透就是用户的请求访问的数据在缓存中没有，在数据库层也没有，当有大量这样的请求时候，肯定会给数据库层造成很大的压力，有可能是恶意的攻击。
     * 	可以使用布隆器判断数据是否存放，对于不存在的数据就不访问数据库了，这样可以减少数据库的压力。
     * 	过滤器的原理（代办）
     *
     *
     *  2：什么是缓存雪崩？怎么解决？何如避免？
     *  我们引入Redis做缓存的时候，用户的请求首先回去缓存中获取，如果没有获取到，才会到数据库获取数据。
     *  Redis的缓存雪崩就是大量的用户请求都没能从缓存中获取数据，然后都请求到数据库，这会给数据库带来很大的压力。
     *
     *  造成缓存雪崩的主要有大量的缓存在同一个时间失效，第二个就是Redis宕机了。
     *  怎么防止缓存雪崩呢，第一个就是随机设置缓存的过期时间，这样可以避免大量的缓存同一时间失效。
     *  还有就是搭建高可用的Redis集群，一个Redis宕机了，立马有其它的Redis提供服务。
     *
     *
     *  3：什么是缓存击穿？怎么解决？如何避免？
     *  缓存击穿指的是数据在数据库存在，但是在缓存中不存在，就是在缓存中过期了，当大量的请求都需要访问这个数据，就会给数据库造成很大的压力。
     *  为了避免这种情况，我们可以对热点的数据设置永不过去。
     *
     *  那么击穿和雪崩有什么区别呢，雪崩指的是大范围的缓存失效的场景，击穿指的是某个热点数据失效的场景。
     *
     *  4：缓存一致性（代办）
     *
     *
     *
     * ---------------------------------------------------------事务--------------------------------------------------
     *  1：说一说Redis的事务
     *  Redis事务允许一次可以执行多条命令，MULTI命令就是Redis事务的开始，然后Redis会将命令放进一个队列中，当遇到EXEC命令时，Redis就会顺序执行队列中的命令。
     *  当然，Redis事务是没有回滚的，在执行命令的过程中，如果有一条命令执行失败了，剩下的命令会继续被执行。
     *  执行的过程中不会被中断，只有执行完了才可以继续请求其他客户端的请求。
     *  这就是我对Redis事务的理解。
     *
     *
     *  2：说一下Redis的WATCH机制
     *
     *
     *
     * ---------------------------------------------------------主从复制-----------------------------------------------
     * 1：说一下Redis的主从复制
     * 我们知道Redis持久化机制就是确保Redis宕机或者断电时，重启时可以恢复Redis的数据。
     * 但是当Redis宕机了，就无法提供服务了，为了确保Redis的服务提供，就需要有多个Redis实例。
     *
     * 当一个Redis挂掉之后，还有其他的实例可以提供服务。
     * Redis的主从关系是这样的，就是主节点提供读写服务、从节点提供读服务，那么两个Redis实例是怎么变成主从关系的呢。
     * 一个Redis实例执行了repOf 另一台Redis的ip 端口，执行命令的Redis就变成了一个从节点。
     * 主节点收到命令之后，就会fork子进程去生产一个RDB文件，生产RDB文件之后，就发给从节点，这样两台实例的数据就一致了，这就是Redis主从复制的基本情况。
     *
     *
     * 2：说一下Redis复制的过程（代办）
     *
     *
     *
     *
     *
     * ---------------------------------------------------------哨兵--------------------------------------------------
     * 1：说一下Redis哨兵
     * Redis一般都是有多个实例同时工作的，主节点负责读写请求，从节点负责读请求。如果有一个从节点挂掉了，还有主节点和其他的从节点可以提供服务。
     * 如果主节点挂掉了，从节点可以提供读请求，但是是无法提供写请求的，这肯定是不行的。那么办呢，哨兵就出来了。
     *
     * 哨兵就是一个特殊的Redis实例，当哨兵监控到主节点挂掉了，就会将一个从节点代替挂掉的主节点成为新的主节点，
     * 然后新的和其他的从节点形成一个新的主从关系，这样就可以继续正常地提供服务。
     *
     *
     * 2：说一下哨兵的工作原理（代办）
     */
}




