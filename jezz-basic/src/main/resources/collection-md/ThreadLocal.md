## ThreadLoacal (线程的局部变量)

ThreadLocal与线程同步机制不同，线程同步机制是多个线程共享同一个变量，而ThreadLocal是为每一个线程创建一个单独的变量副本
故而每个线程都可以独立地改变自己所拥有的变量副本，而不会影响其他线程所对应的副本

### 方法定义:

get()：返回此线程局部变量的当前线程副本中的值。
initialValue()：返回此线程局部变量的当前线程的“初始值”。
remove()：移除此线程局部变量当前线程的值。
set(T value)：将此线程局部变量的当前线程副本中的值设置为指定值。

### ThreadLocalMap 继承自 弱引用WeakReference

ThreadLocalMap其内部利用Entry来实现key-value的存储

### ThreadLocal为什么会内存泄漏

每个Thread都有一个ThreadLocal.ThreadLocalMap的map，该map的key为ThreadLocal实例，它为一个弱引用，我们知道弱引用有利于GC回收。
当ThreadLocal的key == null时，GC就会回收这部分空间，但是value却不一定能够被回收，因为他还与Current Thread存在一个强引用关系
由于存在这个强引用关系，会导致value无法回收。如果这个线程对象不会销毁那么这个强引用关系则会一直存在，就会出现内存泄漏情况。

解决办法 : 在ThreadLocalMap中的setEntry()、getEntry()，如果遇到key == null的情况，会对value设置为null。
当然我们也可以显示调用ThreadLocal的remove()方法进行处理

### 总结

ThreadLocal 不是用于解决共享变量的问题的，也不是为了协调线程同步而存在，而是为了方便每个线程处理自己的状态而引入的一个机制。这点至关重要。
每个Thread内部都有一个ThreadLocal.ThreadLocalMap类型的成员变量，该成员变量用来存储实际的ThreadLocal变量副本。
    
    public void set(T value) {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null)
            map.set(this, value);
        else
            createMap(t, value);
    }
    
ThreadLocal并不是为线程保存对象的副本，它仅仅只起到一个索引的作用。它的主要目的视为每一个线程隔离一个类的实例，这个实例的作用范围仅限于线程内部。

https://www.cnblogs.com/dolphin0520/p/3920407.html