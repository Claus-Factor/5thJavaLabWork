package com.labs.reflection.bean;

import com.labs.reflection.annotations.AutoInjectable;
import com.labs.reflection.interfaces.SomeInterface;
import com.labs.reflection.interfaces.SomeOtherInterface;

public class SomeBean {
    @AutoInjectable
    private SomeInterface field1;
    @AutoInjectable
    private SomeOtherInterface field2;

    public void foo() {
        field1.doSomething();
        field2.doSomeOther();
    }
}