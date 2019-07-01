## ReentrantLock

    // 无参构造方法默认是非公平锁
    public ReentrantLock() {
        sync = new NonfairSync();
    }
    
    // 带boolean型参数构造器,true是公平锁,否则为非公平锁
    public ReentrantLock(boolean fair) {
        sync = fair ? new FairSync() : new NonfairSync();
    }

### 非公平锁

#### 获取锁

    /**
         * Sync object for non-fair locks
         */
        static final class NonfairSync extends Sync {
            private static final long serialVersionUID = 7316153563782823691L;
    
            /**
             * Performs lock.  Try immediate barge, backing up to normal
             * acquire on failure.
             */
            final void lock() {
                // 通过cas尝试快速获取锁,成功将设置为当前线程所有
                if (compareAndSetState(0, 1))
                    setExclusiveOwnerThread(Thread.currentThread());
                else
                    acquire(1);
            }
    
            // 自定义同步组件
            protected final boolean tryAcquire(int acquires) {
                return nonfairTryAcquire(acquires);
            }
        }

通过cas尝试快速获取锁,成功将设置为当前线程所有,失败的话调用aqs 里面的 aquire(int arg) 方法即:

        public final void acquire(int arg) {
            if (!tryAcquire(arg) &&
                    acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
                selfInterrupt();
        }

tryAcquire(int arg)需要自定义同步组件提供实现,非公平锁 首先 判断当前线程的状态是否等于0 ,是 的话表明该锁还没被获取 通过cas获取同步状态,同时设置为当前线程所有。
如果当前线程的状态>0, 先判断当前线程是不是已经持有该锁,是就获取锁直接返回true,同步状态增加
    
    final boolean nonfairTryAcquire(int acquires) {
        final Thread current = Thread.currentThread();
        int c = getState();
        if (c == 0) {
            if (compareAndSetState(0, acquires)) {
                setExclusiveOwnerThread(current);
                return true;
            }
        }
        else if (current == getExclusiveOwnerThread()) {
            int nextc = c + acquires;
            if (nextc < 0) // overflow
                throw new Error("Maximum lock count exceeded");
            setState(nextc);
            return true;
        }
        return false;
    }
    
#### 释放锁

    public void unlock() {
            sync.release(1);
    }
    
    public final boolean release(int arg) {
        if (tryRelease(arg)) {
            Node h = head;
            if (h != null && h.waitStatus != 0)
                unparkSuccessor(h);
            return true;
        }
        return false;
    }
    
AQS方法自定义实现释放同步状态tryRelease(int arg)
    
    protected final boolean tryRelease(int releases) {
        int c = getState() - releases;
        if (Thread.currentThread() != getExclusiveOwnerThread())
            throw new IllegalMonitorStateException();
        boolean free = false;
        if (c == 0) {
            free = true;
            setExclusiveOwnerThread(null);
        }
        setState(c);
        return free;
    }
    
只有当同步状态彻底释放后即状态等于0的时候 , 该方法才会返回true。当state == 0 时，则将锁持有线程设置为null，free= true，表示释放成功

### 公平锁 (tps 低)

#### 获取锁

只有自定义aqs模板与非公平锁是不一样的,区别在于是否按照FIFO队列来

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
    
1.头节点 不等于 尾节点
&& 
2.(同步队列下一个节点等于空 或者 当前线程不是同步队列的第一个节点 )
主要是判断当前线程是否位于CLH同步队列中的第一个。如果是则返回true，否则返回false
    
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
    
## ReentrantReadWriteLock

读写锁的自定义同步器需要在同步状态(一个整形变量)上维护多个线程和一个写线程的状态,所以采用了'按位切割使用'这个方式来维护这个变量，读写锁将变量分成了2个部分，高16位读低16位写
当前同步状态为S，那么写状态等于 S & 0x0000FFFF（将高16位全部抹去），读状态等于S >>> 16(无符号补0右移16位)
当写状态增加1时，等于s+1,当读状态增加1时，等于S+（1<<16）也就是S+0x00010000
推论:S不等于0时，当写状态等于0时，读状态大于0时，读锁被获取

    static final int SHARED_SHIFT   = 16;
    static final int SHARED_UNIT    = (1 << SHARED_SHIFT);
    static final int MAX_COUNT      = (1 << SHARED_SHIFT) - 1;
    static final int EXCLUSIVE_MASK = (1 << SHARED_SHIFT) - 1;

    /** Returns the number of shared holds represented in count  */
    static int sharedCount(int c)    { return c >>> SHARED_SHIFT; }
    /** Returns the number of exclusive holds represented in count  */
    static int exclusiveCount(int c) { return c & EXCLUSIVE_MASK; }

默认使用非公平锁 readLock()返回用于读操作的锁，writeLock()返回用于写操作的锁

     public ReentrantReadWriteLock() {
        this(false);
     }
     public ReentrantReadWriteLock(boolean fair) {
        sync = fair ? new FairSync() : new NonfairSync();
        readerLock = new ReadLock(this);
        writerLock = new WriteLock(this);
     }

### 写锁

#### 获取锁
获取同步状态的时候,写锁>0,存在读锁返回false(如果在存在读锁的情况下允许获取写锁，那么那些已经获取读锁的其他线程可能就无法感知当前写线程的操作)
因此只有等读锁完全释放后，写锁才能够被当前线程所获取，一旦写锁获取了，所有其他读、写线程均会被阻塞。

    protected final boolean tryAcquire(int acquires) {
        Thread current = Thread.currentThread();
        int c = getState();
        int w = exclusiveCount(c);
        if (c != 0) {
            // (Note: if c != 0 and w == 0 then shared count != 0)
            if (w == 0 || current != getExclusiveOwnerThread())
                return false;
            if (w + exclusiveCount(acquires) > MAX_COUNT)
                throw new Error("Maximum lock count exceeded");
            // Reentrant acquire
            setState(c + acquires);
            return true;
        }
        // 写锁是否需要被阻塞
        if (writerShouldBlock() ||
            !compareAndSetState(c, c + acquires))
            return false;
        setExclusiveOwnerThread(current);
        return true;
    }

#### 释放锁

写状态为0时表示 写锁已经完全释放了，将锁的持有者设置为空，从而等待的其他线程可以继续访问读写锁，获取同步状态
    
    protected final boolean tryRelease(int releases) {
        if (!isHeldExclusively())
            throw new IllegalMonitorStateException();
        int nextc = getState() - releases;
        boolean free = exclusiveCount(nextc) == 0;
        if (free)
            setExclusiveOwnerThread(null);
        setState(nextc);
        return free;
    }
    
### 读锁

#### 获取锁

共享式获取同步状态: exclusiveCount(c)计算写锁>0 并且 当前线程不是锁持有者， 返回-1(为锁降级开路)。
sharedCount(c) 读锁数量 ，判断读锁是否需要阻塞，读锁持有线程数小于最大值（65535），且设置锁状态成功 并返回1。如果不满足改条件，执行fullTryAcquireShared()
    
    protected final int tryAcquireShared(int unused) {
        Thread current = Thread.currentThread();
        int c = getState();
        // exclusiveCount(c) != 0 --->>> 用 state & 65535 得到低 16 位的值。如果不是0，说明写锁被持有了。
        // getExclusiveOwnerThread() != current----> 不是当前线程
        // 如果写锁被霸占了，且持有线程不是当前线程，返回 false，加入队列。获取写锁失败。
        // 反之，如果持有写锁的是当前线程，就可以继续获取读锁了---->锁降级
        if (exclusiveCount(c) != 0 &&
            getExclusiveOwnerThread() != current)
            return -1;
        // 如果写锁没有被霸占，则将高16位移到低16位。
        int r = sharedCount(c);
        // !readerShouldBlock() 和写锁的逻辑一样（根据是否公平策略和队列是否含有等待节点）
        // 必须小于 65535，且 CAS 修改成功
        if (!readerShouldBlock() &&
            r < MAX_COUNT &&
            compareAndSetState(c, c + SHARED_UNIT)) {
            // 如果读锁是空闲的， 获取锁成功。
            if (r == 0) {
                // 将当前线程设置为第一个读锁线程
                firstReader = current;
                firstReaderHoldCount = 1;
            } else if (firstReader == current) {
                firstReaderHoldCount++;
            } else {
                HoldCounter rh = cachedHoldCounter;
                if (rh == null || rh.tid != getThreadId(current))
                    cachedHoldCounter = rh = readHolds.get();
                else if (rh.count == 0)
                    readHolds.set(rh);
                rh.count++;
            }
            return 1;
        }
        // 死循环获取读锁。包含锁降级策略。
        return fullTryAcquireShared(current);
    }
    
    
    final int fullTryAcquireShared(Thread current) {
        HoldCounter rh = null;
        for (;;) {
            int c = getState();
            if (exclusiveCount(c) != 0) {
                if (getExclusiveOwnerThread() != current)
                    return -1;
                // else we hold the exclusive lock; blocking here
                // would cause deadlock.
            } else if (readerShouldBlock()) {
                // Make sure we're not acquiring read lock reentrantly
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

#### 锁降级

注: 读锁是不可能升级成写锁的
写锁降级成为读锁,如果当前线程拥有写锁，然后将其释放，最后再获取读锁，这种分段完成的过程不能称之为锁降级
锁降级是指把持住(当前拥有的)写锁，再获取读锁，随后释放(先前拥有的)写锁的过程

锁降级中读锁的获取是否是必要呢?(《java并发编程的艺术》)
必要;主要为了保证数据的可见性,如果当前线程不获取读锁而是直接释放写锁,假设有另外一个线程(暂记作线程T)获取了写锁并修改了数据，那么当前线程无法感知线程T的数据更新。
如果当前线程获取读锁，即遵循锁降级的步骤，则线程T将会被阻塞，直到当前线程使用数据并释放读锁之后，线程T才能获取写锁进行数据更新

#### 释放锁
    
    protected final boolean tryReleaseShared(int unused) {
        Thread current = Thread.currentThread();
        //如果想要释放锁的线程为第一个获取锁的线程
        if (firstReader == current) {
            // assert firstReaderHoldCount > 0;
            //仅获取了一次，则需要将firstReader 设置null，否则 firstReaderHoldCount - 1
            if (firstReaderHoldCount == 1)
                firstReader = null;
            else
                firstReaderHoldCount--;
        } else {
        //获取rh对象，并更新“当前线程获取锁的信息”
            HoldCounter rh = cachedHoldCounter;
            if (rh == null || rh.tid != getThreadId(current))
                rh = readHolds.get();
            int count = rh.count;
            if (count <= 1) {
                readHolds.remove();
                if (count <= 0)
                    throw unmatchedUnlockException();
            }
            --rh.count;
        }
        //CAS更新同步状态
        for (;;) {
            int c = getState();
            int nextc = c - SHARED_UNIT;
            if (compareAndSetState(c, nextc))
                // Releasing the read lock has no effect on readers,
                // but it may allow waiting writers to proceed if
                // both read and write locks are now free.
                return nextc == 0;
        }
    }
    
#### condition

condition为线程提供了一种更为灵活的等待/通知模式，线程在调用await方法后执行挂起操作，直到线程等待的某个条件为真时才会被唤醒。
Condition必须要配合锁一起使用，因为对共享状态变量的访问发生在多线程环境下。
一个Condition的实例必须与一个Lock绑定，因此Condition一般都是作为Lock的内部实现

    public class ConditionObject implements Condition, java.io.Serializable {
        private static final long serialVersionUID = 1173984872572414699L;
        /** First node of condition queue. */
        private transient Node firstWaiter;
        /** Last node of condition queue. */
        private transient Node lastWaiter;

        public ConditionObject() { }

        private Node addConditionWaiter() {
            Node t = lastWaiter;
            // If lastWaiter is cancelled, clean out.
            if (t != null && t.waitStatus != Node.CONDITION) {
                unlinkCancelledWaiters();
                t = lastWaiter;
            }
            Node node = new Node(Thread.currentThread(), Node.CONDITION);
            if (t == null)
                firstWaiter = node;
            else
                t.nextWaiter = node;
            lastWaiter = node;
            return node;
        }
        
##### 等待 await
    
    public final void await() throws InterruptedException {
    // 当前线程中断
        if (Thread.interrupted())
            throw new InterruptedException();
            // 加入到等待队列
        Node node = addConditionWaiter();
        // 释放锁
        int savedState = fullyRelease(node);
        int interruptMode = 0;
        // 检测此节点的线程是否在同步队上，如果不在，则说明该线程还不具备竞争锁的资格，则继续等待,直到检测到此节点在同步队列上
        while (!isOnSyncQueue(node)) {
        // 线程挂起
            LockSupport.park(this);
            // 如果已经中断了 跳出循环
            if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
                break;
        }
        // 竞争同步状态
        if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
            interruptMode = REINTERRUPT;
            //清理下条件队列中的不是在等待条件的节点
        if (node.nextWaiter != null) // clean up if cancelled
            unlinkCancelledWaiters();
        if (interruptMode != 0)
            reportInterruptAfterWait(interruptMode);
    }

##### 通知 signal()

    public final void signal() {
        //检测当前线程是否获取了锁
        if (!isHeldExclusively())
            throw new IllegalMonitorStateException();
        //头节点，唤醒条件队列中的第一个节点
        Node first = firstWaiter;
        if (first != null)
            doSignal(first);    //唤醒
    }

##### Condition实现生产者消费者
