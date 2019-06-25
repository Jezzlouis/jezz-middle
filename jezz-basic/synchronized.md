## synchronized 实现原理 

jvm是基于进入退出Monitor对象实现代码块同步和方法同步

有以下三种使用方式:

1.同步普通方法，锁的是当前对象。

2.同步静态方法，锁的是当前 Class 对象。

3.同步块，锁的是{}中的对象。

同步代码块实现原理: synchronized 代码块同步是使用monitorenter和monitorexit指令实现的
任何一个对象都有一个monitor与之关联,线程执行到monitorenter指令时，将会尝试获取对象所对应的monitor所有权，
当一个monitor被持有后,他将处于锁定状态，即获得了锁。当线程执行到monitorexit指令时，释放锁

同步方法实现原理: synchronized方法则被翻译成了普通的方法调用和返回指令，是用一种隐式的方式来实现，在vm字节码层面没有任何的指令来实现被synchronized修饰的方法
而是在class文件的方法表中 将该方法的access_flags字段中的ACC_SYNCHRONIZED标志被设置，设置了，执行线程获取monitor，获取成功才执行方法体，方法执行完再释放monitor

    > javap -verbose SynchronizedTest 反编译可看见结果
    
## java对象头

数组就是用3个字宽,非数组2个字宽,一个字宽4个字节,一个字节四个比特,也就是说一个字宽32bit
如果是数组 3个字宽分别存储: MarkWord 存储到对象类型的指针 数组的长度

MarkWord 默认存储对象的HashCode(25bit),分代年龄(4bit),锁标记位(2bit),是否偏向锁(1bit)

![Image text]( https://github.com/Jezzlouis/jezz-middle/blob/master/jezz-images/images/concurrent/MarkWord(java并发编程的艺术).png )

![Image text]( https://github.com/Jezzlouis/jezz-middle/blob/master/jezz-images/images/concurrent/MarkWord状态变化(java并发编程的艺术).png )

### 偏向锁

大多数情况下，锁不仅不存在多线程竞争，而且总是由同一线程获得，为了让线程获取锁的代价更低而引入了偏向锁。
当一个线程访问同步块并获取锁的时候，会在对象头和栈帧的锁记录中存储锁偏向的线程ID，以后该线程进入退出同步块的时候不需要进行CAS操作加锁解锁。
只需要简单的测试一下对象头里面的MarkWord里是否存储着指向当前线程的偏向锁，如果测试成功，即获取到了偏向锁，测试失败，就需要再测试下MarkWord里的偏向锁
的标识是否设置为1(表明是偏向锁) ,如果没有设置，则使用cas竞争锁，如果设置了，尝试使用cas将对象头的偏向锁指向当前线程。

![Image text]( https://github.com/Jezzlouis/jezz-middle/blob/master/jezz-images/images/concurrent/偏向锁的获得和撤销.png )

### 偏向锁撤销

偏向锁采用了一种只有竞争才会释放锁的机制，所以当其他线程竞争偏向锁的时候，持有偏向锁的线程才会释放锁。偏向锁的撤销，需要等待全局安全点（在这个时间段没有执行的字节码）
它首先会暂停拥有偏向锁的线程，然后检查偏向锁是否活着，如果线程不属于存活状态，则将对象头设置成无锁状态，如果偏向锁还活着，拥有偏向锁线程的栈会被执行，遍历偏向锁的
锁记录，栈帧的锁记录和对象头的MarkWord要么重新偏向于其他线程，要么恢复到无锁或者标记对象不适合作为偏向锁，最后唤醒暂停的线程。

## 轻量级锁

jvm会先在当前线程的栈帧中创建存储锁记录的空间，并将对象头中的MarkWord复制到锁记录中，官方称 Displaced Mark Word。然后线程尝试使用CAS将对象头中的MarkWord
替换为指向锁记录的指针，如果成功，当前线程获取锁，如果失败，表示其他线程竞争锁，当前线程通过自旋来获取锁

### 轻量级锁解锁

轻量级锁解锁时，会使用原子的CAS操作将DisplacedMarkWord的值替换回到对象头，如果成功，表示没有竞争发生，如果失败，表示当前锁存在竞争，锁就会膨胀成重量级锁

![Image text]( https://github.com/Jezzlouis/jezz-middle/blob/master/jezz-images/images/concurrent/轻量级锁膨胀.png )

## 原子操作的实现原理

### CAS (compare and swap) 

cas操作需要输入2个值，一个旧值和一个新值，在操作期间先比较旧值有没有发生变化，如果没有发生变化，才交换新值，发生了变化则不交换

#### 处理器原子操作

1，通过总线锁保持原子性 2，通过缓存锁定来保证原子性

#### java通过CAS原子操作三大问题

1) ABA问题 因为CAS操作值的时候，先检查值有没有变化，如果没有发生变化就更新，但是如果一个值从A变到B又变回到A，进行CAS操作检查值的时候会发现他的值没有变化，但实际上市变化了
解决方案: 使用版本号，在变量前面追加版本号，每次变量更新的时候把版本号加1，1A->2B->3A;（jdk1.5解决)

2) 循环时间长开销大 自旋CAS如果长时间不成功，会给cpu带来很大的开销（解决方案：jvm提供支持处理器提供的pause指令。没看懂。。。）

3) 只能保证一个共享变量的原子操作 对多个共享变量操作时循环CAS无法保证操作的原子性，这个时候可以用锁，或者将多个共享变量合成一个变量来操作



