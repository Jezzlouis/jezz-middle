## ArrayList

### 属性

    // 默认容量
    private static final int DEFAULT_CAPACITY = 10;
    // 空数组，如果传入的容量为0时使用
    private static final Object[] EMPTY_ELEMENTDATA = {};
    // 空数组，传传入容量时使用，添加第一个元素的时候会重新初始为默认容量大小
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
    // 存储元素的数组
    transient Object[] elementData; 
    // 集合中元素的个数
    private int size;
    
### 构造方法

#### ArrayList()构造方法

不传初始容量，初始化为DEFAULTCAPACITY_EMPTY_ELEMENTDATA空数组，会在添加第一个元素的时候扩容为默认的大小，即10

    public ArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

#### ArrayList(int initialCapacity)构造方法

传入初始容量，如果大于0就初始化elementData为对应大小，如果等于0就使用EMPTY_ELEMENTDATA空数组，如果小于0抛出异常

    public ArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+
                                               initialCapacity);
        }
    }
    
#### ArrayList(Collection c) 构造方法
    
传入集合并初始化elementData，这里会使用拷贝把传入集合的元素拷贝到elementData数组中，如果元素个数为0，则初始化为EMPTY_ELEMENTDATA空数组
    
    public ArrayList(Collection<? extends E> c) {
        elementData = c.toArray();
        if ((size = elementData.length) != 0) {
            // c.toArray might (incorrectly) not return Object[] (see 6260652)
            if (elementData.getClass() != Object[].class)
                elementData = Arrays.copyOf(elementData, size, Object[].class);
        } else {
            // replace with empty array.
            this.elementData = EMPTY_ELEMENTDATA;
        }
    }

### 添加

#### add(E e)方法
    
    public boolean add(E e) {
        // 检查是否需要扩容
        ensureCapacityInternal(size + 1);
        // 把元素插入到最后一位
        elementData[size++] = e;
        return true;
    }
    
    private void ensureCapacityInternal(int minCapacity) {
        ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
    }
    
    private static int calculateCapacity(Object[] elementData, int minCapacity) {
        // 如果是空数组DEFAULTCAPACITY_EMPTY_ELEMENTDATA，就初始化为默认大小10
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            return Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        return minCapacity;
    }
    
    private void ensureExplicitCapacity(int minCapacity) {
        modCount++;
    
        if (minCapacity - elementData.length > 0)
            // 扩容
            grow(minCapacity);
    }
    
    private void grow(int minCapacity) {
        int oldCapacity = elementData.length;
        // 新容量为旧容量的1.5倍
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        // 如果新容量发现比需要的容量还小，则以需要的容量为准
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        // 如果新容量已经超过最大容量了，则使用最大容量
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // 以新容量拷贝出来一个新数组
        elementData = Arrays.copyOf(elementData, newCapacity);
    }
    
#### add(int index, E element)方法

     public void add(int index, E element) {
        // 检查是否越界
        rangeCheckForAdd(index);
        // 检查是否需要扩容
        ensureCapacityInternal(size + 1);
        // 将inex及其之后的元素往后挪一位，则index位置处就空出来了
        System.arraycopy(elementData, index, elementData, index + 1,
                         size - index);
        // 将元素插入到index的位置
        elementData[index] = element;
        // 大小增1
        size++;
    }
    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

#### addAll 方法 将集合c中所有元素添加到当前ArrayList中

    public boolean addAll(Collection<? extends E> c) {
        // 将集合c转为数组
        Object[] a = c.toArray();
        int numNew = a.length;
        // 检查是否需要扩容
        ensureCapacityInternal(size + numNew);
        // 将c中元素全部拷贝到数组的最后
        System.arraycopy(a, 0, elementData, size, numNew);
        // 大小增加c的大小
        size += numNew;
        // 如果c不为空就返回true，否则返回false
        return numNew != 0;
    }
    
### 查找

#### get(int index)方法

    public E get(int index) {
        // 检查是否越界
        rangeCheck(index);
        // 返回数组index位置的元素
        return elementData(index);
    }
    
    private void rangeCheck(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }
    
    E elementData(int index) {
        return (E) elementData[index];
    }

#### remove(int index)方法 ArrayList删除元素的时候并没有缩容

    public E remove(int index) {
        // 检查是否越界
        rangeCheck(index);
    
        modCount++;
        // 获取index位置的元素
        E oldValue = elementData(index);
    
        // 如果index不是最后一位，则将index之后的元素往前挪一位
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index, numMoved);
    
        // 将最后一个元素删除，帮助GC
        elementData[--size] = null; // clear to let GC do its work
    
        // 返回旧值
        return oldValue;
    }

#### remove(Object o)方法

    public boolean remove(Object o) {
        if (o == null) {
            // 遍历整个数组，找到元素第一次出现的位置，并将其快速删除
            for (int index = 0; index < size; index++)
                // 如果要删除的元素为null，则以null进行比较，使用==
                if (elementData[index] == null) {
                    fastRemove(index);
                    return true;
                }
        } else {
            // 遍历整个数组，找到元素第一次出现的位置，并将其快速删除
            for (int index = 0; index < size; index++)
                // 如果要删除的元素不为null，则进行比较，使用equals()方法
                if (o.equals(elementData[index])) {
                    fastRemove(index);
                    return true;
                }
        }
        return false;
    }
    
    private void fastRemove(int index) {
        // 少了一个越界的检查
        modCount++;
        // 如果index不是最后一位，则将index之后的元素往前挪一位
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index, numMoved);
        // 将最后一个元素删除，帮助GC
        elementData[--size] = null; // clear to let GC do its work
    }
    
### 求交集

#### retainAll方法

（1）遍历elementData数组；

（2）如果元素在c中，则把这个元素添加到elementData数组的w位置并将w位置往后移一位；

（3）遍历完之后，w之前的元素都是两者共有的，w之后（包含）的元素不是两者共有的；

（4）将w之后（包含）的元素置为null，方便GC回收；
    
    public boolean retainAll(Collection<?> c) {
        // 集合c不能为null
        Objects.requireNonNull(c);
        // 调用批量删除方法，这时complement传入true，表示删除不包含在c中的元素
        return batchRemove(c, true);
    }
    
    /**
    * 批量删除元素
    * complement为true表示删除c中不包含的元素
    * complement为false表示删除c中包含的元素
    */
    private boolean batchRemove(Collection<?> c, boolean complement) {
        final Object[] elementData = this.elementData;
        // 使用读写两个指针同时遍历数组
        // 读指针每次自增1，写指针放入元素的时候才加1
        // 这样不需要额外的空间，只需要在原有的数组上操作就可以了
        int r = 0, w = 0;
        boolean modified = false;
        try {
            // 遍历整个数组，如果c中包含该元素，则把该元素放到写指针的位置（以complement为准）
            for (; r < size; r++)
                if (c.contains(elementData[r]) == complement)
                    elementData[w++] = elementData[r];
        } finally {
            // 正常来说r最后是等于size的，除非c.contains()抛出了异常
            if (r != size) {
                // 如果c.contains()抛出了异常，则把未读的元素都拷贝到写指针之后
                System.arraycopy(elementData, r,
                                 elementData, w,
                                 size - r);
                w += size - r;
            }
            if (w != size) {
                // 将写指针之后的元素置为空，帮助GC
                for (int i = w; i < size; i++)
                    elementData[i] = null;
                modCount += size - w;
                // 新大小等于写指针的位置（因为每写一次写指针就加1，所以新大小正好等于写指针的位置）
                size = w;
                modified = true;
            }
        }
        // 有修改返回true
        return modified;
    }

### 求差集

#### removeAll
    
    public boolean removeAll(Collection<?> c) {
        // 集合c不能为空
        Objects.requireNonNull(c);
        // 同样调用批量删除方法，这时complement传入false，表示删除包含在c中的元素
        return batchRemove(c, false);
    }

## LinkedList 是一个以双向链表实现的List，它除了作为List使用，还可以作为队列或者栈来使用

### 属性

    // 元素个数
    transient int size = 0;
    // 链表首节点
    transient Node<E> first;
    // 链表尾节点
    transient Node<E> last;

### 内部类 双向列表结构
    
    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;
    
        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

### 构造器

    public LinkedList() {
    }
    
    public LinkedList(Collection<? extends E> c) {
        this();
        addAll(c);
    }

### 添加元素

1.队列方式

    // 从队列首添加元素
    private void linkFirst(E e) {
        // 首节点
        final Node<E> f = first;
        // 创建新节点，新节点的next是首节点
        final Node<E> newNode = new Node<>(null, e, f);
        // 让新节点作为新的首节点
        first = newNode;
        // 判断是不是第一个添加的元素
        // 如果是就把last也置为新节点
        // 否则把原首节点的prev指针置为新节点
        if (f == null)
            last = newNode;
        else
            f.prev = newNode;
        // 元素个数加1
        size++;
        // 修改次数加1，说明这是一个支持fail-fast的集合
        modCount++;
    }
    
    // 从队列尾添加元素
    void linkLast(E e) {
        // 队列尾节点
        final Node<E> l = last;
        // 创建新节点，新节点的prev是尾节点
        final Node<E> newNode = new Node<>(l, e, null);
        // 让新节点成为新的尾节点
        last = newNode;
        // 判断是不是第一个添加的元素
        // 如果是就把first也置为新节点
        // 否则把原尾节点的next指针置为新节点
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        // 元素个数加1
        size++;
        // 修改次数加1
        modCount++;
    }
    
    public void addFirst(E e) {
        linkFirst(e);
    }
    
    public void addLast(E e) {
        linkLast(e);
    }
    
    // 作为无界队列，添加元素总是会成功的
    public boolean offerFirst(E e) {
        addFirst(e);
        return true;
    }
    
    public boolean offerLast(E e) {
        addLast(e);
        return true;
    }

2.list方式

    / 在节点succ之前添加元素
    void linkBefore(E e, Node<E> succ) {
        // succ是待添加节点的后继节点
        // 找到待添加节点的前置节点
        final Node<E> pred = succ.prev;
        // 在其前置节点和后继节点之间创建一个新节点
        final Node<E> newNode = new Node<>(pred, e, succ);
        // 修改后继节点的前置指针指向新节点
        succ.prev = newNode;
        // 判断前置节点是否为空
        // 如果为空，说明是第一个添加的元素，修改first指针
        // 否则修改前置节点的next为新节点
        if (pred == null)
            first = newNode;
        else
            pred.next = newNode;
        // 修改元素个数
        size++;
        // 修改次数加1
        modCount++;
    }
    
    // 寻找index位置的节点
    Node<E> node(int index) {
        // 因为是双链表
        // 所以根据index是在前半段还是后半段决定从前遍历还是从后遍历
        // 这样index在后半段的时候可以少遍历一半的元素
        if (index < (size >> 1)) {
            // 如果是在前半段
            // 就从前遍历
            Node<E> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            // 如果是在后半段
            // 就从后遍历
            Node<E> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }
    
    // 在指定index位置处添加元素
    public void add(int index, E element) {
        // 判断是否越界
        checkPositionIndex(index);
        // 如果index是在队列尾节点之后的一个位置
        // 把新节点直接添加到尾节点之后
        // 否则调用linkBefore()方法在中间添加节点
        if (index == size)
            linkLast(element);
        else
            linkBefore(element, node(index));
    }


### Vector

### CopyOnWrite