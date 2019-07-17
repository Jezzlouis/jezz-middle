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
                    if (tabAt(tab, i) == f) {
                        if (fh >= 0) {
                            binCount = 1;
                            for (Node<K,V> e = f;; ++binCount) {
                                K ek;
                                if (e.hash == hash &&
                                    ((ek = e.key) == key ||
                                     (ek != null && key.equals(ek)))) {
                                    oldVal = e.val;
                                    if (!onlyIfAbsent)
                                        e.val = value;
                                    break;
                                }
                                Node<K,V> pred = e;
                                if ((e = e.next) == null) {
                                    pred.next = new Node<K,V>(hash, key,
                                                              value, null);
                                    break;
                                }
                            }
                        }
                        else if (f instanceof TreeBin) {
                            Node<K,V> p;
                            binCount = 2;
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
         if (tab != null && (f instanceof ForwardingNode) &&
             (nextTab = ((ForwardingNode<K,V>)f).nextTable) != null) {
             int rs = resizeStamp(tab.length);
             while (nextTab == nextTable && table == tab &&
                    (sc = sizeCtl) < 0) {
                 if ((sc >>> RESIZE_STAMP_SHIFT) != rs || sc == rs + 1 ||
                     sc == rs + MAX_RESIZERS || transferIndex <= 0)
                     break;
                 if (U.compareAndSwapInt(this, SIZECTL, sc, sc + 1)) {
                     transfer(tab, nextTab);
                     break;
                 }
             }
             return nextTab;
         }
         return table;
     }

### get操作

### size操作

### 扩容

### 红黑树转换