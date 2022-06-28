/**
 * 多线程debugAQS
 */
public class Demo1ReentrantLockTest {
    private int num = 0;

    private final ReentrantLock lock = new ReentrantLock();

    public void addNum() {
        try{
            lock.lock();
            num++;
            lock.lock();   // 重入锁，加锁两次，也要记得要释放两次
            num++;
            System.out.println(Thread.currentThread().getName() +"执行了num++");
        }finally {
            lock.unlock();
            lock.unlock(); // 当我们进入了重入锁，我们就需要调用两次，否则就无法释放锁了
        }
    }

    public static void main(String[] args) {
        Demo1ReentrantLockTest demo1ReentrantLockTest = new Demo1ReentrantLockTest();
        for (int i = 1; i <= 5; i++) {
            new Thread(()->{
                demo1ReentrantLockTest.addNum();
                }).start();
        }
    }
}


// ------加锁-----
public void lock() {
    sync.lock();  // 默认情况下会进入非公平锁的模式
}



final void lock() {
    // 因为已经有其他线程获取锁了，所以会走else
    if (compareAndSetState(0, 1))
        setExclusiveOwnerThread(Thread.currentThread());
    else
        acquire(1);
}



public final void acquire(int arg) {
    // 条件1：!tryAcquire(arg)，尝试去获取锁
    // 获取锁失败，就会执行addWaiter()方法
    if (!tryAcquire(arg) &&
        acquireQueued(addWaiter(Node.EXCLUSIVE), arg))  
        selfInterrupt();
}

protected final boolean tryAcquire(int acquires) {
    return nonfairTryAcquire(acquires);
}


// 非公平锁尝试去获取锁
final boolean nonfairTryAcquire(int acquires) {
    
    final Thread current = Thread.currentThread();

    // c的值是1，表示已经有别的线程获取到锁了
    int c = getState();
    if (c == 0) {
        if (compareAndSetState(0, acquires)) {
            setExclusiveOwnerThread(current);
            return true;
        }
    }

    // 这个线程并不是自己
    // 当我们重入锁的时候，会走进去，然后将这个state值+1
    else if (current == getExclusiveOwnerThread()) {
        int nextc = c + acquires;
        if (nextc < 0) // overflow
            throw new Error("Maximum lock count exceeded");
        setState(nextc);
        return true;
    }

    // 不是自己会直接返回false
    return false;
}


// 第一个没有获取到锁的线程添加node节点
//    
// 第二个没有获取到锁的线程添加node节点
private Node addWaiter(Node mode) {
    Node node = new Node(Thread.currentThread(), mode);
    // Try the fast path of enq; backup to full enq on failure
    Node pred = tail;  
    // 第一个没有获取到锁的线程，不走if，会直接走enq
    if (pred != null) {
        node.prev = pred;
        if (compareAndSetTail(pred, node)) {
            pred.next = node;
            return node;
        }
    }

    // 第一个没有获取到线程的锁会走到这里，设置一个head节点，将自己设置为tail，就是初始化一个队列，这个方法只会执行一次
    enq(node);
    return node;
}


// 第一个没有获取到锁的线程会走到这里
private Node enq(final Node node) {
    for (;;) {
        Node t = tail;
        if (t == null) { // Must initialize
        
            // 设置新建一个node节点作为头节点，此时tail也是head
            if (compareAndSetHead(new Node()))
                tail = head;
        } else {

            // 再次执行for(;;)，就将当前节点变成一个尾节点
            node.prev = t;
            if (compareAndSetTail(t, node)) {
                t.next = node;
                return t;
            }
        }
    }
}


// 第一个线程没有获取到锁的线程进队
final boolean acquireQueued(final Node node, int arg) {
    boolean failed = true;
    try {
        boolean interrupted = false;
        for (;;) {
            // 第一个没有获取到线程的锁，获取前驱节点，这时候是head的节点，它可以继续tryAcquire这个方法，然后返回false
            final Node p = node.predecessor(); 
            if (p == head && tryAcquire(arg)) {  
                setHead(node);
                p.next = null; // help GC
                failed = false;
                return interrupted;
            }
            // 如果谦虚节点不是head，或者前驱节点是head但是tryAcquire失败，会执行到这里shouldParkAfterFailedAcquire，然后返回的是false，就则不执行后面的了，接着就再次执行for(;;)
            // 接着接着执行shouldParkAfterFailedAcquire，就返回true，然后就接着执行parkAndCheckInterrupt，就是将自己挂起，走到这里就阻塞了
            if (shouldParkAfterFailedAcquire(p, node) &&
                parkAndCheckInterrupt()) // 当线程释放了锁，阻塞的线程会唤醒，然后再次执行for(;;)，然后就可以获取到锁了
                interrupted = true;
        }
    } finally {
        if (failed)
            cancelAcquire(node);
    }
}



private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
    // 第一个没有获取到锁的线程的前驱节点的waitStatus = 0
    // 第一个没有获取到锁的线程在else的条件会将waitStatus设置为SIGNAL
    int ws = pred.waitStatus;
    if (ws == Node.SIGNAL)
        /*
            * This node has already set status asking a release
            * to signal it, so it can safely park.
            */
        return true;
    if (ws > 0) {
        /*
            * Predecessor was cancelled. Skip over predecessors and
            * indicate retry.
            */
        do {
            node.prev = pred = pred.prev;
        } while (pred.waitStatus > 0);
        pred.next = node;
    } else {
        /*
            * waitStatus must be 0 or PROPAGATE.  Indicate that we
            * need a signal, but don't park yet.  Caller will need to
            * retry to make sure it cannot acquire before parking.
            */
        // 第一个没有获取到锁的线程的前驱节点的waitStatus = 0 ，会走到这里，将前驱节点的waitStatus设置为SIGNAL
        compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
    }
    return false;
}


private final boolean parkAndCheckInterrupt() {
    LockSupport.park(this);             // 执行了这里，线程就阻塞起来了
    return Thread.interrupted();
}



// ------释放锁------
// 慢点看，有点看不懂
public void unlock() {
    sync.release(1);
}


public final boolean release(int arg) {
    if (tryRelease(arg)) {
        Node h = head;
        if (h != null && h.waitStatus != 0)
            unparkSuccessor(h);   // 疑惑：什么时候会走到这里，就是没有线程阻塞，要走到这一步，需要将一个线程阻塞，然后再释放锁
        return true;
    }
    return false;
}



protected final boolean tryRelease(int releases) {
    int c = getState() - releases;
    if (Thread.currentThread() != getExclusiveOwnerThread())
        throw new IllegalMonitorStateException();
    boolean free = false;
    // c==0，表示没有锁重入
    // 当时重入锁释放锁的时候，不会走进去
    if (c == 0) {
        free = true;
        setExclusiveOwnerThread(null);
    }
    setState(c);
    return free;
}


/**
方法参数的node就是head也就是队列的头节点。所以第一个if判断肯定为true也就是将头节点的waitStatus值改成0方便后来的判断，因为如果t2唤醒了并且竞争到了锁，那么头节点将会被移除，并且t2线程的节点将成为sentinel哨兵节点。继续看到下一个if判断。
Node s获取到node.next 也就是获取到t2线程节点。因为waitStatus的默认值是0并且如果后面还有节点waitStatus的值也是-1所以当前if判断为false，继续看到下一个判断。
 */

private void unparkSuccessor(Node node) {
    /*
        * If status is negative (i.e., possibly needing signal) try
        * to clear in anticipation of signalling.  It is OK if this
        * fails or if status is changed by waiting thread.
        */
    int ws = node.waitStatus;
    if (ws < 0)
        compareAndSetWaitStatus(node, ws, 0); // 将自己德状态设置为0

    /*
        * Thread to unpark is held in successor, which is normally
        * just the next node.  But if cancelled or apparently null,
        * traverse backwards from tail to find the actual
        * non-cancelled successor.
        */
    Node s = node.next;
    if (s == null || s.waitStatus > 0) {
        s = null; 
        for (Node t = tail; t != null && t != node; t = t.prev)
            if (t.waitStatus <= 0)
                s = t;
    }

    // 有阻塞的线程，将它唤醒，唤醒了线程之后，被阻塞的线程就变成RUNNING的状态了
    if (s != null)
        LockSupport.unpark(s.thread);
}




// -----公平锁-----代办，也需要看的

public void lock() {
    sync.lock();
}



public final void acquire(int arg) {
    if (!tryAcquire(arg) &&
        acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
        selfInterrupt();
}



/**
    * Sync object for fair locks
    */
// 获取公平锁
static final class FairSync extends Sync {
    private static final long serialVersionUID = -3000897897090466540L;

    final void lock() {
        acquire(1);
    }

    /**
        * Fair version of tryAcquire.  Don't grant access unless
        * recursive call or no waiters or is first.
        */

    // 公平锁和非公平锁最大的区别就是，非公平模式去设置state之前，需要住执行hasQueuedPredecessors()这个方法，确保前面没有等待的节点，才能去设置state的值
    protected final boolean tryAcquire(int acquires) {
        final Thread current = Thread.currentThread();
        int c = getState();
        if (c == 0) {
            if (!hasQueuedPredecessors() &&
                compareAndSetState(0, acquires)) {
                setExclusiveOwnerThread(current);
                return true;
            }
        }
        else if (current == getExclusiveOwnerThread()) {
            int nextc = c + acquires;
            if (nextc < 0)
                throw new Error("Maximum lock count exceeded");
            setState(nextc);
            return true;
        }
        return false;
    }
}


// 是否有前驱节点
public final boolean hasQueuedPredecessors() {
    // The correctness of this depends on head being initialized
    // before tail and on head.next being accurate if the current
    // thread is first in queue.
    Node t = tail; // Read fields in reverse initialization order
    Node h = head;
    Node s;
    return h != t &&
        ((s = h.next) == null || s.thread != Thread.currentThread());
}



/**

    总结：
        reentrantLock是一个可重入锁，可以实现公平和非公平的模式，大多数操作都是调用了AQS去实现。默认或者构造参数传入false，表示的是非公平锁，传入true表示的是公平锁。
        公平锁和非公平锁其实是的代码其实也是差不多一样的，最大的区别就是非公平锁就是只要检测到state=0，就可以去竞争锁；而公平锁就是检测到state=0时候，还需要去看自己有没有前序节点，没有才能去竞争锁。

        1：非公平为例子加锁的总结：
            外面调用了lock方法，会委托给nofairSync的类去实现，最后还是会走到AQS那里，首先就是尝试CAS去设置state为1.成功就获取到锁。CAS失败就是获取锁失败，然后就会封装以一个独占模式的node节点。
            如果这个线程是第一个获取锁失败的线程，他就会初始化一个队列，将获取到锁的线程作为head节点，然后将自己设置为尾节点。然后调用park方法，这个线程就阻塞起来了。

        2：非公平锁释放锁的总结

        记住，实现重入锁的需要调用两次lock，一定要记得调用两次unlock，否则就永远不会释放锁了。


 */














