## Map

### HashMap

#### 属性
    
    /**
     * 默认的初始容量为16
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
    
    /**
     * 最大的容量为2的30次方
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;
    
    /**
     * 默认的装载因子
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    
    /**
     * 当一个桶中的元素个数大于等于8时进行树化
     */
    static final int TREEIFY_THRESHOLD = 8;
    
    /**
     * 当一个桶中的元素个数小于等于6时把树转化为链表
     */
    static final int UNTREEIFY_THRESHOLD = 6;
    
    /**
     * 当桶的个数达到64的时候才进行树化
     */
    static final int MIN_TREEIFY_CAPACITY = 64;
    
    /**
     * 数组，又叫作桶（bucket）
     */
    transient Node<K,V>[] table;
    
    /**
     * 作为entrySet()的缓存
     */
    transient Set<Map.Entry<K,V>> entrySet;
    
    /**
     * 元素的数量
     */
    transient int size;
    
    /**
     * 修改次数，用于在迭代的时候执行快速失败策略
     */
    transient int modCount;
    
    /**
     * 当桶的使用数量达到多少时进行扩容，threshold = capacity * loadFactor
     */
    int threshold;
    
    /**
     * 装载因子
     */
    final float loadFactor;

#### Node 内部类

    static class Node<K,V> implements Map.Entry<K,V> {
        final int hash;
        final K key;
        V value;
        Node<K,V> next;
    }
    
#### TreeNode内部类

    // 位于HashMap中
    static final class TreeNode<K,V> extends LinkedHashMap.Entry<K,V> {
        TreeNode<K,V> parent;  // red-black tree links
        TreeNode<K,V> left;
        TreeNode<K,V> right;
        TreeNode<K,V> prev;    // needed to unlink next upon deletion
        boolean red;
    }
    
    // 位于LinkedHashMap中，典型的双向链表节点
    static class Entry<K,V> extends HashMap.Node<K,V> {
        Entry<K,V> before, after;
        Entry(int hash, K key, V value, Node<K,V> next) {
            super(hash, key, value, next);
        }
    }
    
#### 构造方法

##### HashMap()构造方法
    
全部使用默认的值
    
    public HashMap() {
        this.loadFactor = DEFAULT_LOAD_FACTOR; // all other fields defaulted
    }

##### HashMap(int initialCapacity)构造方法

调用HashMap(int initialCapacity, float loadFactor)构造方法，传入默认装载因子。

    public HashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    public HashMap(int initialCapacity, float loadFactor) {
        // 检查传入的初始容量是否合法
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " +
                                               initialCapacity);
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        // 检查装载因子是否合法
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " +
                                               loadFactor);
        this.loadFactor = loadFactor;
        // 计算扩容门槛
        this.threshold = tableSizeFor(initialCapacity);
    }
    
    static final int tableSizeFor(int cap) {
        // 扩容门槛为传入的初始容量往上取最近的2的n次方
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

##### 添加 put方法
    
    public V put(K key, V value) {
        // 调用hash(key)计算出key的hash值
        return putVal(hash(key), key, value, false, true);
    }
    
    static final int hash(Object key) {
        int h;
        // 如果key为null，则hash值为0，否则调用key的hashCode()方法
        // 并让高16位与整个hash异或，这样做是为了使计算出的hash更分散
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
    
    final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K, V>[] tab;
        Node<K, V> p;
        int n, i;
        // 如果桶的数量为0，则初始化
        if ((tab = table) == null || (n = tab.length) == 0)
            // 调用resize()初始化
            n = (tab = resize()).length;
        // (n - 1) & hash 计算元素在哪个桶中
        // 如果这个桶中还没有元素，则把这个元素放在桶中的第一个位置
        if ((p = tab[i = (n - 1) & hash]) == null)
            // 新建一个节点放在桶中
            tab[i] = newNode(hash, key, value, null);
        else {
            // 如果桶中已经有元素存在了
            Node<K, V> e;
            K k;
            // 如果桶中第一个元素的key与待插入元素的key相同，保存到e中用于后续修改value值
            if (p.hash == hash &&
                    ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;
            else if (p instanceof TreeNode)
                // 如果第一个元素是树节点，则调用树节点的putTreeVal插入元素
                e = ((TreeNode<K, V>) p).putTreeVal(this, tab, hash, key, value);
            else {
                // 遍历这个桶对应的链表，binCount用于存储链表中元素的个数
                for (int binCount = 0; ; ++binCount) {
                    // 如果链表遍历完了都没有找到相同key的元素，说明该key对应的元素不存在，则在链表最后插入一个新节点
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                        // 如果插入新节点后链表长度大于8，则判断是否需要树化，因为第一个元素没有加到binCount中，所以这里-1
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            treeifyBin(tab, hash);
                        break;
                    }
                    // 如果待插入的key在链表中找到了，则退出循环
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            // 如果找到了对应key的元素
            if (e != null) { // existing mapping for key
                // 记录下旧值
                V oldValue = e.value;
                // 判断是否需要替换旧值
                if (!onlyIfAbsent || oldValue == null)
                    // 替换旧值为新值
                    e.value = value;
                // 在节点被访问后做点什么事，在LinkedHashMap中用到
                afterNodeAccess(e);
                // 返回旧值
                return oldValue;
            }
        }
        // 到这里了说明没有找到元素
        // 修改次数加1
        ++modCount;
        // 元素数量加1，判断是否需要扩容
        if (++size > threshold)
            // 扩容
            resize();
        // 在节点插入后做点什么事，在LinkedHashMap中用到
        afterNodeInsertion(evict);
        // 没找到元素返回null
        return null;
    }
    
（1）计算key的hash值；

（2）如果桶（数组）数量为0，则初始化桶；

（3）如果key所在的桶没有元素，则直接插入；

（4）如果key所在的桶中的第一个元素的key与待插入的key相同，说明找到了元素，转后续流程（9）处理；

（5）如果第一个元素是树节点，则调用树节点的putTreeVal()寻找元素或插入树节点；

（6）如果不是以上三种情况，则遍历桶对应的链表查找key是否存在于链表中；

（7）如果找到了对应key的元素，则转后续流程（9）处理；

（8）如果没找到对应key的元素，则在链表最后插入一个新节点并判断是否需要树化；

（9）如果找到了对应key的元素，则判断是否需要替换旧值，并直接返回旧值；

（10）如果插入了元素，则数量加1并判断是否需要扩容；

##### 扩容 resize()方法

    final Node<K, V>[] resize() {
        // 旧数组
        Node<K, V>[] oldTab = table;
        // 旧容量
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        // 旧扩容门槛
        int oldThr = threshold;
        int newCap, newThr = 0;
        if (oldCap > 0) {
            if (oldCap >= MAXIMUM_CAPACITY) {
                // 如果旧容量达到了最大容量，则不再进行扩容
                threshold = Integer.MAX_VALUE;
                return oldTab;
            } else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                    oldCap >= DEFAULT_INITIAL_CAPACITY)
                // 如果旧容量的两倍小于最大容量并且旧容量大于默认初始容量（16），则容量扩大为两部，扩容门槛也扩大为两倍
                newThr = oldThr << 1; // double threshold
        } else if (oldThr > 0) // initial capacity was placed in threshold
            // 使用非默认构造方法创建的map，第一次插入元素会走到这里
            // 如果旧容量为0且旧扩容门槛大于0，则把新容量赋值为旧门槛
            newCap = oldThr;
        else {               // zero initial threshold signifies using defaults
            // 调用默认构造方法创建的map，第一次插入元素会走到这里
            // 如果旧容量旧扩容门槛都是0，说明还未初始化过，则初始化容量为默认容量，扩容门槛为默认容量*默认装载因子
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int) (DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }
        if (newThr == 0) {
            // 如果新扩容门槛为0，则计算为容量*装载因子，但不能超过最大容量
            float ft = (float) newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float) MAXIMUM_CAPACITY ?
                    (int) ft : Integer.MAX_VALUE);
        }
        // 赋值扩容门槛为新门槛
        threshold = newThr;
        // 新建一个新容量的数组
        @SuppressWarnings({"rawtypes", "unchecked"})
        Node<K, V>[] newTab = (Node<K, V>[]) new Node[newCap];
        // 把桶赋值为新数组
        table = newTab;
        // 如果旧数组不为空，则搬移元素
        if (oldTab != null) {
            // 遍历旧数组
            for (int j = 0; j < oldCap; ++j) {
                Node<K, V> e;
                // 如果桶中第一个元素不为空，赋值给e
                if ((e = oldTab[j]) != null) {
                    // 清空旧桶，便于GC回收  
                    oldTab[j] = null;
                    // 如果这个桶中只有一个元素，则计算它在新桶中的位置并把它搬移到新桶中
                    // 因为每次都扩容两倍，所以这里的第一个元素搬移到新桶的时候新桶肯定还没有元素
                    if (e.next == null)
                        newTab[e.hash & (newCap - 1)] = e;
                    else if (e instanceof TreeNode)
                        // 如果第一个元素是树节点，则把这颗树打散成两颗树插入到新桶中去
                        ((TreeNode<K, V>) e).split(this, newTab, j, oldCap);
                    else { // preserve order
                        // 如果这个链表不止一个元素且不是一颗树
                        // 则分化成两个链表插入到新的桶中去
                        // 比如，假如原来容量为4，3、7、11、15这四个元素都在三号桶中
                        // 现在扩容到8，则3和11还是在三号桶，7和15要搬移到七号桶中去
                        // 也就是分化成了两个链表
                        Node<K, V> loHead = null, loTail = null;
                        Node<K, V> hiHead = null, hiTail = null;
                        Node<K, V> next;
                        do {
                            next = e.next;
                            // (e.hash & oldCap) == 0的元素放在低位链表中
                            // 比如，3 & 4 == 0
                            if ((e.hash & oldCap) == 0) {
                                if (loTail == null)
                                    loHead = e;
                                else
                                    loTail.next = e;
                                loTail = e;
                            } else {
                                // (e.hash & oldCap) != 0的元素放在高位链表中
                                // 比如，7 & 4 != 0
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        // 遍历完成分化成两个链表了
                        // 低位链表在新桶中的位置与旧桶一样（即3和11还在三号桶中）
                        if (loTail != null) {
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        // 高位链表在新桶中的位置正好是原来的位置加上旧容量（即7和15搬移到七号桶了）
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab;
    }

（1）如果使用是默认构造方法，则第一次插入元素时初始化为默认值，容量为16，扩容门槛为12；

（2）如果使用的是非默认构造方法，则第一次插入元素时初始化容量等于扩容门槛，扩容门槛在构造方法里等于传入容量向上最近的2的n次方；

（3）如果旧容量大于0，则新容量等于旧容量的2倍，但不超过最大容量2的30次方，新扩容门槛为旧扩容门槛的2倍；

（4）创建一个新容量的桶；

（5）搬移元素，原链表分化成两个链表，低位链表存储在原来桶的位置，高位链表搬移到原来桶的位置加旧容量的位置；

![Image text]( https://github.com/Jezzlouis/jezz-middle/blob/master/jezz-images/images/collection/hashmapput.png )