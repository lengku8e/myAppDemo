package com.example.reflectdemo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class ClassB2Mock implements InvocationHandler {

    Object rawB2Object;
    public ClassB2Mock(Object rawB2Object) {
        this.rawB2Object = rawB2Object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if ("doSomething".equals(method.getName())) {
            System.out.print("ClassB2Mock doSomething");
        }
        return method.invoke(rawB2Object, args);
    }
}
