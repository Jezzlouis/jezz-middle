## 线程池

### 内部状态

线程有五种状态：新建，就绪，运行，阻塞，死亡，线程池同样有五种状态：Running, SHUTDOWN, STOP, TIDYING, TERMINATED。

    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;

    // runState is stored in the high-order bits
    private static final int RUNNING    = -1 << COUNT_BITS;
    private static final int SHUTDOWN   =  0 << COUNT_BITS;
    private static final int STOP       =  1 << COUNT_BITS;
    private static final int TIDYING    =  2 << COUNT_BITS;
    private static final int TERMINATED =  3 << COUNT_BITS;

    // Packing and unpacking ctl
    private static int runStateOf(int c)     { return c & ~CAPACITY; }
    private static int workerCountOf(int c)  { return c & CAPACITY; } // workerCountOf(ctl)获取低29位
    private static int ctlOf(int rs, int wc) { return rs | wc; }

ctl : 高3位表示”线程池状态”，低29位表示”线程池中的任务数量”。

    RUNNING            -- 对应的高3位值是111。
    SHUTDOWN       -- 对应的高3位值是000。
    STOP                   -- 对应的高3位值是001。
    TIDYING              -- 对应的高3位值是010。
    TERMINATED     -- 对应的高3位值是011。
      
### 创建线程池 7个参数 4种策略

corePoolSize 核心线程数 maximumPoolSize 最大线程数 keepAliveTime 线程空闲时间 unit 时间单位 

workQueue 用来保存等待执行的任务的阻塞队列 :

    ArrayBlockingQueue:基于数组结构的有界阻塞队列,FIFO原则
    LinkedBlockingQueue:基于链表结构的阻塞队列,吞吐量高于ArrayBlockingQueue,newFixedThreadPool里面使用了他
    SynchronousQueue:不存储元素的阻塞队列,吞吐量高于LinkedBlockingQueue,newCachedThreadPool里面使用了他
    PriorityBlockingQueue:一个具有优先级的无界阻塞队列

threadFactory 设置创建线程的工厂 

handler 线程池的拒绝策略;也可以实现自己的拒绝策略，实现RejectedExecutionHandler接口即可

线程池提供了四种拒绝策略：

    AbortPolicy：直接抛出异常，默认策略；
    CallerRunsPolicy：用调用者所在的线程来执行任务；
    DiscardOldestPolicy：丢弃阻塞队列中靠最前的任务，并执行当前任务；
    DiscardPolicy：直接丢弃任务；

### Executor框架提供了三种线程池，他们都可以通过工具类Executors来创建

#### FixedThreadPool

    public static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads, nThreads,
                                      0L, TimeUnit.MILLISECONDS,
                                      new LinkedBlockingQueue<Runnable>());
    }
    
corePoolSize 和 maximumPoolSize都设置为创建FixedThreadPool时指定的参数nThreads，意味着当线程池满时且阻塞队列也已经满时，
如果继续提交任务，则会直接走拒绝策略，该线程池不会再新建线程来执行任务，而是直接走拒绝策略。FixedThreadPool使用的是默认的拒绝策略，即AbortPolicy，
则直接抛出异常。

keepAliveTime设置为0L，表示空闲的线程会立刻终止。

workQueue则是使用LinkedBlockingQueue，但是没有设置范围，那么则是最大值（Integer.MAX_VALUE），这基本就相当于一个无界队列了。
当线程池中的线程数量等于corePoolSize 时，如果继续提交任务，该任务会被添加到阻塞队列workQueue中，当阻塞队列也满了之后，
则线程池会新建线程执行任务直到maximumPoolSize。由于FixedThreadPool使用的是“无界队列”LinkedBlockingQueue，那么maximumPoolSize参数无效，
同时指定的拒绝策略AbortPolicy也将无效

#### SingleThreadExecutor

    public static ExecutorService newSingleThreadExecutor() {
        return new FinalizableDelegatedExecutorService
            (new ThreadPoolExecutor(1, 1,
                                    0L, TimeUnit.MILLISECONDS,
                                    new LinkedBlockingQueue<Runnable>()));
    }

作为单一worker线程的线程池，SingleThreadExecutor把corePool和maximumPoolSize均被设置为1，
和FixedThreadPool一样使用的是无界队列LinkedBlockingQueue,所以带来的影响和FixedThreadPool一样。

#### CachedThreadPool
    
    public static ExecutorService newCachedThreadPool() {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                      60L, TimeUnit.SECONDS,
                                      new SynchronousQueue<Runnable>());
    }
    
CachedThreadPool的corePool为0，maximumPoolSize为Integer.MAX_VALUE，这就意味着所有的任务一提交就会加入到阻塞队列中。keepAliveTime这是为60L，
unit设置为TimeUnit.SECONDS，意味着空闲线程等待新任务的最长时间为60秒，空闲线程超过60秒后将会被终止。阻塞队列采用的SynchronousQueue
如果主线程提交任务的速度远远大于CachedThreadPool的处理速度，则CachedThreadPool会不断地创建新线程来执行任务，这样有可能会导致系统耗尽CPU和内存资源，
* 所以在使用该线程池是，一定要注意控制并发的任务数，否则创建大量的线程可能导致严重的性能问题。

### 任务提交

两种方式提交任务: Executor.execute()、ExecutorService.submit()
    
    public interface Executor {
    
        void execute(Runnable command);
    }

    public void execute(Runnable command) {
        if (command == null)
            throw new NullPointerException();
        int c = ctl.get();
        if (workerCountOf(c) < corePoolSize) {
            if (addWorker(command, true))
                return;
            c = ctl.get();
        }
        if (isRunning(c) && workQueue.offer(command)) {
            int recheck = ctl.get();
            if (! isRunning(recheck) && remove(command))
                reject(command);
            else if (workerCountOf(recheck) == 0)
                addWorker(null, false);
        }
        else if (!addWorker(command, false))
            reject(command);
    }

执行流程:

1.如果线程池当前线程数小于corePoolSize，则调用addWorker创建新线程执行任务，成功返回true，失败执行步骤2。
2.如果线程池处于RUNNING状态，则尝试加入阻塞队列，如果加入阻塞队列成功，则尝试进行Double Check，如果加入失败，则执行步骤3。
3.如果线程池不是RUNNING状态或者加入阻塞队列失败，则尝试创建新线程直到maxPoolSize，如果失败，则调用reject()方法运行相应的拒绝策略

### 线程终止

## ScheduledThreadPoolExecutor

### 构造方法
    
    public ScheduledThreadPoolExecutor(int corePoolSize) {
        super(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS,
                new DelayedWorkQueue());
    }

    public ScheduledThreadPoolExecutor(int corePoolSize,
                                       ThreadFactory threadFactory) {
        super(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS,
                new DelayedWorkQueue(), threadFactory);
    }

    public ScheduledThreadPoolExecutor(int corePoolSize,
                                       RejectedExecutionHandler handler) {
        super(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS,
                new DelayedWorkQueue(), handler);
    }


    public ScheduledThreadPoolExecutor(int corePoolSize,
                                       ThreadFactory threadFactory,
                                       RejectedExecutionHandler handler) {
        super(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS,
                new DelayedWorkQueue(), threadFactory, handler);
    }
    
#### 调度器

schedule(Callable callable, long delay, TimeUnit unit) :创建并执行在给定延迟后启用的 ScheduledFuture。
schedule(Runnable command, long delay, TimeUnit unit) :创建并执行在给定延迟后启用的一次性操作。
scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) :
创建并执行一个在给定初始延迟后首次启用的定期操作，后续操作具有给定的周期；也就是将在 initialDelay 后开始执行，然后在 initialDelay+period 后执行，
接着在 initialDelay + 2 * period 后执行，依此类推。
scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) :
创建并执行一个在给定初始延迟后首次启用的定期操作，随后，在每一次执行终止和下一次执行开始之间都存在给定的延迟。