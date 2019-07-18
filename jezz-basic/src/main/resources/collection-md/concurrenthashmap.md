## ConcurrentHashMap

为什么不使用hashMap: 

1.7 put会导致死循环: rehash的时候 将每个链表转化到新链表，并且链表中的位置发生反转，而这在多线程情况下是很容易造成链表回路，从而发生 get() 死循环
1.8 进行了优化 用 head 和 tail 来保证链表的顺序和之前一样 但是还有数据丢失等弊端(并发本身的问题)

为什么是 (n-1)&hash 
  
  因为除法运算,取余运算是需要消耗几十个CPU周期,而按位与运算只需要消耗一个CPU周期
 
为什么 n是2的幂次方  
 
  因为当n是2的幂次方的时候 (n-1)& hash 的结果刚好和 取余运算的结果是一样的
  
### 

### 内部类 Node

    static class Node<K,V> implements Map.Entry<K,V> {
        final int hash;
        final K key;
        volatile V val;             //带有volatile，保证可见性
        volatile Node<K,V> next;    //下一个节点的指针

        Node(int hash, K key, V val, Node<K,V> next) {
            this.hash = hash;
            this.key = key;
            this.val = val;
            this.next = next;
        }

        public final K getKey()       { return key; }
        public final V getValue()     { return val; }
        public final int hashCode()   { return key.hashCode() ^ val.hashCode(); }
        public final String toString(){ return key + "=" + val; }
       
        public final V setValue(V value) {
            throw new UnsupportedOperationException();
        }

        public final boolean equals(Object o) {
            Object k, v, u; Map.Entry<?,?> e;
            return ((o instanceof Map.Entry) &&
                    (k = (e = (Map.Entry<?,?>)o).getKey()) != null &&
                    (v = e.getValue()) != null &&
                    (k == key || k.equals(key)) &&
                    (v == (u = val) || v.equals(u)));
        }

        Node<K,V> find(int h, Object k) {
            Node<K,V> e = this;
            if (k != null) {
                do {
                    K ek;
                    if (e.hash == h &&
                            ((ek = e.key) == k || (ek != null && k.equals(ek))))
                        return e;
                } while ((e = e.next) != null);
            }
            return null;
        }
    }


### ConcurrentHashMap的初始化

### 变量
    
    /**
     * Table initialization and resizing control.  When negative, the
     * table is being initialized or resized: -1 for initialization,
     * else -(1 + the number of active resizing threads).  Otherwise,
     * when table is null, holds the initial table size to use upon
     * creation, or 0 for default. After initialization, holds the
     * next element count value upon which to resize the table.
     */
    private transient volatile int sizeCtl;
    sizeCtl 是关键，该变量高 16 位保存 length 生成的标识符，低 16 位保存并发扩容的线程数
    -1，表示有线程正在进行初始化操作
    -(1 + nThreads)，表示有n个线程正在一起扩容
    0，默认值，后续在真正初始化的时候使用默认容量
    > 0，初始化或扩容完成后下一次的扩容门槛
    
    /*
     * Encodings for Node hash fields. See above for explanation.
     */
    static final int MOVED     = -1; // hash for forwarding nodes
    static final int TREEBIN   = -2; // hash for roots of trees
    static final int RESERVED  = -3; // hash for transient reservations
    static final int HASH_BITS = 0x7fffffff; // usable bits of normal node hash

### put操作

    public V put(K key, V value) {
        return putVal(key, value, false);
    }
    
    final V putVal(K key, V value, boolean onlyIfAbsent) {
    // key 和 value 都不能为空 否则抛出异常
        if (key == null || value == null) throw new NullPointerException();
        // 计算key的hash值
        int hash = spread(key.hashCode());
        int binCount = 0;
        // 死循环插入
        for (Node<K,V>[] tab = table;;) {
            Node<K,V> f; int n, i, fh;
            if (tab == null || (n = tab.length) == 0)
            // 初始化tab
                tab = initTable();
                // 计算key在tab里面的索引值 (n-1) & hash
            else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) {
            // tab通过获取该索引的值为空的时候 CAS插入
                if (casTabAt(tab, i, null,
                             new Node<K,V>(hash, key, value, null)))
                    break;                   // no lock when adding to empty bin
            }
            // 如果key的hash值 == MOVED == -1 说明其他线程正在扩容
            else if ((fh = f.hash) == MOVED)
                tab = helpTransfer(tab, f);
            else {
                V oldVal = null;
                synchronized (f) {
                    // 计算桶的第一个元素的hash值
                    if (tabAt(tab, i) == f) {
                    // 大于0 说明不是树 也不在迁移
                        if (fh >= 0) {
                            binCount = 1; // binCount 赋值为1
                            for (Node<K,V> e = f;; ++binCount) {
                                K ek;
                                if (e.hash == hash &&
                                    ((ek = e.key) == key ||
                                     (ek != null && key.equals(ek)))) {
                                    oldVal = e.val;
                                    // 如果找到了这个元素，则赋值了新值
                                    if (!onlyIfAbsent)
                                        e.val = value;
                                    break;
                                }
                                Node<K,V> pred = e; // 尾节点
                                if ((e = e.next) == null) {
                                // 到尾节点还没找到元素，则插入尾节点
                                    pred.next = new Node<K,V>(hash, key,
                                                              value, null);
                                    break;
                                }
                            }
                        }
                        else if (f instanceof TreeBin) { // 如果桶的第一个节点是树节点
                            Node<K,V> p;
                            binCount = 2; // binCount 赋值为2
                            // 调用红黑树的插入方法插入元素
                            if ((p = ((TreeBin<K,V>)f).putTreeVal(hash, key,
                                                           value)) != null) {
                                oldVal = p.val;
                                if (!onlyIfAbsent)
                                    p.val = value;
                            }
                        }
                    }
                }
                if (binCount != 0) {
                // 如果binCount > = 8 树化
                    if (binCount >= TREEIFY_THRESHOLD)
                        treeifyBin(tab, i);
                    if (oldVal != null)
                        return oldVal;
                    break;
                }
            }
        }
        addCount(1L, binCount);
        return null;
    }
    
    static final int HASH_BITS = 0x7fffffff;
    
    // 0x7fffffff == 1111111111111111111111111111111  
    假设原来的key的hash值 h 为 12528 ------ h >>> 16
    
    实际上就是高 16位和 低 16位进行异或
    0000 0000 0000 0011 0000 0000 1111 0000 ---按位异或--- 0000 0000 0000 0000 0000 0000 0000 0011
    
    0000 0000 0000 0011 0000 0000 1111 0011 ---按位与---   1111 1111 1111 1111 1111 1111 1111 1111
    结果
    0000 0000 0000 0011 0000 0000 1111 0011
    
    static final int spread(int h) {
        return (h ^ (h >>> 16)) & HASH_BITS;
    }
    
    private final Node<K,V>[] initTable() {
        Node<K,V>[] tab; int sc;
        while ((tab = table) == null || tab.length == 0) {
            if ((sc = sizeCtl) < 0)
            // sizeCtl 小于0 意味着其他线程CAS操作成功(正处于初始化或者扩容的时候) 当前线程让出CPU
                Thread.yield(); // lost initialization race; just spin
            else if (U.compareAndSwapInt(this, SIZECTL, sc, -1)) {
            // CAS操作(Unsafe.compareAndSwapInt方法)修改sizeCtl为-1成功，当前线程开始初始化
            // CAS 失败不执行if 和 elseif 执行下个循环，同时CAS 失败意味着有其他线程CAS成功正在初始化 ，其他线程CAS成功sizeCtl被其他线程修改为-1成功
            那么在下个while循环的时候 执行sizeCtl<0 一直会为正在初始化的那个线程让出CPU
            // CAS 初始化成功之后 因为tab.length >0 而不再进行while循环
                try {
                    if ((tab = table) == null || tab.length == 0) {
                    // sc 小于等于 0都是默认值16
                        int n = (sc > 0) ? sc : DEFAULT_CAPACITY;
                        @SuppressWarnings("unchecked")
                        Node<K,V>[] nt = (Node<K,V>[])new Node<?,?>[n];
                        table = tab = nt;
                        // 初始化的时候默认是12
                        sc = n - (n >>> 2);
                    }
                } finally {
                    // 将扩容门槛赋值给sizeCtl
                    sizeCtl = sc;
                }
                break;
            }
        }
        return tab;
    }
    
    n == 16 ---- n >>> 2  == 4 == n/2的2次方
    0000 0000 0000 0000 0000 0000 0001 0000 ---- 0000 0000 0000 0000 0000 0000 0000 0100
    sc = n -(n >>>2) -------16-4 =12  == 0.75 n
    // 我们知道hashmap 的扩容门槛 threshold = capacity * loadFactor 默认是0.75倍容量。而concurrenthashmap将 扩容门槛写死为0.75倍n容量
     这也正是没有threshold和loadFactor属性的原因
    
     // 获取当前索引的值(volatile获取)
     static final <K,V> Node<K,V> tabAt(Node<K,V>[] tab, int i) {
        return (Node<K,V>)U.getObjectVolatile(tab, ((long)i << ASHIFT) + ABASE);
     }
     
     /**
      * Helps transfer if a resize is in progress.
        翻译成其他线程正在扩容的时候 一起迁移
      */
     final Node<K,V>[] helpTransfer(Node<K,V>[] tab, Node<K,V> f) {
         Node<K,V>[] nextTab; int sc;
         // 如果桶数组不为空 头结点是forwardingNode 并且 nextTab不为空
         if (tab != null && (f instanceof ForwardingNode) &&
             (nextTab = ((ForwardingNode<K,V>)f).nextTable) != null) {
             // 根据 length 得到一个标识符号
             int rs = resizeStamp(tab.length);
             while (nextTab == nextTable && table == tab &&
                    (sc = sizeCtl) < 0) {
                // sizeCtl<0，说明正在扩容
                // 如果 sizeCtl 无符号右移  16 不等于 rs （ sc前 16 位如果不等于标识符，则标识符变化了）
                // 或者 sizeCtl == rs + 1  （扩容结束了，不再有线程进行扩容），
                在addCount方法中（默认第一个线程设置 sc ==rs 左移 16 位 + 2，当第一个线程结束扩容了，就会将 sc 减一。这个时候，sc 就等于 rs + 1）
                // 或者 sizeCtl == rs + 65535  （如果达到最大帮助线程的数量，即 65535）
                // 或者转移下标正在调整 （扩容结束）
                // 结束循环，返回 table
                 if ((sc >>> RESIZE_STAMP_SHIFT) != rs || sc == rs + 1 ||
                     sc == rs + MAX_RESIZERS || transferIndex <= 0)
                     break;
                  // 如果以上都不是, 将 sizeCtl + 1, （表示增加了一个线程帮助其扩容）
                 if (U.compareAndSwapInt(this, SIZECTL, sc, sc + 1)) {
                     transfer(tab, nextTab);
                     break;
                 }
             }
             return nextTab;
         }
         return table;
     }

    
     /**
     * A node inserted at head of bins during transfer operations.
     * ForwardingNode是在迁移期间插入到桶头的节点
     * ForwardingNode一个用于连接两个table的节点类。它包含一个nextTable指针，用于指向下一张表
     * 而且这个节点的key value next指针全部为null，它的hash值为-1
     */
    static final class ForwardingNode<K,V> extends Node<K,V> {
        final Node<K,V>[] nextTable;
        ForwardingNode(Node<K,V>[] tab) {
            super(MOVED, null, null, null);
            this.nextTable = tab;
        }
        //find的方法是从nextTable里进行查询节点，而不是以自身为头节点进行查找
        Node<K,V> find(int h, Object k) {
            // loop to avoid arbitrarily deep recursion on forwarding nodes
            outer: for (Node<K,V>[] tab = nextTable;;) {
                Node<K,V> e; int n;
                if (k == null || tab == null || (n = tab.length) == 0 ||
                    (e = tabAt(tab, (n - 1) & h)) == null)
                    return null;
                for (;;) {
                    int eh; K ek;
                    if ((eh = e.hash) == h &&
                        ((ek = e.key) == k || (ek != null && k.equals(ek))))
                        return e;
                    if (eh < 0) {
                        if (e instanceof ForwardingNode) {
                            tab = ((ForwardingNode<K,V>)e).nextTable;
                            continue outer;
                        }
                        else
                            return e.find(h, k);
                    }
                    if ((e = e.next) == null)
                        return null;
                }
            }
        }
    }
    
    RESIZE_STAMP_BITS = 16  
    static final int resizeStamp(int n) {
        return Integer.numberOfLeadingZeros(n) | (1 << (RESIZE_STAMP_BITS - 1));
    }
    
    (1 << (RESIZE_STAMP_BITS - 1))
    0000 0000 0000 0000 0000 0000 0000 0001 ------------------------     0000 0000 0000 0001 0000 0000 0000 0000
                                                                         0000 0000 0000 0000 0000 0000 0001 1111
     Integer.numberOfLeadingZeros(n) | (1 << (RESIZE_STAMP_BITS - 1))    0000 0000 0000 0001 0000 0000 0001 1111
    
    // 返回非0数字的个数
    public static int numberOfLeadingZeros(int i) {
        // HD, Figure 5-6
        if (i == 0)
            return 32;
        int n = 1;
        // i >>> 16 高16位右移等于0 说明有数据的部分在低16位，高16都为0， i = i << 16 低16位左移
        if (i >>> 16 == 0) { n += 16; i <<= 16; }
        if (i >>> 24 == 0) { n +=  8; i <<=  8; }
        if (i >>> 28 == 0) { n +=  4; i <<=  4; }
        if (i >>> 30 == 0) { n +=  2; i <<=  2; }
        // n = n - (i >>> 31)
        //i <<16  0000 0000 0000 0000 0000 0000 0000 0001 ---- 0000 0000 0000 0001 0000 0000 0000 0000
        //i >>>31 = 0
        n -= i >>> 31;
        return n;
    }
    
    /**
     * Moves and/or copies the nodes in each bin to new table. See
     * above for explanation.
     */
    private final void transfer(Node<K,V>[] tab, Node<K,V>[] nextTab) {
        int n = tab.length, stride;
        // 将 length / 8 然后除以 CPU核心数。如果得到的结果小于 16，那么就使用 16。
        // 这里的目的是让每个 CPU 处理的桶一样多，避免出现转移任务不均匀的现象，如果桶较少的话，默认一个 CPU（一个线程）处理 16 个桶
        if ((stride = (NCPU > 1) ? (n >>> 3) / NCPU : n) < MIN_TRANSFER_STRIDE)
            stride = MIN_TRANSFER_STRIDE; // subdivide range
        if (nextTab == null) {            // initiating
            try {
                @SuppressWarnings("unchecked")
                // n <<1 扩容2倍
                Node<K,V>[] nt = (Node<K,V>[])new Node<?,?>[n << 1];
                nextTab = nt;
            } catch (Throwable ex) {      // try to cope with OOME
            // 扩容失败 使用最大值
                sizeCtl = Integer.MAX_VALUE;
                return;
            }
            // 将新的tab赋值给临时表
            nextTable = nextTab;
            // 转移下标更新为老tab的长度
            transferIndex = n;
        }
        // 新tab的长度
        int nextn = nextTab.length;
        // 创建一个 fwd 节点，用于占位。当别的线程发现这个槽位中是 fwd 类型的节点，则跳过这个节点。
        ForwardingNode<K,V> fwd = new ForwardingNode<K,V>(nextTab);
        boolean advance = true;
        boolean finishing = false; // to ensure sweep before committing nextTab
        // 死循环,i 表示下标，bound 表示当前线程可以处理的当前桶区间最小下标
        for (int i = 0, bound = 0;;) {
            Node<K,V> f; int fh;
            // while循环领取区间任务
            while (advance) {
                int nextIndex, nextBound;
                // 进来的时候 --i < bound ,同时 transferIndex > 0，说明上次领的任务已经完成，所以会进到CAS领取区间任务 
                // i-1 >=bound 或者 finishing 成立说明 还在做任务 不能继续推进
                if (--i >= bound || finishing)
                // 这里设置 false，是为了防止在没有成功处理一个桶的情况下却进行了推进
                    advance = false;
                else if ((nextIndex = transferIndex) <= 0) {
                // 如果小于等于0，说明没有区间了 ，i 改成 -1，推进状态变成 false，不再推进，表示，扩容结束了，当前线程可以退出了
                    i = -1;
                    advance = false;
                }
                // 这里的目的是：
                1. 当一个线程进入时，会选取最新的转移下标。
                2. 当一个线程处理完自己的区间时，如果还有剩余区间的没有别的线程处理。再次获取区间。
                else if (U.compareAndSwapInt
                         (this, TRANSFERINDEX, nextIndex,
                          nextBound = (nextIndex > stride ?
                                       nextIndex - stride : 0))) { // CAS 修改 transferIndex，即 length - 区间值，留下剩余的区间值供后面的线程使用
                    bound = nextBound;// 这个值就是当前线程可以处理的最小当前区间最小下标
                    i = nextIndex - 1;// 初次对i 赋值，这个就是当前线程可以处理的当前区间的最大下标
                    advance = false;// 这里设置 false，是为了防止在没有成功处理一个桶的情况下却进行了推进，这样对导致漏掉某个桶。下面的 if (tabAt(tab, i) == f) 判断会出现这样的情况。
                }
            }
            if (i < 0 || i >= n || i + n >= nextn) {
            // 扩容结束
                int sc;
                if (finishing) {// 如果完成了扩容
                    nextTable = null;// 临时表释放空间
                    table = nextTab;
                    sizeCtl = (n << 1) - (n >>> 1);// 更新阀值
                    return;
                }
                if (U.compareAndSwapInt(this, SIZECTL, sc = sizeCtl, sc - 1)) {
                // 尝试将 sc -1. 表示这个线程结束帮助扩容了，将 sc 的低 16 位减一。
                    if ((sc - 2) != resizeStamp(n) << RESIZE_STAMP_SHIFT)
                    //如果 sc - 2 不等于标识符左移 16 位。如果他们相等了，说明没有线程在帮助他们扩容了。也就是说，扩容结束了。
                        return;
                    finishing = advance = true;
                    i = n; // recheck before commit // 再次循环检查一下整张表
                }
            }
            else if ((f = tabAt(tab, i)) == null) // 获取老 tab i 下标位置的变量，如果是 null，就使用 fwd 占位。
                advance = casTabAt(tab, i, null, fwd);
            else if ((fh = f.hash) == MOVED)// 如果不是 null 且 hash 值是 MOVED。
                advance = true; // already processed// 说明别的线程已经处理过了
            else {
                synchronized (f) {
                // 判断 i 下标处的桶节点是否和 f 相同
                    if (tabAt(tab, i) == f) {
                        Node<K,V> ln, hn;// low, height 高位桶，低位桶
                        // 如果 f 的 hash 值大于 0 。TreeBin 的 hash 是 -2
                        if (fh >= 0) {
                            int runBit = fh & n;
                            Node<K,V> lastRun = f;// 尾节点，且和头节点的 hash 值取于不相等
                            // 遍历这个桶
                            for (Node<K,V> p = f.next; p != null; p = p.next) {
                            // 桶中每个节点的 hash 值 按位与 桶的长度
                                int b = p.hash & n;
                                // 如果和首节点不同，更新runBit
                                if (b != runBit) {
                                    runBit = b;
                                    lastRun = p;
                                }
                            }
                            // 由于 Map 的长度都是 2 的次方（000001000 这类的数字），那么取于 length 只有 2 种结果，一种是 0，一种是1
                            if (runBit == 0) { // 如果最后更新的 runBit 是 0 ，设置低位节点
                                ln = lastRun;
                                hn = null;
                            }
                            else {// 如果是1 设置高位节点
                                hn = lastRun;
                                ln = null;
                            }
                            // 再次循环，生成两个链表，lastRun 作为停止条件，这样就是避免无谓的循环（lastRun 后面都是相同的取于结果）
                            for (Node<K,V> p = f; p != lastRun; p = p.next) {
                                int ph = p.hash; K pk = p.key; V pv = p.val;
                                // 如果与运算结果是 0，那么就还在低位
                                if ((ph & n) == 0) // 如果是0 ，那么创建低位节点
                                    ln = new Node<K,V>(ph, pk, pv, ln);
                                else // 1 则创建高位
                                    hn = new Node<K,V>(ph, pk, pv, hn);
                            }
                            // 其实这里类似 hashMap 
                            // 设置低位链表放在新链表的 i
                            setTabAt(nextTab, i, ln);
                            // 设置高位链表，在原有长度上加 n
                            setTabAt(nextTab, i + n, hn);
                            // 将旧的链表设置成占位符
                            setTabAt(tab, i, fwd);
                            // 继续向后推进
                            advance = true;
                        }
                        else if (f instanceof TreeBin) { // 如果是红黑树
                            TreeBin<K,V> t = (TreeBin<K,V>)f;
                            TreeNode<K,V> lo = null, loTail = null;
                            TreeNode<K,V> hi = null, hiTail = null;
                            int lc = 0, hc = 0;
                            // 遍历
                            for (Node<K,V> e = t.first; e != null; e = e.next) {
                                int h = e.hash;
                                TreeNode<K,V> p = new TreeNode<K,V>
                                    (h, e.key, e.val, null, null);
                                    // 和链表相同的判断，与运算 == 0 的放在低位
                                if ((h & n) == 0) {
                                    if ((p.prev = loTail) == null)
                                        lo = p;
                                    else
                                        loTail.next = p;
                                    loTail = p;
                                    ++lc;
                                }
                                else {
                                    if ((p.prev = hiTail) == null)
                                        hi = p;
                                    else
                                        hiTail.next = p;
                                    hiTail = p;
                                    ++hc;
                                }
                            }
                            // 如果树的节点数小于等于 6，那么转成链表，反之，创建一个新的树
                            ln = (lc <= UNTREEIFY_THRESHOLD) ? untreeify(lo) :
                                (hc != 0) ? new TreeBin<K,V>(lo) : t;
                            hn = (hc <= UNTREEIFY_THRESHOLD) ? untreeify(hi) :
                                (lc != 0) ? new TreeBin<K,V>(hi) : t;
                            // 低位树
                            setTabAt(nextTab, i, ln);
                            // 高位数
                            setTabAt(nextTab, i + n, hn);
                            // 旧的设置成占位符
                            setTabAt(tab, i, fwd);
                            // 继续向后推进
                            advance = true;
                        }
                    }
                }
            }
        }
    }
    
    
    private final void addCount(long x, int check) {
    // 把数组的大小存储根据不同的线程存储到不同的段上
        CounterCell[] as; long b, s;
        // 先尝试把数量加到baseCount上，如果失败再加到分段的CounterCell上
        if ((as = counterCells) != null ||
            !U.compareAndSwapLong(this, BASECOUNT, b = baseCount, s = b + x)) {
            CounterCell a; long v; int m;
            boolean uncontended = true;
            // 如果as为空 ,或者长度为0,或者当前线程所在的段为null,或者在当前线程的段上加数量失败 说明出现并发了
            if (as == null || (m = as.length - 1) < 0 ||
                (a = as[ThreadLocalRandom.getProbe() & m]) == null ||
                !(uncontended =
                  U.compareAndSwapLong(a, CELLVALUE, v = a.value, v + x))) {
                  // 强制增加数量
                fullAddCount(x, uncontended);
                return;
            }
            if (check <= 1)
                return;
                // 计算元素个数
            s = sumCount();
        }
        if (check >= 0) {
            Node<K,V>[] tab, nt; int n, sc;
             // 如果map.size() 大于 sizeCtl（达到扩容阈值需要扩容） 且
                    // table 不是空；且 table 的长度小于 1 << 30。（可以扩容）
            while (s >= (long)(sc = sizeCtl) && (tab = table) != null &&
                   (n = tab.length) < MAXIMUM_CAPACITY) {
                   // 根据 length 得到一个标识
                int rs = resizeStamp(n);
                if (sc < 0) {                // 如果正在扩容
                // 如果 sc 的低 16 位不等于 标识符（校验异常 sizeCtl 变化了）
                // 如果 sc == 标识符 + 1 （扩容结束了，不再有线程进行扩容）
                （默认第一个线程设置 sc ==rs 左移 16 位 + 2，当第一个线程结束扩容了，就会将 sc 减一。这个时候，sc 就等于 rs + 1）
                // 如果 sc == 标识符 + 65535（帮助线程数已经达到最大）
                // 如果 nextTable == null（结束扩容了）
                // 如果 transferIndex <= 0 (转移状态变化了)
                // 结束循环 
                    if ((sc >>> RESIZE_STAMP_SHIFT) != rs || sc == rs + 1 ||
                        sc == rs + MAX_RESIZERS || (nt = nextTable) == null ||
                        transferIndex <= 0)
                        break;
                    // 如果正在帮助扩容 那么将 sc 加 1. 表示多了一个线程在帮助扩容
                    if (U.compareAndSwapInt(this, SIZECTL, sc, sc + 1))
                        // 扩容
                        transfer(tab, nt);
                }
                // 如果不再扩容，将 sc 更新：标识符左移 16 位 然后 + 2. 也就是变成一个负数。高 16 位是标识符，低 16 位初始是 2.
                else if (U.compareAndSwapInt(this, SIZECTL, sc,
                                             (rs << RESIZE_STAMP_SHIFT) + 2))
                    // 更新 sizeCtl 为负数后，开始扩容。
                    transfer(tab, null);
                s = sumCount();
            }
        }
    }

### get操作

### size操作

### 扩容

### 红黑树转换