### 关于List相关的问题主要有：
    
ArrayList和LinkedList有什么区别？

     arrayList基于动态数组，LinkedList基于链表结构
     arrayList访问元素快，因为LinkedList要移动指针，linedList插入和删除元素快，因为ArrayList要移动数据
    
ArrayList是怎么扩容的？
    
    一 初始化
    首先有三种方式来初始化：默认的构造器，指定大小的构造器，传collection集合的构造器
                      
    二 确保内部容量 以无参构造器为例在需要的时候再分配容量
     <1> 以add(E e)为例
     1. 首先检查需不需要扩容，如果是空数组，就默认初始化为10，否则的话返回需要的容量，
     如果需要的容量减去数组的容量(当前空数组的时候数组个数初始化为10)如果大于0扩容
     2. 新容量是老容量的1.5倍（oldCapacity + (oldCapacity >> 1)）如果加了这么多容量发现比需要的容量还小，则以需要的容量为准
     如果新容量已经超过最大容量了，则使用最大容量
     3.以新容量拷贝出来一个新数组
     <2> 以add(int index, E element)为例
     1.检查索引是否越界
     2.检查是否需要扩容
     3.把插入索引位置后的元素都往后挪一位
     4.在插入索引位置放置插入的元素
     5.大小加1
     <3> addAll 方法
     1.拷贝c中的元素到数组a中
     2.检查是否需要扩容
     3.把数组a中的元素拷贝到elementData的尾部
     4.大小增加c的大小

扩容引申问题:

ArrayList 集合加入 1 万条数据，应该怎么提高效率
        
     ArrayList 的默认初始容量为 10 ，要插入大量数据的时候需要不断扩容，而扩容是非常影响性能的。
     因此，现在明确了 10 万条数据了，我们可以直接在初始化的时候就设置 ArrayList 的容量！
 
Arrays类的copyOf与System类的arrayscopy()的区别
    
    1.源码看copyOf()的实现是实际上依赖于是arrayCopy()
    2.arrayCopy()中参数需要目标数组，对两个数组的内容进行可能不完全的合并操作
    3.copyOf()在内部新建一个数组，调用arrayCopy()将original内容复制到副本中去，并且长度为newLength。返回副本    
    
ArrayList插入、删除、查询元素的时间复杂度各是多少？
    
    ArrayList支持随机访问，通过索引访问元素极快，时间复杂度为O(1)
    ArrayList添加元素到尾部极快，平均时间复杂度为O(1)
    ArrayList从尾部删除元素极快，时间复杂度为O(1)
    ArrayList添加元素到中间比较慢，因为要搬移元素，平均时间复杂度为O(n)
    ArrayList从中间删除元素比较慢，因为要搬移元素，平均时间复杂度为O(n)
    
怎么求两个集合的并集、交集、差集？
    
    并集 addAll 交集 retainAll 单向差集 removeAll
    
求差集大数据量的情况下使用linkedList 或 hashSet
    
ArrayList是怎么实现序列化和反序列化的？

    查看writeObject()方法可知，先调用s.defaultWriteObject()方法，再把size写入到流中，再把元素一个一个的写入到流中
    readObject()方法差不多
    
集合的方法toArray()有什么问题？
    
    返回的有可能不是Object[]类型
    
什么是fail-fast？
    
LinkedList是单链表还是双链表实现的？
    
LinkedList除了作为List还有什么用处？
    
LinkedList插入、删除、查询元素的时间复杂度各是多少？
    
什么是随机访问？
    
哪些集合支持随机访问？他们都有哪些共性？
    
CopyOnWriteArrayList是怎么保证并发安全的？
    
CopyOnWriteArrayList的实现采用了什么思想？
    
CopyOnWriteArrayList是不是强一致性的？
    
CopyOnWriteArrayList适用于什么样的场景？
    
CopyOnWriteArrayList插入、删除、查询元素的时间复杂度各是多少？
    
CopyOnWriteArrayList为什么没有size属性？
    
比较古老的集合Vector和Stack有什么缺陷？
