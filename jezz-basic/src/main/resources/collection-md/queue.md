## queue
阻塞队列 : 锁的应用
非阻塞队列 : cas的应用

### ConcurrentLinkedQueue

#### head的不变性和可变性 《翻译 private transient volatile Node<E> head; 上的注释》

不变性 :
 所有未删除的节点都可以通过head节点遍历到
 head不能为null
 head节点的next不能指向自身
可变性 :
 head的item可能为null，也可能不为null
 允许tail滞后head，也就是说调用succc()方法，从head不可达tail
 
#### tail的不变性和可变性 《翻译 private transient volatile Node<E> tail; 上的注释》

不变性 :
 tail不能为null
可变性 :
 tail的item可能为null，也可能不为null
 tail节点的next域可以指向自身
 允许tail滞后head，也就是说调用succc()方法，从head不可达tail

#### 入队 :

入队主要做两件事情:
第一是将入队节点设置成当前队列尾节点的下一个节点。
第二是更新tail节点，如果tail节点的next节点不为空，则将入队节点设置成tail节点，如果tail节点的next节点为空，则将入队节点设置成tail的next节点，所以tail节点不总是尾节点

    public boolean offer(E e) {
        //检查节点是否为null
        checkNotNull(e);
        // 创建新节点
        final Node<E> newNode = new Node<E>(e);

        //死循环 入队不成功反复入队(初始化的时候: t是尾节点,p是头节点)
        for (Node<E> t = tail, p = t;;) {
            Node<E> q = p.next;
            // q == null 表示 p已经是最后一个节点了，尝试加入到队列尾
            if (q == null) {                               
                // casNext：将入队节点设置成当前队列尾节点的next节点
                // casTail：设置tail 尾节点
                if (p.casNext(null, newNode)) {             
                    if (p != t)     // 更新尾节点                        
                        casTail(t, newNode);                    
                    return true;
                }
            }
            // p == q 等于自身
            else if (p == q)                                
                // p == q 代表着该节点已经被删除了
                // tail位于head的前面,则需要重新设置p
                p = (t != (t = tail)) ? t : head;           
            // tail并没有指向尾节点
            else
                // tail已经不是最后一个节点，将p指向最后一个节点
                p = (p != t && t != (t = tail)) ? t : q;    
        }
    }
    
#### 出队 :

    public E poll() {
        restartFromHead:
        for (;;) {
            for (Node<E> h = head, p = h, q;;) {
                E item = p.item;

                if (item != null && p.casItem(item, null)) {
                    // Successful CAS is the linearization point
                    // for item to be removed from this queue.
                    if (p != h) // hop two nodes at a time
                        updateHead(h, ((q = p.next) != null) ? q : p);
                    return item;
                }
                else if ((q = p.next) == null) {
                    updateHead(h, p);
                    return null;
                }
                else if (p == q)
                    continue restartFromHead;
                else
                    p = q;
            }
        }
    }
    
## 阻塞队列 BlockingQueue (基于锁)

### ArrayBlockingQueue 

由数组实现的有界阻塞队列
ArrayBlockingQueue 支持对等待的生产者线程和使用者线程进行排序的可选公平策略，但是在默认情况下不保证线程公平的访问，在构造时可以选择公平策略（fair = true）

#### 入队 add(E e)
    
    public boolean add(E e) {
        return super.add(e);
    }

    public boolean add(E e) {
        if (offer(e))
            return true;
        else
            throw new IllegalStateException("Queue full");
    }
    
    public boolean offer(E e) {
        checkNotNull(e);
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (count == items.length)
                return false;
            else {
                enqueue(e);
                return true;
            }
        } finally {
            lock.unlock();
        }
    }
    
    // 队尾插入元素
    private void enqueue(E x) {
        // assert lock.getHoldCount() == 1;
        // assert items[putIndex] == null;
        final Object[] items = this.items;
        items[putIndex] = x;
        if (++putIndex == items.length)
            putIndex = 0;
        count++;
        // 通知出列的线程,如果队列为空阻塞
        notEmpty.signal();
    }

#### 出队 poll()

    public E poll() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return (count == 0) ? null : dequeue();
        } finally {
            lock.unlock();
        }
    }
    
    private E dequeue() {
        final Object[] items = this.items;
        E x = (E) items[takeIndex];
        items[takeIndex] = null;
        if (++takeIndex == items.length)
            takeIndex = 0;
        count--;
        if (itrs != null)
            itrs.elementDequeued();
        notFull.signal();
        return x;
    }

### LinkedBlockingQueue 

由链表实现的有界阻塞队列，此列的默认和最大长度为Integer.MAX_VALUE 先进先出原则

### PriorityBlockingQueue

支持优先级的无界阻塞队列.默认情况下元素采用自然顺序升序排序，当然我们也可以通过构造函数来指定Comparator来对元素进行排序。
需要注意的是PriorityBlockingQueue不能保证同优先级元素的顺序

### DelayQueue

支持延时获取元素的无界阻塞队列。里面的元素全部都是“可延期”的元素，列头的元素是最先“到期”的元素，如果队列里面没有元素到期，是不能从列头获取元素的，哪怕有元素也不行。
也就是说只有在延迟期到时才能够从队列中取元素

应用场景: 缓存系统的设计, delayqueue保存元素的有效期，一旦能获取到，说明有限期到了
定时任务调度: TimerQueue

### SynchornousQueue

不存储元素的阻塞队列。每一个put操作都必须等待一个take操作，否则不能继续添加元素
它支持公平访问队列，默认情况下现场采用非公平策略访问队列，如果设置为true，采用先进先出的顺序访问队列

### LinkedTransferQueue

基于链表的 FIFO 无界阻塞队列

transfer 方法: 如果当前有消费者正在等待接收元素(使用了带时间限制的poll或take方法时)，transfer方法可以立即把生产者传入的元素传输给消费者
tryTransfer 方法: 试探生产者传入的元素是否能直接传给消费者。如果没有消费者，返回false
区别: trytransfer 无论消费者是否接收，方法立即返回，transfer方法是必须等到消费者消费了才返回

### LinkedBlockingDeque
    
由链表组成的双向阻塞队列
应用 : "工作窃取" 模式中

## Fork/Join 框架

### 工作窃取算法 某个线程从其他队列里窃取任务来执行 一般采用双端队列 被窃取任务自己从头部拿数据，窃取任务从尾部拿数据

### 设计

1. 分割任务
2. 执行任务并合并结果

Fork/Join使用两个类来完成上述2件事

1. 继承ForkJoinTask的子类:(RecursiveAction 用于没有返回结果的任务)(RecursiveTask 用于有返回结果的任务)
2. ForkJoinPool

### 异常处理

ForkJoinTask在执行的时候可能会抛出异常，但是我们没办法在主线程里面直接捕获异常，所以ForkJoinTask提供了isCompletedAbnormally()方法来检查任务是否已经抛出异常
或被取消了，并且可以通过ForkJoinTask的getException方法获取异常

### 实现原理
ForkJoinPool由ForkJoinTask数组和ForkJoinWorkThread数组组成，ForkJoinTask数组负责存放程序提交给pool的任务，ForkJoinWorkerThread数组负责执行这些任务




