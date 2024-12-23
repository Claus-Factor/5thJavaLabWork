package com.labs.reflection.impl;

import com.labs.reflection.interfaces.SomeInterface;

public class OtherImpl implements SomeInterface {
    @Override
    public void doSomething() {
        System.out.println("B");
    }
}