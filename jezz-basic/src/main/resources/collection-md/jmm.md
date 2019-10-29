## java 内存模型

堆内存在线程中共享(所有实例域,静态域,数组元素)

jmm抽象的定义了线程和主内存之间的抽象关系:线程中的共享变量存在主内存中，每个线程都有私有的本地内存

### happens-before原则 《深入理解Java虚拟机第12章》

    程序顺序规则 : 一个线程内，按照代码顺序，书写在前面的操作先行发生于书写在后面的操作；
    监视器锁规则 : 对一个锁的解锁,先行发生于对这个锁的加锁
    volatile变量规则 : 对一个volatile域的写,先行发生于任意后续对这个volatile域的读
    传递性 : 如果A happens-before B,B happens-before C,那么 A happens-before C
    线程启动规则 : Thread对象的start()方法先行发生于此线程的每个一个动作
    线程中断规则 : 对线程interrupt()方法的调用先行发生于被中断线程的代码检测到中断事件的发生
    线程终结规则 : 线程中所有的操作都先行发生于线程的终止检测，我们可以通过Thread.join()方法结束、Thread.isAlive()的返回值手段检测到线程已经终止执行
    对象终结规则 : 一个对象的初始化完成先行发生于他的finalize()方法的开始

### 重排序原则 (重排序不会影响单线程环境的执行结果，但是会破坏多线程的执行语义) 《java并发编程的艺术3.2章》

as-if-serial语义

    在单线程环境下,不管怎么重排序都不能改变程序运行的结果
    存在数据依赖关系的不允许重排序 

### volatile 《java并发编程的艺术3.4章》

volatile是用来判断是否存数据竞争、线程是否安全的主要依据，它保证了多线程环境下的可见性

happens-before : 

    可见性 : 对一个volatile的读，总可以看到对这个变量最终的写

    原子性 : volatile对单个读/写具有原子性 (复合操作i++除外)

重排序 : volatile 禁止重排序

    

    当第一个操作为volatile读，则不管第二个操作是啥，都不能重排序。这个操作确保volatile读之后的操作不会被编译器重排序到volatile读之前；
    
    当第二个操作为volatile写是，则不管第一个操作是啥，都不能重排序。这个操作确保volatile写之前的操作不会被编译器重排序到volatile写之后；
    
    当第一个操作volatile写，第二操作为volatile读时，不能重排序。


内存语义 : 当写一个volatile变量时，jmm会把该线程对应的本地内存中的共享变量的值刷新到主内存中，
          当读一个volatile变量时，jmm会将该线程本地内存中的变量置为无效，直接读主内存中的数据

实现 : volatile的底层实现是通过插入内存屏障，但是对于编译器来说，发现一个最优布置来最小化插入内存屏障的总数几乎是不可能的，所以，JMM采用了保守策略

    在每一个volatile写操作前面插入一个StoreStore屏障
    
    在每一个volatile写操作后面插入一个StoreLoad屏障

    在每一个volatile读操作后面插入一个LoadLoad屏障

    在每一个volatile读操作后面插入一个LoadStore屏障
    
### DCL 双重检查(单例模式常用)

懒汉式 : 无法保证线程安全

    public class Singleton {
       private static Singleton singleton;
    
       private Singleton(){}
    
       public static Singleton getInstance(){
           if(singleton == null){
               singleton = new Singleton();
           }
    
           return singleton;
       }
    }

基于volatile解决方案 :
   
    public class Singleton {
       //通过volatile关键字来确保安全
       private volatile static Singleton singleton;
    
       private Singleton(){}
    
       public static Singleton getInstance(){
           if(singleton == null){
               synchronized (Singleton.class){
                   if(singleton == null){
                       singleton = new Singleton();
                   }
               }
           }
           return singleton;
       }
    }

基于类初始化的解决方案 : 利用classloder的机制来保证初始化instance时只有一个线程。JVM在类初始化阶段会获取一个锁，这个锁可以同步多个线程对同一个类的初始化。

    public class Singleton {
       private static class SingletonHolder{
           public static Singleton singleton = new Singleton();
       }
    
       public static Singleton getInstance(){
           return SingletonHolder.singleton;
       }
    }

Java语言规定，对于每一个类或者接口C,都有一个唯一的初始化锁LC与之相对应。从C到LC的映射，由JVM的具体实现去自由实现。
JVM在类初始化阶段期间会获取这个初始化锁，并且每一个线程至少获取一次锁来确保这个类已经被初始化过了。

