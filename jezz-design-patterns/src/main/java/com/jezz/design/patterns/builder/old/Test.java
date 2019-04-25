package com.jezz.design.patterns.builder.old;

public class Test {
    public static void main(String[] args) {
        OldUser1 oldUser1 = new OldUser1("1","jezz");
        OldUser2 oldUser2 = new OldUser2();
        oldUser2.setAddress("");
    }
}
