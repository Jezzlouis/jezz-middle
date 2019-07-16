## ConcurrentHashMap

为什么不使用hashMap: 

1.7 put会导致死循环: rehash的时候 将每个链表转化到新链表，并且链表中的位置发生反转，而这在多线程情况下是很容易造成链表回路，从而发生 get() 死循环
1.8 进行了优化 用 head 和 tail 来保证链表的顺序和之前一样 但是还有数据丢失等弊端(并发本身的问题)

### 内部类 Node

    static class Node<K,V> implements Map.Entry<K,V> {
        final int hash;
        final K key;
        volatile V val;             //带有volatile，保证可见性
        volatile Node<K,V> next;    //下一个节点的指针

        Node(int hash, K key, V val, Node<K,V> next) {
            this.hash = hash;
            this.key = key;
            this.val = val;
            this.next = next;
        }

        public final K getKey()       { return key; }
        public final V getValue()     { return val; }
        public final int hashCode()   { return key.hashCode() ^ val.hashCode(); }
        public final String toString(){ return key + "=" + val; }
        /** 不允许修改value的值 */
        public final V setValue(V value) {
            throw new UnsupportedOperationException();
        }

        public final boolean equals(Object o) {
            Object k, v, u; Map.Entry<?,?> e;
            return ((o instanceof Map.Entry) &&
                    (k = (e = (Map.Entry<?,?>)o).getKey()) != null &&
                    (v = e.getValue()) != null &&
                    (k == key || k.equals(key)) &&
                    (v == (u = val) || v.equals(u)));
        }

        /**  赋值get()方法 */
        Node<K,V> find(int h, Object k) {
            Node<K,V> e = this;
            if (k != null) {
                do {
                    K ek;
                    if (e.hash == h &&
                            ((ek = e.key) == k || (ek != null && k.equals(ek))))
                        return e;
                } while ((e = e.next) != null);
            }
            return null;
        }
    }


### ConcurrentHashMap的初始化

### put操作

### get操作

### size操作

### 扩容

### 红黑树转换