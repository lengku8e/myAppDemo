package com.example.reflectdemo;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

public class AMN {

    private static final Singleton<ClassB2Interface> gDefault = new Singleton<ClassB2Interface>() {
        protected ClassB2Interface create() {
            ClassB b2 = new ClassB();
            b2.id = 2;
            return b2;
        }
    };

    static public ClassB2Interface getDefault() {
        return gDefault.get();
    }

    /**
     * 对泛型类的反射
     * @param s
     */
    public static void main(String s []) {
        AMN.getDefault().doSomething(); // 先把单例初始化
        test2(); // hook单例产生的对象
        AMN.getDefault().doSomething(); // 单例对象方法调用
    }


    public static void test2() {
        try {
            // 取出单例中一个字段
            Class<?> singleton = Class.forName("com.example.reflectdemo.Singleton");
            Field mInstanceField = singleton.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);

            //获取AMN的gDefault单例gDefault，gDefault是静态的
            Class<?> activityManagerNativeClass = Class.forName("com.example.reflectdemo.AMN");
            Field gDefaultField = activityManagerNativeClass.getDeclaredField("gDefault");
            gDefaultField.setAccessible(true);
            Object gDefault = gDefaultField.get(null); // 取出静态字段

            // AMN的gDefault对象里面原始的 B2对象
            Object rawB2Object = mInstanceField.get(gDefault);

            // 创建一个这个对象的代理对象ClassB2Mock, 然后替换这个字段, 让我们的代理对象帮忙干活
            Class<?> classB2Interface = Class.forName("com.example.reflectdemo.ClassB2Interface");

            Object proxy = Proxy.newProxyInstance(
                    Thread.currentThread().getContextClassLoader(),
                    new Class<?>[] { classB2Interface },
                    new ClassB2Mock(rawB2Object));

            mInstanceField.set(gDefault, proxy);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
