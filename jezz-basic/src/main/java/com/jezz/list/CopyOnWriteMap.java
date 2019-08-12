package com.jezz.list;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CopyOnWriteMap<k,v> implements Map<k,v>,Cloneable {

    private volatile Map<k,v> internalMap;

    public CopyOnWriteMap() {
        internalMap = new HashMap<>();
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public v get(Object key) {
        return internalMap.get(key);
    }

    @Override
    public v put(k key, v value) {
        synchronized (this){
            Map<k,v> newMap = new HashMap<>(internalMap);
            v val = newMap.put(key, value);
            internalMap = newMap;
            return val;
        }
    }

    @Override
    public v remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends k, ? extends v> m) {
        synchronized (this) {
            Map<k,v> newMap = new HashMap<>(internalMap);
            newMap.putAll(m);
            internalMap = newMap;
        }

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<k> keySet() {
        return null;
    }

    @Override
    public Collection<v> values() {
        return null;
    }

    @Override
    public Set<Entry<k, v>> entrySet() {
        return null;
    }
}
