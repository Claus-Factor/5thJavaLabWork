package com.labs.reflection;

import com.labs.reflection.bean.SomeBean;
import com.labs.reflection.injector.Injector;

public class Main {
    public static void main(String[] args) throws Exception {
        Injector injector = new Injector();
        injector.loadConfig("./config.properties");

        SomeBean someBean = injector.inject(new SomeBean());
        someBean.foo(); // Выведет "AC"
    }
}