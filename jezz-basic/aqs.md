## AQS AbstractQueuedSynchronizer，即队列同步器。它是构建锁或者其他同步组件的基础框架

AQS使用一个int类型的成员变量state来表示同步状态，当state>0时表示已经获取了锁，当state = 0时表示释放了锁。
它提供了三个方法（getState()、setState(int newState)、compareAndSetState(int expect,int update)）来对同步状态state进行操作，
当然AQS可以确保对state的操作是安全的。

AQS通过内置的FIFO同步队列(CLH队列)来完成资源获取线程的排队工作，如果当前线程获取同步状态失败（锁）时，AQS则会将当前线程以及等待状态等信息构造成一个节点（Node）并将其加入同步队列，
同时会阻塞当前线程，当同步状态释放时，则会把节点中的线程唤醒，使其再次尝试获取同步状态。

### 入列

tail指向新节点、新节点的prev指向当前最后的节点，当前最后一个节点的next指向当前节点
    
    private Node addWaiter(Node mode) {
        // 新建节点
        Node node = new Node(Thread.currentThread(), mode);
        // 快速尝试添加尾节点
        Node pred = tail;
        if (pred != null) {
            node.prev = pred;
            // cas设置尾节点
            if (compareAndSetTail(pred, node)) {
                pred.next = node;
                return node;
            }
        }
        // 多次尝试
        enq(node);
        return node;
    }
    
    private Node enq(final Node node) {
            //多次尝试，直到成功为止
            for (;;) {
                Node t = tail;
                //tail不存在，设置为首节点
                if (t == null) {
                    if (compareAndSetHead(new Node()))
                        tail = head;
                } else {
                    //设置为尾节点
                    node.prev = t;
                    if (compareAndSetTail(t, node)) {
                        t.next = node;
                        return t;
                    }
                }
            }
        }
        
### 出列

head执行该节点并断开原首节点的next和当前节点的prev即可，注意在这个过程是不需要使用CAS来保证的，因为只有一个线程能够成功获取到同步状态

### 同步状态的获取

#### 独占式同步状态获取 同一时刻仅有一个线程持有同步状态

acquire(int arg)该方法为独占式获取同步状态，但是该方法对中断不敏感，也就是说由于线程获取同步状态失败加入到CLH同步队列中，后续对线程进行中断操作时，线程不会从同步队列中移除

    public final void acquire(int arg) {
            if (!tryAcquire(arg) &&
                acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
                selfInterrupt();
        }
        
    1.tryAcquire：去尝试获取锁，获取成功则设置锁状态并返回true，否则返回false。该方法自定义同步组件自己实现，该方法必须要保证线程安全的获取同步状态
    2.addWaiter：如果tryAcquire返回FALSE（获取同步状态失败），则调用该方法将当前线程加入到CLH同步队列尾部
    3.acquireQueued：当前线程会根据公平性原则来进行阻塞等待（自旋）,直到获取锁为止；并且返回当前线程在等待过程中有没有中断过。
    4.selfInterrupt：产生一个中断。
    
    final boolean acquireQueued(final Node node, int arg) {
            boolean failed = true;
            try {
                //中断标志
                boolean interrupted = false;
                // 自旋
                for (;;) {
                    //当前线程的前驱节点
                    final Node p = node.predecessor();
                    //当前线程的前驱节点是头结点，且同步状态成功
                    if (p == head && tryAcquire(arg)) {
                        setHead(node);
                        p.next = null; // help GC
                        failed = false;
                        return interrupted;
                    }
                    //获取失败，线程等待--前驱节点判断当前线程是否应该被阻塞
                    if (shouldParkAfterFailedAcquire(p, node) &&
                            parkAndCheckInterrupt())
                        interrupted = true;
                }
            } finally {
                if (failed)
                    cancelAcquire(node);
            }
        }
        
只有其前驱节点为头结点才能够尝试获取同步状态，原因：
1.保持FIFO同步队列原则。
2.头节点释放同步状态后，将会唤醒其后继节点，后继节点被唤醒后需要检查自己是否为头节点。

#### 独占式获取响应中断

AQS提供了acquire(int arg)方法以供独占式获取同步状态，但是该方法对中断不响应，对线程进行中断操作后，该线程会依然位于CLH同步队列中等待着获取同步状态。
为了响应中断，AQS提供了acquireInterruptibly(int arg)方法，该方法在等待获取同步状态时，如果当前线程被中断了，会立刻响应中断抛出异常InterruptedException。

     public final void acquireInterruptibly(int arg)
                throws InterruptedException {
            if (Thread.interrupted())
                throw new InterruptedException();
            if (!tryAcquire(arg))
                doAcquireInterruptibly(arg);
        }

#### 独占式超时获取 tryAcquireNanos(int arg,long nanos)

#### 独占式同步状态释放

先调用自定义同步器自定义的tryRelease(int arg)方法来释放同步状态，释放成功后，会调用unparkSuccessor(Node node)方法唤醒后继节点

    public final boolean release(int arg) {
            if (tryRelease(arg)) {
                Node h = head;
                if (h != null && h.waitStatus != 0)
                    unparkSuccessor(h);
                return true;
            }
            return false;
        }

调用tryAcquireShared(int arg)方法尝试获取同步状态，如果获取失败则调用doAcquireShared(int arg)自旋方式获取同步状态，共享式获取同步状态的标志是返回 >= 0 的值表示获取成功
    
     public final void acquireShared(int arg) {
            if (tryAcquireShared(arg) < 0)
                //获取失败，自旋获取同步状态
                doAcquireShared(arg);
        }
        
#### 共享式同步状态释放
    
    public final boolean releaseShared(int arg) {
            if (tryReleaseShared(arg)) {
                doReleaseShared();
                return true;
            }
            return false;
        }

#### LockSupport

    if (shouldParkAfterFailedAcquire(p, node) &&
                        parkAndCheckInterrupt())
                        interrupted = true;
                        
    private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
        //前驱节点
        int ws = pred.waitStatus;
        //状态为signal，表示当前线程处于等待状态，直接放回true
        if (ws == Node.SIGNAL)
            return true;
        //前驱节点状态 > 0 ，则为Cancelled,表明该节点已经超时或者被中断了，需要从同步队列中取消
        if (ws > 0) {
            do {
                node.prev = pred = pred.prev;
            } while (pred.waitStatus > 0);
            pred.next = node;
        }
        //前驱节点状态为Condition、propagate
        else {
            compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
        }
        return false;
    }

如果当前线程的前驱节点状态为SINNAL，则表明当前线程需要被阻塞，调用unpark()方法唤醒，直接返回true，当前线程阻塞
如果当前线程的前驱节点状态为CANCELLED（ws > 0），则表明该线程的前驱节点已经等待超时或者被中断了，则需要从CLH队列中将该前驱节点删除掉，直到回溯到前驱节点状态 <= 0 ，返回false
如果前驱节点非SINNAL，非CANCELLED，则通过CAS的方式将其前驱节点设置为SINNAL，返回false
