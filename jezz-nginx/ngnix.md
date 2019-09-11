# nginx

## 架构

### Nginx进程模型
Nginx启动后以多进程的模式在后台运行，包含一个master进程和多个work进程。

master进程的作用: 主要用来管理work进程，包含:接收外界的信号，向work进程发送信号，监听work进程的运行状态 ===> 推导出我们只需要跟master进程进行通信就行了
如何控制master进程: 老版本通过kill -HUP pid 重新加载配置文件 生成新的进程接收新的请求，并且信号通知老进程失效了不再接收请求。新版本通过./nginx -s reload重新加载配置文件。

work进程的来源:每个work进程是由master进程fork过来的。 

work进程的作用:处理请求(看懂这里需要先了解 socket通信原理)

在master进程里面首先建立好了需要listen的socket，然后fork出多个work进程，每个进程都可以去accept socket，当一个请求进来的时候，所有work进程都会收到这个通知，
但是只有一个进程最终可以accept这个socket，其他进程都会失败--->这个就叫惊群效应---->惊群效应的坏处就是会唤醒所有的work进程，性能降低。
 Nginx通过在accept上加共享锁来解决（即accept_mutex），同一时刻只有一个进程在accept连接。

### Nginx事件模型
网络事件 异步非阻塞
信号
定时器