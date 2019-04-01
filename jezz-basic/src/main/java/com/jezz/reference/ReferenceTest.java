package com.jezz.reference;

import org.junit.Test;

import java.lang.ref.*;

/**
 * 四种引用
 */
public class ReferenceTest {

    // 强引用
    @Test
    public void strongeReferenceTest(){
        Object o = new Object();
        Object[] objects = new Object[1000];
    }

    //对于软引用关联着的对象，只有在内存不足的时候JVM才会回收该对象。
    // 看到这里你就知道SoftReference到底什么时候被被回收了，它和使用的策略（默认应该是LRUCurrentHeapPolicy），
    // 堆可用大小，该SoftReference上一次调用get方法的时间都有关系
    // 因此，这一点可以很好地用来解决OOM的问题，并且这个特性很适合用来实现缓存：比如网页缓存、图片缓存等
    @Test
    public void softReferenceTest(){
        SoftReference<String> sr = new SoftReference<>(new String("abc"));
        System.out.println(sr.get());
        System.gc();
        System.out.println(sr.get());
    }
    // ReferenceQueue referenceQueue = new ReferenceQueue();

    // 弱引用 当JVM进行垃圾回收时，无论内存是否充足，都会回收被弱引用关联的对象
    @Test
    public void weakReferenceTest(){
        WeakReference<String> weakReference = new WeakReference<>(new String("abcd"));
        System.out.println(weakReference.get());
        System.gc();                //通知JVM的gc进行垃圾回收
        System.out.println(weakReference.get());
    }

    // 虚引用必须和引用队列 （ReferenceQueue）联合使用 虚引用主要用来跟踪对象被垃圾回收的活动
    // 在堆外内存DirectByteBuffer中就是用Cleaner进行堆外内存的回收，这也是虚引用在java中的典型应用。
    @Test
    public void phantomReferenceTest(){
        ReferenceQueue<String> queue = new ReferenceQueue<String>();
        PhantomReference<String> pr = new PhantomReference<String>(new String("hello"), queue);
        System.out.println(pr.get());
    }

    @Test
    public void phantomDemoReferenceTest() throws InterruptedException {
        Object obj = new Object();
        ReferenceQueue<Object> refQueue =new ReferenceQueue<>();
        PhantomReference<Object> phanRef =new PhantomReference<>(obj, refQueue);

        Object objg = phanRef.get();
        //这里拿到的是null
        System.out.println(objg);
        //让obj变成垃圾
        obj=null;
        System.gc();
        Thread.sleep(3000);

        //gc后会将phanRef加入到refQueue中
        Reference<? extends Object> phanRefP = refQueue.remove();
        //这里输出true
        System.out.println(phanRefP==phanRef);
    }

}
