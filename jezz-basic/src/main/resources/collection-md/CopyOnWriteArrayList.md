## 

### 属性

    /** 用于修改时加锁 */
    final transient ReentrantLock lock = new ReentrantLock();
    
    /** 真正存储元素的地方，只能通过getArray()/setArray()访问 */
    private transient volatile Object[] array;

### 构造方法

#### 无参构造方法,直接创建空数组
    
    public CopyOnWriteArrayList() {
        setArray(new Object[0]);
    }
    
    final void setArray(Object[] a) {
        array = a;
    }
 
#### CopyOnWriteArrayList(Collection c)构造    
    
    public CopyOnWriteArrayList(Collection<? extends E> c) {
        Object[] elements;
        if (c.getClass() == CopyOnWriteArrayList.class)
            elements = ((CopyOnWriteArrayList<?>)c).getArray();
        else {
            elements = c.toArray();
            // c.toArray might (incorrectly) not return Object[] (see 6260652)
            if (elements.getClass() != Object[].class)
                elements = Arrays.copyOf(elements, elements.length, Object[].class);
        }
        setArray(elements);
    }
    
如果c是CopyOnWriteArrayList类型，直接把它的数组赋值给当前list的数组，注意这里是浅拷贝，两个集合共用同一个数组。
如果c不是CopyOnWriteArrayList类型，则进行拷贝把c的元素全部拷贝到当前list的数组中。

#### CopyOnWriteArrayList(E[] toCopyIn)构造
    
    public CopyOnWriteArrayList(E[] toCopyIn) {
        setArray(Arrays.copyOf(toCopyIn, toCopyIn.length, Object[].class));  
    }
    
把toCopyIn的元素拷贝给当前list的数组。
