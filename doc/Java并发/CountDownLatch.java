

// demo案例
public class Demo4CountDownLatch {





    /**
     * 一般我们要做一个事情之前，需要做前置的一些步骤，只有这些步骤昨晚之后，才可以启动这个任务，这时候我们可以使用CountDownLatch
     *
     * 我们可以使用CountDownLatch，传入一个state值，表示有N的步骤完成之后这个任务才能开始做。
     *
     * 这个任务就调用await方法，之后就会阻塞住，然后加载完一个步骤之后就调用一次countDown()方法，最后state为0了。任务就可以开始做了。
     *
     *
     *
     */




    public void jobA() {
        System.out.println("线程1完成步骤1");
    }

    public void jobB() {
        System.out.println("线程2完成步骤2");
    }

    public void jobC() {
        System.out.println("线程3完成步骤3");
    }


    public static void main(String[] args) throws InterruptedException {
        Demo4CountDownLatch demo4CountDownLatch = new Demo4CountDownLatch();
        CountDownLatch countDownLatch = new CountDownLatch(3);

        List<Thread> threadList = new ArrayList<>();

        Runnable threadA = (() -> {
            demo4CountDownLatch.jobA();
            countDownLatch.countDown();
        });
        Thread thread1 = new Thread(threadA);
        threadList.add(thread1);


        Runnable threadB = (() -> {
            demo4CountDownLatch.jobB();
            countDownLatch.countDown();
        });
        Thread thread2 = new Thread(threadB);
        threadList.add(thread2);

        Runnable threadC = (() -> {
            demo4CountDownLatch.jobC();
            countDownLatch.countDown();
        });
        Thread thread3 = new Thread(threadC);
        threadList.add(thread3);

        threadList.forEach(Thread::start);


        countDownLatch.await();
        System.out.println("主线程继续走下去");
    }
}


// -----------------------------源码跑读-----------------------

countDownLatch.await();

public void await() throws InterruptedException {
    sync.acquireSharedInterruptibly(1);
}



public final void acquireSharedInterruptibly(int arg)
        throws InterruptedException {
    if (Thread.interrupted())
        throw new InterruptedException();
    if (tryAcquireShared(arg) < 0)
        // 获取锁失败，就会走到这里来
        doAcquireSharedInterruptibly(arg);
}


protected int tryAcquireShared(int arg) {
    throw new UnsupportedOperationException();
}

// 尝试获取共享锁，satte为0，获取锁成功，否则就返回-1
protected int tryAcquireShared(int acquires) {
    return (getState() == 0) ? 1 : -1;
}


private void doAcquireSharedInterruptibly(int arg)
    throws InterruptedException {
    // 将自己封装成一个共享模式的节点，进入队列的尾巴
    final Node node = addWaiter(Node.SHARED);
    boolean failed = true;
    try {
        for (;;) {
            // 如果前驱节点是head，就可以再次尝试获取锁，否则就挂起线程
            if (p == head) {
                // 再次获取锁失败，就阻塞起来
                int r = tryAcquireShared(arg);
                if (r >= 0) {
                    setHeadAndPropagate(node, r);
                    p.next = null; // help GC
                    failed = false;
                    return;
                }
            }
            // 线程会在这里挂起
            if (shouldParkAfterFailedAcquire(p, node) && parkAndCheckInterrupt())
                throw new InterruptedException();
        }
    } finally {
        if (failed)
            cancelAcquire(node);
    }
}



// 释放锁

public void countDown() {
    sync.releaseShared(1);
}

public final boolean releaseShared(int arg) {
    // tryReleaseShared()方法只是将state-1
    if (tryReleaseShared(arg)) {
        // 只有当state的值减到0的时候，就会调用下面的方法
        doReleaseShared();
        return true;
    }
    return false;
}


protected boolean tryReleaseShared(int releases) {
    // Decrement count; signal when transition to zero
    for (;;) {
        int c = getState();
        if (c == 0)
            return false;
        int nextc = c-1;
        // 在这里将state设置为0，然后返回true
        if (compareAndSetState(c, nextc))
            return nextc == 0;
    }
}


// 最后一个释放锁的线程会走到这里
// 这个方法主要就是将阻塞住的线程给唤醒
private void doReleaseShared() {
    for (;;) {
        Node h = head;
        if (h != null && h != tail) {
            int ws = h.waitStatus;
            if (ws == Node.SIGNAL) {
                if (!compareAndSetWaitStatus(h, Node.SIGNAL, 0))
                    continue;            // loop to recheck cases

                // 将head的后继节点唤醒，就是唤醒阻塞队列的第一个节点----->线程被唤醒之后，doAcquireSharedInterruptibly(int arg)这里的逻辑会继续执行
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


private void doAcquireSharedInterruptibly(int arg)
    throws InterruptedException {
    // 将自己封装成一个共享模式的节点，进入队列的尾巴
    final Node node = addWaiter(Node.SHARED);
    boolean failed = true;
    try {
        for (;;) {
            // 如果前驱节点是head，就可以再次尝试获取锁，否则就挂起线程
            if (p == head) {
                // 再次获取锁失败，就阻塞起来
                // 被唤醒之后，再次获取共享锁，这里可以能会获取成功
                int r = tryAcquireShared(arg);
                if (r >= 0) {
                    setHeadAndPropagate(node, r);
                    p.next = null; // help GC
                    failed = false;
                    return;
                }
            }
            
            if (shouldParkAfterFailedAcquire(p, node) && 
                // 线程会在这里挂起，当被其他线程唤醒之后，就会继续走这里的逻辑，额案后继续执行这个for(;;)
                parkAndCheckInterrupt())
                throw new InterruptedException();
        }
    } finally {
        if (failed)
            cancelAcquire(node);
    }
}


private void setHeadAndPropagate(Node node, int propagate) {
    Node h = head; // Record old head for check below
    setHead(node);
    /*
        * Try to signal next queued node if:
        *   Propagation was indicated by caller,
        *     or was recorded (as h.waitStatus either before
        *     or after setHead) by a previous operation
        *     (note: this uses sign-check of waitStatus because
        *      PROPAGATE status may transition to SIGNAL.)
        * and
        *   The next node is waiting in shared mode,
        *     or we don't know, because it appears null
        *
        * The conservatism in both of these checks may cause
        * unnecessary wake-ups, but only when there are multiple
        * racing acquires/releases, so most need signals now or soon
        * anyway.
        */
    // 如果阻塞阻塞队列中还有线程阻塞，这里还需要继续唤醒
    if (propagate > 0 || h == null || h.waitStatus < 0 ||
        (h = head) == null || h.waitStatus < 0) {
        Node s = node.next;
        if (s == null || s.isShared())
            doReleaseShared();
    }
}





// 调用这个方法的时候，state == 0
private void doReleaseShared() {
    for (;;) {
        Node h = head;
        // 1. h == null: 说明阻塞队列为空
        // 2. h == tail: 说明头结点可能是刚刚初始化的头节点，
        //   或者是普通线程节点，但是此节点既然是头节点了，那么代表已经被唤醒了，阻塞队列没有其他节点了
        // 所以这两种情况不需要进行唤醒后继节点
        if (h != null && h != tail) {
            int ws = h.waitStatus;
            // t4 将头节点(此时是 t3)的 waitStatus 设置为 Node.SIGNAL（-1） 了
            if (ws == Node.SIGNAL) {
                // 这里 CAS 失败的场景请看下面的解读
                if (!compareAndSetWaitStatus(h, Node.SIGNAL, 0))
                    continue;            // loop to recheck cases
                // 就是这里，唤醒 head 的后继节点，也就是阻塞队列中的第一个节点
                // 在这里，也就是唤醒 t4
                unparkSuccessor(h);
            }
            else if (ws == 0 &&
                     // 这个 CAS 失败的场景是：执行到这里的时候，刚好有一个节点入队，入队会将这个 ws 设置为 -1
                     !compareAndSetWaitStatus(h, 0, Node.PROPAGATE))
                continue;                // loop on failed CAS
        }
        // 如果到这里的时候，前面唤醒的线程已经占领了 head，那么再循环
        // 否则，就是 head 没变，那么退出循环，
        // 退出循环是不是意味着阻塞队列中的其他节点就不唤醒了？当然不是，唤醒的线程之后还是会调用这个方法的
        if (h == head)                   // loop if head changed
            break;
    }
}




/**
我们分析下最后一个 if 语句，然后才能解释第一个 CAS 为什么可能会失败：

h == head：说明头节点还没有被刚刚用 unparkSuccessor 唤醒的线程（这里可以理解为 t4）占有，此时 break 退出循环。
h != head：头节点被刚刚唤醒的线程（这里可以理解为 t4）占有，那么这里重新进入下一轮循环，唤醒下一个节点（这里是 t4 ）。我们知道，等到 t4 被唤醒后，其实是会主动唤醒 t5、t6、t7...，那为什么这里要进行下一个循环来唤醒 t5 呢？我觉得是出于吞吐量的考虑。
满足上面的 2 的场景，那么我们就能知道为什么上面的 CAS 操作 compareAndSetWaitStatus(h, Node.SIGNAL, 0) 会失败了？

因为当前进行 for 循环的线程到这里的时候，可能刚刚唤醒的线程 t4 也刚刚好到这里了，那么就有可能 CAS 失败了。

for 循环第一轮的时候会唤醒 t4，t4 醒后会将自己设置为头节点，如果在 t4 设置头节点后，for 循环才跑到 if (h == head)，那么此时会返回 false，for 循环会进入下一轮。t4 唤醒后也会进入到这个方法里面，那么 for 循环第二轮和 t4 就有可能在这个 CAS 相遇，那么就只会有一个成功了。


 */

