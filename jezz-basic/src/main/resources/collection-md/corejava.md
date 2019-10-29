### final finally finalize
final 修饰类不可被继承,修饰方法不可被覆盖,修饰变量引用地址不可变,但是值可变
finally try catch后 表示总是执行 但是也有不执行的时候 
1. try里调用了 System.exit(0)
2. try里出现异常
finalize object对象的一个方法,对象被回收的时候调用,只会执行一次


