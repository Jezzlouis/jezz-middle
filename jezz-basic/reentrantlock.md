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
        if (exclusiveCount(c) != 0 &&
            getExclusiveOwnerThread() != current)
            return -1;
        int r = sharedCount(c);
        if (!readerShouldBlock() &&
            r < MAX_COUNT &&
            compareAndSetState(c, c + SHARED_UNIT)) {
            if (r == 0) {
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

#### 释放锁