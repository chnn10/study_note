

// 1：ReentrantReadWriteLock的概述
// 1.1：ReentrantReadWriteLoc是一个读写锁，里面封装了读锁和写锁，读锁可以被多个线程共享，写锁是独占模式的，主要用于读多写少的场景。

// 2：核心属性字段

// state的高16位表示的是读锁，低16位表示写锁
static final int SHARED_SHIFT   = 16;
static final int SHARED_UNIT    = (1 << SHARED_SHIFT);
static final int MAX_COUNT      = (1 << SHARED_SHIFT) - 1;
static final int EXCLUSIVE_MASK = (1 << SHARED_SHIFT) - 1;

// 获取读锁的个数
static int sharedCount(int c)    { return c >>> SHARED_SHIFT; }

// 写锁重入的次数
static int exclusiveCount(int c) { return c & EXCLUSIVE_MASK; }

// 当前线程持有读锁的次数
private transient ThreadLocalHoldCounter readHolds;

// 最后一个线程获取读锁的重入个数，每一个线程获取到了读锁都会写这里，其实也是很快就被释放了，为了提高性能，这里有点难懂
private transient HoldCounter cachedHoldCounter;

// 第一个获取读锁的线程
private transient Thread firstReader = null;

// 第一个获取读锁持有读锁的个数
private transient int firstReaderHoldCount;

// 3：获取读锁
public void lock() {
    sync.acquireShared(1);
}


public final void acquireShared(int arg) {
    // 尝试获取共享锁
    if (tryAcquireShared(arg) < 0)
        doAcquireShared(arg);
}


protected final int tryAcquireShared(int unused) {
    Thread current = Thread.currentThread();
    int c = getState();

    // 如果已经有线程获取了写锁，并且这个写锁不是自己获取的，直接失败。（如果自己已经获取到了写锁，是可以获取读锁的）
    if (exclusiveCount(c) != 0 &&
        getExclusiveOwnerThread() != current)
        return -1;

    // r表示的是获取读锁的次数
    int r = sharedCount(c);

    // 条件1：读锁不阻塞的情况
    // 条件2：获取读锁的次数小于 MAX_COUNT （一般都不会成立）
    // 条件3：CAS将state高16位+1，成功就表示获取读锁成功
    if (!readerShouldBlock() && r < MAX_COUNT && compareAndSetState(c, c + SHARED_UNIT)) {

        // -----------------------------
        // 走到这里表示当前线程获取读锁成功
        // -----------------------------

        // r=0，表示的是当前线程是第一个线程第一次获取到读锁
        if (r == 0) {
            firstReader = current;
            firstReaderHoldCount = 1;

        // 这里表示的是当前线程还是第一个获取到锁的线程，只是这次是重入了这个读锁
        } else if (firstReader == current) {
            firstReaderHoldCount++;
        } else {
            // 下面的逻辑就是记录最后以后一个线程获取读锁的个数
            HoldCounter rh = cachedHoldCounter;
            if (rh == null || rh.tid != getThreadId(current))
                cachedHoldCounter = rh = readHolds.get();
            else if (rh.count == 0)
                readHolds.set(rh);
            rh.count++;
        }
        return 1;
    }

    // 读锁阻塞了会走到这里，上面CAS失败也会走到这里
    return fullTryAcquireShared(current);
}


// 这里的逻辑和外层获取读锁的逻辑大体上也是差不多的，这里主要是会for(;;)，会一直循环知道获取到锁
final int fullTryAcquireShared(Thread current) {
    /*
        * This code is in part redundant with that in
        * tryAcquireShared but is simpler overall by not
        * complicating tryAcquireShared with interactions between
        * retries and lazily reading hold counts.
        */
    HoldCounter rh = null;
    for (;;) {
        int c = getState();
        if (exclusiveCount(c) != 0) {
            if (getExclusiveOwnerThread() != current)
                return -1;
            // else we hold the exclusive lock; blocking here
            // would cause deadlock.
        } else if (readerShouldBlock()) {
            /**
                走到这里的条件：
                    1：其他线程获取到了写锁
                    2：阻塞队列有其他线程在等待
                这里其实就是处理读锁重入的，外层有一个for，如果没有别的线程获取到写锁，会一直执行for，最终还是会获取到锁的

             */
            if (firstReader == current) {
                // assert firstReaderHoldCount > 0;
            } else {
                if (rh == null) {
                    rh = cachedHoldCounter;
                    if (rh == null || rh.tid != getThreadId(current)) {
                        rh = readHolds.get();
                        if (rh.count == 0)
                            readHolds.remove();
                    }
                }
                if (rh.count == 0)
                    return -1;
            }
        }
        if (sharedCount(c) == MAX_COUNT)
            throw new Error("Maximum lock count exceeded");
        if (compareAndSetState(c, c + SHARED_UNIT)) {
            if (sharedCount(c) == 0) {
                firstReader = current;
                firstReaderHoldCount = 1;
            } else if (firstReader == current) {
                firstReaderHoldCount++;
            } else {
                if (rh == null)
                    rh = cachedHoldCounter;
                if (rh == null || rh.tid != getThreadId(current))
                    rh = readHolds.get();
                else if (rh.count == 0)
                    readHolds.set(rh);
                rh.count++;
                cachedHoldCounter = rh; // cache for release
            }
            return 1;
        }
    }
}



// 是读锁
public void unlock() {
    sync.releaseShared(1);
}



public final boolean releaseShared(int arg) {
    if (tryReleaseShared(arg)) {
        doReleaseShared();
        return true;
    }
    return false;
}



protected final boolean tryReleaseShared(int unused) {
    Thread current = Thread.currentThread();
    // 当前线程是第一个获取到锁的线程
    if (firstReader == current) {
        // 没有重入，就直接将firstRead 设置为 null
        if (firstReaderHoldCount == 1)
            firstReader = null;
        else
            // 表示读锁重入了，就减1
            firstReaderHoldCount--;
    } else {

        // cachedHoldCounter没有缓存当前的线程，就去 ThreadLocal 那里获取
        HoldCounter rh = cachedHoldCounter;
        if (rh == null || rh.tid != getThreadId(current))
            rh = readHolds.get();
        int count = rh.count;
        if (count <= 1) {
            readHolds.remove();
            if (count <= 0)
                throw unmatchedUnlockException();
        }
        
        //count - 1
        --rh.count;
    }
    for (;;) {
        int c = getState();
        int nextc = c - SHARED_UNIT;
        if (compareAndSetState(c, nextc))
            // Releasing the read lock has no effect on readers,
            // but it may allow waiting writers to proceed if
            // both read and write locks are now free.
            // 表示的是读锁和写锁都是空的
            return nextc == 0;
    }
}

// 唤醒后继节点
private void doReleaseShared() {
    for (;;) {
        Node h = head;
        if (h != null && h != tail) {
            int ws = h.waitStatus;
            if (ws == Node.SIGNAL) {
                if (!compareAndSetWaitStatus(h, Node.SIGNAL, 0))
                    continue;            // loop to recheck cases
                unparkSuccessor(h);
            }
            else if (ws == 0 &&
                        !compareAndSetWaitStatus(h, 0, Node.PROPAGATE))
                continue;                // loop on failed CAS
        }
        if (h == head)                   // loop if head changed
            break;
    }
}



// 获取写锁
public void lock() {
    sync.acquire(1);
}


public void lock() {
    sync.acquire(1);
}



protected final boolean tryAcquire(int acquires) {
    
    Thread current = Thread.currentThread();
    int c = getState();
    int w = exclusiveCount(c);

    // 表示的是已经有别的线程获取到锁了
    if (c != 0) {
        // 情况1：w==0，表示的是线程获取锁获取的都是读锁，返回false
        // 情况2：前置条件 w!=0，current != getExclusiveOwnerThread()，表示的是有线程获取到了写锁，这个写锁不是自己，也返回false
        // 总结：有线程获取了读锁，其他线程获取了写锁，写锁都获取失败
        if (w == 0 || current != getExclusiveOwnerThread())
            return false;
        if (w + exclusiveCount(acquires) > MAX_COUNT)
            throw new Error("Maximum lock count exceeded");
        // Reentrant acquire

        // 走到这里表示的是，自己获取了写锁，可以重入
        setState(c + acquires);
        return true;
    }

    // 走到这里表示的是：目前没有其他线程获取到锁

    // 如果获取写锁没有被阻塞，CAS成功就获取写锁成功
    if (writerShouldBlock() ||
        !compareAndSetState(c, c + acquires))
        return false;
    setExclusiveOwnerThread(current);
    return true;
}


// #AQS
// 获取写锁失败，就会走到这里  ------------>（共享的模式暴怒是很懂）
private void doAcquireShared(int arg) {
    // 新建一个共享模式的节点，放到队列尾
    final Node node = addWaiter(Node.SHARED);
    boolean failed = true;
    try {
        boolean interrupted = false;
        for (;;) {
            final Node p = node.predecessor();
            // 当前节点的前驱节点是head，那就可以再尝试去获取共享锁了
            if (p == head) {
                int r = tryAcquireShared(arg);
                if (r >= 0) {
                    setHeadAndPropagate(node, r);  // ------------> 这里看不懂
                    p.next = null; // help GC
                    if (interrupted)
                        selfInterrupt();
                    failed = false;
                    return;
                }
            }
            // 再次尝试获取失败，就走到这里来
            // 这里也是AQS的代码，就是挂起当前线程
            if (shouldParkAfterFailedAcquire(p, node) &&
                parkAndCheckInterrupt())
                interrupted = true;
        }
    } finally {
        if (failed)
            cancelAcquire(node);
    }
}

// #AQS，和AQS一样的
private Node addWaiter(Node mode) {
    Node node = new Node(Thread.currentThread(), mode);
    // Try the fast path of enq; backup to full enq on failure
    Node pred = tail;
    if (pred != null) {
        node.prev = pred;
        if (compareAndSetTail(pred, node)) {
            pred.next = node;
            return node;
        }
    }
    enq(node);
    return node;
}


/**
    * Acquires in shared uninterruptible mode.
    * @param arg the acquire argument
    */
private void doAcquireShared(int arg) {
    final Node node = addWaiter(Node.SHARED);
    boolean failed = true;
    try {
        boolean interrupted = false;
        for (;;) {
            final Node p = node.predecessor();
            if (p == head) {
                int r = tryAcquireShared(arg);
                if (r >= 0) {
                    setHeadAndPropagate(node, r);
                    p.next = null; // help GC
                    if (interrupted)
                        selfInterrupt();
                    failed = false;
                    return;
                }
            }
            if (shouldParkAfterFailedAcquire(p, node) &&
                parkAndCheckInterrupt())
                interrupted = true;
        }
    } finally {
        if (failed)
            cancelAcquire(node);
    }
}







