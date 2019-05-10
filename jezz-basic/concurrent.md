### sychornized

    public class SynchronizedTest {
            public synchronized void method(){
        
            }
        
            public void code(){
                synchronized (this){
        
                }
            }
    }

   运行代码 javac SynchronizedTest.java  
   反编译 javap -c SynchronizedTest
   结果如下:
   
   ![Image text](https://github.com/Jezzlouis/jezz-middle/blob/master/jezz-images/images/concurrent/concurrent_synchornized_1.png)
    
1.同步代码块
 上图可见同步代码块是由monitorenter 和 monitorexit 指令实现的;
 monitorenter指令插入到同步代码块的开始位置，monitorexit指令插入到同步代码块的结束位置，JVM需要保证每一个monitorenter都有一个monitorexit与之相对应。
 任何对象都有一个monitor与之相关联，当且一个monitor被持有之后，他将处于锁定状态。线程执行到monitorenter指令时，将会尝试获取对象所对应的monitor所有权，
 即尝试获取对象的锁；
    
    
2.同步方法:
 上图无法看出同步方法实现,在VM字节码层面并没有任何特别的指令来实现被synchronized修饰的方法，
 而是在[Class文件的方法表](https://blog.csdn.net/luanlouis/article/details/41113695)中将该方法的access_flags字段中的synchronized标志位置1，
 表示该方法是同步方法并使用调用该方法的对象或该方法所属的Class在JVM的内部对象表示Klass做为锁对象
    
### reen