package com.jezz.list;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRU<k,v> extends LinkedHashMap<k,v> implements Map<k,v> {

    public LRU(int initialCapacity, float loadFactor, boolean accessOrder) {
        super(initialCapacity, loadFactor, accessOrder);
    }

    @Override
    protected boolean removeEldestEntry(Entry<k, v> eldest) {
        if(size() > 6){
            return true;
        }
        return false;
    }
}
