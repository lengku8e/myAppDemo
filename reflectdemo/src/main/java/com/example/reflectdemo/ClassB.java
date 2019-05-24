package com.example.reflectdemo;

public class ClassB implements ClassB2Interface {
    public int id;
    @Override
    public void doSomething() {
        System.out.print("ClassB doSomeThing");
    }
}
