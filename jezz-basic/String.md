String、StringBuffer、StringBuilder 的区别？

String ，是只读字符串，也就意味着 String 引用的字符串内容是不能被改变的。

每次对 String 类型进行改变的时候，都会生成一个新的 String 对象，然后将指针指向新的 String 对象。

StringBuffer(线程安全)/StringBuilder 类，表示的字符串对象可以直接进行修改。

String 为什么是不可变的？

    > 源码 :
    > private final char value[];
    
StringBuilder 与 StringBuffer 都继承自 AbstractStringBuilder 类，在 AbstractStringBuilder 中也是使用字符数组保存字符串 char[] value,
但是没有用 final 关键字修饰
    
    > 源码 :
    > char[] value;
    
StringTokenizer 是什么？
StringTokenizer ，是一个用来分割字符串的工具类。

int 和 Integer 有什么区别？
注意下 Integer 的缓存策略


