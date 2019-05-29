package com.example.reflectdemo.reflect.Proxy;

import java.lang.reflect.Proxy;

public class MyProxy {

    public static void main (String [] args) {
        testClass3();
    }

    public static void testClass3() {
        Class1Interface class1 = new Class1();
        Class1Interface class1Proxy = (Class1Interface) Proxy.newProxyInstance(
                class1.getClass().getClassLoader(),
                new Class<?>[] {Class1Interface.class},
                new InvocationHandlerTest(class1));
        class1Proxy.doSomething();
    }




}
