package cn.chnn10;

public class JDK {
    /**
     * 线程基础
     *   线程和进程的区别
     *   线程中的run和start的区别
     *   java中sleep方法和wait方法的区别
     *   线程中sleep方法和yield方法的区别
     *   notify方法和notifyAll方法的区别
     *   谈一谈你对线程优先级的理解
     *   Java中你怎么阻塞一个线程
     *   创建线程的四种方式
     *   说一下Callabla和Runnable
     *   为什么wait、notify和notifyAll在Object类
     *   线程之间怎么通信的
     *   什么是线程上下文切换
     *   Java线程的五种状态
     *   怎么让正在运行的线程暂停一段时间
     *   线程类的构造方法、静态块被那块线程调用的
     *   为什么wait、notify、notifyAll必须在同步方法或者同步块中调用
     *   线程运行时发生异常会怎样
     *   interrupted和isInterrrupted的区别
     *   线程调度策略
     *   多线程应用场景
     *   Java线程数过多会造成什么异常
     *   怎么调用wait方法的，在if块还是循环块
     *   怎么打印出线程栈信息
     *   线程怎么同步的
     *   守护线程和非守护线程的区别
     *   线程的状态流转图
     *   并发线程的缺点
     *   什么是竞争条件、怎么发现和解决
     *   怎么唤醒一个阻塞的线程
     *   线程的调度策略
     *   线程数过多会造成什么异常
     *   并发和并行
     *   线程同步和线程互斥，有哪几种实现方式
     *   为什么wait和notify方法要在同步块中调用
     *   并发三个必要因素是什么
     *   什么是线程组，为什么不推荐使用
     *   什么是同步、什么是异步
     *   线程的生命周期
     *   守护线程和本地线程的区别
     *   两个线程如何共享数据
     * 锁
     *   什么是原子类
     *   什么是死锁
     *   手写一个简单的死锁的代码
     *   怎么避免线程死锁
     *   什么是自旋
     *   synchronized可重入原理
     *   在项目中怎么使用synchronized关键字
     *   volatile关键字的作用，应用场景
     *   CAS的会导致什么问题
     *   实现可见性的方法有哪些
     *   synchronized和ReentrantLock的区别
     *   synchronized和Lock的区别
     *   synchronized、CAS、volatile的区别
     *   synchronized锁升级的原理是什么
     *   synchronized和volatile的区别
     *   说一下Atomic的原理
     *   Java中能创建volatile数组吗
     *   谈一谈乐观锁和悲观锁
     *   怎么找到死锁的线程
     *   volatile和atomic变量有什么不同
     *   怎么检测一个线程拥有锁
     *   死锁和活锁、死锁和锁饥饿
     *   什么是自旋
     *   不变的对象对多线程有什么帮助
     * java内存模型
     *   java内存模型是什么
     *   什么是指令重排
     *   As-if-serial规则和happens-before的规则的区别
     *   代码为什么会重排序
     *   什么是内存屏障
     *
     * ThreadLocal
     *   说一下ThreadLocals
     *   什么是ThreadLocal变量
     * AQS
     *   介绍一下AQS
     *   AQS两种同步方式
     * HashMap相关
     *   说一下HashMap的实现原理
     *   SynchronizedMap和ConcurrentHashMap的区别
     *   ConcurrentHashMap和HashTable的区别
     *   HashMap和HashTable的区别
     *   HashMap的长度为什么是2的幂次方
     *   ConcurrentHashMap的并发度是什么
     *   HashMap的扩容怎么实现的
     *   HashMap的put具体流程
     * 并发工具类
     *   常用的并发工具类
     *   CopyOnWriteArrayList的设计思想
     *   CopyOnWriteArrayList的缺点
     *   CopyOnWriteArrayList的应用
     *   ReadWriteLock是什么
     *   CycliBarriar和CountdownLatch的区别
     *   Java中的Semaphore是什么
     *   ArrayList和Vector有什么不同
     *   HashSet内部是怎么工作的
     *   多线程怎么实现ArrayList
     * 线程池
     *   线程池的作用
     *   线程池的参数
     *   线程池的工作原理
     *   线程池都有哪些状态
     *   FatureTask是什么
     *   什么是Callable和Future
     *   线程池四种创建方式
     *   execute和submit的区别
     *   如何合理分配线程池大小
     *   ThreadPoolExecutor饱和策略有哪些
     * 容器
     *   什么是并发容器的实现
     *   什么是Vector
     *   说一下ArrayList的优点和缺点
     *   WeakHashMap是怎么工作的
     *   为什么HashTable是线程安全的
     *   List和Set的区别
     *   遍历一个List有哪些不同的方式？实现原理是什么？最佳实践
     *   集合和数组的区别
     *   ArrayList和LinkedList的区别
     *   List、Set、Map三者的区别
     *   LinkedHashMap和PriorityQueue的区别
     *   集合框架底层数据结构
     *   TreeSet和TreeMap怎么比较元素的
     *   Collections工具类的sort方法是怎么比较元素的
     *   Array和ArrayList有什么不一样
     *   ArratList和HashMap的默认大小是多少
     *   List接口有什么特点
     *   ArrayList、Vector和LinkedList存储性能和特性
     *   List、Set、Map是否都继承Collections接口
     * 队列
     *   什么是阻塞队列
     *   阻塞队列的实现原理是什么
     *   介绍一下并发队列
     *
     *
     *
     *-----------------------------------------------线程基础------------------------------------------------------------
     * 1：线程和进程的区别（2022-6-15）
     *     说进程和线程之前，首先要说一下什么是程序，程序就是存放在磁盘中的代码和静态数据。
     *     要执行一个程序，就要将它放到内存中跑起来，跑起来的程序就是一个进程。
     *
     *     进程是动态的，进程不仅仅包含静态数据和代码，还有程序计数器、堆栈信息和CPU寄存器这些运行需要用到的信息。
     *     一个进程可以有多个运行路线的，而线程就是进程的一个运行路线，线程是CPU调用的基本单位。
     *     这就是我对进程和线程区别的理解。
     *
     *
     * 2：线程中的run和start的区别（2022-6-15）
     *     start方法就是新建一个线程，然后这个新的线程去执行代码，start方法执行之后，此时是有两个线程并发执行后的。
     *     run方法就是执行线程的代码，我们在main方法中执行run方法，和执行普通的方法是一样的，并不会产生新的线程。
     *     这就是run和start方法的区别。
     *
     *
     * 3：java中sleep方法和wait方法的区别（2022-6-15）
     *     执行sleep方法的线程会休眠起来，等休眠的时间到了，又可以重新运行了，在线程休眠的期间是不会释放锁的。
     *     当一个线程调用了wait方法，会释放对象的锁，然后阻塞起来，直到对象的另一个线程调用了notify方法将它唤醒，然后重新获取锁才可以继续执行。
     *     要注意的是，在调用wait方法之前要获取到对象的锁，否则就会报错。
     *     这就是他们之间的区别。
     *
     *
     * 4：线程中sleep方法和yield方法的区别（2022-6-15）
     *     线程调用了sleep方法之后，它会休眠起来，休眠的期间是一个阻塞的状态的。
     *     而线程调用了yield方法呢，就是它的时间片还没有用完，它就主动让出了CPU，而它是一个就绪的状态的。
     *     这就是sleep和yield的的区别。
     *
     *
     * 5：notify方法和notifyAll方法的区别（2022-6-15）
     *     notify和notifyAll都是可以唤醒因为调用了这个对象的wait方法而阻塞起来的线程的，区别就是notify只是随机唤醒一个线程，而notifyALL可以唤醒所有的线程。
     *     无论是唤醒一个，还是所有，被唤醒的线程是需要重新竞争锁才可以继续运行。这就是他们的区别。
     *
     *
     * 6：谈一谈你对线程优先级的理解（2022-6-15）
     *
     *
     *
     *
     *
     *
     */
}
