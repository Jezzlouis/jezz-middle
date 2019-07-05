package com.jezz.collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class CollectionTest {

    @Test
    public void ArrayListTest(){
        List<Integer>  list = new ArrayList<>();
        List<Integer>  arrayList = new ArrayList<>(2);
    }

    @Test
    public void LinkedListTest(){
        List<Integer> list = new LinkedList<>();
    }

    @Test
    public void VectorTest(){
        List<Integer> list = new Vector<>();
    }
}
